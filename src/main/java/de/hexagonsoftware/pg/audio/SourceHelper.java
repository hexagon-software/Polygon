package de.hexagonsoftware.pg.audio;

import java.util.HashMap;

/**
 * This class contains a Map for saving
 * Sound-Source-IDs with a name. This
 * should only be used for sounds used
 * frequently.
 * */
public class SourceHelper {
	public HashMap<String, Integer> SOURCE_MAP;
	
	public SourceHelper() {
		SOURCE_MAP = new HashMap<>();
	}
	
	public void clear() {
		SOURCE_MAP.clear();
	}
}
