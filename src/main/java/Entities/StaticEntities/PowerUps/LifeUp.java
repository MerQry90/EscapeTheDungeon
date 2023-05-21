package Entities.StaticEntities.PowerUps;

import Entities.DynamicEntities.Player;

import java.awt.*;

/**
 * Increases the Player's health by 2 hearts.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class LifeUp extends PowerUp{
	Image LIFE_UP;

	public LifeUp(int x, int y){
		super(x, y);
		LIFE_UP = setSpriteFromPath("src/resources/sprites/PowerUps/onelife.png");
		setActiveSprite(LIFE_UP);
	}

	@Override
	public void activate(Player player) {
		for(int i = 0; i < 2; i++) {
			if (player.getMaxHealth() < 6) {
				player.setMaxHealth(player.getMaxHealth() + 1);
			}
			if (player.getHealth() < player.getMaxHealth()) {
				player.setHealth(player.getHealth() + 1);
			}
		}
		player.showPowerUpMessageLU = true;
	}
}
