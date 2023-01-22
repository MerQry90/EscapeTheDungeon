package GameStates;

import Application.KeyHandler;
import Components.EntityManager;
import Components.Stage;

import java.awt.*;


public class MainGame extends GameState{

	private Stage stage;
	
	//private CollisionBox boundaries;
	
	private int clearedTotalStages;
	
	private EntityManager entityManager;
	
	public MainGame(KeyHandler keyH){
		this.keyH = keyH;
		setActive();
		background.loadMainGameBackground();
		
		entityManager = new EntityManager();
		
		stage = new Stage(entityManager);
		//stage.loadRandomStage(enemies);
		
		clearedTotalStages = 0;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		entityManager.renderEntities(g);
	}
	
	@Override
	public void processInput() {

		//movimento
		if(keyH.upPressed && keyH.rightPressed){
			entityManager.getPlayer().calculateTranslations(1, -1);
		}
		else if(keyH.upPressed && keyH.leftPressed){
			entityManager.getPlayer().calculateTranslations(-1, -1);
		}
		else if(keyH.downPressed && keyH.rightPressed){
			entityManager.getPlayer().calculateTranslations(1, 1);
		}
		else if(keyH.downPressed && keyH.leftPressed) {
			entityManager.getPlayer().calculateTranslations(-1, 1);
		}
		else if(keyH.upPressed){
			entityManager.getPlayer().calculateTranslations(0, -1);
		}
		else if(keyH.downPressed){
			entityManager.getPlayer().calculateTranslations(0, 1);
		}
		else if(keyH.leftPressed){
			entityManager.getPlayer().calculateTranslations(-1, 0);
		}
		else if(keyH.rightPressed) {
			entityManager.getPlayer().calculateTranslations(1, 0);
		}
		
		//shooting
		/*if (keyH.shootUp && player.canShoot()) {
			arrows.add(new Arrow(player.getX(), player.getY(), false, false));
		}
		else if (keyH.shootDown && player.canShoot()) {
			arrows.add(new Arrow(player.getX(), player.getY(), false, true));
		}
		else if (keyH.shootLeft && player.canShoot()) {
			arrows.add(new Arrow(player.getX(), player.getY(), true, false));
		}
		else if (keyH.shootRight && player.canShoot()) {
			arrows.add(new Arrow(player.getX(), player.getY(), true, true));
		}

		//DEBUG ONLY
		if(keyH.killAll){
			for(Enemy enemy: enemies){
				enemy.setInactive();
			}
		}*/
		
	}
	
	@Override
	public void update() {
		if (clearedTotalStages >= 10){
			setInactive();
		}
		processInput();
		entityManager.getPlayer().updateCoolDown();
		entityManager.updateArrows();
		entityManager.updateEnemies();
		entityManager.checkDynamicCollisions();
		/*if(isGameOver()){
			setInactive();
		}
		else if(checkStageCompletion()){
			door.setActive();
		}*/
	}
}
