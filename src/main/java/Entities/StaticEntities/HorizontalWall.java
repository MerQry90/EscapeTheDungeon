package Entities.StaticEntities;

import Components.EntityManager;
import Entities.GenericEntity;

public class HorizontalWall extends StaticEntity {
	public HorizontalWall(int x, int y, EntityManager entityManager) {
		setX(x);
		setY(y);
		this.entityManager = entityManager;
		init();
	}

	@Override
	public void init() {
		setWidth(64 * 15);
		setHeight(32);
		setCBwidthScalar(1.0);
		setCBheightScalar(1.0);
		initCollisionBox();
	}
}
