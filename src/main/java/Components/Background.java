package Components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Background {
	
	protected final static int UPPER_BOUND = 64;
	protected final static int LOWER_BOUND = 64 * 8;
	protected final static int LEFT_BOUND = 64;
	protected final static int RIGHT_BOUND = 64 * 16;
	private int index;
	
	/*
	width e height sono le misure del background e conseguentemente della finestra di gioco
	(per ora sono impostate manualmente e moltiplicate per 64 perché basate sulla scala del vecchio progetto)
	 */
	private final int width = 17 * 64;
	private final int height = 9 * 64;

	private Image background;
	private ImageIcon MainMenuBackground, BLACK_BACKGROUND, COMMAND_BACKGROUND, MainGameBackground, GameOverBackground, GameWinBackground;
	private ArrayList<ImageIcon> introductionPages;

	private Tile tile;

	/*
	Le tre immagini di background sono pre-caricate all'inizio del programma nel costruttore,
	in questo modo, quando lo stato cambia, basterà chiamare uno dei metodi sottostanti che non fa altro che cambiare
	background e associare l'immagine corretta
	 */
	public Background(){
		tile = new Tile();
		introductionPages = new ArrayList<>();

		MainMenuBackground = new ImageIcon("src/resources/sprites/backgrounds/MainMenu_PlaceHolder_2.png");
		BLACK_BACKGROUND = new ImageIcon("src/resources/sprites/backgrounds/black_background.png");
		COMMAND_BACKGROUND = new ImageIcon("src/resources/sprites/backgrounds/mainmenucommands.png");

		MainGameBackground = new ImageIcon("src/resources/sprites/Backgrounds - Doors/murofin.png");
		GameOverBackground = new ImageIcon("src/resources/sprites/backgrounds/gameOver_placeHolder.png");
		GameWinBackground = new ImageIcon("src/resources/sprites/backgrounds/victory_screen.png");
		introductionPages.add(GameOverBackground);
		introductionPages.add(GameWinBackground);
		index = 0;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void loadBlackBackground(){
		background = BLACK_BACKGROUND.getImage();
	}
	public void loadCommandBackground(){
		background = COMMAND_BACKGROUND.getImage();
	}
	public void loadMainMenuBackground(){
		background = MainMenuBackground.getImage();
	}
	public void loadMainGameBackground(){
		background = MainGameBackground.getImage();
	}
	public void loadGameOverBackground(){
		background = GameOverBackground.getImage();
	}
	public void loadGameWinBackground(){
		background = GameWinBackground.getImage();
	}
	public void loadIntroductionPage(String direction){
		background = introductionPages.get(index).getImage();
		if(Objects.equals(direction, "next") && index < 5){
			index += 1;
		}
		else if (index > 0) {
			index -= 1;
		}
		background = introductionPages.get(index).getImage();
	}
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, width, height, null);
	}
}
