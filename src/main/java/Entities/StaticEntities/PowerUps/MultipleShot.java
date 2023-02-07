package Entities.StaticEntities.PowerUps;

import Entities.DynamicEntities.Player;

import java.awt.*;

public class MultipleShot extends PowerUp{
	Image MULTIPLESHOT;

	public MultipleShot(){
		super(64 * 6, 64 * 5);
		MULTIPLESHOT = setSpriteFromPath("src/resources/sprites/png/multipleshot.png");
		setActiveSprite(MULTIPLESHOT);
	}

	@Override
	public void activate(Player player) {
		player.activateMultipleShot();
	}
}
