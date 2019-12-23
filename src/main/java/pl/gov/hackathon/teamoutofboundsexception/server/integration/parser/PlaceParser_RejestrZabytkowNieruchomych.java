package pl.gov.hackathon.teamoutofboundsexception.server.integration.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class PlaceParser_RejestrZabytkowNieruchomych extends PlaceParser {

    public PlaceParser_RejestrZabytkowNieruchomych(String outputEncoding, String inputEncoding) {
        super(new AtributeMaping(), new AtributeMaping(), outputEncoding, inputEncoding);
    }

    @Override
    public Place parsePlace(CSVRecord record) {
        String tmp = record.get(0);
        try {

            placeId = 110000000 + Integer.parseInt(tmp.substring(tmp.lastIndexOf('.') + 1));
        } catch (Exception e) {
            System.out.println("Exception ocured parsing id: " + e);
        }

        cityId = cities.get(record.get(11));
        cityName = record.get(11);
        postalCode = null;

        if (cityId == null) {
            cityId = cities.put(record.get(11));
        }

        placeTypeId = placeTypes.get(record.get(5));
        if (placeTypeId == null) {
            placeTypeId = placeTypes.put(record.get(5));
        }
        placeName = record.get(3);
        mapX = null;
        mapY = null;

        if (record.get(12).length() > 0) {
            streetName = "ul. " + record.get(12);
        } else streetName = null;
        if (record.get(13).length() > 0) {
            houseNumber = record.get(13);
        } else houseNumber = null;

        apartmentNumber = null;

        normalAVGPrice = null;


        Place place = new Place(placeId, cityId, cityName, postalCode, placeTypeId, placeName, mapX, mapY, streetName, houseNumber, apartmentNumber, normalAVGPrice);
        //ALPHA VERSION ONLY
        if (record.get(10).toLowerCase().contains("warszawa") == false){
            return null;
        }

        return place;
    }

    @Override
    public int parseto_list(InputStream ins, List<Place> lst) {

        try {
            InputStreamReader rd = new InputStreamReader(ins, inputEncoding);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().parse(rd);

            for (CSVRecord record : records) {
                Place place = parsePlace(record);
                //ALPHA VERSION ONLY
                if(place != null) {
                    lst.add(place);
                }
            }

        }catch (Exception e) {
            System.out.println("Ah shit, here we go again.\n" + e);
        }
        return 0;
    }
}