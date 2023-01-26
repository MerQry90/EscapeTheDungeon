package Entities.DynamicEntities;

import Components.Vector2D;
import Entities.GenericEntity;

import static java.lang.Math.*;

public abstract class DynamicEntity extends GenericEntity {
	
	protected Vector2D translation;
	private boolean canFly;
	private boolean canPassThroughWalls;
	
	public int getSpeed() {
		return round((float) (translation.getModule()));
	}
	public void setSpeed(int speed) {
		translation.setModule(speed);
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
	
	public boolean moveEntity(int translationX, int translationY){
		if(translationX < 0){
			for(int i = 0; i > translationX; i--){
				if(entityManager.checkWallsCollisions(this) && !canPassThroughWalls) {
					setX(getX() + 1);
					return true;
					//break;
				}
				/*if(entityManager.checkObstaclesCollisions(this) && !canFly) {
					setX(getX() + 1);
					break;
				}*/
				setX(getX() - 1);
			}
		}
		else {
			for(int i = 0; i < translationX; i++){
				if(entityManager.checkWallsCollisions(this) && !canPassThroughWalls){
					setX(getX() - 1);
					return true;
					//break;
				}
				setX(getX() + 1);
			}
		}
		if(translationY < 0){
			for(int i = 0; i > translationY; i--){
				if(entityManager.checkWallsCollisions(this) && !canPassThroughWalls) {
					setY(getY() + 1);
					return true;
					//break;
				}
				setY(getY() - 1);
			}
		}
		else {
			for(int i = 0; i < translationY; i++){
				if(entityManager.checkWallsCollisions(this) && !canPassThroughWalls){
					setY(getY() - 1);
					return true;
					//break;
				}
				setY(getY() + 1);
			}
		}
		return false;
	}
}
