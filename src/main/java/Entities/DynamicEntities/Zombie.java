package Entities.DynamicEntities;

import Components.EntityManager;
import Components.Vector2D;
import java.awt.*;
import static java.lang.Math.*;


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
		
		translationVector2D = new Vector2D(0);
		setRandomSpeed(5, 4);
		setRandomHealth(3, 2);
		setCanPassThroughWalls(false);
		setCanFly(false);
		changeBehaviourTo("follow-player");
	}
	
	@Override
	public void updateBehaviour() {
		if(checkActivation()) {
			switch (getCurrentBehaviour()) {
				case "follow-player" -> {
					int dX = getDeltaXToObjective(entityManager.getPlayerX());
					int dY = getDeltaYToObjective(entityManager.getPlayerY());
					translationVector2D.setAngulationFromCoordinates(dX, dY);
					moveEntity();
				}
				default -> {
					changeBehaviourTo("follow-player");
				}
			}
		}
	}
	
	@Override
	public void moveEntity(){
		//FIXME
		int originalX = getX();
		int originalY = getY();
		double originalTheta = translationVector2D.getAngulation();
		double epsilon = toRadians(90);
		boolean positiveEpsilonEligible;
		boolean negativeEpsilonEligible;
		int iterator = -1;
		int[] distanceArray = new int[2];
		do{
			iterator += 1;
			positiveEpsilonEligible = false;
			negativeEpsilonEligible = false;
			//tentativo con epsilon positivo
			translationVector2D.setAngulation(originalTheta + (epsilon * iterator));
			setX(originalX + translationVector2D.getTranslationOnX());
			setY(originalY + translationVector2D.getTranslationOnY());
			if((!entityManager.checkWallsCollisions(this) || canPassThroughWalls) &&
					(!entityManager.checkObstaclesCollisions(this) || canFly)){
				positiveEpsilonEligible = true;
				distanceArray[0] = getDistanceToObjective(entityManager.getPlayerX(), entityManager.getPlayerY());
			}
			//tentativo con epsilon negativo
			translationVector2D.setAngulation(originalTheta - (epsilon * iterator));
			setX(originalX + translationVector2D.getTranslationOnX());
			setY(originalY + translationVector2D.getTranslationOnY());
			if((!entityManager.checkWallsCollisions(this) || canPassThroughWalls) &&
					(!entityManager.checkObstaclesCollisions(this) || canFly)){
				negativeEpsilonEligible = true;
				distanceArray[1] = getDistanceToObjective(entityManager.getPlayerX(), entityManager.getPlayerY());
			}
		}
		while (!positiveEpsilonEligible && !negativeEpsilonEligible);
		
		//qui da warning ma è intellij stupido
		if(positiveEpsilonEligible && negativeEpsilonEligible){
			if(distanceArray[0] >= distanceArray[1]){
				translationVector2D.setAngulation(originalTheta + (epsilon * iterator));
			}
			else {
				translationVector2D.setAngulation(originalTheta - (epsilon * iterator));
			}
		}
		else if(positiveEpsilonEligible){
			translationVector2D.setAngulation(originalTheta + (epsilon * iterator));
		}
		else if(negativeEpsilonEligible){
			translationVector2D.setAngulation(originalTheta - (epsilon * iterator));
		}
		setX(originalX + translationVector2D.getTranslationOnX());
		setY(originalY + translationVector2D.getTranslationOnY());
	}
}
