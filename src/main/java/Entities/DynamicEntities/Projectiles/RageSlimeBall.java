package Entities.DynamicEntities.Projectiles;

import Components.EntityManager;
import Entities.GenericEntity;

import java.awt.*;

import static java.lang.Math.*;

public class RageSlimeBall extends Projectile{
	private Image SLIME_BALL;

	private int centerX, centerY;
	private double angulation;
	private double distance;
	private EntityManager entityManager;
	private int duration;
	public RageSlimeBall(int centerX, int centerY, double angulation, EntityManager entityManager){
		this.centerX = centerX;
		this.centerY = centerY;
		this.angulation = angulation;
		this.entityManager = entityManager;
		init();
	}
	@Override
	public void init() {
		SLIME_BALL = GenericEntity.setSpriteFromPath("src/resources/sprites/projectiles/slimeball.png");
		setActiveSprite(SLIME_BALL);

		distance = 30;
		duration = 30 * 10;

		setWidth(24);
		setHeight(24);
		setCBwidthScalar(0.9);
		setCBheightScalar(0.9);
		activateCollisionBox();

		updateRageSlimeBall();
	}
	@Override
	public void moveEntity() {
		distance += 3;
		updateRageSlimeBall();
	}

	public void updateRageSlimeBall(){
		duration -= 1;
		int tx = (int) round(distance * cos(angulation));
		int ty = (int) round(distance * sin(angulation));
		setXFromCenter(centerX + tx);
		setYFromCenter(centerY + ty);
		if(duration <= 0){
			setInactive();
		}
	}
}
