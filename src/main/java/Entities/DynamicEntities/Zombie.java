package Entities.DynamicEntities;

import Components.EntityManager;

import java.awt.*;

public class Zombie extends Enemy{
	
	private Image LIVING_ZOMBIE_LEFT;
	//TODO altri sprite
	
	public Zombie(int x, int y, EntityManager entityManager) {
		super(x, y, entityManager);
	}
	
	@Override
	public void init(){
		//CARICAMENTO SPRITE
		LIVING_ZOMBIE_LEFT = setSpriteFromPath("src/resources/sprites/png/zombie.png");
		setActiveSprite(LIVING_ZOMBIE_LEFT);
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		setRandomSpeed(5, 4);
		setRandomHealth(3, 2);
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		changeBehaviourTo(0);
	}
	
	@Override
	public void updateBehaviour() {
		switch (getCurrentBehaviour()){
			case 0:
				calculateTranslations(entityManager.getPlayerX() - getX(),
						entityManager.getPlayerY() - getY());
				break;
			default:
				changeBehaviourTo(0);
		}
	}
}
