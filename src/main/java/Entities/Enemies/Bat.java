package Entities.Enemies;

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
		//l'estremo è escluso, velocità a cui viene sommata maximumSpeed
		//verrà sommato a minimumSpeed
		setRandomSpeed(1, 30);
		setWidth(64);
		setHeight(64);
		setCBwidthScalar(0.7);
		setCBheightScalar(0.9);
		setRandomHealth(2, 1);
		setSprite("src/resources/sprites/png/player_sample.png");

		wait = 40;
		countdown = 0;
		movingCountdown = 20;
		alternate = true;

		deltaX = 0;
		deltaY = 0;
	}

	@Override
	public void updateBehaviour(int playerX, int playerY) {
		countdown++;
		switch (getCurrentBehaviour()) {
			case 0 -> {
				setSprite("src/resources/sprites/png/player_sample.png");
				//deltaX = playerX - getX();
				//deltaY = playerY - getY();
				initializeDeltas(playerX,playerY);
				if (alternate) {
					moveUp();
					alternate = false;
				} else {
					moveDown();
					alternate = true;
				}
				if (countdown > wait) {
					changeBehaviourTo(1);
				}
			}
			case 1 -> {
				//double angle;
				//int traslX, traslY;
				movingCountdown--;
				setSprite("src/resources/sprites/backgrounds/MainMenu_PlaceHolder_2.png");
				/*if (abs(deltaX) >= abs(deltaY)) {
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
				setY(keepYBoundaries(getY() + traslY));*/
				followPlayer();
				if (movingCountdown <= 0) {
					countdown = 0;
					movingCountdown = 20;
				}
				if (countdown <= wait) {
					changeBehaviourTo(0);
				}
			}
			default -> changeBehaviourTo(0);
		}
	}
}
