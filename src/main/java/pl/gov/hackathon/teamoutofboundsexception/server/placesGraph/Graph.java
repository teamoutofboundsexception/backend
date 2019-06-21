package pl.gov.hackathon.teamoutofboundsexception.server.placesGraph;

import pl.gov.hackathon.teamoutofboundsexception.server.model.PlaceModel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private ArrayList<Vertex> vertices;
    private ArrayList<Trip> trips;
    private LocalTime availableTime;

    // TODO set default time
    public Graph(){
        this.vertices = new ArrayList<>();
        this.trips = new ArrayList<>();
    }

    public Graph(LocalTime availableTime){
        this();
        this.availableTime = availableTime;
    }

    public void initGraph(List<PlaceModel> placesList, LocalTime availableTime) {
        this.availableTime = availableTime;

            placesList.forEach(place -> {
                Vertex vertex = new Vertex(place);

                for(Vertex v : this.vertices){
                    vertex.setEdgeWith(v);
                }

                this.vertices.add(vertex);
            });
    }

    public void computeTrips(float userMapX, float userMapY){
        for(Vertex v : this.vertices){
            LocalTime timeToArrive = Converter.coordinatesToTime(userMapX, userMapY, v.getMapX(), v.getMapY());
            LocalTime totalTime = Converter.addTime(v.getAvgTimeSpent(), timeToArrive);
            if(totalTime.compareTo(this.availableTime) <= 0){
                Trip trip = new Trip();
                trip.add(v);
                this.trips.add(trip);
                ArrayList<Vertex> leftV = (ArrayList<Vertex>) this.vertices.clone();
                leftV.remove(v);
                recursiveComputeTrips(v, (Trip)trip.clone(), totalTime, leftV);
            }
        }
    }

    private void recursiveComputeTrips(Vertex vertex, Trip trip, LocalTime totalTime, ArrayList<Vertex> leftV){
        for(Edge e : vertex.getEdges()){
            Vertex v = e.getOtherV(vertex);

            boolean valid = false;
            for(Vertex vC : leftV){
                if(v.getPlaceId() == vC.getPlaceId())
                    valid = true;
            }
            if(!valid)
                return;

            LocalTime timeToArrive = Converter.coordinatesToTime(vertex.getMapX(), vertex.getMapY(), v.getMapX(), v.getMapY());
            LocalTime newTotalTime = Converter.addTime(totalTime, timeToArrive);
            newTotalTime = Converter.addTime(newTotalTime, v.getAvgTimeSpent());
            if(newTotalTime.compareTo(this.availableTime) <= 0){
                Trip newTrip = (Trip)trip.clone();
                newTrip.add(v);
                this.trips.add(newTrip);
                ArrayList<Vertex> newLeftV = (ArrayList<Vertex>) leftV.clone();
                newLeftV.remove(v);
                recursiveComputeTrips(v, newTrip, newTotalTime, newLeftV);
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
        System.out.println("Time ava: " + this.availableTime.toString());
        for(Trip trip : trips){
            for(Vertex v : trip){
                System.out.print("(" + v.getPlaceId() + "{ avgT:" + v.getAvgTimeSpent()  + "}" + ")");
                System.out.print(" -> ");
            }
            System.out.println();
        }
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }
}
