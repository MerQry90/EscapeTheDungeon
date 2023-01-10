package Entities;

import javax.swing.*;
import java.awt.*;

public class Background {

	private final int width = 17 * 64;
	private final int length = 9 * 64;

	private Image background;
	private ImageIcon MainMenuBackground;
	private ImageIcon MainGameBackground;
	private ImageIcon EndBackground;

	private Tile tile;

	public Background(){
		tile = new Tile();
		MainMenuBackground = new ImageIcon("src/resources/sprites/backgrounds/MainMenu_PlaceHolder_2.png");
		MainGameBackground = new ImageIcon("src/resources/sprites/backgrounds/background_sample.png");
		EndBackground = new ImageIcon("src/resources/sprites/backgrounds/gameOver_placeHolder.png");
	}


	public void loadMainMenuBackground(){
		background = MainMenuBackground.getImage();
	}
	public void loadMainGameBackground(){
		background = MainGameBackground.getImage();
	}
	public void loadEndBackground(){
		background = EndBackground.getImage();
	}
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, width, length, null);
	}
}
