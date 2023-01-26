package Entities.StaticEntities;

import Components.EntityManager;
import Entities.GenericEntity;

import java.awt.*;

public class Door extends GenericEntity {
	
	private Image OPEN_DOOR;
	//private Image CLOSED_DOOR;
	
	public Door(int x, int y, double CBwidthScalar, double CBheightScalar) {
		setX(x);
		setY(y);
		setCBwidthScalar(CBwidthScalar);
		setCBheightScalar(CBheightScalar);
		
		setInactive();
		init();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		OPEN_DOOR = setSpriteFromPath("src/resources/sprites/png/player_front.png");
		setActiveSprite(OPEN_DOOR);
		
		setWidth(64);
		setHeight(64);
	}
}
