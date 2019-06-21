package pl.gov.hackathon.teamoutofboundsexception.server.image;


import com.google.gson.*;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class ImageService {

    private final String key1="63fe996aec9e4346937b38bbe62cfff3";
    private final String key2="8a0504f73fcb4fb3a83d98c2780ce862";
    private final String host="https://api.cognitive.microsoft.com";
    private final String path="/bing/v7.0/images/search";
    private final String mkt = "&mkt=pl-PL";

    public String getImageURL(String queryValue){
        String queryTerm = queryValue;// + mkt; //z mkt na Polskę nie działa ...
        try{
            URL url = new URL(host + path + "?q=" +  URLEncoder.encode(queryTerm, "UTF-8"));
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", key1);

            //Query:
            InputStream stream = connection.getInputStream();
            String response = new Scanner(stream).useDelimiter("\\A").next();
            ImageSearchResults results = new ImageSearchResults(new HashMap<String, String>(), response);

            Map<String, List<String>> headers = connection.getHeaderFields();
            for (String header : headers.keySet()) {
                if (header == null) continue;      // may have null key
                if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
                    results.relevantHeaders.put(header, headers.get(header).get(0));
                }
            }

            stream.close();
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(results.jsonResponse).getAsJsonObject();
            //get the first image result from the JSON object, along with the total
            //number of images returned by the Bing Image Search API.
            String total = json.get("totalEstimatedMatches").getAsString();
            JsonArray jsonresults = json.getAsJsonArray("value");
            JsonObject first_result = (JsonObject)jsonresults.get(0);
            String resultURL = first_result.get("thumbnailUrl").getAsString();

            return resultURL;



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Check URL");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Check HttpsURLConnection");
            e.printStackTrace();

        }

        return "Some result";

    }


}