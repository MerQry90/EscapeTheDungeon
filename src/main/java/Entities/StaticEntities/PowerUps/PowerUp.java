package Entities.StaticEntities.PowerUps;

import Entities.DynamicEntities.Player;
import Entities.GenericEntity;

public abstract class PowerUp extends GenericEntity {
	public PowerUp(int x, int y){
		setX(x);
		setY(y);
		setWidth(64);
		setHeight(64);
		init();
		setActive();
	}

	@Override
	public void init() {
		setCBwidthScalar(1);
		setCBheightScalar(1);
		activateCollisionBox();
	}

	public abstract void activate(Player player);
}
