package Components;

import Entities.DynamicEntities.Enemy;
import Entities.StaticEntities.Item;
import Entities.StaticEntities.Obstacle;
import Entities.StaticEntities.PowerUps.PowerUp;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private int ID;
	private boolean defeated;
	private boolean itemsDropped;

	private List<Enemy> enemies;
	private List<Obstacle> obstacles;
	private List<Item> items;
	private List<PowerUp> powerUpList;

	public Group(int ID){
		enemies = new ArrayList<>();
		obstacles = new ArrayList<>();
		items = new ArrayList<>();
		powerUpList = new ArrayList<>();
		this.ID = ID;
		defeated = false;
		itemsDropped = false;
	}

	public boolean isItemsDropped() {
		return itemsDropped;
	}

	public void setItemsDropped() {
		this.itemsDropped = true;
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

	public List<Item> getItems() {
		return items;
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
