package br.com.thelastsurvivor.engine.audio;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import br.com.thelastsurvivor.R;

public class AudioGame {
	
	static private AudioGame _instance;
	
	private static SoundPool soundPool; 
	private static HashMap<Integer, Integer> sounds; 
	private static AudioManager  audioManager;
	private static Context context;
	
	private AudioGame(){}
	
	static synchronized public AudioGame getInstance() {
	    if (_instance == null) 
	      _instance = new AudioGame();
	    return _instance;
	 }
	
	
	public static  void initSounds(Context c){ 
		context = c;
	    soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
	    sounds = new HashMap<Integer, Integer>(); 
	    audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE); 	    
	} 

	
	public static void addSound(int index,int soundID){
		sounds.put(index, soundPool.load(context, soundID, 1));
	}
	
	public static void loadSounds(){         	
		sounds.put(3, soundPool.load(context, R.raw.explosion, 1));	
		sounds.put(2, soundPool.load(context, R.raw.laser_single, 1));	
		sounds.put(4, soundPool.load(context, R.raw.bonus, 1));
		sounds.put(5, soundPool.load(context, R.raw.trophy, 1));
	}
	
	public static int playSound(int index, int loop, float speed){ 		
	     float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); 
	     streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	     return soundPool.play(sounds.get(index), streamVolume, 2, 1, 0, speed); 
	}

	
	public static void stopSound(int index){
		soundPool.stop(sounds.get(index));
	}
	
	public static void cleanup(){
		soundPool.release();
		soundPool = null;
	    sounds.clear();
	    audioManager.unloadSoundEffects();
	    _instance = null;
	    
	}
	
}
	
