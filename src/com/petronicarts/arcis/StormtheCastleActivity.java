package com.petronicarts.arcis;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class StormtheCastleActivity extends Activity{
    /** Called when the activity is first created. */
	MainGamePanel viewPanel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Window state functions.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                
        //This works without declaring a viewPanel instance here.
        //The instance declaration is needed to pass the 
        //onPause and onResume commands though.
        viewPanel = new MainGamePanel(this, this);
        setContentView(viewPanel);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        viewPanel.onCreate();
    }
    
    //Restarts the accelerometer after onPause
    protected void onResume() {
    	super.onResume();
        viewPanel.resume(this);
        
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        viewPanel.onStart();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus)
        {
        	viewPanel.gainedFocus();
        }
        else
        {
        	Log.d("Arcis", "Window Change Pause");
        	viewPanel.pause();
        }
    }

    
    @Override
    protected void onStop() {
        super.onStop();
        //viewPanel.onStop();
    }
    
    //Standard Method run when the Application loses focus.
    //This runs the pause() function in the viewPanel so that
    //the accelerometer can be paused.
    protected void onPause() {
    	Log.d("Arcis", "onPause Pause");
    	super.onPause();   
        viewPanel.pause();
         
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	viewPanel.destroy();
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        // do something on back.
	    	viewPanel.backHit();
	    	return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
    
    //private void stopForceScreenOn() {
    //    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    //} 
}