package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Vector2D;
import Entities.DynamicEntities.Projectiles.Peas;

import java.awt.*;

public class Shooter extends Enemy{
	
	private Image LIVING_SHOOTER_LEFT;
	
	private int idleCountdown;
	
	public Shooter(int x, int y, EntityManager entityManager){
		this.entityManager = entityManager;
		setX(x);
		setY(y);
		init();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		LIVING_SHOOTER_LEFT = setSpriteFromPath("src/resources/sprites/png/plant.png");
		setActiveSprite(LIVING_SHOOTER_LEFT);
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.8);
		setCBheightScalar(0.9);
		activateCollisionBox();
		
		translationVector2D = new Vector2D(0);
		setRandomHealth(6, 4);
		setCanPassThroughWalls(false);
		setCanFly(false);
		changeBehaviourTo("idle");
		
		idleCountdown = 30;
	}
	
	@Override
	public void updateBehaviour() {
		if(checkActivation()) {
			if(!checkIfActive()){
				changeBehaviourTo("dead");
			}
			switch (getCurrentBehaviour()) {
				case "idle" -> {
					if(idleCountdown <= 0){
						changeBehaviourTo("shoot");
					}
					idleCountdown -= 1;
				}
				case "shoot" -> {
					entityManager.newHostileProjectile(new Peas(getX(), getY(),
							entityManager.getPlayerX(), entityManager.getPlayerY(), entityManager));
					idleCountdown = 30;
					changeBehaviourTo("idle");
				}
				case "dead" -> {
					//cambio di sprite
				}
				default -> {
					changeBehaviourTo("idle");
				}
			}
		}
	}
	
	@Override
	public void moveEntity() {
		//non si muove
	}
}
