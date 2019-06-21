package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import lombok.Data;

@Data
public class TripRequestDTO {
    private String query;
    private String time;
    private Float longitude;
    private Float latitude;
}
