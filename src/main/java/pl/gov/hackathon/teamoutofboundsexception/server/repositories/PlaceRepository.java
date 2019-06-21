package pl.gov.hackathon.teamoutofboundsexception.server.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;

import java.time.LocalTime;
import java.util.List;

public interface PlaceRepository extends JpaRepository<PlaceModel, Integer> {
    PlaceModel findByHash(String hash);

    List<PlaceModel> findByCityNameAndImageUrlIsNull(String cityName);

    // TODO add orderby like in procedure
    List<PlaceModel> findByCityNameAndMapXBetweenAndMapYBetweenAndOpeningTimeIsBeforeAndClosingTimeIsAfter(String cityName, Float v, Float x, Float y, Float z, LocalTime after, LocalTime before);

    List<PlaceModel> findByCityNameAndMapXBetweenAndMapYBetweenAndOpeningTimeIsBeforeAndClosingTimeIsAfterAndImageUrlIsNull(String cityName, Float v, Float x, Float y, Float z, LocalTime after, LocalTime before);
}
