package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.toRadians;

public class Tank extends Enemy{
    private Image TANK;

    public Tank(int x, int y, EntityManager entityManager) {
        this.entityManager = entityManager;
        setX(x);
        setY(y);
        init();
    }
    @Override
    public void init() {
        //CARICAMENTO SPRITE
        TANK = setSpriteFromPath("src/resources/sprites/png/zombie.png");
        setActiveSprite(TANK);

        //l'estremo è escluso, velocità a cui viene sommata maximumSpeed
        //verrà sommato a minimumSpeed

        setWidth(96);
        setHeight(128);
        setCBwidthScalar(0.7);
        setCBheightScalar(0.9);
        initCollisionBox();

        canGenerateBloodStain = true;
        canBreakRocks = true;

        translationVector2D = new Vector2D(0);
        setRandomSpeed(1, 5);
        setRandomHealth(5, 18);
        setCanPassThroughWalls(false);
        setCanFly(false);
        changeBehaviourTo("follow-player");
    }

    @Override
    public void moveEntity() {
        int originalX = getX();
        int originalY = getY();
        double originalTheta = translationVector2D.getAngulation();
        double epsilon = toRadians(15.0);

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

    @Override
    public void updateBehaviour() {
        if(checkActivation()) {
            switch (getCurrentBehaviour()) {
                case "follow-player" -> {
                    int dX = getDeltaXToObjective(entityManager.getPlayerX());
                    int dY = getDeltaYToObjective(entityManager.getPlayerY());
                    translationVector2D.setAngulationFromCoordinates(dX, dY);
                    moveEntity();
                }
                default -> {
                    changeBehaviourTo("follow-player");
                }
            }
        }
    }
}
