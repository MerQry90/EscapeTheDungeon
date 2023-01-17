package Stages;

import Entities.Enemy;
import Entities.Zombie;

import java.util.List;

//E' TUTTO TEMPORANEO
public class Stage {

	public Stage(List<Enemy> enemies){
		enemies.add(new Zombie(30, 30));
		enemies.add(new Zombie(200, 30));
	}

	/*public void loadStage(List<Enemy> enemies){
		enemies.add(new Zombie(30, 30));
		enemies.add(new Zombie(200, 30));
	}*/
}