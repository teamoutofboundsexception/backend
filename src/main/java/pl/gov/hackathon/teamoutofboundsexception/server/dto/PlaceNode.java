package pl.gov.hackathon.teamoutofboundsexception.server.dto;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class PlaceNode {

    @Id
    @GeneratedValue
    private Long placeId;
    private Integer cityId;
    private Integer placeTypeId;
    private String placeName;
    private Float mapX;
    private Float mapY;
    private String streetName;
    private String houseNumber;
    private Integer apartmentNumber;
    private Float normalAVGPrice;

    public PlaceNode(Integer cityId, Integer placeTypeId, String placeName, Float mapX, Float mapY, String streetName,
                 String houseNumber, Integer apartmentNumber, Float normalAVGPrice) {
        this.cityId = cityId;
        this.placeTypeId = placeTypeId;
        this.placeName = placeName;
        this.mapX = mapX;
        this.mapY = mapY;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.normalAVGPrice = normalAVGPrice;
    }
}
