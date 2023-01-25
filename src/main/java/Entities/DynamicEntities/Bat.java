package Entities.DynamicEntities;

import Components.EntityManager;

import java.awt.*;

import static java.lang.Math.abs;

public class Bat extends Enemy{

	private Image LIVING_BAT_OFF;
	private Image LIVING_BAT_ON;
	
	private int wait, countdown, movingCountdown;
	int deltaX, deltaY;
	private boolean alternate;
	private int dashSpeed = 30;


	public Bat(int x, int y, EntityManager entityManager){
		super(x, y, entityManager);
	}

	@Override
	public void init() {
		//CARICAMENTO SPRITE
		LIVING_BAT_OFF = setSpriteFromPath("src/resources/sprites/png/player_sample.png");
		LIVING_BAT_ON = setSpriteFromPath("src/resources/sprites/backgrounds/MainMenu_PlaceHolder_2.png");
		setActiveSprite(LIVING_BAT_OFF);
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		setRandomSpeed(1, 30);
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		setRandomHealth(2, 1);

		wait = 40;
		countdown = 0;
		movingCountdown = 20;
		alternate = true;

		deltaX = 0;
		deltaY = 0;
	}

	//TODO creare metodo di countdown
	@Override
	public void updateBehaviour() {
		countdown++;
		switch (getCurrentBehaviour()) {
			case 0 -> {
				if (alternate) {
					setActiveSprite(LIVING_BAT_ON);
					alternate = false;
				} else {
					setActiveSprite(LIVING_BAT_OFF);
					alternate = true;
				}
				if (countdown > wait) {
					changeBehaviourTo(1);
				}
			}
			case 1 -> {
				//TODO bat op
				movingCountdown--;
				setActiveSprite(LIVING_BAT_OFF);
				calculateTranslations(entityManager.getPlayerX() - getX(),
						entityManager.getPlayerY() - getY());
				if (movingCountdown <= 0) {
					countdown = 0;
					movingCountdown = 20;
				}
				if (countdown <= wait) {
					changeBehaviourTo(0);
				}
			}
			default -> changeBehaviourTo(0);
		}
	}
}