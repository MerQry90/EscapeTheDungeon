package GameStates;

import Application.KeyHandler;
import Components.AudioManager;

/**
 * Type of GameState, it's activated when the Player is killed
 * and shows a game over screen.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class GameOver extends GameState{
	
	private int nextGameStateCountdown;

	/**
	 * Loads the game over screen.
	 * @param keyH used to read input and return to the MainMenu GameState
	 * @param audioManager needed to play music and audio effects
	 */
	public GameOver(KeyHandler keyH, AudioManager audioManager) {
		this.keyH = keyH;
		background.loadGameOverBackground();
		setActive();
		audioManager.playSoundOnce(AudioManager.GAME_OVER_MUSIC_INDEX);
		nextGameStateCountdown = 280;
	}
	
	@Override
	public void processInput() {
		if(keyH.enterPressed){
			setInactive();
		}
	}
	
	@Override
	public void update() {
		processInput();
		if(nextGameStateCountdown > 0){
			nextGameStateCountdown -= 1;
		}
		else {
			setInactive();
		}
	}
}
