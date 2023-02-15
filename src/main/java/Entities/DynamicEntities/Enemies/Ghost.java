package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.abs;

public class Ghost extends Enemy{
	
	private Image VISIBLE_GHOST_LEFT;
	private Image INVISIBLE_GHOST;
	
	private int countdown;
	
	public Ghost(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}
	
	@Override
	public void init(){
		//CARICAMENTO SPRITE
		VISIBLE_GHOST_LEFT = setSpriteFromPath("src/resources/sprites/png/ghost.png");
		INVISIBLE_GHOST = setSpriteFromPath("src/resources/sprites/png/invisible_cube.png");
		setActiveSprite(VISIBLE_GHOST_LEFT);
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.6);
		setCBheightScalar(0.6);
		initCollisionBox();
		
		translationVector2D = new Vector2D(4);
		setRandomHealth(3, 2);
		setCanPassThroughWalls(true);
		setCanFly(true);
		changeBehaviourTo("visible");
		countdown = 60;
	}
	
	@Override
	public void updateBehaviour() {
		if(checkActivation()) {
			switch (getCurrentBehaviour()) {
				case "visible" -> {
					if(countdown <= 0){
						changeBehaviourTo("invisible");
						countdown = 60;
						setActiveSprite(INVISIBLE_GHOST);
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
						countdown = 60;
						setActiveSprite(VISIBLE_GHOST_LEFT);
					}
					else {
						countdown -= 1;
					}
					int dX = getDeltaXToObjective(entityManager.getPlayerX());
					int dY = getDeltaYToObjective(entityManager.getPlayerY());
					translationVector2D.setAngulationFromCoordinates(dX, dY);
					moveEntity();
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
