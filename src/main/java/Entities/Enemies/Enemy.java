package Entities.Enemies;

import Entities.GenericEntity;

import java.util.Random;

public abstract class Enemy extends GenericEntity {
	
	private int health;
	Random random;
	
	public Enemy(int x, int y){
		super(x, y);
	}

	public void setRandomHealth(int minimumHealth, int maximumHealth){
		random = new Random();
		int health = random.nextInt(minimumHealth) + maximumHealth;
		this.health = health;
	}
	
	public void lowerHealth(){
		health -= 1;
		if(health <= 0){
			setInactive();
		}
	}
	public void setRandomSpeed(int minimumSpeed, int maximumSpeed){
		random = new Random();
		int speed = random.nextInt(minimumSpeed) + maximumSpeed;
		setSpeed(speed);
	}
	
	public abstract void updateBehaviour(int playerX, int playerY);
}
