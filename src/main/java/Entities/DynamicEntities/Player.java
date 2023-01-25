package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;
import Entities.GenericEntity;

import java.awt.*;

public class Player extends DynamicEntity {

	private Image LEFT_PLAYER;
	
	private String nextPlayerInstruction = "stop";
	private int shootCoolDown; //valore fisso che indica ogni quanti frame il giocatore può sparare una freccia
	private int shootCoolDownValue; //valore che incrementa
	private boolean hasShot;

	public Player(EntityManager entityManager) {
		this.entityManager = entityManager;
		setX(512);
		setY(256);
		init();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		LEFT_PLAYER = setSpriteFromPath("src/resources/sprites/png/player_front.png");
		setActiveSprite(LEFT_PLAYER);

		setX(512);
		setY(256);
		setHeight(64); //tmp
		setWidth(64); //tmp
		setCBwidthScalar(0.8);
		setCBheightScalar(0.8);
		initCollisionBox();
		
		translation = new Vector2D(10);
		shootCoolDown = 15;
		shootCoolDownValue = 0;
		hasShot = false;
		setCanPassThroughWalls(false);
	}

	public void updateCoolDown(){
		shootCoolDownValue += 1;
	}
	
	public void setNextPlayerInstruction(String nextPlayerInstruction) {
		this.nextPlayerInstruction = nextPlayerInstruction;
	}
	
	public boolean canShoot() {
		if (hasShot) {
			if (shootCoolDown - shootCoolDownValue > 0) {
				//shootCoolDownValue += 1;
			}
			else {
				hasShot = false;
				shootCoolDownValue = 0;
			}
			return false;
		}
		//hasShoot = false, quindi il player può sparare
		else {
			hasShot = true;
			shootCoolDownValue = 0;
			return true;
		}
	}
	
	public void move() {
		boolean canMove = true;
		switch (nextPlayerInstruction){
			case "up-right" -> {
				translation.setAngulationToObjective(1, -1);
			}
			case "up-left" -> {
				translation.setAngulationToObjective(-1, -1);
			}
			case "down-right" -> {
				translation.setAngulationToObjective(1, 1);
			}
			case "down-left" -> {
				translation.setAngulationToObjective(-1, 1);
			}
			case "up" -> {
				translation.setAngulationToObjective(0, -1);
			}
			case "down" -> {
				translation.setAngulationToObjective(0, 1);
			}
			case "right" -> {
				translation.setAngulationToObjective(1, 0);
			}
			case "left" -> {
				translation.setAngulationToObjective(-1, 0);
			}
			case "stop" -> {
				translation.setAngulationToObjective(0, 0);
				canMove = false;
			}
		}
		if(canMove) {
			moveEntity(translation.getXTranslation(), translation.getYTranslation());
		}
	}
}
