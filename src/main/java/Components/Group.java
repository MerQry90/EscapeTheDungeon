package Components;

import Entities.DynamicEntities.Enemies.Enemy;
import Entities.StaticEntities.HeartItem;
import Entities.StaticEntities.Obstacle;
import Entities.StaticEntities.PowerUps.PowerUp;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private int ID;
	private boolean defeated;

	private List<Enemy> enemies;
	private List<Obstacle> obstacles;
	private List<HeartItem> heartItems;
	private List<PowerUp> powerUpList;

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
