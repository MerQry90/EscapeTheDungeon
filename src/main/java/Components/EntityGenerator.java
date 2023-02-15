package Components;

import Entities.DynamicEntities.Enemies.Shooter;
import Entities.DynamicEntities.Enemies.Tank;
import Entities.StaticEntities.PowerUps.LifeUp;
import Entities.StaticEntities.PowerUps.MultipleShot;
import Entities.StaticEntities.Rock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityGenerator {

	private String difficulty;
	private List<Group> groups;
	private EntityManager entityManager;
	private int bossRoomID;
	private List<Integer> specialRoomsIDS;

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
	
	public void setSpecialRoomsIDS(List<Integer> specialRoomsIDS){
		this.specialRoomsIDS = specialRoomsIDS;
	}
	public boolean checkIfSpecialRoom(int ID){
		for(int id: specialRoomsIDS){
			if(id == ID){
				return true;
			}
		}
		return false;
	}
	public void setBossRoomID(int bossRoomID){
		this.bossRoomID = bossRoomID;
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
			else if(checkIfSpecialRoom(group.getID())){
				//STANZA SPECIALE
				group.getPowerUpList().add(new LifeUp());
				group.getPowerUpList().add(new MultipleShot());
			}
			else if(group.getID() != 35){
				//STANZA COMUNE CON I NEMICI
				switch (difficulty) {
					case "easy" -> {
						switch (random.nextInt(2)) {
							case 0 -> {
								
								//group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(3)));
								group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(4)));
								//group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(5)));
								//group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(3)));
								group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(4)));
								//group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(5)));


								group.getEnemies().add(new Tank(Tile.getTile(7), Tile.getTile(2), entityManager));
								group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(3)));
								group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(4)));
								group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(5)));
								group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(3)));
								group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(4)));
								group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(5)));

							}
							case 1 -> {
								group.getEnemies().add(new Shooter(Tile.getTile(7), Tile.getTile(2), entityManager));
								group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(3)));
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