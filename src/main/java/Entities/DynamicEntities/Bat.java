package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

import static java.lang.Math.abs;

public class Bat extends Enemy{

	private Image LIVING_BAT_OFF;
	private Image LIVING_BAT_ON;
	
	private int wait, countdown, movingCountdown;
	
	public Bat(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}

	@Override
	public void init() {
		//CARICAMENTO SPRITE
		LIVING_BAT_OFF = setSpriteFromPath("src/resources/sprites/png/player_sample.png");
		LIVING_BAT_ON = setSpriteFromPath("src/resources/sprites/backgrounds/MainMenu_PlaceHolder_2.png");
		setActiveSprite(LIVING_BAT_OFF);
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		initCollisionBox();
		
		translation = new Vector2D(0);
		setRandomSpeed(1, 30);
		setRandomHealth(2, 1);
		setCanPassThroughWalls(false);
		wait = 40;
		countdown = 0;
		movingCountdown = 20;
		changeBehaviourTo("stop-1");
	}

	@Override
	public void updateBehaviour() {
		switch (getCurrentBehaviour()) {
			case "stop-1" -> {
				countdown++;
				setActiveSprite(LIVING_BAT_ON);
				if (countdown > wait) {
					changeBehaviourTo("aiming");
				}
				else {
					changeBehaviourTo("stop-2");
				}
			}
			case "stop-2" -> {
				countdown++;
				setActiveSprite(LIVING_BAT_OFF);
				if (countdown > wait) {
					changeBehaviourTo("aiming");
				}
				else {
					changeBehaviourTo("stop-1");
				}
			}
			case "aiming" ->{
				int dX = getDeltaXToObjective(entityManager.getPlayerX());
				int dY = getDeltaYToObjective(entityManager.getPlayerY());
				translation.setAngulationToObjective(dX, dY);
				changeBehaviourTo("dashing");
			}
			case "dashing" -> {
				movingCountdown--;
				setActiveSprite(LIVING_BAT_OFF);
				setX(translation.getXTranslation() + getX());
				setY(translation.getYTranslation() + getY());
				if (movingCountdown <= 0) {
					countdown = 0;
					movingCountdown = 20;
				}
				if (countdown <= wait) {
					changeBehaviourTo("stop-1");
				}
			}
			default -> changeBehaviourTo("stop-1");
		}
	}
}
