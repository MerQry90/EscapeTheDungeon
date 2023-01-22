package Components;

import Entities.StaticEntities.Door;
import Entities.GenericEntity;
import Entities.DynamicEntities.Player;
import Entities.DynamicEntities.Projectile;
import Entities.StaticEntities.HorizontalWall;
import Entities.StaticEntities.VerticalWall;
import Entities.DynamicEntities.Enemy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

	//private CollisionBox boundaries;
	
	private Player player;
	private Door door;
	private List<GenericEntity> boundaries;
	private List<Enemy> enemies;
	private List<Projectile> friendlyProjectiles;
	private List<Projectile> hostileProjectiles;
	//TODO lista di ostacoli
	
	public EntityManager(){
		//boundaries = new CollisionBox(64, 32, 1088 - 128, 576 + 32, 1.0, 1.0);
		
		player = new Player(this);
		door = new Door(64 * 7, 0, this);
		enemies = new ArrayList<>();
		friendlyProjectiles = new ArrayList<>();
		hostileProjectiles = new ArrayList<>();
		boundaries = new ArrayList<>();
		
		//SETTAGGIO MURI
		boundaries.add(new HorizontalWall(64, 0, this));
		boundaries.add(new HorizontalWall(64, 64 * 8, this));
		boundaries.add(new VerticalWall(0, 64, this));
		boundaries.add(new VerticalWall(64 * 16, 64, this));
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
	
	public boolean checkObstaclesCollisions(GenericEntity entity){
		for (GenericEntity obstacle: boundaries) {
			if(obstacle.checkCollision(entity)){
				return true;
			}
		}
		return false;
	}
	
	public void checkDynamicCollisions(){
		if(door.checkIfActive() && player.getCollisionBox().checkCollision(door.getCollisionBox())){
			//clearedTotalStages++;
			//loadNextStage();
		}
		for(Enemy enemy: enemies){
			if(enemy.checkIfActive() && enemy.getCollisionBox().checkCollision(player.getCollisionBox())){
				player.setInactive();
			}
			for(Projectile arrow: friendlyProjectiles){
				if(enemy.checkIfActive() && enemy.getCollisionBox().checkCollision(arrow.getCollisionBox())){
					arrow.setInactive();
					enemy.lowerHealth();
				}
			}
		}
	}
	
	public void updateArrows(){
		for(int i = 0; i < friendlyProjectiles.size(); i++){
			friendlyProjectiles.get(i).checkBoundaries();
			if (friendlyProjectiles.get(i).checkIfActive()){
				if(friendlyProjectiles.get(i).getAxis() && friendlyProjectiles.get(i).getDirection()){
					//la freccia si muove a destra
					friendlyProjectiles.get(i).calculateTranslations(friendlyProjectiles.get(i).getSpeed(), 0);
				}
				else if(friendlyProjectiles.get(i).getAxis() && friendlyProjectiles.get(i).getDirection() == false){
					//la freccia si muove a sinistra
					friendlyProjectiles.get(i).calculateTranslations(friendlyProjectiles.get(i).getSpeed() * -1, 0);
				}
				else if (friendlyProjectiles.get(i).getAxis() == false && friendlyProjectiles.get(i).getDirection()){
					//la freccia si muove verso il basso
					friendlyProjectiles.get(i).calculateTranslations(0, friendlyProjectiles.get(i).getSpeed());
				}
				else if (friendlyProjectiles.get(i).getAxis() == false && friendlyProjectiles.get(i).getDirection() == false){
					//la freccia si muove verso l'alto
					friendlyProjectiles.get(i).calculateTranslations(0, friendlyProjectiles.get(i).getSpeed() * -1);
				}
			}
			else {
				friendlyProjectiles.remove(i);
				i-= 1;
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
		for (Projectile arrow: friendlyProjectiles){
			arrow.paint(g);
		}
		for (Enemy enemy: enemies) {
			if(enemy.checkIfActive()) {
				enemy.paint(g);
			}
		}
	}
}
