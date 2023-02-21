package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Tile;
import Components.Vector2D;

import java.awt.*;

public class Boss extends Enemy{
	
	private Image BOSS_TMP;
	
	public Boss(EntityManager entityManager){
		this.entityManager = entityManager;
		init();
	}
	
	@Override
	public void init() {
		//CARICAMENTO SPRITE
		BOSS_TMP = setSpriteFromPath("src/resources/sprites/png/boss.png");
		setActiveSprite(BOSS_TMP);
		
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		
		setX(Tile.getTile(7));
		setY(Tile.getTile(3));
		
		setWidth(64 * 3);
		setHeight(64 * 3);
		setCBwidthScalar(1.0);
		setCBheightScalar(1.0);
		initCollisionBox();
		
		translationVector2D = new Vector2D(3);
		setRandomHealth(2, 2);
		setCanPassThroughWalls(false);
		setCanFly(false);
		changeBehaviourTo("taunt");
	}
	
	@Override
	public void updateBehaviour() {
		if(checkActivation()) {
			switch (getCurrentBehaviour()) {
				case "taunt" -> {

				}
				case "swirlingBalls" -> {

				}
				case "homingBalls" -> {

				}
				case "slimeTrail" -> {

				}
				case "finalRage" -> {

				}
				default -> {
					changeBehaviourTo("taunt");
				}
			}
		}
	}
	
	@Override
	public void moveEntity() {
	
	}
}
