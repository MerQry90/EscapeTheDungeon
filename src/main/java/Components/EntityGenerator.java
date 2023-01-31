package Components;

import Entities.DynamicEntities.Bat;
import Entities.DynamicEntities.Enemy;
import Entities.DynamicEntities.Zombie;
import Entities.StaticEntities.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityGenerator {

	private String difficulty;
	private List<Group> groups;
	private EntityManager entityManager;

	public EntityGenerator(EntityManager entityManager) {
		this.groups = new ArrayList<>();
		difficulty = "easy";
		this.entityManager = entityManager;
	}

	public void addGroup(int ID){
		groups.add(new Group(ID));
	}

	public Group getGroupByID(int ID){
		for(Group group: groups){
			if(ID == group.getID()){
				return group;
			}
		}
		return null;
	}

	public void generateEntities(){
		Random random = new Random();
		for(Group group: groups) {
			switch (difficulty) {
				case "easy" -> {
					switch (random.nextInt(3)) {
						case 0 -> {
							group.getEnemies().add(new Zombie(64, 64, entityManager));
							group.getEnemies().add(new Zombie(64 * 15, 64, entityManager));
							group.getObstacles().add(new Obstacle(64 * 7, 64 * 4, 64, 64));
							group.getObstacles().add(new Obstacle(64 * 8, 64 * 5, 64, 64));
							group.getObstacles().add(new Obstacle(64 * 10, 64 * 5, 64, 64));
							group.getObstacles().add(new Obstacle(64 * 11, 64 * 4, 64, 64));
						}
						case 1 -> {
							group.getEnemies().add(new Bat(64, 64, entityManager));
							group.getEnemies().add(new Bat(64 * 15, 64, entityManager));
							group.getObstacles().add(new Obstacle(64 * 7, 64 * 4, 64, 64));
							group.getObstacles().add(new Obstacle(64 * 8, 64 * 5, 64, 64));
							group.getObstacles().add(new Obstacle(64 * 10, 64 * 5, 64, 64));
							group.getObstacles().add(new Obstacle(64 * 11, 64 * 4, 64, 64));
						}
						case 2 -> {
							group.getEnemies().add(new Zombie(64, 64, entityManager));
							group.getEnemies().add(new Bat(64 * 15, 64, entityManager));
							group.getObstacles().add(new Obstacle(64 * 7, 64 * 4, 64, 64));
							group.getObstacles().add(new Obstacle(64 * 8, 64 * 5, 64, 64));
							group.getObstacles().add(new Obstacle(64 * 10, 64 * 5, 64, 64));
							group.getObstacles().add(new Obstacle(64 * 11, 64 * 4, 64, 64));

						}
					}
				}
				case "medium" -> {

				}
				case "hard" -> {

				}
			}
		}
	}
}