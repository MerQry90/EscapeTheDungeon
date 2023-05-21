package GameStates;

import Application.KeyHandler;
import Components.AudioManager;

/**
 * Type of GameState, it's activated when the Player defeats the final boss
 * and shows a victory screen.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class GameWin extends GameState{
	
	private int nextGameStateCountdown;
	
	public GameWin(KeyHandler keyH, AudioManager audioManager){
		this.keyH = keyH;
		background.loadGameWinBackground();
		setActive();
		audioManager.playSoundOnce(AudioManager.GAME_WIN_MUSIC_INDEX);
		nextGameStateCountdown = 300;
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
