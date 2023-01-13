package Entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class EntityControl {
	private Entity player;
	private List<Enemy> enemyList;
	private List<Arrow> arrowList;

	private int upperBound;
	private int lowerBound;
	private int leftBound;
	private int rightBound;
	
	public EntityControl(){
		player = new Player();
		enemyList = new ArrayList<>();
		arrowList = new ArrayList<>();
		
		upperBound = 64; //tmp
		lowerBound = 64 * 7; //tmp
		leftBound = 64; //tmp
		rightBound = 64 * 15; //tmp
		
		//nemici dello scenario temporaneo------
		enemyList.add(new Zombie(30, 30, 6));
		enemyList.add(new Zombie(200, 30, 7));
		//--------------------------------------
	}
	
	public boolean checkUpperBound(Entity entity){
		if(entity.getY() - entity.getSpeed() < upperBound){
			return false;
		}
		return true;
	}
	public boolean checkLowerBound(Entity entity){
		if(entity.getY() + entity.getSpeed() > lowerBound){
			return false;
		}
		return true;
	}
	public boolean checkLeftBound(Entity entity){
		if(entity.getX() - entity.getSpeed() < leftBound){
			return false;
		}
		return true;
	}
	public boolean checkRightBound(Entity entity){
		if(entity.getX() + entity.getSpeed() > rightBound){
			return false;
		}
		return true;
	}
	
	public void movePlayerUp(){
		if(checkUpperBound(player)){
			player.moveUp();
		}
	}
	public void movePlayerDown(){
		if(checkLowerBound(player)){
			player.moveDown();
		}
	}
	public void movePlayerLeft(){
		if(checkLeftBound(player)){
			player.moveLeft();
		}
	}
	public void movePlayerRight(){
		if(checkRightBound(player)){
			player.moveRight();
		}
	}
	
	/*
	actionByRate determina la probabilità dello zombie di muoversi
	(potrebbe non servire più se il gioco gira decentemente a 30 fps)
	*/
	public boolean actionByRate(int actionPercentage){
		if(actionPercentage >= 100){
			return true;
		}
		if(actionPercentage <= 0){
			return false;
		}
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		int val = random.nextInt(100);
		if(val <= actionPercentage) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void moveEnemies(){
		for (Entity enemy: enemyList) {
			if(enemy.checkIsAlive()){
				if(actionByRate(50)){
					int dx = player.getX() - enemy.getX();
					int dy = player.getY() - enemy.getY();
					//moves on the x axis
					if(abs(dx) >= abs(dy)){
						if(dx >= 0){
							enemy.moveRight();
						}
						else {
							enemy.moveLeft();
						}
					}
					//moves on the y axis
					else {
						if(dy >= 0){
							enemy.moveDown();
						}
						else {
							enemy.moveUp();
						}
					}
				}
			}
		}
	}

	public void addArrow(boolean axis, boolean direction){
		arrowList.add(new Arrow(player.getX(), player.getY(), axis, direction));
	}

	public void updateArrows(){
		for(int i = 0; i < arrowList.size(); i++){
			if (arrowList.get(i).checkIsAlive()){
				if(arrowList.get(i).getAxis() && arrowList.get(i).getDirection()){
					//la freccia si muove a destra
					arrowList.get(i).moveRight();
				}
				else if(arrowList.get(i).getAxis() && arrowList.get(i).getDirection() == false){
					//la freccia si muove a sinistra
					arrowList.get(i).moveLeft();
				}
				else if (arrowList.get(i).getAxis() == false && arrowList.get(i).getDirection()){
					//la freccia si muove verso il basso
					arrowList.get(i).moveDown();
				}
				else if (arrowList.get(i).getAxis() == false && arrowList.get(i).getDirection() == false){
					//la freccia si muove verso l'alto
					arrowList.get(i).moveUp();
				}
			}
			else {
				arrowList.remove(i);
				i-= 1;
			}
		}
	}

	/*
	* Controlla se si verificano collisioni tra le frecce e i nemici
	*/
	public void checkCollisionsAE(){
		boolean xAlignment;
		boolean yAlignment;
		for(Enemy enemy: enemyList){
			for(Arrow arrow: arrowList){
				//controllo allineamento della freccia con il nemico nell'asse delle x
				xAlignment = (arrow.getX() < (enemy.getX() + enemy.getWidth()))
							&& ((arrow.getX() + arrow.getWidth()) > enemy.getX());
				//controllo allineamento della freccia con il nemico nell'asse delle y
				yAlignment = (arrow.getY() < (enemy.getY() + enemy.getHeight()))
							&& ((arrow.getY() + arrow.getHeight()) > enemy.getY());
				if(enemy.checkIsAlive() && xAlignment && yAlignment) {
					enemy.kill();
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
		for(Enemy enemy: enemyList){
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

	public boolean isGameOver(){
		return !(player.checkIsAlive());
	}

	public void renderAllEntities(Graphics g){
		player.paint(g);
		for (Arrow arrow: arrowList){
			arrow.paint(g);
		}
		for (Enemy enemy: enemyList) {
			if(enemy.checkIsAlive()) {
				enemy.paint(g);
			}
		}
	}
}
