package Entities.DynamicEntities.Projectiles;

import Components.EntityManager;
import Components.Vector2D;
import Entities.GenericEntity;

import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.toRadians;

/**
 * Projectile shot by the player to damage enemies.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Arrow extends Projectile {

	public static final int ORIENTATION_UP_STRAIGHT = 0;
	public static final int ORIENTATION_UP_toLEFT = 1;
	public static final int ORIENTATION_UP_toRIGHT = 2;
	public static final int ORIENTATION_DOWN_STRAIGHT = 3;
	public static final int ORIENTATION_DOWN_toLEFT = 4;
	public static final int ORIENTATION_DOWN_toRIGHT = 5;
	public static final int ORIENTATION_LEFT_STRAIGHT = 6;
	public static final int ORIENTATION_LEFT_toUP = 7;
	public static final int ORIENTATION_LEFT_toDOWN = 8;
	public static final int ORIENTATION_RIGHT_STRAIGHT = 9;
	public static final int ORIENTATION_RIGHT_toUP = 10;
	public static final int ORIENTATION_RIGHT_toDOWN = 11;

	private Image ARROW_RIGHT, ARROW_LEFT, ARROW_UP, ARROW_DOWN;
	private int arrowDuration;
	
	//la freccia deve essere visibile finché non collide con qualcosa
	private final int arrowSpeed = 20;
	private int arrowOrientation;

	/**
	 * Initializes sprites, animations, speed, CollisionBox and the parameters needed to move the arrow.
	 * @param x Coordinate where the arrow will be initially located
	 * @param y Coordinate where the arrow will be initially located
	 * @param arrowOrientation Values that determines the arrow orientation.<br>
	 *                         0-5: vertical arrow, with 0-2 facing up and 3-5 facing down<br>
	 *                         6-11: horizontal arrow, with 6-8 facing left and 9-11 facing right<br>
	 * @param entityManager Necessary to check collisions with other entities
	 */
	public Arrow(int x, int y, int arrowOrientation, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.arrowOrientation = arrowOrientation;
		setX(x);
		setY(y);
		init();
	}

	public boolean checkIfHorizontal(){
		return arrowOrientation >= 6 && arrowOrientation <= 11;
	}
	public boolean checkIfRight(){
		return arrowOrientation >= 9 && arrowOrientation <= 11;
	}
	public boolean checkIfUp(){
		return arrowOrientation >= 0 && arrowOrientation <= 2;
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
		if(checkIfHorizontal()) {
			if(checkIfRight()){
				setActiveSprite(ARROW_RIGHT);
			}
			else {
				setActiveSprite(ARROW_LEFT);
			}
			setCBwidthScalar(0.9);
			setCBheightScalar(0.5);
		}
		else {
			if(checkIfUp()){
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
		setCanFly(false);

		translationVector2D = new Vector2D(arrowSpeed);
	}
	
	@Override
	public void moveEntity() {
		int epsilon = 20;
		arrowDuration--;
		switch (arrowOrientation){
			case ORIENTATION_UP_toLEFT ->{
				translationVector2D.setAngulation(toRadians(270 - epsilon));
			}
			case ORIENTATION_UP_STRAIGHT ->{
				translationVector2D.setAngulation(toRadians(270));
			}
			case ORIENTATION_UP_toRIGHT ->{
				translationVector2D.setAngulation(toRadians(270 + epsilon));
			}
			case ORIENTATION_RIGHT_toUP ->{
				translationVector2D.setAngulation(toRadians(-epsilon));
			}
			case ORIENTATION_RIGHT_STRAIGHT ->{
				translationVector2D.setAngulation(toRadians(0));
			}
			case ORIENTATION_RIGHT_toDOWN ->{
				translationVector2D.setAngulation(toRadians(epsilon));
			}
			case ORIENTATION_DOWN_toRIGHT ->{
				translationVector2D.setAngulation(toRadians(90 - epsilon));
			}
			case ORIENTATION_DOWN_STRAIGHT ->{
				translationVector2D.setAngulation(toRadians(90));
			}
			case ORIENTATION_DOWN_toLEFT ->{
				translationVector2D.setAngulation(toRadians(90 + epsilon));
			}
			case ORIENTATION_LEFT_toDOWN ->{
				translationVector2D.setAngulation(toRadians(180 - epsilon));
			}
			case ORIENTATION_LEFT_STRAIGHT ->{
				translationVector2D.setAngulation(toRadians(180));
			}
			case ORIENTATION_LEFT_toUP ->{
				translationVector2D.setAngulation(toRadians(180 + epsilon));
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
