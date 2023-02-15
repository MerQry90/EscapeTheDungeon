package Entities.DynamicEntities;

import Components.Vector2D;
import Entities.GenericEntity;

import java.util.List;

import static java.lang.Math.*;

public abstract class DynamicEntity extends GenericEntity {
	
	private int health;
	protected Vector2D translationVector2D;
	protected boolean canFly;
	protected boolean canPassThroughWalls;
	protected boolean canBreakRocks = false;
	
	public int getSpeed() {
		return round((float) (translationVector2D.getModule()));
	}
	public void setSpeed(int speed) {
		translationVector2D.setModule(speed);
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

	public void setCanBreakRocks(){
		canBreakRocks = true;
	}

	public boolean getCanBreakRocks(){
		return canBreakRocks;
	}
	
	public abstract void moveEntity();
}
