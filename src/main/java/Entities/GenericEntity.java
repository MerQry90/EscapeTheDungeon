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
	private int speed;
	private int width;
	private int height;
	private double CBwidthScalar;
	private double CBheightScalar;
	private Image activeSprite;
	private CollisionBox cb;
	private boolean isActive = true;
	
	public GenericEntity(int x, int y, EntityManager entityManager) {
		this.x = x;
		this.y = y;
		this.entityManager = entityManager;
		
		init();
		cb = new CollisionBox(getX(), getY(), getWidth(), getHeight(), getCBwidthScalar(), getCBheightScalar());
	}
	
	public abstract void init();

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
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Image setSpriteFromPath(String path){
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
	
	//Metodi per il movimento delle entità------------------------------------------------------------------------------
	
	public int getTranslationX(int deltaX, int deltaY){
		double angle;
		int translationX;
		if(abs(deltaX) >= abs(deltaY)){
			angle = Math.atan((double) abs(deltaY) / abs(deltaX));
			translationX = (int) (getSpeed() * Math.cos(angle));
		}
		else {
			angle = Math.atan((double) abs(deltaX) / abs(deltaY));
			translationX = (int) (getSpeed() * Math.sin(angle));
		}
		if(deltaX < 0){
			return translationX * -1;
		}
		return translationX;
	}
	public int getTranslationY(int deltaX, int deltaY){
		double angle;
		int translationY;
		if(abs(deltaX) >= abs(deltaY)){
			angle = Math.atan((double) abs(deltaY) / abs(deltaX));
			translationY = (int) (getSpeed() * Math.sin(angle));
		}
		else {
			angle = Math.atan((double) abs(deltaX) / abs(deltaY));
			translationY = (int) (getSpeed() * Math.cos(angle));
		}
		if(deltaY < 0){
			return translationY * -1;
		}
		return translationY;
	}
	
	public void moveEntity(int tX, int tY){
		if(tX < 0){
			for(int i = 0; i > tX; i--){
				if(entityManager.checkObstaclesCollisions(this)) {
					setX(getX() + 1);
					break;
				}
				setX(getX() - 1);
			}
		}
		else {
			for(int i = 0; i < tX; i++){
				if(entityManager.checkObstaclesCollisions(this)){
					setX(getX() - 1);
					break;
				}
				setX(getX() + 1);
			}
		}
		if(tY < 0){
			for(int i = 0; i > tY; i--){
				if(entityManager.checkObstaclesCollisions(this)) {
					setY(getY() + 1);
					break;
				}
				setY(getY() - 1);
			}
		}
		else {
			for(int i = 0; i < tY; i++){
				if(entityManager.checkObstaclesCollisions(this)){
					setY(getY() - 1);
					break;
				}
				setY(getY() + 1);
			}
		}
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