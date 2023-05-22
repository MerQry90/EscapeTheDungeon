package Entities.DynamicEntities.Projectiles;

import Components.AudioManager;
import Components.EntityManager;
import Entities.GenericEntity;

import java.awt.*;

import static java.lang.Math.*;

/**
 * Projectile shot by the Boss, it orbits around it
 * until the end of the duration, it also changes direction
 * at some point.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class OrbitalSlimeBall extends Projectile{

	private final double EPSILON = toRadians(2);

	private Image SLIME_BALL;
	private Image NOT_VISIBLE;
	
	private int centerX, centerY;
	private double distanceFromCenter;
	private double angulationFromCenter;
	private int duration;
	private int ttl;
	private int spawnTime;
	private int changeDirectionWait;
	
	private boolean theChosenOneForAudio;

	/**
	 * Initializes sprites, animations, speed, CollisionBox and the parameters needed to move the ball.
	 * @param centerX Central x-coordinate of the generating entity
	 * @param centerY Central y-coordinate of the generating entity
	 * @param distanceFromCenter Distance of the ball from the center, value passed from a for loop
	 * @param startingAngulation Starting angulation of the ball, expressed in radiant
	 * @param duration Duration of the ball before it disappears
	 * @param spawnTime Time before it appears on the screen and the collisionBox is activated
	 * @param entityManager Necessary to check collisions with other entities
	 */
	public OrbitalSlimeBall(int centerX, int centerY, double distanceFromCenter,
							double startingAngulation, int duration, int spawnTime, EntityManager entityManager){
		this.centerX = centerX;
		this.centerY = centerY;
		this.distanceFromCenter = distanceFromCenter;
		this.angulationFromCenter = startingAngulation;
		this.duration = duration;
		this.spawnTime = spawnTime;
		this.entityManager = entityManager;
		init();
	}
	
	public void updateCoordinates(){
		double tX = distanceFromCenter * cos(angulationFromCenter);
		double tY = distanceFromCenter * sin(angulationFromCenter);
		setXFromCenter((int)(centerX + round(tX)));
		setYFromCenter((int)(centerY + round(tY)));
	}
	
	@Override
	public void init() {
		SLIME_BALL = GenericEntity.setSpriteFromPath("src/resources/sprites/Enemies/Boss/pallina/slimeball_2.png");
		NOT_VISIBLE = GenericEntity.setSpriteFromPath("src/resources/sprites/png/invisible_cube.png");
		setActiveSprite(NOT_VISIBLE);
		
		updateCoordinates();
		
		setWidth(32);
		setHeight(32);
		setCBwidthScalar(0.9);
		setCBheightScalar(0.9);
		ttl = 0;
		changeDirectionWait = 10;
		
		theChosenOneForAudio = angulationFromCenter == toRadians(45);
	}
	
	@Override
	public void moveEntity() {
		
		if(spawnTime > 0){
			spawnTime -= 1;
		}
		else if(!isCollisionBoxActive()){
			if(theChosenOneForAudio){
				entityManager.mainGameReference.audioManager.playSoundOnce(AudioManager.SLIME_SOUND_1_INDEX);
			}
			activateCollisionBox();
			setActiveSprite(SLIME_BALL);
		}
		
		if(ttl < (duration / 2)) {
			ttl += 1;
			angulationFromCenter += EPSILON;
			updateCoordinates();
		}
		else if(changeDirectionWait > 0){
			changeDirectionWait -= 1;
		}
		else if(ttl < duration){
			ttl += 1;
			angulationFromCenter -= EPSILON;
			updateCoordinates();
		}
		else {
			setInactive();
		}
	}
}
