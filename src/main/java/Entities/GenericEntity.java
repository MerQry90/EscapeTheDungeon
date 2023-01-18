package Entities;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.sqrt;

public abstract class GenericEntity {

	private int x;
	private int y;
	private int speed;
	private int width;
	private int height;
	private double CBwidthScalar;
	private double CBheightScalar;
	private Image sprite;
	private CollisionBox cb;
	private boolean isActive = true;
	
	public GenericEntity(int x, int y) {
		this.x = x;
		this.y = y;
		init();
		cb = new CollisionBox(getX(), getY(), getWidth(), getHeight(), getCBwidthScalar(), getCBheightScalar());
	}
	
	public abstract void init();
	
	public void setInactive(){
		isActive = false;
	}
	public void setActive(){ isActive = true; }
	
	public boolean checkIfActive() {
		return isActive;
	}
	
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
	
	public void setSprite(String path){
		ImageIcon icon = new ImageIcon(path);
		sprite = icon.getImage();
	}
	public Image getSprite(){
		return sprite;
	}
	public CollisionBox getCollisionBox(){
		return cb;
	}
	
	public int keepXBoundaries(int tmpX){
		if(tmpX <= Background.LEFT_BOUND){
			return Background.LEFT_BOUND;
		}
		if(tmpX >= Background.RIGHT_BOUND - getWidth()){
			return Background.RIGHT_BOUND - getWidth();
		}
		return tmpX;
	}
	public int keepYBoundaries(int tmpY){
		if(tmpY <= Background.UPPER_BOUND){
			return Background.UPPER_BOUND;
		}
		if(tmpY >= Background.LOWER_BOUND - getHeight()){
			return Background.LOWER_BOUND - getHeight();
		}
		return tmpY;
	}
	
	public void moveUp(){
		setY(keepYBoundaries(getY() - getSpeed()));
	}
	public void moveDown(){
		setY(keepYBoundaries(getY() + getSpeed()));
	}
	public void moveLeft(){
		setX(keepXBoundaries(getX() - getSpeed()));
	}
	public void moveRight(){
		setX(keepXBoundaries(getX() + getSpeed()));
	}
	public void moveUpRight(){
		setX(keepXBoundaries((int) (getX() + getSpeed() / sqrt(2))));
		setY(keepYBoundaries((int) (getY() - getSpeed() / sqrt(2))));
	}
	public void moveUpLeft(){
		setX(keepXBoundaries((int) (getX() - getSpeed() / sqrt(2))));
		setY(keepYBoundaries((int) (getY() - getSpeed() / sqrt(2))));
	}
	public void moveDownRight(){
		setX(keepXBoundaries((int) (getX() + getSpeed() / sqrt(2))));
		setY(keepYBoundaries((int) (getY() + getSpeed() / sqrt(2))));
	}
	public void moveDownLeft(){
		setX(keepXBoundaries((int) (getX() - getSpeed() / sqrt(2))));
		setY(keepYBoundaries((int) (getY() + getSpeed() / sqrt(2))));
	}
	
	public void paint(Graphics g){
		g.drawImage(getSprite(), getX(), getY(), getWidth(), getHeight(), null);
		g.setColor(Color.CYAN);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.GREEN);
		g.drawRect(cb.getCBx(), cb.getCBy(), cb.getCBw(), cb.getCBh());
	}
	
}