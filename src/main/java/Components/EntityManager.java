package Components;

import Entities.DynamicEntities.*;
import Entities.StaticEntities.Door;
import Entities.GenericEntity;

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
	
	private Room room;
	
	public EntityManager(){
		player = new Player(this);
		enemies = new ArrayList<>();
		friendlyArrows = new ArrayList<>();
		hostileProjectiles = new ArrayList<>();
		
		room = new Room(true, true, true, true);
		
		//TMP
		enemies.add(new Bat(100, 100, this));
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
	//------------------------------------------------------------------------------------------------------------------

	//collisioni--------------------------------------------------------------------------------------------------------
	public boolean checkWallsCollisions(GenericEntity entity){
		return room.checkCollisions(entity);
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

	public boolean checkStageCompletion(){
		for(Enemy enemy: enemies){
			if(enemy.checkIfActive()){
				return false;
			}
		}
		room.openDoors();
		return true;
	}
	//------------------------------------------------------------------------------------------------------------------
	
	public void renderEntities(Graphics g){
		room.paintDoors(g);
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
