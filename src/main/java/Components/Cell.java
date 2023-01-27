package Components;

public class Cell {
	
	private int position;
	private Room currentRoom;
	
	public Cell(int position){
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setCurrentRoom(boolean hasNorthernDoor, boolean hasEasternDoor,
							   boolean hasSouthernDoor, boolean hasWesternDoor) {
		this.currentRoom = new Room(hasNorthernDoor, hasEasternDoor, hasSouthernDoor, hasWesternDoor);
	}
	
	public boolean hasNorthernDoor() {
		return currentRoom.hasNorthernDoor();
	}
	public boolean hasEasternDoor() {
		return currentRoom.hasEasternDoor();
	}
	public boolean hasSouthernDoor() {
		return currentRoom.hasSouthernDoor();
	}
	public boolean hasWesternDoor() {
		return currentRoom.hasWesternDoor();
	}
}
