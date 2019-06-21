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

    public TripPlaceDTO(Vertex v, String text) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        this.title = v.getPlaceName();
        this.text = text;
        this.time = v.getAvgTimeSpent().format(dtf);
        this.longitude = v.getMapY();
        this.latitude = v.getMapX();
        this.rating = v.getRating().toString().substring(0, 4);
        this.imageUrl = v.getImageUrl();
    }
}
