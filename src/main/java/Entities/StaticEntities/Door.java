package Entities.StaticEntities;

import Entities.GenericEntity;

import java.awt.*;

public class Door extends GenericEntity {
	
	private Image OPEN_DOOR;
	//private Image CLOSED_DOOR;
	private int leadsTo;
	
	public Door(int x, int y, double CBwidthScalar, double CBheightScalar, int leadsTo) {
		setX(x);
		setY(y);
		setCBwidthScalar(CBwidthScalar);
		setCBheightScalar(CBheightScalar);
		this.leadsTo = leadsTo;
		init();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		OPEN_DOOR = setSpriteFromPath("src/resources/sprites/png/player_front.png");
		setActiveSprite(OPEN_DOOR);
		
		setWidth(64);
		setHeight(64);
		
		activateCollisionBox();
	}
	
	public int leadsTo(){
		return leadsTo;
	}
}
