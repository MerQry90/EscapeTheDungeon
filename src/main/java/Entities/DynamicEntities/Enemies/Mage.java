package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;
import java.util.Random;

public class Mage extends Enemy{

    public Image MAGE;
    private int idleCountdown, teleportCountdown, shotNumber;
    public Mage(int x, int y, EntityManager entityManager){
        setX(x);
        setY(y);
        this.entityManager = entityManager;
        init();
    }
    @Override
    public void init() {
        MAGE = setSpriteFromPath("src/resources/sprites/png/Mage.png");
        setActiveSprite(MAGE);
        setWidth(64);
        setHeight(64);
        setCBwidthScalar(0.8);
        setCBheightScalar(0.9);
        initCollisionBox();

        translationVector2D = new Vector2D(0);
        setRandomHealth(3, 3);
        setCanPassThroughWalls(false);
        setCanFly(false);
        changeBehaviourTo("teleport");

        idleCountdown = 30;
        teleportCountdown = 20;
        shotNumber = 0;
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
                        shotNumber = 0;
                        changeBehaviourTo("teleport");
                    }
                    if(idleCountdown <= 0){
                        changeBehaviourTo("shoot");
                    }
                    idleCountdown -= 1;
                }
                case "shoot" -> {
                    entityManager.newHostileProjectile(getX(), getY(), entityManager.getPlayerX(), entityManager.getPlayerY(), this);
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
                    //cambio di sprite
                }
                default -> {
                    changeBehaviourTo("idle");
                }
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
        while(getX() < 65 || getX() > 960 || getY() < 65 || getY() > 448);
    }
}
