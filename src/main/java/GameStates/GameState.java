package GameStates;

import Application.KeyHandler;
import Components.AudioManager;
import Components.Background;

import java.awt.*;

/**
 * Defines the actual state of the game.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public abstract class GameState {

	private boolean active;
	protected Background background = new Background();
	protected KeyHandler keyH;
	public AudioManager audioManager;
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
