package pl.gov.hackathon.teamoutofboundsexception.server.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;

import java.util.List;

public interface PlaceRepository extends JpaRepository<PlaceModel, Integer> {
    PlaceModel findByHash(int hash);

    List<PlaceModel> findByMapXAndMapY(Float mapX, Float mapY);

    // TODO add time and orderby like in procedure
    List<PlaceModel> findByCityNameAndMapXBetweenAndMapYBetween(String cityName, Float x, Float y, Float z, Float ads);
}
