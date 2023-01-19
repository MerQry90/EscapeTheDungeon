package Entities.Enemies;

import static java.lang.Math.abs;

public class Dodger extends Enemy{
	int traslX = 0;
	int traslY = 0;


	public Dodger(int x, int y){
		super(x, y);
	}

	@Override
	public void init() {
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		setRandomSpeed(1, 9);
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		setRandomHealth(2, 1);
		setSprite("src/resources/sprites/png/player_sample.png");
	}

	@Override
	public void updateBehaviour(int playerX, int playerY) {
		/*
		 * angolo min = arctan(cateto min / cateto magg)
		 * */
		initializeDeltas(playerX, playerY);

		double angle;
		switch(getCurrentBehaviour()) {
			case 0 -> {
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
				if(traslX <= 2 || traslY <= 2){
					changeBehaviourTo(1);
				}
			}
			case 1 -> {
				traslX *= -1;
				traslY *= -1;

				int tmp = traslX;
				traslX = traslY;
				traslY = tmp;
				if(traslX > 2 || traslY > 2){
					changeBehaviourTo(0);
				}
			}
			default -> changeBehaviourTo(0);
		}
		setX(keepXBoundaries(getX() + traslX));
		setY(keepYBoundaries(getY() + traslY));
	}
}
