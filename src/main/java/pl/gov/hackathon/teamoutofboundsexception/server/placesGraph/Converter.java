package placesGraph;

import java.time.LocalTime;

public class Converter {
    public static LocalTime coordinatesToTime(float x1, float y1, float x2, float y2){
        //TODO!!!!!
        //return (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
        return LocalTime.of(1,0);
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
