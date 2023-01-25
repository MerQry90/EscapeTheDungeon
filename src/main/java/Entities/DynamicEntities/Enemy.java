package Entities.DynamicEntities;

import Components.EntityManager;
import Entities.GenericEntity;

import java.util.Random;

import static java.lang.Math.abs;

public abstract class Enemy extends DynamicEntity{
	
	private int health;
	private String currentBehaviour;

	public void setRandomHealth(int minimumHealth, int maximumHealth){
		Random random = new Random();
		int health = random.nextInt(minimumHealth) + maximumHealth;
		this.health = health;
	}
	public void setRandomSpeed(int minimumSpeed, int maximumSpeed){
		Random random = new Random();
		int speed = random.nextInt(minimumSpeed) + maximumSpeed;
		setSpeed(speed);
	}
	
	public void lowerHealth(){
		health -= 1;
		if(health <= 0){
			setInactive();
		}
	}

	//metodi riguardanti lo stato del nemico----------------------------------------------------------------------------
	public String getCurrentBehaviour(){
		return currentBehaviour;
	}

	public void changeBehaviourTo(String newBehaviour){
		this.currentBehaviour = newBehaviour;
	}
	
	public abstract void updateBehaviour();
	//------------------------------------------------------------------------------------------------------------------
}
