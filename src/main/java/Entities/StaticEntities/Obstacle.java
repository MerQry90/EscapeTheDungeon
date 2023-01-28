package Entities.StaticEntities;

import Components.EntityManager;
import Entities.GenericEntity;

import java.awt.*;

public class Obstacle extends GenericEntity {
	public Image rock;
	
	public Obstacle(int x, int y, int width, int height){
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		init();
	}
	
	@Override
	public void init() {
		rock = setSpriteFromPath("src/resources/sprites/png/rock.png");
		setCBwidthScalar(1);
		setCBheightScalar(1);
		initCollisionBox();
	}
}
