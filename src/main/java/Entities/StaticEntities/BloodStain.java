package Entities.StaticEntities;

import Entities.StaticEntities.Obstacle;

import java.awt.*;

public class BloodStain extends Obstacle {
    private Image BLOODSTAIN;
    public BloodStain(int x, int y){
        super(x, y, 64, 64);
    }

    @Override
    public void init() {
        BLOODSTAIN = setSpriteFromPath("src/resources/sprites/mapTiles/BossRoom.png");
        setActiveSprite(BLOODSTAIN);

        setWidth(64);
        setHeight(64);
        setCBwidthScalar(1);
        setCBheightScalar(1);
        initCollisionBox();
    }
}
