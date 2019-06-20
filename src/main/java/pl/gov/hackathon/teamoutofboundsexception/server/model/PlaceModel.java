package pl.gov.hackathon.teamoutofboundsexception.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor
public class PlaceModel {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "place_seq")
    @SequenceGenerator(
            name = "place_seq",
            sequenceName = "place_seq"
    )
    private Integer placeId;
    private Integer cityId;
    private String cityName;
    private String postalCode;
    private Integer placeTypeId;
    private String placeName;
    private Float mapX;
    private Float mapY;
    private String address;
    private Float normalPrice;
    private int hash;

    private LocalTime openingTime;
    private LocalTime closingTime;
    private Float avgTimeSpent;

    public PlaceModel(Integer cityId, String cityName, String postalCode, Integer placeTypeId, String placeName, Float mapX, Float mapY, String streetName,
                      String houseNumber, Integer apartmentNumber, Float normalPrice, Integer hash) {
        this.placeId = placeId;
        this.cityId = cityId;
        this.cityName = cityName;
        this.postalCode = postalCode;
        this.placeTypeId = placeTypeId;
        this.placeName = placeName;
        this.mapX = mapX;
        this.mapY = mapY;
        this.address = streetName;
        this.hash = hash;

        if (houseNumber != null && !houseNumber.isEmpty()) {
            address = address + " " + houseNumber;
        }

        if (apartmentNumber != null) {
            address = address + "/" + apartmentNumber;
        }

        this.normalPrice = normalPrice;
    }
}
