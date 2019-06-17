package pl.gov.hackaton.teamoutofboundsexception.integration.files;

public class Place{
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
		
		
	}
	public String getPlaceName() {
		return placeName;
	}
	public String createRecordCSV(char delimeter) {
		String result = new String();
		//String d = new String();
		// d = d + delimeter;
		result = "" + placeId + delimeter + cityId + delimeter + placeTypeId + delimeter + mapX + delimeter + mapY
				+ delimeter + streetName + delimeter + houseNumber + delimeter + normalAVGPrice;
		
		return result;
	}
}