package Entities.DynamicEntities.Projectiles;

import Components.EntityManager;
import Entities.GenericEntity;

import java.awt.*;

public class DirectSlimeBalls extends Projectile{
    
    private float angulation;
    private Image SLIME_BALL;

    public DirectSlimeBalls(int x, int y, float angulation, EntityManager entityManager){
        this.entityManager = entityManager;
        setX(x);
        setY(y);
        this.angulation = angulation;
        init();
    }

    public void init(){
        SLIME_BALL = GenericEntity.setSpriteFromPath("src/resources/sprites/projectiles/slimeball.png");
    }

    @Override
    public void moveEntity() {

    }
}
