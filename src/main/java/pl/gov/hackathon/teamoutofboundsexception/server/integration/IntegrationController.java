package pl.gov.hackathon.teamoutofboundsexception.server.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gcardone.junidecode.Junidecode;
import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.gov.hackathon.teamoutofboundsexception.server.ServerApplication;

import pl.gov.hackathon.teamoutofboundsexception.server.integration.parser.*;
import pl.gov.hackathon.teamoutofboundsexception.server.integration.pojo.ResourceDetail;
import pl.gov.hackathon.teamoutofboundsexception.server.integration.pojo.ResourcesDetails;
import pl.gov.hackathon.teamoutofboundsexception.server.localization.ConverterService;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;
import pl.gov.hackathon.teamoutofboundsexception.server.repositories.PlaceRepository;
import pl.gov.hackathon.teamoutofboundsexception.server.synchronization.description.WikiSynchronizationService;
import pl.gov.hackathon.teamoutofboundsexception.server.util.Sleepy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/integration")
public class IntegrationController {
    private String tempFileAbsolutePath;

    private String outputEncoding;
    private String inputEncoding;
    private List<Place> placeList;

    private PlaceRepository placeRepository;
    private ConverterService converterService;
    private WikiSynchronizationService wikiSynchronizationService;

    @Autowired
    public IntegrationController(PlaceRepository placeRepository, ConverterService converterService, WikiSynchronizationService wikiSynchronizationService) {

        this.placeRepository = placeRepository;
        this.converterService = converterService;
        this.wikiSynchronizationService = wikiSynchronizationService;

        ApplicationHome home = new ApplicationHome(ServerApplication.class);
        tempFileAbsolutePath = home.getDir().getAbsolutePath() + File.separator + "temp" + File.separator;

        outputEncoding = "utf-8";
        inputEncoding = "Windows-1250";

        placeList = new LinkedList<>();
    }

    @GetMapping("/clean")
    public void cleanTemp() throws IOException {
        FileUtils.cleanDirectory(new File(tempFileAbsolutePath));
    }

    // @TODO move URLS to properties file
    @GetMapping("/perform")
    public void downloadData() throws IOException {
        getDataUrlAndDownloadData("https://api.dane.gov.pl/datasets/443,rejestry-muzeow/resources");
        getDataUrlAndDownloadData("https://api.dane.gov.pl/datasets/1385,rejestr-zabytkow-nieruchomych-2/resources");
        getDataUrlAndDownloadData("https://api.dane.gov.pl/datasets/1180,obiekty-wpisane-na-liste-swiatowego-dziedzictwa-unesco/resources");
        getDataUrlAndDownloadData("https://api.dane.gov.pl/datasets/210,rejestr-zabytkow-archeologicznych/resources");
        getDataUrlAndDownloadData("https://api.dane.gov.pl/datasets/1130,rejestr-zabytkow-nieruchomych/resources");
        getDataUrlAndDownloadData("https://api.dane.gov.pl/datasets/168,pomniki-historii/resources");

        FileInputStream museumInput = new FileInputStream( tempFileAbsolutePath + "Wykaz_muzeow_(csv).csv");
        //FileInputStream rznInput = new FileInputStream( tempFileAbsolutePath + "Rejestr_zabytkow_nieruchomych___plik_w_formacie_CSV.csv");

        PlaceParser museumParser = new PlaceParser_WykazMuzeow(converterService, outputEncoding, inputEncoding);
        museumParser.parseto_list(museumInput, placeList);

        /*PlaceParser rzn = new PlaceParser_RejestrZabytkowNieruchomych(outputEncoding, inputEncoding);
        rzn.parseto_list(rznInput, placeList);*/

        // IMPORTANT THING
        museumInput.close();
        //rznInput.close();

        List<PlaceModel> places = new LinkedList<>();

        placeList.forEach(place -> {

            String placeDescription = wikiSynchronizationService.getDescription(place.getPlaceName());
            Sleepy.sleep(1);

            places.add(new PlaceModel(place.getPlaceId(), place.getCityId(), place.getCityName(), place.getPostalCode(), place.getPlaceTypeId(), place.getPlaceName(), place.getMapX(), place.getMapY(), place.getStreetName(), place.getHouseNumber(), place.getApartmentNumber(), place.md5HashCode() + new Integer(place.hashCode()).toString(), placeDescription));
        });

        places.forEach(n -> {
            if (places.size() > 0) {
                boolean placeExists = placeRepository.findByHash(n.getHash()) != null;
                if (!placeExists) {
                    placeRepository.save(n);
                }
            }
        });

        placeList.clear();
    }

    private void downloadDataSet(String downloadUrl, String fileName) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(downloadUrl, HttpMethod.GET, entity, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Files.write(Paths.get(fileName), response.getBody());
        }
    }

    private void getDataUrlAndDownloadData(String resourceUrl) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        ResourcesDetails details = objectMapper.readValue(response.getBody(), ResourcesDetails.class);

        for (ResourceDetail detail : details.data) {
            String fileName = Junidecode.unidecode(detail.attributes.title).replaceAll("-", "_").replaceAll("\\s+", "_") + "." + detail.attributes.format;

            downloadDataSet(detail.attributes.download_url, tempFileAbsolutePath + fileName);
        }
    }
}
