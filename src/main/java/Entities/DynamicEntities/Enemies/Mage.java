package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;
import Entities.DynamicEntities.Projectiles.MagicBall;

import java.awt.*;
import java.util.Random;

public class Mage extends Enemy{

    private Image MAGE_IDLE, MAGE_SHOOTING, DEAD_MAGE;
    private Image MAGETP1, MAGETP2, MAGETP3, MAGETP4, MAGETP5;
    
    private int idleCountdown, shotNumber, animationIndex;
    public Mage(int x, int y, EntityManager entityManager){
        setX(x);
        setY(y);
        this.entityManager = entityManager;
        init();
    }
    @Override
    public void init() {
        MAGE_IDLE = setSpriteFromPath("src/resources/sprites/Enemies/Mage/magikino1.png");
        MAGE_SHOOTING  = setSpriteFromPath("src/resources/sprites/Enemies/Mage/magikino2.png");

        MAGETP1 = setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline1.png");
        MAGETP2 = setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline2.png");
        MAGETP3 = setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline3.png");
        MAGETP4 = setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline4.png");
        MAGETP5 = setSpriteFromPath("src/resources/sprites/Enemies/Mage/stelline/stelline5.png");

        DEAD_MAGE = setSpriteFromPath("src/resources/sprites/png/deadMage.png");

        setActiveSprite(MAGE_IDLE);
        setWidth(64);
        setHeight(64);
        setCBwidthScalar(0.8);
        setCBheightScalar(0.9);
        activateCollisionBox();

        translationVector2D = new Vector2D(0);
        setRandomHealth(3, 3);
        setCanPassThroughWalls(false);
        setCanFly(false);

        idleCountdown = 30;
        shotNumber = 0;
        animationIndex = 0;
    }

    @Override
    public void updateBehaviour() {
        if(checkActivation()) {
            if(!checkIfActive()){
                changeBehaviourTo("dead");
            }
            switch (getCurrentBehaviour()) {
                case "idle" -> {
                    if(shotNumber >= 3){
                        setActiveSprite(MAGE_IDLE);
                        shotNumber = 0;
                        nextAnimation();
                        changeBehaviourTo("teleport");
                    }
                    if(idleCountdown <= 0){
                        setActiveSprite(MAGE_SHOOTING);
                        changeBehaviourTo("shoot");
                    }
                    else if(idleCountdown > 30){
                        nextAnimation();
                    }
                    else {
                        setActiveSprite(MAGE_IDLE);
                    }
                    idleCountdown -= 1;
                }
                case "shoot" -> {
                    setActiveSprite(MAGE_SHOOTING);
                    entityManager.newHostileProjectile(new MagicBall(getX(), getY(),
                            entityManager.getPlayerX(), entityManager.getPlayerY(), entityManager));
                    idleCountdown = 10;
                    shotNumber += 1;
                    changeBehaviourTo("idle");
                }
                case  "teleport" ->{
                    idleCountdown = 40;
                    moveEntity();
                    changeBehaviourTo("idle");
                }
                case "dead" -> {
                    disableCollisionBox();
                    setActiveSprite(DEAD_MAGE);
                }
                default -> {
                    changeBehaviourTo("idle");
                }
            }
        }
    }

    public void nextAnimation(){
        animationIndex += 1;
        switch (animationIndex){
            case 0 ->{
                setActiveSprite(MAGETP1);
            }
            case 2 ->{
                setActiveSprite(MAGETP2);
            }
            case 4 ->{
                setActiveSprite(MAGETP3);
            }
            case 6 ->{
                setActiveSprite(MAGETP4);
            }
            case 8 ->{
                setActiveSprite(MAGETP5);
                animationIndex = 0;
            }
        }
    }

    @Override
    public void moveEntity() {
        Random random = new Random();
        int minimumSpace = 128;
        int bound = 128;
        int randomX, randomY;
        do{
            randomX = entityManager.getPlayerX();
            randomY = entityManager.getPlayerY();
            if(random.nextBoolean()){
                randomX += random.nextInt(bound) + minimumSpace;
            }
            else {
                randomX -= random.nextInt(bound) + minimumSpace;
            }
            if(random.nextBoolean()){
                randomY += random.nextInt(bound) + minimumSpace;
            }
            else {
                randomY -= random.nextInt(bound) + minimumSpace;
            }
            setX(randomX);
            setY(randomY);
        }
        while(getX() < 65 || getX() > 960 || getY() < 65 || getY() > 448 || entityManager.checkObstaclesCollisions(this));
    }
}
