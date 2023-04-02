package Entities.DynamicEntities.Enemies;

import Components.AudioManager;
import Components.EntityManager;
import Components.Vector2D;
import Entities.DynamicEntities.Projectiles.Peas;

import java.awt.*;
import java.util.Random;

public class Shooter extends Enemy{

	private Image SHOOTER1_L, SHOOTER2_L, SHOOTER3_L, SHOOTER4_L, SHOOTER5_L, SHOOTER6_L,
			SHOOTER1_R,	SHOOTER2_R, SHOOTER3_R, SHOOTER4_R, SHOOTER5_R, SHOOTER6_R;
	private Image DEAD_SHOOTER;
	
	private int idleCountdown, animationIndex;
	
	private boolean performDeathActions;
	
	public Shooter(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}

	@Override
	public void init() {
		//CARICAMENTO SPRITE
		SHOOTER1_L = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter1_L.png");
		SHOOTER2_L = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter2_L.png");
		SHOOTER3_L = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter3_L.png");
		SHOOTER4_L = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter4_L.png");
		SHOOTER5_L = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter5_L.png");
		SHOOTER6_L = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter6_L.png");

		SHOOTER1_R = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter1_R.png");
		SHOOTER2_R = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter2_R.png");
		SHOOTER3_R = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter3_R.png");
		SHOOTER4_R = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter4_R.png");
		SHOOTER5_R = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter5_R.png");
		SHOOTER6_R = setSpriteFromPath("src/resources/sprites/Enemies/Shooter/rockshooter6_R.png");

		DEAD_SHOOTER = setSpriteFromPath("src/resources/sprites/png/deadMage.png");

		setActiveSprite(SHOOTER1_L);
		animationIndex = 0;
		performDeathActions = true;
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.8);
		setCBheightScalar(0.9);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(0);
		setRandomHealth(6, 4);
		setCanPassThroughWalls(false);
		setCanFly(false);
		
		idleCountdown = 20;
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
					entityManager.newHostileProjectile(new Peas(getX(), getY(),
							entityManager.getPlayerX(), entityManager.getPlayerY(), entityManager));
					idleCountdown = 20;
					changeBehaviourTo("idle");
				}
				case "dead" -> {
					if(performDeathActions) {
						performDeathActions = false;
						disableCollisionBox();
						setActiveSprite(DEAD_SHOOTER);
						entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.ROCK_BROKEN_INDEX);
					}
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
	public void nextAnimationLeft(){
		animationIndex += 1;

		switch (animationIndex){
			case 0 ->{
				setActiveSprite(SHOOTER1_L);
			}
			case 5 ->{
				setActiveSprite(SHOOTER2_L);
			}
			case 10 ->{
				setActiveSprite(SHOOTER3_L);
			}
			case 15 ->{
				setActiveSprite(SHOOTER4_L);
			}
			case 20 ->{
				setActiveSprite(SHOOTER5_L);
			}
			case 25 ->{
				setActiveSprite(SHOOTER6_L);
			}
			case 29 ->{
				animationIndex = 0;
			}
		}
	}

	public void nextAnimationRight(){
		animationIndex += 1;

		switch (animationIndex){
			case 0 ->{
				setActiveSprite(SHOOTER1_R);
			}
			case 5 ->{
				setActiveSprite(SHOOTER2_R);
			}
			case 10 ->{
				setActiveSprite(SHOOTER3_R);
			}
			case 15 ->{
				setActiveSprite(SHOOTER4_R);
			}
			case 20 ->{
				setActiveSprite(SHOOTER5_R);
			}
			case 25 ->{
				setActiveSprite(SHOOTER6_R);
			}
			case 29 ->{
				animationIndex = 0;
			}
		}
	}
}
