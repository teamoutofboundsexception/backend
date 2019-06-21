package pl.gov.hackathon.teamoutofboundsexception.server.synchronization.image;

import java.util.HashMap;

class ImageSearchResults {
    HashMap<String, String> relevantHeaders;
    String jsonResponse;
    ImageSearchResults(HashMap<String, String> headers, String json) {
        relevantHeaders = headers;
        jsonResponse = json;
    }
}