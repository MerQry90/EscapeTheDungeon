package Components;

import static java.lang.Math.*;

public class Vector2D {
	
	private double module;
	private double angulation;
	
	public Vector2D(int speed){
		setModule(speed);
		setAngulationFromCoordinates(0, 0);
	}
	
	public void setModule(int speed){
		module = speed;
	}
	public double getModule() {
		return module;
	}
	public void setAngulation(double angulation){
		this.angulation = angulation;
	}
	public double getAngulation() {
		return angulation;
	}
	public void setAngulationFromCoordinates(int oX, int oY){ //-1, 0
		if(oX == 0 && oY == 0){
			angulation = 0;
		}
		else {
			double theta = atan((double) (oY)/(double) (oX));
			// 2o e 3o quad
			if(oX < 0){
				angulation = toRadians(180) + theta;
			}
			// 4o quad
			else if(oY < 0){
				angulation = toRadians(360) + theta;
			}
			// 1o quad
			else {
				angulation = theta;
			}
		}
	}
// 0 gradi a dx e poi in senso orario
	public int getTranslationOnX(){
		double tX = module * cos(angulation);
		if(abs(tX) <= 0.5){
			return 0;
		}
		return round((float) (tX));
	}
	public int getTranslationOnY(){
		double tY = module * sin(angulation);
		if(abs(tY) <= 0.5){
			return 0;
		}
		return round((float) (tY));
	}
	public int getSignOnX(){
		if(getTranslationOnX() >= 0){
			return 1;
		}
		return -1;
	}
	public int getSignOnY(){
		if(getTranslationOnY() >= 0){
			return 1;
		}
		return -1;
	}
}






















