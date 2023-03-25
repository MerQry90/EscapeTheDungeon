package Components;

import javax.sound.sampled.*;
import java.io.*;

public class AudioManager {
	
	private File[] audioFiles;
	private Clip[] clips;
	private int currentSoundLoop;
	
	public AudioManager(){
		audioFiles = new File[20];
		clips = new Clip[20];
		currentSoundLoop = 0;
		
		//dungeon music
		audioFiles[0] = new File("src/resources/audio/dungeonOstVer2.wav");
		//player gets hit
		audioFiles[1] = new File("src/resources/audio/playerGotHit.wav");
		//boss music
		audioFiles[2] = new File("src/resources/audio/bossOst.wav");
		//arrow shot
		audioFiles[3] = new File("src/resources/audio/shoot.wav");
		//key picked up
		audioFiles[4] = new File("src/resources/audio/keyPickup.wav");
		//enemy hit with an arrow
		audioFiles[5] = new File("src/resources/audio/enemyHit.wav");
		//doors opening
		audioFiles[6] = new File("src/resources/audio/doorOpening.wav");
	}
	
	private void setClip(int i){
		try {
			clips[i] = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(audioFiles[i]);
			clips[i].open(ais);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void playSoundLoop(int i){
		currentSoundLoop = i;
		setClip(i);
		clips[i].start();
		clips[i].loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stopSoundLoop(){
		clips[currentSoundLoop].stop();
	}
	public void playSoundOnce(int i){
		setClip(i);
		clips[i].start();
	}
}
