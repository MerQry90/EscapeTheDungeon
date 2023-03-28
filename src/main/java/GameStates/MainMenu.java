package GameStates;

import Application.KeyHandler;

public class MainMenu extends GameState {
	public boolean showIntroduction, showCommands;
	public int inputCountdown;

	/*
	il costruttore carica l'immagine, dopo aver settato 'active' lo stato
	 */
	public MainMenu(KeyHandler keyH) {
		this.keyH = keyH;
		showCommands = false;
		showIntroduction = false;
		inputCountdown = 6;
		setActive();
		background.loadMainMenuBackground();
	}

	@Override
	public void processInput(){
		inputCountdown --;
		if(keyH.enterPressed && !showIntroduction && !showCommands){
			setInactive();
		}
		if(inputCountdown <= 0) {
			if (keyH.iPressed && !showCommands || showIntroduction) {
				inputCountdown = 6;
			}
			else if (keyH.cPressed || showCommands) {
				inputCountdown = 6;
				showCommands = true;
				background.loadCommandBackground();
				if (keyH.escapePressed) {
					inputCountdown = 6;
					background.loadMainMenuBackground();
					showCommands = false;
				}
			} else if (keyH.escapePressed) {
				System.exit(0);
			}
		}
	}
	
	@Override
	public void update() {
		processInput();
	}
}
