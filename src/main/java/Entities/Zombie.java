package Entities;

import java.util.Random;

public class Zombie extends Enemy{
	
	public Zombie(int x, int y, int speed, int health, int movementCoolDown, int standByMovementCoolDown){
		super(health, movementCoolDown, standByMovementCoolDown);
		setX(x);
		setY(y);
		setSpeed(speed);
		setWidth(64);
		setHeight(64);
		setSprite("src/resources/sprites/png/zombie.png");
	}
	
	
}
