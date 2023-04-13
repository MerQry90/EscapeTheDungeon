package Entities.StaticEntities;

public abstract class Hazard extends Obstacle{
	private int duration;
	int index;
	int x, y;
	public Hazard(int x, int y, int width, int height, int duration) {
		super(x, y, width, height);
		this.duration = duration;
		index = 1;
		this.x = x;
		this.y = y;
		setWidth(1);
		setHeight(1);
	}

	public void updateHazard(){
		duration -= 1;
		setWidth(getWidth() + 1);
		setHeight(getHeight() + 1);
		if(duration % 2 == 0) {
			x = getX() - 1;
			y = getY() - 1;
		}
		setX(x);
		setY(y);
		if(duration <= 0){
			setInactive();
		}
	}
}
