package Entities.StaticEntities.PowerUps;

import Components.Tile;
import Entities.DynamicEntities.Player;

import java.awt.*;

public class Key extends PowerUp{
	
	private Image KEY;
	
	public Key(int x, int y) {
		super(x, y);
		KEY = setSpriteFromPath("src/resources/sprites/png/key.png");
		setActiveSprite(KEY);
	}
	
	@Override
	public void activate(Player player) {
		player.addKey();
	}
}
