package Entities;

public class Arrow extends Entity{

	//la freccia deve essere visibile finché non collide con qualcosa
	private boolean visible;
	private final int arrowSpeed = 20;

	/*
	axis = true: asse x
	axis = false: asse y
	 */
	private boolean axis, direction;

	public Arrow(int x, int y, boolean axis, boolean direction) {
		setX(x);
		setY(y);
		this.axis = axis;
		this.direction = direction;
		setSpeed(arrowSpeed);
		setVisible(true);
		setSprite("src/resources/sprites/projectiles/arrow.png");
		setWidth(32);
		setHeight(32);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getAxis() {
		return axis;
	}

	public boolean getDirection() {
		return direction;
	}
}
