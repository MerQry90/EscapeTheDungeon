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
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		
		setRandomSpeed(5, 4);
		setRandomHealth(3, 2);
		changeBehaviourTo("follow-player");
	}
	
	@Override
	public void updateBehaviour() {
		switch (getCurrentBehaviour()){
			case "follow-player" -> {
				this.moveEntity();
			}
			default:
				changeBehaviourTo("follow-player");
		}
	}
	
	@Override
	public void move() {
	
	}
}
