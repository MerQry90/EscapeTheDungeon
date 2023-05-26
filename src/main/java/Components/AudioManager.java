package Components;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Manages the whole sound compartment of the game,
 * allowing other classes to play music and sound effects.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class AudioManager {
	
	public static final int NORMAL_MUSIC_INDEX = 0;
	public static final int PLAYER_HURTED_INDEX = 1;
	public static final int BOSS_MUSIC_INDEX = 2;
	public static final int ARROW_SHOOTING_INDEX = 3;
	public static final int KEY_PICKEDUP_INDEX = 4;
	public static final int STANDARD_ENEMY_HURTED_INDEX = 5;
	public static final int DOORS_OPENING_INDEX = 6;
	public static final int BAT_SOUND_1_INDEX = 7;
	public static final int ROCK_BROKEN_INDEX = 8;
	public static final int PLAYER_HEALED = 9;
	public static final int MAGE_DEATH_SOUND_INDEX = 10;
	public static final int SLIME_SOUND_1_INDEX = 11;
	public static final int TELEPORT_SOUND_INDEX = 13;
	public static final int BAT_SOUND_2_INDEX = 14;
	public static final int POWERUP_PICKED_INDEX = 15;
	public static final int ROCK_THROW_1_INDEX = 16;
	public static final int ROCK_THROW_2_INDEX = 17;
	public static final int TANK_SOUND_INDEX = 12;
	public static final int TANK_DEATH_INDEX = 18;
	public static final int ZOMBIE_SOUND_1_INDEX = 19;
	public static final int ZOMBIE_SOUND_2_INDEX = 20;
	public static final int ZOMBIE_DEATH_INDEX = 21;
	public static final	int MAIN_MENU_MUSIC_INDEX = 22;
	public static final int GAME_WIN_MUSIC_INDEX = 23;
	public static final int GAME_OVER_MUSIC_INDEX = 24;
	public static final int SLIME_SOUND_2_INDEX = 25;
	
	private final URL[] audioFiles;
	private final Clip[] clips;
	private int currentSoundLoop;

	/**
	 * initializes all audio files of the application
	 */
	public AudioManager(){
		int audioNumber = 26;
		audioFiles = new URL[audioNumber];
		clips = new Clip[audioNumber];
		currentSoundLoop = 0;
		
		try {
			//music
			audioFiles[MAIN_MENU_MUSIC_INDEX] = getClass().getClassLoader().getResource("audio/mainMenuOst.wav");
			audioFiles[NORMAL_MUSIC_INDEX] = getClass().getClassLoader().getResource("audio/dungeonOstVer2.wav");
			audioFiles[GAME_WIN_MUSIC_INDEX] = getClass().getClassLoader().getResource("audio/winMusic.wav");
			audioFiles[GAME_OVER_MUSIC_INDEX] = getClass().getClassLoader().getResource("audio/gameOver.wav");
			//player gets hit
			audioFiles[PLAYER_HURTED_INDEX] = getClass().getClassLoader().getResource("audio/playerGotHit.wav");
			//boss music
			audioFiles[BOSS_MUSIC_INDEX] = getClass().getClassLoader().getResource("audio/bossOst.wav");
			//arrow shot
			audioFiles[ARROW_SHOOTING_INDEX] = getClass().getClassLoader().getResource("audio/shoot.wav");
			//key picked up
			audioFiles[KEY_PICKEDUP_INDEX] = getClass().getClassLoader().getResource("audio/keyPickup.wav");
			//enemy hit with an arrow
			audioFiles[STANDARD_ENEMY_HURTED_INDEX] = getClass().getClassLoader().getResource("audio/enemyHit.wav");
			//doors opening
			audioFiles[DOORS_OPENING_INDEX] = getClass().getClassLoader().getResource("audio/doorOpening.wav");
			//bat sounds
			audioFiles[BAT_SOUND_1_INDEX] = getClass().getClassLoader().getResource("audio/batDash1.wav");
			audioFiles[BAT_SOUND_2_INDEX] = getClass().getClassLoader().getResource("audio/batDash2.wav");
			//broken rock
			audioFiles[ROCK_BROKEN_INDEX] = getClass().getClassLoader().getResource("audio/brokenRock.wav");
			//healing sound
			audioFiles[PLAYER_HEALED] = getClass().getClassLoader().getResource("audio/heal.wav");
			//mage sound
			audioFiles[MAGE_DEATH_SOUND_INDEX] = getClass().getClassLoader().getResource("audio/mageSound.wav");
			//teleport
			audioFiles[TELEPORT_SOUND_INDEX] = getClass().getClassLoader().getResource("audio/teleport.wav");
			//powerup
			audioFiles[POWERUP_PICKED_INDEX] = getClass().getClassLoader().getResource("audio/powerUp.wav");
			//rock throw
			audioFiles[ROCK_THROW_1_INDEX] = getClass().getClassLoader().getResource("audio/rockThrow1.wav");
			audioFiles[ROCK_THROW_2_INDEX] =getClass().getClassLoader().getResource("audio/rockThrow2.wav");
			//tank sounds
			audioFiles[TANK_SOUND_INDEX] = getClass().getClassLoader().getResource("audio/TankSound.wav");
			audioFiles[TANK_DEATH_INDEX] = getClass().getClassLoader().getResource("audio/TankDeath.wav");
			//zombie sounds
			audioFiles[ZOMBIE_SOUND_1_INDEX] = getClass().getClassLoader().getResource("audio/ZombieSound1.wav");
			audioFiles[ZOMBIE_SOUND_2_INDEX] = getClass().getClassLoader().getResource("audio/ZombieSound2.wav");
			audioFiles[ZOMBIE_DEATH_INDEX] = getClass().getClassLoader().getResource("audio/ZombieDeath.wav");
			//slime sounds
			audioFiles[SLIME_SOUND_1_INDEX] = getClass().getClassLoader().getResource("audio/SlimeSound.wav");
			audioFiles[SLIME_SOUND_2_INDEX] = getClass().getClassLoader().getResource("audio/slimeSound2.wav");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void setClip(int i){
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(audioFiles[i]);
			clips[i] = AudioSystem.getClip();
			clips[i].open(ais);
		}
		catch (Throwable e){
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
