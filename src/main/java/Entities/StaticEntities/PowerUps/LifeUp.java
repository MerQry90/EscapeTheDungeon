package Entities.StaticEntities.PowerUps;

import Entities.DynamicEntities.Player;

import java.awt.*;

public class LifeUp extends PowerUp{
	Image LIFE_UP;

	public LifeUp(){
		super(64 * 10, 64 * 5);
		LIFE_UP = setSpriteFromPath("src/resources/sprites/png/fungus.png");
		setActiveSprite(LIFE_UP);
	}

	@Override
	public void activate(Player player) {
		if(player.getMaxHealth() < 6) {
			player.setMaxHealth(player.getMaxHealth() + 1);
		}
		if(player.getHealth() < player.getMaxHealth()){
			player.setHealth(player.getHealth() + 1);
		}
	}
}
