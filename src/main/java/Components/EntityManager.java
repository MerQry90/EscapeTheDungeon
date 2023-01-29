package Components;

import Entities.DynamicEntities.*;
import Entities.GenericEntity;
import Entities.StaticEntities.Obstacle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

	/*TODO
	   fare Door e Room in entity manager,
	   così che RoomManager sia solo una struttura di dati
	   e anche per rendere più semplice l'incorporazione di
	   classi che indicano il numero di nemici e ostacoli(forse)
	*/

	private Player player;
	private List<Enemy> enemies;
	private List<Arrow> friendlyArrows;
	private List<Projectile> hostileProjectiles;
	private List<Obstacle> obstacles;

	private Room room;
	
	public EntityManager(){
		player = new Player(this);
		enemies = new ArrayList<>();
		friendlyArrows = new ArrayList<>();
		hostileProjectiles = new ArrayList<>();
		obstacles = new ArrayList<>();
		//TMP
		enemies.add(new Bat(100, 100, this));
		obstacles.add(new Obstacle(64 * 4, 64 *6, 64, 64));
		for (Obstacle obstacle: obstacles){
			obstacle.setActiveSprite(obstacle.rock);
		}
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
		for(Obstacle obstacle: obstacles){
			if(entity.checkIfActive() && !entity.getCanFly()){
				return obstacle.checkCollision(entity);
			}
		}
		return false;
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
		/*if(door.checkIfActive() && player.checkCollision(door)){
			clearedTotalStages++;
			loadNextStage();
		}*/
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

	//gestione stato----------------------------------------------------------------------------------------------------
	public boolean isGameOver(){
		if(!player.checkIfActive()){
			return true;
		}
		return false;
	}
	
	public void setNewRoom(int nID, int eID, int sID, int wID){
		room = new Room(nID, eID, sID, wID);
	}
	
	public void checkRoomCompletion(){
		boolean completed = true;
		for(Enemy enemy: enemies){
			if(enemy.checkIfActive()){
				completed = false;
			}
		}
		if(completed){
			room.openDoors();
		}
	}
	//------------------------------------------------------------------------------------------------------------------
	
	public void renderEntities(Graphics g){
		room.paintDoors(g);
		player.paint(g);
		for (Projectile arrow: friendlyArrows){
			arrow.paint(g);
		}
		for (Obstacle obstacle: obstacles){
			obstacle.paint(g);
		}
		for (Enemy enemy: enemies) {
			if(enemy.checkIfActive()) {
				enemy.paint(g);
			}
		}
	}
}
