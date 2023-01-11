package GameStates;

public class MainMenu extends GameState {

	/*
	il costruttore carica l'immagine, dopo aver settato 'active' lo stato
	 */
	public MainMenu() {
		setActive();
		background.loadMainMenuBackground();
	}

	@Override
	public void processInput(){
		if(keyH.enterPressed){
			setInactive();
		}
	}
	
	@Override
	public void update() {
		processInput();
	}
}
