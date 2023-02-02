package Components;

import Entities.DynamicEntities.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UI {
	private Image NORMAL_ROOM;
	private Image SPECIAL_ROOM;
	private Image BOSS_ROOM;

	public UI(){
		ImageIcon icon = new ImageIcon("src/resources/sprites/mapTiles/NormalRoom.png");
		NORMAL_ROOM = icon.getImage();
		icon = new ImageIcon("src/resources/sprites/mapTiles/SpecialRoom.png");
		SPECIAL_ROOM = icon.getImage();
		icon = new ImageIcon("src/resources/sprites/mapTiles/BossRoom.png");
		BOSS_ROOM = icon.getImage();
	}

	public void drawUI(Graphics g, Player player){
		drawPlayerHeart(g, player);
		//chiamare qui tutti i metodi della UI
	}

	public void drawPlayerHeart(Graphics g, Player player){
		for(int i = 0; i < player.getMaxHealth(); i += 1){
			if(i < player.getHealth()) {
				g.drawImage(player.HEART_FULL, 2 + i * 40, 0, null);
			}
			else{
				g.drawImage(player.HEART_EMPTY, 2 + i * 40, 0, null);
			}
		}
	}

	public void drawMap(List<Cell> cells, int playerCellID ,Graphics g){
		int currentCellID;
		int cellX, cellY;
		int mapX, mapY;
		//scorro tutte le celle
		for(Cell cell: cells){
			//segno l'ID della cella corrente
			currentCellID = cell.getID();

			//ricavo la X e la Y della cella(per fare i calcoli sulle coordinate in cui devono essere dipinte)
			cellX = currentCellID % 10;
			cellY = currentCellID - cellX;

			//calcolo le coordinate
			mapX = (cellX + 4) * 64; //aggiungo 4 per far venire la mappa centrata
			mapY = (cellY / 10) * 64; //divido per 10 per ottenere la coordinata

			g.drawImage(NORMAL_ROOM, mapX, mapY, 64, 64, null);
			if(cell.getID() == playerCellID){
				//dipingo il quadrato rosso dove c'è il player
				g.drawImage(BOSS_ROOM, mapX + 16, mapY + 16, 32, 32, null);
			}
		}
	}
}
