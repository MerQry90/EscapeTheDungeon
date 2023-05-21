package Entities.StaticEntities;

import Entities.GenericEntity;

/**
 * Generic implementation of a non-moving entity that cannot perform any action.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
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
		activateCollisionBox();
	}
}
