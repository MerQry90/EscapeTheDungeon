package Components;

import Entities.DynamicEntities.Enemies.Bat;
import Entities.DynamicEntities.Enemies.Enemy;

import java.util.List;
import java.util.Random;

public class Stage {

	private Random random;
	int stageNumber;
	EntityManager entityManager;
	
	public Stage(EntityManager entityManager){
		random = new Random();
		this.entityManager = entityManager;
	}

	public void loadRandomStage(List<Enemy> enemies){
		stageNumber = random.nextInt(1);
		switch(stageNumber){
			case 0:
				/*
				per testing inserire come parametro in random.nextInt(1) in modo da fare
				uscire solo la prima stanza e inserire appunto i nemici da testare qui
				 */
				//enemies.add(new Dodger(256, 256));
				enemies.add(new Bat(256, 256, entityManager));
				//enemies.add(new Bat(700, 256));

				break;
		}
	}
}