package Entities;

public abstract class Enemy extends Entity{
	
	private int health;
	
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
	public Enemy(int health, int movementCoolDown, int standByMovementCoolDown){
		this.health = health;
		
		this.movementCoolDown = movementCoolDown;
		this.standByMovementCoolDown = standByMovementCoolDown;
		currentMovementCoolDownValue = 0;
		hasMoved = false;
	}
	
	public void lowerHealth(){
		health -= 1;
		if(health <= 0){
			kill();
		}
	}
	
	public boolean tryMovement(){
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
	}
	
	//TODO fare la stessa cosa di movement per action
	
	public abstract void moveTo(int dstX, int dstY);
}
