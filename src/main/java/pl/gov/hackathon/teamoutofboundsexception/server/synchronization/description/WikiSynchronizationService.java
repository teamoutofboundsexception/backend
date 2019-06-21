package pl.gov.hackathon.teamoutofboundsexception.server.synchronization.description;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WikiSynchronizationService {

        public String getDescription(String placeName){

            final String WIKI_API_EXTRACT = "https://pl.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&titles=";

            String placeN = placeName.replace(" ", "_");
            String url = WIKI_API_EXTRACT + placeN;

            return getResponse(url, "extract");
        }

        private String getResponse(String url, String tag){
            try {
                RestTemplate restTemplate = new RestTemplate();
                ObjectMapper objectMapper = new ObjectMapper();

                ResponseEntity<String> response
                        = restTemplate.getForEntity(url, String.class);

                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode queryNode = rootNode.path("query");
                JsonNode pagesNode = queryNode.path("pages");
                JsonNode endNode = pagesNode.elements().next().path(tag);

                return endNode.toString()
                        .replaceAll("(?:<style.+?>.+?</style>|<script.+?>.+?</script>|<(?:!|/?[a-zA-Z]+).*?/?>)","");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        //in case whole content is needed
        void getHtml(String placeName){

            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();

            String placeN = placeName.replace(" ", "_");
            String fooResourceUrl
                    = "https://pl.wikipedia.org/api/rest_v1/page/html/" + placeN;

            ResponseEntity<String> response
                    = restTemplate.getForEntity(fooResourceUrl, String.class);
            String s = response.toString();
            s = s.substring(s.indexOf(",") + 1);

            System.out.println(s);
            //parseHtml(s);
            //return getResponse(fooResourceUrl, "revisions");
        }
}
