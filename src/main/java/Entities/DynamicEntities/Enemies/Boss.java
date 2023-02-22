package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Tile;
import Components.Vector2D;
import Entities.DynamicEntities.Projectiles.OrbitalSlimeBalls;
import Entities.DynamicEntities.Projectiles.DirectSlimeBalls;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.atan;
import static java.lang.Math.toRadians;


public class Boss extends Enemy{
	
	private Image BOSS_TMP;
	private int behaviourCountdown;
	int shootCuntDown, finalRageCountDown;
	
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

		shootCuntDown = 15;
		finalRageCountDown = 45;

		translationVector2D = new Vector2D(3);
		setHealth(300);
		setCanPassThroughWalls(false);
		setCanFly(false);
		changeBehaviourTo("denseSwirlingBalls");
		behaviourCountdown = 30 * 10;
	}
	
	@Override
	public void updateBehaviour() {
		System.out.println(getCurrentBehaviour());
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
					System.out.println("proiettile boss sparato");
					
					//basso dx
					for(int i = 50; i < 500; i += 30){
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getX(), getY(), i, toRadians(45), 30 * 30, entityManager));
					}
					
					changeBehaviourTo("idle");
				}
				case "looseSwirlingBalls" -> {
				
				}
				case "fastBalls" -> {
					shootCuntDown -= 1;
					int dX = getDeltaXToObjective(entityManager.getPlayerX());
					int dY = getDeltaYToObjective(entityManager.getPlayerY());
					float angulation;
					if(dX == 0 && dY == 0){
						angulation = 0;
					}
					else {
						double theta = atan((double) (dY) / (double) (dX));
						// 2o e 3o quad
						if (dX < 0) {
							angulation = (float) (toRadians(180) + theta);
						}
						// 4o quad
						else if (dY < 0) {
							angulation = (float) (toRadians(360) + theta);
						}
						// 1o quad
						else {
							angulation = (float) (theta);
						}
						if(shootCuntDown <= 0){
							entityManager.newHostileProjectile(new DirectSlimeBalls(Tile.getTile(8), Tile.getTile(4), 30, angulation, entityManager));
							shootCuntDown = 15;
						}
					}
				}
				case "slimeTrail" -> {

				}
				case "finalRage" -> {
					finalRageCountDown -= 1;
					if(finalRageCountDown <= 0) {
						for (double i = 0; i <= 4.5; i += 0.1) {
							entityManager.newHostileProjectile(new DirectSlimeBalls(Tile.getTile(8), Tile.getTile(4), 5, i, entityManager));
						}
						finalRageCountDown = 45;
					}
				}
				case "idle" ->{
					//Do nothing
				}
				default -> {
					changeBehaviourTo("fastBalls");
				}
			}
		}
	}
	
	
	
	
	
	@Override
	public void moveEntity() {
		//nulla
	}
}
