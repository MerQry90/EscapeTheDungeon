package Entities.Boundaries;

import Entities.EntityManager;
import Entities.GenericEntity;

public class VerticalWall extends GenericEntity {
	public VerticalWall(int x, int y, EntityManager entityManager) {
		super(x, y, entityManager);
	}

	@Override
	public void init() {
		setSpeed(0);
		setWidth(64);
		setHeight(1088);
		setCBwidthScalar(1.0);
		setCBheightScalar(1.0);
	}
}
