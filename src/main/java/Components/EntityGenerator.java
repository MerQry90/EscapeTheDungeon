package Components;

import Entities.DynamicEntities.Enemies.*;
import Entities.StaticEntities.HeartItem;
import Entities.StaticEntities.PowerUps.FastFeet;
import Entities.StaticEntities.PowerUps.Key;
import Entities.StaticEntities.PowerUps.LifeUp;
import Entities.StaticEntities.PowerUps.MultipleShot;
import Entities.StaticEntities.Rock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityGenerator {

	private List<Group> groups;
	private EntityManager entityManager;
	private int bossRoomID, specialRoomNumber;
	private List<Integer> specialRoomsIDS;

	public EntityGenerator(EntityManager entityManager) {
		this.groups = new ArrayList<>();
		this.entityManager = entityManager;
		specialRoomNumber = 0;
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
				group.getEnemies().add(new Boss(entityManager));
			}
			else if(checkIfSpecialRoom(group.getID())){
				//STANZA SPECIALE
				switch (specialRoomNumber){
					case 0 -> {
						group.getPowerUpList().add(new LifeUp(Tile.getTile(11), Tile.getTile(4)));
					}
					case 1 -> {
						group.getPowerUpList().add(new FastFeet(Tile.getTile(11), Tile.getTile(4)));
					}
					case 2 -> {
						group.getPowerUpList().add(new MultipleShot(Tile.getTile(11), Tile.getTile(4)));
					}
				}
				group.getPowerUpList().add(new Key(Tile.getTile(5), Tile.getTile(4)));
				specialRoomNumber += 1;
			}
			else if(group.getID() != 35){
				//STANZA COMUNE CON I NEMICI
				switch (random.nextInt(1)){
					case 0 -> {
						group.getEnemies().add(new Tank(Tile.getTile(8), Tile.getTile(3), entityManager));
						/*group.getEnemies().add(new Tank(Tile.getTile(8), Tile.getTile(3), entityManager));
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(7), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(7), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(9), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(9), Tile.getTile(4)));*/
					}
					case 1 -> {
						group.getEnemies().add(new Zombie(Tile.getTile(1), Tile.getTile(1), entityManager));
						group.getEnemies().add(new Zombie(Tile.getTile(1), Tile.getTile(7), entityManager));
						group.getEnemies().add(new Zombie(Tile.getTile(15), Tile.getTile(1), entityManager));
						group.getEnemies().add(new Zombie(Tile.getTile(15), Tile.getTile(7), entityManager));


						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(5)));
					}
					case 2 -> {
						group.getEnemies().add(new Shooter(Tile.getTile(8), Tile.getTile(4), entityManager));
					}
					case 3 -> {
						group.getEnemies().add(new Mage(Tile.getTile(8), Tile.getTile(4), entityManager));
					}
					case 4 -> {
						group.getEnemies().add(new Bat(Tile.getTile(7), Tile.getTile(4), entityManager));
						group.getEnemies().add(new Bat(Tile.getTile(9), Tile.getTile(4), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(1), Tile.getTile(1), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(15), Tile.getTile(7), entityManager));
					}
					case 5 -> {
						group.getEnemies().add(new Crab(Tile.getTile(1), Tile.getTile(1), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(1), Tile.getTile(7), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(15), Tile.getTile(1), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(15), Tile.getTile(7), entityManager));
					}
					case 6 -> {
						group.getEnemies().add(new Mage(Tile.getTile(7), Tile.getTile(2), entityManager));

						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(5)));


						//group.getEnemies().add(new Tank(Tile.getTile(7), Tile.getTile(2), entityManager))


					}
					case 7 -> {
						group.getEnemies().add(new Shooter(Tile.getTile(2), Tile.getTile(1), entityManager));
						group.getEnemies().add(new Shooter(Tile.getTile(14), Tile.getTile(1), entityManager));
						group.getEnemies().add(new Shooter(Tile.getTile(2), Tile.getTile(7), entityManager));
						group.getEnemies().add(new Shooter(Tile.getTile(14), Tile.getTile(7), entityManager));

						group.getEnemies().add(new Tank(Tile.getTile(7), Tile.getTile(4), entityManager));


						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(1)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(1)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(2)));

						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(6)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(7)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(6)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(7)));
					}
					//stanze di simone
					case 8 -> {
					
					}
					case 9 -> {
					
					}
					case 10 -> {
					
					}
					case 11 -> {
					
					}
					case 12 -> {
					
					}
					case 13 -> {
					
					}
					case 14 -> {
					
					}
					case 15 -> {
					
					}
					case 16 -> {
					
					}
					case 17 -> {
					
					}
					case 18 -> {
					
					}
					case 19 -> {
					
					}
					//stanze di michele
					case 20 -> {
						group.getObstacles().add(new Rock(Tile.getTile(1), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(1), Tile.getTile(6)));
						group.getObstacles().add(new Rock(Tile.getTile(1), Tile.getTile(7)));
						
						group.getObstacles().add(new Rock(Tile.getTile(2), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(2), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(2), Tile.getTile(4)));
						
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(6), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(6), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(6), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(7), Tile.getTile(1)));
						group.getObstacles().add(new Rock(Tile.getTile(7), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(9), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(9), Tile.getTile(7)));
						
						group.getObstacles().add(new Rock(Tile.getTile(10), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(10), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(10), Tile.getTile(5)));
						
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(14), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(14), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(14), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(15), Tile.getTile(1)));
						group.getObstacles().add(new Rock(Tile.getTile(15), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(15), Tile.getTile(3)));
					}
					case 21 -> {
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(6)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(6)));
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(4)));
						
						group.getEnemies().add(new Shooter(Tile.getTile(4), Tile.getTile(5), entityManager));
						group.getEnemies().add(new Shooter(Tile.getTile(12), Tile.getTile(3), entityManager));
						
						group.getEnemies().add(new Zombie(Tile.getTile(8), Tile.getTile(4), entityManager));
					}
					case 22 -> {
						group.getObstacles().add(new Rock(Tile.getTile(2), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(2), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(2), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(5)));
						
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(5)));
						
						group.getObstacles().add(new Rock(Tile.getTile(6), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(6), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(6), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(10), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(10), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(10), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(5)));
						
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(6)));
						
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(5)));
						
						group.getObstacles().add(new Rock(Tile.getTile(14), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(14), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(14), Tile.getTile(6)));
						
						group.getEnemies().add(new Tank(Tile.getTile(7) + 32, Tile.getTile(3) + 32, entityManager));
					}
					case 23 -> {
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(6)));
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(6), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(7), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(2)));
						
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(6)));
						group.getObstacles().add(new Rock(Tile.getTile(9), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(10), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(2)));
						
						group.getEnemies().add(new Bat(Tile.getTile(8), Tile.getTile(4), entityManager));
						group.getEnemies().add(new Bat(Tile.getTile(2), Tile.getTile(2), entityManager));
						
						group.getEnemies().add(new Shooter(Tile.getTile(14), Tile.getTile(6), entityManager));
					}
					case 24 -> {
						group.getEnemies().add(new Crab(Tile.getTile(5), Tile.getTile(6), entityManager));
						group.getEnemies().add(new Mage(Tile.getTile(9), Tile.getTile(4), entityManager));
					}
					case 25 -> {
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(5)));
						
						group.getObstacles().add(new Rock(Tile.getTile(7), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(7), Tile.getTile(6)));
						group.getObstacles().add(new Rock(Tile.getTile(7), Tile.getTile(7)));
						
						group.getObstacles().add(new Rock(Tile.getTile(9), Tile.getTile(1)));
						group.getObstacles().add(new Rock(Tile.getTile(9), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(9), Tile.getTile(3)));
						
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(5)));
						
						group.getEnemies().add(new Ghost(Tile.getTile(5), Tile.getTile(4), entityManager));
						group.getEnemies().add(new Ghost(Tile.getTile(11), Tile.getTile(4), entityManager));
					}
					case 26 -> {
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(5), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(6), Tile.getTile(4)));
						
						group.getObstacles().add(new Rock(Tile.getTile(10), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(11), Tile.getTile(4)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(4)));
						
						group.getEnemies().add(new Zombie(Tile.getTile(5), Tile.getTile(2), entityManager));
						group.getEnemies().add(new Zombie(Tile.getTile(5), Tile.getTile(6), entityManager));
						group.getEnemies().add(new Zombie(Tile.getTile(11), Tile.getTile(2), entityManager));
						group.getEnemies().add(new Zombie(Tile.getTile(11), Tile.getTile(6), entityManager));
					}
					case 27 -> {
						group.getObstacles().add(new Rock(Tile.getTile(2), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(3), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(5)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(6)));
						group.getObstacles().add(new Rock(Tile.getTile(4), Tile.getTile(7)));
						
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(1)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(2)));
						group.getObstacles().add(new Rock(Tile.getTile(12), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(13), Tile.getTile(3)));
						group.getObstacles().add(new Rock(Tile.getTile(14), Tile.getTile(3)));
						
						group.getEnemies().add(new Shooter(Tile.getTile(3), Tile.getTile(6), entityManager));
						group.getEnemies().add(new Shooter(Tile.getTile(13), Tile.getTile(2), entityManager));
						
						group.getEnemies().add(new Mage(Tile.getTile(8), Tile.getTile(4), entityManager));
					}
					case 28 -> {
						group.getObstacles().add(new Rock(Tile.getTile(8), Tile.getTile(4)));
						
						group.getEnemies().add(new Zombie(Tile.getTile(3), Tile.getTile(6), entityManager));
						group.getEnemies().add(new Zombie(Tile.getTile(13), Tile.getTile(2), entityManager));
						
						group.getEnemies().add(new Ghost(Tile.getTile(3), Tile.getTile(2), entityManager));
						group.getEnemies().add(new Ghost(Tile.getTile(13), Tile.getTile(6), entityManager));
					}
					case 29 -> {
						group.getEnemies().add(new Crab(Tile.getTile(5), Tile.getTile(4), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(6), Tile.getTile(3), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(6), Tile.getTile(5), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(7), Tile.getTile(4), entityManager));
						
						group.getEnemies().add(new Crab(Tile.getTile(9), Tile.getTile(4), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(10), Tile.getTile(3), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(10), Tile.getTile(5), entityManager));
						group.getEnemies().add(new Crab(Tile.getTile(11), Tile.getTile(4), entityManager));
					}
				}
			}
		}
	}

	public void generateHearts(int id){
		Random random = new Random();
		if(random.nextInt(3) == 0){
			getGroupByID(id).addHeart();
		}
	}
}