package Components;

import javax.sound.sampled.*;
import java.io.*;

public class AudioManager {
	
	private Clip clip;
	private File[] audioFiles;
	
	public AudioManager(){
		audioFiles = new File[30];
		audioFiles[0] = new File("src/resources/audio/BeepBox-Song2.wav");
		audioFiles[1] = new File("src/resources/audio/playerGotHit.wav");
	}
	
	public void setClip(int i){
		try {
			clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(audioFiles[i]);
			clip.open(ais);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public void play(){
		clip.start();
	}
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop(){
		clip.stop();
	}
}
