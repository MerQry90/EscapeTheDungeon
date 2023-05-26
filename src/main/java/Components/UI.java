package Components;

import Entities.DynamicEntities.Player;
import Entities.GenericEntity;

import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * Manages the User UI during the game.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class UI {
	private static final int MAP_ROOM_DIMENSION = 24;

	private Image NORMAL_ROOM;
	private Image SPECIAL_ROOM;
	private Image BOSS_ROOM;
	private Image DARK_ROOM;

	private Image DARK_SCREEN;

	private Image HEART_FULL;
	private Image HEART_EMPTY;
	
	private Image KEY;
	
	private Image NOTHING;

	private boolean mapEnabled;

	private List<Integer> specialRoomsIDS;
	private int bossRoomID;
	
	private int messageTime;
	private int messageIndex;
	private String[] messages;

	/**
	 * Initializes every variable, image and string needed for the correct UI visualization.
	 */
	public UI(){
		NORMAL_ROOM = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/mapTiles/NormalRoom.png")));
		SPECIAL_ROOM = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/mapTiles/SpecialRoom.png")));
		BOSS_ROOM = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/mapTiles/BossRoom.png")));
		DARK_ROOM = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/mapTiles/DarkRoom.png")));

		DARK_SCREEN = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/mapTiles/DarkScreen.png")));

		HEART_FULL = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/MainCharacter/Hearts/cuore.png")));
		HEART_EMPTY = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/MainCharacter/Hearts/cuore_vuoto.png")));
		
		KEY = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/Items/chiave.png")));
		
		NOTHING = GenericEntity.setSpriteFromPath(Objects.requireNonNull(getClass().getClassLoader().getResource(
				"sprites/invisible_cube.png")));

		mapEnabled = true;
		messageTime = 0;
		
		messageIndex = 0;
		messages = new String[5];
		messages[0] = "A mysterious force repel you...";
		messages[1] = "3 is better than 1!";
		messages[2] = "You feel better than ever!";
		messages[3] = "SUPER-FAST!";
		messages[4] = "PAUSE";
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

	public void enableMap(){
		mapEnabled = true;
	}

	public void disableMap(){
		mapEnabled = false;
	}
	
	public void drawKeyNumber(int numberOfKeys, Graphics g){
	
		if(numberOfKeys > 0){
			g.drawImage(KEY, 10, Tile.getTile(8) + 10, 40, 40, null);
		}
		else {
			g.drawImage(NOTHING, 10, Tile.getTile(8) + 10, 40, 40, null);
		}
		
		if(numberOfKeys > 1){
			g.drawImage(KEY, 60, Tile.getTile(8) + 10, 40, 40, null);
		}
		else {
			g.drawImage(NOTHING, 60, Tile.getTile(8) + 10, 40, 40, null);
		}
		
		if(numberOfKeys > 2){
			g.drawImage(KEY, 110, Tile.getTile(8) + 10, 40, 40, null);
		}
		else {
			g.drawImage(NOTHING, 110, Tile.getTile(8) + 10, 40, 40, null);
		}
	
	}
	
	public void setOnscreenMessage(int index){
		if(index != 4) {
			messageIndex = index;
			messageTime = 90;
		}
		else {
			messageIndex = index;
			messageTime += 1;
		}
	}
	public void updateOnscreenMessage(Graphics g){
		if(messageTime > 0) {
			g.setFont(new Font("Verdana", Font.BOLD, 30));
			g.setColor(Color.ORANGE);
			g.drawString(messages[messageIndex], Tile.getTile(1), Tile.getTile(8));
			messageTime -= 1;
		}
	}
	

	public void drawMap(List<Integer> foundRooms, List<Integer> almostFoundRooms, int playerCellID, Graphics g){
		if(mapEnabled){
			int IDX, IDY;
			int mapCellX, mapCellY;
			double xOffset, yOffset;
			xOffset = 12.75 * 64;
			yOffset = 0;
			//scorro tutte le celle
			g.drawImage(DARK_SCREEN, (int)xOffset + 24, (int)yOffset + 24, 9 * 24, 9 * 24, null);
			for(Integer ID: foundRooms){
				//ricavo la X e la Y della cella(per fare i calcoli sulle coordinate in cui devono essere dipinte)
				IDX = ID % 10;
				IDY = (ID - IDX) / 10;

				mapCellX = (int)xOffset + (IDX * MAP_ROOM_DIMENSION);
				mapCellY = (int)yOffset + (IDY * MAP_ROOM_DIMENSION);

				if(checkIfSpecialRoom(ID)){
					g.drawImage(SPECIAL_ROOM, mapCellX, mapCellY, MAP_ROOM_DIMENSION, MAP_ROOM_DIMENSION, null);
				}
				else if(checkIfBossRoom(ID)){
					g.drawImage(BOSS_ROOM, mapCellX, mapCellY, MAP_ROOM_DIMENSION, MAP_ROOM_DIMENSION, null);
				}
				else {
					g.drawImage(NORMAL_ROOM, mapCellX, mapCellY, MAP_ROOM_DIMENSION, MAP_ROOM_DIMENSION, null);
				}
				if(ID == playerCellID){
					//dipingo il quadrato rosso dove c'è il player
					g.drawImage(BOSS_ROOM, mapCellX + 8, mapCellY + 8, 8, 8, null);
				}
			}
			for(Integer ID: almostFoundRooms){
				IDX = ID % 10;
				IDY = (ID - IDX) / 10;


				mapCellX = (int)xOffset + (IDX * MAP_ROOM_DIMENSION);
				mapCellY = (int)yOffset + (IDY * MAP_ROOM_DIMENSION);

				g.drawImage(DARK_ROOM, mapCellX, mapCellY, MAP_ROOM_DIMENSION, MAP_ROOM_DIMENSION, null);
			}
		}
	}

	//METODI PER IL RICONOSCIMENTO DELLE CELLE
	public void setSpecialRoomsIDS(List<Integer> specialRoomsIDS){
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
