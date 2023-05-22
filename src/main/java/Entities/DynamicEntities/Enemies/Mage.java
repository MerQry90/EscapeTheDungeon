package Entities.DynamicEntities.Enemies;

import Components.AudioManager;
import Components.EntityManager;
import Components.Vector2D;
import Entities.DynamicEntities.Projectiles.MagicBall;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Type of enemy, teleport across the rooms,
 * while shooting homing projectiles.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Mage extends Enemy{
	
	private Image MAGE_IDLE, MAGE_SHOOTING, DEAD_MAGE;
	private ArrayList<Image> mageTeleportSprites;
	private ArrayList<Image> deathAnimationSprites;

	private int idleCountdown, shotNumber, animationIndex;

	/**
	 * Initializes sprites, animations, speed, health, CollisionBox and the parameters needed to move the entity.
	 * @param x Coordinate where the entity will be initially located
	 * @param y Coordinate where the entity will be initially located
	 * @param entityManager Necessary to check collisions with other entities
	 */
	public Mage(int x, int y, EntityManager entityManager){
		setX(x);
		setY(y);
		this.entityManager = entityManager;
		init();
	}
	@Override
	public void init() {
		mageTeleportSprites = new ArrayList<>();
		deathAnimationSprites = new ArrayList<>();

		MAGE_IDLE = setSpriteFromPath("src/resources/sprites/Enemies/Mage/magikino1.png");
		MAGE_SHOOTING  = setSpriteFromPath("src/resources/sprites/Enemies/Mage/magikino2.png");
		
		mageTeleportSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline1.png"));
		mageTeleportSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline2.png"));
		mageTeleportSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline3.png"));
		mageTeleportSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline4.png"));
		mageTeleportSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline5.png"));

		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/DeathAnimation/magikinomorte1.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/DeathAnimation/magikinomorte2.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/DeathAnimation/magikinomorte3.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/DeathAnimation/magikinomorte4.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/DeathAnimation/magikinomorte5.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/DeathAnimation/magikinomorte6.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/DeathAnimation/magikinomorte7.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Mage/DeathAnimation/magikinomorte8.png"));

		DEAD_MAGE = setSpriteFromPath("");

		setActiveSprite(MAGE_IDLE);
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.8);
		setCBheightScalar(0.9);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(0);
		setHealth(5);
		
		setCanPassThroughWalls(false);
		setCanFly(false);
		
		idleCountdown = 30;
		shotNumber = 0;
		animationIndex = 0;
	}
	
	@Override
	public void updateBehaviour() {
		if(checkActivation()) {
			if(!checkIfActive()){
				changeBehaviourTo("dead");
			}
			switch (getCurrentBehaviour()) {
				case "idle" -> {
					if(shotNumber >= 3){
						setActiveSprite(MAGE_IDLE);
						shotNumber = 0;
						nextAnimation();
						changeBehaviourTo("teleport");
					}
					if(idleCountdown <= 0){
						setActiveSprite(MAGE_SHOOTING);
						changeBehaviourTo("shoot");
					}
					else if(idleCountdown > 30){
						nextAnimation();
					}
					else {
						setActiveSprite(MAGE_IDLE);
					}
					idleCountdown -= 1;
				}
				case "shoot" -> {
					setActiveSprite(MAGE_SHOOTING);
					entityManager.newHostileProjectile(new MagicBall(getX(), getY(), entityManager));
					idleCountdown = 10;
					shotNumber += 1;
					changeBehaviourTo("idle");
				}
				case  "teleport" ->{
					entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.TELEPORT_SOUND_INDEX);
					idleCountdown = 40;
					moveEntity();
					changeBehaviourTo("idle");
				}
				case "dead" -> {
					if(performDeathActions) {
						performDeathActions = false;
						entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.MAGE_DEATH_SOUND_INDEX);
						disableCollisionBox();
						animationIndex = 0;
					}
					deathAnimation();
				}
				default -> {
					changeBehaviourTo("idle");
				}
			}
		}
	}

	public void deathAnimation(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 ->{
				setActiveSprite(deathAnimationSprites.get(0));
			}
			case 2 ->{
				setActiveSprite(deathAnimationSprites.get(1));
			}
			case 4 ->{
				setActiveSprite(deathAnimationSprites.get(2));
			}
			case 6 ->{
				setActiveSprite(deathAnimationSprites.get(3));
			}
			case 8 ->{
				setActiveSprite(deathAnimationSprites.get(4));
			}
			case 10 ->{
				setActiveSprite(deathAnimationSprites.get(5));
			}
			case 12 ->{
				setActiveSprite(deathAnimationSprites.get(6));
			}
			case 14 ->{
				setActiveSprite(deathAnimationSprites.get(7));
			}
			case 16->{
				setActiveSprite(DEAD_MAGE);
			}
		}
	}
	
	public void nextAnimation(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 ->{
				setActiveSprite(mageTeleportSprites.get(0));
			}
			case 2 ->{
				setActiveSprite(mageTeleportSprites.get(1));
			}
			case 4 ->{
				setActiveSprite(mageTeleportSprites.get(2));
			}
			case 6 ->{
				setActiveSprite(mageTeleportSprites.get(3));
			}
			case 8 ->{
				setActiveSprite(mageTeleportSprites.get(4));
				animationIndex = 0;
			}
		}
	}
	
	@Override
	public void moveEntity() {
		Random random = new Random();
		int minimumSpace = 128;
		int bound = 128;
		int randomX, randomY;
		do{
			randomX = entityManager.getPlayerX();
			randomY = entityManager.getPlayerY();
			if(random.nextBoolean()){
				randomX += random.nextInt(bound) + minimumSpace;
			}
			else {
				randomX -= random.nextInt(bound) + minimumSpace;
			}
			if(random.nextBoolean()){
				randomY += random.nextInt(bound) + minimumSpace;
			}
			else {
				randomY -= random.nextInt(bound) + minimumSpace;
			}
			setX(randomX);
			setY(randomY);
		}
		while(getX() < 65 || getX() > 960 || getY() < 65 || getY() > 448 || entityManager.checkObstaclesCollisions(this));
	}
}
