package Entities;

public class Arrow extends GenericEntity {

	//la freccia deve essere visibile finché non collide con qualcosa
	private final int arrowSpeed = 20;
	
	/*
	axis = true: asse x
	axis = false: asse y
 	*/
	private boolean axis, direction;

	public Arrow(int x, int y, boolean axis, boolean direction) {
		super(x, y, 40, 40);
		this.axis = axis;
		this.direction = direction;
		setSpeed(arrowSpeed);
		setSprite("src/resources/sprites/projectiles/arrow.png");
	}

	public boolean getAxis() {
		return axis;
	}

	public boolean getDirection() {
		return direction;
	}

	public int getArrowSpeed(){
		return arrowSpeed;
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
