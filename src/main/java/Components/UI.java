package Components;

import Entities.DynamicEntities.Player;
import Entities.GenericEntity;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UI {
	private Image NORMAL_ROOM;
	private Image SPECIAL_ROOM;
	private Image BOSS_ROOM;
	private Image DARK_SCREEN;
	private Image HEART_FULL;
	private Image HEART_EMPTY;

	private int[] specialRoomsIDS;
	private int bossRoomID;

	public UI(){
		NORMAL_ROOM = GenericEntity.setSpriteFromPath("src/resources/sprites/mapTiles/NormalRoom.png");
		SPECIAL_ROOM = GenericEntity.setSpriteFromPath("src/resources/sprites/mapTiles/SpecialRoom.png");
		BOSS_ROOM = GenericEntity.setSpriteFromPath("src/resources/sprites/mapTiles/BossRoom.png");
		DARK_SCREEN = GenericEntity.setSpriteFromPath("src/resources/sprites/mapTiles/DarkScreen.png");
		HEART_FULL = GenericEntity.setSpriteFromPath("src/resources/sprites/png/full_heart.png");
		HEART_EMPTY = GenericEntity.setSpriteFromPath("src/resources/sprites/png/empty_heart.png");
	}

	public void drawUI(Graphics g, Player player){
		drawPlayerHeart(g, player);
		//chiamare qui tutti i metodi della UI
	}

	public void drawPlayerHeart(Graphics g, Player player){
		for(int i = 0; i < player.getMaxHealth(); i += 1){
			if(i < player.getHealth()) {
				g.drawImage(HEART_FULL, 2 + i * 40, 0, null);
			}
			else{
				g.drawImage(HEART_EMPTY, 2 + i * 40, 0, null);
			}
		}
	}

	public void drawMap(List<Cell> cells, int playerCellID ,Graphics g){
		int currentCellID;
		int IDX, IDY;
		int mapCellX, mapCellY;
		//scorro tutte le celle
		g.drawImage(DARK_SCREEN, 0, 0, 17 * 64, 9 * 64, null);
		for(Cell cell: cells){
			//segno l'ID della cella corrente
			currentCellID = cell.getID();

			//ricavo la X e la Y della cella(per fare i calcoli sulle coordinate in cui devono essere dipinte)
			IDX = currentCellID % 10;
			IDY = currentCellID - IDX;

			//calcolo le coordinate
			mapCellX = (IDX + 4) * 64; //aggiungo 4 per far venire la mappa centrata
			mapCellY = (IDY / 10) * 64 - 64; //divido per 10 per ottenere la coordinata

			if(checkIfSpecialRoom(cell.getID())){
				g.drawImage(SPECIAL_ROOM, mapCellX, mapCellY, 64, 64, null);
			}
			else if(checkIfBossRoom(cell.getID())){
				g.drawImage(BOSS_ROOM, mapCellX, mapCellY, 64, 64, null);
			}
			else {
				g.drawImage(NORMAL_ROOM, mapCellX, mapCellY, 64, 64, null);
			}
			if(cell.getID() == playerCellID){
				//dipingo il quadrato rosso dove c'è il player
				g.drawImage(BOSS_ROOM, mapCellX + 16, mapCellY + 16, 32, 32, null);
			}
		}
	}

	//METODI PER IL RICONOSCIMENTO DELLE CELLE
	public void setSpecialRoomsIDS(int[] specialRoomsIDS){
		this.specialRoomsIDS = specialRoomsIDS;
	}
	public boolean checkIfSpecialRoom(int ID){
		for(int id: specialRoomsIDS){
			if(id == ID){
				return true;
			}
		}
		return false;
	}
	public void setBossRoomID(int bossRoomID){
		this.bossRoomID = bossRoomID;
	}
	public boolean checkIfBossRoom(int ID){
		return ID == bossRoomID;
	}
}
