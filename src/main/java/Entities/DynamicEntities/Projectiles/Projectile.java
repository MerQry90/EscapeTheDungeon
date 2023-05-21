package Entities.DynamicEntities.Projectiles;

import Components.Background;
import Components.EntityManager;
import Entities.DynamicEntities.DynamicEntity;
import Entities.GenericEntity;

/**
 * it serves as a generic reference to all types of projectiles,
 * allows them to be in the same data structure.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public abstract class Projectile extends DynamicEntity {
	
	protected boolean stuckOnWall = false;
	
}
