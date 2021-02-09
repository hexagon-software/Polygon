package de.hexagonsoftware.pg.audio;

public class AudioEngineShutdownThread extends Thread {
	public AudioEngineShutdownThread() {
		super("AE Shutdown");
	}
	
	@Override
	public void run() {
		AudioEngine.getInstance().killALData();
	}
}
