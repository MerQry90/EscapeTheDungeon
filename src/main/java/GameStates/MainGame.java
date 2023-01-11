package GameStates;

import Application.KeyHandler;
import Entities.EntityControl;

public class MainGame extends GameState{
	
	// classe che serve come transito di comunicazione per il programma con le entità
	EntityControl entityControl;
	
	public MainGame(KeyHandler keyH){
		this.keyH = keyH;
		setActive();
		background.loadMainGameBackground();
		entityControl = new EntityControl();
	}
	
	@Override
	public void processInput() {
		if(keyH.upPressed){
			entityControl.movePlayerUp();
			//System.out.println("Player su");
		}
		else if(keyH.downPressed){
			entityControl.movePlayerDown();
		}
		else if(keyH.leftPressed){
			entityControl.movePlayerLeft();
		}
		else if(keyH.rightPressed) {
			entityControl.movePlayerRight();
		}
	}
	
	@Override
	public void update() {
		processInput();
	}
}
