package GameStates;

import Application.KeyHandler;
import Components.*;

import java.awt.*;


public class MainGame extends GameState{

	private Stage stage;
	private boolean pause;
	private int pauseCountdown;
	
	private int clearedTotalStages;
	
	private EntityManager entityManager;
	private CellManager cellManager;
	private UI ui;
	
	public MainGame(KeyHandler keyH){
		this.keyH = keyH;
		setActive();
		background.loadMainGameBackground();
		
		entityManager = new EntityManager();
		
		stage = new Stage(entityManager);
		
		clearedTotalStages = 0;
		pause = false;
		
		cellManager = new CellManager();
		setEntityGroups();
		translateCellToNewRoom(-1);
		ui = new UI();
	}

	public void setEntityGroups(){
		for(Cell cell: cellManager.getCellsList()){
			entityManager.entityGenerator.addGroup(cell.getID());
		}
		int r1 = cellManager.getDeadEndsList().get(0).getID();
		int r2 = cellManager.getDeadEndsList().get(cellManager.getDeadEndsList().size() - 3).getID();
		int r3 = cellManager.getDeadEndsList().get(cellManager.getDeadEndsList().size() - 2).getID();
		int br = cellManager.getDeadEndsList().get(cellManager.getDeadEndsList().size() - 1).getID();
		entityManager.entityGenerator.setSpecialRoomsIDS(r1, r2, r3, br);
		entityManager.entityGenerator.generateEntities();
	}
	
	public void translateCellToNewRoom(int newID){
		if(entityManager.getRoom() == null){
			entityManager.setNewRoom(cellManager.STARTING_CELL,
					cellManager.getCellByID(cellManager.STARTING_CELL).getNorthDoorID(),
					cellManager.getCellByID(cellManager.STARTING_CELL).getEastDoorID(),
					cellManager.getCellByID(cellManager.STARTING_CELL).getSouthDoorID(),
					cellManager.getCellByID(cellManager.STARTING_CELL).getWestDoorID());
			entityManager.setDefaultPlayerPositionCenter();
			entityManager.setRoomAsDeadEnd();
			newID = cellManager.STARTING_CELL;
		}
		else {
			System.out.println("room #" + newID);
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
		}
		if(entityManager.entityGenerator.checkIfSpecial(newID)){
			System.out.println("SPECIAL ROOM");
		}
		else if(entityManager.entityGenerator.checkIfBossRoom(newID)){
			System.out.println("BOSS ROOM");
		}
	}
	
	@Override
	public void processInput() {
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
		
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		entityManager.renderEntities(g);
		ui.drawUI(g, entityManager.getPlayer());
	}
	
	@Override
	public void update() {
		pauseCountdown--;
		processInput();
		if(!pause) {
			if (clearedTotalStages >= 10) {
				setInactive();
			}
			if(entityManager.isGameOver()){
				setInactive();
			}
			entityManager.getPlayer().move();
			entityManager.getPlayer().updateCoolDown();
			entityManager.updateArrows();
			entityManager.updateEnemies();
			entityManager.checkDynamicCollisions();
			entityManager.checkItemsCollisions();
			entityManager.checkRoomCompletion();
			int collisionID = entityManager.checkPlayerAndDoorsCollisions();
			if(collisionID > 0){
				translateCellToNewRoom(collisionID);
			}
		}
	}
}
