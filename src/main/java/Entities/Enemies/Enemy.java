package Entities.Enemies;

import Entities.GenericEntity;

import java.util.Random;

import static java.lang.Math.abs;

public abstract class Enemy extends GenericEntity {
	
	private int health;
	private int currentBehaviour;
	int deltaX = 0;
	int deltaY = 0;
	
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

	public void initializeDeltas(int playerX, int playerY){
		deltaX = playerX - getX();
		deltaY = playerY - getY();
	}

	public void followPlayer(){
		/*
		 * angolo min = arctan(cateto min / cateto magg)
		 * */

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



	//metodi riguardanti lo stato del nemico----------------------------------------------------------------------------
	public int getCurrentBehaviour(){
		return currentBehaviour;
	}

	public void changeBehaviourTo(int newBehaviour){
		this.currentBehaviour = newBehaviour;
	}
	
	public abstract void updateBehaviour(int playerX, int playerY);
	//------------------------------------------------------------------------------------------------------------------
}
