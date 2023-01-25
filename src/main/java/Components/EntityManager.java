package Components;

import Entities.DynamicEntities.*;
import Entities.StaticEntities.Door;
import Entities.GenericEntity;
import Entities.StaticEntities.HorizontalWall;
import Entities.StaticEntities.VerticalWall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

	//private CollisionBox boundaries;
	
	private Player player;
	private Door door;
	private List<GenericEntity> boundaries;
	private List<Enemy> enemies;
	private List<Arrow> friendlyArrows;
	private List<Projectile> hostileProjectiles;
	//TODO lista di ostacoli
	
	public EntityManager(){
		//boundaries = new CollisionBox(64, 32, 1088 - 128, 576 + 32, 1.0, 1.0);
		
		player = new Player(this);
		door = new Door(64 * 7, 0, this);
		enemies = new ArrayList<>();
		friendlyArrows = new ArrayList<>();
		hostileProjectiles = new ArrayList<>();
		boundaries = new ArrayList<>();
		
		//SETTAGGIO MURI
		boundaries.add(new HorizontalWall(64, 0, this));
		boundaries.add(new HorizontalWall(64, 64 * 8, this));
		boundaries.add(new VerticalWall(0, 64, this)); //////
		boundaries.add(new VerticalWall(64 * 16, 64, this));
		
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
		for (GenericEntity obstacle: boundaries) {
			if(obstacle.checkCollision(entity)){
				return true;
			}
		}
		return false;
	}
	
	public void checkDynamicCollisions(){
		if(door.checkIfActive() && player.checkCollision(door)){
			//clearedTotalStages++;
			//loadNextStage();
		}
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
		friendlyArrows.add(new Arrow(getPlayerX(), getPlayerY(), orientation, this));
	}

	public void updateArrows(){
		for (int i = 0; i < friendlyArrows.size(); i++){
			System.out.println(checkWallsCollisions(friendlyArrows.get(i)));
			System.out.println(friendlyArrows.get(i).getX());
			if(friendlyArrows.get(i).checkIfActive()){
				friendlyArrows.get(i).move();
			}
			else {
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
		if(door.checkIfActive()){
			door.paint(g);
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
