package Entities;

import java.util.ArrayList;
import java.util.List;

public class EntityControl {
	Entity player;
	List<Entity> enemyList;
	List<Entity> projectileList;
	
	public EntityControl(){
		player = new Player();
		enemyList = new ArrayList<>();
		projectileList = new ArrayList<>();
	}
	
	
}
