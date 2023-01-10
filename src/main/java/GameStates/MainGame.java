package GameStates;

import Entities.EntityControl;

public class MainGame extends GameState{
	
	// classe che serve come transito di comunicazione per il programma con le entità
	EntityControl entityControl;
	
	public MainGame(){
		setActive();
		//TODO load main game background
		entityControl = new EntityControl();
	}
	
	@Override
	public void processInput() {
	
	}
	
	@Override
	public void update() {
	
	}
}
