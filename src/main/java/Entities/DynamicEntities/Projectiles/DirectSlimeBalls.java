package Entities.DynamicEntities.Projectiles;

import Components.EntityManager;
import Components.Vector2D;
import Entities.GenericEntity;

import java.awt.*;

public class DirectSlimeBalls extends Projectile{
    
    private double angulation;
    private Image SLIME_BALL;
    private int speed;

    public DirectSlimeBalls(int x, int y, int speed , double angulation, EntityManager entityManager){
        this.entityManager = entityManager;
        setXFromCenter(x);
        setYFromCenter(y);
        this.speed = speed;
        this.angulation = angulation;
        init();
    }

    public void init(){
        SLIME_BALL = GenericEntity.setSpriteFromPath("src/resources/sprites/projectiles/slimeball.png");
        setActiveSprite(SLIME_BALL);

        setWidth(32);
        setHeight(32);
        setCBwidthScalar(0.9);
        setCBheightScalar(0.9);
        initCollisionBox();

        translationVector2D = new Vector2D(speed);
        translationVector2D.setAngulation(angulation);
        setCanPassThroughWalls(false);
        setCanFly(true);
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
