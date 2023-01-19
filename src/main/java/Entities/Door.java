package Entities;

import java.awt.*;

public class Door extends GenericEntity{
	
	private Image OPEN_DOOR;
	//private Image CLOSED_DOOR;
	
	public Door(int x, int y) {
		super(x, y);
		setInactive();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		OPEN_DOOR = setSpriteFromPath("src/resources/sprites/png/player_front.png");
		setActiveSprite(OPEN_DOOR);
		
		setWidth(64 * 3);
		setHeight(64);
		setCBwidthScalar(1.0);
		setCBheightScalar(1.3);
	}
}
