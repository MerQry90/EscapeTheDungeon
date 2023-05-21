package Entities.StaticEntities.PowerUps;

import Entities.DynamicEntities.Player;

import java.awt.*;

/**
 * Makes the Player shoot 3 arrows instead if 1.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class MultipleShot extends PowerUp{
	Image MULTIPLESHOT;

	public MultipleShot(int x, int y){
		super(x, y);
		MULTIPLESHOT = setSpriteFromPath("src/resources/sprites/PowerUps/trpleshot.png");
		setActiveSprite(MULTIPLESHOT);
	}

	@Override
	public void activate(Player player) {
		player.activateMultipleShot();
		player.showPowerUpMessageMS = true;
	}
}
