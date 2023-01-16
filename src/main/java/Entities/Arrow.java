package Entities;

public class Arrow extends GenericEntity {

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
		return getAxis();
	}

	public boolean getDirection() {
		return getDirection();
	}

	public int getArrowSpeed(){
		return arrowSpeed;
	}
}
