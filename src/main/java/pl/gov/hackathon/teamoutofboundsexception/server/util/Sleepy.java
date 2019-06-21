package pl.gov.hackathon.teamoutofboundsexception.server.util;

public class Sleepy {

    public static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
