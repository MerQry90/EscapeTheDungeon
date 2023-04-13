package GameStates;

import Application.KeyHandler;
import Components.AudioManager;

public class GameOver extends GameState{
	
	public GameOver(KeyHandler keyH, AudioManager audioManager) {
		this.keyH = keyH;
		this.audioManager = audioManager;
		background.loadGameOverBackground();
		setActive();
		audioManager.playSoundOnce(AudioManager.GAME_OVER_MUSIC_INDEX);
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
	}
}
