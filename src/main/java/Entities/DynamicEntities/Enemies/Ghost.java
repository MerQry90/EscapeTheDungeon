package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.abs;

public class Ghost extends Enemy{
	
	private Image GHOST_1;
	private Image GHOST_2;
	private Image GHOST_3;
	private Image GHOST_4;
	private Image GHOST_5;
	private Image GHOST_6;
	private Image GHOST_7;
	
	private Image VISIBLE_GHOST_LEFT;
	private Image DEAD_GHOST;
	
	private int countdown;
	private int animationIndex;
	
	public Ghost(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}
	
	@Override
	public void init(){
		//CARICAMENTO SPRITE
		GHOST_1 = setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino1.png");
		GHOST_2 = setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino2.png");
		GHOST_3 = setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino3.png");
		GHOST_4 = setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino4.png");
		GHOST_5 = setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino5.png");
		GHOST_6 = setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino6.png");
		GHOST_7 = setSpriteFromPath("src/resources/sprites/Enemies/Ghost/fantasmino7.png");
		
		VISIBLE_GHOST_LEFT = setSpriteFromPath("src/resources/sprites/png/ghost.png");
		DEAD_GHOST = setSpriteFromPath("src/resources/sprites/png/invisible_cube.png");
		//setActiveSprite(VISIBLE_GHOST_LEFT);
		animationIndex = 0;
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.6);
		setCBheightScalar(0.6);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(4);
		setRandomHealth(3, 2);
		setCanPassThroughWalls(true);
		setCanFly(true);
		countdown = 54 + 9;
	}
	
	public void nextAnimation(){
		animationIndex += 1;
		switch (animationIndex){
			case 0 -> {
				setActiveSprite(GHOST_1);
			}
			case 3 -> {
				setActiveSprite(GHOST_2);
			}
			case 6 -> {
				setActiveSprite(GHOST_3);
			}
			case 9 -> {
				setActiveSprite(GHOST_4);
			}
			case 12 -> {
				setActiveSprite(GHOST_5);
			}
			case 15 -> {
				setActiveSprite(GHOST_6);
			}
			case 18 -> {
				setActiveSprite(GHOST_7);
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
					disableCollisionBox();
					setActiveSprite(DEAD_GHOST);
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
