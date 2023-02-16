package GameStates;

import Application.KeyHandler;

public class GameWin extends GameState{
	
	public GameWin(KeyHandler keyH){
		this.keyH = keyH;
		background.loadGameWinBackground();
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
