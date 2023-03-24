package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.abs;

public class Bat extends Enemy{

	private Image BAT_1;
	private Image BAT_2;
	private Image BAT_3;
	private Image BAT_4;
	private Image BAT_5;
	private Image BAT_6;
	private Image BAT_7;
	private Image BAT_8;
	private Image BAT_9;
	private Image BAT_10;
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
		BAT_1 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello1.png");
		BAT_2 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello2.png");
		BAT_3 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello3.png");
		BAT_4 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello4.png");
		BAT_5 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello5.png");
		BAT_6 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello6.png");
		BAT_7 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello7.png");
		BAT_8 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello8.png");
		BAT_9 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello9.png");
		BAT_10 = setSpriteFromPath("src/resources/sprites/Enemies/Bat/pistrello10.png");
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
					changeBehaviourTo("dashing");
				}
				case "dashing" -> {
					movingCountdown--;
					nextAnimation();
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
			case 4 ->{
				setActiveSprite(BAT_2);
			}
			case 8 ->{
				setActiveSprite(BAT_3);
			}
			case 12 ->{
				setActiveSprite(BAT_4);
			}
			case 16 ->{
				setActiveSprite(BAT_5);
			}
			case 20 ->{
				setActiveSprite(BAT_6);
			}
			case 24 ->{
				setActiveSprite(BAT_7);
			}
			case 28 ->{
				setActiveSprite(BAT_8);
			}
			case 32 ->{
				setActiveSprite(BAT_9);
			}
			case 36 ->{
				setActiveSprite(BAT_10);
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
