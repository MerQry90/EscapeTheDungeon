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
	
	private boolean deadEnd;
	public int roomID;
	
	public Room(int roomID, int northLeadsTo, int eastLeadsTo, int southLeadsTo, int westLeadsTo) {
		this.roomID = roomID;
		deadEnd = false;
		if(northLeadsTo > 0){
			this.hasNorthernDoor = true;
		}
		if(eastLeadsTo > 0){
			this.hasEasternDoor = true;
		}
		if(southLeadsTo > 0){
			this.hasSouthernDoor = true;
		}
		if(westLeadsTo > 0){
			this.hasWesternDoor = true;
		}

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
			northernDoor = new Door(northernCenterWall.getX(), northernCenterWall.getY(),
					1.0, 0.1, northLeadsTo);
		}
		if(hasEasternDoor){
			easternDoor = new Door(easternCenterWall.getX(), easternCenterWall.getY(),
					0.1, 1.0, eastLeadsTo);
		}
		if(hasSouthernDoor){
			southernDoor = new Door(southernCenterWall.getX(), southernCenterWall.getY(),
					1.0, 0.1, southLeadsTo);
		}
		if(hasWesternDoor){
			westernDoor = new Door(westernCenterWall.getX(), westernCenterWall.getY(),
					0.1, 1.0, westLeadsTo);
		}
		
		isCleared = false;
	}
	
	public boolean checkWallsCollisions(GenericEntity entity){
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
	
	public void setAsDeadEnd(){
		this.deadEnd = true;
	}
	public boolean isDeadEnd() {
		return deadEnd;
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
	
	public Door getNorthernDoor() {
		return northernDoor;
	}
	public Door getEasternDoor() {
		return easternDoor;
	}
	public Door getSouthernDoor() {
		return southernDoor;
	}
	public Door getWesternDoor() {
		return westernDoor;
	}
}
