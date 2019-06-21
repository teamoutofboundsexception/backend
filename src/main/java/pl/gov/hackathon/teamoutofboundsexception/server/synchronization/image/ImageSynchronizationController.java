package pl.gov.hackathon.teamoutofboundsexception.server.synchronization.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;
import pl.gov.hackathon.teamoutofboundsexception.server.repositories.PlaceRepository;
import pl.gov.hackathon.teamoutofboundsexception.server.util.Sleepy;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/image")
public class ImageSynchronizationController {

    private ImageSynchronizationService imageSynchronizationService;
    private PlaceRepository placeRepository;

    @Autowired
    public ImageSynchronizationController(ImageSynchronizationService imageSynchronizationService, PlaceRepository placeRepository) {
        this.imageSynchronizationService = imageSynchronizationService;
        this.placeRepository = placeRepository;
    }

    @GetMapping("/perform")
    public ResponseEntity<String> getImagesURL() {

        List<PlaceModel> list = placeRepository.findByCityNameAndImageUrlIsNull("Warszawa");

        list.forEach(place -> {
            try {
                String imageUrl = imageSynchronizationService.getImageUrl(place.getPlaceName());

                Sleepy.sleep(2);

                PlaceModel tempPlace = place;
                tempPlace.setImageUrl(imageUrl);

                placeRepository.save(tempPlace);

            } catch (IOException e) {
                log.error("Error while retrieving image for: " + place.getPlaceName());
            }
        });

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
