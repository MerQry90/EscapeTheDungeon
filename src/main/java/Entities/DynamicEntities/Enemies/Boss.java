package Entities.DynamicEntities.Enemies;

import Components.EntityManager;
import Components.Tile;
import Components.Vector2D;
import Entities.DynamicEntities.Projectiles.OrbitalSlimeBalls;
import Entities.DynamicEntities.Projectiles.DirectSlimeBalls;
import Entities.DynamicEntities.Projectiles.RageSlimeBall;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.*;


public class Boss extends Enemy{
	
	private Image BOSS_TMP;
	private int behaviourCountdown;
	private int shootCuntDown, finalRageCountDown;
	private double previousHoleAngulation;
	
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
		setCBwidthScalar(1.0);
		setCBheightScalar(1.0);
		initCollisionBox();

		shootCuntDown = 15;
		finalRageCountDown = 75;

		Random random = new Random();
		previousHoleAngulation = toRadians(random.nextDouble(365));

		translationVector2D = new Vector2D(0);
		setHealth(49);
		setCanPassThroughWalls(false);
		setCanFly(false);
		changeBehaviourTo("denseSwirlingBalls");
		behaviourCountdown = 30 * 10;
	}
	
	@Override
	public void updateBehaviour() {
		System.out.println(getCurrentBehaviour());
		if(getHealth() > 50){
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
				else if(!(getCurrentBehaviour().equals("slimeTrail"))){
					changeBehaviourTo("slimeTrail");
				}
				else{
					changeBehaviourTo("looseSwirlingBalls");
				}
			}
		}
		else {
			changeBehaviourTo("finalRage");
		}
		if(checkActivation()) {
			switch (getCurrentBehaviour()) {
				case "denseSwirlingBalls" -> {
					for(int i = 50; i < 600; i += 50){
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(45), 30 * 10, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(135), 30 * 10, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(225), 30 * 10, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(315), 30 * 10, entityManager));
					}
					changeBehaviourTo("idle");
				}
				case "looseSwirlingBalls" -> {
					for(int i = 50; i < 600; i += 150){
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(45), 30 * 10, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(135), 30 * 10, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(225), 30 * 10, entityManager));
						entityManager.newHostileProjectile(new OrbitalSlimeBalls(
								getCenterX(), getCenterY(), i, toRadians(315), 30 * 10, entityManager));
					}
					changeBehaviourTo("idle");
				}
				case "fastBalls" -> {
					shootCuntDown -= 1;
					if(shootCuntDown <= 0){
						entityManager.newHostileProjectile(new DirectSlimeBalls(
								getCenterX(), getCenterY(), 30, getPlayerAngulation(), entityManager));
						shootCuntDown = 15;
					}
				}
				case "slimeTrail" -> {

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
						finalRageCountDown = 60;
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
