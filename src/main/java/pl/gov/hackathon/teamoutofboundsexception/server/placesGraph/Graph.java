package pl.gov.hackathon.teamoutofboundsexception.server.placesGraph;

import pl.gov.hackathon.teamoutofboundsexception.server.dbCon.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Graph {

    private static final String queryBeginning = "call GetPlacesList(";

    private ArrayList<Vertex> vertices;
    private ResultSet resultSet = null;

    public Graph(){
        this.vertices = new ArrayList<>();
    }
    public Graph(ResultSet resultSet){
        this();
        this.resultSet = resultSet;
    }

    private String constructQuery(float mapX, float mapY, int cityId, String availableTime, float coordR) {
        StringBuilder sb = new StringBuilder(queryBeginning);
        sb.append(mapX);
        sb.append(',');
        sb.append(mapY);
        sb.append(',');
        sb.append(cityId);
        sb.append(",'");
        sb.append(availableTime);
        sb.append("',");
        sb.append(coordR);
        sb.append(");");
        return sb.toString();
    }

    public void initGraph(String username, String password, float mapX, float mapY, int cityId, String availableTime /*TIME AS STRING*/, float coordR) throws SQLException {
        initGraph(
                new DBConnection(username, password)
                        .executeQuery(
                                constructQuery(mapX, mapY, cityId, availableTime, coordR)));
    }

    public void initGraph(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            int placeId = resultSet.getInt("placeId");
            int cityId = resultSet.getInt("cityId");
            int placeTypeId = resultSet.getInt("placeTypeId");
            String placeName = resultSet.getString("placeName");
            float mapX = resultSet.getFloat("mapX");
            float mapY = resultSet.getFloat("mapY");
            String placeAddress = resultSet.getString("address");
            float normalPrice = resultSet.getFloat("normalPrice");
            float avgTimeSpent = resultSet.getFloat("avgTimeSpent");
            float openingTime = resultSet.getFloat("openingTime");
            float closingTime = resultSet.getFloat("closingTime");

            this.vertices.add(new Vertex(
                placeId, cityId, placeTypeId, placeName,
                mapX, mapY, placeAddress,
                normalPrice, avgTimeSpent,
                openingTime, closingTime
            ));
        }
    }

    public void computeTrips(){
        //TUDUDUUUU
    }
}
