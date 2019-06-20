package pl.gov.hackathon.teamoutofboundsexception.server.placesGraph;

public class CoordinatesToTimeConverter {
    public static float get(float x1, float y1, float x2, float y2){
        //TODO!!!!!
        return (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }
}
