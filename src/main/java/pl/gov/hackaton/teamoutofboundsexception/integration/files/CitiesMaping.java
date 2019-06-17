package pl.gov.hackaton.teamoutofboundsexception.integration.files;

import java.util.HashMap;

//Wraping for hashmap to provide unique citId
public class CitiesMaping {
	protected HashMap<String, Integer> hashmap;
	
	public CitiesMaping() {
		hashmap = new HashMap<String, Integer>();
	}
	public Integer put(String K) {
		if (hashmap.containsKey(K) ==false){
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
