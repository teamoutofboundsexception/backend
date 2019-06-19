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
import pl.gov.hackathon.teamoutofboundsexception.server.dto.Place;
import pl.gov.hackathon.teamoutofboundsexception.server.integration.parser.CitiesMaping;
import pl.gov.hackathon.teamoutofboundsexception.server.integration.parser.PlaceParser;
import pl.gov.hackathon.teamoutofboundsexception.server.integration.parser.PlaceParser_WykazMuzeow;
import pl.gov.hackathon.teamoutofboundsexception.server.integration.pojo.ResourceDetail;
import pl.gov.hackathon.teamoutofboundsexception.server.integration.pojo.ResourcesDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/integration")
public class IntegrationController {
    private String tempFileAbsolutePath;
    private CitiesMaping citiesMaping;
    private String outputEncoding;
    private String inputEncoding;
    private List<Place> placeList;

    @Autowired
    public IntegrationController(CitiesMaping citiesMaping) {
        this.citiesMaping = citiesMaping;

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

        FileInputStream input = new FileInputStream( tempFileAbsolutePath + "Wykaz_muzeow_(csv).csv");
        PlaceParser placeparser = new PlaceParser_WykazMuzeow(citiesMaping, outputEncoding, inputEncoding);
        placeparser.parseto_list(input, placeList);

        // IMPORTANT THING
        input.close();

        System.out.println(placeList.size());
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
