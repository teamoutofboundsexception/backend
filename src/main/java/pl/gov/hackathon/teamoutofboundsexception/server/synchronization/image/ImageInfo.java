package pl.gov.hackathon.teamoutofboundsexception.server.synchronization.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageInfo {
    public String contentUrl;
    public Integer width;
    public Integer height;
}
