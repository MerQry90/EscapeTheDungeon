package Entities.StaticEntities;

import java.awt.*;

/**
 * Type of Hazard spawned by the Tank, it expands over time.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
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
