package com.petronicarts.arcis;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.petronicarts.arcis.Image;

public class Bullet {

	private int x;			
	private int y;		
	private int z;
	private int baseX, baseY;
	private int lastX;			
	private int lastY;	
	private int terminate;
	public Image BulletImage;
	private float xDelta, yDelta, Gravity;
	private float finalTime;
	private float currentTime;
	private float velocity;
	private boolean active;
	private int cycles;
	//private float circleRadius;
	//private float originalRadius;
	
	public Bullet(int X, int Y, float XDelta, float YDelta, float Velocity, String image, float Angle, AssetManager assetMgr) {
		this.x = X;
		this.y = Y;
		this.baseX = this.x;
		this.baseY = this.y;
		this.z = 15;
		this.terminate = 0;
		this.BulletImage = new Image(25, 25, -100, -100, 1, 1, "SmallBullet.png", assetMgr);
		//circleRadius = 25;
		//originalRadius = circleRadius;
		float theta = (float) (Math.PI * Angle / 180);
		
		this.xDelta = (float) (Velocity*Math.cos(theta))*(-1);
		this.yDelta = (float) (Velocity*Math.sin(theta));
		
		this.currentTime = 0;
		if (XDelta != 0)
		{
			this.finalTime = (float) (XDelta / xDelta);
		}
		else
		{
			this.finalTime = (float) (YDelta / yDelta);
		}
		this.velocity = Velocity;
		this.Gravity = (float) 2*velocity/finalTime;
		this.active = false;
		this.cycles = 0;
		
		Log.d("BaseX: ",Float.toString(baseX));
		Log.d("BaseY: ",Float.toString(baseY));
		Log.d("Theta: ",Float.toString(theta));
		Log.d("xDelta: ",Float.toString(xDelta));
		Log.d("yDelta: ",Float.toString(yDelta));
		Log.d("XDelta: ",Float.toString(XDelta));
		Log.d("YDelta: ",Float.toString(YDelta));
		Log.d("Final Time: ", Float.toString(finalTime));
		Log.d("Gravity: ",Float.toString(Gravity));
		
		if (Gravity < 0)
			setTerminate();
		
//		Log.d("X: ", Integer.toString(X));
//		Log.d("Y: ", Integer.toString(Y));
//		Log.d("XDelta: ", Float.toString(XDelta));
//		Log.d("YDelta: ", Float.toString(YDelta));
//		Log.d("XDelta Unit: ", Float.toString(xDelta));
//		Log.d("YDelta Unit: ", Float.toString(yDelta));
//		Log.d("Velocity: ", Float.toString(Velocity));
//		Log.d("Angle: ", Float.toString(Angle));
//		Log.d("Final Time: ", Float.toString(finalTime));
//		Log.d("Gravity: ", Float.toString(finalTime));
//		Log.d("Theta: ", Float.toString(theta));
		
	}

	public int getZ() {
		return z;
	}
	public void setZ(int Z) {
		this.z = Z;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int X) {
		this.x = X;
	}
	public int getY() {
		return y;
	}
	
	public int getLastY() {
		return lastY;
	}
	
	public int getLastX()
	{
		return lastX;
	}
	
	public void setY(int Y) {
		this.y = Y;
	}

	public void draw(Canvas canvas, Paint paint) {
		BulletImage.drawCenter(canvas, paint);
		//paint.setARGB(255, 192, 162, 0);
		//canvas.drawCircle(x, y, circleRadius, paint);
		
		//Rect retVal = new Rect();
		//retVal.set((int) (BulletImage.getX() - BulletImage.getWidth()/2), (int) (BulletImage.getY() + BulletImage.getHeight()/2), (int) (BulletImage.getX() + BulletImage.getWidth()/2),(int)(BulletImage.getY() - BulletImage.getHeight()/2));
		//paint.setColor(Color.MAGENTA);
		//canvas.drawRect(retVal, paint);
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
	
	public boolean Active()
	{
		return active;
	}
	
	public Rect rect()
	{
		Rect retVal = new Rect();
		retVal.set((int) (BulletImage.getX() - BulletImage.getWidth()/2), (int) (BulletImage.getY() + BulletImage.getHeight()/2), (int) (BulletImage.getX() + BulletImage.getWidth()/2),(int)(BulletImage.getY() - BulletImage.getHeight()/2));
		
		return retVal;
	}
	
	public void update(float elapsedTime)
	{	
		Log.d("Bullet Z:", Float.toString(z));
		this.currentTime += elapsedTime;
		Log.d("CurrentTime: ", Float.toString(currentTime));
		
		this.lastX = x;
		this.lastY = y;
		this.BulletImage.setX(x);
		this.BulletImage.setY(y);
		this.x = (int) ((xDelta)*(currentTime) + baseX);
		this.y = (int) ((yDelta)*(currentTime) + baseY);
		this.z = (int) (velocity*currentTime - (Gravity/2)*((currentTime)*(currentTime)));
		if (z > 0)
		{
			//circleRadius = originalRadius*(100+z)/100;
			this.BulletImage.scalePercent(100+z);
		}
		else
		{
			//circleRadius = originalRadius;
			this.BulletImage.scalePercent(100);
		}
		
		
		//Log.d("Z:", Integer.toString(z));
		if (z > 150)
			terminate = 1;
		
		if (z < -10)
		{
			terminate = 1;
		}
		if (cycles > 3)
		{
			if (z < 25)
			{
				this.active = true;
			}
		}
		this.cycles += 1;
	}	
}
