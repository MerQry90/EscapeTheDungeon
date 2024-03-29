package Components;

/**
 * Data structure that identifies as a room in the
 * procedural generation of the game map
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Cell {
	
	private int ID;
	private int northDoorID, eastDoorID, southDoorID, westDoorID;
	private boolean deadEnd;

	/**
	 * Data structure that identifies as a room in the
	 * procedural generation of the map.
	 * All the DoorID's are set to -1 and deadEnd is set to false.
	 * @param ID is the unique identifier of the room and is determined by the position in a grid.
	 *           The starting room cell ID is always 35 and is the center of the grid.
	 */
	public Cell(int ID){
		this.ID = ID;
		northDoorID = -1;
		eastDoorID = -1;
		southDoorID = -1;
		westDoorID = -1;
		deadEnd = false;
	}
	
	public int getID() {
		return ID;
	}
	public int getNorthDoorID() {
		return northDoorID;
	}
	public void setNorthDoorID(int northDoorID) {
		this.northDoorID = northDoorID;
	}
	public int getEastDoorID() {
		return eastDoorID;
	}
	public void setEastDoorID(int eastDoorID) {
		this.eastDoorID = eastDoorID;
	}
	public int getSouthDoorID() {
		return southDoorID;
	}
	public void setSouthDoorID(int southDoorID) {
		this.southDoorID = southDoorID;
	}
	public int getWestDoorID() {
		return westDoorID;
	}
	public void setWestDoorID(int westDoorID) {
		this.westDoorID = westDoorID;
	}
	public boolean isDeadEnd() {
		return deadEnd;
	}
	public void setAsDeadEnd() {
		this.deadEnd = true;
	}
	
	@Override
	public String toString(){
		return "#"+getID()+" N:"+getNorthDoorID()+
				" E:"+getEastDoorID()+" S:"+getSouthDoorID()+
				" W:"+getWestDoorID()+" deadEnd: "+isDeadEnd();
	}
	
}
