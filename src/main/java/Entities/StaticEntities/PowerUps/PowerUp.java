package Entities.StaticEntities.PowerUps;

import Entities.DynamicEntities.Player;
import Entities.GenericEntity;

/**
 * Generic description of a PowerUp, meaning an entity that performs an action when collides
 * with the player, then vanishes.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
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
