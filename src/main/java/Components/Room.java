package Components;

import Entities.StaticEntities.Door;
import Entities.StaticEntities.StaticEntity;
import Entities.StaticEntities.Wall;

public class Room {
	
	private Wall NorthernLeftWall;
	private Wall NorthernCenterWall;
	private Wall NorthernRightWall;
	private Wall EasternUpperWall;
	private Wall EasternCenterWall;
	private Wall EasternLowerWall;
	private Wall SouthernRightWall;
	private Wall SouthernCenterWall;
	private Wall SouthernLeftWall;
	private Wall WesternLowerWall;
	private Wall WesternCenterWall;
	private Wall WesternUpperWall;
	
	private Door NorthernDoor;
	private Door EasternDoor;
	private Door SouthernDoor;
	private Door WesternDoor;
	
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
	
	}
}
