package GameStates;

import Entities.Background;

import java.io.NotActiveException;

public class MainMenu extends GameState {
	
	public MainMenu() {
		setActive();
		background.loadMainMenuBackground();
	}
	
	@Override
	public void processInput(){
		if(keyH.enterPressed){
			setInactive();
		}
	}
	
	@Override
	public void update() {
		processInput();
		//TODO paint the background
	}
}
