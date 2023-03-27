package Entities.DynamicEntities.Projectiles;

import Components.EntityManager;
import Components.Vector2D;
import Entities.GenericEntity;

import java.awt.*;

import static java.lang.Math.abs;

public class Arrow extends Projectile {

	private Image ARROW_RIGHT, ARROW_LEFT, ARROW_UP, ARROW_DOWN;
	private int arrowDuration;
	
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
		ARROW_UP = setSpriteFromPath("src/resources/sprites/Projectiles/freccia_up.png");
		ARROW_DOWN = setSpriteFromPath("src/resources/sprites/Projectiles/freccia_down.png");
		ARROW_LEFT = setSpriteFromPath("src/resources/sprites/Projectiles/freccia_left.png");
		ARROW_RIGHT = setSpriteFromPath("src/resources/sprites/Projectiles/freccia_right.png");

		
		setWidth(40);
		setHeight(40);
		if(arrowOrientation.compareTo("right") == 0 || arrowOrientation.compareTo("left") == 0) {
			if(arrowOrientation.compareTo("right") == 0){
				setActiveSprite(ARROW_RIGHT);
			}
			else {
				setActiveSprite(ARROW_LEFT);
			}
			setCBwidthScalar(0.9);
			setCBheightScalar(0.5);
		}
		if(arrowOrientation.compareTo("up") == 0 || arrowOrientation.compareTo("down") == 0) {
			if(arrowOrientation.compareTo("up") == 0){
				setActiveSprite(ARROW_UP);
			}
			else  {
				setActiveSprite(ARROW_DOWN);
			}
			setCBwidthScalar(0.5);
			setCBheightScalar(0.9);
		}
		activateCollisionBox();
		arrowDuration = 60;
		setCanFly(true);

		translationVector2D = new Vector2D(arrowSpeed);
	}
	
	@Override
	public void moveEntity() {
		
		arrowDuration--;
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
		
		if(stuckOnWall){
			setInactive();
		}
		if(translationVector2D.getTranslationOnX() != 0) {
			int signX = translationVector2D.getTranslationOnX() / Math.abs(translationVector2D.getTranslationOnX());
			setX(getX() + translationVector2D.getTranslationOnX());
			while ((entityManager.checkWallsCollisions(this)) ||
					(entityManager.checkObstaclesCollisions(this))) {
				setX(getX() - signX);
				stuckOnWall = true;
			}
		}
		if(translationVector2D.getTranslationOnY() != 0) {
			int signY = translationVector2D.getTranslationOnY() / Math.abs(translationVector2D.getTranslationOnY());
			setY(getY() + translationVector2D.getTranslationOnY());
			while ((entityManager.checkWallsCollisions(this))||
					(entityManager.checkObstaclesCollisions(this))) {
				setY(getY() - signY);
				stuckOnWall = true;
			}
		}
		
		if(arrowDuration <= 0){
			setInactive();
		}
	}
}
