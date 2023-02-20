package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.abs;

public class MagicBall extends Projectile {
    private int objectiveX, objectiveY;
    private int duration = 60;
    private Image MAGIC_BALL;

    public MagicBall(int x, int y, int objectiveX, int objectiveY, EntityManager entityManager){
        this.entityManager = entityManager;
        setX(x);
        setY(y);
        this.objectiveX = objectiveX;
        this.objectiveY = objectiveY;
        init();
    }
    @Override
    public void init() {
        MAGIC_BALL = setSpriteFromPath("src/resources/sprites/projectiles/Magic_ball.png");
        setActiveSprite(MAGIC_BALL);

        setWidth(24);
        setHeight(24);
        setCBwidthScalar(0.9);
        setCBheightScalar(0.9);
        initCollisionBox();

        translationVector2D = new Vector2D(8);
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
