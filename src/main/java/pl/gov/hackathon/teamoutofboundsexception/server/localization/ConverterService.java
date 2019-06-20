package pl.gov.hackathon.teamoutofboundsexception.server.localization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ConverterService {
    private final String BASE_API = "https://nominatim.openstreetmap.org/";

    public AddressWrapper cordinatesToAdress(Float latitude, Float longtitude) {
        RestTemplate restTemplate = createRestTemplateWithSSL();
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
        RestTemplate restTemplate = createRestTemplateWithSSL();
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

    private RestTemplate createRestTemplateWithSSL() {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        try {
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();

            requestFactory.setHttpClient(httpClient);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }

        return new RestTemplate(requestFactory);
    }
}
