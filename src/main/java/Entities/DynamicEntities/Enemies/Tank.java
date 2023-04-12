package Entities.DynamicEntities.Enemies;

import Components.AudioManager;
import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.toRadians;

public class Tank extends Enemy{
   private ArrayList<Image> tankLeftSprites, tankRightSprites;
   private Image DEAD_TANK;

   private int  animationIndex;
    
   private int soundCountDown;
    
   private boolean performDeathAction;

    public Tank(int x, int y, EntityManager entityManager) {
        this.entityManager = entityManager;
        setX(x);
        setY(y);
        init();
    }
    @Override
    public void init() {
        tankLeftSprites = new ArrayList<>();
        tankRightSprites = new ArrayList<>();

        //CARICAMENTO SPRITE
        tankLeftSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank1_left.png"));
        tankLeftSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank2_left.png"));
        tankLeftSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank3_left.png"));
        tankLeftSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank4_left.png"));

        tankRightSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank1_right.png"));
        tankRightSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank2_right.png"));
        tankRightSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank3_right.png"));
        tankRightSprites.add(setSpriteFromPath("src/resources/sprites/Enemies/Tank/tank4_right.png"));

        DEAD_TANK = setSpriteFromPath("src/resources/sprites/png/deadMage.png");

        setActiveSprite(tankLeftSprites.get(0));
        animationIndex = 0;
        soundCountDown = 0;

        performDeathAction = true;
        
        //l'estremo è escluso, velocità a cui viene sommata maximumSpeed
        //verrà sommato a minimumSpeed

        setWidth(100);
        setHeight(100);
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
                    trySound();
                }
                case "dead" ->{
                    if(performDeathAction) {
                        performDeathAction = false;
                        disableCollisionBox();
                        setActiveSprite(DEAD_TANK);
                        entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.TANK_DEATH_INDEX);
                    }
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
                setActiveSprite(tankLeftSprites.get(0));
            }
            case 5 ->{
                setActiveSprite(tankLeftSprites.get(1));
            }
            case 10 ->{
                setActiveSprite(tankLeftSprites.get(2));
            }
            case 15 ->{
                setActiveSprite(tankLeftSprites.get(3));
                animationIndex = 0;
            }
        }
    }

    public void nextAnimationRight(){
        animationIndex += 1;

        switch (animationIndex){
            case 0 ->{
                setActiveSprite(tankRightSprites.get(0));
            }
            case 5 ->{
                setActiveSprite(tankRightSprites.get(1));
            }
            case 10 ->{
                setActiveSprite(tankRightSprites.get(2));
            }
            case 15 ->{
                setActiveSprite(tankRightSprites.get(3));
                animationIndex = 0;
            }
        }
    }
    
    public void trySound(){
        if(soundCountDown > 0){
            soundCountDown -= 1;
        }
        else {
            Random random = new Random();
            if(random.nextInt(0, 5) == 0){
                soundCountDown = 30 * 3;
                entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.TANK_SOUND_INDEX);
            }
        }
    }
}
