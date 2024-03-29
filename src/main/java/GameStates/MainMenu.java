package GameStates;

import Application.KeyHandler;
import Components.AudioManager;

/**
 * Type of GameState, defines the main menu of the game.
 * Allows the user to start the game or see the commands.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class MainMenu extends GameState {
	public boolean showCommands;
	public int inputCountdown;
	public AudioManager audioManager;

	/*
	il costruttore carica l'immagine, dopo aver settato 'active' lo stato
	 */

	/**
	 * Loads the main menu screen.
	 * @param keyH used to read inputs
	 * @param audioManager needed to play music and audio effects
	 */
	public MainMenu(KeyHandler keyH, AudioManager audioManager) {
		this.keyH = keyH;
		this.audioManager = audioManager;
		showCommands = false;
		inputCountdown = 6;
		setActive();
		background.loadMainMenuBackground();
		audioManager.playSoundLoop(AudioManager.MAIN_MENU_MUSIC_INDEX);
	}

	@Override
	public void processInput(){
		inputCountdown --;
		if(keyH.enterPressed && !showCommands){
			setInactive();
			audioManager.stopSoundLoop();
		}
		if(inputCountdown <= 0) {
			if (keyH.cPressed || showCommands) {
				inputCountdown = 6;
				showCommands = true;
				background.loadBlackBackground();
				if (keyH.escapePressed) {
					inputCountdown = 6;
					background.loadMainMenuBackground();
					showCommands = false;
				}
			} else if (keyH.escapePressed) {
				System.exit(0);
			}
		}
	}
	
	@Override
	public void update() {
		processInput();
	}
}
