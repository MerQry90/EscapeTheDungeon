package Components;

public class Cell {
	
	private int ID;
	private int northDoorID, eastDoorID, southDoorID, westDoorID;
	private boolean deadEnd;
	private boolean hasBeenFound;
	
	public Cell(int ID){
		this.ID = ID;
		northDoorID = -1;
		eastDoorID = -1;
		southDoorID = -1;
		westDoorID = -1;
		deadEnd = false;
		hasBeenFound = false;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
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
