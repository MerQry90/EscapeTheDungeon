package Entities.StaticEntities.PowerUps;

import Components.Tile;
import Entities.DynamicEntities.Player;

import java.awt.*;
import java.util.Objects;

/**
 * Gives a key item to the player.
 * When 3 are collected, the Player can fight against the Boss.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Key extends PowerUp{
	
	private Image KEY;
	
	public Key(int x, int y) {
		super(x, y);
		KEY = setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Items/chiave.png")));
		setActiveSprite(KEY);
	}
	
	@Override
	public void activate(Player player) {
		player.addKey();
	}
}
