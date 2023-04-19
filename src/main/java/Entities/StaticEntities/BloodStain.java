package Entities.StaticEntities;

import java.awt.*;

public class BloodStain extends Hazard{
    private Image BLOODSTAIN;
    public BloodStain(int x, int y){
        super(x, y, 192, 192, 192);
    }

    @Override
    public void init() {
        BLOODSTAIN = setSpriteFromPath("src/resources/sprites/Items/BloodPots/sanguegrande.png");
        setActiveSprite(BLOODSTAIN);

        setWidth(192);
        setHeight(192);
        setCBwidthScalar(0.8);
        setCBheightScalar(0.8);
        activateCollisionBox();
    }
}
