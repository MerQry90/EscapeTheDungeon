package Components;

import java.util.*;

//Griglia delle stanze è 8 col x 8 righe
// 1a cifra la colonna, 2a cifra la riga

/**
 * Generates in a procedural way the entire game map
 * in an 8 x 8 grid, and identifies special rooms
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class CellManager {
	
	public final int STARTING_CELL = 35;
	private int placeableRooms;
	private List<Cell> cells;
	private List<Cell> deadEnds;
	private Map<Integer, Boolean> knownIDsAndFound;

	/**
	 * Initialize the starting room in order to
	 * create the entire map starting from the ID of the starting cell.
	 */
	public CellManager(){
		//init stanza iniziale così da poter cominciare a reiterare
		cells = new ArrayList<>();
		deadEnds = new ArrayList<>();
		do{
			cells.clear();
			deadEnds.clear();
			placeableRooms = 20;
			cells.add(new Cell(STARTING_CELL));
			makeMap(getCellByID(STARTING_CELL));
			makeDoors();
		}
		while(getNumberOfDeadEnds() < 4 || placeableRooms > 1 || getCellByID(STARTING_CELL).isDeadEnd());
		//printCells();
		knownIDsAndFound = new HashMap<>();
	}
	
	public List<Integer> getFoundRooms(){
		List<Integer> foundRooms = new ArrayList<>();
		if(!knownIDsAndFound.isEmpty()) {
			for (int id : knownIDsAndFound.keySet()) {
				if (knownIDsAndFound.get(id)) {
					foundRooms.add(id);
				}
			}
		}
		return foundRooms;
	}
	public List<Integer> getAlmostFoundRooms() {
		List<Integer> almostFoundRooms = new ArrayList<>();
		if(!knownIDsAndFound.isEmpty()) {
			for (int id : knownIDsAndFound.keySet()) {
				if (!knownIDsAndFound.get(id)) {
					almostFoundRooms.add(id);
				}
			}
		}
		return almostFoundRooms;
	}
	public void addNewFoundRoom(int ID){
		knownIDsAndFound.putIfAbsent(ID, true);
		if(!knownIDsAndFound.get(ID)){
			knownIDsAndFound.replace(ID, true);
		}
		int tmpID;
		if((tmpID = getCellByID(ID).getNorthDoorID()) != -1){
			knownIDsAndFound.putIfAbsent(tmpID, false);
		}
		if((tmpID = getCellByID(ID).getEastDoorID()) != -1){
			knownIDsAndFound.putIfAbsent(tmpID, false);
		}
		if((tmpID = getCellByID(ID).getSouthDoorID()) != -1){
			knownIDsAndFound.putIfAbsent(tmpID, false);
		}
		if((tmpID = getCellByID(ID).getWestDoorID()) != -1){
			knownIDsAndFound.putIfAbsent(tmpID, false);
		}
	}

	public List<Cell> getCellsList() {
		return cells;
	}
	public int getBossRoomID(){
		return deadEnds.get(deadEnds.size() - 1).getID();
	}
	public List<Integer> getSpecialRoomsIDs(){
		List<Integer> specialRooms = new ArrayList<>();
		specialRooms.add(deadEnds.get(0).getID());
		specialRooms.add(deadEnds.get(deadEnds.size() - 3).getID());
		specialRooms.add(deadEnds.get(deadEnds.size() - 2).getID());
		return specialRooms;
	}

	public Cell getCellByID(int ID){
		for(Cell cell: cells){
			if(cell.getID() == ID){
				return cell;
			}
		}
		return null;
	}
	
	public int getNumberOfDeadEnds(){
		int nOfDeadEnds = 0;
		for(Cell cell: cells){
			if(cell.isDeadEnd()){
				nOfDeadEnds += 1;
			}
		}
		return nOfDeadEnds;
	}
	
	public boolean checkIfFree(int ID){
		for(Cell cell: cells){
			if(cell.getID() == ID){
				return false;
			}
		}
		return true;
	}
	
	public boolean checkIfWithinBounds(int ID){
		if(ID < 11 || ID > 99 || ID % 10 == 0){
			//OOB
			return false;
		}
		return true;
	}
	
	public boolean checkChance(){
		//chance del 50% che una stanza non venga piazzata
		if(placeableRooms <= 1){
			return false;
		}
		Random random = new Random();
		return random.nextInt(2) == 0;
	}
	
	public int checkNumberOfNeighbours(int ID){
		//permette di creare un piano composto da più "corridoi", evitando di creare un unico blocco di stanze
		int northID = ID - 10;
		int southID = ID + 10;
		int eastID = ID + 1;
		int westID = ID - 1;
		int nOfNeighbours = 0;
		if(getCellByID(northID) != null){
			nOfNeighbours += 1;
		}
		if(getCellByID(eastID) != null) {
			nOfNeighbours += 1;
		}
		if(getCellByID(southID) != null){
			nOfNeighbours += 1;
		}
		if(getCellByID(westID) != null){
			nOfNeighbours += 1;
		}
		return nOfNeighbours;
	}
	
	public void makeMap(Cell cell){
		
		int northID = cell.getID() - 10;
		int southID = cell.getID() + 10;
		int eastID = cell.getID() + 1;
		int westID = cell.getID() - 1;
		
		if(checkIfFree(northID) && checkChance() &&
				checkIfWithinBounds(northID) && !(checkNumberOfNeighbours(northID) > 1)){
			placeableRooms -= 1;
			cells.add(new Cell(northID));
			makeMap(getCellByID(northID));
		}
		if(checkIfFree(eastID) && checkChance() &&
				checkIfWithinBounds(eastID) && !(checkNumberOfNeighbours(eastID) > 1)){
			placeableRooms -= 1;
			cells.add(new Cell(eastID));
			makeMap(getCellByID(eastID));
		}
		if(checkIfFree(southID) && checkChance() &&
				checkIfWithinBounds(southID) && !(checkNumberOfNeighbours(southID) > 1)){
			placeableRooms -= 1;
			cells.add(new Cell(southID));
			makeMap(getCellByID(southID));
		}
		if(checkIfFree(westID) && checkChance() &&
				checkIfWithinBounds(westID) && !(checkNumberOfNeighbours(westID) > 1)){
			placeableRooms -= 1;
			cells.add(new Cell(westID));
			makeMap(getCellByID(westID));
		}
	}
	
	public void makeDoors(){
		int northID;
		int southID;
		int eastID;
		int westID;
		int nOfDoors;
		
		for(Cell cell: cells){
			northID = cell.getID() - 10;
			southID = cell.getID() + 10;
			eastID = cell.getID() + 1;
			westID = cell.getID() - 1;
			nOfDoors = 0;
			
			if(!checkIfFree(northID) && checkIfWithinBounds(northID)){
				cell.setNorthDoorID(northID);
				nOfDoors += 1;
			}
			if(!checkIfFree(eastID) && checkIfWithinBounds(eastID)){
				cell.setEastDoorID(eastID);
				nOfDoors += 1;
			}
			if(!checkIfFree(southID) && checkIfWithinBounds(southID)){
				cell.setSouthDoorID(southID);
				nOfDoors += 1;
			}
			if(!checkIfFree(westID) && checkIfWithinBounds(westID)){
				cell.setWestDoorID(westID);
				nOfDoors += 1;
			}
			if(nOfDoors <= 1){
				cell.setAsDeadEnd();
				deadEnds.add(cell);
			}
		}
	}
	
}
