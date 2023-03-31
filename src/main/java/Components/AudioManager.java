package Components;

import javax.sound.sampled.*;
import java.io.*;

public class AudioManager {
	
	public static final int NORMAL_MUSIC_INDEX = 0;
	public static final int PLAYER_HURTED_INDEX = 1;
	public static final int BOSS_MUSIC_INDEX = 2;
	public static final int ARROW_SHOOTING_INDEX = 3;
	public static final int KEY_PICKEDUP_INDEX = 4;
	public static final int STANDARD_ENEMY_HURTED_INDEX = 5;
	public static final int DOORS_OPENING_INDEX = 6;
	public static final int BAT_SOUND_INDEX = 7;
	public static final int ROCK_BROKEN_INDEX = 8;
	public static final int PLAYER_HEALED = 9;
	public static final int MAGE_SOUND_INDEX = 10;
	public static final int SLIME_SOUND_INDEX = 11;
	public static final int TANK_SOUND_INDEX = 12;
	
	
	private File[] audioFiles;
	private Clip[] clips;
	private int currentSoundLoop;
	
	public AudioManager(){
		audioFiles = new File[20];
		clips = new Clip[20];
		currentSoundLoop = 0;
		
		//dungeon music
		audioFiles[NORMAL_MUSIC_INDEX] = new File("src/resources/audio/dungeonOstVer2.wav");
		//player gets hit
		audioFiles[PLAYER_HURTED_INDEX] = new File("src/resources/audio/playerGotHit.wav");
		//boss music
		audioFiles[BOSS_MUSIC_INDEX] = new File("src/resources/audio/bossOst.wav");
		//arrow shot
		audioFiles[ARROW_SHOOTING_INDEX] = new File("src/resources/audio/shoot.wav");
		//key picked up
		audioFiles[KEY_PICKEDUP_INDEX] = new File("src/resources/audio/keyPickup.wav");
		//enemy hit with an arrow
		audioFiles[STANDARD_ENEMY_HURTED_INDEX] = new File("src/resources/audio/enemyHit.wav");
		//doors opening
		audioFiles[DOORS_OPENING_INDEX] = new File("src/resources/audio/doorOpening.wav");
		//bat sounds
		audioFiles[BAT_SOUND_INDEX] = new File("src/resources/audio/BatSound.wav");
		//broken rock
		audioFiles[ROCK_BROKEN_INDEX] = new File("src/resources/audio/brokenRock.wav");
		//healing sound
		audioFiles[PLAYER_HEALED] = new File("src/resources/audio/heal.wav");
		//mage sound
		audioFiles[MAGE_SOUND_INDEX] = new File("src/resources/audio/mageSound.wav");
		//slime sound
		audioFiles[SLIME_SOUND_INDEX] = new File("src/resources/audio/SlimeSound.wav");
		//tank sound
		audioFiles[TANK_SOUND_INDEX] = new File("src/resources/audio/tankSound.wav");
		
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
