package pl.gov.hackathon.teamoutofboundsexception.server.integration.parser;

import org.apache.commons.csv.CSVRecord;
import pl.gov.hackathon.teamoutofboundsexception.server.localization.Address;
import pl.gov.hackathon.teamoutofboundsexception.server.localization.ConverterService;
import pl.gov.hackathon.teamoutofboundsexception.server.localization.Cordinates;

import java.io.IOException;
import java.util.List;

public class PlaceParser_WykazMuzeow extends PlaceParser {

    private ConverterService converterService;

    public PlaceParser_WykazMuzeow(ConverterService converterService, String outputEncoding, String inputEncoding) {
        super(new AtributeMaping(), new AtributeMaping(), outputEncoding, inputEncoding);

        this.converterService = converterService;
    }

    @Override
    public Place parsePlace(CSVRecord record) throws IOException {
        placeId = 100000000 + Integer.parseInt(record.get(0));
        cityId = cities.get(record.get(3));
        cityName = record.get(3);
        postalCode = record.get(2);
        if (cityId == null) {
            cityId = cities.put(record.get(3));
        }

        placeTypeId = placeTypes.get("Muzeum");
        placeName = record.get(1);


        streetName = record.get(7) + " " + record.get(8);
        houseNumber = null;
        houseNumber = record.get(9);

        if (record.get(10) != null && !record.get(10).isEmpty()) {
            apartmentNumber = record.get(10);
        } else {
            apartmentNumber = null;
        }

        List<Cordinates> cordinates = converterService.addressToCordinates(new Address(cityName, houseNumber, streetName, postalCode));

       if (cordinates.size() > 0) {
            // TODO check and change
            mapX = cordinates.get(0).lat;
            mapY = cordinates.get(0).lon;
        } else {
            mapX = null;
            mapY = null;
        }

        normalAVGPrice = null;

        return new Place(placeId, cityId, cityName, postalCode, placeTypeId, placeName, mapX, mapY, streetName, houseNumber, apartmentNumber, normalAVGPrice);
    }
}
