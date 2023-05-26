package GameStates;

import Application.KeyHandler;
import Components.*;
import Entities.DynamicEntities.Player;
import Entities.DynamicEntities.Projectiles.Arrow;

import java.awt.*;

/**
 * Type of GameState, defines the actual running game.
 * Manages the creation of the map and the initialization of all the game components.
 * This state is over when the player dies or the final boss is killed.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class MainGame extends GameState{
	
	private boolean pause;
	private boolean toggleMap;
	private int pauseCountdown;
	private int mapCountdown;
	
	private EntityManager entityManager;
	private CellManager cellManager;
	public UI ui;
	
	public boolean win;

	/**
	 * Loads the main game.
	 * @param keyH used to read inputs
	 * @param audioManager needed to play music and audio effects
	 */
	public MainGame(KeyHandler keyH, AudioManager audioManager){
		this.keyH = keyH;
		this.audioManager = audioManager;
		
		setActive();
		background.loadMainGameBackground();
		
		entityManager = new EntityManager(this);

		pause = false;
		toggleMap = true;
		mapCountdown = 0;
		
		cellManager = new CellManager();
		setEntityGroups();
		goToStartingRoom();
		ui = new UI();
		setUI();
		audioManager.playSoundLoop(AudioManager.NORMAL_MUSIC_INDEX);
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
			toggleMap = !toggleMap;
			if(toggleMap){
				ui.enableMap();
			}
			else {
				ui.disableMap();
			}
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
			entityManager.newArrow(Arrow.ORIENTATION_UP_STRAIGHT);
		}
		else if (keyH.shootDown && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow(Arrow.ORIENTATION_DOWN_STRAIGHT);
		}
		else if (keyH.shootLeft && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow(Arrow.ORIENTATION_LEFT_STRAIGHT);
			entityManager.getPlayer().setAnimationShootingLeft();
		}
		else if (keyH.shootRight && entityManager.getPlayer().canShoot()) {
			entityManager.newArrow(Arrow.ORIENTATION_RIGHT_STRAIGHT);
			entityManager.getPlayer().setAnimationShootingRight();
		}

		//DEBUG ONLY*****************************************************************************************
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
		ui.drawKeyNumber(entityManager.getPlayer().getNumberOfKeys(), g);
		ui.updateOnscreenMessage(g);
	}
	
	@Override
	public void update() {
		processInput();
		if(pauseCountdown > 0){
			pauseCountdown -= 1;
		}
		if(mapCountdown > 0){
			mapCountdown -= 1;
		}
		if(!pause) {
			if(entityManager.isBossDead()){
				win = true;
				setInactive();
				audioManager.stopSoundLoop();
			}
			if(entityManager.isGameOver()){
				win = false;
				setInactive();
				audioManager.stopSoundLoop();
			}
			entityManager.getPlayer().translateInputToAction();
			entityManager.getPlayer().updateCoolDown();
			entityManager.updateArrows();
			entityManager.updateEnemies();
			entityManager.updateHazards();
			entityManager.updateHostileProjectiles();
			entityManager.checkEntityToEntityCollisions();
			entityManager.checkItemsCollisions();
			entityManager.checkPowerUpCollision();
			if(entityManager.getPlayer().showPowerUpMessageMS){
				entityManager.getPlayer().showPowerUpMessageMS = false;
				ui.setOnscreenMessage(1);
			}
			if(entityManager.getPlayer().showPowerUpMessageLU){
				entityManager.getPlayer().showPowerUpMessageLU = false;
				ui.setOnscreenMessage(2);
			}
			if(entityManager.getPlayer().showPowerUpMessageFF){
				entityManager.getPlayer().showPowerUpMessageFF = false;
				ui.setOnscreenMessage(3);
			}
			entityManager.checkHazardsCollision();
			entityManager.checkRoomCompletion();
			int collisionID = entityManager.checkPlayerToDoorsCollisions();
			if(collisionID > 0){
				if(entityManager.entityGenerator.checkIfBossRoom(collisionID) && entityManager.getPlayer().getNumberOfKeys() < 3){
					goToStartingRoom();
					cellManager.addNewFoundRoom(cellManager.getBossRoomID());
					ui.setOnscreenMessage(0);
				}
				else {
					translateCellToNewRoom(collisionID);
				}
			}
		}
		else {
			ui.setOnscreenMessage(4);
		}
	}
}
