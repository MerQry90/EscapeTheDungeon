package Entities;

public class Tile {
	int tileSize;

	public Tile (){
		setTileSize(64);
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileDimension){
		tileSize = tileDimension;
	}
}
