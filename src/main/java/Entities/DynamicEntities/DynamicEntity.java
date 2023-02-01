package Entities.DynamicEntities;

import Components.Vector2D;
import Entities.GenericEntity;

import static java.lang.Math.*;

public abstract class DynamicEntity extends GenericEntity {

	private int health;
	protected Vector2D translation;
	private boolean canFly;
	private boolean canPassThroughWalls;
	
	public int getSpeed() {
		return round((float) (translation.getModule()));
	}
	public void setSpeed(int speed) {
		translation.setModule(speed);
	}

	public int getHealth(){
		return health;
	}
	public void setHealth(int health){
		this.health = health;
	}
	public void lowerHealth(){
		health -= 1;
		if(health <= 0){
			setInactive();
		}
	}
	
	public int getDeltaXToObjective(int oX){
		return oX - this.getX();
	}
	public int getDeltaYToObjective(int oY){
		return oY - this.getY();
	}
	public int getDistanceToObjective(int oX, int oY){
		return round((float) (sqrt(pow(oX - this.getX(), 2) + pow(oY - this.getY(), 2))));
	}
	
	public void setCanFly(boolean flag){
		canFly = flag;
	}
	public void setCanPassThroughWalls(boolean flag){
		canPassThroughWalls = flag;
	}

	public boolean getCanFly() {
		return canFly;
	}

	public boolean getCanPassThroughWalls() {
		return canPassThroughWalls;
	}

	public void moveEntity(int translationX, int translationY){
		if(translationX != 0) {
			int signX = translationX / abs(translationX);
			setX(getX() + translationX);
			while ((entityManager.checkWallsCollisions(this) && !canPassThroughWalls)||
					(entityManager.checkObstaclesCollisions(this) && !canFly)) {
				setX(getX() - signX);
			}
		}
		if(translationY != 0) {
			int signY = translationY / abs(translationY);
			setY(getY() + translationY);
			while ((entityManager.checkWallsCollisions(this) && !canPassThroughWalls)||
					(entityManager.checkObstaclesCollisions(this) && !canFly)) {
				setY(getY() - signY);
			}
		}
	}
}
