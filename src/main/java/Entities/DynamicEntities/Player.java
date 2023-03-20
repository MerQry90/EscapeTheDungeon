package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.*;

public class Player extends DynamicEntity {

	private Image PLAYER_RIGHT_1;
	private Image PLAYER_RIGHT_2;
	private Image PLAYER_RIGHT_3;
	private Image PLAYER_RIGHT_4;
	private Image PLAYER_RIGHT_5;
	private Image PLAYER_RIGHT_6;
	private Image PLAYER_RIGHT_7;
	private Image PLAYER_RIGHT_8_IDLE;
	
	private Image PLAYER_LEFT_1;
	private Image PLAYER_LEFT_2;
	private Image PLAYER_LEFT_3;
	private Image PLAYER_LEFT_4;
	private Image PLAYER_LEFT_5;
	private Image PLAYER_LEFT_6;
	private Image PLAYER_LEFT_7;
	private Image PLAYER_LEFT_8_IDLE;
	
	//tmp
	//private Image LEFT_PLAYER;
	private Image INVULNERABLE_PLAYER;

	private boolean multipleShot;

	private String nextPlayerInstruction = "stop";
	private int shootCoolDown;
	private boolean vulnerability;
	private boolean isFacingRight;
	private int animationIndex;
	private int invulnerabilityCountdown;
	private int maxHealth;
	private int numberOfKeys;

	public Player(EntityManager entityManager) {
		this.entityManager = entityManager;
		setX(512);
		setY(256);
		init();
	}
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		
		PLAYER_RIGHT_1 = setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0001.png");
		PLAYER_RIGHT_2 = setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0002.png");
		PLAYER_RIGHT_3 = setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0003.png");
		PLAYER_RIGHT_4 = setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0004.png");
		PLAYER_RIGHT_5 = setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0005.png");
		PLAYER_RIGHT_6 = setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0006.png");
		PLAYER_RIGHT_7 = setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0007.png");
		PLAYER_RIGHT_8_IDLE = setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0008.png");
		
		PLAYER_LEFT_1 = setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim12.png");
		PLAYER_LEFT_2 = setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim13.png");
		PLAYER_LEFT_3 = setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim14.png");
		PLAYER_LEFT_4 = setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim15.png");
		PLAYER_LEFT_5 = setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim16.png");
		PLAYER_LEFT_6 = setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim17.png");
		PLAYER_LEFT_7 = setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim18.png");
		PLAYER_LEFT_8_IDLE = setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim19.png");
		
		//LEFT_PLAYER = setSpriteFromPath("src/resources/sprites/png/player_front.png");
		INVULNERABLE_PLAYER = setSpriteFromPath("src/resources/sprites/png/player_invulnerable.png");
		isFacingRight = false;
		animationIndex = 0;
		setIdleAnimation();

		maxHealth = 3;
		setHealth(maxHealth);
		vulnerability = true;

		setX(512);
		setY(256);
		setHeight(64); //tmp
		setWidth(64); //tmp
		setCBwidthScalar(0.7);
		setCBheightScalar(0.7);
		activateCollisionBox();

		translationVector2D = new Vector2D(10);
		shootCoolDown = 15;
		setCanPassThroughWalls(false);

		multipleShot = false;
		numberOfKeys = 0;
	}
	
	public void addKey(){
		numberOfKeys += 1;
	}
	public int getNumberOfKeys(){
		return numberOfKeys;
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

	//Metodi per la gestione del cool down del player------------------------------------------------------------------
	public void updateCoolDown(){
		shootCoolDown -= 1;
		invulnerabilityCountdown -= 1;
		if(invulnerabilityCountdown <= 0){
			vulnerability = true;
		}
	}
	
	public boolean canShoot() {
		if(shootCoolDown < 0){
			shootCoolDown = 15;
			return true;
		}
		return false;
	}
	//------------------------------------------------------------------------------------------------------------------

	//Movimento del giocatore-------------------------------------------------------------------------------------------
	public void setIdleAnimation(){
		if(isFacingRight){
			setActiveSprite(PLAYER_RIGHT_8_IDLE);
		}
		else {
			setActiveSprite(PLAYER_LEFT_8_IDLE);
		}
		animationIndex = 0;
	}
	public void nextAnimationRight(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 -> {
				setActiveSprite(PLAYER_RIGHT_1);
			}
			case 4 -> {
				setActiveSprite(PLAYER_RIGHT_2);
			}
			case 8 -> {
				setActiveSprite(PLAYER_RIGHT_3);
			}
			case 12 -> {
				setActiveSprite(PLAYER_RIGHT_4);
			}
			case 16 -> {
				setActiveSprite(PLAYER_RIGHT_5);
			}
			case 20 -> {
				setActiveSprite(PLAYER_RIGHT_6);
			}
			case 24 -> {
				setActiveSprite(PLAYER_RIGHT_7);
			}
			case 28 -> {
				setActiveSprite(PLAYER_RIGHT_8_IDLE);
				animationIndex = 0;
			}
		}
	}
	public void nextAnimationLeft(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 -> {
				setActiveSprite(PLAYER_LEFT_1);
			}
			case 4 -> {
				setActiveSprite(PLAYER_LEFT_2);
			}
			case 8 -> {
				setActiveSprite(PLAYER_LEFT_3);
			}
			case 12 -> {
				setActiveSprite(PLAYER_LEFT_4);
			}
			case 16 -> {
				setActiveSprite(PLAYER_LEFT_5);
			}
			case 20 -> {
				setActiveSprite(PLAYER_LEFT_6);
			}
			case 24 -> {
				setActiveSprite(PLAYER_LEFT_7);
			}
			case 28 -> {
				setActiveSprite(PLAYER_LEFT_8_IDLE);
				animationIndex = 0;
			}
		}
	}
	
	public void setNextPlayerInstruction(String nextPlayerInstruction) {
		this.nextPlayerInstruction = nextPlayerInstruction;
	}

	public void translateInputToMovement() {
		boolean canMove = true;
		switch (nextPlayerInstruction){
			case "up-right" -> {
				translationVector2D.setAngulation(toRadians(315));
				nextAnimationRight();
				isFacingRight = true;
			}
			case "up-left" -> {
				translationVector2D.setAngulation(toRadians(225));
				nextAnimationLeft();
				isFacingRight = false;
			}
			case "down-right" -> {
				translationVector2D.setAngulation(toRadians(45));
				nextAnimationRight();
				isFacingRight = true;
			}
			case "down-left" -> {
				translationVector2D.setAngulation(toRadians(135));
				nextAnimationLeft();
				isFacingRight = false;
			}
			case "up" -> {
				translationVector2D.setAngulation(toRadians(270));
				if(isFacingRight){
					nextAnimationRight();
				}
				else {
					nextAnimationLeft();
				}
			}
			case "down" -> {
				translationVector2D.setAngulation(toRadians(90));
				if(isFacingRight){
					nextAnimationRight();
				}
				else {
					nextAnimationLeft();
				}
			}
			case "right" -> {
				translationVector2D.setAngulation(toRadians(0));
				nextAnimationRight();
				isFacingRight = true;
			}
			case "left" -> {
				translationVector2D.setAngulation(toRadians(180));
				nextAnimationLeft();
				isFacingRight = false;
			}
			case "stop" -> {
				canMove = false;
				setIdleAnimation();
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
