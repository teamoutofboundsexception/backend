package pl.gov.hackathon.teamoutofboundsexception.server.dto;

public class Place {

    protected Integer placeId;
    protected Integer cityId;
    protected Integer placeTypeId;
    protected String placeName;
    protected Float mapX;
    protected Float mapY;
    protected String streetName;
    protected String houseNumber;
    protected Integer apartmentNumber;
    protected Float normalAVGPrice;
    protected Float rating;
    protected Integer visitorsNo;
    protected Integer followerNo;
    protected Integer likesNo;
    protected Float avgTimeSpent;


    public Place(Integer placeId, Integer cityId, Integer placeTypeId, String placeName, Float mapX, Float mapY, String streetName,
                 String houseNumber, Integer apartmentNumber, Float normalAVGPrice) {
        this.placeId = placeId;
        this.cityId = cityId;
        this.placeTypeId = placeTypeId;
        this.placeName = placeName;
        this.mapX = mapX;
        this.mapY = mapY;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.normalAVGPrice = normalAVGPrice;
        this.rating = null;
        this.visitorsNo = null;
        this.followerNo = null;
        this.likesNo = null;
        this.avgTimeSpent = null;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String createRecordCSV(char delimeter) {
        return "" + placeId + delimeter + cityId + delimeter + placeTypeId + delimeter + mapX + delimeter + mapY
                + delimeter + streetName + delimeter + houseNumber + delimeter + normalAVGPrice + delimeter +
                rating+ delimeter + visitorsNo + delimeter + followerNo + delimeter + likesNo
                + delimeter + avgTimeSpent;
    }
}
