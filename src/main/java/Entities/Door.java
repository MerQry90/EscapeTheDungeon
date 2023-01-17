package Entities;

public class Door extends GenericEntity{
	
	public Door(int x, int y, int width, int height) {
		super(x, y, width, height);
		setSprite("src/resources/sprites/png/player_front.png");
		setInactive();
	}
}
