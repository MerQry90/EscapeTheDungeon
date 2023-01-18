package Entities.Enemies;

import Entities.GenericEntity;

import java.util.Random;

public abstract class Enemy extends GenericEntity {
	
	private int health;
	Random random;
	private int minimumSpeed, maximumSpeed, minimumHealth, maximumHealth;
	
	public Enemy(int x, int y){
		super(x, y);
	}

	public void setHealth(){
		this.health = initializeRandomHealth();
	}
	
	public void lowerHealth(){
		health -= 1;
		if(health <= 0){
			setInactive();
		}
	}

	public void setMinimumSpeed(int minimumSpeed) {
		this.minimumSpeed = minimumSpeed;
	}

	public void setMaximumSpeed(int maximumSpeed){
		this.maximumSpeed = maximumSpeed;
	}

	public void setMinimumHealth(int minimumHealth) {
		this.minimumHealth = minimumHealth;
	}

	public void setMaximumHealth(int maximumHealth) {
		this.maximumHealth = maximumHealth;
	}

	public int initializeRandomSpeed(){
		random = new Random();
		int speed = random.nextInt(minimumSpeed) + maximumSpeed;
		return speed;
	}
	public int initializeRandomHealth(){
		random = new Random();
		int health = random.nextInt(minimumHealth) + maximumHealth;
		return health;
	}
	
	public abstract void updateBehaviour(int playerX, int playerY);
}
