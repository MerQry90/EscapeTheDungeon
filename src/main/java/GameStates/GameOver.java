package GameStates;

import Application.KeyHandler;
import Components.AudioManager;

public class GameOver extends GameState{
	
	private int nextGameStateCountdown;
	
	public GameOver(KeyHandler keyH, AudioManager audioManager) {
		this.keyH = keyH;
		background.loadGameOverBackground();
		setActive();
		audioManager.playSoundOnce(AudioManager.GAME_OVER_MUSIC_INDEX);
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
