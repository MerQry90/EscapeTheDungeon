package Entities;

import java.util.Random;

public abstract class Enemy extends GenericEntity {
	
	private int health;
	Random random;
	private int minimumSpeed, maximumSpeed, minimumHealth, maximumHealth;

	/*
	* Più i valori di cooldown saranno alti, più l'entità rimarrà
	* in quello specifico stato a lungo
	* */
	
	private int movementCoolDown;
	private int standByMovementCoolDown;
	private int currentMovementCoolDownValue;
	private boolean hasMoved;
	
	//alcuni nemici potrebbero non avere azioni da compiere
	private int actionCoolDown;
	private int standByActionCoolDown;
	private int currentActionCoolDownValue;
	private boolean hasActed;
	
	//@Overload
	public Enemy(int health, int movementCoolDown, int standByMovementCoolDown,
				 int actionCoolDown, int standByActionCoolDown){
		this.health = health;
		
		this.movementCoolDown = movementCoolDown;
		this.standByMovementCoolDown = standByMovementCoolDown;
		currentMovementCoolDownValue = 0;
		hasMoved = false;
		
		this.actionCoolDown = actionCoolDown;
		this.standByActionCoolDown = standByActionCoolDown;
		currentActionCoolDownValue = 0;
		hasActed = false;
	}
	public Enemy(/*int movementCoolDown, int standByMovementCoolDown*/){
		//this.health = health;
		
		this.movementCoolDown = movementCoolDown;
		this.standByMovementCoolDown = standByMovementCoolDown;
		currentMovementCoolDownValue = 0;
		hasMoved = false;
	}

	public void setHealth(){
		this.health = initializeRandomHealth();
	}
	
	public void lowerHealth(){
		health -= 1;
		if(health <= 0){
			kill();
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

	/*public boolean tryMovement(){
		//NON può muoversi
		if(hasMoved){
			if(standByMovementCoolDown - currentMovementCoolDownValue > 0){
				currentMovementCoolDownValue += 1;
			}
			else {
				hasMoved = false;
				currentMovementCoolDownValue = 0;
			}
			return false;
		}
		//può muoversi
		else {
			if(movementCoolDown - currentMovementCoolDownValue > 0){
				currentMovementCoolDownValue += 1;
			}
			else {
				hasMoved = true;
				currentMovementCoolDownValue = 0;
			}
			return true;
		}
	}*/

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
