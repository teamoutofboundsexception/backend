package pl.gov.hackathon.teamoutofboundsexception.server.integration.parser;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AtributeMaping {
    private HashMap<String, Integer> hashmap;

    public AtributeMaping() {
        hashmap = new HashMap<>();
    }

    Integer put(String K) {
        if (!hashmap.containsKey(K)){
            hashmap.put(K, hashmap.size());
        }
        return hashmap.get(K);
    }

    Integer get(String K) {
        return hashmap.get(K);
    }

    public boolean containsKey(String K) {
        return hashmap.containsKey(K);
    }

}
