package pl.gov.hackathon.teamoutofboundsexception.server.integration.parser;

import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalTime;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

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
    protected String apartmentNumber;
    protected Float normalAVGPrice;
    protected Float rating;
    protected Integer visitorsNo;
    protected Integer followerNo;
    protected Integer likesNo;
    protected LocalTime avgTimeSpent;

    public Place(Integer placeId, Integer cityId, String cityName, String postalCode, Integer placeTypeId, String placeName, Float mapX, Float mapY, String streetName,
                 String houseNumber, String apartmentNumber, Float normalAVGPrice) {
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
        this.rating = null;
        this.visitorsNo = null;
        this.followerNo = null;
        this.likesNo = null;
        this.avgTimeSpent = null;
    }

    public static byte[] int2array(Integer n) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(n).array();
        return bytes;
    }

    public static byte[] float2array(Float f){
        byte[] bytes = ByteBuffer.allocate(4).putFloat(f).array();
        return bytes;
    }

    // TODO ustalic która metoda jakie pola bierze pod uwage
    public String md5HashCode(){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(int2array(cityId));
            messageDigest.update(cityName.getBytes());
            messageDigest.update(postalCode.getBytes());

            if (placeTypeId != null) {
                messageDigest.update(int2array(placeTypeId));
            }

            messageDigest.update(placeName.getBytes());

            if (mapX != null) {
                messageDigest.update(float2array(mapX));
            }

            if (mapY != null) {
                messageDigest.update(float2array(mapY));
            }

            messageDigest.update(streetName.getBytes());

            byte[] digest = messageDigest.digest();
            return DatatypeConverter.printHexBinary(digest);
        } catch(NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        return null;
    }

    // TODO ustalic która metoda jakie pola bierze pod uwage
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(cityId)
                .append(cityName)
                .append(postalCode)
                .append(placeTypeId)
                .append(placeName)
                .append(mapX)
                .append(mapY)
                .append(streetName)
                .toHashCode();
    }

    public String createRecordCSV(char delimeter) {
        return "" + placeId + delimeter + cityId + delimeter + placeTypeId + delimeter + mapX + delimeter + mapY
                + delimeter + streetName + delimeter + houseNumber + delimeter + normalAVGPrice + delimeter +
                rating+ delimeter + visitorsNo + delimeter + followerNo + delimeter + likesNo
                + delimeter + avgTimeSpent;
    }
}
