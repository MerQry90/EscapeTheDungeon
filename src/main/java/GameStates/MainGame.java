package GameStates;

import Application.KeyHandler;
import Components.*;

import java.awt.*;


public class MainGame extends GameState{

	private Stage stage;
	private boolean pause;
	private boolean toggleMap;
	private int pauseCountdown;
	private int mapCountdown;
	private int numberOfKeys;
	
	private int clearedTotalStages;
	
	private EntityManager entityManager;
	private CellManager cellManager;
	private UI ui;
	
	public boolean win;
	
	public MainGame(KeyHandler keyH){
		this.keyH = keyH;
		setActive();
		background.loadMainGameBackground();
		
		entityManager = new EntityManager(this);
		
		stage = new Stage(entityManager);
		
		clearedTotalStages = 0;
		pause = false;
		toggleMap = false;
		numberOfKeys = 0;
		
		cellManager = new CellManager();
		setEntityGroups();
		goToStartingRoom();
		ui = new UI();
		setUI();
		audioManager = new AudioManager();
		playMusic(0);
	}
	
	public void addKey(){
		numberOfKeys += 1;
	}
	public boolean checkIfEnoughKeys(){
		return numberOfKeys >= 3;
	}
	
	public void setUI(){
		ui.setSpecialRoomsIDS(cellManager.getSpecialRoomsIDs());
		ui.setBossRoomID(cellManager.getBossRoomID());
	}

	public void setEntityGroups(){
		for(Cell cell: cellManager.getCellsList()){
			entityManager.entityGenerator.addGroup(cell.getID());
		}
		entityManager.entityGenerator.setSpecialRoomsIDS(cellManager.getSpecialRoomsIDs());
		entityManager.entityGenerator.setBossRoomID(cellManager.getBossRoomID());
		entityManager.entityGenerator.generateEntities();
	}
	
	public void goToStartingRoom(){
		entityManager.setNewRoom(cellManager.STARTING_CELL,
				cellManager.getCellByID(cellManager.STARTING_CELL).getNorthDoorID(),
				cellManager.getCellByID(cellManager.STARTING_CELL).getEastDoorID(),
				cellManager.getCellByID(cellManager.STARTING_CELL).getSouthDoorID(),
				cellManager.getCellByID(cellManager.STARTING_CELL).getWestDoorID());
		entityManager.setDefaultPlayerPositionCenter();
		entityManager.setRoomAsDeadEnd();
		cellManager.addNewFoundRoom(cellManager.STARTING_CELL);
	}
	
	public void translateCellToNewRoom(int newID){
		if (entityManager.getRoomID() == newID + 10) {
			entityManager.setDefaultPlayerPositionDown();
		} else if (entityManager.getRoomID() == newID - 1) {
			entityManager.setDefaultPlayerPositionLeft();
		} else if (entityManager.getRoomID() == newID - 10) {
			entityManager.setDefaultPlayerPositionUp();
		} else if (entityManager.getRoomID() == newID + 1) {
			entityManager.setDefaultPlayerPositionRight();
		}
		entityManager.setNewRoom(newID,
				cellManager.getCellByID(newID).getNorthDoorID(),
				cellManager.getCellByID(newID).getEastDoorID(),
				cellManager.getCellByID(newID).getSouthDoorID(),
				cellManager.getCellByID(newID).getWestDoorID());
		cellManager.addNewFoundRoom(newID);
	}
	
	@Override
	public void processInput() {
		//vari
		if(keyH.mPressed && mapCountdown <= 0){
			mapCountdown = 10;
			System.out.println(mapCountdown);
			toggleMap = !toggleMap;
			if(toggleMap){
				ui.enableMap();
			}
			else ui.disableMap();
		}
		if(keyH.escapePressed && pauseCountdown <= 0){
			pauseCountdown = 10;
			pause = !pause;
		}
		//movimento
		if(keyH.upPressed && keyH.rightPressed){
			entityManager.setNextPlayerInstruction("up-right");
		}
		else if(keyH.upPressed && keyH.leftPressed){
			entityManager.setNextPlayerInstruction("up-left");
		}
		else if(keyH.downPressed && keyH.rightPressed){
			entityManager.setNextPlayerInstruction("down-right");
		}
		else if(keyH.downPressed && keyH.leftPressed) {
			entityManager.setNextPlayerInstruction("down-left");
		}
		else if(keyH.upPressed){
			entityManager.setNextPlayerInstruction("up");
		}
		else if(keyH.downPressed){
			entityManager.setNextPlayerInstruction("down");
		}
		else if(keyH.leftPressed){
			entityManager.setNextPlayerInstruction("left");
		}
		else if(keyH.rightPressed) {
			entityManager.setNextPlayerInstruction("right");
		}
		else {
			entityManager.setNextPlayerInstruction("stop");
		}
		
		//shooting
		if (keyH.shootUp && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow("up");
		}
		else if (keyH.shootDown && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow("down");
		}
		else if (keyH.shootLeft && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow("left");
		}
		else if (keyH.shootRight && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow("right");
		}

		//DEBUG ONLY
		if(keyH.killAll){
			entityManager.killAll();
		}
		if(keyH.tpToBoss){
			entityManager.setDefaultPlayerPositionUp();
			translateCellToNewRoom(cellManager.getBossRoomID());
		}
		
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		entityManager.renderEntities(g);
		ui.drawMap(cellManager.getFoundRooms(), cellManager.getAlmostFoundRooms(), entityManager.getRoomID(), g);
		ui.drawPlayerHeart(g, entityManager.getPlayer());
		ui.drawKeyNumber(numberOfKeys, g);
		ui.drawRepelMessage(g);
	}
	
	@Override
	public void update() {
		pauseCountdown--;
		mapCountdown--;
		processInput();
		if(!pause) {
			if(entityManager.isBossDead()){
				win = true;
				setInactive();
			}
			if(entityManager.isGameOver()){
				win = false;
				setInactive();
			}
			entityManager.getPlayer().translateInputToMovement();
			entityManager.getPlayer().updateCoolDown();
			entityManager.updateArrows();
			entityManager.updateEnemies();
			entityManager.updateHazards();
			entityManager.updateHostileProjectiles();
			entityManager.checkEntityToEntityCollisions();
			entityManager.checkItemsCollisions();
			entityManager.checkPowerUpCollision();
			entityManager.checkHazardsCollision();
			entityManager.checkRoomCompletion();
			int collisionID = entityManager.checkPlayerToDoorsCollisions();
			if(collisionID > 0){
				if(entityManager.entityGenerator.checkIfBossRoom(collisionID) && !checkIfEnoughKeys()){
					goToStartingRoom();
					ui.refreshRepelMessage();
				}
				else {
					if(entityManager.entityGenerator.checkIfSpecialRoom(collisionID)){
						addKey();
					}
					translateCellToNewRoom(collisionID);
				}
			}
		}
	}
}
