package Entities.DynamicEntities.Enemies;

import Components.AudioManager;
import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Type of enemy, it charges and then dashes
 * towards the player.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Bat extends Enemy{

	private ArrayList<Image> batSprites;
	private ArrayList<Image> batBallSprites;
	private ArrayList<Image> deathAnimationSprites;
	
	private int wait, countdown, movingCountdown, animationIndex;

	/**
	 * Initializes sprites, animations, speed, health, CollisionBox and the parameters needed to move the entity.
	 * @param x Coordinate where the entity will be initially located
	 * @param y Coordinate where the entity will be initially located
	 * @param entityManager Necessary to check collisions with other entities
	 */
	public Bat(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}

	@Override
	public void init() {
		batSprites = new ArrayList<>();
		batBallSprites = new ArrayList<>();
		deathAnimationSprites = new ArrayList<>();

		//CARICAMENTO SPRITE

		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello1.png"));
		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello2.png"));
		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello3.png"));
		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello4.png"));
		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello5.png"));
		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello6.png"));
		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello7.png"));
		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello8.png"));
		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello9.png"));
		batSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/Idle/pistrello10.png"));

		batBallSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello4.png"));
		batBallSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello5.png"));
		batBallSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello6.png"));
		batBallSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello7.png"));
		batBallSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello8.png"));
		batBallSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/pallina-pistrello/pistrello9.png"));

		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/DeathAnimation/pistrellomorte1.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/DeathAnimation/pistrellomorte2.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/DeathAnimation/pistrellomorte3.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/DeathAnimation/pistrellomorte4.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Bat/DeathAnimation/pistrellomorte5.png"));

		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(25);
		setHealth(3);
		
		setCanPassThroughWalls(false);
		setCanFly(true);
		wait = 40;
		countdown = 0;
		movingCountdown = 20;
		animationIndex = 0;
		setActiveSprite(batSprites.get(0));
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
					Random random = new Random();
					//20% di dash
					if(random.nextInt(0,5) == 0) {
						if(random.nextInt(0, 1) == 0){
							entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.BAT_SOUND_1_INDEX);
						}
						else {
							entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.BAT_SOUND_2_INDEX);
						}
						changeBehaviourTo("dashing");
					}
				}
				case "dashing" -> {
					movingCountdown--;
					setActiveSprite(batBallSprites.get(5));
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
					if(performDeathActions) {
						performDeathActions = false;
						disableCollisionBox();
						animationIndex = 0;
						entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.BAT_SOUND_1_INDEX);
					}
					deathAnimation();
				}
				default -> changeBehaviourTo("stop-1");
			}
		}
	}

	public void deathAnimation(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 ->{
				setActiveSprite(deathAnimationSprites.get(0));
			}
			case 3 ->{
				setActiveSprite(deathAnimationSprites.get(1));
			}
			case 6 ->{
				setActiveSprite(deathAnimationSprites.get(2));
			}
			case 9 ->{
				setActiveSprite(deathAnimationSprites.get(3));
			}
			case 12 ->{
				setActiveSprite(deathAnimationSprites.get(4));
			}
		}
	}

	public void nextAnimation(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 ->{
				setActiveSprite(batSprites.get(0));
			}
			case 3 ->{
				setActiveSprite(batSprites.get(1));
			}
			case 6 ->{
				setActiveSprite(batSprites.get(2));
			}
			case 9 ->{
				setActiveSprite(batSprites.get(3));
			}
			case 12 ->{
				setActiveSprite(batSprites.get(4));
			}
			case 15 ->{
				setActiveSprite(batSprites.get(5));
			}
			case 18 ->{
				setActiveSprite(batSprites.get(6));
			}
			case 21 ->{
				setActiveSprite(batSprites.get(7));
			}
			case 24 ->{
				setActiveSprite(batSprites.get(8));
			}
			case 27 ->{
				setActiveSprite(batSprites.get(9));
			}
			case 30 ->{
				setActiveSprite(batBallSprites.get(0));
			}
			case 33 ->{
				setActiveSprite(batBallSprites.get(1));
			}
			case 36 ->{
				setActiveSprite(batBallSprites.get(2));
			}
			case 39 ->{
				setActiveSprite(batBallSprites.get(3));
			}
			case 42 ->{
				setActiveSprite(batSprites.get(4));
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
