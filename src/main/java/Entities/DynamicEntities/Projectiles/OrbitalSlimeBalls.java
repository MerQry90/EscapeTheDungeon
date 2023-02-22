package Entities.DynamicEntities.Projectiles;

import Components.EntityManager;
import Entities.GenericEntity;

import java.awt.*;

public class OrbitalSlimeBalls extends Projectile{
	
	private int duration;
	private Image SLIME_BALL;
	
	private int centerX, centerY;
	
	public OrbitalSlimeBalls(int centerX, int centerY, int startingX, int startingY, int duration, EntityManager entityManager){
		setX(startingX);
		setY(startingY);
		this.centerX = centerX;
		this.centerY = centerY;
		this.duration = duration;
		this.entityManager = entityManager;
		init();
	}
	
	@Override
	public void init() {
		SLIME_BALL = GenericEntity.setSpriteFromPath("src/resources/sprites/projectiles/slimeball.png");
	}
	
	@Override
	public void moveEntity() {
	
	}
}
