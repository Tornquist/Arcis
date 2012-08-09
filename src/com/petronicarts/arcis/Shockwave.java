package com.petronicarts.arcis;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Shockwave {
	private float x;
	private float y;
	private float radiusDelta;
	private float radius;
	private float baseRadius;
	private float totalTime;
	private float elapsedTime;
	private int color;
	private boolean terminate;
	
	public Shockwave(float X, float Y, float StartRadius, float EndRadius, float Time, int Color)
	{
		x = X;
		y = Y;
		radiusDelta = (EndRadius - StartRadius) / ((float)Time);
		color = Color;
		totalTime = Time;
		elapsedTime = 0;
		radius = StartRadius;
		baseRadius = radius;
		terminate = false;
	}
	
	public void Update(float ElapsedTime)
	{
		elapsedTime += ElapsedTime;
		radius = baseRadius + radiusDelta * elapsedTime;
		if (elapsedTime > totalTime)
			terminate = true;
	}
	
	public boolean Kill()
	{
		return terminate;
	}
	
	public void Draw(Canvas canvas, Paint paint)
	{
		if (!terminate)
		{
			paint.setColor(color);
			paint.setAlpha(100);
			canvas.drawCircle(x, y, radius, paint);
		}
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	public float getRadiusDelta()
	{
		return radiusDelta;
	}
}
