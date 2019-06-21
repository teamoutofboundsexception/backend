package pl.gov.hackathon.teamoutofboundsexception.server.synchronization.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageWrapper {
    public List<ImageInfo> value;
}
