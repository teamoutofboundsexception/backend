package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import lombok.Data;
import pl.gov.hackathon.teamoutofboundsexception.server.placesGraph.Vertex;

import java.time.format.DateTimeFormatter;

@Data
public class TripPlaceDTO {
    private String title;
    private String text;
    private String time;
    private Float longitude;
    private Float latitude;
    private String rating;
    private String imageUrl;
    private Boolean wheelChairFriendly;
    private Boolean blindFriendly;

    public TripPlaceDTO(Vertex v) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        this.title = v.getPlaceName();
        this.time = v.getAvgTimeSpent().format(dtf);
        this.longitude = v.getMapY();
        this.latitude = v.getMapX();
        this.rating = v.getRating().toString().substring(0, 4);
        this.imageUrl = v.getImageUrl();
        this.text = v.getPlaceDescription();
        this.blindFriendly = v.getBlindFriendly();
        this.wheelChairFriendly = v.getWheelChairFriendly();
    }
}
