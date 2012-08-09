package com.petronicarts.arcis;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TowerEffect {

	private float x;
	private float y;
	private int colorR;
	private int colorG;
	private int colorB;
	private float timer;
	private float effectLength;
	private int radius;
	private boolean terminate;
	
	public TowerEffect(float X, float Y, int ColorR, int ColorG, int ColorB, long EffectLength, int Radius)
	{
		x = X;
		y = Y;
		colorR = ColorR;
		colorG = ColorG;
		colorB = ColorB;
		effectLength = EffectLength;
		radius = Radius;
		timer = 0;
		terminate = false;
	}
	
	public boolean Kill()
	{
		return terminate;
	}
	
	public void Update(float elapsedTime)
	{
		timer += elapsedTime;
		if (timer > effectLength)
			terminate = true;
	}
	
	public void Draw(Canvas canvas, Paint paint)
	{
		if (!terminate)
		{
			paint.setARGB(150, colorR, colorG, colorB);
			canvas.drawCircle(x, y, radius, paint);	
		}
	}
}
