package pl.gov.hackathon.teamoutofboundsexception.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Random;

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
    private String hash;

    private LocalTime openingTime;
    private LocalTime closingTime;
    private LocalTime avgTimeSpent;
    private Float rating;
    private Integer visitorsNo;
    private Integer followerNo;
    private Integer likesNo;

    private static final Random RANDOM = new Random();

    public PlaceModel(Integer placeId, Integer cityId, String cityName, String postalCode, Integer placeTypeId, String placeName, Float mapX, Float mapY, String streetName,
                      String houseNumber, String apartmentNumber, String hash) {
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

        add_random_atributes();
    }

    public static float randomFloat(float min, float max) {
        return RANDOM.nextFloat()*(max-min)+min;
    }

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public void add_random_atributes() {
        rating = randomFloat(0, 5);
        visitorsNo = randomInt(0, 10000);
        followerNo = randomInt(0,100);
        likesNo = randomInt(0,10000);
        avgTimeSpent = LocalTime.of(15, 30);
        normalPrice = randomFloat(10, 120);

        int openningHour = randomInt(6,10);

        openingTime =  LocalTime.now().withHour(openningHour);

        int closingTemp = openningHour + randomInt(6,12);

        if (closingTemp > 23) {
            closingTemp = 23;
        }

        closingTime = LocalTime.now().withHour(closingTemp);
    }
}
