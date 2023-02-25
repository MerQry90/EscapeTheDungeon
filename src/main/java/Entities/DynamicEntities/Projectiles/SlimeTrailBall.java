package Entities.DynamicEntities.Projectiles;

import Components.EntityManager;
import Entities.GenericEntity;

import java.awt.*;

public class SlimeTrailBall extends Projectile{

	private Image SLIME_BALL;
	private String direction;
	private EntityManager entityManager;
	private int slimePuddleCountdown;

	public SlimeTrailBall(int centerX, int centerY, String direction, EntityManager entityManager){

		this.direction = direction;
		this.entityManager = entityManager;

		setXFromCenter(centerX);
		setYFromCenter(centerY);

		init();
	}

	@Override
	public void init() {
		SLIME_BALL = GenericEntity.setSpriteFromPath("src/resources/sprites/projectiles/slimeball.png");
		setActiveSprite(SLIME_BALL);

		slimePuddleCountdown = 0;

		setWidth(24);
		setHeight(24);
		setCBwidthScalar(0.9);
		setCBheightScalar(0.9);
		initCollisionBox();

	}

	@Override
	public void moveEntity() {
		if (slimePuddleCountdown <= 0){
			entityManager.generateSlimePuddle(this.getCenterX(), this.getCenterY());
			slimePuddleCountdown = 10;
		}
		slimePuddleCountdown -= 1;
		if(entityManager.checkWallsCollisions(this)){
			entityManager.generateSlimePuddle(this.getCenterX(), this.getCenterY());
			setInactive();
		}
		switch (direction){
			case "right" ->{
				setX(getX() + 5);
			}
			case "left" ->{
				setX(getX() - 5);
			}
			case "up" ->{
				setY(getY() - 5);
			}
			case "down" ->{
				setY(getY() + 5);
			}
		}
	}
}
