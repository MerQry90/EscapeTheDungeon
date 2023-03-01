package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.abs;

public class Bat extends Enemy{

	private Image LIVING_BAT_OFF;
	private Image LIVING_BAT_ON;
	private Image DEAD_BAT;
	
	private int wait, countdown, movingCountdown;
	
	public Bat(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}

	@Override
	public void init() {
		//CARICAMENTO SPRITE
		LIVING_BAT_OFF = setSpriteFromPath("src/resources/sprites/png/player_sample.png");
		LIVING_BAT_ON = setSpriteFromPath("src/resources/sprites/backgrounds/MainMenu_PlaceHolder_2.png");
		DEAD_BAT = setSpriteFromPath("src/resources/sprites/png/deadMage.png");
		setActiveSprite(LIVING_BAT_OFF);
		
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
					setActiveSprite(LIVING_BAT_ON);
					if (countdown > wait) {
						changeBehaviourTo("aiming");
					} else {
						changeBehaviourTo("stop-2");
					}
				}
				case "stop-2" -> {
					countdown++;
					setActiveSprite(LIVING_BAT_OFF);
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
					setActiveSprite(LIVING_BAT_OFF);
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
