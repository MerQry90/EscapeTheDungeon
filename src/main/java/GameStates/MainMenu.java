package GameStates;


import Entities.Background;

import java.io.NotActiveException;

public class MainMenu extends GameState {

	private Background background;

	public MainMenu() {
		setActive();
		background = getBackground();
		background.loadMainMenuBackground();
	}
}
