package pl.gov.hackathon.teamoutofboundsexception.server.integration.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.List;

public abstract class PlaceParser {

    protected String inputEncoding;
    protected String outputEncoding;
    protected AtributeMaping cities;
    protected AtributeMaping placeTypes;

    //temporal for parsing single record:
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

    @Autowired
    public PlaceParser(AtributeMaping cities, AtributeMaping placeTypes, String outputEncoding, String inputEncoding) {
        this.cities = cities;
        this.placeTypes = placeTypes;
        this.inputEncoding = inputEncoding;
        this.outputEncoding = outputEncoding;
    }

    public abstract Place parsePlace(CSVRecord record);

    //public abstract int reformat(InputStream ins, OutputStream outs);
    public int reformat(InputStream ins, OutputStream outs) {

        try {
            InputStreamReader rd = new InputStreamReader(ins, inputEncoding);
            Writer wr = new OutputStreamWriter(outs, outputEncoding);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().parse(rd);

            for (CSVRecord record : records) {
                Place place = parsePlace(record);

                wr.write(place.createRecordCSV(';') + "\n");
            }

        } catch (Exception e) {
            System.out.println("Ah shit, here we go again.\n" + e);
        }
        return 0;
    }

    public int parseto_list(InputStream ins, List<Place> lst) {

        try {
            InputStreamReader rd = new InputStreamReader(ins, inputEncoding);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().parse(rd);

            for (CSVRecord record : records) {
                Place place = parsePlace(record);

                lst.add(place);
            }
        } catch (Exception e) {
            System.out.println("Ah shit, here we go again.\n" + e);
        }
        return 0;
    }
}