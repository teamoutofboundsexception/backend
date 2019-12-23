package pl.gov.hackathon.teamoutofboundsexception.server.integration.parser;

import java.util.HashMap;

public class AtributeMaping {
    private HashMap<String, Integer> hashmap;

    public AtributeMaping() {
        hashmap = new HashMap<>();
    }

    public Integer put(String K) {
        if (!hashmap.containsKey(K)){
            hashmap.put(K, hashmap.size());
        }
        return hashmap.get(K);
    }

    public Integer get(String K) {
        return hashmap.get(K);
    }

    public boolean containsKey(String K) {
        return hashmap.containsKey(K);
    }
}
