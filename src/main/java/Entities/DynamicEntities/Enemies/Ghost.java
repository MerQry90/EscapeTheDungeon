package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Type of enemy, moves towards the player becoming invisible.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Ghost extends Enemy{

	private ArrayList<Image> ghostSprites;

	private Image DEAD_GHOST;
	
	private int countdown;
	private int animationIndex;
	private ArrayList<Image> deathAnimationSprites;

	/**
	 * Initializes sprites, animations, speed, health, CollisionBox and the parameters needed to move the entity.
	 * @param x Coordinate where the entity will be initially located
	 * @param y Coordinate where the entity will be initially located
	 * @param entityManager Necessary to check collisions with other entities
	 */
	public Ghost(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}
	
	@Override
	public void init(){
		ghostSprites = new ArrayList<>();
		deathAnimationSprites = new ArrayList<>();

		//CARICAMENTO SPRITE
		ghostSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino1.png"));
		ghostSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino2.png"));
		ghostSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino3.png"));
		ghostSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino4.png"));
		ghostSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino5.png"));
		ghostSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino6.png"));
		ghostSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino7.png"));

		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/DeathAnimation/fantasminomorte5.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/DeathAnimation/fantasminomorte6.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/DeathAnimation/fantasminomorte7.png"));
		deathAnimationSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Ghost/DeathAnimation/fantasminomorte8.png"));

		DEAD_GHOST = setSpriteFromPath("src/resources/sprites/png/invisible_cube.png");
		animationIndex = 0;
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.6);
		setCBheightScalar(0.6);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(4);
		setHealth(3);
		
		setCanPassThroughWalls(true);
		setCanFly(true);
		countdown = 54 + 9;
		setActiveSprite(ghostSprites.get(0));
	}
	public void deathAnimation(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 -> {
				setActiveSprite(deathAnimationSprites.get(0));
			}
			case 5 -> {
				setActiveSprite(deathAnimationSprites.get(1));
			}
			case 10 -> {
				setActiveSprite(deathAnimationSprites.get(2));
			}
			case 15 -> {
				setActiveSprite(deathAnimationSprites.get(3));
			}
		}
	}
	public void nextAnimation(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 -> {
				setActiveSprite(ghostSprites.get(0));
			}
			case 3 -> {
				setActiveSprite(ghostSprites.get(1));
			}
			case 6 -> {
				setActiveSprite(ghostSprites.get(2));
			}
			case 9 -> {
				setActiveSprite(ghostSprites.get(3));
			}
			case 12 -> {
				setActiveSprite(ghostSprites.get(4));
			}
			case 15 -> {
				setActiveSprite(ghostSprites.get(5));
			}
			case 18 -> {
				setActiveSprite(ghostSprites.get(6));
				animationIndex = 0;
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
				case "visible" -> {
					nextAnimation();
					if(countdown <= 0){
						changeBehaviourTo("invisible");
						countdown = 60;
						setActiveSprite(DEAD_GHOST);
						animationIndex = 0;
					}
					else {
						countdown -= 1;
					}
					int dX = getDeltaXToObjective(entityManager.getPlayerX());
					int dY = getDeltaYToObjective(entityManager.getPlayerY());
					translationVector2D.setAngulationFromCoordinates(dX, dY);
					moveEntity();
				}
				case "invisible" ->{
					if(countdown <= 0){
						changeBehaviourTo("visible");
						countdown = 54 + 9;
					}
					else {
						countdown -= 1;
					}
					int dX = getDeltaXToObjective(entityManager.getPlayerX());
					int dY = getDeltaYToObjective(entityManager.getPlayerY());
					translationVector2D.setAngulationFromCoordinates(dX, dY);
					moveEntity();
				}
				case "dead" -> {
					if(performDeathActions){
						performDeathActions = false;
						animationIndex = 0;
					}
					disableCollisionBox();
					deathAnimation();
				}
				default -> {
					changeBehaviourTo("visible");
				}
			}
		}
	}
	
	@Override
	public void moveEntity() {
		if(translationVector2D.getTranslationOnX() != 0) {
			int signX = translationVector2D.getTranslationOnX() / abs(translationVector2D.getTranslationOnX());
			setX(getX() + translationVector2D.getTranslationOnX());
			while ((entityManager.checkWallsCollisions(this)) ||
					(entityManager.checkObstaclesCollisions(this))) {
				setX(getX() - signX);
			}
		}
		if(translationVector2D.getTranslationOnY() != 0) {
			int signY = translationVector2D.getTranslationOnY() / abs(translationVector2D.getTranslationOnY());
			setY(getY() + translationVector2D.getTranslationOnY());
			while ((entityManager.checkWallsCollisions(this))||
					(entityManager.checkObstaclesCollisions(this))) {
				setY(getY() - signY);
			}
		}
	}
}
