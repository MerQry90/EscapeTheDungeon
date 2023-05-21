package Entities.StaticEntities.PowerUps;

import Entities.DynamicEntities.Player;

import java.awt.*;

/**
 * Makes the Player faster in its movement capabilities.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class FastFeet extends PowerUp{
	Image FAST_FEET;
	public FastFeet(int x, int y) {
		super(x, y);
		FAST_FEET = setSpriteFromPath("src/resources/sprites/PowerUps/fast.png");
		setActiveSprite(FAST_FEET);
	}

	@Override
	public void activate(Player player) {
		player.setSpeed(player.getSpeed() + 3);
		player.showPowerUpMessageFF = true;
	}
}
