package pl.gov.hackathon.teamoutofboundsexception.server.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import pl.gov.hackathon.teamoutofboundsexception.server.dto.PlaceNode;

@Repository
public interface PlaceNodeRepository extends Neo4jRepository<PlaceNode, Long> {
}
