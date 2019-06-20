package pl.gov.hackathon.teamoutofboundsexception.server.placesGraph;

import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;

import java.time.LocalTime;
import java.util.ArrayList;

public class Vertex {

    private int placeId, cityId, placeTypeId;
    private String placeName, placeAddress;
    private float mapX, mapY, normalPrice;
    private LocalTime avgTimeSpent, openingTime, closingTime;
    private ArrayList<Edge> edges;
    private boolean visited;

    public Vertex(PlaceModel place) {
        this.placeId = place.getPlaceId();
        this.cityId = place.getCityId();

        // TODO check if placeTypeId is null in DB
        //this.placeTypeId = place.getPlaceTypeId();
        this.placeTypeId = 0;
        this.placeName = place.getPlaceName();
        this.mapX = place.getMapX();
        this.mapY = place.getMapY();
        this.placeAddress = place.getAddress();
        this.normalPrice = place.getNormalPrice();
        this.avgTimeSpent = LocalTime.of(Integer.parseInt(place.getAvgTimeSpent().toString().substring(0, 2)), Integer.parseInt(place.getAvgTimeSpent().toString().substring(3)));
        this.openingTime = place.getOpeningTime();
        this.closingTime = place.getClosingTime();
        this.edges = new ArrayList<>();
    }

    public Vertex(int placeId, int cityId, int placeTypeId, String placeName,
                  float mapX, float mapY, String placeAddress,
                  float normalPrice, LocalTime avgTimeSpent,
                  LocalTime openingTime, LocalTime closingTime) {
        this.placeId = placeId;
        this.cityId = cityId;
        this.placeTypeId = placeTypeId;
        this.placeName = placeName;
        this.mapX = mapX;
        this.mapY = mapY;
        this.placeAddress = placeAddress;
        this.normalPrice = normalPrice;
        this.avgTimeSpent = avgTimeSpent;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.edges = new ArrayList<>();
    }

    public void setEdgeWith(Vertex vertex){
        LocalTime val = Converter.coordinatesToTime(this.mapX, this.mapY, vertex.getMapX(), vertex.getMapY());
        Edge edge = new Edge(this, vertex, val);
        this.edges.add(edge);
        vertex.getEdges().add(edge);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
    public LocalTime getAvgTimeSpent() {
        return avgTimeSpent;
    }
    public LocalTime getClosingTime() {
        return closingTime;
    }
    public float getMapX() {
        return mapX;
    }
    public float getMapY() {
        return mapY;
    }
    public float getNormalPrice() {
        return normalPrice;
    }
    public LocalTime getOpeningTime() {
        return openingTime;
    }
    public int getCityId() {
        return cityId;
    }
    public int getPlaceId() {
        return placeId;
    }
    public int getPlaceTypeId() {
        return placeTypeId;
    }
    public String getPlaceAddress() {
        return placeAddress;
    }
    public String getPlaceName() {
        return placeName;
    }
    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
