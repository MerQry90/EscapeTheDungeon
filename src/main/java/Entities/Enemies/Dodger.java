package Entities.Enemies;

import Entities.EntityManager;

import java.awt.*;

import static java.lang.Math.abs;

public class Dodger extends Enemy{
	
	private Image ALIVE_DODGER;
	
	int traslX = 0;
	int traslY = 0;


	public Dodger(int x, int y, EntityManager entityManager){
		super(x, y, entityManager);
	}

	@Override
	public void init() {
		//CARICAMENTO SPRITE
		ALIVE_DODGER = setSpriteFromPath("src/resources/sprites/png/player_sample.png");
		setActiveSprite(ALIVE_DODGER);
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		setRandomSpeed(1, 9);
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		setRandomHealth(2, 1);
	}

	@Override
	public void updateBehaviour() {
		switch (getCurrentBehaviour()) {
			case 0:
				calculateTranslations(entityManager.getPlayerX() - getX(),
						entityManager.getPlayerY() - getY());
				break;
			default:
				changeBehaviourTo(0);
		}
	}
}
