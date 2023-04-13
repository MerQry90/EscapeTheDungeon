package Entities.DynamicEntities.Enemies;

import Components.AudioManager;
import Components.EntityManager;
import Components.Vector2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Zombie extends Enemy{

	private ArrayList<Image> zombieLeftSprites;
	private ArrayList<Image> zombieRightSprites;
	private ArrayList<Image> deathAnimationSprites;
	private Image DEAD_ZOMBIE;

	private int animationIndex;
	private int soundCountDown;
	
	public Zombie(int x, int y, EntityManager entityManager) {
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}
	
	@Override
	public void init(){
		zombieLeftSprites = new ArrayList<>();
		zombieRightSprites = new ArrayList<>();
		deathAnimationSprites = new ArrayList<>();

		//CARICAMENTO SPRITE

		zombieLeftSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie1_L.png"));
		zombieLeftSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie2_L.png"));
		zombieLeftSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie3_L.png"));
		zombieLeftSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie4_L.png"));

		zombieRightSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie1_R.png"));
		zombieRightSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie2_R.png"));
		zombieRightSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie3_R.png"));
		zombieRightSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/zombie4_R.png"));

		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/DeathAnimation/Zombiemorte1.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/DeathAnimation/Zombiemorte2.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/DeathAnimation/Zombiemorte3.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/DeathAnimation/Zombiemorte4.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Zombie/DeathAnimation/Zombiemorte5.png"));

		setActiveSprite(zombieLeftSprites.get(0));
		animationIndex = 0;
		soundCountDown = 0;
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(0);
		//setRandomSpeed(5, 2);
		setSpeed(5);
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
					if(performDeathActions) {
						performDeathActions = false;
						disableCollisionBox();
						animationIndex = 0;
						entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.ZOMBIE_DEATH_INDEX);
					}
					deathAnimation();
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

	public void deathAnimation(){
		animationIndex += 1;

		switch (animationIndex){
			case 0 ->{
				setActiveSprite(deathAnimationSprites.get(0));
			}
			case 4 ->{
				setActiveSprite(deathAnimationSprites.get(1));
			}
			case 8 ->{
				setActiveSprite(deathAnimationSprites.get(2));
			}
			case 12 ->{
				setActiveSprite(deathAnimationSprites.get(3));
			}
			case 15 ->{
				setActiveSprite(deathAnimationSprites.get(4));
			}
		}
	}

	public void nextAnimationLeft(){
		animationIndex += 1;

		switch (animationIndex){
			case 0 ->{
				setActiveSprite(zombieLeftSprites.get(0));
			}
			case 4 ->{
				setActiveSprite(zombieLeftSprites.get(1));
			}
			case 8 ->{
				setActiveSprite(zombieLeftSprites.get(2));
			}
			case 12 ->{
				setActiveSprite(zombieLeftSprites.get(3));
				animationIndex = 0;
			}
		}
	}

	public void nextAnimationRight(){
		animationIndex += 1;

		switch (animationIndex){
			case 0 ->{
				setActiveSprite(zombieRightSprites.get(0));
			}
			case 4 ->{
				setActiveSprite(zombieRightSprites.get(1));
			}
			case 8 ->{
				setActiveSprite(zombieRightSprites.get(2));
			}
			case 12 ->{
				setActiveSprite(zombieRightSprites.get(3));
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
