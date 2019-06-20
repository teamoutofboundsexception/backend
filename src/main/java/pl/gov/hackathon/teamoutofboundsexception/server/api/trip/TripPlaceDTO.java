package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class TripPlaceDTO {
    private String title;
    private String text;
    private String time;
    private Float longtitude;
    private Float latitude;

    public TripPlaceDTO(String title, String text, LocalTime time, Float longtitude, Float latitude) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        this.title = title;
        this.text = text;
        this.time = time.format(dtf);
        this.longtitude = longtitude;
        this.latitude = latitude;
    }
}
