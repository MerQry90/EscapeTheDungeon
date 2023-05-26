package Entities.StaticEntities;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

/**
 * Obstacle in the room that blocks the Player,
 * it can be destroyed by the Tank.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Rock extends Obstacle{

	private Image ROCK_IMAGE, ROCK_IMAGE2;

	public Rock(int x, int y) {
		super(x, y, 64, 64);
	}

	@Override
	public void init() {
		ROCK_IMAGE = setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Rocks/roccia.png")));
		ROCK_IMAGE2 = setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Rocks/roccia2.png")));

		Random random = new Random();
		if(random.nextBoolean()){
			setActiveSprite(ROCK_IMAGE);
		}
		else {
			setActiveSprite(ROCK_IMAGE2);
		}

		setCBwidthScalar(1);
		setCBheightScalar(1);
		activateCollisionBox();
	}
}
