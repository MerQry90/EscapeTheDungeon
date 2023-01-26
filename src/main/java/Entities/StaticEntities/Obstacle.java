package Entities.StaticEntities;

import Components.EntityManager;
import Entities.GenericEntity;

public class Obstacle extends GenericEntity {
	
	public Obstacle(int x, int y, int width, int height){
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		init();
	}
	
	@Override
	public void init() {
		setCBwidthScalar(1);
		setCBheightScalar(1);
		initCollisionBox();
	}
}
