package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;
import Entities.GenericEntity;

import java.awt.*;

public class Arrow extends Projectile {

	private Image RIGHT_ARROW;
	private int arrowCountdown;
	
	//la freccia deve essere visibile finché non collide con qualcosa
	private final int arrowSpeed = 20;
	private String arrowOrientation;

	public Arrow(int x, int y, String arrowOrientation, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.arrowOrientation = arrowOrientation;
		setX(x);
		setY(y);
		init();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		RIGHT_ARROW = setSpriteFromPath("src/resources/sprites/projectiles/arrow.png");
		setActiveSprite(RIGHT_ARROW);
		
		setWidth(40);
		setHeight(40);
		setCBwidthScalar(0.9);
		setCBheightScalar(0.5);
		initCollisionBox();
		arrowCountdown = 35;
		setCanFly(true);

		translation = new Vector2D(arrowSpeed);
	}

	public void move() {
		arrowCountdown--;
		int oldX = this.getX();
		int oldY = this.getY();
		switch (arrowOrientation){
			case "up" ->{
				translation.setAngulationToObjective(0, -1);
			}
			case "down" ->{
				translation.setAngulationToObjective(0, 1);
			}
			case "right" ->{
				translation.setAngulationToObjective(1, 0);
			}
			case "left" ->{
				translation.setAngulationToObjective(-1, 0);
			}
		}
		moveEntity(translation.getXTranslation(), translation.getYTranslation());
		if(arrowCountdown <= 0){
			setInactive();
		}
	}
}
