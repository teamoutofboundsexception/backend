package pl.gov.hackathon.teamoutofboundsexception.server.synchronization.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.gov.hackathon.teamoutofboundsexception.server.localization.SSLRestTemplateFactory;

import java.io.IOException;
import java.util.Collections;

@Service
public class ImageSynchronizationService {

    private final String key1="63fe996aec9e4346937b38bbe62cfff3";
    private final String key2="8a0504f73fcb4fb3a83d98c2780ce862";

    private final String BASE_API="https://api.cognitive.microsoft.com/bing/v7.0/images/search?q=";

    private final String mkt = "&mkt=pl-PL";

    public String getImageUrl(String queryValue) throws IOException {
        RestTemplate restTemplate = SSLRestTemplateFactory.retrieve();
        HttpEntity<String> httpEntity = prepareRequestHeaders();

        ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_API + queryValue + mkt, HttpMethod.GET, httpEntity, String.class);

        ObjectMapper mapper = new ObjectMapper();

        ImageWrapper details = mapper.readValue(responseEntity.getBody(), ImageWrapper.class);

        if (details.getValue() != null && details.getValue().size() > 0) {
            for (ImageInfo imageInfo : details.getValue()) {
                if (!imageInfo.contentUrl.isEmpty() && imageInfo.width >= 512 && imageInfo.height >= 256)  {
                    return imageInfo.contentUrl;
                }
            }
        }

        return null;
    }

    private HttpEntity<String> prepareRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Ocp-Apim-Subscription-Key", key1);
        return new HttpEntity<>("body", headers);
    }
}