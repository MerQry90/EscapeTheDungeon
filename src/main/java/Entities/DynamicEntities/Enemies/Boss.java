package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Tile;
import Components.Vector2D;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Boss extends Enemy{
	
	private Image BOSS_TMP;
	private int behaviourCountdown;
	
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
		setHealth(10);
		setCanPassThroughWalls(false);
		setCanFly(false);
		changeBehaviourTo("fastBalls");
		behaviourCountdown = 30 * 10;
	}
	
	@Override
	public void updateBehaviour() {
		if(getHealth() > 70){
			if(behaviourCountdown > 0){
				behaviourCountdown -= 1;
			}
			else {
				behaviourCountdown = 30 * 10;
				Random random = new Random();
				int randomInt = random.nextInt(3);
				if(randomInt == 0 && getCurrentBehaviour().equals("slimeTrail")){
					changeBehaviourTo("looseSwirlingBalls");
				}
				else if(randomInt == 0){
					changeBehaviourTo("denseSwirlingBalls");
				}
				else if(randomInt == 1){
					changeBehaviourTo("fastBalls");
				}
				else {
					changeBehaviourTo("slimeTrail");
				}
			}
		}
		else {
			changeBehaviourTo("finalRage");
		}
		if(checkActivation()) {
			switch (getCurrentBehaviour()) {
				case "denseSwirlingBalls" -> {
				
				}
				case "looseSwirlingBalls" -> {
				
				}
				case "fastBalls" -> {

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
		//nulla
	}
}
