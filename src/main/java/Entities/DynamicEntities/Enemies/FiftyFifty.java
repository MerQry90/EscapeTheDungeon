package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.abs;

public class FiftyFifty extends Enemy{

    private int wait, countdown, movingCountdown;
    private Image FIFTYFIFTY;
    public FiftyFifty(int x, int y, EntityManager entityManager){
        this.entityManager = entityManager;
        setX(x);
        setY(y);
        init();
    }
    @Override
    public void init() {
        //CARICAMENTO SPRITE
        FIFTYFIFTY = setSpriteFromPath("src/resources/sprites/projectiles/peas.png");
        setActiveSprite(FIFTYFIFTY);

        setWidth(48);
        setHeight(48);
        setCBwidthScalar(0.7);
        setCBheightScalar(0.9);
        initCollisionBox();

        translationVector2D = new Vector2D(0);
        setRandomSpeed(1, 30);
        setRandomHealth(2, 1);
        setCanPassThroughWalls(false);
        setCanFly(true);
        wait = 18;
        countdown = 0;
        movingCountdown = 3;
        changeBehaviourTo("stop-1");
    }
    @Override
    public void moveEntity() {
        int translationOnX = translationVector2D.getTranslationOnX();
        int translationOnY = translationVector2D.getTranslationOnY();
        if(translationOnX != 0) {
            int signX = translationOnX / abs(translationOnX);
            setX(getX() + translationOnX);
            while ((entityManager.checkWallsCollisions(this) && !canPassThroughWalls)||
                    (entityManager.checkObstaclesCollisions(this) && !canFly)) {
                setX(getX() - signX);
            }
        }
        if(translationOnY != 0) {
            int signY = translationOnY / abs(translationOnY);
            setY(getY() + translationOnY);
            while ((entityManager.checkWallsCollisions(this) && !canPassThroughWalls)||
                    (entityManager.checkObstaclesCollisions(this) && !canFly)) {
                setY(getY() - signY);
            }
        }
    }

    @Override
    public void updateBehaviour() {
        if(checkActivation()) {
            switch (getCurrentBehaviour()) {
                case "stop-1" -> {
                    countdown++;
                    if (countdown > wait) {
                        changeBehaviourTo("aiming");
                    } else {
                        changeBehaviourTo("stop-2");
                    }
                }
                case "stop-2" -> {
                    countdown++;
                    if (countdown > wait) {
                        changeBehaviourTo("aiming");
                    } else {
                        changeBehaviourTo("stop-1");
                    }
                }
                case "aiming" -> {
                    Random random = new Random();
                    if(random.nextBoolean()){
                        int dX = getDeltaXToObjective(entityManager.getPlayerX());
                        int dY = getDeltaYToObjective(entityManager.getPlayerY());
                        translationVector2D.setAngulationFromCoordinates(dX, dY);
                    }
                    else {
                        int dX = random.nextInt();
                        int dY = random.nextInt();
                        translationVector2D.setAngulationFromCoordinates(dX, dY);
                    }
                    changeBehaviourTo("dashing");
                }
                case "dashing" -> {
                    movingCountdown--;
                    moveEntity();
                    if (movingCountdown <= 0) {
                        countdown = 0;
                        movingCountdown = 3;
                    }
                    if (countdown <= wait) {
                        changeBehaviourTo("stop-1");
                    }
                }
                default -> changeBehaviourTo("stop-1");
            }
        }
    }


}
