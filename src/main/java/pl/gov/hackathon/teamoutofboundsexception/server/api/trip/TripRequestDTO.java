package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import lombok.Data;

@Data
public class TripRequestDTO {
    private String actualPlaceName;
    private String time;
    private Float longtitude;
    private Float latitude;
}
