package Components;

import Entities.GenericEntity;
import Entities.StaticEntities.Door;
import Entities.StaticEntities.Obstacle;

import java.awt.*;

public class Room {
	
	private Obstacle northernLeftWall;
	private Obstacle northernCenterWall;
	private Obstacle northernRightWall;
	
	private Obstacle easternUpperWall;
	private Obstacle easternCenterWall;
	private Obstacle easternLowerWall;
	
	private Obstacle southernRightWall;
	private Obstacle southernCenterWall;
	private Obstacle southernLeftWall;
	
	private Obstacle westernLowerWall;
	private Obstacle westernCenterWall;
	private Obstacle westernUpperWall;
	
	private Door northernDoor;
	private Door easternDoor;
	private Door southernDoor;
	private Door westernDoor;
	
	private boolean hasNorthernDoor;
	private boolean hasEasternDoor;
	private boolean hasSouthernDoor;
	private boolean hasWesternDoor;
	
	private boolean isCleared;
	
	public Room(boolean hasNorthernDoor, boolean hasEasternDoor, boolean hasSouthernDoor, boolean hasWesternDoor) {
		this.hasNorthernDoor = hasNorthernDoor;
		this.hasEasternDoor = hasEasternDoor;
		this.hasSouthernDoor = hasSouthernDoor;
		this.hasWesternDoor = hasWesternDoor;
		init();
	}
	
	public void init(){
		northernLeftWall = new Obstacle(64, 0, 64 * 7, 64);
		northernCenterWall = new Obstacle(64 * 8, 0, 64, 64);
		northernRightWall = new Obstacle(64 * 9, 0, 64 * 7, 64);
		easternUpperWall = new Obstacle(64 * 16, 64, 64, 64 * 3);
		easternCenterWall = new Obstacle(64 * 16, 64 * 4, 64, 64);
		easternLowerWall = new Obstacle(64 * 16, 64 * 5, 64, 64 * 3);
		southernRightWall = new Obstacle(64 * 9, 64 * 8, 64 * 7, 64);
		southernCenterWall = new Obstacle(64 * 8, 64 * 8, 64, 64);
		southernLeftWall = new Obstacle(64, 64 * 8, 64 * 7, 64);
		westernLowerWall = new Obstacle(0, 64 * 5, 64, 64 * 3);
		westernCenterWall = new Obstacle(0, 64 * 4, 64, 64);
		westernUpperWall = new Obstacle(0, 64, 64, 64 * 3);
		
		if(hasNorthernDoor){
			northernDoor = new Door(northernCenterWall.getX(), northernCenterWall.getY(), 1.0, 0.1);
		}
		if(hasEasternDoor){
			easternDoor = new Door(easternCenterWall.getX(), easternCenterWall.getY(), 0.1, 1.0);
		}
		if(hasSouthernDoor){
			southernDoor = new Door(southernCenterWall.getX(), southernCenterWall.getY(), 1.0, 0.1);
		}
		if(hasWesternDoor){
			westernDoor = new Door(westernCenterWall.getX(), westernCenterWall.getY(), 0.1, 1.0);
		}
		
		isCleared = false;
	}
	
	public boolean checkCollisions(GenericEntity entity){
		return entity.checkCollision(northernLeftWall) ||
				(entity.checkCollision(northernCenterWall) && !(hasNorthernDoor && isCleared)) ||
				entity.checkCollision(northernRightWall) ||
				entity.checkCollision(easternUpperWall) ||
				(entity.checkCollision(easternCenterWall) && !(hasEasternDoor && isCleared)) ||
				entity.checkCollision(easternLowerWall) ||
				entity.checkCollision(southernRightWall) ||
				(entity.checkCollision(southernCenterWall) && !(hasSouthernDoor && isCleared)) ||
				entity.checkCollision(southernLeftWall) ||
				entity.checkCollision(westernLowerWall) ||
				(entity.checkCollision(westernCenterWall) && !(hasWesternDoor && isCleared)) ||
				entity.checkCollision(westernUpperWall);
	}
	
	public void openDoors(){
		isCleared = true;
	}
	
	public void paintDoors(Graphics g){
		if(hasNorthernDoor && isCleared){
			northernDoor.paint(g);
		}
		if(hasEasternDoor && isCleared){
			easternDoor.paint(g);
		}
		if(hasSouthernDoor && isCleared){
			southernDoor.paint(g);
		}
		if(hasWesternDoor && isCleared){
			westernDoor.paint(g);
		}
	}
	
	public boolean hasNorthernDoor() {
		return hasNorthernDoor;
	}
	public boolean hasEasternDoor() {
		return hasEasternDoor;
	}
	public boolean hasSouthernDoor() {
		return hasSouthernDoor;
	}
	public boolean hasWesternDoor() {
		return hasWesternDoor;
	}
}
