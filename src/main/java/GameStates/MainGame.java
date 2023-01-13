package GameStates;

import Application.KeyHandler;
import Entities.EntityControl;

import java.awt.*;

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
	public void render(Graphics g) {
		super.render(g);
		entityControl.renderAllEntities(g);
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

		if(keyH.shootUp){
			entityControl.addArrow(false, false);
		}
		else if(keyH.shootDown){
			entityControl.addArrow(false, true);
		}
		else if(keyH.shootLeft){
			entityControl.addArrow(true, false);
		}
		else if(keyH.shootRight){
			entityControl.addArrow(true, true);
		}
	}
	
	@Override
	public void update() {
		processInput();
		entityControl.moveEnemies();
		entityControl.updateArrows();
		entityControl.checkCollisionsAE();
		entityControl.checkCollisionsPE();
		if(entityControl.isGameOver()){
			setInactive();
		}
	}
}
