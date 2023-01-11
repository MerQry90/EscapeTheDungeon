package Entities;

import javax.swing.*;
import java.awt.*;

public abstract class Entity {

	private int x;
	private int y;
	private int speed;
	private int width;
	private int height;
	private Image sprite;
	private boolean isAlive = true;
	
	public void kill(){
		isAlive = false;
	}
	
	public boolean checkIsAlive() {
		return isAlive;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void moveUp(){
		setY(getY() - getSpeed());
	}
	public void moveDown(){
		setY(getY() + getSpeed());
	}
	public void moveLeft(){
		setX(getX() - speed);
	}
	public void moveRight(){
		setX(getX() + speed);
	}
	
	public void setSprite(String path){
		ImageIcon icon = new ImageIcon(path);
		sprite = icon.getImage();
	}
	public Image getSprite(){
		return sprite;
	}
	
	public void paint(Graphics g){
		g.drawImage(getSprite(), getX(), getY(), getWidth(), getHeight(), null);
	}
}
