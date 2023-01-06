package GameStates;

import Entities.Background;
import Entities.Tile;

public abstract class GameState {

	private boolean active;
	private Background background = new Background();

	public Background getBackground() {
		return background;
	}

	public boolean isActive(){
		return active;
	}
	public void setActive(){
		active = true;
	}
	public void setInactive() {
		active = false;
	}

	public void initialize() {

	}

	public void update() {

	}

	public void finish() {

	}
}
