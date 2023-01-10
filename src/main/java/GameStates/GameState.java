package GameStates;

import Application.KeyHandler;
import Entities.Background;
import Entities.Tile;

import java.awt.*;

public abstract class GameState {

	private boolean active;
	protected Background background = new Background();
	protected KeyHandler keyH = new KeyHandler();

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

	public abstract void processInput();
	
	public abstract void update();
}
