package Components;

import Entities.DynamicEntities.*;
import Entities.DynamicEntities.Enemies.*;
import Entities.DynamicEntities.Projectiles.Arrow;
import Entities.DynamicEntities.Projectiles.Projectile;
import Entities.GenericEntity;
import Entities.StaticEntities.*;
import Entities.StaticEntities.PowerUps.Key;
import Entities.StaticEntities.PowerUps.PowerUp;
import GameStates.MainGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityManager {
	
	/*
	* link agli sprite di sharon
	* https://drive.google.com/drive/folders/1iUHRR4yvI7v8MohoRNtiPgisIckRKKzU?usp=sharing
	* */

	public MainGame mainGameReference;
	
	private Player player;
	private List<Enemy> enemies;
	private List<Arrow> friendlyArrows;
	private List<Projectile> hostileProjectiles;
	private List<Obstacle> obstacles;
	private List<HeartItem> heartItems;
	private List<PowerUp> powerUpList;
	private List<Hazard> hazards;

	public EntityGenerator entityGenerator;

	private Room room;
	
	private boolean bossHasBeenDefeated;
	private boolean roomHasBeenCompleted_actionPerformedOnce;
	
	public EntityManager(MainGame mainGameReference){
		this.mainGameReference = mainGameReference;
		bossHasBeenDefeated = false;
		roomHasBeenCompleted_actionPerformedOnce = false;
		player = new Player(this);

		enemies = new ArrayList<>();
		friendlyArrows = new ArrayList<>();
		hostileProjectiles = new ArrayList<>();
		obstacles = new ArrayList<>();
		powerUpList = new ArrayList<>();
		hazards = new ArrayList<>();

		entityGenerator = new EntityGenerator(this);
	}

	//metodi riguardanti il giocatore-----------------------------------------------------------------------------------
	public Player getPlayer() {
		return player;
	}
	public int getPlayerX() {
		return player.getX();
	}
	public int getPlayerY(){
		return player.getY();
	}
	public void setNextPlayerInstruction(String instruction){
		player.setNextPlayerInstruction(instruction);
	}
	public void setDefaultPlayerPositionUp(){
		player.setX(64 * 8);
		player.setY(64);
	}
	public void setDefaultPlayerPositionRight(){
		player.setX(64 * 15);
		player.setY(64 * 4);
	}
	public void setDefaultPlayerPositionDown(){
		player.setX(64 * 8);
		player.setY(64 * 7);
	}
	public void setDefaultPlayerPositionLeft(){
		player.setX(64);
		player.setY(64 * 4);
	}
	public void setDefaultPlayerPositionCenter(){
		player.setX(64 * 8);
		player.setY(64 * 4);
	}
	//------------------------------------------------------------------------------------------------------------------
	
	//collisioni--------------------------------------------------------------------------------------------------------
	public boolean checkWallsCollisions(GenericEntity entity){
		return room.checkWallsCollisions(entity);
	}
	
	public boolean checkObstaclesCollisions(DynamicEntity entity){
		boolean hasCollided = false;
		for(Obstacle obstacle: obstacles){
			if(entity.checkIfActive() && !entity.getCanFly() && obstacle.checkCollision(entity) &&
					!(obstacle instanceof BloodStain || obstacle instanceof SlimePuddle) && obstacle.checkIfActive()){
				if(entity.getCanBreakRocks()){
					obstacle.setInactive();
					mainGameReference.audioManager.playSoundOnce(AudioManager.ROCK_BROKEN_INDEX);
				}
				else {
					hasCollided = true;
				}
			}
		}
		return hasCollided;
	}

	public void checkHazardsCollision(){
		for (Hazard hazard: hazards){
			if(hazard.checkIfActive() && !player.getCanFly() && hazard.checkCollision(player) && player.isVulnerable()){
				player.lowerHealth();
				player.setInvulnerable();
				mainGameReference.audioManager.playSoundOnce(1);
			}
		}
	}

	public int checkPlayerToDoorsCollisions(){
		int collisionID = -1;
		if(room.hasNorthernDoor() && room.getNorthernDoor().checkCollision(player)){
			collisionID = room.getNorthernDoor().leadsTo();
		}
		else if(room.hasEasternDoor() && room.getEasternDoor().checkCollision(player)){
			collisionID = room.getEasternDoor().leadsTo();
		}
		else if(room.hasSouthernDoor() && room.getSouthernDoor().checkCollision(player)){
			collisionID = room.getSouthernDoor().leadsTo();
		}
		else if(room.hasWesternDoor() && room.getWesternDoor().checkCollision(player)){
			collisionID = room.getWesternDoor().leadsTo();
		}
		return collisionID;
	}

	public void checkEntityToEntityCollisions(){
		for(Enemy enemy: enemies){
			if(enemy.checkIfActive() && enemy.checkCollision(player) && player.isVulnerable()){
				player.lowerHealth();
				player.setInvulnerable();
				mainGameReference.audioManager.playSoundOnce(1);
			}
			for(Projectile arrow: friendlyArrows){
				if(enemy.checkIfActive() && enemy.checkCollision(arrow)){
					arrow.setInactive();
					enemy.lowerHealth();
					mainGameReference.audioManager.playSoundOnce(5);
				}
			}
		}
		for(Projectile projectile: hostileProjectiles){
			if(projectile.checkIfActive() && projectile.checkCollision(player) && player.isVulnerable()){
				player.lowerHealth();
				player.setInvulnerable();
				projectile.setInactive();
				mainGameReference.audioManager.playSoundOnce(1);
			}
		}
	}

	public void checkItemsCollisions(){
		for(int i = 0; i < heartItems.size(); i += 1){
			if (heartItems.get(i).checkIfActive() && heartItems.get(i).checkCollision(player) && (player.getHealth() < player.getMaxHealth())) {
				entityGenerator.getGroupByID(getRoomID()).getItems().remove(i);
				heartItems = entityGenerator.getGroupByID(getRoomID()).getItems();
				player.setHealth(player.getHealth() + 1);
				mainGameReference.audioManager.playSoundOnce(AudioManager.PLAYER_HEALED);
				i -= 1;
			}
		}
	}

	public void checkPowerUpCollision(){
		for(int i = 0; i < powerUpList.size(); i += 1){
			if(powerUpList.get(i).checkIfActive() && powerUpList.get(i).checkCollision(player)){
				powerUpList.get(i).activate(player);
				if(powerUpList.get(i) instanceof Key){
					mainGameReference.audioManager.playSoundOnce(AudioManager.KEY_PICKEDUP_INDEX);
				}
				else {
					mainGameReference.audioManager.playSoundOnce(AudioManager.POWERUP_PICKED_INDEX);
				}
				powerUpList.remove(i);
				i -= 1;
			}
		}
	}
	//------------------------------------------------------------------------------------------------------------------

	//gestione proiettili-----------------------------------------------------------------------------------------------
	public void newArrow(int orientation){
		// +11 perché centro del player
		friendlyArrows.add(new Arrow(getPlayerX() + 11, getPlayerY() + 11, orientation, this));
		if (player.getMultipleShot()){
			friendlyArrows.add(new Arrow(getPlayerX() + 11, getPlayerY() + 11,
					orientation + 1, this));
			friendlyArrows.add(new Arrow(getPlayerX() + 11, getPlayerY() + 11,
					orientation + 2, this));
		}
		mainGameReference.audioManager.playSoundOnce(3);
	}

	public void updateArrows(){
		for (int i = 0; i < friendlyArrows.size(); i++){
			if(friendlyArrows.get(i).checkIfActive()){
				friendlyArrows.get(i).moveEntity();
			}
			if(!friendlyArrows.get(i).checkIfActive()){
				friendlyArrows.remove(i);
				i -= 1;
			}
		}
	}
	public void newHostileProjectile(Projectile newProjectile){
		hostileProjectiles.add(newProjectile);
	}
	
	public void updateHostileProjectiles(){
		for (int i = 0; i < hostileProjectiles.size(); i++){
			if(hostileProjectiles.get(i).checkIfActive()){
				hostileProjectiles.get(i).moveEntity();
			}
			if(!hostileProjectiles.get(i).checkIfActive()){
				hostileProjectiles.remove(i);
				i -= 1;
			}
		}
	}

	public void cleanHostileProjectiles(){
		hostileProjectiles.clear();
	}
	
	public void generateSlimePuddle(int x, int y){
		hazards.add(new SlimePuddle(x, y));
	}

	public void updateHazards(){
		for (int i = 0; i < hazards.size(); i++){
			if(hazards.get(i).checkIfActive()){
				hazards.get(i).updateHazard();
			}
			else{
				hazards.remove(i);
				i -= 1;
			}
		}
	}
	//------------------------------------------------------------------------------------------------------------------

	//gestione nemici---------------------------------------------------------------------------------------------------
	public void updateEnemies(){
		for(Enemy enemy: enemies){
			enemy.updateBehaviour();
			if(enemy instanceof Tank && !enemy.checkIfActive() && enemy.canGenerateBloodStain){
				hazards.add(enemy.generateBloodStain());
			}
			if(enemy instanceof Boss && !enemy.checkIfActive()){
				bossHasBeenDefeated = true;
			}
		}
	}

	//DEBUG ONLY
	public void killAll() {
		for (Enemy enemy : enemies) {
			enemy.setInactive();
		}
	}

	//gestione stato----------------------------------------------------------------------------------------------------
	public boolean isGameOver(){
		return !player.checkIfActive();
	}
	public boolean isBossDead(){
		return bossHasBeenDefeated;
	}
	
	public void setNewRoom(int ID, int nID, int eID, int sID, int wID){
		room = new Room(ID, nID, eID, sID, wID);
		
		//Pulizia liste
		if(enemies != null){
			enemies.clear();
		}
		if(friendlyArrows != null){
			friendlyArrows.clear();
		}
		if(hostileProjectiles != null){
			hostileProjectiles.clear();
		}
		if(powerUpList != null){
			powerUpList.clear();
		}
		if(obstacles != null){
			obstacles.clear();
		}
		if(hazards != null){
			hazards.clear();
		}
		
		enemies = new ArrayList<>(entityGenerator.getGroupByID(getRoomID()).getEnemies());
		roomHasBeenCompleted_actionPerformedOnce = false;
		if(!entityGenerator.getGroupByID(getRoomID()).isDefeated()){
			powerUpList = new ArrayList<>(entityGenerator.getGroupByID(getRoomID()).getPowerUpList());
			roomHasBeenCompleted_actionPerformedOnce = true;
		}
		obstacles = new ArrayList<>(entityGenerator.getGroupByID(getRoomID()).getObstacles());
		heartItems = new ArrayList<>(entityGenerator.getGroupByID(getRoomID()).getItems());

		if(entityGenerator.checkIfBossRoom(ID)){
			mainGameReference.audioManager.stopSoundLoop();
			mainGameReference.audioManager.playSoundLoop(2);
		}
	}
	public Room getRoom(){
		return room;
	}
	public int getRoomID(){
		return room.roomID;
	}
	public void setRoomAsDeadEnd(){
		room.setAsDeadEnd();
	}
	public boolean isRoomDeadEnd() {
		return room.isDeadEnd();
	}
	
	public void checkRoomCompletion(){
		boolean completed = true;
		if(!enemies.isEmpty()){
			for(Enemy enemy: enemies){
				if (!enemy.getCurrentBehaviour().equals("dead")) {
					completed = false;
					break;
				}
			}
		}
		else if(!powerUpList.isEmpty()){
			completed = false;
		}
		if(completed){
			room.openDoors();
			if(roomHasBeenCompleted_actionPerformedOnce) {
				roomHasBeenCompleted_actionPerformedOnce = false;
				mainGameReference.audioManager.playSoundOnce(6);
				entityGenerator.generateHearts(getRoomID());
				heartItems = entityGenerator.getGroupByID(getRoomID()).getItems();
			}
			entityGenerator.getGroupByID(getRoomID()).setAsDefeated();
		}
	}
	//------------------------------------------------------------------------------------------------------------------
	
	public void renderEntities(Graphics g){
		for(HeartItem heartItem : heartItems){
			heartItem.paint(g);
		}
		for(PowerUp powerUp: powerUpList){
			powerUp.paint(g);
		}
		for (Obstacle obstacle: obstacles){
			if(obstacle.checkIfActive()) {
				obstacle.paint(g);
			}
		}
		for (Hazard hazard: hazards){
			if(hazard.checkIfActive()) {
				hazard.paint(g);
			}
		}
		for (Enemy enemy: enemies) {
			if(!enemy.checkIfActive()) {
				enemy.paint(g);
			}
		}
		for (Enemy enemy: enemies) {
			if(enemy.checkIfActive()) {
				enemy.paint(g);
			}
		}
		for (Projectile arrow: friendlyArrows){
			arrow.paint(g);
		}
		for (Projectile projectile: hostileProjectiles){
			projectile.paint(g);
		}
		room.paintDoors(g);
		player.paint(g);
	}
}
