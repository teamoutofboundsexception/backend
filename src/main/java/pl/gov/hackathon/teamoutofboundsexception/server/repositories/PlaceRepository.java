package pl.gov.hackathon.teamoutofboundsexception.server.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;

public interface PlaceRepository extends JpaRepository<PlaceModel, Integer> {
    PlaceModel findByHash(int hash);
}
