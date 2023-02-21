package Entities.DynamicEntities;

import Components.EntityManager;

import java.awt.*;

public class SlimeBall extends Projectile{

    private int objectiveX, objectiveY;
    private int duration = 60;
    private Image SLIME_BALL;

    public SlimeBall(int x, int y, int objectiveX, int objectiveY, EntityManager entityManager){
        this.entityManager = entityManager;
        setX(x);
        setY(y);
        this.objectiveX = objectiveX;
        this.objectiveY = objectiveY;
        init();
    }

    public void init(){
        SLIME_BALL = setSpriteFromPath("src/resources/sprites/projectiles/slimeball.png");
    }

    @Override
    public void moveEntity() {

    }
}
