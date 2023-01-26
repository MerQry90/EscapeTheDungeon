package Components;

import Entities.GenericEntity;
import Entities.StaticEntities.Door;
import Entities.StaticEntities.Obstacle;

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
	}
	
	public boolean checkCollisions(GenericEntity entity){
		return entity.checkCollision(northernLeftWall) ||
				(entity.checkCollision(northernCenterWall) && !hasNorthernDoor)||
				entity.checkCollision(northernRightWall) ||
				entity.checkCollision(easternUpperWall) ||
				(entity.checkCollision(easternCenterWall) && !hasEasternDoor)||
				entity.checkCollision(easternLowerWall) ||
				entity.checkCollision(southernRightWall) ||
				(entity.checkCollision(southernCenterWall) && !hasSouthernDoor)||
				entity.checkCollision(southernLeftWall) ||
				entity.checkCollision(westernLowerWall) ||
				(entity.checkCollision(westernCenterWall) && !hasWesternDoor) ||
				entity.checkCollision(westernUpperWall);
	}
}
