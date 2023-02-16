package GameStates;

import Application.KeyHandler;

public class GameOver extends GameState{
	
	public GameOver(KeyHandler keyH) {
		this.keyH = keyH;
		background.loadGameOverBackground();
		setActive();
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
