package pl.gov.hackathon.teamoutofboundsexception.server.integration.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes {
    public String format;
    public int file_size;
    public String title;
    public String download_url;
    public String file_url;
}