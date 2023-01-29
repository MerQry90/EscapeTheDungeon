package GameStates;

import Application.KeyHandler;
import Components.EntityManager;
import Components.CellManager;
import Components.Stage;
import Components.UI;

import java.awt.*;


public class MainGame extends GameState{

	private Stage stage;
	private boolean pause;
	private int pauseCountdown;
	private UI ui;
	
	private int clearedTotalStages;
	
	private EntityManager entityManager;
	
	public MainGame(KeyHandler keyH){
		this.keyH = keyH;
		setActive();
		background.loadMainGameBackground();
		
		entityManager = new EntityManager();
		
		stage = new Stage(entityManager);
		
		clearedTotalStages = 0;
		pause = false;
		
		CellManager rm = new CellManager();
		ui = new UI();
	}
	
	@Override
	public void processInput() {
		if(keyH.escapePressed && pauseCountdown <= 0){
			pauseCountdown = 10;
			pause = !pause;
		}
		//movimento
		if(keyH.upPressed && keyH.rightPressed){
			entityManager.setNextPlayerInstruction("up-right");
		}
		else if(keyH.upPressed && keyH.leftPressed){
			entityManager.setNextPlayerInstruction("up-left");
		}
		else if(keyH.downPressed && keyH.rightPressed){
			entityManager.setNextPlayerInstruction("down-right");
		}
		else if(keyH.downPressed && keyH.leftPressed) {
			entityManager.setNextPlayerInstruction("down-left");
		}
		else if(keyH.upPressed){
			entityManager.setNextPlayerInstruction("up");
		}
		else if(keyH.downPressed){
			entityManager.setNextPlayerInstruction("down");
		}
		else if(keyH.leftPressed){
			entityManager.setNextPlayerInstruction("left");
		}
		else if(keyH.rightPressed) {
			entityManager.setNextPlayerInstruction("right");
		}
		else {
			entityManager.setNextPlayerInstruction("stop");
		}
		
		//shooting
		if (keyH.shootUp && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow("up");
		}
		else if (keyH.shootDown && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow("down");
		}
		else if (keyH.shootLeft && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow("left");
		}
		else if (keyH.shootRight && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow("right");
		}

		//DEBUG ONLY
		if(keyH.killAll){
			entityManager.killAll();
		}
		
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		entityManager.renderEntities(g);
		ui.drawUI(g, entityManager.getPlayer());
	}
	
	@Override
	public void update() {
		pauseCountdown--;
		processInput();
		if(!pause) {
			if (clearedTotalStages >= 10) {
				setInactive();
			}
			if(entityManager.isGameOver()){
				setInactive();
			}
			entityManager.getPlayer().move();
			entityManager.getPlayer().updateCoolDown();
			entityManager.updateArrows();
			entityManager.updateEnemies();
			entityManager.checkDynamicCollisions();
			entityManager.checkStageCompletion();
		}
	}
}
