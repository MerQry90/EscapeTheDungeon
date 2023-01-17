package Entities.Enemies;

import static java.lang.Math.*;

public class Zombie extends Enemy{

	public Zombie(int x, int y){

		super(x, y, 64, 64, 0.7, 0.9);
		
		setMinimumSpeed(5); //l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		setMaximumSpeed(4); //verrà sommato a minimumSpeed
		setSpeed(initializeRandomSpeed());
		setMinimumHealth(3);
		setMaximumHealth(2);
		
		setHealth();
		setSprite("src/resources/sprites/png/zombie.png");
	}
	
	@Override
	public void updateBehaviour(int playerX, int playerY) {
		/*
		* angolo min = arctan(cateto min / cateto magg)
		* */
		int deltaX = playerX - getX();
		int deltaY = playerY - getY();
		
		double angle;
		int traslX, traslY;
		
		if(abs(deltaX) >= abs(deltaY)){
			angle = Math.atan((double) abs(deltaY) / abs(deltaX));
			traslX = (int) (getSpeed() * Math.cos(angle));
			traslY = (int) (getSpeed() * Math.sin(angle));
		}
		else {
			angle = Math.atan((double) abs(deltaX) / abs(deltaY));
			traslX = (int) (getSpeed() * Math.sin(angle));
			traslY = (int) (getSpeed() * Math.cos(angle));
		}
		if(deltaX < 0){
			traslX = traslX * -1;
		}
		if(deltaY < 0){
			traslY = traslY * -1;
		}
		setX(keepXBoundaries(getX() + traslX));
		setY(keepYBoundaries(getY() + traslY));
	}
}
