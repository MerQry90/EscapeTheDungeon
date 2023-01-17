package GameStates;

import Application.KeyHandler;
import Entities.*;
import Entities.Enemies.Enemy;
import Stages.Stage;

import java.util.ArrayList;
import java.awt.*;


public class MainGame extends GameState{

	private Stage stage;
	private Player player;
	private Door door;
	private java.util.List<Enemy> enemies;
	private java.util.List<Arrow> arrows;
	private int clearedTotalStages;
	
	public MainGame(KeyHandler keyH){
		this.keyH = keyH;
		setActive();
		background.loadMainGameBackground();
		
		player = new Player();
		door = new Door(64 * 7, 0, 64 * 3, 64);
		enemies = new ArrayList<>();
		arrows = new ArrayList<>();
		stage = new Stage();
		stage.loadRandomStage(enemies);
		clearedTotalStages = 0;
	}
	
	/*
	* Controlla se si verificano collisioni tra le frecce e i nemici
	*/
	public void checkCollisions(){
		if(door.checkIfActive() && player.getCollisionBox().checkCollision(door.getCollisionBox())){
			clearedTotalStages++;
			loadNextStage();
		}
		for(Enemy enemy: enemies){
			if(enemy.checkIfActive() && enemy.getCollisionBox().checkCollision(player.getCollisionBox())){
				player.setInactive();
			}
			for(Arrow arrow: arrows){
				if(enemy.checkIfActive() && enemy.getCollisionBox().checkCollision(arrow.getCollisionBox())){
					arrow.setInactive();
					enemy.lowerHealth();
				}
			}
		}
	}
	
	public void updateArrows(){
		for(int i = 0; i < arrows.size(); i++){
			arrows.get(i).checkBoundaries();
			if (arrows.get(i).checkIfActive()){
				if(arrows.get(i).getAxis() && arrows.get(i).getDirection()){
					//la freccia si muove a destra
					arrows.get(i).moveRight();
				}
				else if(arrows.get(i).getAxis() && arrows.get(i).getDirection() == false){
					//la freccia si muove a sinistra
					arrows.get(i).moveLeft();
				}
				else if (arrows.get(i).getAxis() == false && arrows.get(i).getDirection()){
					//la freccia si muove verso il basso
					arrows.get(i).moveDown();
				}
				else if (arrows.get(i).getAxis() == false && arrows.get(i).getDirection() == false){
					//la freccia si muove verso l'alto
					arrows.get(i).moveUp();
				}
			}
			else {
				arrows.remove(i);
				i-= 1;
			}
		}
	}
	
	public void updateEnemies(){
		for(Enemy enemy: enemies){
			enemy.updateBehaviour(player.getX(), player.getY());
		}
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(door.checkIfActive()){
			door.paint(g);
		}
		player.paint(g);
		for (Arrow arrow: arrows){
			arrow.paint(g);
		}
		for (Enemy enemy: enemies) {
			if(enemy.checkIfActive()) {
				enemy.paint(g);
			}
		}
	}
	
	@Override
	public void processInput() {

		//movimento
		if(keyH.upPressed && keyH.rightPressed){
			player.moveUpRight();
		}
		else if(keyH.upPressed && keyH.leftPressed){
			player.moveUpLeft();
		}
		else if(keyH.downPressed && keyH.rightPressed){
			player.moveDownRight();
		}
		else if(keyH.downPressed && keyH.leftPressed) {
			player.moveDownLeft();
		}
		else if(keyH.upPressed){
			player.moveUp();
		}
		else if(keyH.downPressed){
			player.moveDown();
		}
		else if(keyH.leftPressed){
			player.moveLeft();
		}
		else if(keyH.rightPressed) {
			player.moveRight();
		}
		
		//shooting
		if (keyH.shootUp && player.canShoot()) {
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
		}
	}

	public boolean isGameOver(){
		if(!player.checkIfActive()){
			return true;
		}
		return false;
	}

	public boolean checkStageCompletion(){
		for(Enemy enemy: enemies){
			if(enemy.checkIfActive()){
				return false;
			}
		}
		return true;
	}

	public void loadNextStage(){
		player.setY(512 - 64);
		player.setX(512);
		stage.loadRandomStage(enemies);
		door.setInactive();
	}
	
	@Override
	public void update() {
		if (clearedTotalStages >= 10){
			setInactive();
		}
		processInput();
		player.updateCoolDown();
		updateArrows();
		updateEnemies();
		checkCollisions();
		if(isGameOver()){
			setInactive();
		}
		else if(checkStageCompletion()){
			door.setActive();
		}
	}
}
