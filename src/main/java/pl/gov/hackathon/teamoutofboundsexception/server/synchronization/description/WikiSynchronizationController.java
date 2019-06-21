package pl.gov.hackathon.teamoutofboundsexception.server.synchronization.description;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;
import pl.gov.hackathon.teamoutofboundsexception.server.repositories.PlaceRepository;
import pl.gov.hackathon.teamoutofboundsexception.server.util.Sleepy;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/wiki")
public class WikiSynchronizationController {
    private WikiSynchronizationService wikiSynchronizationService;
    private PlaceRepository placeRepository;

    @Autowired
    public WikiSynchronizationController(WikiSynchronizationService wikiSynchronizationService, PlaceRepository placeRepository) {
        this.wikiSynchronizationService = wikiSynchronizationService;
        this.placeRepository = placeRepository;
    }

    @GetMapping("/perform")
    public ResponseEntity<String> test() {
        List<PlaceModel> list =  placeRepository.findByCityNameAndPlaceDescriptionIsNull("Warszawa");

        list.forEach(place -> {
            String description = wikiSynchronizationService.getDescription(place.getPlaceName());

            Sleepy.sleep(1);

            PlaceModel tempPlace = place;
            tempPlace.setPlaceDescription(description);

            placeRepository.save(tempPlace);
        });

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
