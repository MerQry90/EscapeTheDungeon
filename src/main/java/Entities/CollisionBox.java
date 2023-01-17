package Entities;

import java.awt.geom.Rectangle2D;

public class CollisionBox {
	private int CBx, CBy, CBw, CBh;
	private Rectangle2D hitBox;
	
	public CollisionBox(int Sx, int Sy, int Sw, int Sh) {
		setNewValues(Sx, Sy, Sw, Sh);
	}
	
	public int getCBx() {
		return CBx;
	}
	public int getCBy() {
		return CBy;
	}
	public int getCBw() {
		return CBw;
	}
	public int getCBh() {
		return CBh;
	}
	public Rectangle2D getHitBox(){
		return hitBox;
	}
	
	public void setNewValues(int Sx, int Sy, int Sw, int Sh){
		this.CBx = Sx + (Sw / 6);
		this.CBy = Sy + (Sh / 6);
		this.CBw = Sw * 2 / 3;
		this.CBh = Sh * 2 / 3;
		updateHitBox();
	}
	
	public void updateHitBox(){
		hitBox = new Rectangle2D.Double(getCBx(), getCBy(), getCBw(), getCBh());
	}
	
	public boolean checkCollision(CollisionBox otherCB){
		return this.getHitBox().intersects(otherCB.getHitBox());
	}
	
}
