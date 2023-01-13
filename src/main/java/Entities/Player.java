package Entities;

public class Player extends Entity {

	private int shootCoolDown; //valore fisso che indica ogni quanti frame il giocatore può sparare una freccia
	private int shootCoolDownValue; //valore che incrementa
	private boolean hasShoot;

	public Player() {
		setX(512); //tmp
		setY(256); //tmp
		setHeight(64); //tmp
		setWidth(64); //tmp
		setSpeed(10); //tmp
		setSprite("src/resources/sprites/png/player_front.png");
		shootCoolDown = 15;
		shootCoolDownValue = 0;
		hasShoot = false;
	}

	public void coolDown(){
		shootCoolDownValue += 1;
	}

	public boolean tryShoot() {
		if (hasShoot) {
			if (shootCoolDown - shootCoolDownValue > 0) {
				//shootCoolDownValue += 1;
			}
			else {
				hasShoot = false;
				shootCoolDownValue = 0;
			}
			return false;
		}
		//hasShoot = false, quindi il player può sparare
		else {
			hasShoot = true;
			shootCoolDownValue = 0;
			return true;
		}
	}


}
