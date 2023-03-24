package Entities.StaticEntities.PowerUps;

import Entities.DynamicEntities.Player;

import java.awt.*;

public class MultipleShot extends PowerUp{
	Image MULTIPLESHOT;

	public MultipleShot(int x, int y){
		super(x, y);
		MULTIPLESHOT = setSpriteFromPath("src/resources/sprites/png/multipleshot.png");
		setActiveSprite(MULTIPLESHOT);
	}

	@Override
	public void activate(Player player) {
		player.activateMultipleShot();
	}
}
