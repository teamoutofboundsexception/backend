package pl.gov.hackathon.teamoutofboundsexception.server.placesGraph;

import java.time.LocalTime;

public class Converter {
    public static LocalTime coordinatesToTime(float x1, float y1, float x2, float y2) {

        final float avgSpeed = 6;
        final int R = 6371;

        float lat1 = y1;
        float lat2 = y2;
        float lon1 = x1;
        float lon2 = x2;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = R * c;

        float floatTime = (float) (dist / avgSpeed);

        int hour = (int) floatTime;
        int minute = (int) ((floatTime - hour) * 60);

        if (hour > 23) {
            hour = 23;
            minute = 59;
        }

        return LocalTime.of(hour, minute);
    }

    public static LocalTime addTime(LocalTime t1, LocalTime t2) {
        int hour = t1.getHour() + t2.getHour();
        int min = t1.getMinute() + t2.getMinute();

        while(min > 59){
            min -= 60;
            hour += 1;
        }
        while(hour > 23) {
            hour -= 24;
        }
        return LocalTime.of(hour, min);
    }
}
