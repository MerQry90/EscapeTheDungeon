package Entities;

import java.util.Random;

import static java.lang.Math.*;

public class Zombie extends Enemy{
	
	public Zombie(int x, int y, int speed, int health, int movementCoolDown, int standByMovementCoolDown){
		super(health, movementCoolDown, standByMovementCoolDown);
		setX(x);
		setY(y);
		setSpeed(speed);
		setWidth(64); //tmp
		setHeight(64); //tmp
		setSprite("src/resources/sprites/png/zombie.png");
	}
	
	/*@Override
	public void moveTo(int dstX, int dstY) {
		if(checkIsAlive() && tryMovement()){
			int dx = dstX - getX();
			int dy = dstY - getY();
			int delta = abs(abs(dx) - abs(dy));
			if(delta <= 80){
				if(dx > 0 && dy > 0){
					moveDownRight();
				}
				else if (dx > 0 && dy < 0){
					moveUpRight();
				}
				else if(dx < 0 && dy > 0){
					moveDownLeft();
				}
				else {
					moveUpLeft();
				}
			}
			else{
				//moves on the x axis
				if(abs(dx) > abs(dy)){
					if(dx >= 0){
						moveRight();
					}
					else {
						moveLeft();
					}
				}
				//moves on the y axis
				else {
					if(dy >= 0){
						moveDown();
					}
					else {
						moveUp();
					}
				}
			}
		}
	}*/
	
	@Override
	public void updateBehaviour(int playerX, int playerY) {
		/*
		* angolo min = arctan(cateto min / cateto magg)
		* */
		int deltaX = playerX - getX();
		int deltaY = playerY - getY();
		
		double angle;
		int traslX, traslY;
		
		if(abs(deltaX) >= abs(deltaY)){
			angle = Math.atan((double) abs(deltaY) / abs(deltaX));
			traslX = (int) (getSpeed() * Math.cos(angle));
			traslY = (int) (getSpeed() * Math.sin(angle));
		}
		else {
			angle = Math.atan((double) abs(deltaX) / abs(deltaY));
			traslX = (int) (getSpeed() * Math.sin(angle));
			traslY = (int) (getSpeed() * Math.cos(angle));
		}
		if(deltaX < 0){
			traslX = traslX * -1;
		}
		if(deltaY < 0){
			traslY = traslY * -1;
		}
		setX(keepXBoundaries(getX() + traslX));
		setY(keepYBoundaries(getY() + traslY));
	}
}
