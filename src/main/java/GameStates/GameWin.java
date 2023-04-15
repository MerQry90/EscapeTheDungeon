package GameStates;

import Application.KeyHandler;
import Components.AudioManager;

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
