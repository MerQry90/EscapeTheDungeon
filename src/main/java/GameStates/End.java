package GameStates;

import Application.KeyHandler;

public class End extends GameState{
	
	public End(KeyHandler keyH) {
		this.keyH = keyH;
		background.loadEndBackground();
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
