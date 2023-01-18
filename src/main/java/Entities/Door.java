package Entities;

public class Door extends GenericEntity{
	
	public Door(int x, int y) {
		super(x, y);
		setSprite("src/resources/sprites/png/player_front.png");
		setInactive();
	}
	
	@Override
	public void init() {
		setWidth(64 * 3);
		setHeight(64);
		setCBwidthScalar(1.0);
		setCBheightScalar(1.1);
	}
}
