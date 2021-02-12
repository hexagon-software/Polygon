package de.hexagonsoftware.pg.audio;

import org.apache.logging.log4j.LogManager;

public class AudioEngineShutdownThread extends Thread {
	public AudioEngineShutdownThread() {
		super("AE Shutdown");
	}
	
	@Override
	public void run() {
		LogManager.getLogger(this).info("Shutting down the AudioEngine...");
		AudioEngine.getInstance().killALData();
		LogManager.getLogger(this).info("AudioEngine shutdown!");
	}
}
