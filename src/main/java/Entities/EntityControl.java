package Entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityControl {
	private Entity player;
	private List<Entity> enemyList;
	private List<Arrow> ArrowList;
	
	private int upperBound;
	private int lowerBound;
	private int leftBound;
	private int rightBound;
	
	public EntityControl(){
		player = new Player();
		enemyList = new ArrayList<>();
		ArrowList = new ArrayList<>();
		
		upperBound = 64 * 2; //tmp
		lowerBound = 64 * 7; //tmp
		leftBound = 64 * 2; //tmp
		rightBound = 64 * 15; //tmp
		
		//nemici dello scenario temporaneo------
		enemyList.add(new Zombie(30, 30, 10));
		enemyList.add(new Zombie(200, 30, 20));
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
	public boolean actionByRate(int possibleCases, int totalCases){
		if(possibleCases > totalCases || possibleCases < 0){
			return false;
		}
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		int val = random.nextInt(totalCases);
		if(val <= possibleCases) {
			return true;
		}
		return false;
	}
	
	public void moveEnemies(){
		for (Entity enemy: enemyList) {
			if(enemy.checkIsAlive()){
				if(actionByRate(100, 100)){
					int dx = player.getX() - enemy.getX();
					int dy = player.getY() - enemy.getY();
					int modX, modY;
					
					if(dx < 0){
						modX = dx * (-1);
					}
					else{
						modX = dx;
					}
					if(dy < 0){
						modY = dy * (-1);
					}
					else{
						modY = dy;
					}
					
					//moves on the x axis
					if(modX >= modY){
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
	
	public void checkCollisions(){
		//collisioni di frecce con i nemici
		for(Entity enemy: enemyList){
			if(enemy.checkIsAlive()){
				for(Arrow arrow: ArrowList){
					if((arrow.getX() >= enemy.getX()) && (arrow.getX() < (enemy.getX() + enemy.getWidth())) &&
							((arrow.getY() >= enemy.getY()) && (arrow.getY() < enemy.getY() + enemy.getHeight()))) {
						enemy.kill();
						arrow.kill();
					}
				}
				
				//collisioni dei nemici con il player
				if((player.getX() >= enemy.getX()) && (player.getX() < (enemy.getX() + enemy.getWidth())) &&
						((player.getY() >= enemy.getY()) && (player.getY() < enemy.getY() + enemy.getHeight()))) {
					player.kill();
				}
			}
		}
	}
	
	public boolean isGameOver(){
		return !(player.checkIsAlive());
	}
	
	public void renderAllEntities(Graphics g){
		player.paint(g);
		for (Entity enemy: enemyList) {
			if(enemy.checkIsAlive()) {
				enemy.paint(g);
			}
		}
		//TODO altre entità
	}
}
