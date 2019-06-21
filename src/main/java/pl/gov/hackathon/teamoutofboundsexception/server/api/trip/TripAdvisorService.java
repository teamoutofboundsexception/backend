package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;
import pl.gov.hackathon.teamoutofboundsexception.server.placesGraph.Graph;
import pl.gov.hackathon.teamoutofboundsexception.server.placesGraph.Trip;
import pl.gov.hackathon.teamoutofboundsexception.server.repositories.PlaceRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TripAdvisorService {

    private PlaceRepository placeRepository;

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
                TripPlaceDTO dto = new TripPlaceDTO(vertex);
                newTrip.add(dto);
            });

            toReturn.add(newTrip);
        });

        return toReturn;
    }
}
