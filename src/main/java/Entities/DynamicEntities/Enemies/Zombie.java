package Entities.DynamicEntities.Enemies;

import Components.AudioManager;
import Components.EntityManager;
import Components.Vector2D;
import java.awt.*;
import java.util.Random;


public class Zombie extends Enemy{
	
	private Image ZOMBIE1_L, ZOMBIE2_L, ZOMBIE3_L, ZOMBIE4_L, ZOMBIE1_R, ZOMBIE2_R, ZOMBIE3_R, ZOMBIE4_R;
	private Image DEAD_ZOMBIE;

	private int animationIndex;
	private int soundCountDown;
	
	private boolean performDeathAction;
	
	public Zombie(int x, int y, EntityManager entityManager) {
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}
	
	@Override
	public void init(){
		//CARICAMENTO SPRITE
		ZOMBIE1_L = setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie1_L.png");
		ZOMBIE2_L = setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie2_L.png");
		ZOMBIE3_L = setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie3_L.png");
		ZOMBIE4_L = setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie4_L.png");

		ZOMBIE1_R = setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie1_R.png");
		ZOMBIE2_R = setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie2_R.png");
		ZOMBIE3_R = setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie3_R.png");
		ZOMBIE4_R = setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie4_R.png");

		DEAD_ZOMBIE = setSpriteFromPath("src/resources/sprites/png/deadMage.png");

		setActiveSprite(ZOMBIE1_L);
		animationIndex = 0;
		performDeathAction = true;
		soundCountDown = 0;
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(0);
		setRandomSpeed(5, 2);
		//setSpeed(6);
		setRandomHealth(3, 2);
		setCanPassThroughWalls(false);
		setCanFly(false);
	}
	
	@Override
	public void updateBehaviour() {
		if(checkActivation()) {
			if(!checkIfActive()){
				changeBehaviourTo("dead");
			}
			switch (getCurrentBehaviour()) {
				case "follow-player" -> {
					nextAnimation();
					int dX = getDeltaXToObjective(entityManager.getPlayerX());
					int dY = getDeltaYToObjective(entityManager.getPlayerY());
					translationVector2D.setAngulationFromCoordinates(dX, dY);
					moveEntity();
					trySound();
				}
				case "dead" -> {
					if(performDeathAction) {
						performDeathAction = false;
						disableCollisionBox();
						setActiveSprite(DEAD_ZOMBIE);
						entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.ZOMBIE_DEATH_INDEX);
					}
				}
				default -> {
					changeBehaviourTo("follow-player");
				}
			}
		}
	}
	
	@Override
	public void moveEntity(){
		int originalX = getX();
		int originalY = getY();
		
		boolean collisionOnX = false;
		boolean collisionOnY = false;
		
		setX(originalX + translationVector2D.getTranslationOnX());
		if(entityManager.checkObstaclesCollisions(this)){
			collisionOnX = true;
		}
		setX(originalX);
		
		setY(originalY + translationVector2D.getTranslationOnY());
		if(entityManager.checkObstaclesCollisions(this)){
			collisionOnY = true;
		}
		setY(originalY);
		
		if(collisionOnX && !collisionOnY){
			setY(originalY + (getSpeed() * translationVector2D.getSignOnY()));
		}
		else if(collisionOnY && !collisionOnX){
			setX(originalX + (getSpeed() * translationVector2D.getSignOnX()));
		}
		else {
			setX(originalX + translationVector2D.getTranslationOnX());
			setY(originalY + translationVector2D.getTranslationOnY());
		}
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
				setActiveSprite(ZOMBIE1_L);
			}
			case 4 ->{
				setActiveSprite(ZOMBIE2_L);
			}
			case 8 ->{
				setActiveSprite(ZOMBIE3_L);
			}
			case 12 ->{
				setActiveSprite(ZOMBIE4_L);
				animationIndex = 0;
			}
		}
	}

	public void nextAnimationRight(){
		animationIndex += 1;

		switch (animationIndex){
			case 0 ->{
				setActiveSprite(ZOMBIE1_R);
			}
			case 4 ->{
				setActiveSprite(ZOMBIE2_R);
			}
			case 8 ->{
				setActiveSprite(ZOMBIE3_R);
			}
			case 12 ->{
				setActiveSprite(ZOMBIE4_R);
				animationIndex = 0;
			}
		}
	}
	
	public void trySound(){
		if(soundCountDown > 0){
			soundCountDown -= 1;
		}
		else {
			Random random = new Random();
			if(random.nextInt(0, 10) == 0){
				soundCountDown = 30 * 3;
				if(random.nextInt(0, 2) == 0){
					entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.ZOMBIE_SOUND_1_INDEX);
				}
				else {
					entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.ZOMBIE_SOUND_2_INDEX);
				}
			}
		}
	}
	
}
