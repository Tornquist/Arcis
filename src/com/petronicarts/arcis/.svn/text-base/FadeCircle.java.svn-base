package com.petronicarts.arcis;

import android.graphics.Canvas;
import android.graphics.Paint;

public class FadeCircle {
	private float x;
	private float y;
	private float radius;
	private int type;
	private float startRadius;
	private float endRadius;
	private float elapsedTime;
	private float fadeTime;
	private boolean terminate;
	private float percent;
	
	public FadeCircle(int Type, float CenterX, float CenterY, float StartRadius, float EndRadius, float FadeTime)
	{
		x = CenterX;
		y = CenterY;
		type = Type;
		fadeTime = FadeTime;
		if (fadeTime < 0)
			fadeTime = -fadeTime;
		terminate = false;
		
		percent = 1;
		startRadius = StartRadius;
		endRadius = EndRadius;
		radius = startRadius;
	}
	
	public void Update(float ElapsedTime)
	{
		elapsedTime += ElapsedTime;
		percent = (((float) elapsedTime) / ((float)fadeTime));
		radius = (endRadius - startRadius)*percent + startRadius;
		
		if (percent >= 1)
		{
			terminate = true;
		}
	}
	
	public void Draw(Canvas canvas, Paint paint)
	{
		if (!terminate)
		{
			switch (type) {
			case 1:
				paint.setARGB(255, 61, 190, 255);
				break;
			case 2:
				paint.setARGB(255, 192, 0, 32);
				break;
			case 4: //Switched 3 and 4 instead of going through and updating all of the logic in MainGamePanel.java
				paint.setARGB(255, 255, 194, 0);
				break;
			case 3:
			{
				//paint.setARGB(255, 255, 194, 0);
				paint.setARGB(255, (int) (255*(1 - percent)), (int) (194*(1 - percent)), 0);
				break;
			}
			default:
				paint.setARGB(255, 61, 190, 255);
				break;
			}
			paint.setAlpha((int) (255*(1 - percent)));
			canvas.drawCircle(x, y, radius, paint);
		}
	}
	
	public boolean Kill()
	{
		return terminate;
	}
	
}
