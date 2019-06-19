package pl.gov.hackathon.teamoutofboundsexception.server.integration.parser;

import org.apache.commons.csv.CSVRecord;
import pl.gov.hackathon.teamoutofboundsexception.server.dto.Place;

public class PlaceParser_WykazMuzeow extends PlaceParser {

    public PlaceParser_WykazMuzeow(AtributeMaping cities, AtributeMaping placeTypes, String outputEncoding,
                                   String inputEncoding) {
        super(cities, placeTypes, outputEncoding, inputEncoding);
    }

    @Override
    public Place parsePlace(CSVRecord record) {
        placeId = 100000000 + Integer.parseInt(record.get(0));
        cityId = cities.get(record.get(3));

        if (cityId == null) {
            cityId = cities.put(record.get(3));
        }

        placeTypeId = placeTypes.get("Muzeum");
        placeName = record.get(1);
        mapX = null;
        mapY = null;
        streetName = record.get(7) + " " + record.get(8);
        houseNumber = null;
        apartmentNumber = null;

        try {
            houseNumber = record.get(9);
        } catch (NumberFormatException e) {
            System.out.println("housenumber:" + e);
            System.out.println(record.get(9));

            //happens, do nothing just leave null
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

        try {
            apartmentNumber = Integer.parseInt(record.get(10));
            //apartmentNumber = record.get(10);

        } catch (NumberFormatException e) {
            System.out.println("apartmentnumber:" + e);
            System.out.println(record.get(10));
            //happens, do nothing just leave null

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

        normalAVGPrice = null;

        Place place = new Place(placeId, cityId, placeTypeId, placeName, mapX, mapY, streetName, houseNumber, apartmentNumber, normalAVGPrice);

        return place;
    }
}
