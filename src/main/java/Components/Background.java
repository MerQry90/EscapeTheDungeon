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

	private boolean showIntroductionText;
	private int introductionIndex;
	private String[] introductionText;
	
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
		introductionPages.add(BLACK_BACKGROUND);

		showIntroductionText = false;
		introductionIndex = 0;

		introductionText = new String[13];
		introductionText[0] = "You were walking in a forest, hunting all alone.";
		introductionText[1] = "These are though days, and you can rely only on your crossbow";
		introductionText[2] = "Everything is quiet and peaceful today, even the sun blesses";
		introductionText[3] = "you with his warm presence, the trees around you seems";
		introductionText[4] = "friendly, and you see a deer quietly walking around.";
		introductionText[5] = "The perfect prey for the perfect day. In the last";
		introductionText[6] = "days you didn't eat anything, so you silently start to";
		introductionText[7] = "follow it until the right time to shoot.";
		introductionText[8] = "Suddenly, a weird sound broke into the peaceful place,";
		introductionText[9] = "making the deer run away, and leaving you starving.";
		introductionText[10] = "But right after, an earthquake creates an huge crack on";
		introductionText[11] = "the ground, causing you to fall into the into the depths";
		introductionText[12] = "of the earth.";
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void loadBlackBackground(){
		background = BLACK_BACKGROUND.getImage();
		showIntroductionText = true;
	}
	public void loadCommandBackground(){
		background = COMMAND_BACKGROUND.getImage();
	}
	public void loadMainMenuBackground(){
		showIntroductionText = false;
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
	public void loadIntroductionText(String direction){
		if(Objects.equals(direction, "next") && introductionIndex < 12){
			introductionIndex += 1;
		}
		else if (Objects.equals(direction, "previous") && introductionIndex > 0) {
			introductionIndex -= 1;
		}
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
			g.setFont(new Font("Verdana", Font.BOLD, 10));
			g.drawString("Press ESC to return to the main menu, and left/right arrow to scroll text",
					Tile.getTile(1), Tile.getTile(9) - 10);
		}
	}
}
