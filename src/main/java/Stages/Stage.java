package Stages;

import Entities.Enemy;
import Entities.Zombie;

import java.util.List;
import java.util.Random;

public class Stage {

	private Random random;
	int stageNumber;

	public Stage(){
		random = new Random();
	}

	public void loadRandomStage(List<Enemy> enemies){
		stageNumber = random.nextInt(10);
		switch(stageNumber){
			case 0:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;
			case 1:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;
			case 2:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;
			case 3:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;
			case 4:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;
			case 5:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;
			case 6:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;
			case 7:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;
			case 8:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;
			case 9:
				enemies.add(new Zombie(30, 30));
				enemies.add(new Zombie(800, 30));
				enemies.add(new Zombie(30, 400));
				enemies.add(new Zombie(800, 400));
				break;

		}
	}
}