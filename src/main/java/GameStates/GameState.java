package GameStates;

import Application.KeyHandler;
import Components.Background;

import java.awt.*;

public abstract class GameState {

	private boolean active;
	protected Background background = new Background();
	protected KeyHandler keyH;
	
	public void render(Graphics g){
		background.paint(g);
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
