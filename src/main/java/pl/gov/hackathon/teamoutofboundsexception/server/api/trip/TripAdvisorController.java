package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/trip")
public class TripAdvisorController {

    private TripAdvisorService tripAdvisorService;

    @Autowired
    public TripAdvisorController(TripAdvisorService tripAdvisorService) {
        this.tripAdvisorService = tripAdvisorService;
    }

    @PostMapping("/advise")
    public ResponseEntity<List<List<TripPlaceDTO>>> getTripAdvise(@RequestBody TripRequestDTO request) {
        return new ResponseEntity<>(tripAdvisorService.getTripAdvise(new TripPlacePromise(request)), HttpStatus.OK);
    }
}
