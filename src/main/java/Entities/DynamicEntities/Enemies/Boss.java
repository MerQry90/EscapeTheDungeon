package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Tile;
import Components.Vector2D;
import Entities.DynamicEntities.Projectiles.OrbitalSlimeBalls;
import Entities.DynamicEntities.Projectiles.DirectSlimeBalls;
import Entities.DynamicEntities.Projectiles.RageSlimeBall;
import Entities.DynamicEntities.Projectiles.SlimeTrailBall;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.*;


public class Boss extends Enemy{
	
	private Image BOSS_TMP;
	private int behaviourCountdown;
	private int shootCountDown, finalRageCountDown;
	private double previousHoleAngulation;
	private boolean cleanProjectilesOnce;
	
	private final int FPS = 30;
	private final int ORBITAL_BEHAVIOUR_DURATION = 20 * FPS;
	private final int FASTBALL_BEHAVIOUR_DURATION = 8 * FPS;
	//private final int SLIMETRAIL_BEHAVIOUR_DURATION = 3 * FPS;
	
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
		
		setX(Tile.getTile(8));
		setY(Tile.getTile(4));
		
		setWidth(Tile.getTile(1));
		setHeight(Tile.getTile(1));
		setCBwidthScalar(1.2);
		setCBheightScalar(1.2);
		activateCollisionBox();

		shootCountDown = 15;
		finalRageCountDown = 75;

		Random random = new Random();
		previousHoleAngulation = toRadians(random.nextDouble(365));

		translationVector2D = new Vector2D(0);
		setHealth(51);
		behaviourCountdown = 0;
		cleanProjectilesOnce = true;
	}
	
	@Override
	public void updateBehaviour() {
		System.out.println(getCurrentBehaviour());
		if(getHealth() > 50){
			if(behaviourCountdown > 0){
				behaviourCountdown -= 1;
			}
			else {
				Random random = new Random();
				int randomInt = random.nextInt(3);
				if(randomInt == 0 && getCurrentBehaviour().equals("slimeTrail")){
					changeBehaviourTo("looseSwirlingBalls");
					behaviourCountdown = ORBITAL_BEHAVIOUR_DURATION;
				}
				else if(randomInt == 0){
					changeBehaviourTo("denseSwirlingBalls");
					behaviourCountdown = ORBITAL_BEHAVIOUR_DURATION;
				}
				else if(randomInt == 1){
					changeBehaviourTo("fastBalls");
					behaviourCountdown = FASTBALL_BEHAVIOUR_DURATION;
				}
				else if(!(getCurrentBehaviour().equals("slimeTrail"))){
					changeBehaviourTo("slimeTrail");
					behaviourCountdown = 0;
				}
			}
		}
		else {
			if(cleanProjectilesOnce){
				cleanProjectilesOnce = false;
				entityManager.cleanHostileProjectiles();
			}
			changeBehaviourTo("finalRage");
		}
		
		if(checkActivation()) {
			switch (getCurrentBehaviour()) {
				case "denseSwirlingBalls" -> {
					int loopCount = 0;
					for(int i = 50; i < 600; i += 50){
						loopCount += 1;
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(45),
								ORBITAL_BEHAVIOUR_DURATION, loopCount * 20, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(135),
								ORBITAL_BEHAVIOUR_DURATION,loopCount * 20, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(225),
								ORBITAL_BEHAVIOUR_DURATION, loopCount * 20, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(315),
								ORBITAL_BEHAVIOUR_DURATION, loopCount * 20, entityManager));
					}
					changeBehaviourTo("idle");
				}
				case "looseSwirlingBalls" -> {
					int loopCount = 0;
					for(int i = 50; i < 600; i += 150){
						loopCount += 1;
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(45),
								ORBITAL_BEHAVIOUR_DURATION, loopCount * 20, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(135),
								ORBITAL_BEHAVIOUR_DURATION, loopCount * 20, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(225),
								ORBITAL_BEHAVIOUR_DURATION, loopCount * 20, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(315),
								ORBITAL_BEHAVIOUR_DURATION, loopCount * 20, entityManager));
					}
					changeBehaviourTo("idle");
				}
				case "fastBalls" -> {
					shootCountDown -= 1;
					if(shootCountDown <= 0){
						entityManager.newHostileProjectile(new DirectSlimeBalls(
								getCenterX(), getCenterY(), 15, getPlayerAngulation(), entityManager));
						shootCountDown = 7;
					}
				}
				case "slimeTrail" -> {
					Random random = new Random();
					if(random.nextBoolean()){
						entityManager.newHostileProjectile(new SlimeTrailBall(getX(), getY(), "up", entityManager));
						entityManager.newHostileProjectile(new SlimeTrailBall(getX(), getY(), "down", entityManager));
					}
					else {
						entityManager.newHostileProjectile(new SlimeTrailBall(getX(), getY(), "right", entityManager));
						entityManager.newHostileProjectile(new SlimeTrailBall(getX(), getY(), "left", entityManager));
					}
				}
				case "finalRage" -> {
					finalRageCountDown -= 1;

					if(finalRageCountDown <= 0) {
						double epsilon = toRadians(9);
						Random random = new Random();
						if(random.nextBoolean()){
							previousHoleAngulation += toRadians(60);
						}
						else {
							previousHoleAngulation -= toRadians(60);
						}
						for(int i = 0; i <= 360/(toDegrees(epsilon) + 1.2); i += 1){
							entityManager.newHostileProjectile(new RageSlimeBall(
									getCenterX(), getCenterY(), epsilon * i + previousHoleAngulation, entityManager
							));
						}
						finalRageCountDown = 80;
					}
				}
				case "idle" ->{
					//Do nothing
				}
				default -> {
					changeBehaviourTo("denseSwirlingBalls");
				}
			}
		}
	}

	public float getPlayerAngulation(){
		int dX = getDeltaXToObjective(entityManager.getPlayerX());
		int dY = getDeltaYToObjective(entityManager.getPlayerY());
		if(dX == 0 && dY == 0){
			return 0;
		}
		else {
			double theta = atan((double) (dY) / (double) (dX));
			// 2o e 3o quad
			if (dX < 0) {
				return (float) (toRadians(180) + theta);
			}
			// 4o quad
			else if (dY < 0) {
				return (float) (toRadians(360) + theta);
			}
			// 1o quad
			else {
				return (float) (theta);
			}
		}
	}
	
	
	
	@Override
	public void moveEntity() {
		//nulla
	}
}
