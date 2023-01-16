package GameStates;

import Application.KeyHandler;
import Entities.Arrow;
import Entities.Enemy;
import Entities.Player;
import Entities.Zombie;

import java.util.ArrayList;
import java.awt.*;


public class MainGame extends GameState{
	
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
		
		//nemici dello scenario temporaneo------
		enemies.add(new Zombie(30, 30, 6, 5, 60, 20));
		enemies.add(new Zombie(200, 30, 6, 10, 30, 10));
		//--------------------------------------
	}
	
	/*
	* Controlla se si verificano collisioni tra le frecce e i nemici
	*/
	public void checkCollisionsAE(){
		boolean xAlignment;
		boolean yAlignment;
		for(Enemy enemy: enemies){
			for(Arrow arrow: arrows){
				//controllo allineamento della freccia con il nemico nell'asse delle x
				xAlignment = (arrow.getX() < (enemy.getX() + enemy.getWidth()))
							&& ((arrow.getX() + arrow.getWidth()) > enemy.getX());
				//controllo allineamento della freccia con il nemico nell'asse delle y
				yAlignment = (arrow.getY() < (enemy.getY() + enemy.getHeight()))
							&& ((arrow.getY() + arrow.getHeight()) > enemy.getY());
				if(enemy.checkIsAlive() && xAlignment && yAlignment) {
					enemy.lowerHealth();
					arrow.kill();
				}
			}
		}
	}
	
	/*
	* Controlla se si verificano collisioni tra il giocatore e i nemici
	*/
	public void checkCollisionsPE(){
		boolean xAlignment;
		boolean yAlignment;
		for(Enemy enemy: enemies){
			//controllo allineamento del player con il nemico nell'asse delle x
			xAlignment = (player.getX() < (enemy.getX() + enemy.getWidth()))
					&& ((player.getX() + player.getWidth()) > enemy.getX());
			//controllo allineamento del player con il nemico nell'asse delle y
			yAlignment = (player.getY() < (enemy.getY() + enemy.getHeight()))
					&& ((player.getY() + player.getHeight()) > enemy.getY());
			if(enemy.checkIsAlive() && xAlignment && yAlignment) {
				player.kill();
			}
		}
	}
	
	public void updateArrows(){
		for(int i = 0; i < arrows.size(); i++){
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
	
	@Override
	public void update() {
		processInput();
		player.updateCoolDown();
		updateArrows();
		updateEnemies();
		checkCollisionsPE();
		checkCollisionsAE();
		/*
		entityControl.moveEnemies();
		entityControl.updateArrows();
		entityControl.checkCollisionsAE();
		entityControl.checkCollisionsPE();
		if(entityControl.isGameOver()){
			setInactive();
		}
		if(entityControl.checkStageCompletion()){
			//TODO si aprono le porte e il giocatore può accedere all'area successiva
			setInactive();
		}*/
	}
}
