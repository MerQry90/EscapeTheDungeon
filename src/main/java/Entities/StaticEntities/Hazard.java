package Entities.StaticEntities;

public abstract class Hazard extends Obstacle{
	private int duration;
	public Hazard(int x, int y, int width, int height, int duration) {
		super(x, y, width, height);
		this.duration = duration;
	}

	public void updateHazard(){
		duration -= 1;
		if(duration <= 0){
			setInactive();
		}
	}
}
