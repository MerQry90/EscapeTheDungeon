package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.toDegrees;

public class Peas extends Projectile{
	
	Image PEAS;
	int objectiveX, objectiveY;
	
	public Peas(int x, int y, int objectiveX, int objectiveY, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		this.objectiveX = objectiveX;
		this.objectiveY = objectiveY;
		init();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		PEAS = setSpriteFromPath("src/resources/sprites/projectiles/peas.png");
		setActiveSprite(PEAS);
		
		setWidth(32);
		setHeight(32);
		setCBwidthScalar(0.9);
		setCBheightScalar(0.9);
		initCollisionBox();
		
		translationVector2D = new Vector2D(10);
		int dX = getDeltaXToObjective(entityManager.getPlayerX());
		int dY = getDeltaYToObjective(entityManager.getPlayerY());
		translationVector2D.setAngulationFromCoordinates(dX, dY);
		//System.out.println(toDegrees(translationVector2D.getAngulation()));
		setCanPassThroughWalls(false);
		setCanFly(false);
	}
	
	@Override
	public void moveEntity() {
		if(stuckOnWall){
			setInactive();
		}
		if(translationVector2D.getTranslationOnX() != 0) {
			int signX = translationVector2D.getTranslationOnX() / abs(translationVector2D.getTranslationOnX());
			setX(getX() + translationVector2D.getTranslationOnX());
			while ((entityManager.checkWallsCollisions(this)) ||
					(entityManager.checkObstaclesCollisions(this))) {
				setX(getX() - signX);
				stuckOnWall = true;
			}
		}
		if(translationVector2D.getTranslationOnY() != 0) {
			int signY = translationVector2D.getTranslationOnY() / abs(translationVector2D.getTranslationOnY());
			setY(getY() + translationVector2D.getTranslationOnY());
			while ((entityManager.checkWallsCollisions(this))||
					(entityManager.checkObstaclesCollisions(this))) {
				setY(getY() - signY);
				stuckOnWall = true;
			}
		}
	}
	
}
