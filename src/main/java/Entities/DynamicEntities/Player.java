package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.*;

public class Player extends DynamicEntity {

	private Image LEFT_PLAYER;
	private Image INVULNERABLE_PLAYER;

	private boolean multipleShot;

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
		setCBwidthScalar(0.7);
		setCBheightScalar(0.7);
		initCollisionBox();

		translationVector2D = new Vector2D(15);
		shootCoolDown = 15;
		shootCoolDownValue = 0;
		hasShot = false;
		setCanPassThroughWalls(false);

		multipleShot = false;
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
		invulnerabilityCountdown = 40;
	}
	public boolean isVulnerable(){
		return vulnerability;
	}
	public void activateMultipleShot(){
		multipleShot = true;
	}
	public boolean getMultipleShot(){
		return multipleShot;
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

	public void translateInputToMovement() {
		boolean canMove = true;
		switch (nextPlayerInstruction){
			case "up-right" -> {
				translationVector2D.setAngulation(toRadians(315));
			}
			case "up-left" -> {
				translationVector2D.setAngulation(toRadians(225));
			}
			case "down-right" -> {
				translationVector2D.setAngulation(toRadians(45));
			}
			case "down-left" -> {
				translationVector2D.setAngulation(toRadians(135));
			}
			case "up" -> {
				translationVector2D.setAngulation(toRadians(270));
			}
			case "down" -> {
				translationVector2D.setAngulation(toRadians(90));
			}
			case "right" -> {
				translationVector2D.setAngulation(toRadians(0));
			}
			case "left" -> {
				translationVector2D.setAngulation(toRadians(180));
			}
			case "stop" -> {
				canMove = false;
			}
		}
		if(canMove) {
			moveEntity();
		}
	}
	
	@Override
	public void moveEntity() {
		int translationOnX = translationVector2D.getTranslationOnX();
		int translationOnY = translationVector2D.getTranslationOnY();
		if(translationOnX != 0) {
			int signX = translationOnX / abs(translationOnX);
			setX(getX() + translationOnX);
			while ((entityManager.checkWallsCollisions(this) && !canPassThroughWalls)||
					(entityManager.checkObstaclesCollisions(this) && !canFly)) {
				setX(getX() - signX);
			}
		}
		if(translationOnY != 0) {
			int signY = translationOnY / abs(translationOnY);
			setY(getY() + translationOnY);
			while ((entityManager.checkWallsCollisions(this) && !canPassThroughWalls)||
					(entityManager.checkObstaclesCollisions(this) && !canFly)) {
				setY(getY() - signY);
			}
		}
	}
}
