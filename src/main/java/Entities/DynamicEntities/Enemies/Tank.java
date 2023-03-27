package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.toRadians;

public class Tank extends Enemy{
    private Image TANK1_R, TANK2_R, TANK3_R, TANK4_R, TANK1_L, TANK2_L, TANK3_L, TANK4_L;
    private Image DEAD_TANK;

    private int  animationIndex;

    public Tank(int x, int y, EntityManager entityManager) {
        this.entityManager = entityManager;
        setX(x);
        setY(y);
        init();
    }
    @Override
    public void init() {
        //CARICAMENTO SPRITE
        TANK1_R = setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank1_right.png");
        TANK2_R = setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank2_right.png");
        TANK3_R = setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank3_right.png");
        TANK4_R = setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank4_right.png");

        TANK1_L = setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank1_left.png");
        TANK2_L = setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank2_left.png");
        TANK3_L = setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank3_left.png");
        TANK4_L = setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank4_left.png");

        DEAD_TANK = setSpriteFromPath("src/resources/sprites/png/deadMage.png");

        setActiveSprite(TANK1_L);
        animationIndex = 0;


        //l'estremo è escluso, velocità a cui viene sommata maximumSpeed
        //verrà sommato a minimumSpeed

        setWidth(76);
        setHeight(112);
        setCBwidthScalar(0.7);
        setCBheightScalar(0.9);
        activateCollisionBox();

        canGenerateBloodStain = true;
        canBreakRocks = true;

        translationVector2D = new Vector2D(0);
        setRandomSpeed(1, 5);
        setRandomHealth(5, 18);
        setCanPassThroughWalls(false);
        setCanFly(false);
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
            if(!checkIfActive()){
                changeBehaviourTo("dead");
            }
            switch (getCurrentBehaviour()) {
                case "follow-player" -> {
                    int dX = getDeltaXToObjective(entityManager.getPlayerX());
                    int dY = getDeltaYToObjective(entityManager.getPlayerY());
                    translationVector2D.setAngulationFromCoordinates(dX, dY);
                    nextAnimation();
                    moveEntity();
                }
                case "dead" ->{
                    disableCollisionBox();
                    setActiveSprite(DEAD_TANK);
                }
                default -> {
                    changeBehaviourTo("follow-player");
                }
            }
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
    public void nextAnimationLeft(){
        animationIndex += 1;

        switch (animationIndex){
            case 0 ->{
                setActiveSprite(TANK1_L);
            }
            case 5 ->{
                setActiveSprite(TANK2_L);
            }
            case 10 ->{
                setActiveSprite(TANK3_L);
            }
            case 15 ->{
                setActiveSprite(TANK4_L);
                animationIndex = 0;
            }
        }
    }

    public void nextAnimationRight(){
        animationIndex += 1;

        switch (animationIndex){
            case 0 ->{
                setActiveSprite(TANK1_R);
            }
            case 5 ->{
                setActiveSprite(TANK2_R);
            }
            case 10 ->{
                setActiveSprite(TANK3_R);
            }
            case 15 ->{
                setActiveSprite(TANK4_R);
                animationIndex = 0;
            }
        }
    }
}
