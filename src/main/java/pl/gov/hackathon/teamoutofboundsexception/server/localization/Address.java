package pl.gov.hackathon.teamoutofboundsexception.server.localization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;
    private String house_number;
    private String road;
    private String postcode;
}
