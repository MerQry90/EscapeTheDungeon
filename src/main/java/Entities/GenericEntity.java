package Entities;

import Components.CollisionBox;
import Components.EntityManager;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;

public abstract class GenericEntity {

	protected EntityManager entityManager;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private double CBwidthScalar;
	private double CBheightScalar;
	private Image activeSprite;
	private CollisionBox cb;
	private boolean isActive = true;
	
	public abstract void init();

	public void initCollisionBox(){
		cb = new CollisionBox(getX(), getY(), getWidth(), getHeight(), getCBwidthScalar(), getCBheightScalar());
	}
	
	//Attivazione e disattivazione dell'entità--------------------------------------------------------------------------
	public void setInactive(){
		isActive = false;
	}
	public void setActive(){ isActive = true; }
	
	public boolean checkIfActive() {
		return isActive;
	}
	//------------------------------------------------------------------------------------------------------------------

	//Getter e setter dei parametri dell'entità-------------------------------------------------------------------------
	public void setX(int x) {
		this.x = x;
		if(cb != null) {
			cb.setCBx(x, getWidth());
		}
	}
	public void setY(int y) {
		this.y = y;
		if(cb != null) {
			cb.setCBy(y, getHeight());
		}
	}
	public void setWidth(int width){
		this.width = width;
		if(cb != null){
			cb.setCBw(width);
			
		}
	}
	public void setHeight(int height){
		this.height = height;
		if(cb != null){
			cb.setCBh(height);
		}
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setXFromCenter(int x){
		setX(x - (getWidth() / 2));
	}
	public void setYFromCenter(int y){
		setY(y - (getHeight() / 2));
	}
	public int getCenterX(){
		return getX() + (getWidth() / 2);
	}
	public int getCenterY(){
		return getY() + (getHeight() / 2);
	}
	public static Image setSpriteFromPath(String path){
		ImageIcon icon = new ImageIcon(path);
		return icon.getImage();
	}
	public void setActiveSprite(Image newSprite){
		this.activeSprite = newSprite;
	}
	public Image getActiveSprite(){
		return activeSprite;
	}
	//------------------------------------------------------------------------------------------------------------------

	//Getter e setter per parametri della collision box-----------------------------------------------------------------
	public double getCBwidthScalar() {
		return CBwidthScalar;
	}
	public void setCBwidthScalar(double CBwidthScalar) {
		this.CBwidthScalar = CBwidthScalar;
	}
	public double getCBheightScalar() {
		return CBheightScalar;
	}
	public void setCBheightScalar(double CBheightScalar) {
		this.CBheightScalar = CBheightScalar;
	}
	public CollisionBox getCollisionBox(){
		return cb;
	}
	public boolean checkCollision(GenericEntity other){
		return this.getCollisionBox().getHitBox().intersects(other.getCollisionBox().getHitBox());
	}
	//------------------------------------------------------------------------------------------------------------------
	
	public void paint(Graphics g){
		g.drawImage(getActiveSprite(), getX(), getY(), getWidth(), getHeight(), null);
		g.setColor(Color.CYAN);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.GREEN);
		g.drawRect(cb.getCBx(), cb.getCBy(), cb.getCBw(), cb.getCBh());
	}
	
}