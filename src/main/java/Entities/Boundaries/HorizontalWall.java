package Entities.Boundaries;

import Entities.EntityManager;
import Entities.GenericEntity;

public class HorizontalWall extends GenericEntity {
	public HorizontalWall(int x, int y, EntityManager entityManager) {
		super(x, y, entityManager);
	}

	@Override
	public void init() {
		setSpeed(0);
		setWidth(1088);
		setHeight(64);
		setCBwidthScalar(1.0);
		setCBheightScalar(1.0);
	}
}
