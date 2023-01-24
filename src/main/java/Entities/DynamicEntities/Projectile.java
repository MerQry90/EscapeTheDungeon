package Entities.DynamicEntities;

import Components.Background;
import Components.EntityManager;
import Entities.GenericEntity;

public abstract class Projectile extends DynamicEntity {

	/*
	axis = true: asse x
	axis = false: asse y
 	*/
	private boolean axis, direction;
	
	public void setAxis(boolean axis){
		this.axis = axis;
	}

	public void setDirection(boolean direction){
		this.direction = direction;
	}

	public boolean getAxis(){
		return axis;
	}

	public boolean getDirection(){
		return direction;
	}
	
	public void checkBoundaries(){
		if((getX() <= Background.LEFT_BOUND && axis && !direction)
				|| (getX() >= Background.RIGHT_BOUND - getWidth() && axis && direction)
				|| (getY() <= Background.UPPER_BOUND && !axis && !direction)
				|| (getY() >= Background.LOWER_BOUND - getHeight() && !axis && direction)){
			setInactive();
		}
	}
}
