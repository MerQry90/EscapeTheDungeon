package Entities.DynamicEntities;

import Entities.GenericEntity;

import static java.lang.Math.abs;

public abstract class DynamicEntity extends GenericEntity {
	
	//Metodi per il movimento delle entità------------------------------------------------------------------------------
	
	public int getTranslationX(int deltaX, int deltaY){
		double angle;
		int translationX;
		if(abs(deltaX) >= abs(deltaY)){
			angle = Math.atan((double) abs(deltaY) / abs(deltaX));
			translationX = (int) (getSpeed() * Math.cos(angle));
		}
		else {
			angle = Math.atan((double) abs(deltaX) / abs(deltaY));
			translationX = (int) (getSpeed() * Math.sin(angle));
		}
		if(deltaX < 0){
			return translationX * -1;
		}
		return translationX;
	}
	public int getTranslationY(int deltaX, int deltaY){
		double angle;
		int translationY;
		if(abs(deltaX) >= abs(deltaY)){
			angle = Math.atan((double) abs(deltaY) / abs(deltaX));
			translationY = (int) (getSpeed() * Math.sin(angle));
		}
		else {
			angle = Math.atan((double) abs(deltaX) / abs(deltaY));
			translationY = (int) (getSpeed() * Math.cos(angle));
		}
		if(deltaY < 0){
			return translationY * -1;
		}
		return translationY;
	}
	
	public void moveEntity(int tX, int tY){
		if(tX < 0){
			for(int i = 0; i > tX; i--){
				if(entityManager.checkObstaclesCollisions(this)) {
					setX(getX() + 1);
					break;
				}
				setX(getX() - 1);
			}
		}
		else {
			for(int i = 0; i < tX; i++){
				if(entityManager.checkObstaclesCollisions(this)){
					setX(getX() - 1);
					break;
				}
				setX(getX() + 1);
			}
		}
		if(tY < 0){
			for(int i = 0; i > tY; i--){
				if(entityManager.checkObstaclesCollisions(this)) {
					setY(getY() + 1);
					break;
				}
				setY(getY() - 1);
			}
		}
		else {
			for(int i = 0; i < tY; i++){
				if(entityManager.checkObstaclesCollisions(this)){
					setY(getY() - 1);
					break;
				}
				setY(getY() + 1);
			}
		}
	}
}
