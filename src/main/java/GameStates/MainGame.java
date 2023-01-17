package GameStates;

import Application.KeyHandler;
import Entities.*;
import Stages.Stage;

import java.util.ArrayList;
import java.awt.*;


public class MainGame extends GameState{

	private Stage stage;
	private Player player;
	private java.util.List<Enemy> enemies;
	private java.util.List<Arrow> arrows;
	
	public MainGame(KeyHandler keyH){
		this.keyH = keyH;
		setActive();
		background.loadMainGameBackground();
		
		player = new Player();
		enemies = new ArrayList<>();
		arrows = new ArrayList<>();
		stage = new Stage();
		stage.loadRandomStage(enemies);

		//TODO chiamata al creatore di scenari
		//nemici dello scenario temporaneo------
		//enemies.add(new Zombie(30, 30, 6, 5, 60, 20));
		//enemies.add(new Zombie(200, 30, 6, 10, 30, 10));
		//--------------------------------------
	}
	
	/*
	* Controlla se si verificano collisioni tra le frecce e i nemici
	*/
	public void checkCollisions(){
		for(Enemy enemy: enemies){
			if(enemy.checkIsAlive() && enemy.getCollisionBox().checkCollision(player.getCollisionBox())){
				player.kill();
			}
			for(Arrow arrow: arrows){
				if(enemy.checkIsAlive() && enemy.getCollisionBox().checkCollision(arrow.getCollisionBox())){
					arrow.kill();
					enemy.lowerHealth();
				}
			}
		}
	}
	
	public void updateArrows(){
		for(int i = 0; i < arrows.size(); i++){
			arrows.get(i).checkBoundaries();
			if (arrows.get(i).checkIsAlive()){
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
		player.paint(g);
		for (Arrow arrow: arrows){
			arrow.paint(g);
		}
		for (Enemy enemy: enemies) {
			if(enemy.checkIsAlive()) {
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
	}

	public boolean isGameOver(){
		if(!player.checkIsAlive()){
			return true;
		}
		return false;
	}

	public boolean checkStageCompletion(){
		for(Enemy enemy: enemies){
			if(enemy.checkIsAlive()){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void update() {
		processInput();
		player.updateCoolDown();
		updateArrows();
		updateEnemies();
		checkCollisions();
		if(isGameOver()){
			setInactive();
		}
		else if(checkStageCompletion()){
			//TODO si aprono le porte e il giocatore può accedere all'area successiva
			setInactive();
		}
	}
}
