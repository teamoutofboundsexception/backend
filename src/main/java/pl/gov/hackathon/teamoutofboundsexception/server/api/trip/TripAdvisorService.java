package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TripAdvisorService {

    public List<TripPlaceDTO> getTripAdvise(TripPlacePromise promise) {

        return new LinkedList<>();

    }
}
