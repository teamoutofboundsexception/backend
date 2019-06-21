package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import lombok.Data;
import pl.gov.hackathon.teamoutofboundsexception.server.placesGraph.Vertex;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class TripPlaceDTO {
    private String title;
    private String text;
    private String time;
    private Float longitude;
    private Float latitude;

    public TripPlaceDTO(String title, String text, LocalTime time, Float longitude, Float latitude) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        this.title = title;
        this.text = text;
        this.time = time.format(dtf);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public TripPlaceDTO(Vertex v) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        this.title = v.getPlaceName();
        this.text = "Lorem Ipsum Dolor";
        this.time = v.getAvgTimeSpent().format(dtf);
        this.longitude = v.getMapY();
        this.latitude = v.getMapX();
    }
}
