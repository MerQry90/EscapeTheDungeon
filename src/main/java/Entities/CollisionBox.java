package Entities;

import java.awt.geom.Rectangle2D;

public class CollisionBox {
	private int CBx, CBy, CBw, CBh;
	private double wScalar, hScalar;
	private Rectangle2D hitBox;
	
	
	public CollisionBox(int Ex, int Ey, int Ew, int Eh, double wScalar, double hScalar) {
		this.wScalar = wScalar;
		this.hScalar = hScalar;
		
		this.CBx = (int) (Ex + ((double)(Ew) * (1.0 - wScalar)));
		this.CBy = (int) (Ey + ((double)(Eh) * (1.0 - hScalar)));
		this.CBw = (int) (Ew * wScalar);
		this.CBh = (int) (Eh * hScalar);
		
		updateHitBox();
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
	
	public void setCBx(int Ex) {
		this.CBx = (int) (Ex + ((double)(CBw) * (1.0 - wScalar) / 2.0));
		updateHitBox();
	}
	public void setCBy(int Ey) {
		this.CBy = (int) (Ey + ((double)(CBh) * (1.0 - hScalar) / 2.0));
		updateHitBox();
	}
	public void setCBw(int Ew) {
		this.CBw = (int) (Ew * wScalar);
		updateHitBox();
	}
	public void setCBh(int Eh) {
		this.CBh = (int) (Eh * hScalar);
		updateHitBox();
	}
	
	private void updateHitBox(){
		hitBox = new Rectangle2D.Double(getCBx(), getCBy(), getCBw(), getCBh());
	}
	
	public boolean checkCollision(CollisionBox otherCB){
		return this.getHitBox().intersects(otherCB.getHitBox());
	}
	
}
