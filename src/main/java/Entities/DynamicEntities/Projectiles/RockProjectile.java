package Entities.DynamicEntities.Projectiles;

import Components.EntityManager;
import Components.Vector2D;
import Entities.GenericEntity;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Projectile shot by the shooter, moves in the same direction until it collides.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class RockProjectile extends Projectile{
	
	Image PEAS;

	/**
	 * Initializes sprites, animations, speed, CollisionBox and the parameters needed to move the projectile.
	 * @param x Coordinate where the projectile will be initially located
	 * @param y Coordinate where the projectile will be initially located
	 * @param entityManager Necessary to check collisions with other entities
	 */
	public RockProjectile(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		PEAS = GenericEntity.setSpriteFromPath("src/resources/sprites/Projectiles/rockshooterrock.png");
		setActiveSprite(PEAS);
		
		setWidth(32);
		setHeight(32);
		setCBwidthScalar(0.9);
		setCBheightScalar(0.9);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(10);
		int dX = getDeltaXToObjective(entityManager.getPlayerX());
		int dY = getDeltaYToObjective(entityManager.getPlayerY());
		translationVector2D.setAngulationFromCoordinates(dX, dY);
		setCanPassThroughWalls(false);
		setCanFly(false);
	}
	
	@Override
	public void moveEntity() {
		if(stuckOnWall){
			setInactive();
		}
		if(translationVector2D.getTranslationOnX() != 0) {
			int signX = translationVector2D.getTranslationOnX() / Math.abs(translationVector2D.getTranslationOnX());
			setX(getX() + translationVector2D.getTranslationOnX());
			while ((entityManager.checkWallsCollisions(this)) ||
					(entityManager.checkObstaclesCollisions(this))) {
				setX(getX() - signX);
				stuckOnWall = true;
			}
		}
		if(translationVector2D.getTranslationOnY() != 0) {
			int signY = translationVector2D.getTranslationOnY() / Math.abs(translationVector2D.getTranslationOnY());
			setY(getY() + translationVector2D.getTranslationOnY());
			while ((entityManager.checkWallsCollisions(this))||
					(entityManager.checkObstaclesCollisions(this))) {
				setY(getY() - signY);
				stuckOnWall = true;
			}
		}
	}
	
}
