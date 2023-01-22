package Components;

public class Tile {
	int tileSize;

	public Tile (){
		setTileSize(10);
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileDimension){
		tileSize = tileDimension;
	}
}
