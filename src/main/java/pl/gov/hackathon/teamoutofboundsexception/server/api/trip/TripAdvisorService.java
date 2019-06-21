package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;
import pl.gov.hackathon.teamoutofboundsexception.server.placesGraph.Graph;
import pl.gov.hackathon.teamoutofboundsexception.server.placesGraph.Trip;
import pl.gov.hackathon.teamoutofboundsexception.server.repositories.PlaceRepository;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TripAdvisorService {

    private PlaceRepository placeRepository;

    private class WikiApiQuery {

        String getDescription(String placeName){

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
    @Autowired
    public TripAdvisorService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<List<TripPlaceDTO>> getTripAdvise(TripPlacePromise promise) {
        LocalTime x = LocalTime.now();

        List<PlaceModel> tempList = placeRepository.findByCityNameAndMapXBetweenAndMapYBetweenAndOpeningTimeIsBeforeAndClosingTimeIsAfter(promise.getActualPlaceName(), promise.getLongitude() - (float) 20.0, promise.getLongitude() + (float) 20.0,promise.getLatitude() - (float) 20.0, promise.getLatitude() + (float) 20.0, x, x);

        Graph graph = new Graph();
        graph.initGraph(tempList, promise.getTime());
        graph.computeTrips();

        ArrayList<Trip> trips = graph.getTrips();
        //graph.printTrips();
        return prepareResponse(trips);
    }

    List<List<TripPlaceDTO>> prepareResponse(ArrayList<Trip> trips) {
        List<List<TripPlaceDTO>> toReturn = new LinkedList<>();

        trips.forEach(trip -> {

            List<TripPlaceDTO> newTrip = new LinkedList<>();

            trip.forEach(vertex -> {
                TripPlaceDTO dto = new TripPlaceDTO(vertex,
                        new WikiApiQuery().getDescription(vertex.getPlaceName()));
                newTrip.add(dto);
            });

            toReturn.add(newTrip);
        });

        return toReturn;
    }
}
