package Components;

import javax.swing.*;
import java.awt.*;

public class Background {
	
	protected final static int UPPER_BOUND = 64;
	protected final static int LOWER_BOUND = 64 * 8;
	protected final static int LEFT_BOUND = 64;
	protected final static int RIGHT_BOUND = 64 * 16;
	
	/*
	width e height sono le misure del background e conseguentemente della finestra di gioco
	(per ora sono impostate manualmente e moltiplicate per 64 perché basate sulla scala del vecchio progetto)
	 */
	private final int width = 17 * 64;
	private final int height = 9 * 64;

	private Image background;
	private ImageIcon MainMenuBackground;
	private ImageIcon MainGameBackground;
	private ImageIcon GameOverBackground;
	private ImageIcon GameWinBackground;

	private Tile tile;

	/*
	Le tre immagini di background sono pre-caricate all'inizio del programma nel costruttore,
	in questo modo, quando lo stato cambia, basterà chiamare uno dei metodi sottostanti che non fa altro che cambiare
	background e associare l'immagine corretta
	 */
	public Background(){
		tile = new Tile();
		MainMenuBackground = new ImageIcon("src/resources/sprites/backgrounds/MainMenu_PlaceHolder_2.png");
		MainGameBackground = new ImageIcon("src/resources/sprites/backgrounds/backgroundNew_placeholder.png");
		GameOverBackground = new ImageIcon("src/resources/sprites/backgrounds/gameOver_placeHolder.png");
		GameWinBackground = new ImageIcon("src/resources/sprites/backgrounds/victory_screen.png");
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, width, height, null);
	}
}
