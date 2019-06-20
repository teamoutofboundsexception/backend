package pl.gov.hackathon.teamoutofboundsexception.server.localization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cordinates {
    public Float lat;
    public Float lon;
}
