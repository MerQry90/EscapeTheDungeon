package Components;

import Entities.DynamicEntities.Bat;
import Entities.DynamicEntities.Enemy;
import Entities.DynamicEntities.Zombie;
import Entities.StaticEntities.Rock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityGenerator {

	private String difficulty;
	private List<Group> groups;
	private EntityManager entityManager;
	private int bossRoomID;
	private int[] specialRoomsIDS;

	public EntityGenerator(EntityManager entityManager) {
		this.groups = new ArrayList<>();
		difficulty = "easy";
		this.entityManager = entityManager;
		specialRoomsIDS = new int[3];
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
	
	public void setSpecialRoomsIDS(int r1, int r2, int r3, int br){
		specialRoomsIDS[0] = r1;
		specialRoomsIDS[1] = r2;
		specialRoomsIDS[2] = r3;
		bossRoomID = br;
	}
	public boolean checkIfSpecial(int ID){
		for(int id: specialRoomsIDS){
			if(id == ID){
				return true;
			}
		}
		return false;
	}
	public boolean checkIfBossRoom(int ID){
		return ID == bossRoomID;
	}

	public void generateEntities(){
		Random random = new Random();
		for(Group group: groups) {
			if(bossRoomID == group.getID()){
				//STANZA DEL BOSS
			}
			else if(checkIfSpecial(group.getID())){
				//STANZA SPECIALE
			}
			else {
				//STANZA COMUNE CON I NEMICI
				switch (difficulty) {
					case "easy" -> {
						switch (random.nextInt(3)) {
							case 0 -> {
								group.getEnemies().add(new Zombie(64, 64, entityManager));
								group.getEnemies().add(new Zombie(64 * 15, 64, entityManager));
								group.getObstacles().add(new Rock(64 * 7, 64 * 4));
								group.getObstacles().add(new Rock(64 * 8, 64 * 5));
								group.getObstacles().add(new Rock(64 * 10, 64 * 5));
								group.getObstacles().add(new Rock(64 * 11, 64 * 4));
							}
							case 1 -> {
								group.getEnemies().add(new Bat(64, 64, entityManager));
								group.getEnemies().add(new Bat(64 * 15, 64, entityManager));
								group.getObstacles().add(new Rock(64 * 7, 64 * 4));
								group.getObstacles().add(new Rock(64 * 8, 64 * 5));
								group.getObstacles().add(new Rock(64 * 10, 64 * 5));
								group.getObstacles().add(new Rock(64 * 11, 64 * 4));
							}
							case 2 -> {
								group.getEnemies().add(new Zombie(64, 64, entityManager));
								group.getEnemies().add(new Bat(64 * 15, 64, entityManager));
								group.getObstacles().add(new Rock(64 * 7, 64 * 4));
								group.getObstacles().add(new Rock(64 * 8, 64 * 5));
								group.getObstacles().add(new Rock(64 * 10, 64 * 5));
								group.getObstacles().add(new Rock(64 * 11, 64 * 4));
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
}