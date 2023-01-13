package Entities;

import java.util.Random;

import static java.lang.Math.abs;

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
	
	@Override
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
	}
}
