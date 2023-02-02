package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

public class Player extends DynamicEntity {

	private Image LEFT_PLAYER;
	private Image INVULNERABLE_PLAYER;


	private String nextPlayerInstruction = "stop";
	private int shootCoolDown; //valore fisso che indica ogni quanti frame il giocatore può sparare una freccia
	private int shootCoolDownValue; //valore che incrementa
	private boolean hasShot;
	private boolean vulnerability;
	private int invulnerabilityCountdown;
	private int maxHealth;

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
		INVULNERABLE_PLAYER = setSpriteFromPath("src/resources/sprites/png/player_invulnerable.png");
		setActiveSprite(LEFT_PLAYER);

		maxHealth = 3;
		setHealth(maxHealth);
		vulnerability = true;

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

	//Getter e setter metodi specifici del giocatore--------------------------------------------------------------------
	public int getMaxHealth(){
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setInvulnerable(){
		vulnerability = false;
		setActiveSprite(INVULNERABLE_PLAYER);
		invulnerabilityCountdown = 25;
	}
	public boolean isVulnerable(){
		return vulnerability;
	}
	//------------------------------------------------------------------------------------------------------------------

	//Metodi per la gestione del cool down per sparare------------------------------------------------------------------
	public void updateCoolDown(){
		shootCoolDownValue += 1;
		invulnerabilityCountdown --;
		if(invulnerabilityCountdown <= 0){
			setActiveSprite(LEFT_PLAYER);
			vulnerability = true;
		}
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
	//------------------------------------------------------------------------------------------------------------------

	//Movimento del giocatore-------------------------------------------------------------------------------------------
	public void setNextPlayerInstruction(String nextPlayerInstruction) {
		this.nextPlayerInstruction = nextPlayerInstruction;
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
