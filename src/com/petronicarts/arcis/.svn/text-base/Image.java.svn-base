package com.petronicarts.arcis;

import java.io.IOException;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;



public class Image {

	private int width;
	private int height;
	public Bitmap image;
	private Bitmap originalImage;
	private boolean loaded;
	Matrix transform = new Matrix();
    private float angle;
    private float oldAngle;
    private float x;
    private float y;
    
	public Image(int Width, int Height, float X, float Y, float scaleX, float scaleY, String filePath, AssetManager assetMgr) {
		this.width = (int) (Width * scaleX);
		this.height = (int) (Height * scaleY);
		this.x = X;
		this.y = Y;
		angle = 0;
		oldAngle = angle;
		transform.setTranslate(x - width/2, y - height/2);
		try {
			this.image = BitmapFactory.decodeStream(assetMgr.open(filePath));
			this.image = Bitmap.createScaledBitmap(this.image, width, height, false);
			this.originalImage = this.image;
			loaded = true;
		} catch (IOException e) {
			loaded = false;			
		}
	}
	
	
	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float Angle) {
		if (oldAngle != Angle)
		{
			angle = Angle;
			transform.setTranslate(x - width/2, y - height/2);
			transform.preRotate(angle, width/2, height/2);
			//this.rotatedImage = Bitmap.createBitmap(originalImage, 0, 0, width, height, transform, false);
			oldAngle = angle;
		}
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float X) {
		this.x = X;
		transform.setTranslate(x - width/2, y - height/2);
		transform.preRotate(angle, width/2, height/2);
	}
	
	public void setY(float Y) {
		this.y = Y;
		transform.setTranslate(x - width/2, y - height/2);
		transform.preRotate(angle, width/2, height/2);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void draw(Canvas canvas, Paint paint) {
		//canvas.drawBitmap(rotatedImage, x - rotateOffsetX, y - rotateOffsetY, paint);
		canvas.drawBitmap(image, transform, paint);
	}
	
	public void drawStraight(Canvas canvas, Paint paint) {
		canvas.drawBitmap(image, x, y, paint);
	}
	
	public void drawCenter(Canvas canvas, Paint paint) {
		canvas.drawBitmap(image, x - image.getWidth()/2, y - image.getHeight()/2, paint);
	}
	
	public void scalePercent(float percent)
	{
		image = Bitmap.createScaledBitmap(originalImage, (int) (width*percent/100), (int) (height*percent/100), true);
	}
	
	public boolean Loaded()
	{
		return loaded;
	}
}
