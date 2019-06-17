package pl.gov.hackathon.teamoutofboundsexception.server.integration.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceDetail {
    public Attributes attributes;
    public String id;
}
