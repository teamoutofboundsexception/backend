package placesGraph;

import dbCon.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class Graph {

    private static final String queryBeginning = "call GetPlacesList(";

    private ArrayList<Vertex> vertices;
    private ArrayList<Trip> trips;
    private ResultSet resultSet = null;

    private LocalTime availableTime;

    public Graph(){
        this.vertices = new ArrayList<>();
        this.trips = new ArrayList<>();
    }
    public Graph(ResultSet resultSet, LocalTime availableTime){
        this();
        this.availableTime = availableTime;
        this.resultSet = resultSet;
    }

    private String constructQuery(float mapX, float mapY, int cityId, LocalTime availableTime, float coordR) {
        StringBuilder sb = new StringBuilder(queryBeginning);
        sb.append(mapX);
        sb.append(',');
        sb.append(mapY);
        sb.append(',');
        sb.append(cityId);
        sb.append(",'");
        sb.append(availableTime.toString());
        sb.append("',");
        sb.append(coordR);
        sb.append(");");
        return sb.toString();
    }

    public void initGraph(String username, String password, float mapX, float mapY, int cityId, LocalTime availableTime, float coordR) throws SQLException {
        initGraph(
                new DBConnection(username, password)
                        .executeQuery(
                                constructQuery(mapX, mapY, cityId, availableTime, coordR)), availableTime);
    }

    public void initGraph(ResultSet resultSet, LocalTime availableTime) throws SQLException {
        this.availableTime = availableTime;
        while(resultSet.next()){
            int placeId = resultSet.getInt("placeId");
            int cityId = resultSet.getInt("cityId");
            int placeTypeId = resultSet.getInt("placeTypeId");
            String placeName = resultSet.getString("placeName");
            float mapX = resultSet.getFloat("mapX");
            float mapY = resultSet.getFloat("mapY");
            String placeAddress = resultSet.getString("address");
            float normalPrice = resultSet.getFloat("normalPrice");
            Time avgTimeSpent = resultSet.getTime("avgTimeSpent");
            Time openingTime = resultSet.getTime("openingTime");
            Time closingTime = resultSet.getTime("closingTime");

            Vertex vertex = new Vertex(
                    placeId, cityId, placeTypeId, placeName,
                    mapX, mapY, placeAddress,
                    normalPrice, avgTimeSpent.toLocalTime(),
                    openingTime.toLocalTime(), closingTime.toLocalTime()
            );
            for(Vertex v : this.vertices){
                vertex.setEdgeWith(v);
            }
            this.vertices.add(vertex);
        }
    }

    public void computeTrips(){
        for(Vertex v : this.vertices){
            if (v.getAvgTimeSpent().compareTo(this.availableTime) <= 0) {
                LocalTime totalTime = v.getAvgTimeSpent();
                ArrayList<Vertex> tmp = (ArrayList<Vertex>) this.vertices.clone();
                tmp.remove(v);
                Trip trip = new Trip();
                trip.add(v);
                recursiveComputeTrips(v, trip, tmp, totalTime);
            }
        }
    }

    private void recursiveComputeTrips(Vertex vertex, Trip trip, ArrayList<Vertex> leftV, LocalTime totalTime){
        for(Edge edge : vertex.getEdges()){
            Vertex other = edge.getOtherV(vertex);
            if(leftV.contains(other)) {
                LocalTime e = edge.getVal();
                LocalTime v = other.getAvgTimeSpent();
                LocalTime t = Converter.addTime(totalTime, e);
                t = Converter.addTime(t, v);
                if (t.compareTo(this.availableTime) > 0) {
                    this.trips.add(trip);
                }
                else {
                    totalTime = t;
                    Trip tmpTrip = (Trip) trip.clone();
                    tmpTrip.add(other);
                    ArrayList<Vertex> tmp = (ArrayList<Vertex>) leftV.clone();
                    tmp.remove(other);
                    recursiveComputeTrips(other, tmpTrip, tmp, totalTime);
                }
            }
        }
    }

    public void printNodes() {
        for(Vertex v : this.vertices){
            int placeId = v.getPlaceId();
            String placeName = v.getPlaceName();
            float mapX = v.getMapX();
            float mapY = v.getMapY();
            LocalTime avgTimeSpent = v.getAvgTimeSpent();
            LocalTime openingTime = v.getOpeningTime();
            LocalTime closingTime = v.getClosingTime();

            System.out.println(placeId + "\t" + placeName + "\t" + mapX + "\t" + mapY + "\t" + avgTimeSpent + "\t" + openingTime.toString() + "\t" + closingTime.toString());
        }
    }
    public void printGraph() {
        int i = 0;
        for(Vertex v : this.vertices) {
            for(Edge e : v.getEdges()){
                System.out.println(i++ + ": " +
                        "(" + e.getV1().getPlaceId() + "{ avgT:" + e.getV1().getAvgTimeSpent()  + "}" + ")"
                        + "-[" + e.getVal() + "]->" +
                        "(" + e.getV2().getPlaceId() + "{ avgT:" + e.getV2().getAvgTimeSpent()  + "}" + ")");
            }
            System.out.println();
        }
    }

    public void printTrips(){
        int i = 0;
        for(Trip trip : trips){
            System.out.print(i++);
            for(Vertex v : trip){
                System.out.print(" -> ");
                System.out.print(v.getPlaceId());
            }
            System.out.println();
        }
    }

}
