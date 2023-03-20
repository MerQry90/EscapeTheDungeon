package Entities.StaticEntities;

import java.awt.*;

public class BloodStain extends Hazard{
    private Image BLOODSTAIN;
    public BloodStain(int x, int y){
        super(x, y, 96, 96, 60);
    }

    @Override
    public void init() {
        BLOODSTAIN = setSpriteFromPath("src/resources/sprites/mapTiles/BossRoom.png");
        setActiveSprite(BLOODSTAIN);

        setWidth(96);
        setHeight(96);
        setCBwidthScalar(1);
        setCBheightScalar(1);
        activateCollisionBox();
    }
}
