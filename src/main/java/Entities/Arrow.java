package Entities;

public class Arrow extends Projectiles {

	//la freccia deve essere visibile finché non collide con qualcosa
	private final int arrowSpeed = 20;

	private boolean axis, direction;

	public Arrow(int x, int y, boolean axis, boolean direction) {
		setX(x);
		setY(y);
		this.axis = axis;
		this.direction = direction;
		setSpeed(arrowSpeed);
		setSprite("src/resources/sprites/projectiles/arrow.png");
		setWidth(40);
		setHeight(40);
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
		if(getX() <= LEFT_BOUND + 1|| getX() >= RIGHT_BOUND - 1){
			kill();
		}
		else if(getY() <= UPPER_BOUND + 1 || getY() >= LOWER_BOUND - 1){
			kill();
		}
	}
}
