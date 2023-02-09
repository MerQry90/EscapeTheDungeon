package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.abs;

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
		if(arrowOrientation.compareTo("right") == 0 || arrowOrientation.compareTo("left") == 0) {
			setCBwidthScalar(0.9);
			setCBheightScalar(0.5);
		}
		if(arrowOrientation.compareTo("up") == 0 || arrowOrientation.compareTo("down") == 0) {
			setCBwidthScalar(0.5);
			setCBheightScalar(0.9);
		}
		initCollisionBox();
		arrowCountdown = 35;
		setCanFly(true);

		translationVector2D = new Vector2D(arrowSpeed);
	}

	public void move() {
		arrowCountdown--;
		int oldX = this.getX();
		int oldY = this.getY();
		switch (arrowOrientation){
			case "up" ->{
				translationVector2D.setAngulationFromCoordinates(0, -1);
			}
			case "down" ->{
				translationVector2D.setAngulationFromCoordinates(0, 1);
			}
			case "right" ->{
				translationVector2D.setAngulationFromCoordinates(1, 0);
			}
			case "left" ->{
				translationVector2D.setAngulationFromCoordinates(-1, 0);
			}
		}
		moveEntity();
		if(arrowCountdown <= 0){
			setInactive();
		}
	}
	
	@Override
	public void moveEntity() {
		int translationOnX = translationVector2D.getTranslationOnX();
		int translationOnY = translationVector2D.getTranslationOnY();
		if(translationOnX != 0) {
			int signX = translationOnX / abs(translationOnX);
			setX(getX() + translationOnX);
			while ((entityManager.checkWallsCollisions(this) && !canPassThroughWalls)||
					(entityManager.checkObstaclesCollisions(this) && !canFly)) {
				setX(getX() - signX);
			}
		}
		if(translationOnY != 0) {
			int signY = translationOnY / abs(translationOnY);
			setY(getY() + translationOnY);
			while ((entityManager.checkWallsCollisions(this) && !canPassThroughWalls)||
					(entityManager.checkObstaclesCollisions(this) && !canFly)) {
				setY(getY() - signY);
			}
		}
	}
}
