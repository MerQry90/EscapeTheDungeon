package Entities;

public class Arrow extends Entity{

	//la freccia deve essere visibile finché non collide con qualcosa
	private boolean visible;

	public Arrow(int x, int y, int speed) {
		setX(x);
		setY(y);
		setSpeed(speed);
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
}
