package Entities.StaticEntities;

import java.awt.*;

public class SlimePuddle extends Hazard{
	private Image SLIME_PUDDLE;
	public SlimePuddle(int x, int y){
		super(x, y, 64, 64, 30 * 5);
	}

	@Override
	public void init() {
		SLIME_PUDDLE = setSpriteFromPath("src/resources/sprites/Enemies/Boss/trail/striaslime1.png");
		setActiveSprite(SLIME_PUDDLE);

		setWidth(64);
		setHeight(64);
		setCBwidthScalar(1);
		setCBheightScalar(1);
		activateCollisionBox();
	}
}
