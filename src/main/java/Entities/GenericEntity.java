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
	private Image sprite;
	private CollisionBox cb;
	private boolean isAlive = true;
	
	public GenericEntity(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		cb = new CollisionBox(getX(), getY(), getWidth(), getHeight());
	}
	
	public void kill(){
		isAlive = false;
	}
	
	public boolean checkIsAlive() {
		return isAlive;
	}
	
	public void setX(int x) {
		this.x = x;
		cb.setNewValues(getX(), getY(), getWidth(), getHeight());
	}
	public void setY(int y) {
		this.y = y;
		cb.setNewValues(getX(), getY(), getWidth(), getHeight());
	}
	public void setWidth(int width){
		this.width = width;
		cb.setNewValues(getX(), getY(), getWidth(), getHeight());
	}
	public void setHeight(int height){
		this.height = height;
		cb.setNewValues(getX(), getY(), getWidth(), getHeight());
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
	
	public void setSprite(String path){
		ImageIcon icon = new ImageIcon(path);
		sprite = icon.getImage();
	}
	public Image getSprite(){
		return sprite;
	}
	public CollisionBox getHitBox(){
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
	}
	
}
