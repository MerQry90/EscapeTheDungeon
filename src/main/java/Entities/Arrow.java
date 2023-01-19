package Entities;

import java.awt.*;

public class Arrow extends GenericEntity {

	private Image RIGHT_ARROW;
	
	//la freccia deve essere visibile finché non collide con qualcosa
	private final int arrowSpeed = 20;
	
	/*
	axis = true: asse x
	axis = false: asse y
 	*/
	private boolean axis, direction;

	public Arrow(int x, int y, boolean axis, boolean direction) {
		super(x, y);
		this.axis = axis;
		this.direction = direction;
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
		setSpeed(arrowSpeed);
	}

	public boolean getAxis() {
		return axis;
	}

	public boolean getDirection() {
		return direction;
	}

	/*public int getArrowSpeed(){
		return arrowSpeed;
	}*/

	public void checkBoundaries(){
		if((getX() <= Background.LEFT_BOUND && axis && !direction)
				|| (getX() >= Background.RIGHT_BOUND - getWidth() && axis && direction)
				|| (getY() <= Background.UPPER_BOUND && !axis && !direction)
				|| (getY() >= Background.LOWER_BOUND - getHeight() && !axis && direction)){
			setInactive();
		}
	}
}
