package pl.gov.hackathon.teamoutofboundsexception.server.integration.parser;

import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public class Place {

    protected Integer placeId;
    protected Integer cityId;
    protected String cityName;
    protected String postalCode;
    protected Integer placeTypeId;
    protected String placeName;
    protected Float mapX;
    protected Float mapY;
    protected String streetName;
    protected String houseNumber;
    protected Integer apartmentNumber;
    protected Float normalAVGPrice;

    public Place(Integer placeId, Integer cityId, String cityName, String postalCode, Integer placeTypeId, String placeName, Float mapX, Float mapY, String streetName,
                 String houseNumber, Integer apartmentNumber, Float normalAVGPrice) {
        this.placeId = placeId;
        this.cityId = cityId;
        this.cityName = cityName;
        this.postalCode = postalCode;
        this.placeTypeId = placeTypeId;
        this.placeName = placeName;
        this.mapX = mapX;
        this.mapY = mapY;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.normalAVGPrice = normalAVGPrice;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(cityId)
                .append(placeTypeId)
                .append(placeName)
                .append(mapX)
                .append(mapY)
                .toHashCode();
    }

    public String createRecordCSV(char delimeter) {
        return "" + placeId + delimeter + cityId + delimeter + placeTypeId + delimeter + mapX + delimeter + mapY
                + delimeter + streetName + delimeter + houseNumber + delimeter + normalAVGPrice;
    }
}
