package Components;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

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
	
	private final File[] audioFiles;
	private final Clip[] clips;
	private int currentSoundLoop;

	/**
	 * initializes all audio files of the application
	 */
	public AudioManager(){
		int audioNumber = 30;
		audioFiles = new File[audioNumber];
		clips = new Clip[audioNumber];
		currentSoundLoop = 0;

		//music
		audioFiles[NORMAL_MUSIC_INDEX] = new File("src/resources/audio/dungeonOstVer2.wav");
		audioFiles[MAIN_MENU_MUSIC_INDEX] = new File(getClass().getClassLoader().getResource("audio/mainMenuOst.wav").toString());
		audioFiles[GAME_WIN_MUSIC_INDEX] = new File("src/resources/audio/winMusic.wav");
		audioFiles[GAME_OVER_MUSIC_INDEX] = new File("src/resources/audio/gameOver.wav");
		
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
		audioFiles[BAT_SOUND_1_INDEX] = new File("src/resources/audio/batDash1.wav");
		audioFiles[BAT_SOUND_2_INDEX] = new File("src/resources/audio/batDash2.wav");
		//broken rock
		audioFiles[ROCK_BROKEN_INDEX] = new File("src/resources/audio/brokenRock.wav");
		//healing sound
		audioFiles[PLAYER_HEALED] = new File("src/resources/audio/heal.wav");
		//mage sound
		audioFiles[MAGE_DEATH_SOUND_INDEX] = new File("src/resources/audio/mageSound.wav");
		//teleport
		audioFiles[TELEPORT_SOUND_INDEX] = new File("src/resources/audio/teleport.wav");
		//powerup
		audioFiles[POWERUP_PICKED_INDEX] = new File("src/resources/audio/powerUp.wav");
		//rock throw
		audioFiles[ROCK_THROW_1_INDEX] = new File("src/resources/audio/rockThrow1.wav");
		audioFiles[ROCK_THROW_2_INDEX] = new File("src/resources/audio/rockThrow2.wav");
		//tank sounds
		audioFiles[TANK_SOUND_INDEX] = new File("src/resources/audio/TankSound.wav");
		audioFiles[TANK_DEATH_INDEX] = new File("src/resources/audio/TankDeath.wav");
		//zombie sounds
		audioFiles[ZOMBIE_SOUND_1_INDEX] = new File("src/resources/audio/ZombieSound1.wav");
		audioFiles[ZOMBIE_SOUND_2_INDEX] = new File("src/resources/audio/ZombieSound2.wav");
		audioFiles[ZOMBIE_DEATH_INDEX] = new File("src/resources/audio/ZombieDeath.wav");
		//slime sounds
		audioFiles[SLIME_SOUND_1_INDEX] = new File("src/resources/audio/SlimeSound.wav");
		audioFiles[SLIME_SOUND_2_INDEX] = new File("src/resources/audio/slimeSound2.wav");
	}
	
	private void setClip(int i){
		try {
			clips[i] = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(audioFiles[i]);
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
