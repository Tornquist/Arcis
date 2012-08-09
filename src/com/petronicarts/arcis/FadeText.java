package com.petronicarts.arcis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class FadeText {
	private float startX;
	private float startY;
	private float driftX;
	private float driftY;
	private float elapsedTime;
	private float fadeTime;
	private String text;
	private boolean terminate;
	private int textColor;
	private float percent;
	private float X;
	private float Y;
	private int textSize;
	
	public FadeText(String Text, float StartX, float StartY, float DriftX, float DriftY, float FadeTime, int color, int TextSize)
	{
		startX = StartX;
		startY = StartY;
		driftX = DriftX; 
		driftY = DriftY;
		text = Text;
		fadeTime = FadeTime;
		terminate = false;
		textColor = color;
		textSize = TextSize;
		percent = 1;
		X = startX + driftX*percent;
		Y = startY + driftY*percent;
	}
	
	public void Update(float ElapsedTime)
	{
		elapsedTime += ElapsedTime;
		percent = (((float) elapsedTime) / ((float)fadeTime));
		//Log.d("Percent: ", Float.toString(percent));
		X = startX + driftX*percent;
		Y = startY + driftY*percent;
		//Log.d("X: ", Float.toString(X));
		//Log.d("Y: ", Float.toString(Y));
		
		if (percent >= 1)
		{
			terminate = true;
		}
	}
	
	public void Draw(Canvas canvas, Paint paint)
	{
		if (!terminate)
		{
			paint.setColor(textColor);
			paint.setAlpha((int) (255*(1 - percent)));
			paint.setTextSize(textSize);
			canvas.drawText(text, X, Y, paint);		
		}
	}
	
	public boolean Kill()
	{
		return terminate;
	}
	
}
