package pl.gov.hackaton.teamoutofboundsexception.integration.files;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class PlaceParser_WykazMuzeow  extends PlaceParser {
	
	
	
	
	
	
	

	public PlaceParser_WykazMuzeow(CitiesMaping cities, String outputEncoding, String inputEncoding) {
		super(cities, outputEncoding, inputEncoding);
	}


	@Override
	public Place parsePlace(CSVRecord record) {
		
		placeId = 1000000 + Integer.parseInt(record.get(0));
	    
	    cityId = cities.get(record.get(3));
	    if (cityId == null) {
	    	cityId = cities.put(record.get(3));
	    }
	    
	    placeTypeId = 1; //1 to muzeum?
	    placeName = record.get(1);
	    mapX = null;
	    mapY = null;
	    streetName = record.get(7) + " " + record.get(8);
	    houseNumber = null;
	    apartmentNumber = null;
	    try {
	    	houseNumber = record.get(9);
	    }catch (NumberFormatException e) {
	    	System.out.println("housenumber:" + e);
	    	System.out.println(record.get(9));
	    	
	    	//happens, do nothing just leave null
	    }catch (Exception e) {
	    	System.out.println("Exception:" + e);
	    }
	    try {
	    	apartmentNumber = Integer.parseInt(record.get(10));
	    	//apartmentNumber = record.get(10);
	    	
	    }catch (NumberFormatException e) {
	    	System.out.println("apartmentnumber:" + e);
	    	System.out.println(record.get(10));
	    	//happens, do nothing just leave null
	    	
	    }catch (Exception e) {
	    	System.out.println("Exception:" + e);
	    }
	    
	    
	    normalAVGPrice = null;
	    //System.out.println('a');
	    
	    Place place = new Place(placeId,cityId, placeTypeId, placeName, mapX, mapY, streetName, houseNumber, apartmentNumber, normalAVGPrice);
	    //System.out.println('b');
	    return place;
		
	}

	
	





}
