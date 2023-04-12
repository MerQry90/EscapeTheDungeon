package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Player extends DynamicEntity {

	private ArrayList<Image> playerLeftSprites;
	private ArrayList<Image> playerRightSprites;
	private Image INVULNERABLE_PLAYER;

	private boolean multipleShot;

	private String nextPlayerInstruction = "stop";
	private int shootCoolDown;
	private boolean vulnerability;
	private boolean isFacingRight;
	private boolean isShootingRight;
	private boolean isShootingLeft;
	private int timeGateBackwardsShooting;
	private int animationIndex;
	private int invulnerabilityCountdown;
	private int maxHealth;
	private int numberOfKeys;
	
	public boolean showPowerUpMessageMS;
	public boolean showPowerUpMessageLU;
	public boolean showPowerUpMessageFF;

	public Player(EntityManager entityManager) {
		this.entityManager = entityManager;
		setX(512);
		setY(256);
		init();
	}
	@Override
	public void init() {
		playerLeftSprites = new ArrayList<>();
		playerRightSprites = new ArrayList<>();

		//CARICAMENTO SPRITE
		playerLeftSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim12.png"));
		playerLeftSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim13.png"));
		playerLeftSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim14.png"));
		playerLeftSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim15.png"));
		playerLeftSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim16.png"));
		playerLeftSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim17.png"));
		playerLeftSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim18.png"));
		playerLeftSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/sx/pg_sim19.png"));

		playerRightSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0001.png"));
		playerRightSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0002.png"));
		playerRightSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0003.png"));
		playerRightSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0004.png"));
		playerRightSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0005.png"));
		playerRightSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0006.png"));
		playerRightSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0007.png"));
		playerRightSprites.add(setSpriteFromPath("src/resources/sprites/MainCharacter/dx/Sprite-0008.png"));

		INVULNERABLE_PLAYER = setSpriteFromPath("src/resources/sprites/png/player_front.png");
		isFacingRight = false;
		setAnimationNotShooting();
		animationIndex = 0;
		vulnerability = true;
		setIdleAnimation();

		maxHealth = 3;
		setHealth(maxHealth);


		setX(512);
		setY(256);
		setHeight(64);
		setWidth(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.7);
		activateCollisionBox();

		translationVector2D = new Vector2D(10);
		shootCoolDown = 15;
		setCanPassThroughWalls(false);

		multipleShot = false;
		numberOfKeys = 0;
		
		showPowerUpMessageMS = false;
		showPowerUpMessageLU = false;
		showPowerUpMessageFF = false;
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

	//Metodi per la gestione del cool down del player-------------------------------------------------------------------
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
	public void setAnimationShootingRight(){
		isShootingRight = true;
		isShootingLeft = false;
		if(!isFacingRight){
			resetTimeGateBackwardsShooting();
		}
	}
	public void setAnimationShootingLeft(){
		isShootingLeft = true;
		isShootingRight = false;
		if(isFacingRight){
			resetTimeGateBackwardsShooting();
		}
	}
	public void setAnimationNotShooting(){
		isShootingRight = false;
		isShootingLeft = false;
	}
	public void resetTimeGateBackwardsShooting(){
		timeGateBackwardsShooting = 12;
	}
	public void stopBackwardShooting(){
		timeGateBackwardsShooting = 0;
	}
	public boolean checkBackwardShooting(){
		//System.out.println(timeGateBackwardsShooting);
		if(timeGateBackwardsShooting > 0){
			return false;
		}
		return true;
	}
	public void updateBackwardShooting(){
		if(timeGateBackwardsShooting > 0){
			timeGateBackwardsShooting -= 1;
		}
	}
	
	public void setIdleAnimation(){
		if(!isVulnerable()){
			setActiveSprite(INVULNERABLE_PLAYER);
		}
		else {
			if(isShootingRight){
				setActiveSprite(playerRightSprites.get(7));
				isFacingRight = true;
			}
			else if(isShootingLeft){
				setActiveSprite(playerLeftSprites.get(7));
				isFacingRight = false;
			}
			else if(isFacingRight){
				setActiveSprite(playerRightSprites.get(7));
			}
			else {
				setActiveSprite(playerLeftSprites.get(7));
			}
		}
		animationIndex = 0;
	}
	public void nextAnimationRight(){
		if(!isVulnerable()){
			setActiveSprite(INVULNERABLE_PLAYER);
		}
		else {
			if(checkBackwardShooting()){
				animationIndex -= 1;
				if(animationIndex <= -4){
					animationIndex = 28;
				}
			}
			else {
				animationIndex += 1;
				if(animationIndex >= 32){
					animationIndex = 0;
				}
			}
			switch (animationIndex) {
				case 0 -> {
					setActiveSprite(playerRightSprites.get(0));
				}
				case 4 -> {
					setActiveSprite(playerRightSprites.get(1));
				}
				case 8 -> {
					setActiveSprite(playerRightSprites.get(2));
				}
				case 12 -> {
					setActiveSprite(playerRightSprites.get(3));
				}
				case 16 -> {
					setActiveSprite(playerRightSprites.get(4));
				}
				case 20 -> {
					setActiveSprite(playerRightSprites.get(5));
				}
				case 24 -> {
					setActiveSprite(playerRightSprites.get(6));
				}
				case 28 -> {
					setActiveSprite(playerRightSprites.get(7));
				}
			}
		}
	}
	public void nextAnimationLeft(){
		if(!isVulnerable()){
			setActiveSprite(INVULNERABLE_PLAYER);
		}
		else {
			if(checkBackwardShooting()){
				animationIndex -= 1;
				if(animationIndex <= -4){
					animationIndex = 28;
				}
			}
			else {
				animationIndex += 1;
				if(animationIndex >= 32){
					animationIndex = 0;
				}
			}
			switch (animationIndex) {
				case 0 -> {
					setActiveSprite(playerLeftSprites.get(0));
				}
				case 4 -> {
					setActiveSprite(playerLeftSprites.get(1));
				}
				case 8 -> {
					setActiveSprite(playerLeftSprites.get(2));
				}
				case 12 -> {
					setActiveSprite(playerLeftSprites.get(3));
				}
				case 16 -> {
					setActiveSprite(playerLeftSprites.get(4));
				}
				case 20 -> {
					setActiveSprite(playerLeftSprites.get(5));
				}
				case 24 -> {
					setActiveSprite(playerLeftSprites.get(6));
				}
				case 28 -> {
					setActiveSprite(playerLeftSprites.get(7));
				}
			}
		}
	}
	
	public void setNextPlayerInstruction(String nextPlayerInstruction) {
		this.nextPlayerInstruction = nextPlayerInstruction;
	}

	public void translateInputToAction() {
		boolean canMove = true;
		switch (nextPlayerInstruction){
			case "up-right" -> {
				translationVector2D.setAngulation(toRadians(315));
				if(checkBackwardShooting()) {
					nextAnimationRight();
				}
				else {
					nextAnimationLeft();
				}
				isFacingRight = true;
			}
			case "up-left" -> {
				translationVector2D.setAngulation(toRadians(225));
				if(checkBackwardShooting()) {
					nextAnimationLeft();
				}
				else {
					nextAnimationRight();
				}
				isFacingRight = false;
			}
			case "down-right" -> {
				translationVector2D.setAngulation(toRadians(45));
				if(checkBackwardShooting()) {
					nextAnimationRight();
				}
				else {
					nextAnimationLeft();
				}
				isFacingRight = true;
			}
			case "down-left" -> {
				translationVector2D.setAngulation(toRadians(135));
				if(checkBackwardShooting()) {
					nextAnimationLeft();
				}
				else {
					nextAnimationRight();
				}
				isFacingRight = false;
			}
			case "up" -> {
				translationVector2D.setAngulation(toRadians(270));
				if(isFacingRight){
					if(checkBackwardShooting()) {
						nextAnimationRight();
					}
					else {
						nextAnimationLeft();
					}
				}
				else {
					if(checkBackwardShooting()) {
						nextAnimationLeft();
					}
					else {
						nextAnimationRight();
					}
				}
			}
			case "down" -> {
				translationVector2D.setAngulation(toRadians(90));
				if(isFacingRight){
					if(checkBackwardShooting()) {
						nextAnimationRight();
					}
					else {
						nextAnimationLeft();
					}
				}
				else {
					if(checkBackwardShooting()) {
						nextAnimationLeft();
					}
					else {
						nextAnimationRight();
					}
				}
			}
			case "right" -> {
				translationVector2D.setAngulation(toRadians(0));
				if(checkBackwardShooting()) {
					nextAnimationRight();
				}
				else {
					nextAnimationLeft();
				}
				isFacingRight = true;
			}
			case "left" -> {
				translationVector2D.setAngulation(toRadians(180));
				if(checkBackwardShooting()) {
					nextAnimationLeft();
				}
				else {
					nextAnimationRight();
				}
				isFacingRight = false;
			}
			case "stop" -> {
				canMove = false;
				setIdleAnimation();
				//stopBackwardShooting();
			}
		}
		if(canMove) {
			moveEntity();
		}
		setAnimationNotShooting();
		updateBackwardShooting();
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
