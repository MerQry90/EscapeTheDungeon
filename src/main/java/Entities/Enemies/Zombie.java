package Entities.Enemies;

import static java.lang.Math.*;

public class Zombie extends Enemy{
	
	public Zombie(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void init(){
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		setRandomSpeed(5, 4);
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		
		setRandomHealth(3, 2);
		setSprite("src/resources/sprites/png/zombie.png");
		changeBehaviourTo(0);
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
		
		switch (getCurrentBehaviour()){
			case 0:
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
				break;
			default:
				changeBehaviourTo(0);
		}
	}
}
