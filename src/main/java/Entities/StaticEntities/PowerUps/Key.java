package Entities.StaticEntities.PowerUps;

import Components.Tile;
import Entities.DynamicEntities.Player;

import java.awt.*;

public class Key extends PowerUp{
	
	private Image KEY;
	
	public Key() {
		super(Tile.getTile(3), Tile.getTile(3));
		KEY = setSpriteFromPath("src/resources/sprites/png/key.png");
		setActiveSprite(KEY);
	}
	
	@Override
	public void activate(Player player) {
		player.addKey();
	}
}
