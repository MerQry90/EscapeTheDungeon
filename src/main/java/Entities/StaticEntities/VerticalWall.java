package Entities.StaticEntities;

import Components.EntityManager;
import Entities.GenericEntity;

public class VerticalWall extends StaticEntity {
	public VerticalWall(int x, int y, EntityManager entityManager) {
		super(x, y, entityManager);
	}

	@Override
	public void init() {
		setWidth(64);
		setHeight(64 * 7);
		setCBwidthScalar(1.0);
		setCBheightScalar(1.0);
	}
}
