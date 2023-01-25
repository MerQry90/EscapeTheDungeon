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
	
	public void moveEntity(int tX, int tY){
		if(tX < 0){
			for(int i = 0; i > tX; i--){
				if(entityManager.checkWallsCollisions(this) && !canPassThroughWalls) {
					setX(getX() + 1);
					break;
				}
				/*if(entityManager.checkObstaclesCollisions(this) && !canFly) {
					setX(getX() + 1);
					break;
				}*/
				setX(getX() - 1);
			}
		}
		else {
			for(int i = 0; i < tX; i++){
				if(entityManager.checkWallsCollisions(this) && !canPassThroughWalls){
					setX(getX() - 1);
					break;
				}
				setX(getX() + 1);
			}
		}
		if(tY < 0){
			for(int i = 0; i > tY; i--){
				if(entityManager.checkWallsCollisions(this) && !canPassThroughWalls) {
					setY(getY() + 1);
					break;
				}
				setY(getY() - 1);
			}
		}
		else {
			for(int i = 0; i < tY; i++){
				if(entityManager.checkWallsCollisions(this) && !canPassThroughWalls){
					setY(getY() - 1);
					break;
				}
				setY(getY() + 1);
			}
		}
	}
}
