package Entities.StaticEntities;

import Entities.GenericEntity;

import java.awt.*;

public class Door extends GenericEntity {
	
	private Image CLOSED_nDOOR,  CLOSED_sDOOR, CLOSED_eDOOR , CLOSED_wDOOR;
	private Image OPENED_nDOOR,  OPENED_sDOOR, OPENED_eDOOR , OPENED_wDOOR;
	private String position;
	private int leadsTo;
	
	public Door(int x, int y, double CBwidthScalar, double CBheightScalar, int leadsTo, String position) {
		setX(x);
		setY(y);
		setCBwidthScalar(CBwidthScalar);
		setCBheightScalar(CBheightScalar);
		this.leadsTo = leadsTo;
		this.position = position;
		init();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		CLOSED_nDOOR = setSpriteFromPath("src/resources/sprites/Backgrounds - Doors/Doors/closed_nDoor.png");
		CLOSED_sDOOR = setSpriteFromPath("src/resources/sprites/Backgrounds - Doors/Doors/closed_sDoor.png");
		CLOSED_eDOOR = setSpriteFromPath("src/resources/sprites/Backgrounds - Doors/Doors/closed_eDoor.png");
		CLOSED_wDOOR = setSpriteFromPath("src/resources/sprites/Backgrounds - Doors/Doors/closed_wDoor.png");

		OPENED_nDOOR = setSpriteFromPath("src/resources/sprites/Backgrounds - Doors/Doors/opened_nDoor.png");
		OPENED_sDOOR = setSpriteFromPath("src/resources/sprites/Backgrounds - Doors/Doors/opened_sDoor.png");
		OPENED_eDOOR = setSpriteFromPath("src/resources/sprites/Backgrounds - Doors/Doors/opened_eDoor.png");
		OPENED_wDOOR = setSpriteFromPath("src/resources/sprites/Backgrounds - Doors/Doors/opened_wDoor.png");

		switch (position){
			case "n" -> {
				setActiveSprite(CLOSED_nDOOR);
			}
			case "s" -> {
				setActiveSprite(CLOSED_sDOOR);
			}
			case "e" -> {
				setActiveSprite(CLOSED_eDOOR);
			}
			case "w" -> {
				setActiveSprite(CLOSED_wDOOR);
			}
		}

		setWidth(64);
		setHeight(64);
		
		activateCollisionBox();
	}

	public void openDoor(){
		switch (position){
			case "n" -> {
				setActiveSprite(OPENED_nDOOR);
			}
			case "s" -> {
				setActiveSprite(OPENED_sDOOR);
			}
			case "e" -> {
				setActiveSprite(OPENED_eDOOR);
			}
			case "w" -> {
				setActiveSprite(OPENED_wDOOR);
			}
		}
	}
	public int leadsTo(){
		return leadsTo;
	}
}
