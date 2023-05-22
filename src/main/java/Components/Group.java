package Components;

import Entities.DynamicEntities.Enemies.Enemy;
import Entities.StaticEntities.HeartItem;
import Entities.StaticEntities.Obstacle;
import Entities.StaticEntities.PowerUps.PowerUp;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure that identifies a group of enemies
 * linked to a specific room, used in the procedural
 * generation of the game.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Group {
	private int ID;
	private boolean defeated;

	private List<Enemy> enemies;
	private List<Obstacle> obstacles;
	private List<HeartItem> heartItems;
	private List<PowerUp> powerUpList;

	/**
	 * Uses the ID to generate the entities relative to a specific room.
	 * @param ID used to identify the room for which the entities are generated
	 */
	public Group(int ID){
		enemies = new ArrayList<>();
		obstacles = new ArrayList<>();
		heartItems = new ArrayList<>();
		powerUpList = new ArrayList<>();
		this.ID = ID;
		defeated = false;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
	public List<Obstacle> getObstacles() {
		return obstacles;
	}
	public List<PowerUp> getPowerUpList(){
		return powerUpList;
	}

	public List<HeartItem> getItems() {
		return heartItems;
	}
	public void addHeart(){
		heartItems.add(new HeartItem(512, 256));
	}

	public boolean isDefeated() {
		return defeated;
	}
	public void setAsDefeated() {
		this.defeated = true;
	}
	public int getID() {
		return ID;
	}
}
