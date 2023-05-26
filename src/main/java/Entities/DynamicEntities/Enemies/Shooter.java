package Entities.DynamicEntities.Enemies;

import Components.AudioManager;
import Components.EntityManager;
import Components.Vector2D;
import Entities.DynamicEntities.Projectiles.RockProjectile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Type of enemy, it stands still but rapidly shoots
 * projectiles toward the player.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Shooter extends Enemy{

	private ArrayList<Image> shooterLeftSprites;
	private ArrayList<Image> shooterRightSprites;
	private ArrayList<Image> deathAnimationSprites;
	
	private int idleCountdown, animationIndex;

	/**
	 * Initializes sprites, animations, speed, health, CollisionBox and the parameters needed to move the entity.
	 * @param x Coordinate where the entity will be initially located
	 * @param y Coordinate where the entity will be initially located
	 * @param entityManager Necessary to check collisions with other entities
	 */
	public Shooter(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}

	@Override
	public void init() {
		shooterLeftSprites = new ArrayList<>();
		shooterRightSprites = new ArrayList<>();
		deathAnimationSprites = new ArrayList<>();

		//CARICAMENTO SPRITE

		shooterLeftSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter1_L.png"))));
		shooterLeftSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter2_L.png"))));
		shooterLeftSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter3_L.png"))));
		shooterLeftSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter4_L.png"))));
		shooterLeftSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter5_L.png"))));
		shooterLeftSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter6_L.png"))));

		shooterRightSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter1_R.png"))));
		shooterRightSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter2_R.png"))));
		shooterRightSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter3_R.png"))));
		shooterRightSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter4_R.png"))));
		shooterRightSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter5_R.png"))));
		shooterRightSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/rockshooter6_R.png"))));


		deathAnimationSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/DeathAnimation/rockshootermorte1.png"))));
		deathAnimationSprites.add(setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Enemies/Shooter/DeathAnimation/rockshootermorte2.png"))));

		setActiveSprite(shooterLeftSprites.get(0));
		animationIndex = 0;
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
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
	}
	
	@Override
	public void updateBehaviour() {
		if(checkActivation()) {
			if(!checkIfActive()){
				changeBehaviourTo("dead");
			}
			switch (getCurrentBehaviour()) {
				case "idle" -> {
					Random random = new Random();
					nextAnimation();
					if(idleCountdown <= 0 && random.nextInt(0, 5) == 0){
						entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.ROCK_THROW_2_INDEX);
						changeBehaviourTo("shoot");
					}
					idleCountdown -= 1;
				}
				case "shoot" -> {
					nextAnimation();
					entityManager.newHostileProjectile(new RockProjectile(getX() + 16, getY() + 16, entityManager));
					idleCountdown = 30;
					changeBehaviourTo("idle");
				}
				case "dead" -> {
					if(performDeathActions) {
						performDeathActions = false;
						disableCollisionBox();
						entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.ROCK_BROKEN_INDEX);
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
	
	@Override
	public void moveEntity() {
		//non si muove
	}
	public void nextAnimation(){
		if(entityManager.getPlayerX() >= this.getX()){
			nextAnimationRight();
		}
		else {
			nextAnimationLeft();
		}
	}
	public void deathAnimation(){
		animationIndex += 1;

		switch (animationIndex){
			case 0 ->{
				setActiveSprite(deathAnimationSprites.get(0));
			}
			case 5 ->{
				setActiveSprite(deathAnimationSprites.get(1));
			}
		}
	}

	public void nextAnimationLeft(){
		animationIndex += 1;

		switch (animationIndex){
			case 0 ->{
				setActiveSprite(shooterLeftSprites.get(0));
			}
			case 5 ->{
				setActiveSprite(shooterLeftSprites.get(1));
			}
			case 10 ->{
				setActiveSprite(shooterLeftSprites.get(2));
			}
			case 15 ->{
				setActiveSprite(shooterLeftSprites.get(3));
			}
			case 20 ->{
				setActiveSprite(shooterLeftSprites.get(4));
			}
			case 25 ->{
				setActiveSprite(shooterLeftSprites.get(5));
				animationIndex = 0;
			}
		}
	}

	public void nextAnimationRight(){
		animationIndex += 1;

		switch (animationIndex){
			case 0 ->{
				setActiveSprite(shooterRightSprites.get(0));
			}
			case 5 ->{
				setActiveSprite(shooterRightSprites.get(1));
			}
			case 10 ->{
				setActiveSprite(shooterRightSprites.get(2));
			}
			case 15 ->{
				setActiveSprite(shooterRightSprites.get(3));
			}
			case 20 ->{
				setActiveSprite(shooterRightSprites.get(4));
			}
			case 25 ->{
				setActiveSprite(shooterRightSprites.get(5));
				animationIndex = 0;
			}
		}
	}
}
