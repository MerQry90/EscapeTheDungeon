package Components;

import Entities.DynamicEntities.*;
import Entities.StaticEntities.Door;
import Entities.GenericEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

	
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
		enemies.add(new Zombie(100, 100, this));
	}
	
	public void setNextPlayerInstruction(String instruction){
		player.setNextPlayerInstruction(instruction);
	}
	
	public Player getPlayer() {
		return player;
	}
	public int getPlayerX() {
		return player.getX();
	}
	public int getPlayerY(){
		return player.getY();
	}
	
	public boolean checkWallsCollisions(GenericEntity entity){
		return room.checkCollisions(entity);
	}
	
	public void checkDynamicCollisions(){
		/*if(door.checkIfActive() && player.checkCollision(door)){
			clearedTotalStages++;
			loadNextStage();
		}*/
		for(Enemy enemy: enemies){
			if(enemy.checkIfActive() && enemy.checkCollision(player)){
				player.setInactive();
			}
			for(Projectile arrow: friendlyArrows){
				if(enemy.checkIfActive() && enemy.checkCollision(arrow)){
					arrow.setInactive();
					enemy.lowerHealth();
				}
			}
		}
	}

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
	
	public void updateEnemies(){
		for(Enemy enemy: enemies){
			enemy.updateBehaviour();
		}
	}
	
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
	
	/*public void loadNextStage(){
		player.setY(512 - 64);
		player.setX(512);
		stage.loadRandomStage(enemies);
		door.setInactive();
	}/
	 */
	
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
