package Entities.StaticEntities;

import Components.EntityManager;
import Entities.GenericEntity;

public class HorizontalWall extends StaticEntity {
	public HorizontalWall(int x, int y, EntityManager entityManager) {
		super(x, y, entityManager);
	}

	@Override
	public void init() {
		setSpeed(0);
		setWidth(64 * 15);
		setHeight(32);
		setCBwidthScalar(1.0);
		setCBheightScalar(1.0);
	}
}
