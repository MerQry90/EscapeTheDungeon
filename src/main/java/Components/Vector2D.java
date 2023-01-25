package Components;

import static java.lang.Math.*;

public class Vector2D {
	
	private double module;
	private double angulation;
	
	public Vector2D(int speed){
		setModule(speed);
		setAngulationToObjective(0, 0);
	}
	
	public void setModule(int speed){
		module = speed;
	}
	public double getModule() {
		return module;
	}
	
	public void setAngulationToObjective(int oX, int oY){ //-1, 0
		if(oX == 0 && oY == 0){
			angulation = 0;
		}
		else {
			double theta = atan((double) (oY)/(double) (oX));
			// 3o quad
			if(oX < 0){
				angulation = toRadians(180) + theta;
				System.out.println(toDegrees(angulation));
			}
			// 4o quad
			else if(oY < 0){
				angulation = toRadians(360) + theta;
				System.out.println(toDegrees(angulation));
			}
			// 1o quad
			else {
				angulation = theta;
				System.out.println(toDegrees(angulation));
			}
		}
	}
	//0 gradi è verso il basso, quindi sin e cos sono invertiti
	public int getXTranslation(){
		double tX = module * cos(angulation);
		return round((float) (tX));
	}
	public int getYTranslation(){
		double tY = module * sin(angulation);
		return round((float) (tY));
	}
}






















