package Components;

import Entities.DynamicEntities.*;
import Entities.DynamicEntities.Enemies.*;
import Entities.GenericEntity;
import Entities.StaticEntities.BloodStain;
import Entities.StaticEntities.Item;
import Entities.StaticEntities.Obstacle;
import Entities.StaticEntities.PowerUps.PowerUp;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityManager {

	private Player player;
	private List<Enemy> enemies;
	private List<Arrow> friendlyArrows;
	private List<Projectile> hostileProjectiles;
	private List<Obstacle> obstacles;
	private List<Item> items;
	private List<PowerUp> powerUpList;
	//private List<BloodStain> bloodStains;

	public EntityGenerator entityGenerator;

	private Room room;
	
	private boolean bossHasBeenDefeated;
	
	public EntityManager(){
		bossHasBeenDefeated = false;
		player = new Player(this);

		enemies = new ArrayList<>();
		friendlyArrows = new ArrayList<>();
		hostileProjectiles = new ArrayList<>();
		obstacles = new ArrayList<>();
		powerUpList = new ArrayList<>();

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
			//per le pozze di sangue
			if(obstacle instanceof BloodStain && obstacle.checkIfActive() && !player.getCanFly() && obstacle.checkCollision(player) && player.isVulnerable()){
				player.lowerHealth();
				player.setInvulnerable();
			}
			else if(entity.checkIfActive() && !entity.getCanFly() && obstacle.checkCollision(entity) && !(obstacle instanceof BloodStain) && obstacle.checkIfActive()){
				if(entity.getCanBreakRocks()){
					obstacle.setInactive();
				}
				else {
					hasCollided = true;
				}
			}
		}
		return hasCollided;
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
			}
			for(Projectile arrow: friendlyArrows){
				if(enemy.checkIfActive() && enemy.checkCollision(arrow)){
					arrow.setInactive();
					enemy.lowerHealth();
				}
			}
		}
		for(Projectile projectile: hostileProjectiles){
			if(projectile.checkIfActive() && projectile.checkCollision(player) && player.isVulnerable()){
				player.lowerHealth();
				player.setInvulnerable();
				projectile.setInactive();
			}
		}
	}

	public void checkItemsCollisions(){
		for(Item item: items) {
			if (item.checkIfActive() && item.checkCollision(player) && player.getHealth() < player.getMaxHealth()) {
				player.setHealth(player.getHealth() + 1);
				item.setInactive();
			}
		}
	}

	public void checkPowerUpCollision(){
		for(PowerUp powerUp: powerUpList){
			if(powerUp.checkIfActive() && powerUp.checkCollision(player)){
				powerUp.activate(player);
				powerUp.setInactive();
			}
		}
	}
	//------------------------------------------------------------------------------------------------------------------

	//gestione proiettili-----------------------------------------------------------------------------------------------
	public void newArrow(String orientation){
		friendlyArrows.add(new Arrow(getPlayerX() + 11, getPlayerY() + 11, orientation, this));
		if (player.getMultipleShot()){
			if(orientation.compareTo("right") == 0 || orientation.compareTo("left") == 0) {
				friendlyArrows.add(new Arrow(getPlayerX() + 11, getPlayerY() + 11 - 64, orientation, this));
				friendlyArrows.add(new Arrow(getPlayerX() + 11, getPlayerY() + 11 + 64, orientation, this));
			}
			else{
				friendlyArrows.add(new Arrow(getPlayerX() + 11 - 64, getPlayerY() + 11, orientation, this));
				friendlyArrows.add(new Arrow(getPlayerX() + 11 + 64, getPlayerY() + 11, orientation, this));
			}
		}
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
	
	public void clearProjectiles(){
		friendlyArrows.clear();
		hostileProjectiles.clear();
	}
	//------------------------------------------------------------------------------------------------------------------

	//gestione nemici---------------------------------------------------------------------------------------------------
	public void updateEnemies(){
		for(Enemy enemy: enemies){
			enemy.updateBehaviour();
			if(enemy instanceof Tank && !enemy.checkIfActive() && enemy.canGenerateBloodStain){
				obstacles.add(enemy.generateBloodStain());
			}
			if(enemy instanceof Boss && !enemy.checkIfActive()){
				bossHasBeenDefeated = true;
			}
		}
	}

	//DEBUG ONLY
	public void killAll(){
		for (Enemy enemy: enemies){
			enemy.setInactive();
		}
	}
	//------------------------------------------------------------------------------------------------------------------

	public void dropItems(){
		Random random = new Random();
		if(random.nextInt(4) == 0){
			items.add(new Item(512, 256));
		}
	}

	//gestione stato----------------------------------------------------------------------------------------------------
	
	
	public boolean isGameOver(){
		if(!player.checkIfActive()){
			return true;
		}
		return false;
	}
	public boolean isBossDead(){
		return bossHasBeenDefeated;
	}
	
	public void setNewRoom(int ID, int nID, int eID, int sID, int wID){
		room = new Room(ID, nID, eID, sID, wID);
		if (!entityGenerator.getGroupByID(getRoomID()).isDefeated()){
			enemies = entityGenerator.getGroupByID(getRoomID()).getEnemies();
		}
		obstacles = entityGenerator.getGroupByID(getRoomID()).getObstacles();
		items = entityGenerator.getGroupByID(getRoomID()).getItems();
		powerUpList = entityGenerator.getGroupByID(getRoomID()).getPowerUpList();
		clearProjectiles();
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
		for(Enemy enemy: enemies){
			if(enemy.checkIfActive()){
				completed = false;
			}
		}
		if(completed){
			entityGenerator.getGroupByID(getRoomID()).setAsDefeated();
			room.openDoors();
			if(!entityGenerator.getGroupByID(getRoomID()).isItemsDropped()){
				dropItems();
				entityGenerator.getGroupByID(getRoomID()).setItemsDropped();
			}
		}
	}
	//------------------------------------------------------------------------------------------------------------------
	
	public void renderEntities(Graphics g){
		for(Item item: items){
			if(item.checkIfActive()){
				item.paint(g);
			}
		}
		for(PowerUp powerUp: powerUpList){
			if(powerUp.checkIfActive()){
				powerUp.paint(g);
			}
		}
		room.paintDoors(g);
		for (Obstacle obstacle: obstacles){
			if(obstacle.checkIfActive()) {
				obstacle.paint(g);
			}
		}
		player.paint(g);
		for (Projectile arrow: friendlyArrows){
			arrow.paint(g);
		}
		for (Projectile projectile: hostileProjectiles){
			projectile.paint(g);
		}
		for (Enemy enemy: enemies) {
			if(enemy.checkIfActive()) {
				enemy.paint(g);
			}
		}
	}
}
