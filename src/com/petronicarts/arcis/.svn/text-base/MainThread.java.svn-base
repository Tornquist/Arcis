package com.petronicarts.arcis;

import com.petronicarts.arcis.MainGamePanel;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

private SurfaceHolder surfaceHolder;
private MainGamePanel gamePanel;
private boolean running;
public boolean pleaseWait = true;

public void setRunning(boolean running) {
    this.running = running;
}

public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
    super();
    this.surfaceHolder = surfaceHolder;
    this.gamePanel = gamePanel;
    

}

@SuppressLint("WrongCall")
@Override
public void run() 
{
	boolean ScaleGame = true;
	//boolean SkipFrame = false;
	Bitmap screen = Bitmap.createBitmap(960, 540, Config.RGB_565);
    Canvas canvas;
    Canvas canvas2 = new Canvas(screen);
    Paint paint = new Paint();
    Matrix matrix = new Matrix();
    matrix.preScale(gamePanel.getScaleXValue(), gamePanel.getScaleYValue());
    if (gamePanel.getScaleXValue() == 1 && gamePanel.getScaleYValue() == 1)
    	ScaleGame = false;
    long startTime, elapsedTime;
    startTime = System.currentTimeMillis();
    elapsedTime = System.currentTimeMillis() - startTime;
    
    this.gamePanel.setScreenBitmap(screen);
    
    while (running) {
        if(!pleaseWait) {
            canvas = null;
            // try locking the canvas for exclusive pixel editing on the surface
            try {
                canvas = this.surfaceHolder.lockCanvas();
                if (canvas != null)
                {
	                if (ScaleGame)
	                	canvas.setMatrix(matrix);
	                synchronized (surfaceHolder) 
	                {
	                    // update game state
	                	elapsedTime = System.currentTimeMillis() - startTime;
	                	startTime = System.currentTimeMillis();
	                    this.gamePanel.update((float)elapsedTime);
	
	                    // draws the canvas on the panel
	                    this.gamePanel.setScreenBitmap(screen);
	                    //if (elapsedTime > 35 && !SkipFrame)
	                    //	SkipFrame = true;
	                    //else
	                    //	SkipFrame = false;
	                    //if (!SkipFrame)
	                    //{
	                	this.gamePanel.onDraw(canvas2);
	                    //}
	                    canvas.drawBitmap(screen, 0, 0, paint);
	                    
	                    
	                }
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }   // end finally            
        }
        else {
            synchronized (this) {
                try {
                    wait();
                } catch (Exception e) { }
            }
        }
    }
}
}
