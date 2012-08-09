package com.petronicarts.arcis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class SimpleBullet 
{
	private float xPos;
	private float yPos;
	private int terminate;
	private float xDelta;
	private float yDelta;
	private float xStart;
	private float yStart;
	private float maxDistance;
	private float damage;
	private Bitmap image;
	private float width;
	private float height;
	private int source;
	
	public SimpleBullet(float X, float Y, float XDelta, float YDelta, float Velocity, float MAX, float Damage, Bitmap Image, int Source)
	{
		xPos = X;
		yPos = Y;
		xStart = X;
		yStart = Y;
		
		float mag = Distance(0,0,XDelta,YDelta);
		XDelta = XDelta/mag;
		YDelta = YDelta/mag;
		xDelta = XDelta*Velocity;
		yDelta = YDelta*Velocity;
		
		maxDistance = MAX;
		damage = Damage;
		
		terminate = 0;
		
		image = Image;
		width = image.getWidth();
		height = image.getHeight();
		source = Source;
	}
	
	public boolean iceBullet()
	{
		if (source == 2)
			return true;
		else
			return false;
	}
	
	public float getDamage()
	{
		return damage;
	}
		
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(image, xPos - width/2, yPos - height/2, paint);
	}
	
	public void update(float elapsedTime)
	{
		xPos += xDelta*elapsedTime;
		yPos += yDelta*elapsedTime;
		
		float mag = Distance(xStart, yStart, xPos, yPos);
		if (mag > maxDistance)
		{
			setTerminate();
		}				
	}
	
	public boolean kill()
	{
		boolean retVal = false;
		if (this.terminate == 0)
			retVal = false;
		else if (this.terminate == 1)
			retVal = true;
		return retVal;
	} 
	
	public void setTerminate()
	{
		terminate = 1;
	}
	
	public Rect rect()
	{
		Rect retVal = new Rect();
		retVal.set((int) (xPos - width/2), (int) (yPos - height/2), (int) (xPos + width/2),(int)(yPos - height/2));
		
		return retVal;
	}
	
	float Distance(float x1, float y1, float x2, float y2)
	{
		float retVal = 0;
		retVal = (float) (Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
		retVal = (float) Math.pow(retVal, .5);
		return retVal;
	}

}
