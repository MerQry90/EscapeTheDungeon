package Entities;

import java.util.Random;

public class Zombie extends Entity{

	private boolean isAlive;

	public Zombie(int x, int y, int speed){
		isAlive = true;
		setX(x);
		setY(y);
		setSpeed(speed);
		setWidth(64);
		setHeight(64);
		setSprite("src/resources/sprites/png/zombie.png");
	}

	public void kill(){
		isAlive = false;
	}

	public boolean checkIsAlive() {
		return isAlive;
	}

	public boolean actionByRate(int possibleCases, int totalCases){
		if(possibleCases > totalCases || possibleCases < 0){
			return false;
		}
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		int val = random.nextInt(totalCases);
		if(val <= possibleCases) {
			return true;
		}
		return false;
	}

	public void move(int px, int py){
		/*
		actionByRate determina la probabilità dello zombie di muoversi
		(potrebbe non servire più se il gioco gira decentemente a 30 fps)
		 */
		if(actionByRate(100, 100)){
			int dx = px - getX();
			int dy = py - getY();
			int modx, mody;

			if(dx < 0){
				modx = dx * (-1);
			}
			else{
				modx = dx;
			}
			if(dy < 0){
				mody = dy * (-1);
			}
			else{
				mody = dy;
			}

			//moves on the x axis
			if(modx >= mody){
				if(dx >= 0){
					moveRight();
				}
				else {
					moveLeft();
				}
			}
			//moves on the y axis
			else {
				if(dy >= 0){
					moveDown();
				}
				else {
					moveUp();
				}
			}
		}
	}
}
