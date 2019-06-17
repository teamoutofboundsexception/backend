package pl.gov.hackaton.teamoutofboundsexception.integration.files;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public abstract class PlaceParser {
	
	protected String inputEncoding;
	protected String outputEncoding;
	protected CitiesMaping cities;
	
	
	
	//temporal for parsing single record:
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
	
	
	public PlaceParser( CitiesMaping cities, String outputEncoding, String inputEncoding ) {
		this.cities = cities;
		this.inputEncoding = inputEncoding;
		this.outputEncoding = outputEncoding;
	}

	public abstract Place parsePlace(CSVRecord record);

	//public abstract int reformat(InputStream ins, OutputStream outs);
	public int reformat(InputStream ins, OutputStream outs){
		
		
		
		try {
			InputStreamReader rd = new InputStreamReader(ins, inputEncoding );
			Writer wr = new OutputStreamWriter(outs, outputEncoding);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().parse(rd);
			for (CSVRecord record : records) {
				Place place = parsePlace(record);
				//System.out.print('a');
			    wr.write(place.createRecordCSV(';') + "\n");
			    //System.out.println(place.getPlaceName());
			}
		
		}catch(Exception e) {
			System.out.println("Ah shit, here we go again.\n" + e );
		}		return 0;
	}
	public int parseto_list(InputStream ins, ArrayList<Place> lst){
		
		
		
		try {
			InputStreamReader rd = new InputStreamReader(ins, inputEncoding );
			//Writer wr = new OutputStreamWriter(outs, outputEncoding);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().parse(rd);
			for (CSVRecord record : records) {
				Place place = parsePlace(record);
				//System.out.print('a');
			    lst.add(place);
			    //System.out.println(place.getPlaceName());
			}
		
		}catch(Exception e) {
			System.out.println("Ah shit, here we go again.\n" + e );
		}		return 0;
	}
	//public int parse_to_list(Reader rd, ArrayList<Place> lst);
}
