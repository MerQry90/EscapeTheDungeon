package Entities.StaticEntities;

import java.awt.*;

public class Rock extends Obstacle{

	private Image ROCK_IMAGE;

	public Rock(int x, int y) {
		super(x, y, 64, 64);
	}

	@Override
	public void init() {
		ROCK_IMAGE = setSpriteFromPath("src/resources/sprites/Rocks/roccia.png");
		setActiveSprite(ROCK_IMAGE);
		setCBwidthScalar(1);
		setCBheightScalar(1);
		activateCollisionBox();
	}
}
