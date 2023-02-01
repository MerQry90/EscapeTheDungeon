package Components;

import Entities.DynamicEntities.Enemy;
import Entities.StaticEntities.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private int ID;
	private boolean defeated;
	private boolean roomIsDeadEnd;

	private List<Enemy> enemies;
	private List<Obstacle> obstacles;

	public Group(int ID, boolean roomIsDeadEnd){
		enemies = new ArrayList<>();
		obstacles = new ArrayList<>();
		this.ID = ID;
		defeated = false;
		this.roomIsDeadEnd = roomIsDeadEnd;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
	public List<Obstacle> getObstacles() {
		return obstacles;
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
	
	public boolean isRoomADeadEnd() {
		return roomIsDeadEnd;
	}
}
