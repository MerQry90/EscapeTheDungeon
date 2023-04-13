package GameStates;

import Application.KeyHandler;
import Components.AudioManager;

public class GameWin extends GameState{
	
	public AudioManager audioManager;
	
	public GameWin(KeyHandler keyH, AudioManager audioManager){
		this.keyH = keyH;
		this.audioManager = audioManager;
		background.loadGameWinBackground();
		setActive();
		audioManager.playSoundOnce(AudioManager.GAME_WIN_MUSIC_INDEX);
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
