package Entities.StaticEntities;

import java.awt.*;

/**
 * Type of Hazard spawned by the Boss, it expands over time.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class SlimePuddle extends Hazard{
	private Image SLIME_PUDDLE_VERTICAL;
	private Image SLIME_PUDDLE_HORIZONTAL;
	
	public SlimePuddle(int x, int y, boolean isVertical){
		super(x, y, 64, 64, 30 * 5);
		init();
		if(isVertical){
			setActiveSprite(SLIME_PUDDLE_VERTICAL);
		}
		else {
			setActiveSprite(SLIME_PUDDLE_HORIZONTAL);
		}
	}

	@Override
	public void init() {
		SLIME_PUDDLE_VERTICAL = setSpriteFromPath("src/resources/sprites/Enemies/Boss/trail/striaslime1_verticale.png");
		SLIME_PUDDLE_HORIZONTAL = setSpriteFromPath("src/resources/sprites/Enemies/Boss/trail/striaslime1_orizzontale.png");

		setWidth(64);
		setHeight(64);
		setCBwidthScalar(1);
		setCBheightScalar(1);
		activateCollisionBox();
	}
}
