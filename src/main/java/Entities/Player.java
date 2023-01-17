package Entities;

import Application.KeyHandler;

import static java.lang.Math.sqrt;

public class Player extends GenericEntity {

	private int shootCoolDown; //valore fisso che indica ogni quanti frame il giocatore può sparare una freccia
	private int shootCoolDownValue; //valore che incrementa
	private boolean hasShot;

	public Player() {
		super(512, 256, 64, 64);
		setX(512); //tmp
		setY(256); //tmp
		setHeight(64); //tmp
		setWidth(64); //tmp
		setSpeed(10); //tmp
		setSprite("src/resources/sprites/png/player_front.png");
		shootCoolDown = 15;
		shootCoolDownValue = 0;
		hasShot = false;
	}

	public void updateCoolDown(){
		shootCoolDownValue += 1;
	}

	public boolean canShoot() {
		if (hasShot) {
			if (shootCoolDown - shootCoolDownValue > 0) {
				//shootCoolDownValue += 1;
			}
			else {
				hasShot = false;
				shootCoolDownValue = 0;
			}
			return false;
		}
		//hasShoot = false, quindi il player può sparare
		else {
			hasShot = true;
			shootCoolDownValue = 0;
			return true;
		}
	}
	
	
}
