package Entities.DynamicEntities.Projectiles;

import Components.EntityManager;
import Components.Vector2D;
import Entities.GenericEntity;

import java.awt.*;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * Projectiles shot by the Mage, they continuously
 * follow the player until the end of their duration.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class MagicBall extends Projectile {
    private int duration = 60;
    private Image MAGIC_BALL;

    /**
     * Initializes sprites, animations, speed, CollisionBox and the parameters needed to move the ball.
     * @param x Coordinate where the ball will be initially located
     * @param y Coordinate where the ball will be initially located
     * @param entityManager Necessary to check collisions with other entities
     */
    public MagicBall(int x, int y, EntityManager entityManager){
        this.entityManager = entityManager;
        setX(x);
        setY(y);
        init();
    }
    @Override
    public void init() {
        MAGIC_BALL = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "sprites/projectiles/Magic_ball.png")));
        setActiveSprite(MAGIC_BALL);

        setWidth(24);
        setHeight(24);
        setCBwidthScalar(0.9);
        setCBheightScalar(0.9);
        activateCollisionBox();

        translationVector2D = new Vector2D(6);
        int dX = getDeltaXToObjective(entityManager.getPlayerX());
        int dY = getDeltaYToObjective(entityManager.getPlayerY());
        translationVector2D.setAngulationFromCoordinates(dX, dY);
        setCanPassThroughWalls(false);
        setCanFly(true);
    }
    @Override
    public void moveEntity() {
        int dX = getDeltaXToObjective(entityManager.getPlayerX());
        int dY = getDeltaYToObjective(entityManager.getPlayerY());
        translationVector2D.setAngulationFromCoordinates(dX, dY);
        duration -= 1;
        if(stuckOnWall || duration <= 0){
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
