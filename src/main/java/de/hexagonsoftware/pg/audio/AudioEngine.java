package de.hexagonsoftware.pg.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

import de.hexagonsoftware.pg.Polygon;

/**
 * Hexagon AudioEngine.
 * The Hexagon AudioEngine is part of Polygon and
 * lies under the same license.
 * 
 * Thanks to Xerxes Rånby for helping in the creation of
 * this Audio Engine.
 * 
 * @author Felix Eckert
 * */

//TODO: Integrate AudioEngine into rest of the engine

public class AudioEngine {
	/**
	 * Instance of the Audio Engine
	 * */
	private static AudioEngine AE;
	/**
	 * OpenAL instance
	 * */
	private AL AE_AL;
	
	/**
	 * Map of loaded sounds
	 * */
	private Map<String, Sound> AE_SOUNDS;
	/**
	 * List of created sources
	 * */
	private ArrayList<Source> AE_SOURCES;
	
	/**
	 * The Listener
	 * */
	public Listener AE_LISTENER;
	
	/**
	 * Logger
	 * */
	private Logger AE_LOGGER = LogManager.getLogger("AudioEngine");
	
	private AudioEngine() {
		this.AE_AL 		 = ALFactory.getAL();
		this.AE_SOUNDS   = new HashMap<>();
		this.AE_SOURCES  = new ArrayList<>();
		
		AE_LOGGER.info("Initialising AudioEngine...");
		Runtime.getRuntime().addShutdownHook(new AudioEngineShutdownThread());
		ALut.alutInit();
		AE_AL.alGetError();
		
		AE_LOGGER.info("AudioEngine Initialised!");
	}
	
	public static AudioEngine getInstance() {
		if (AE == null) AE = new AudioEngine();
		
		return AE;
	}
	
	//////////////////////////////////////////////////
	
	/**
	 * Loads a (WAV) Sound
	 * 
	 * @param name The name for it to be registered under
	 * @param file The file of the sound
	 * @throws AudioEngineException 
	 * */
	public void loadSound(String name, String file) {
		Sound s = new Sound(1);
		
		AE_AL.alGenBuffers(1, s.buffer, 0);
		if (AE_AL.alGetError() != AL.AL_NO_ERROR) {
			AE_LOGGER.error(String.format("An error occured whilst loading sound: NAME \"%s\"; FILE \"%t\"", name, file));
			return;
		}
		
		ALut.alutLoadWAVFile(Polygon.PG_CLASS_LOADING.getResourceAsStream(file), s.format, s.data, s.size, s.freq, s.loop);
		AE_AL.alBufferData(s.buffer[0], s.format[0], s.data[0], s.size[0], s.freq[0]);
		
		AE_SOUNDS.put(name, s);
	}
	
	/**
	 * Creates a Source
	 * @param sound Name of the sound
	 * @param pos Position of the source
	 * @param vel Velocity of the source
	 * @param gain The Gain of the source
	 * @param pitch The Pitch of the source
	 * @return The ID of the source
	 * */
	public int createSource(String sound, float[] pos, float[] vel, float gain, float pitch) {
		// Gen Sources
		Sound s = AE_SOUNDS.get(sound);
		Source src = new Source(pos, vel);
		AE_AL.alGenSources(1, src.source, 0);
		
		int err = AE_AL.alGetError();
		if (err != AL.AL_NO_ERROR) {
			AE_LOGGER.error(String.format("An error occured whilst creating sound source: NAME \"%s\" ; %s (alGenSources)", sound, getErrorName(err)));
			return -1;
		}
		
		// Set Source Parameters
		
		AE_AL.alSourcei (src.source[0], AL.AL_BUFFER,   s.buffer[0]    );
		AE_AL.alSourcef (src.source[0], AL.AL_PITCH,    pitch     	   );
		AE_AL.alSourcef (src.source[0], AL.AL_GAIN,     gain 		   );
		AE_AL.alSourcefv(src.source[0], AL.AL_POSITION, src.getPos(), 0);
		AE_AL.alSourcefv(src.source[0], AL.AL_VELOCITY, src.getVel(), 0);
		AE_AL.alSourcei (src.source[0], AL.AL_LOOPING,  s.loop[0]      );
		
		err = AE_AL.alGetError();
		if (err != AL.AL_NO_ERROR) {
			AE_LOGGER.error(String.format("An error occured whilst creating sound source: NAME \"%s\" ; %s (setting source parameters)", sound, getErrorName(err)));
			return -1;
		}
		
		AE_SOURCES.add(src);
		return src.source[0];
	}
	
	/**
	 * Plays a source
	 * @param source The ID of the source to be played
	 * */
	public void playSource(int source) {
		if (source == -1) return;
		AE_AL.alSourcePlay(source);
	}
	
	/**
	 * Creates a new Listener object for the Engine
	 * 
	 * @param pos The Position of the listener
	 * @param ori The Orientation of the listener
	 * @param vel The Velocity of the listener
	 * */
	public void createListener(float[] pos, float[] ori, float[] vel) {
		if (AE_LISTENER != null) { 
			AE_LOGGER.warn("A listener object already exists!");
			AE_LISTENER = null; 
		}
		
		AE_LISTENER = new Listener(pos, ori, vel);

        AE_AL.alListenerfv(AL.AL_POSITION,    AE_LISTENER.getPos(), 0);
        AE_AL.alListenerfv(AL.AL_VELOCITY,    AE_LISTENER.getOri(), 0);
        AE_AL.alListenerfv(AL.AL_ORIENTATION, AE_LISTENER.getVel(), 0);
	}
	
	/**
	 * Updates the position, velocity and orientation of the listener
	 * */
	public void updateListener() {
		AE_AL.alListenerfv(AL.AL_POSITION,    AE_LISTENER.getPos(), 0);
        AE_AL.alListenerfv(AL.AL_VELOCITY,    AE_LISTENER.getOri(), 0);
        AE_AL.alListenerfv(AL.AL_ORIENTATION, AE_LISTENER.getVel(), 0);
	}
	
	/**
	 * Kills all AL data
	 * */
	public void killALData() {
		AE_LOGGER.info("Shutting down AudioEngine...");
		AE_SOUNDS.forEach((k, v) -> AE_AL.alDeleteBuffers(AE_SOUNDS.keySet().size(), v.buffer, 0));
		AE_SOURCES.forEach((v) -> AE_AL.alDeleteSources(AE_SOURCES.size(), v.source, 0));
		ALut.alutExit();
	}
	
	/**
	 * Returns the appropriate Error Name for the
	 * given error code.
	 * 
	 * @param err The error code
	 * */
	public static String getErrorName(int err) {
		switch (err) {
		  case AL.AL_NO_ERROR:      	 return "AL_NO_ERROR";
		  case AL.AL_INVALID_NAME:  	 return "AL_INVALID_NAME";
		  case AL.AL_INVALID_ENUM: 		 return "AL_INVALID_ENUM";
		  case AL.AL_INVALID_VALUE: 	 return "AL_INVALID_VALUE";
		  case AL.AL_OUT_OF_MEMORY: 	 return "AL_OUT_OF_MEMORY";
		  case AL.AL_INVALID_OPERATION : return "AL_INVALID_OPERATION";
		  default:
		    return "Unknown error code "+err;
		}
	}
}
