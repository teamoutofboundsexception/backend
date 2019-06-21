package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gov.hackathon.teamoutofboundsexception.server.localization.Address;
import pl.gov.hackathon.teamoutofboundsexception.server.localization.AddressWrapper;
import pl.gov.hackathon.teamoutofboundsexception.server.localization.ConverterService;
import pl.gov.hackathon.teamoutofboundsexception.server.localization.Cordinates;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;
import pl.gov.hackathon.teamoutofboundsexception.server.placesGraph.Graph;
import pl.gov.hackathon.teamoutofboundsexception.server.placesGraph.Trip;
import pl.gov.hackathon.teamoutofboundsexception.server.repositories.PlaceRepository;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class TripAdvisorService {
    private PlaceRepository placeRepository;
    private ConverterService converterService;

    @Autowired
    public TripAdvisorService(PlaceRepository placeRepository, ConverterService converterService) {
        this.placeRepository = placeRepository;
        this.converterService = converterService;
    }

    public List<List<TripPlaceDTO>> getTripAdvise(TripPlacePromise promise) {

        // We have cordinates
        if (promise.getLatitude() != null && promise.getLongitude() != null) {

            // but We Have cityName
            if (promise.getCityName() != null && !promise.getCityName().isEmpty()) {
                return prepareTripAdviceBasedOnLocationAndCityName(promise);

                // but we dont have cityName
            } else {
                AddressWrapper x = converterService.cordinatesToAdress(promise.getLongitude(), promise.getLatitude());
                Address address = x.getAddress();

                return prepareTripAdviceBasedOnLocationAndCityName(new TripPlacePromise(address.getCity(), promise.getTime(), promise.getLongitude(), promise.getLatitude()));
            }

            // We dont have cordinates
        } else if (promise.getCityName() != null && promise.getStreet() != null) {
            List<Cordinates> list = null;

            try {
                list = converterService.addressToCordinates(new Address(promise.getCityName(), promise.getHouseNumber(), promise.getStreet(), null));
            } catch (IOException e) {
               log.error("Error during retrieving cordinates");
            }

            if (list.size() > 0) {
                return prepareTripAdviceBasedOnLocationAndCityName(new TripPlacePromise(promise.getCityName(), null, promise.getHouseNumber(), promise.getTime(), list.get(0).lat, list.get(0).lon));
            }
        }

        return null;
    }

    private List<List<TripPlaceDTO>> prepareTripAdviceBasedOnLocationAndCityName(TripPlacePromise promise) {
        LocalTime x = LocalTime.now();

        List<PlaceModel> tempList = placeRepository.findByCityNameAndMapXBetweenAndMapYBetweenAndOpeningTimeIsBeforeAndClosingTimeIsAfterAndImageUrlIsNotNull(promise.getCityName(), promise.getLongitude() - (float) 20.0, promise.getLongitude() + (float) 20.0,promise.getLatitude() - (float) 20.0, promise.getLatitude() + (float) 20.0, x, x);

        Graph graph = new Graph();
        graph.initGraph(tempList, promise.getTime());
        graph.computeTrips(promise.getLongitude(), promise.getLatitude());

        ArrayList<Trip> trips = graph.getTrips();
        //graph.printTrips();
        return prepareResponse(trips);
    }

    List<List<TripPlaceDTO>> prepareResponse(ArrayList<Trip> trips) {
        List<List<TripPlaceDTO>> toReturn = new LinkedList<>();

        trips.forEach(trip -> {

            List<TripPlaceDTO> newTrip = new LinkedList<>();

            trip.forEach(vertex -> newTrip.add(new TripPlaceDTO(vertex)));

            toReturn.add(newTrip);
        });

        return toReturn;
    }
}
