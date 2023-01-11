package Entities;

import java.util.Random;

public class Zombie extends Entity{

	private boolean isAlive;

	public Zombie(int x, int y, int speed){
		isAlive = true;
		setX(x);
		setY(y);
		setSpeed(speed);
		setWidth(64);
		setHeight(64);
		setSprite("src/resources/sprites/png/zombie.png");
	}
}
