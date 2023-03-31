package Entities.DynamicEntities.Enemies;

import Components.AudioManager;
import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.abs;

public class Bat extends Enemy{

	private Image BAT_1, BAT_2, BAT_3, BAT_4, BAT_5, BAT_6, BAT_7, BAT_8, BAT_9, BAT_10;
	private Image BATBALL_1, BATBALL_2, BATBALL_3, BATBALL_4, BATBALL_5, BATBALL_6, BATBALL_7, BATBALL_8, BATBALL_9;
	private Image DEAD_BAT;
	
	private int wait, countdown, movingCountdown, animationIndex;
	
	public Bat(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}

	@Override
	public void init() {
		//CARICAMENTO SPRITE
		BAT_1 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello1.png");
		BAT_2 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello2.png");
		BAT_3 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello3.png");
		BAT_4 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello4.png");
		BAT_5 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello5.png");
		BAT_6 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello6.png");
		BAT_7 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello7.png");
		BAT_8 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello8.png");
		BAT_9 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello9.png");
		BAT_10 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello10.png");

		BATBALL_1 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello4.png");
		BATBALL_2 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello5.png");
		BATBALL_3 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello6.png");
		BATBALL_4 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello7.png");
		BATBALL_5 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello8.png");
		BATBALL_6 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello9.png");
		/*BATBALL_7 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello10.png");
		BATBALL_8 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello11.png");
		BATBALL_9 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello12.png");*/

		DEAD_BAT = setSpriteFromPath("src/resources/sprites/png/deadMage.png");
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(0);
		setRandomSpeed(1, 30);
		setRandomHealth(2, 1);
		setCanPassThroughWalls(false);
		setCanFly(true);
		wait = 40;
		countdown = 0;
		movingCountdown = 20;
		animationIndex = 0;
		setActiveSprite(BAT_1);
	}

	@Override
	public void updateBehaviour() {
		if(checkActivation()) {
			if(!checkIfActive()){
				changeBehaviourTo("dead");
			}
			switch (getCurrentBehaviour()) {
				case "stop-1" -> {
					countdown++;
					nextAnimation();
					if (countdown > wait) {
						changeBehaviourTo("aiming");
					} else {
						changeBehaviourTo("stop-2");
					}
				}
				case "stop-2" -> {
					countdown++;
					nextAnimation();
					if (countdown > wait) {
						changeBehaviourTo("aiming");
					} else {
						changeBehaviourTo("stop-1");
					}
				}
				case "aiming" -> {
					int dX = getDeltaXToObjective(entityManager.getPlayerX());
					int dY = getDeltaYToObjective(entityManager.getPlayerY());
					translationVector2D.setAngulationFromCoordinates(dX, dY);
					entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.BAT_SOUND_INDEX);
					changeBehaviourTo("dashing");
				}
				case "dashing" -> {
					movingCountdown--;
					setActiveSprite(BATBALL_6);
					moveEntity();
					if (movingCountdown <= 0) {
						countdown = 0;
						movingCountdown = 20;
					}
					if (countdown <= wait) {
						changeBehaviourTo("stop-1");
					}
				}
				case "dead" -> {
					disableCollisionBox();
					setActiveSprite(DEAD_BAT);
				}
				default -> changeBehaviourTo("stop-1");
			}
		}
	}

	public void nextAnimation(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 ->{
				setActiveSprite(BAT_1);
			}
			case 3 ->{
				setActiveSprite(BAT_2);
			}
			case 6 ->{
				setActiveSprite(BAT_3);
			}
			case 9 ->{
				setActiveSprite(BAT_4);
			}
			case 12 ->{
				setActiveSprite(BAT_5);
			}
			case 15 ->{
				setActiveSprite(BAT_6);
			}
			case 18 ->{
				setActiveSprite(BAT_7);
			}
			case 21 ->{
				setActiveSprite(BAT_8);
			}
			case 24 ->{
				setActiveSprite(BAT_9);
			}
			case 27 ->{
				setActiveSprite(BAT_10);
			}
			case 30 ->{
				setActiveSprite(BATBALL_1);
			}
			case 33 ->{
				setActiveSprite(BATBALL_2);
			}
			case 36 ->{
				setActiveSprite(BATBALL_3);
			}
			case 39 ->{
				setActiveSprite(BATBALL_4);
			}
			case 42 ->{
				setActiveSprite(BATBALL_5);
				animationIndex = 0;
			}
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
