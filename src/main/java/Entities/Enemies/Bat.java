package Entities.Enemies;

import Entities.GenericEntity;

import static java.lang.Math.abs;

public class Bat extends Enemy{

	private int wait, countdown, movingCountdown;
	int deltaX, deltaY;
	private boolean alternate;
	private int dashSpeed = 30;


	public Bat(int x, int y){
		super(x, y);
	}

	@Override
	public void init() {
		setMinimumSpeed(1); //l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		setMaximumSpeed(5); //verrà sommato a minimumSpeed
		setSpeed(initializeRandomSpeed());
		setMinimumHealth(2);
		setMaximumHealth(1);
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		
		wait = 40;
		countdown = 0;
		movingCountdown = 20;
		deltaX = 0;
		deltaY = 0;
		alternate = true;

		setHealth();
		setSprite("src/resources/sprites/png/player_sample.png");
	}

	@Override
	public void updateBehaviour(int playerX, int playerY) {
		countdown++;

		if(countdown <= wait){
			setSprite("src/resources/sprites/png/player_sample.png");
			deltaX = playerX - getX();
			deltaY = playerY - getY();
			if(alternate){
				moveUp();
				alternate = false;
			}
			else {
				moveDown();
				alternate = true;
			}
		}

		double angle;
		int traslX, traslY;
		if(countdown >= wait) {
			movingCountdown--;
			setSprite("src/resources/sprites/backgrounds/MainMenu_PlaceHolder_2.png");
			if (abs(deltaX) >= abs(deltaY)) {
				angle = Math.atan((double) abs(deltaY) / abs(deltaX));
				traslX = (int) (dashSpeed * Math.cos(angle));
				traslY = (int) (dashSpeed * Math.sin(angle));
			} else {
				angle = Math.atan((double) abs(deltaX) / abs(deltaY));
				traslX = (int) (dashSpeed * Math.sin(angle));
				traslY = (int) (dashSpeed * Math.cos(angle));
			}
			if (deltaX < 0) {
				traslX = traslX * -1;
			}
			if (deltaY < 0) {
				traslY = traslY * -1;
			}
			setX(keepXBoundaries(getX() + traslX));
			setY(keepYBoundaries(getY() + traslY));
			if(movingCountdown <= 0){
				countdown = 0;
				movingCountdown = 20;
			}
		}
	}
}
