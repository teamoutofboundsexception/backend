package pl.gov.hackathon.teamoutofboundsexception.server.api.trip;

import lombok.Getter;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class TripPlacePromise {
    private String cityName;
    private String street;
    private String houseNumber;
    private LocalTime time;
    private Float longitude;
    private Float latitude;

    public TripPlacePromise(String cityName, LocalTime time, Float longitude, Float latitude) {
        this.cityName = cityName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public TripPlacePromise(String cityName, String street, String houseNumber, LocalTime time, Float longitude, Float latitude) {
        this.cityName = cityName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public TripPlacePromise(TripRequestDTO dto) {
        Pattern p = Pattern.compile("(\\p{IsAlphabetic}*,?) (\\d+[a-zA-z]?,?)?\\s?(\\p{IsAlphabetic}*)");

        if (dto.getQuery() != null && !dto.getQuery().isEmpty()) {
            Matcher m = p.matcher(dto.getQuery());

            //String entireAddress = null;
            String street = null;
            String houseNumber = null;
            String cityName = null;


            if (m.find()) {
                //entireAddress = m.group(0); // to remember

                if (m.group(1) != null && m.group(1).contains(",")) {
                    street = m.group(1).replaceAll(",", "");
                } else {
                    street = m.group(1);
                }

                if (m.group(2) != null && m.group(2).contains(",")) {
                    houseNumber = m.group(2).replaceAll(",", "");
                } else {
                    houseNumber = m.group(2);
                }

                cityName = m.group(3);

                this.cityName = cityName;
                this.street = street;
                this.houseNumber = houseNumber;
            }
        }

        this.time = LocalTime.of(Integer.parseInt(dto.getTime().substring(0, 2)), Integer.parseInt(dto.getTime().substring(3)));
        this.longitude = dto.getLongitude();
        this.latitude = dto.getLatitude();
    }
}
