package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;
import pl.gov.hackathon.teamoutofboundsexception.server.placesGraph.Graph;
import pl.gov.hackathon.teamoutofboundsexception.server.repositories.PlaceRepository;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class TripAdvisorService {

    private PlaceRepository placeRepository;

    @Autowired
    public TripAdvisorService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<TripPlaceDTO> getTripAdvise(TripPlacePromise promise) {
        List<PlaceModel> tempList = placeRepository.findByCityNameAndMapXBetweenAndMapYBetween(promise.getActualPlaceName(), promise.getLongtitude() - (float) 20.0, promise.getLongtitude() + (float) 20.0,promise.getLatitude() - (float) 20.0, promise.getLatitude() + (float) 20.0);

        Graph graph = new Graph();
        graph.initGraph(tempList, LocalTime.of(4, 0));
        graph.computeTrips();
        //ArrayList<Trip> trips = graph.getTrips();

        graph.printTrips();

        // TODO zwracanie
        return new LinkedList<>();
    }
}
