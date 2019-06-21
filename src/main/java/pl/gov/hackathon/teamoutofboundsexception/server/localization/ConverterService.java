package pl.gov.hackathon.teamoutofboundsexception.server.localization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ConverterService {
    private final String BASE_API = "https://nominatim.openstreetmap.org/";

    public AddressWrapper cordinatesToAdress(Float latitude, Float longtitude) {
        RestTemplate restTemplate = SSLRestTemplateFactory.retrieve();
        ObjectMapper objectMapper = new ObjectMapper();

        String url = BASE_API + "reverse?lat=" + latitude.toString() + "&lon=" + longtitude.toString() + "&format=json";

        ResponseEntity<String> json = restTemplate.getForEntity(url, String.class);

        try {
            return objectMapper.readValue(json.getBody(), AddressWrapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO skasowac
        return null;
    }

    public List<Cordinates> addressToCordinates(Address address) throws IOException {
        RestTemplate restTemplate = SSLRestTemplateFactory.retrieve();
        ObjectMapper objectMapper = new ObjectMapper();

        String url = BASE_API + "search";

        boolean road = address.getRoad() != null && !address.getRoad().isEmpty();
        boolean city = address.getCity() != null && !address.getCity().isEmpty();
        boolean postalCode = address.getPostcode() != null && !address.getPostcode().isEmpty();
        boolean houseNumber = address.getHouse_number() != null && !address.getHouse_number().isEmpty();

        if (road) {
            url = url + "?street=" + address.getRoad();

            if (houseNumber) {
                url = url + " " + address.getHouse_number();
            }
        }

        if (city) {
            if (road)  {
                url = url + "&city=" + address.getCity();
            } else {
                url = url + "?city=" + address.getCity();
            }
        }

        if (postalCode) {
            if (road || city)  {
                url = url + "&postalcode=" + address.getPostcode();
            } else {
                url = url + "?postalcode=" +  address.getPostcode();
            }
        }

        url = url + "&format=json";

        ResponseEntity<String> json = restTemplate.getForEntity(url, String.class);

        Cordinates[] myObjects = objectMapper.readValue(json.getBody(), Cordinates[].class);

        return myObjects.length > 0 ? Arrays.asList(myObjects) : Collections.emptyList();
    }
}
