package Entities;

public abstract class Projectiles extends GenericEntity {

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
}
