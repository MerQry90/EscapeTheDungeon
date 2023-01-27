package Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Griglia delle stanze è 8 col x 8 rig
// 1a cifra la colonna, 2a cifra la riga

public class RoomManager {

	private final int startingCell = 35;
	private int placeableRooms;
	private List<Cell> cells;
	
	public RoomManager(){
		//init stanza iniziale così da poter cominciare a reiterare
		cells = new ArrayList<>();
		cells.add(new Cell(startingCell));
		getCellByPosition(startingCell).setCurrentRoom(true, true, true, true);
		placeableRooms = 10;
		makeMap(getCellByPosition(startingCell));
	}
	
	public Cell getCellByPosition(int position){
		for(Cell cell: cells){
			if(cell.getPosition() == position){
				return cell;
			}
		}
		return null;
	}
	
	public boolean checkIfFree(int position){
		if(position < 11 || position > 99){
			//OOB
			return false;
		}
		for(Cell cell: cells){
			if(cell.getPosition() == position){
				return false;
			}
		}
		return true;
	}
	
	public boolean checkChance(){
		if(placeableRooms <= 0){
			return false;
		}
		Random random = new Random();
		return random.nextInt(2) == 0;
	}
	
	public void makeMap(Cell cell){
		
		boolean roomPlacedNorth = false;
		boolean roomPlacedEast = false;
		boolean roomPlacedSouth = false;
		boolean roomPlacedWest = false;
		
		int northPos = cell.getPosition() - 10;
		int southPos = cell.getPosition() + 10;
		int eastPos = cell.getPosition() + 1;
		int westPos = cell.getPosition() - 1;
		
		if(checkIfFree(northPos) && checkChance()){
			roomPlacedNorth = true;
			placeableRooms -= 1;
			cells.add(new Cell(northPos));
			makeMap(getCellByPosition(northPos));
		}
		
		if(checkIfFree(eastPos) && checkChance()){
			roomPlacedEast = true;
			placeableRooms -= 1;
			cells.add(new Cell(eastPos));
			makeMap(getCellByPosition(eastPos));
		}
		
		if(checkIfFree(southPos) && checkChance()){
			roomPlacedSouth = true;
			placeableRooms -= 1;
			cells.add(new Cell(southPos));
			makeMap(getCellByPosition(southPos));
		}
		
		if(checkIfFree(westPos) && checkChance()){
			roomPlacedWest = true;
			placeableRooms -= 1;
			cells.add(new Cell(westPos));
			makeMap(getCellByPosition(westPos));
		}
		
		cell.setCurrentRoom(roomPlacedNorth, roomPlacedEast, roomPlacedSouth, roomPlacedWest);
		
		System.out.println(cell.getPosition());
	}
	
}
