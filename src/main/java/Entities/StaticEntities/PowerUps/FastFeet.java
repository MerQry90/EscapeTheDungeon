package Entities.StaticEntities.PowerUps;

import Entities.DynamicEntities.Player;

import java.awt.*;

public class FastFeet extends PowerUp{
	Image FAST_FEET;
	public FastFeet(int x, int y) {
		super(x, y);
		FAST_FEET = setSpriteFromPath("src/resources/sprites/png/fungus.png");
		setActiveSprite(FAST_FEET);
	}

	@Override
	public void activate(Player player) {
		player.setSpeed(player.getSpeed() + 3);
	}
}
