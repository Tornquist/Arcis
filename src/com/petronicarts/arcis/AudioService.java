package com.petronicarts.arcis;

//import android.app.Service;

//import android.app.Activity;
import com.petronicarts.arcis.R;

import android.content.Context;
//import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
//import android.os.AsyncTask;
//import android.os.Binder;
import android.os.Handler;
import android.util.Log;
//import android.os.IBinder;

//public class AudioService extends Service {
public class AudioService {
    //private final IBinder mBinder = new LocalBinder();
    private MediaPlayer playerOne;
    private MediaPlayer playerTwo;
    private SoundManager SoundManager;
    private Context context;
    private boolean transitioning; 
    private int track = 0;
    private float volume = 100;
    final Handler handler = new Handler();
    private int oldLevel;
    private boolean oldPause;
    private int oldState;
    private boolean paused;
    private int fireSoundID = 0;
    
    public AudioService(Context CONTEXT)
    {
    	//Track:
    	//	1	Menu
    	
    	context = CONTEXT;
        playerOne = MediaPlayer.create(context, R.raw.menu);
        playerOne.setLooping(true); // Set looping
        playerOne.setVolume(volume/100,volume/100);
        track = 1;
        SoundManager = new SoundManager();
        SoundManager.initSounds(context);//.getBaseContext());
        SoundManager.addSound(1, R.raw.enemy_death);
        SoundManager.addSound(2, R.raw.death);
        SoundManager.addSound(3, R.raw.ice_charge_quiet);
        SoundManager.addSound(4, R.raw.level_up);
        SoundManager.addSound(5, R.raw.menu_select);
        SoundManager.addSound(6, R.raw.shockwave);
        SoundManager.addSound(7, R.raw.damage);
        SoundManager.addSound(8, R.raw.fire_active_quiet);
        SoundManager.addSound(9, R.raw.tower_upgrade_fire_new_2);
        SoundManager.addSound(10, R.raw.tower_upgrade_health_3);
        SoundManager.addSound(11, R.raw.tower_upgrade_ice_new_2);
        SoundManager.addSound(12, R.raw.land_bullet);
        SoundManager.addSound(13, R.raw.shoot_bullet);
        SoundManager.addSound(14, R.raw.hit);
        transitioning = false;
        
        oldPause = false;
        oldLevel = 1;
        oldState = 0;
        
        paused = false;        
    }
    
    public enum SoundEffect {
    	GameOver,
    	Ice_Charge,
    	Level_Up,
    	EnemyDestroyed,
    	Menu_Select,
    	Shockwave,
    	Damage,
    	Fire_Active,
    	TowerFire,
    	TowerIce,
    	TowerHeal,
    	Bullet_Land,
    	Bullet_Shoot,
    	Hit
    }
    
    public void PlaySound(SoundEffect sound)
    {
//    	SoundManager.playSound(index);
    	switch(sound)
    	{
    	case GameOver:
    		SoundManager.playSound(2);
    		break;
    	case Ice_Charge:
    		SoundManager.playSound(3);
    		break;
    	case Level_Up:
    		SoundManager.playSound(4);
    		break;
    	case EnemyDestroyed:
    		SoundManager.playSound(1);
    		break;
    	case Menu_Select:
    		SoundManager.playSound(5);
    		break;
    	case Shockwave:
    		SoundManager.playSound(6);
    		break;
    	case Damage:
    		SoundManager.playSound(7);
    		break;
    	case Fire_Active:
    		SoundManager.playSound(8);
    		break;
    	case TowerFire:
    		SoundManager.playSound(9);
    		break;
    	case TowerIce:
    		SoundManager.playSound(11);
    		break;
    	case TowerHeal:
    		SoundManager.playSound(10);
    		break;
    	case Bullet_Land:
    		SoundManager.playSound(12);
    		break;
    	case Bullet_Shoot:
    		SoundManager.playSound(13);
    		break;
    	case Hit:
    		SoundManager.playSound(14);
    		break;
		default:
			break;
    	}
    }
    
    public void PlayLoopedSound(SoundEffect sound)
    {
//    	SoundManager.playSound(index);
    	switch(sound)
    	{
    	case GameOver:
    		SoundManager.playLoopedSound(2, 1);
    		break;
    	case Ice_Charge:
    		SoundManager.playLoopedSound(3, 1);
    		break;
    	case Level_Up:
    		SoundManager.playLoopedSound(4, 1);
    		break;
    	case EnemyDestroyed:
    		SoundManager.playLoopedSound(1, 1);
    		break;
    	case Menu_Select:
    		SoundManager.playLoopedSound(5, 1);
    		break;
    	case Shockwave:
    		SoundManager.playLoopedSound(6, 1);
    		break;
    	case Damage:
    		SoundManager.playLoopedSound(7, 1);
    		break;
    	case Fire_Active:
			fireSoundID = SoundManager.playLoopedSound(8, 2);
    		break;
		default:
			break;
    	}
    }
    
    public void StopSound(SoundEffect sound)
    {
//    	SoundManager.playSound(index);
    	switch(sound)
    	{
    	case GameOver:
    		SoundManager.stopSound(2);
    		break;
    	case Ice_Charge:
    		SoundManager.stopSound(3);
    		break;
    	case Level_Up:
    		SoundManager.stopSound(4);
    		break;
    	case EnemyDestroyed:
    		SoundManager.stopSound(1);
    		break;
    	case Menu_Select:
    		SoundManager.stopSound(5);
    		break;
    	case Shockwave:
    		SoundManager.stopSound(6);
    		break;
    	case Damage:
    		SoundManager.stopSound(7);
    		break;
    	case Fire_Active:
    		SoundManager.stopSound(fireSoundID);
    		break;
		default:
			break;
    	}
    }
    
    //@Override
//    public void onCreate() {
//        super.onCreate();
//        player = MediaPlayer.create(this, R.raw.braincandy);
//        player.setLooping(true); // Set looping
//        player.setVolume(100,100);
//        
//        SoundManager = new SoundManager();
//        SoundManager.initSounds(getBaseContext());
//        SoundManager.addSound(1, R.raw.laser);
//        
//        transitioning = false;
//    }

//    public class LocalBinder extends Binder {
//        AudioService getService() {
//            return AudioService.this;
//        }
//    }
    
    public void StartMusic()
    {
    	if (playerOne != null)
    		playerOne.start();
    	if (playerTwo != null)
    		playerTwo.start();
    	paused = false;
    }
    
    public void StopMusic()
    {
    	//Log.d("AudioService", "StopMusic() Hit");
    	if (playerOne != null)
    	{
    		playerOne.pause();
    		//Log.d("AudioService", "playerOne Paused");
    	}
    	if (playerTwo != null)
    	{
    		playerTwo.pause();
    		//Log.d("AudioService", "playerTwoPaused");
    	}
    	paused = true;
    }

//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }
    
    
    
//    public void HalfOverSwitch()
//    {
//    	if (playerOne != null)
//    	{
//	    	if (( ((float)playerOne.getCurrentPosition()) / ((float)playerOne.getDuration()) > 0.5) && !transitioning)
//	    	{
//	    		transitioning = true;
//	    		//new AudioTransition(R.raw.dumped, 10000).execute();
//	    		//((Activity)context).runOnUiThread(new Runnable() { public void run() { AudioTransition task = new AudioTransition(R.raw.dumped, 10000); task.execute();} });
//	    		Log.d("Volume", "Started");
//	    		playerTwo = MediaPlayer.create(context, R.raw.dumped);
//	    		playerTwo.setVolume(0, 0);
//	    		playerTwo.setLooping(true);
//	    		playerTwo.start();
//	    	    handler.post(VolumeFadeRunnable);
//	    	    volume = 100;
//	    	}
//    	}
//    }
    
    final Runnable VolumeFadeRunnable = new Runnable(){
    	public void run() 
    	{
    		//Log.d("Volume", Float.toString(volume));
    		volume--;
    		playerOne.setVolume(volume/100, volume/100);
    		playerTwo.setVolume(1-volume/100, 1-volume/100);
            if(volume>0)
            {
            	if (playerOne.isPlaying() && playerTwo.isPlaying())
            		handler.postDelayed(this, 80);
            }
        	else
        	{
        		playerOne.reset();
        		playerTwo.setVolume(1, 1);
        		playerTwo.setLooping(false);
  		      	transitioning = false;
        	}
        }
    };
     
    public void update(int gameState, boolean pauseGame, int maxLevel)
    {
    	//Log.d("AudioService", "Update Hit");
    	if (!paused)
    	{
    		//Log.d("AudioService", "Update Running");
	    	if (gameState != 1 || (gameState == 1 && pauseGame))
	    	{
	    		if (track == 1)
	    		{
	    			if (!playerOne.isPlaying())
	    			{
	    				playerOne.start();
	    				playerOne.setLooping(true);
	    				if (playerTwo != null)
	    					playerTwo.reset();
	    			}
	    		}
	    		else
	    		{
	    			volume = 100;
	    			playerOne.reset();
	    			playerOne = MediaPlayer.create(context, R.raw.menu);
	    	        playerOne.setLooping(true); // Set looping
	    	        playerOne.setVolume(volume/100,volume/100);
	    	        playerOne.start();
	    	        track = 1;
	    		}
	    		
	    		if (playerTwo != null)
	    			if (playerTwo.isPlaying())
	    				playerTwo.stop();
	    	}
	    	else
	    	{
	//    		if (playerOne != null)
	//    			playerOne.reset();
	//    		if (playerTwo != null)
	//    			playerTwo.reset();
	//    		track = 0;
	    		
	    		if (oldPause) //Runs to put game back in correct music mode after a paused game. 
	    		{
	    			if (maxLevel < 40)
	    			{
	    				volume = 100;
	    				playerOne.reset();
	        			playerOne = MediaPlayer.create(context, R.raw.main_loop);
	        	        playerOne.setLooping(true); // Set looping
	        	        playerOne.setVolume(volume/100,volume/100);
	        	        playerOne.start();
	        	        track = 3;
	        	        
	        	        if (playerTwo != null)
	        	        	playerTwo.reset();
	    			}
	    			else
	    			{
	    				volume = 100;
	    				playerTwo.reset();
	    				playerTwo = MediaPlayer.create(context, R.raw.hard_loop);
	    				playerTwo.setLooping(true); // Set looping
	    				playerTwo.setVolume(volume/100,volume/100);
	    				playerTwo.start();
	        	        track = 5;
	        	        
	        	        
	        	        
	        	        if (playerOne != null)
	        	        	playerOne.reset();
	    			}
	    		}
	    		
	    		if (oldState != 1)//New Game
	    		{
	    			volume = 100;
					playerOne.reset();
	    			playerOne = MediaPlayer.create(context, R.raw.main_in);
	    	        playerOne.setLooping(true); // Set looping
	    	        playerOne.setVolume(volume/100,volume/100);
	    	        playerOne.start();
	    	        track = 2;
	    	        
	    	        playerOne.setOnCompletionListener(new OnCompletionListener() {

	    	            @Override
	    	            public void onCompletion(MediaPlayer playerOne) {
	    	                playerOne_PerformOnEnd();
	    	            }

	    	            });
	    		}
	    		
	    		if (oldLevel == 39 && maxLevel == 40)
	    		{
	    			
	    			playerTwo = MediaPlayer.create(context, R.raw.hard_in);
		    		playerTwo.setVolume(0, 0);
		    		playerTwo.setLooping(true);
		    		playerTwo.start();
		    	    handler.post(VolumeFadeRunnable);
		    	    volume = 100;
		    	    track = 4;
		    	    
		    	    playerTwo.setOnCompletionListener(new OnCompletionListener() {

		                @Override
		                public void onCompletion(MediaPlayer playerTwo) {
		                    playerTwo_PerformOnEnd();
		                }

		                });
	    		}
			}
	    	
	    	oldState = gameState;
	    	oldPause = pauseGame;
			oldLevel = maxLevel;
    	}
    	////Log.d("Audio Service", "Update() Ending");
	}
        
    
    public void playerOne_PerformOnEnd()
    {
    	if (track == 2)
    	{
    		volume = 100;
			playerOne.reset();
			playerOne = MediaPlayer.create(context, R.raw.main_loop);
	        playerOne.setLooping(true); // Set looping
	        playerOne.setVolume(volume/100,volume/100);
	        playerOne.start();
	        track = 3;
	        
	        if (playerTwo != null)
	        	playerTwo.stop();
    	}
    }
    
    public void playerTwo_PerformOnEnd()
    {
    	if (track == 4)
    	{
    		volume = 100;
			playerTwo.reset();
			playerTwo = MediaPlayer.create(context, R.raw.hard_loop);
			playerTwo.setLooping(true); // Set looping
			playerTwo.setVolume(volume/100,volume/100);
			playerTwo.start();
	        track = 5;
	        
	        if (playerOne != null)
	        	playerOne.stop();
    	}
    }
}
