package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;

import java.awt.*;

public class Zombie extends Enemy{
	
	private Image LIVING_ZOMBIE_LEFT;
	//TODO altri sprite
	
	public Zombie(int x, int y, EntityManager entityManager) {
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}
	
	@Override
	public void init(){
		//CARICAMENTO SPRITE
		LIVING_ZOMBIE_LEFT = setSpriteFromPath("src/resources/sprites/png/zombie.png");
		setActiveSprite(LIVING_ZOMBIE_LEFT);
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		initCollisionBox();
		
		translation = new Vector2D(0);
		setRandomSpeed(5, 4);
		setRandomHealth(3, 2);
		setCanPassThroughWalls(false);
		changeBehaviourTo("follow-player");
	}
	
	@Override
	public void updateBehaviour() {
		switch (getCurrentBehaviour()){
			case "follow-player" -> {
				int dX = getDeltaXToObjective(entityManager.getPlayerX());
				int dY = getDeltaYToObjective(entityManager.getPlayerY());
				translation.setAngulationToObjective(dX, dY);
				moveEntity(translation.getXTranslation(), translation.getYTranslation());
			}
			default -> {
				changeBehaviourTo("follow-player");
			}
		}
	}
}
