package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Type of enemy, it has a 50% chance to move towards the player,
 * or 50% chance to move randomly.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Crab extends Enemy{
	
	private int wait, countdown, movingCountdown;
	private Image DEAD_FIFTYFIFTY;
	
	private int animationCountDown;
	private int animationIndex;
	private Image[] crabSprites;

	/**
	 * Initializes sprites, animations, speed, health, CollisionBox and the parameters needed to move the entity.
	 * @param x Coordinate where the entity will be initially located
	 * @param y Coordinate where the entity will be initially located
	 * @param entityManager Necessary to check collisions with other entities
	 */
	public Crab(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		crabSprites = new Image[4];
		crabSprites[0] = setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Crab/granchio1.png")));
		crabSprites[1] = setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Crab/granchio2.png")));
		crabSprites[2] = setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Crab/granchio3.png")));
		crabSprites[3] = setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Crab/granchio4.png")));
		DEAD_FIFTYFIFTY = setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Items/BloodPots/sanguepiccolo.png")));
		setActiveSprite(crabSprites[0]);
		
		setWidth(48);
		setHeight(48);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(20);
		setHealth(2);
		
		setCanPassThroughWalls(false);
		setCanFly(false);
		wait = 18;
		countdown = 0;
		movingCountdown = 4;
		animationCountDown = 5;
		animationIndex = 0;
	}
	
	public void nextAnimation(){
		if(animationCountDown > 0){
			animationCountDown -= 1;
		}
		else {
			animationCountDown = 10;
			switch (animationIndex){
				case 0 -> {
					animationIndex = 1;
					setActiveSprite(crabSprites[0]);
				}
				case 1 -> {
					animationIndex = 2;
					setActiveSprite(crabSprites[1]);
				}
				case 2 -> {
					animationIndex = 3;
					setActiveSprite(crabSprites[2]);
				}
				case 3 -> {
					animationIndex = 0;
					setActiveSprite(crabSprites[3]);
				}
			}
		}
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
					if (countdown > wait) {
						changeBehaviourTo("aiming");
					} else {
						changeBehaviourTo("stop-2");
					}
				}
				case "stop-2" -> {
					countdown++;
					if (countdown > wait) {
						changeBehaviourTo("aiming");
					} else {
						changeBehaviourTo("stop-1");
					}
				}
				case "aiming" -> {
					Random random = new Random();
					if(random.nextBoolean()){
						int dX = getDeltaXToObjective(entityManager.getPlayerX());
						int dY = getDeltaYToObjective(entityManager.getPlayerY());
						translationVector2D.setAngulationFromCoordinates(dX, dY);
					}
					else {
						int dX = random.nextInt();
						int dY = random.nextInt();
						translationVector2D.setAngulationFromCoordinates(dX, dY);
					}
					changeBehaviourTo("dashing");
				}
				case "dashing" -> {
					movingCountdown--;
					moveEntity();
					nextAnimation();
					if (movingCountdown <= 0) {
						countdown = 0;
						movingCountdown = 4;
					}
					if (countdown <= wait) {
						changeBehaviourTo("stop-1");
					}
				}
				case "dead" -> {
					disableCollisionBox();
					setActiveSprite(DEAD_FIFTYFIFTY);
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
