package Components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Manages the backgrounds of the game and
 * some walls of text in the main menu
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class Background {
	/*
		width e height sono le misure del background e conseguentemente della finestra di gioco
		(per ora sono impostate manualmente e moltiplicate per 64 perché basate sulla scala del vecchio progetto)
		 */
	private final int width = 17 * 64;
	private final int height = 9 * 64;

	private boolean showIntroductionText, showCommandText, showGameOverText, showGameWinText;
	private int introductionIndex;

	private String[] introductionText;
	private String[] commandsText;

	private Image background;
	private ImageIcon MainMenuBackground, BLACK_BACKGROUND, MainGameBackground;

	/*
	Le tre immagini di background sono pre-caricate all'inizio del programma nel costruttore,
	in questo modo, quando lo stato cambia, basterà chiamare uno dei metodi sottostanti che non fa altro che cambiare
	background e associare l'immagine corretta
	 */

	/**
	 * pre-loads the background images (the main manu it's considered a background), initializes the boolean values
	 * used to display various information on the screen, and related information.
	 */
	public Background(){

		MainMenuBackground = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Backgrounds - Doors/menu.png")));
		BLACK_BACKGROUND = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Backgrounds - Doors/black_background.png")));
		MainGameBackground = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Backgrounds - Doors/murofin.png")));

		showIntroductionText = false;
		showCommandText = false;
		showGameOverText = false;
		showGameWinText = false;

		/*TESTO SCHERMATA COMANDI*/
		commandsText = new String[7];
		commandsText[0] = "- use W, A, S, D, to move the character";
		commandsText[1] = "- press the directional arrows to shoot";
		commandsText[2] = "- press ESC to pause the game";
		commandsText[3] = "- press M to toggle the map(on/off)";
		commandsText[4] = "        Cheat keys:";
		commandsText[5] = "- K to kill all enemies in the room";
		commandsText[6] = "- B to teleport instantly to the boss room";
	}

	public void loadBlackBackground(){
		showCommandText = true;
		background = BLACK_BACKGROUND.getImage();
	}
	public void loadMainMenuBackground(){
		showGameWinText = false;
		showGameOverText = false;
		showIntroductionText = false;
		showCommandText = false;
		background = MainMenuBackground.getImage();
	}
	public void loadMainGameBackground(){
		background = MainGameBackground.getImage();
	}
	public void loadGameOverBackground(){
		showGameOverText = true;
		background = BLACK_BACKGROUND.getImage();
	}
	public void loadGameWinBackground(){
		showGameWinText = true;
		background = BLACK_BACKGROUND.getImage();
	}

	public void paint(Graphics g){
		g.drawImage(background, 0, 0, width, height, null);
		if(showIntroductionText){
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", Font.BOLD, 30));
			for(int i = 0; i <= introductionIndex; i++){
				g.drawString(introductionText[i], 14,40 + (i * 40));
			}
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", Font.BOLD, 15));
			g.drawString("Press ESC to return to the main menu, and left/right arrow to scroll text",
					Tile.getTile(1), Tile.getTile(9) - 10);
		}
		if(showCommandText){
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", Font.BOLD, 30));
			for(int i = 0; i < commandsText.length; i++){
				g.drawString(commandsText[i], 14,64 + (i * 60));
			}
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", Font.BOLD, 15));
			g.drawString("Press ESC to return to the main menu", Tile.getTile(1), Tile.getTile(9) - 10);
		}
		if(showGameOverText){
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", Font.BOLD, 70));
			g.drawString("YOU DIED", Tile.getTile(5) + 16, Tile.getTile(4) + 16);
		}

		if(showGameWinText){
			g.setColor(Color.GREEN);
			g.setFont(new Font("Verdana", Font.BOLD, 70));
			g.drawString("YOU SURVIVED!", Tile.getTile(3) + 16, Tile.getTile(4) + 16);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", Font.BOLD, 20));
			g.drawString("good luck getting out of there!", Tile.getTile(5) + 32, Tile.getTile(5) + 16);
		}
	}
}
