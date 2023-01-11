package Entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityControl {
	private Entity player;
	private List<Entity> enemyList;
	private List<Entity> projectileList;
	
	private int upperBound;
	private int lowerBound;
	private int leftBound;
	private int rightBound;
	
	public EntityControl(){
		player = new Player();
		enemyList = new ArrayList<>();
		projectileList = new ArrayList<>();
		
		upperBound = 64 * 2; //tmp
		lowerBound = 64 * 7; //tmp
		leftBound = 64 * 2; //tmp
		rightBound = 64 * 15; //tmp
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
	
	public void renderAllEntities(Graphics g){
		player.paint(g);
		//TODO altre entità
	}
}
