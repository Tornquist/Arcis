package com.petronicarts.arcis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Rectangle {

	private int x;			
	private int y;			
	private int width;
	private int height;
	private Rect rectangle = new Rect();
	private int collideFinger;
	private int touched;
	private int offsetX;
	private int offsetY;

	public Rectangle(int X, int Y, int Width, int Height) {
		this.x = X;
		this.y = Y;
		this.width = Width;
		this.height = Height;
		this.rectangle.set(x,y,x+width,y+height);
		this.collideFinger = -1;
		this.touched = 0;	
		this.offsetX = 0;
		this.offsetY = 0;
	}

	
	public int getX() {
		return x;
	}
	public void setX(int X) {
		this.x = X;
		this.rectangle.set(x,y,x+width,y+height);
	}
	public int getY() {
		return y;
	}
	public void setY(int Y) {
		this.y = Y;
		this.rectangle.set(x,y,x+width,y+height);
	}
	public Rect getRect() {
		return rectangle;
	}
	public int getWidth()
	{
		return this.width;
	}
	public int getHeight()
	{
		return this.height;
	}

	public void draw(Canvas canvas, Paint paint) {
		canvas.drawRect(rectangle, paint);
	}
	
	public boolean collides(Rect rect)
	{
		return this.rectangle.intersect(rect);
	}
	
	public void setTouched(int touchFinger)
	{
		this.touched = 1;
		this.collideFinger = touchFinger;
	}
	
	public boolean touched()
	{
		boolean retVal = false;
		if (this.touched == 0)
			retVal = false;
		else if (this.touched == 1)
			retVal = true;
		return retVal;
	}
	
	public int touchFinger()
	{
		return this.collideFinger;
	}
	
	public void setOffset(int xPos, int yPos)
	{
		this.offsetX = this.x - xPos;
		this.offsetY = this.y - yPos;
	}
	
	public void dragY(int yPos)
	{
		setY((this.offsetY + yPos));
	}
	
	public void dragX(int xPos)
	{
		setX((this.offsetX + xPos));
	}
	
	public void liftFinger(){
		this.touched = 0;
		this.collideFinger = -1;
	}
	

}
