package Components;

import Entities.DynamicEntities.*;
import Entities.GenericEntity;
import Entities.StaticEntities.Item;
import Entities.StaticEntities.Obstacle;

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
	private Item heart;

	public EntityGenerator entityGenerator;

	private Room room;
	
	public EntityManager(){
		player = new Player(this);
		enemies = new ArrayList<>();
		friendlyArrows = new ArrayList<>();
		hostileProjectiles = new ArrayList<>();
		obstacles = new ArrayList<>();
		entityGenerator = new EntityGenerator(this);
		heart = null;
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
			if(entity.checkIfActive() && !entity.getCanFly() && obstacle.checkCollision(entity)){
				hasCollided = true;
			}
		}
		return hasCollided;
	}
	
	public int checkPlayerAndDoorsCollisions(){
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

	public void checkDynamicCollisions(){
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
	}
	//------------------------------------------------------------------------------------------------------------------

	//gestione frecce---------------------------------------------------------------------------------------------------
	public void newArrow(String orientation){
		friendlyArrows.add(new Arrow(getPlayerX() + 11, getPlayerY() + 11, orientation, this));
	}

	public void updateArrows(){
		for (int i = 0; i < friendlyArrows.size(); i++){
			if(friendlyArrows.get(i).checkIfActive()){
				friendlyArrows.get(i).move();
			}
			if(!friendlyArrows.get(i).checkIfActive()){
				friendlyArrows.remove(i);
				i -= 1;
			}
		}
	}
	//------------------------------------------------------------------------------------------------------------------

	//gestione nemici---------------------------------------------------------------------------------------------------
	public void updateEnemies(){
		for(Enemy enemy: enemies){
			enemy.updateBehaviour();
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
		if(random.nextBoolean()){

		}
	}

	//gestione stato----------------------------------------------------------------------------------------------------
	public boolean isGameOver(){
		if(!player.checkIfActive()){
			return true;
		}
		return false;
	}
	
	public void setNewRoom(int ID, int nID, int eID, int sID, int wID){
		room = new Room(ID, nID, eID, sID, wID);
		if (!entityGenerator.getGroupByID(getRoomID()).isDefeated()){
			enemies = entityGenerator.getGroupByID(getRoomID()).getEnemies();
		}
		obstacles = entityGenerator.getGroupByID(getRoomID()).getObstacles();
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
		}
	}
	//------------------------------------------------------------------------------------------------------------------
	
	public void renderEntities(Graphics g){
		room.paintDoors(g);
		for (Obstacle obstacle: obstacles){
			obstacle.paint(g);
		}
		player.paint(g);
		for (Projectile arrow: friendlyArrows){
			arrow.paint(g);
		}
		for (Enemy enemy: enemies) {
			if(enemy.checkIfActive()) {
				enemy.paint(g);
			}
		}
	}
}
