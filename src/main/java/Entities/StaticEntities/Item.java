package Entities.StaticEntities;

import Entities.GenericEntity;

import java.awt.*;

public class Item extends GenericEntity {
	private Image HEART;
	private String item;

	public Item(int x, int y){
		setX(x);
		setY(y);
		setWidth(32);
		setHeight(32);
		init();
		setInactive();
	}

	@Override
	public void init() {
		HEART = setSpriteFromPath("src/resources/sprites/png/full_heart.png");
		setActiveSprite(HEART);
		//KEY = setSpriteFromPath()
		setCBwidthScalar(1);
		setCBheightScalar(1);
		initCollisionBox();
	}
}
