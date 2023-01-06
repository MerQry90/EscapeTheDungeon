package Entities;


import javax.swing.*;

public class Background {

	private final int width = 17;
	private final int length = 9;

	private ImageIcon background;

	private Tile tile;

	public Background(){
		tile = new Tile();
	}

	/*
	Image I/O recognises the contents of the file as a JPEG format image,
	and decodes it into a BufferedImage which can be directly used by Java 2D.
	 */
	public void loadMainMenuBackground(){
		background = new ImageIcon("src/resources/sprites/backgrounds/MainMenu_PlaceHolder_2.png");
	}
}
