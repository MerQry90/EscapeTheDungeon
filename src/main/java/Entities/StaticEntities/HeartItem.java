package Entities.StaticEntities;

import Entities.GenericEntity;

import java.awt.*;

/**
 * Heals the Player by 1 hearth when picked up.
 * Can randomly spawn when a room is completed.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class HeartItem extends GenericEntity {
	private Image HEART;

	public HeartItem(int x, int y){
		setX(x);
		setY(y);
		setWidth(32);
		setHeight(32);
		init();
		setActive();
	}

	@Override
	public void init() {
		HEART = setSpriteFromPath("src/resources/sprites/MainCharacter/Hearts/cuore.png");
		setActiveSprite(HEART);
		setCBwidthScalar(1);
		setCBheightScalar(1);
		activateCollisionBox();
	}
}
