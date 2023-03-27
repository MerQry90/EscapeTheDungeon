package Entities.StaticEntities;

import Entities.GenericEntity;

import java.awt.*;

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
		HEART = setSpriteFromPath("src/resources/sprites/png/full_heart.png");
		setActiveSprite(HEART);
		setCBwidthScalar(1);
		setCBheightScalar(1);
		activateCollisionBox();
	}
}
