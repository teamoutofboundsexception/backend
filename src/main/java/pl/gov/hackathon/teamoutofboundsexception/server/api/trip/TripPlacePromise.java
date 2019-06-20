package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import java.time.LocalTime;

public class TripPlacePromise {
    private String actualPlaceName;
    private LocalTime time;
    private Float longtitude;
    private Float latitude;

    public TripPlacePromise(TripRequestDTO dto) {
        this.actualPlaceName = dto.getActualPlaceName();
        this.time = LocalTime.of(Integer.parseInt(dto.getTime().substring(0, 2)), Integer.parseInt(dto.getTime().substring(3)));
        this.longtitude = dto.getLongtitude();
        this.latitude = dto.getLatitude();
    }
}
