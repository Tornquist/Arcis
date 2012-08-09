package com.petronicarts.arcis;

import android.graphics.Canvas;
import android.graphics.Paint;

public class FadeBlock {
	private float x;
	private float y;
	private float radius;
	private int level;
	private float startRadius;
	private float endRadius;
	private float elapsedTime;
	private float fadeTime;
	private boolean terminate;
	private float percent;
	
	public FadeBlock(int Level, float CenterX, float CenterY, float StartRadius, float EndRadius, float FadeTime)
	{
		x = CenterX;
		y = CenterY;
		level = Level;
		fadeTime = FadeTime;
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
			switch (level) {
			case 1:
				paint.setARGB(255, 61, 190, 255);
				break;
			case 2:
				paint.setARGB(255, 162, 0, 194);
				break;
			case 3:
				paint.setARGB(255, 0, 194, 162);
				break;
			case 4:
				paint.setARGB(255, 129, 194, 0);
				break;
			case 5:
				paint.setARGB(255, 255, 85, 0);
				break;
			case 6:
				paint.setARGB(255, 255, 194, 0);
				break;
			default:
				paint.setARGB(255, 61, 190, 255);
				break;
			}
			paint.setAlpha((int) (255*(1 - percent)));

			canvas.drawRect(x-radius, y - radius, x + radius, y + radius, paint);
		}
	}
	
	public boolean Kill()
	{
		return terminate;
	}
	
}
