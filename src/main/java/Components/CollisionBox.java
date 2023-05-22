package Components;

import java.awt.geom.Rectangle2D;

/**
 * Identifies the area in which an entity can detect a collision.
 * @author Michele Lugli
 * @author Simone Mercurio
 * @version 2023.05.21
 */
public class CollisionBox {
	private int CBx, CBy, CBw, CBh;
	private double wScalar, hScalar;
	private Rectangle2D hitBox;

	/**
	 *Initialize the area in which an entity can detect a collision using the following parameters:
	 * @param Ex Entity x: the x coordinate in which the entity is situated
	 * @param Ey Entity y: the y coordinate in which the entity is situated
	 * @param Ew Entity width
	 * @param Eh Entity height
	 * @param wScalar Width scalar: a value from 1 to 0
	 *                that determines if the collisionBox is as wide as the entity (1)
	 *                or smaller (0.9 to 0)
	 * @param hScalar Height scalar: a value from 1 to 0
	 * 	 *                that determines if the collisionBox is as high as the entity (1)
	 * 	 *                or smaller (0.9 to 0)
	 */
	public CollisionBox(int Ex, int Ey, int Ew, int Eh, double wScalar, double hScalar) {
		this.wScalar = wScalar;
		this.hScalar = hScalar;
		
		this.CBw = (int) ((double)(Ew) * wScalar);
		this.CBh = (int) ((double)(Eh) * hScalar);
		
		this.CBx = (int) (Ex + ((double)(Ew - this.CBw) / 2.0));
		this.CBy = (int) (Ey + ((double)(Eh - this.CBh) / 2.0));
		
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
	
	public void setCBx(int Ex, int Ew) {
		this.CBx = (int) (Ex + ((double)(Ew - this.CBw) / 2.0));
		if(hitBox != null){
			updateHitBox();
		}
	}
	public void setCBy(int Ey, int Eh) {
		this.CBy = (int) (Ey + ((double)(Eh - this.CBh) / 2.0));
		if(hitBox != null){
			updateHitBox();
		}
	}
	public void setCBw(int Ew) {
		this.CBw = (int) (Ew * wScalar);
		if(hitBox != null){
			updateHitBox();
		}
	}
	public void setCBh(int Eh) {
		this.CBh = (int) (Eh * hScalar);
		if(hitBox != null){
			updateHitBox();
		}
	}
	
	private void updateHitBox(){
		hitBox = new Rectangle2D.Double(getCBx(), getCBy(), getCBw(), getCBh());
	}
	
}
