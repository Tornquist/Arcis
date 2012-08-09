package com.petronicarts.arcis;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import com.petronicarts.stormthecastle.AudioService.LocalBinder;
import com.petronicarts.arcis.Bullet;
import com.petronicarts.arcis.FadeBlock;
import com.petronicarts.arcis.FadeCircle;
import com.petronicarts.arcis.FadeText;
import com.petronicarts.arcis.Node;
import com.petronicarts.arcis.Rectangle;
import com.petronicarts.arcis.Shockwave;
import com.petronicarts.arcis.SimpleBullet;
import com.petronicarts.arcis.TowerEffect;
import com.petronicarts.arcis.Unit;
import com.petronicarts.arcis.AudioService.SoundEffect;

import android.app.Activity;

//import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
//import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
//import android.os.IBinder;
//import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback 
{
	//Variable Declarations.
	private MainThread thread;
			
	public int screenWidth;
	public int screenHeight;   
	
	Paint paint;
	
	public float fingerData[][] = new float[4][3];
	public Rect fingerRects[] = new Rect[4];
	public int fingerState[] = new int[4];
	public int fingerRegion[] = new int[4];
	public boolean fingerIgnore[] = new boolean[4];
	public boolean fingerCentralTower[] = new boolean[4];
	
	public static final int DEFAULT_WIDTH = 960;
	public static final int DEFAULT_HEIGHT = 540;
		
	public Rectangle SliderRect;
	public Rectangle ShooterButtonRect;
	public Rectangle FireButtonRect;
	public Rectangle IceButtonRect;
	public Rectangle HealButtonRect;
	
	public Rectangle ButtonOne;
	public Rectangle ButtonTwo;
	public Rectangle ButtonThree;
	
	public float SliderMin;
	public float SliderMax;
	public float Angle;
	public float CannonCenterX;
	public float CannonCenterY;
	public float CannonEndX;
	public float CannonEndY;
	public float Speed;
	public List<Bullet> Bullets = new ArrayList<Bullet>();
	public List<Unit> Units = new ArrayList<Unit>();
	public List<SimpleBullet> SimpleBullets = new ArrayList<SimpleBullet>();
	public List<TowerEffect> TowerEffects = new ArrayList<TowerEffect>();
	public List<FadeText> FadingText = new ArrayList<FadeText>();
	public List<FadeBlock> FadingBlocks = new ArrayList<FadeBlock>();
	public List<FadeCircle> FadingCircles = new ArrayList<FadeCircle>();
	public List<Shockwave> Shockwaves = new ArrayList<Shockwave>();
	public int CostArray[][] = new int[30][14];
	public int LastCostArray[][] = new int[30][14];
	//Bullet newBullet = new Bullet(0, 0, 0, 0, 0, 0, 0, 0);
	public Image Tower;
	public Image ControlBackground;
	public Image ShootButtonIn;
	public Image ShootButtonOut;
	public Image Slider;
	public Image SmallBullet;
	public Image CrossHairs;
	public float CrossHairMag;
	public float CrossHairDelta;
	public Image FireButtonIn;
	public Image FireButtonOut;
	public Image IceButtonIn;
	public Image IceButtonOut;
	public Image HealButtonIn;
	public Image HealButtonOut;
	public int PrepBullet = 0;
	public int ShockwaveCost = 1000;
	
	public int GameBoard[][] = new int[30][14];
	public int DirectionMap[][] = new int[30][14];
	public float TowerData[][][] = new float[30][14][3];
	public float TowerTimer[][] = new float[30][14];
	public int TowerLevel[][] = new int[30][14];
	public int TowerWallMap[][] = new int[30][14];
	public int BaseDirectionMap[][] = new int[30][14];
	public boolean IceTowerActive[][] = new boolean[30][14];
	
	public Bitmap direction_Horizontal;
	public Bitmap direction_Vertical;
	public Bitmap direction_Diagonal_1;
	public Bitmap direction_Diagonal_2;
	
	public Bitmap fireBullet;
	
	public float spawnCounter;
	public float initSpawnCounter;
	public boolean allowSpawn;
	public float initSpawnTime;
	
	public Context gameContext;
	public Activity gameActivity;
	
	public int deviceWidth;
	public int deviceHeight;
	public float scaleX;
	public float scaleY;
	
	public float maxHealth;
	public float health;
	public boolean justPause = false;
	public float gold;
	public boolean pauseGame = false;
	public int maxLevel;
	public int oldLevel;
	public boolean gameOver = false;
	
	public int experiencePoints;
	public int highscore;
	public int gameState = -1;
	public int towerPower = 0;
	public Bitmap screen;
	public Bitmap FireButtonThumbnail;
	public Bitmap IceButtonThumbnail;
	public Bitmap HealButtonThumbnail;
	
	public Bitmap WallPiece1;
	public Bitmap WallPiece2;
	public Bitmap WallPiece3;
	public Bitmap WallPiece4;
	public Bitmap WallPiece5;
	public Bitmap WallPiece6;
	public Bitmap WallPiece7;
	public Bitmap WallPiece8;
	public Bitmap WallPiece9;
	public Bitmap WallPiece10;
	public Bitmap WallPiece11;
	public Bitmap WallPiece12;
	public Bitmap WallPiece13;
	public Bitmap WallPiece14;
	public Bitmap WallPiece15;
	public Bitmap WallPiece16;
	public Bitmap WallPiece17;
	
	public Bitmap Heal_1;
	public Bitmap Heal_2;
	public Bitmap Heal_3;
	public Bitmap Fire_1;
	public Bitmap Fire_2;
	public Bitmap Fire_3;
	public Bitmap Ice_1;
	public Bitmap Ice_2;
	public Bitmap Ice_3;
	
	public Bitmap control_overlay;
	public Bitmap Tower_Ice;
	public Bitmap Tower_Heal;
	public Bitmap Tower_Fire;
	public Bitmap Tower_AttackReady;
	
	public Bitmap Enemy_1;
	public Bitmap Enemy_2;
	public Bitmap Enemy_3;
	public Bitmap Enemy_4;
	public Bitmap Enemy_5;
	public Bitmap Enemy_6;
	
	public Bitmap MainMenu;
	public Bitmap MenuBackground;
	public Bitmap intro;
	public Bitmap backButton;
	public Bitmap menuFade;
	
	public boolean JustStarted = true;
		
	public float ElapsedTime = 0;
	public float IntroTimer = 0;
	
	public Bitmap backArrow;
	public Bitmap Instructions_1; 
	public Bitmap Instructions_2;
	public Bitmap Instructions_3;
//	public Bitmap Instructions_4;
//	public Bitmap Instructions_5;
//	public Bitmap Instructions_6;
//	public Bitmap Instructions_7;
	public Bitmap Inst1;
	public Bitmap Inst2;
	public Bitmap Inst3;
	public int InstructionsCount;
	public int InstructionsIndex;
	public float InstructionsX;
	public float InstructionsY;
	public float instStartX;
	public float instStartY;
	public int instScrollDirection;
	public boolean instTransitioning;
	public float InstructionsOldX;
	public float InstructionsOldY;
	public float InstructionsDriftY;
	public Rect srcRect;
	public Rect destRect;
	public boolean newHighscore;
	public int InstructionsBumpScroll;
	public int instructionsJump;
	
	public Bitmap AboutText;
	public Bitmap QuestionBox;
	public Bitmap pausedText;
	public Bitmap gameOverText;
	public Bitmap instructionPosition;
	public String InstructionsNextImage;
	public int InstructionsNextReplace;
	public boolean InstructionsNextLoad = false;
	public int InstructionsNextIndex;
	
	public Bitmap donate;
	public Bitmap donateText;
	
	public Typeface LargeNine;
	public boolean ShowDialog;
	public int DialogOption;
	public Bitmap newgameButton;
	public Bitmap resumeButton;
	public Bitmap quitButton;
	public Bitmap hourglass;
	
	public Bitmap joystick;
	
	public AudioService AudioService;
    public boolean AudioServiceBound = false;
	
	public boolean DevMode;
	
	public Bitmap FireThumb;
	public Bitmap IceThumb;
	public Bitmap HealThumb;
	
	public boolean fireSoundPlay;
	
	public boolean slidingControlScheme; 
	public boolean enableSound;
	public boolean enableMusic;
	public boolean showTutorial;
	public boolean skipButton;
	public int tutorialIndex;
	public int tutorialCount = 8;
	//TODO: Set Max Tutorial Pages
	
	public Bitmap leftArrow;
	public Bitmap rightArrow;
	public Bitmap upArrow;
	public Bitmap downArrow;
	
	public Bitmap SlideBar;
	public Bitmap JoystickStart;
	
	//public Bitmap HardMode;
	public boolean HardModeEnabled;
	public boolean HardModeActive;
	public boolean HardModeShowUpgrade;
	public boolean EasyModeActive;
		
	public static boolean displayDonation = false;
	
	//public Bitmap enemyGlow;
	
	public MainGamePanel(Context context, Activity activity)
	{
		super(context);
		gameContext = context;
		gameActivity = activity;
		getHolder().addCallback(this);
		
		thread = new MainThread(getHolder(),this);
		
		paint = new Paint();
		paint.setAntiAlias(true);
			
		Display display = ((Activity) context).getWindowManager().getDefaultDisplay(); 
		screenWidth = 960; //display.getWidth();
		screenHeight = 540; //display.getHeight();
		setScale(display.getWidth(), display.getHeight());
//								
//		Log.d("Display.getWidth(): ", Integer.toString(display.getWidth()));
//		Log.d("Display.getHeight(): ", Integer.toString(display.getHeight()));
//		
//		DisplayMetrics metrics = new DisplayMetrics();
//		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		
//		Log.d("Metrics.getWidth(): ", Integer.toString(metrics.widthPixels));
//		Log.d("Metrics.getHeight(): ", Integer.toString(metrics.heightPixels));
//		
//		setScale(metrics.widthPixels, metrics.heightPixels);
//		
		//SET INITIAL CONDITIONS
		initVariables();
		initMenu(); // Changes ButtonOne and ButtonTwo states.  Reset correctly then initVariables is run again.
		//END SET INITIAL CONDITIONS	
		//Load Images
		AssetManager assetMgr = context.getAssets();
		Tower = new Image(100, 100, CannonCenterX, CannonCenterY, 1, 1, "Tower.png", assetMgr); 
		ControlBackground = new Image(960, 100, 0, screenHeight - 100, 1, 1, "ControlBackground.png", assetMgr);
		ShootButtonIn = new Image(100, 100, screenWidth - 100, screenHeight - 100, 1, 1, "ShootButtonIn.png",assetMgr);
		ShootButtonOut = new Image(100, 100, screenWidth - 100, screenHeight - 100, 1, 1, "ShootButtonOut.png",assetMgr);
		FireButtonIn = new Image(100, 100, screenWidth - 365, screenHeight - 100, 1, 1, "FireButtonIn.png",assetMgr);
		FireButtonOut = new Image(100, 100, screenWidth - 365, screenHeight - 100, 1, 1, "FireButtonOut.png",assetMgr);
		IceButtonIn = new Image(100, 100, screenWidth - 250, screenHeight - 100, 1, 1, "IceButtonIn.png",assetMgr);
		IceButtonOut = new Image(100, 100, screenWidth - 250, screenHeight - 100, 1, 1, "IceButtonOut.png",assetMgr);
		HealButtonIn = new Image(100, 100, screenWidth - 480, screenHeight - 100, 1, 1, "HealButtonIn.png",assetMgr);
		HealButtonOut = new Image(100, 100, screenWidth - 480, screenHeight - 100, 1, 1, "HealButtonOut.png",assetMgr);
		Slider = new Image(36, 54, -100, -100, 1, 1, "Slider.png", assetMgr);
		Slider.setY((float) (SliderRect.getY() - 4));
		SmallBullet = new Image(25, 25, -100, -100, 1, 1, "SmallBullet.png", assetMgr);
		CrossHairs = new Image(32, 32, -100, -100, 1, 1, "CrossHairs.png", assetMgr);

		//END LOAD IMAGES
				
		HardModeShowUpgrade = false;
		
		AudioService = new AudioService(context);
				
		DevMode = false;
		
		setFocusable(true);
		
		for (int i = 0; i < fingerIgnore.length; i++)
		{
			fingerIgnore[i] = false;
		}
		
	    //Temp
	    try {
	    	
	    	BitmapFactory.Options bitmapLoadingOptions = new BitmapFactory.Options();
	    	bitmapLoadingOptions.inPreferredConfig = Bitmap.Config.RGB_565;

			direction_Horizontal = BitmapFactory.decodeStream(assetMgr.open("Horizontal.bmp"), null, bitmapLoadingOptions);
			direction_Vertical = BitmapFactory.decodeStream(assetMgr.open("Vertical.bmp"), null, bitmapLoadingOptions);
			direction_Diagonal_1 = BitmapFactory.decodeStream(assetMgr.open("Diagonal_1.bmp"), null, bitmapLoadingOptions);
			direction_Diagonal_2 = BitmapFactory.decodeStream(assetMgr.open("Diagonal_2.bmp"), null, bitmapLoadingOptions);
			
			fireBullet = BitmapFactory.decodeStream(assetMgr.open("fireBullet.png"));
			
			WallPiece1 = BitmapFactory.decodeStream(assetMgr.open("Wall_1.png"));
			WallPiece2 = BitmapFactory.decodeStream(assetMgr.open("Wall_2.png"));
			WallPiece3 = BitmapFactory.decodeStream(assetMgr.open("Wall_3.png"));
			WallPiece4 = BitmapFactory.decodeStream(assetMgr.open("Wall_4.png"));
			WallPiece5 = BitmapFactory.decodeStream(assetMgr.open("Wall_5.png"));
			WallPiece6 = BitmapFactory.decodeStream(assetMgr.open("Wall_6.png"));
			WallPiece7 = BitmapFactory.decodeStream(assetMgr.open("Wall_7.png"));
			WallPiece8 = BitmapFactory.decodeStream(assetMgr.open("Wall_8.png"));
			WallPiece9 = BitmapFactory.decodeStream(assetMgr.open("Wall_9.png"));
			WallPiece10 = BitmapFactory.decodeStream(assetMgr.open("Wall_10.png"));
			WallPiece11 = BitmapFactory.decodeStream(assetMgr.open("Wall_11.png"));
			WallPiece12 = BitmapFactory.decodeStream(assetMgr.open("Wall_12.png"));
			WallPiece13 = BitmapFactory.decodeStream(assetMgr.open("Wall_13.png"));
			WallPiece14 = BitmapFactory.decodeStream(assetMgr.open("Wall_14.png"));
			WallPiece15 = BitmapFactory.decodeStream(assetMgr.open("Wall_15.png"));
			WallPiece16 = BitmapFactory.decodeStream(assetMgr.open("Wall_16.png"));
			WallPiece17 = BitmapFactory.decodeStream(assetMgr.open("Wall_17.png"));
			

			Heal_1 = BitmapFactory.decodeStream(assetMgr.open("Heal_1.png"), null, bitmapLoadingOptions);
			Heal_2 = BitmapFactory.decodeStream(assetMgr.open("Heal_2.png"), null, bitmapLoadingOptions);
			Heal_3 = BitmapFactory.decodeStream(assetMgr.open("Heal_3.png"), null, bitmapLoadingOptions);
			
			Fire_1 = BitmapFactory.decodeStream(assetMgr.open("Fire_1.png"), null, bitmapLoadingOptions);
			Fire_2 = BitmapFactory.decodeStream(assetMgr.open("Fire_2.png"), null, bitmapLoadingOptions);
			Fire_3 = BitmapFactory.decodeStream(assetMgr.open("Fire_3.png"), null, bitmapLoadingOptions);
			
			Ice_1 = BitmapFactory.decodeStream(assetMgr.open("Ice_1.png"), null, bitmapLoadingOptions);
			Ice_2 = BitmapFactory.decodeStream(assetMgr.open("Ice_2.png"), null, bitmapLoadingOptions);
			Ice_3 = BitmapFactory.decodeStream(assetMgr.open("Ice_3.png"), null, bitmapLoadingOptions);
			
			control_overlay = BitmapFactory.decodeStream(assetMgr.open("control_overlay.png"));
			
			Tower_Heal = BitmapFactory.decodeStream(assetMgr.open("Tower_Heal.png"));
			Tower_Fire = BitmapFactory.decodeStream(assetMgr.open("Tower_Fire.png"));
			Tower_Ice = BitmapFactory.decodeStream(assetMgr.open("Tower_Ice.png"));
			Tower_AttackReady = BitmapFactory.decodeStream(assetMgr.open("Tower_AttackReady.png"));
			
			Enemy_1 = BitmapFactory.decodeStream(assetMgr.open("Enemy_1.bmp"), null, bitmapLoadingOptions);
			Enemy_2 = BitmapFactory.decodeStream(assetMgr.open("Enemy_2.bmp"), null, bitmapLoadingOptions);
			Enemy_3 = BitmapFactory.decodeStream(assetMgr.open("Enemy_3.bmp"), null, bitmapLoadingOptions);
			Enemy_4 = BitmapFactory.decodeStream(assetMgr.open("Enemy_4.bmp"), null, bitmapLoadingOptions);
			Enemy_5 = BitmapFactory.decodeStream(assetMgr.open("Enemy_5.bmp"), null, bitmapLoadingOptions);
			Enemy_6 = BitmapFactory.decodeStream(assetMgr.open("Enemy_6.bmp"), null, bitmapLoadingOptions);
			
//			Enemy_1 = BitmapFactory.decodeStream(assetMgr.open("Enemy_1.png"));
//			Enemy_2 = BitmapFactory.decodeStream(assetMgr.open("Enemy_2.png"));
//			Enemy_3 = BitmapFactory.decodeStream(assetMgr.open("Enemy_3.png"));
//			Enemy_4 = BitmapFactory.decodeStream(assetMgr.open("Enemy_4.png"));
//			Enemy_5 = BitmapFactory.decodeStream(assetMgr.open("Enemy_5.png"));
//			Enemy_6 = BitmapFactory.decodeStream(assetMgr.open("Enemy_6.png"));
						
			MainMenu = BitmapFactory.decodeStream(assetMgr.open("Main_Menu.png"));
			
			MenuBackground = BitmapFactory.decodeStream(assetMgr.open("Menu_Background.bmp"));
			
			intro = BitmapFactory.decodeStream(assetMgr.open("intro.png"));
			
			backButton = BitmapFactory.decodeStream(assetMgr.open("backButton.png"));
			menuFade = BitmapFactory.decodeStream(assetMgr.open("menuFade.png"));
			AboutText = BitmapFactory.decodeStream(assetMgr.open("aboutText.png"));
			QuestionBox = BitmapFactory.decodeStream(assetMgr.open("QuestionBox.png"));
			gameOverText = BitmapFactory.decodeStream(assetMgr.open("gameOverText.png"));
			pausedText = BitmapFactory.decodeStream(assetMgr.open("pausedText.png"));

			newgameButton = BitmapFactory.decodeStream(assetMgr.open("newgameButton.png"));
			resumeButton = BitmapFactory.decodeStream(assetMgr.open("resumeButton.png"));
			quitButton = BitmapFactory.decodeStream(assetMgr.open("quitButton.png"));
			backArrow = BitmapFactory.decodeStream(assetMgr.open("backArrow.png"));
			
			Instructions_1 = BitmapFactory.decodeStream(assetMgr.open("Options.png"));
			Instructions_2 = BitmapFactory.decodeStream(assetMgr.open("SoloPongAd.png"));
			Instructions_3 = BitmapFactory.decodeStream(assetMgr.open("MusicAdvertisement.png"));
//			Instructions_4 = BitmapFactory.decodeStream(assetMgr.open("Instructions_4.png"));
//			Instructions_5 = BitmapFactory.decodeStream(assetMgr.open("Instructions_5.png"));
//			Instructions_6 = BitmapFactory.decodeStream(assetMgr.open("Instructions_6.png"));
//			Instructions_7 = BitmapFactory.decodeStream(assetMgr.open("Instructions_7.png"));
//			Inst1 = Instructions_1;
//			Inst2 = Instructions_1;
//			Inst3 = Instructions_2;
			Inst1 = Instructions_1;
			Inst2 = Instructions_1;
			Inst3 = Instructions_2;
			
			hourglass = BitmapFactory.decodeStream(assetMgr.open("hourglass.png"));
			donate = BitmapFactory.decodeStream(assetMgr.open("donate.png"));
			donateText = BitmapFactory.decodeStream(assetMgr.open("donateText.png"));
			
			//Inst1 = BitmapFactory.decodeStream(assetMgr.open("Instructions_1.png"));
			//Inst2 = BitmapFactory.decodeStream(assetMgr.open("Instructions_1.png"));
			//Inst3 = BitmapFactory.decodeStream(assetMgr.open("Instructions_2.png"));
			instructionPosition = BitmapFactory.decodeStream(assetMgr.open("instructionPosition.png"));
			LargeNine = Typeface.createFromAsset(assetMgr, "fonts/largeNine.ttf");
			
			FireThumb = BitmapFactory.decodeStream(assetMgr.open("FireThumb.png"));
			HealThumb = BitmapFactory.decodeStream(assetMgr.open("HealThumb.png"));
			IceThumb = BitmapFactory.decodeStream(assetMgr.open("IceThumb.png"));
			
			joystick = BitmapFactory.decodeStream(assetMgr.open("joystick.png"));
			leftArrow = BitmapFactory.decodeStream(assetMgr.open("leftArrow.png"));
			rightArrow = BitmapFactory.decodeStream(assetMgr.open("rightArrow.png"));
			upArrow = BitmapFactory.decodeStream(assetMgr.open("upArrow.png"));
			downArrow = BitmapFactory.decodeStream(assetMgr.open("downArrow.png"));
			
			SlideBar = BitmapFactory.decodeStream(assetMgr.open("SlideBar.png"));
			JoystickStart = BitmapFactory.decodeStream(assetMgr.open("JoystickStart.png"));
			//HardMode = BitmapFactory.decodeStream(assetMgr.open("HardMode.png"));
			
			//enemyGlow = BitmapFactory.decodeStream(assetMgr.open("glow.png"));
		} catch (IOException e) { 
			
		}

	    SharedPreferences fileStore = context.getSharedPreferences("userData", 0);
	    
    	int score = fileStore.getInt("highscore", 0); //0 is the default value
    	
    	highscore = score;

    	slidingControlScheme = fileStore.getBoolean("controlScheme", true);
    	enableSound = fileStore.getBoolean("enableSound", true);
    	enableMusic = fileStore.getBoolean("enableMusic", true);
    	showTutorial = fileStore.getBoolean("showTutorial", true);
    	HardModeEnabled = fileStore.getBoolean("HardModeEnabled", false);
    	HardModeActive = false; //fileStore.getBoolean("HardModeActive", false);
    	EasyModeActive = false;
    	
	    thread.setRunning(true);
	    thread.start();
	    
	}
	
	public void onCreate()
	{
		gameContext.startService(new Intent(gameContext, BillingService.class));
		BillingHelper.setCompletedHandler(mTransactionHandler);
	}

	public static Handler mTransactionHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//Log.i(TAG, "Transaction complete");
			//Log.i(TAG, "Transaction status: "+BillingHelper.latestPurchase.purchaseState);
			//Log.i(TAG, "Item purchased is: "+BillingHelper.latestPurchase.productId);
			
			if(BillingHelper.latestPurchase.isPurchased()){
				//showItem();
				//TODO Show Confirmation of Donation
				//Log.d("Money Money", "Money Money Money");
				switchDonation();
			}
		};
	
};
	public static void switchDonation()
	{
		displayDonation = true;
	}

//	private ServiceConnection myConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName className,
//                IBinder service) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance
//            LocalBinder binder = (LocalBinder) service;
//            AudioService = binder.getService();
//            AudioServiceBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//        	AudioServiceBound = false;
//        }
//    };
	
	public void setScale(int DeviceWidth, int DeviceHeight)
	{
		this.deviceWidth = DeviceWidth;
		this.deviceHeight = DeviceHeight;
		if (Build.MANUFACTURER.toLowerCase() == "amazon" || Build.MODEL.toLowerCase() == "kindle fire")
		{
			deviceHeight-=20;
		}
		float widthA = (float)deviceWidth;
		float widthB = (float)DEFAULT_WIDTH;
		float heightA = (float)deviceHeight;
		float heightB = (float)DEFAULT_HEIGHT;
		this.scaleX = widthA/widthB;
		this.scaleY = heightA/heightB;
	}
	
	public float getScaleXValue()
	{
		return scaleX;
	}
	
	public float getScaleYValue() 
	{
		return scaleY;
	}	
	
	private void initMenu() {

		ButtonOne = new Rectangle( 572, 166, 283, 88); 
		ButtonThree = new Rectangle( 572, 286, 283, 88);
		ButtonTwo = new Rectangle( 572, 405, 283, 88);
		DialogOption = 0;
		ShowDialog = false;
	}
	
	private void initHelp() {
		
		DialogOption = 0;
		ShowDialog = false;
		skipButton = false;
		ButtonOne = new Rectangle( 25, screenHeight - 125, 100, 100);
//		try
//		{
//
//			AssetManager assetMgr = gameContext.getAssets();
//		Inst1 = BitmapFactory.decodeStream(assetMgr.open("Instructions_1.png"));
//		Inst2 = BitmapFactory.decodeStream(assetMgr.open("Instructions_1.png"));
//		Inst3 = BitmapFactory.decodeStream(assetMgr.open("Instructions_2.png"));
//		} catch (IOException e) { 
//			
//		}
		
		//Removed when switched from in-game instructions to basic tutorial with settings menu
//		Inst1 = Instructions_1;
//		Inst2 = Instructions_1;
//		Inst3 = Instructions_2;
		
		InstructionsCount = 3;
		InstructionsIndex = 0;
		
		InstructionsNextImage = "Instructions_Full_1.png";
	    InstructionsNextIndex = InstructionsIndex;
	    InstructionsNextReplace = 2;
	    InstructionsNextLoad = true;	
		
		InstructionsX = 0;
		InstructionsY = 0;
		instStartX = -10;
		instStartY = -10;
		instScrollDirection = 0;
		instTransitioning = false;
		InstructionsOldX = 0;
		InstructionsOldY = 0;
		srcRect = new Rect(0,0,1,1);
		destRect = new Rect(0,0,1,1);
		InstructionsDriftY = 0;
		InstructionsBumpScroll = 0;
		instructionsJump = 0;
		
	}
	
	private void initAbout() {
		ButtonOne = new Rectangle( 25, screenHeight - 118, 287, 93);
		ButtonTwo = new Rectangle( 437, 181, 457, 70);
		ButtonThree = new Rectangle( 357, 283, 314, 17);
		ShowDialog = false;
		DialogOption = 0;
	}
	
	private void initVariables() {
		Units.clear();
		Bullets.clear();
		SimpleBullets.clear();
		FadingText.clear();
		FadingBlocks.clear();
		FadingCircles.clear();
		TowerEffects.clear();
		Shockwaves.clear();
		
		fireSoundPlay = false;
		
		tutorialIndex = 0;
		
		for (int i = 0; i<fingerData.length; i++)
			for (int j = 0; j<fingerData[i].length; j++)
				fingerData[i][j] = 0;
		
		for (int i = 0; i<fingerRects.length; i++)
		{
			fingerRects[i] = new Rect();
			fingerRects[i].set((int) fingerData[i][0] - 20, (int)fingerData[i][1] - 20, (int)fingerData[i][0]+20, (int)fingerData[i][1]+20);
		}
		
		
		
		for (int i = 0; i < fingerCentralTower.length; i++)
		{
			fingerCentralTower[i] = false;
		}
		
		for (int i = 0; i < IceTowerActive.length; i++)
		{
			for (int j = 0; j < IceTowerActive[j].length; j++)
			{
				IceTowerActive[i][j] = false;
			}
		}
		
		spawnCounter = 0;
		initSpawnCounter = 0;
		initSpawnTime = 500;
		allowSpawn = true;
		Angle = 90;
		if (slidingControlScheme)
		{
			SliderMin = 25;
			SliderMax = screenWidth/2 - 50;//screenWidth/2 - 25;
			SliderRect = new Rectangle((int) (SliderMin + ((SliderMin+SliderMax)/2) - 50), (int) (screenHeight - 50), (int) (75),(int)(50));
		}
		else
		{
			SliderMin = 177;
			SliderMax = 277;
			SliderRect = new Rectangle(202, 490-50, 75, 75);
		}
		
		ShooterButtonRect = new Rectangle((int) (screenWidth - 100), (int) (screenHeight - 100), (int) (100), (int) (100));
		FireButtonRect = new Rectangle((int) (screenWidth - 365), (int) (screenHeight - 100), (int) (100), (int) (100));
		IceButtonRect = new Rectangle((int) (screenWidth - 250), (int) (screenHeight - 100), (int) (100), (int) (100));
		HealButtonRect = new Rectangle((int) (screenWidth - 480), (int) (screenHeight - 100), (int) (100), (int) (100));
		
		ButtonOne = new Rectangle( 25, screenHeight - 93 - 25, 287, 93);
		ButtonTwo = new Rectangle( screenWidth - 287 - 25, screenHeight - 93 - 25, 287, 93);
				
		CannonCenterX = DEFAULT_WIDTH/2;
		CannonCenterY = 30;
		Speed = 30;
		CannonEndX = CannonCenterX;
		CannonEndY = CannonCenterY + Speed;
		for (int i = 0; i < GameBoard.length; i++)
		{
			for (int j = 0; j < GameBoard[i].length; j++)
			{
				GameBoard[i][j] = 0;
			}
		}
		for (int i = 0; i < DirectionMap.length; i++)
		{
			for (int j = 0; j < DirectionMap[i].length; j++)
			{
				DirectionMap[i][j] = 0;
			}
		}
		
		JustStarted = true;
		GameBoard[14][0] = 2;//Set Turret Location
		GameBoard[14][1] = 2;
		GameBoard[15][0] = 2;
		GameBoard[15][1] = 2;
		DirectionMap = AStar(GameBoard);
		BaseDirectionMap = DirectionMap;
		UpdateTowerWallMap();
		CrossHairMag = 100;
		CrossHairDelta = 3;
		
		ShockwaveCost = 1000;
		
		newHighscore = true;
		
		for (int i = 0; i < fingerState.length; i++)
		{
			fingerState[i] = 0;
		}
		for (int i = 0; i < fingerRegion.length; i++)
		{
			fingerRegion[i] = 0;
		}
		for (int i = 0; i < TowerData.length; i++)
		{

			for (int j = 0; j < TowerData[i].length; j++)
			{
				for (int k = 0; k < TowerData[i][j].length; k++)
				{
					TowerData[i][j][k] = 0;
				}
			}
		}
		
		for (int i = 0; i < TowerTimer.length; i++)
		{

			for (int j = 0; j < TowerTimer[i].length; j++)
			{
				TowerTimer[i][j] = 0;
			}
		}
		
		for (int i = 0; i < TowerLevel.length; i++)
		{
			for (int j = 0; j < TowerLevel[i].length; j++)
			{
				TowerLevel[i][j] = 0;
			}
		}
		
		for (int i = 0; i < TowerWallMap.length; i++)
		{
			for (int j = 0; j < TowerWallMap[i].length; j++)
			{
				TowerWallMap[i][j] = 0;
			}
		}
		
		maxHealth = 200;
		health = maxHealth;
		
		if (!HardModeActive)
		{
			gold = 600;		
			experiencePoints = 0;
			maxLevel = 1;
		}
		else
		{
			gold = 5000;
			experiencePoints = 24000;
			maxLevel = (int) (((float)experiencePoints) / 1000);
			maxLevel += 1;
		}
		
		oldLevel = maxLevel;
		gameOver = false;
		
		towerPower = 0;		
	}

	public void ResetTowers()
	{
		HealButtonRect.liftFinger();
		HealButtonRect.setX((int)(screenWidth - 480));
		HealButtonRect.setY((int)(screenHeight - 100));
		HealButtonIn.setX((int)(screenWidth - 480));
		HealButtonIn.setY((int)(screenHeight - 100));
		HealButtonOut.setX((int)(screenWidth - 480));
		HealButtonOut.setY((int)(screenHeight - 100));		
		
		IceButtonRect.liftFinger();
		IceButtonRect.setX((int)(screenWidth - 250));
		IceButtonRect.setY((int)(screenHeight - 100));
		IceButtonIn.setX((int)(screenWidth - 250));
		IceButtonIn.setY((int)(screenHeight - 100));
		IceButtonOut.setX((int)(screenWidth - 250));
		IceButtonOut.setY((int)(screenHeight - 100));

		FireButtonRect.liftFinger();
		FireButtonRect.setX((int)(screenWidth - 365));
		FireButtonRect.setY((int)(screenHeight - 100));
		FireButtonIn.setX((int)(screenWidth - 365));
		FireButtonIn.setY((int)(screenHeight - 100));
		FireButtonOut.setX((int)(screenWidth - 365));
		FireButtonOut.setY((int)(screenHeight - 100));
		
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//continue the thread
	    synchronized (thread) {
	        thread.pleaseWait = false;
	        thread.notifyAll();
	    }
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//pause the thread
	    synchronized (thread) {
	        thread.pleaseWait = true;
	    }
	    
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
        int pointerId = event.getPointerId(pointerIndex);
        //Fix for newer phones
        if (pointerId < 4)
        {
        	switch (action) {
        	case MotionEvent.ACTION_DOWN:
        	case MotionEvent.ACTION_POINTER_DOWN:
	        	//Log.d("pointer id - down",Integer.toString(pointerId));
	        	fingerData[pointerId][0] = event.getX(pointerIndex)/scaleX;
	        	fingerData[pointerId][1] = event.getY(pointerIndex)/scaleY;
	        	fingerData[pointerId][2] = 1;
	            break;
	
	        case MotionEvent.ACTION_UP:          
	        case MotionEvent.ACTION_POINTER_UP:
	        case MotionEvent.ACTION_CANCEL:
	        	//Log.d("pointer id - cancel",Integer.toString(pointerId));
	        	fingerData[pointerId][0] = event.getX(pointerIndex)/scaleX;
	        	fingerData[pointerId][1] = event.getY(pointerIndex)/scaleY;
	        	fingerData[pointerId][2] = 0;
	        	fingerIgnore[pointerId] = false;
	            break;
	
	        case MotionEvent.ACTION_MOVE:
	        	
	        	int pointerCount = event.getPointerCount();
	        	for(int i = 0; i < pointerCount; ++i)
	        	{
	        		pointerIndex = i;
	        		pointerId = event.getPointerId(pointerIndex);
	        		//Log.d("pointer id - move",Integer.toString(pointerId));
	        		if (pointerId < 4)
	        		{
	        			fingerData[pointerId][0] = event.getX(pointerIndex)/scaleX;
	        			fingerData[pointerId][1] = event.getY(pointerIndex)/scaleY;
	        			fingerData[pointerId][2] = 1;
	        		}
	        	}
	            break;
        	}
        }
		return true;
	}

	public void backHit()
	{
		//System.exit(0);
		if (gameState == 0)
		{
			if (ShowDialog && DialogOption == 2)
			{
				ShowDialog = false;
				DialogOption = 0;
			}
			else
			{
				SharedPreferences fileStore = this.getContext().getSharedPreferences("userData", 0);
				SharedPreferences.Editor editor = fileStore.edit();
				editor.putInt("highscore", highscore);
		    	editor.putBoolean("controlScheme", slidingControlScheme);
		    	editor.putBoolean("enableSound", enableSound);
		    	editor.putBoolean("enableMusic", enableMusic);
		    	editor.putBoolean("showTutorial", showTutorial);
		    	editor.putBoolean("HardModeEnabled", HardModeEnabled);
		    	//editor.putBoolean("HardModeActive", HardModeActive);
				editor.commit();
				System.exit(0);
			}
		}
		else if (gameState == 1)
		{
			if (pauseGame)
			{
				gameState = 0;
				initMenu();
				//AudioService.StopMusic();
			}
			else
			{
				justPause = true;
				pauseGame = true;
				
				if (fireSoundPlay && enableSound)
				{
					fireSoundPlay = false;
					AudioService.StopSound(SoundEffect.Fire_Active);
					Log.d("Fire Sound","Stop");
				}
			}
		}
		else if (gameState == 2)
		{
			if (InstructionsIndex != 0)
			{
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Menu_Select);
				Inst1 = Instructions_1;
				Inst2 = Instructions_1;
				Inst3 = Instructions_2;
									
				InstructionsY = 0;
				InstructionsIndex = 0;
				
				InstructionsNextImage = "Instructions_Full_1.png";
			    InstructionsNextIndex = InstructionsIndex;
			    InstructionsNextReplace = 2;
			    InstructionsNextLoad = true;	
			}
			else
			{
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Menu_Select);
				pauseGame = false;
				initMenu();
				gameState = 0;
				gameOver = false;
			}
		}
		else if (gameState == 3)
		{
			gameState = 0;
			initMenu();
		}
		else if (gameState == 4)
		{
			gameState = 3;
			initAbout();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{	
		if (gameState == -1)
		{
			paint.setColor(Color.BLACK);
			canvas.drawPaint(paint);
			if (IntroTimer < 500)
				paint.setAlpha((int)(((IntroTimer-1500)/500)*255));
			else if (IntroTimer > 1500)
				paint.setAlpha((int)(255-((IntroTimer-1500)/500)*255));
			else
			{
				paint.setAlpha(255);
			}
			canvas.drawBitmap(intro, DEFAULT_WIDTH/2 - intro.getWidth()/2, DEFAULT_HEIGHT/2 - intro.getHeight()/2, paint);
		}
		else if (gameState == 0)
		{
//			paint.setColor(Color.BLACK);
//			canvas.drawPaint(paint);
//			paint.setColor(Color.RED);
//			paint.setTextSize(100);
//			canvas.drawRect(ButtonOne.getRect(), paint);
//			canvas.drawRect(ButtonTwo.getRect(), paint);
//			canvas.drawRect(ButtonThree.getRect(), paint);
//			canvas.drawText("Storm the Castle",50,100,paint);
//			
//			paint.setColor(Color.WHITE);
//			paint.setTextSize(50);
//			canvas.drawText("Quit", ButtonTwo.getX()+25, ButtonTwo.getY()+75, paint);
//			
//			canvas.drawText("New Game", ButtonOne.getX()+25, ButtonOne.getY()+75, paint);
//			canvas.drawText("Help", ButtonThree.getX()+25, ButtonThree.getY()+75, paint);
			
			paint.setARGB(255, 69, 69, 69);
			canvas.drawPaint(paint);
			canvas.drawBitmap(MenuBackground, 0, 0, paint);
			if (DialogOption != 2)
			{
				canvas.drawBitmap(MainMenu, -1, -1, paint);
				
				if (ShowDialog)
				{
					paint.setColor(Color.WHITE);
					paint.setTypeface(Typeface.DEFAULT);
					canvas.drawBitmap(QuestionBox, 180, 170, paint);
					canvas.drawBitmap(QuestionBox, 180, 170, paint);
					paint.setTextSize(30);
					if (DialogOption == 1)
					{
						canvas.drawText("Open website NathanTornquist.com?", 180+30, 170+70, paint);
					}
					canvas.drawText("Yes", 277, 332, paint);
					canvas.drawText("No", 627, 332, paint);
				}
			}
			else
			{
				canvas.drawBitmap(backButton, 25, screenHeight - 118, paint);
				paint.setTypeface(LargeNine);
				paint.setColor(Color.WHITE);
				paint.setTextSize(82);
				canvas.drawText("Difficulty:", 62, 102, paint);
				paint.setTextSize(50);
				canvas.drawText("Easy", 63, 280, paint);
				canvas.drawText("Normal", 349, 280, paint);
				
//				paint.setStrokeWidth(2);
//				paint.setStyle(Style.STROKE);
//				canvas.drawRect(960-50, 270-50, 960+50, 270+50, paint);
//				canvas.drawCircle(960, 270, 200, paint);
//				paint.setStyle(Style.FILL_AND_STROKE);
//				paint.setStrokeWidth(1);
				
				if (!HardModeEnabled)
					paint.setAlpha(125);
				canvas.drawText("Hard", 702, 280, paint);
			}
		}		
		else if (gameState == 2)
		{
			paint.setARGB(255, 69, 69, 69);
			canvas.drawPaint(paint);
			canvas.drawBitmap(MenuBackground, 0, 0, paint);
			
			if (InstructionsX > 0)
			{
				srcRect.set(0,(int)(-InstructionsY),(int)(960-InstructionsX),(int)(540-InstructionsY));
				destRect.set((int)InstructionsX,0,960,540);
				//canvas.drawBitmap(Inst2, InstructionsX, InstructionsY, paint);
				canvas.drawBitmap(Inst2, srcRect, destRect, paint);
			}
			else if (InstructionsX < 0)
			{
				srcRect.set((int)-InstructionsX,(int)(-InstructionsY),960,(int)(540-InstructionsY));
				destRect.set(0,0,(int)(960+InstructionsX),540);
				//canvas.drawBitmap(Inst2, InstructionsX, InstructionsY, paint);
				canvas.drawBitmap(Inst2, srcRect, destRect, paint);
			}
			else
			{
				srcRect.set(0,(int)(-InstructionsY),960,(int)(540-InstructionsY));
				destRect.set(0,0,960,540);
				//canvas.drawBitmap(Inst2, InstructionsX, InstructionsY, paint);
				canvas.drawBitmap(Inst2, srcRect, destRect, paint);
			}
			
			if (instTransitioning)
			{				
				if (InstructionsX > 0)
				{
					srcRect.set((int)(960-InstructionsX),(int)(-InstructionsOldY),960,(int)(540-InstructionsOldY));
					destRect.set(0,0,(int)InstructionsX,540);
					canvas.drawBitmap(Inst1, srcRect, destRect, paint);
					//canvas.drawBitmap(Inst1, InstructionsX-960, InstructionsOldY, paint);
				}
				if (InstructionsX < 0)
				{
					srcRect.set(0,(int)(-InstructionsOldY),(int)-InstructionsX,(int)(540-InstructionsOldY));
					destRect.set((int)(960+InstructionsX),0,960,540);

					canvas.drawBitmap(Inst3, srcRect, destRect, paint);
					//canvas.drawBitmap(Inst3, InstructionsX+960, InstructionsOldY, paint);
				}
			}
			else
			{
				if (InstructionsX > 0)
				{
					srcRect.set((int)(960-InstructionsX),0,960,540);
					destRect.set(0,0,(int)InstructionsX,540);
					canvas.drawBitmap(Inst1, srcRect, destRect, paint);
					//canvas.drawBitmap(Inst1, InstructionsX-960, 0, paint);
				}
				if (InstructionsX < 0)
				{
					srcRect.set(0,0,(int)-InstructionsX,540);
					destRect.set((int)(960+InstructionsX),0,960,540);

					canvas.drawBitmap(Inst3, srcRect, destRect, paint);
					//canvas.drawBitmap(Inst3, InstructionsX+960, 0, paint);
				}
			}
			
			if (InstructionsIndex == 0)
			{
				paint.setARGB(255, 0, 128, 192);
				if (enableMusic)
				{
					canvas.drawRect(136+InstructionsX, 297, 158+InstructionsX, 319, paint);
				}
				if (enableSound)
				{
					canvas.drawRect(136+InstructionsX, 338, 158+InstructionsX, 360, paint);
				}
				
				if (slidingControlScheme)
				{
					canvas.drawRect(136+InstructionsX, 174, 158+InstructionsX, 196, paint);
				}
				else
				{
					canvas.drawRect(136+InstructionsX, 215, 158+InstructionsX, 237, paint);
				}
				
				if (showTutorial)
				{
					canvas.drawRect(484+InstructionsX, 174, 506+InstructionsX, 196, paint);
				}
				
//				if (HardModeEnabled)
//				{
//					canvas.drawBitmap(HardMode, 481+InstructionsX, 212, paint);
//					if (HardModeActive)
//					{
//						canvas.drawRect(484+InstructionsX, 215, 506+InstructionsX, 237, paint);
//					}
//				}
			}
			else if (InstructionsIndex == 1)
			{
				paint.setARGB(255, 0, 128, 192);
				if (showTutorial)
				{
					canvas.drawRect(484+InstructionsX-960, 174, 506+InstructionsX-960, 196, paint);
				}
				
//				if (HardModeEnabled)
//				{
//					canvas.drawBitmap(HardMode, 481+InstructionsX-960, 212, paint);
//					if (HardModeActive)
//					{
//						canvas.drawRect(484+InstructionsX-960, 215, 506+InstructionsX-960, 237, paint);
//					}
//				}
			}
			
			
			paint.setColor(Color.RED);
			paint.setTextSize(100);
			//canvas.drawRect(ButtonOne.getRect(), paint);
			//canvas.drawText("Instructions:",50,100,paint);
						
			
			//paint.setColor(Color.WHITE);
			//paint.setTextSize(50);
			//canvas.drawText("Back", ButtonOne.getX()+25, ButtonOne.getY()+75, paint);
			//canvas.drawBitmap(menuFade, 0, DEFAULT_HEIGHT-117, paint);
			canvas.drawBitmap(backArrow, ButtonOne.getX(), ButtonOne.getY(), paint);
			
			canvas.drawBitmap(instructionPosition, 378+60, 508, paint);
			paint.setARGB(255, 255, 255, 255);
			canvas.drawCircle(390+(InstructionsIndex)*30+60, 520, 10, paint);
			
//			if ((InstructionsIndex == 0 || InstructionsIndex == 2 || InstructionsIndex == 3 || InstructionsIndex == 4 || InstructionsIndex == 5) && Inst2.getHeight() == 540)
//			{
//				//paint.setTextSize(20);
//				//paint.setColor(Color.WHITE);
//				//canvas.drawText("\u231B", DEFAULT_WIDTH - 40, 20, paint);
//				canvas.drawBitmap(hourglass, DEFAULT_WIDTH-50, 10, paint);
//			}
			
			if (ShowDialog)
			{
				paint.setTextSize(30);
				paint.setColor(Color.WHITE);
				paint.setTypeface(Typeface.DEFAULT);
				canvas.drawBitmap(QuestionBox, 180, 170, paint);
				canvas.drawBitmap(QuestionBox, 180, 170, paint);
				if (DialogOption == 1)
				{
					canvas.drawText("Reset high score? (Cannot be undone)", 180+30, 170+70, paint);
				}
				else if (DialogOption == 2)
				{
					canvas.drawText("Open website MichaelBetzMusic.com?", 180+30, 170+70, paint);
				}
				canvas.drawText("Yes", 277, 332, paint);
				canvas.drawText("No", 627, 332, paint);
			}
		}		
		else if (gameState == 3)
		{
			//paint.setColor(Color.BLACK);
			//canvas.drawPaint(paint);
			//paint.setColor(Color.RED);
			//paint.setTextSize(100);
			//canvas.drawRect(ButtonOne.getRect(), paint);
			//canvas.drawText("About:",50,100,paint);
			
			//paint.setColor(Color.WHITE);
			//paint.setTextSize(50);
			//canvas.drawText("Back", ButtonOne.getX()+25, ButtonOne.getY()+75, paint);
			paint.setARGB(255, 69, 69, 69);
			canvas.drawPaint(paint);
			canvas.drawBitmap(MenuBackground, 0, 0, paint);
			canvas.drawBitmap(AboutText,0,0,paint);
			canvas.drawBitmap(backButton, ButtonOne.getX(), ButtonOne.getY(), paint);
			
			paint.setTypeface(LargeNine);
			paint.setTextSize(30);
			paint.setColor(Color.WHITE);
			paint.setAlpha(200);
			canvas.drawText("Created By: Nathan Tornquist", 100, 200, paint);
			canvas.drawText("of Petronic Arts", 440, 250, paint);
			canvas.drawText("Audio By: Michael Betz", 100, 300, paint);
			canvas.drawText("Version: 1.2", 100, 350, paint);
				
			canvas.drawBitmap(donate, DEFAULT_WIDTH - donate.getWidth(), 0, paint);
			
			if (DevMode)
			{
				paint.setColor(Color.YELLOW);
			}
			
			canvas.drawText("Dev Mode", 860, 520, paint);
			
			if (ShowDialog)
			{
				paint.setColor(Color.WHITE);
				paint.setTypeface(Typeface.DEFAULT);
				canvas.drawBitmap(QuestionBox, 180, 170, paint);
				canvas.drawBitmap(QuestionBox, 180, 170, paint);
				if (DialogOption == 1)
				{
					canvas.drawText("Open website NathanTornquist.com?", 180+30, 170+70, paint);
				}
				else if (DialogOption == 2)
				{
					canvas.drawText("Open website MichaelBetzMusic.com?", 180+30, 170+70, paint);
				}
				canvas.drawText("Yes", 277, 332, paint);
				canvas.drawText("No", 627, 332, paint);
			}
		}
		else if (gameState == 4)
		{
			paint.setARGB(255, 69, 69, 69);
			canvas.drawPaint(paint);
			canvas.drawBitmap(MenuBackground, 0, 0, paint);
			canvas.drawBitmap(donateText,0,0,paint);
			canvas.drawBitmap(backButton, ButtonOne.getX(), ButtonOne.getY(), paint);
			
			paint.setTypeface(LargeNine);
			paint.setTextSize(30);
			paint.setColor(Color.WHITE);
			paint.setAlpha(200);
			//paint.setAlpha(100);
			canvas.drawText("Support the Developer:", 100, 200, paint);
			canvas.drawText("$ 1", 200, 250, paint);
			canvas.drawText("$ 5", 200, 300, paint);	
			canvas.drawText("$ 10", 200, 350, paint);	
			canvas.drawText("$ 15", 500, 250, paint);	
			canvas.drawText("$ 25", 500, 300, paint);	
			canvas.drawText("$ 50", 500, 350, paint);	
			if (displayDonation)
				canvas.drawText("Thank you for your support!", 100, 400, paint);
			//paint.setColor(Color.RED);
			//paint.setTextSize(50);
			//canvas.drawText("Disabled for Beta", 60, 275, paint);
			
			
		}
		else if (gameState == 1) //Play State
		{
			//Debug.startMethodTracing("myapp_graphics");
			//Reset Canvas
						
			//paint.setColor(Color.WHITE);
			//if (JustStarted)
			//{
			paint.setARGB(255, 69, 69, 69);
			//canvas.drawPaint(paint);
			canvas.drawRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT-100, paint);
				//JustStarted = false;
			//}
					
			//Draw Blocks
			for (int i = 0; i < DirectionMap.length; i++)
			{
				for (int j = 0; j < DirectionMap[i].length; j++)
				{
					
					if (DirectionMap[i][j] == 0)
					{
						continue;
					}
					else
					{
						if (DirectionMap[i][j] == 9 && TowerWallMap[i][j] != 0)
						{
							paint.setARGB(255, 208, 162, 17);
							canvas.drawRect(i*32,j*32,(i+1)*32,(j+1)*32, paint);
						}
						else
						{
							paint.setARGB(255,69,69,69);
							canvas.drawRect(i*32,j*32,(i+1)*32,(j+1)*32, paint);
							int r, g, b;
							r = (int) (34+35*((float)CostArray[i][j]/350.0));
							g = (int) (98-29*((float)CostArray[i][j]/350.0));
							b = (int) (130-61*((float)CostArray[i][j]/350.0));
							if (r>69 || g<69 || b<69)
							{
								r = g = b = 69;
							}
							paint.setARGB(255,r, g, b);
							canvas.drawRect(i*32+1,j*32+1,(i+1)*32-1,(j+1)*32-1, paint);
						}
						
						
						if (DirectionMap[i][j] == 9)
						{			
							
							int red = (int)((32-194)*(TowerData[i][j][0]/255)+194);
							int green = (int)((194-0)*(TowerData[i][j][0]/255)+0);
							int blue = (int)((0-32)*(TowerData[i][j][0]/255)+32);
							paint.setARGB(255, red, green, blue);
														
							if (TowerWallMap[i][j] != 0)
							{
								
								/*
								if (BaseDirectionMap[i][j] == 1)
									canvas.drawBitmap(direction_Vertical, i*32, j*32, paint);
								else if (BaseDirectionMap[i][j] == 2)
									canvas.drawBitmap(direction_Diagonal_2, i*32, j*32, paint);
								else if (BaseDirectionMap[i][j] == 3)
									canvas.drawBitmap(direction_Horizontal, i*32, j*32, paint);
								else if (BaseDirectionMap[i][j] == 4)
									canvas.drawBitmap(direction_Diagonal_1, i*32, j*32, paint);
								else if (BaseDirectionMap[i][j] == 5)
									canvas.drawBitmap(direction_Vertical, i*32, j*32, paint);
								else if (BaseDirectionMap[i][j] == 6)
									canvas.drawBitmap(direction_Diagonal_2, i*32, j*32, paint);
								else if (BaseDirectionMap[i][j] == 7)
									canvas.drawBitmap(direction_Horizontal, i*32, j*32, paint);
								else if (BaseDirectionMap[i][j] == 8)
									canvas.drawBitmap(direction_Diagonal_1, i*32, j*32, paint);
								
								*/
								if (TowerWallMap[i][j] == 1)
								{
									canvas.drawRect(i*32+8, j*32+0, i*32+23, j*32+32, paint);
									canvas.drawBitmap(WallPiece1, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 2)
								{
									canvas.drawRect(i*32+0, j*32+8, i*32+32, j*32+23, paint);									
									canvas.drawBitmap(WallPiece2, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 3)
								{
									canvas.drawRect(i*32+0, j*32+8, i*32+23, j*32+23, paint);
									canvas.drawBitmap(WallPiece3, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 4)
								{
									canvas.drawRect(i*32+8, j*32+0, i*32+23, j*32+23, paint);
									canvas.drawBitmap(WallPiece4, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 5)
								{
									canvas.drawRect(i*32+8, j*32+8, i*32+32, j*32+23, paint);
									
									canvas.drawBitmap(WallPiece5, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 6)
								{
									canvas.drawRect(i*32+8, j*32+8, i*32+23, j*32+32, paint);
									
									canvas.drawBitmap(WallPiece6, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 7)
								{
									canvas.drawRect(i*32+0, j*32+8, i*32+23, j*32+23, paint);
									canvas.drawRect(i*32+8, j*32+8, i*32+23, j*32+32, paint);
									
									canvas.drawBitmap(WallPiece7, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 8)
								{
									canvas.drawRect(i*32+0, j*32+8, i*32+23, j*32+23, paint);
									canvas.drawRect(i*32+8, j*32+0, i*32+23, j*32+23, paint);
									
									canvas.drawBitmap(WallPiece8, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 9)
								{
									canvas.drawRect(i*32+8, j*32+0, i*32+23, j*32+23, paint);
									canvas.drawRect(i*32+8, j*32+8, i*32+32, j*32+23, paint);
									
									canvas.drawBitmap(WallPiece9, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 10)
								{
									canvas.drawRect(i*32+8, j*32+8, i*32+32, j*32+23, paint);
									canvas.drawRect(i*32+8, j*32+8, i*32+23, j*32+32, paint);
									
									canvas.drawBitmap(WallPiece10, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 11)
								{
									canvas.drawRect(i*32+8, j*32+0, i*32+23, j*32+32, paint);
									canvas.drawRect(i*32+8, j*32+8, i*32+32, j*32+23, paint);
									
									canvas.drawBitmap(WallPiece11, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 12)
								{
									canvas.drawRect(i*32+0, j*32+8, i*32+32, j*32+23, paint);
									canvas.drawRect(i*32+8, j*32+8, i*32+23, j*32+32, paint);
									
									canvas.drawBitmap(WallPiece12, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 13)
								{
									canvas.drawRect(i*32+8, j*32+0, i*32+23, j*32+32, paint);
									canvas.drawRect(i*32+0, j*32+8, i*32+23, j*32+23, paint);
									
									canvas.drawBitmap(WallPiece13, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 14)
								{
									canvas.drawRect(i*32+0, j*32+8, i*32+32, j*32+23, paint);
									canvas.drawRect(i*32+8, j*32+0, i*32+23, j*32+23, paint);
									
									canvas.drawBitmap(WallPiece14, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 15)
								{
									canvas.drawRect(i*32+8, j*32+0, i*32+23, j*32+32, paint);
									canvas.drawRect(i*32+0, j*32+8, i*32+32, j*32+23, paint);
									
									canvas.drawBitmap(WallPiece15, i*32, j*32, paint);
									continue;
								}
								else if (TowerWallMap[i][j] == 16)
								{
									//Tower
									if (TowerData[i][j][2] == 1)
									{
										//canvas.drawRect(i*32+12, j*32+5, i*32+19, j*32+26, paint);
										//canvas.drawRect(i*32+5, j*32+12, (i)*32+26, (j)*32+19, paint);
										if (TowerLevel[i][j] == 1)
										{
											canvas.drawBitmap(Fire_1, i*32, j*32, paint);
											
										}
										else if (TowerLevel[i][j] == 2)
										{
											canvas.drawBitmap(Fire_2, i*32, j*32, paint);
										}
										else if (TowerLevel[i][j] == 3)
										{
											canvas.drawBitmap(Fire_3, i*32, j*32, paint);
										}
										
									}
									else if (TowerData[i][j][2] == 2)
									{
										//canvas.drawRect(i*32+12, j*32+5, i*32+19, j*32+26, paint);
										//canvas.drawRect(i*32+5, j*32+12, (i)*32+26, (j)*32+19, paint);
										if (TowerLevel[i][j] == 1)
										{
											canvas.drawBitmap(Ice_1, i*32, j*32, paint);
										}
										else if (TowerLevel[i][j] == 2)
										{
											canvas.drawBitmap(Ice_2, i*32, j*32, paint);
										}
										else if (TowerLevel[i][j] == 3)
										{
											canvas.drawBitmap(Ice_3, i*32, j*32, paint);
										}
										
									}
									else if (TowerData[i][j][2] == 3)
									{
										//canvas.drawRect(i*32+12, j*32+5, i*32+19, j*32+26, paint);
										//canvas.drawRect(i*32+5, j*32+12, (i)*32+26, (j)*32+19, paint);
										if (TowerLevel[i][j] == 1)
										{
											canvas.drawBitmap(Heal_1, i*32, j*32, paint);
										}
										else if (TowerLevel[i][j] == 2)
										{
											canvas.drawBitmap(Heal_2, i*32, j*32, paint);
										}
										else if (TowerLevel[i][j] == 3)
										{
											canvas.drawBitmap(Heal_3, i*32, j*32, paint);
										}
										
									}
									//canvas.drawBitmap(WallPiece16, i*32, j*32, paint);
									canvas.drawRect(i*32+11, j*32+11, (i)*32+21, (j)*32+21, paint);
									continue;
									
								}
								else if (TowerWallMap[i][j] == 17)
								{
									canvas.drawRect(i*32+8, j*32+8, i*32+23, j*32+23, paint);									
									canvas.drawBitmap(WallPiece17, i*32, j*32, paint);
									continue;
								}
							}
							
						}
						else if (DirectionMap[i][j] == 10)
						{
							//paint.setColor(Color.GREEN);
							//canvas.drawRect(i*32, j*32, (i+1)*32, (j+1)*32, paint);
							//break;
							continue;
						}
						else if (DirectionMap[i][j] == 1)
						{
							//canvas.drawBitmap(direction_Vertical, i*32, j*32, paint);
							continue;
						}
						else if (DirectionMap[i][j] == 2)
						{
							
							//canvas.drawBitmap(direction_Diagonal_2, i*32, j*32, paint);
							continue;
						}
						else if (DirectionMap[i][j] == 3)
						{
							//canvas.drawBitmap(direction_Horizontal, i*32, j*32, paint);
							continue;
						}
						else if (DirectionMap[i][j] == 4)
						{
							//canvas.drawBitmap(direction_Diagonal_1, i*32, j*32, paint);
							continue;
						}
						else if (DirectionMap[i][j] == 5)
						{
							//canvas.drawBitmap(direction_Vertical, i*32, j*32, paint);
							continue;
						}
						else if (DirectionMap[i][j] == 6)
						{
							//canvas.drawBitmap(direction_Diagonal_2, i*32, j*32, paint);
							continue;
						}
						else if (DirectionMap[i][j] == 7)
						{
							//canvas.drawBitmap(direction_Horizontal, i*32, j*32, paint);
							continue;
						}
						else if (DirectionMap[i][j] == 8)
						{
							//canvas.drawBitmap(direction_Diagonal_1, i*32, j*32, paint);
							continue;
						}
						
					}
				}
			}
			
			for (int i = 0; i < TowerEffects.size(); i++)
			{
				TowerEffects.get(i).Draw(canvas, paint);
			}
					
					
			
			for (int i = 0; i < Shockwaves.size(); i++)
			{
				Shockwaves.get(i).Draw(canvas, paint);
			}
			
			for (int i = 0; i < FadingBlocks.size(); i++)
			{
				FadingBlocks.get(i).Draw(canvas, paint);
			}
			
			for (int i = 0; i < Units.size(); i++)
			{
				if (Units.get(i).stillAlive())
				{
					float x = Units.get(i).getX();
					float y = Units.get(i).getY();
					if (Units.get(i).getLevel() == 4)
					{
						//paint.setColor(Color.BLUE);
						paint.setARGB(255,0,194,162);
						paint.setAlpha(100);
						canvas.drawCircle(x+16, y+16, 50, paint);
					}
					else if (Units.get(i).getLevel() == 6 && Units.get(i).powerupReady())
					{
						//paint.setColor(Color.GREEN);

						paint.setARGB(255,32,194,0);
						paint.setAlpha(100);
						canvas.drawCircle(x + 16, y + 16, 50, paint);
					}
					
					//paint.setARGB(255, 69, 69, 69);
					//canvas.drawRect(x, y, x + 32, y + 32, paint);
					paint.setAlpha(255);
					//canvas.drawBitmap(enemyGlow, x-6, y-6, paint);
					switch (Units.get(i).getLevel()) {
					case 1:
						canvas.drawBitmap(Enemy_1, x, y, paint);
						//paint.setARGB(255, 61, 190, 255);
						break;
					case 2:
						canvas.drawBitmap(Enemy_2, x, y, paint);
						//paint.setARGB(255, 162, 0, 194);
						break;
					case 3:
						canvas.drawBitmap(Enemy_3, x, y, paint);
						//paint.setARGB(255, 0, 194, 162);
						break;
					case 4:
						canvas.drawBitmap(Enemy_4, x, y, paint);
						//paint.setARGB(255, 129, 194, 0);
						break;
					case 5:
						canvas.drawBitmap(Enemy_5, x, y, paint);
						//paint.setARGB(255, 255, 85, 0);
						break;
					case 6:
						canvas.drawBitmap(Enemy_6, x, y, paint);
						//paint.setARGB(255, 194, 65, 0);
						break;
					default:
						canvas.drawBitmap(Enemy_1, x, y, paint);
						//paint.setARGB(255, (int)(255 - 255*Units.get(i).healthPercent()), 0, (int)(255*Units.get(i).healthPercent()));
						break;
					}
					
					//canvas.drawRect(x+2, y+2, x + 30, y + 30, paint);

					//paint.setARGB(255, 69, 69, 69);
					//canvas.drawRect(x + 10, y + 10, x + 21, y + 21, paint);
						
					paint.setARGB(255, (int)(255 - 255*Units.get(i).healthPercent()), 0, (int)(255*Units.get(i).healthPercent()));
					canvas.drawRect(x+11, y+11, x + 20, y + 20, paint);
					
				}
				
			}
					
			for (int i = 0; i < FadingCircles.size(); i++)
			{
				FadingCircles.get(i).Draw(canvas, paint);
			}
			
			paint.setColor(Color.WHITE);
			paint.setTextSize(30);
			/*
			for (int i = 0; i < Units.size(); i++)
			{
				float x = Units.get(i).getX();
				float y = Units.get(i).getY();
				canvas.drawText(Integer.toString(Units.get(i).getLevel()), x + 2, y + 20, paint);
			}*/
						
			for (int i = 0; i < Bullets.size(); i++)
			{
				//SmallBullet.setX(Bullets.get(i).getX());
				//SmallBullet.setY(Bullets.get(i).getY());
				//SmallBullet.drawCenter(canvas, paint);
				
				
				
				
				//Bullets.get(i).draw(canvas, paint);
				
				paint.setARGB(255, 69, 69, 69);
				//paint.setARGB(255, 255, 194, 0);
				//paint.setStyle(Style.STROKE);
				canvas.drawCircle(Bullets.get(i).BulletImage.getX(), Bullets.get(i).BulletImage.getY(), Bullets.get(i).BulletImage.image.getWidth()/2, paint);
				//paint.setStyle(Style.FILL_AND_STROKE);
				
				paint.setARGB(255, 255, 194, 0);
				//paint.setARGB(255, 69, 69, 69);
				canvas.drawCircle(Bullets.get(i).BulletImage.getX(), Bullets.get(i).BulletImage.getY(), Bullets.get(i).BulletImage.image.getWidth()/2-4, paint);
			
			}
			
			
			paint.setAlpha(255);			
			Tower.draw(canvas, paint);
			if(towerPower == 1)
			{
				canvas.drawBitmap(Tower_Fire, Tower.getX() - Tower.getWidth()/2, Tower.getY() - Tower.getHeight()/2, paint);
			}
			else if(towerPower == 2)
			{
				canvas.drawBitmap(Tower_Ice, Tower.getX() - Tower.getWidth()/2, Tower.getY() - Tower.getHeight()/2, paint);
			}
			else if(towerPower == 3)
			{
				canvas.drawBitmap(Tower_Heal, Tower.getX() - Tower.getWidth()/2, Tower.getY() - Tower.getHeight()/2, paint);
			}
			
			if (gold >= ShockwaveCost)
			{
				canvas.drawBitmap(Tower_AttackReady, Tower.getX() - Tower.getWidth()/2, Tower.getY() - Tower.getHeight()/2, paint);
			}
			
			if (ShooterButtonRect.touched())
				CrossHairs.drawCenter(canvas, paint);
			
			paint.setARGB(150, 255, 242, 0);
			canvas.drawRect(0,0, DEFAULT_WIDTH*(experiencePoints-(maxLevel-1)*1000)/1000, 10, paint);
			
			
			//Draw Power up construction
			if (FireButtonRect.touched())
			{
				int x = (int)(fingerData[FireButtonRect.touchFinger()][0] / (32));
				int y = (int)(fingerData[FireButtonRect.touchFinger()][1] / (32));
				if (x < 30 && y < 14)
				{
					if ((x != 0) && (x != 29) && (y != 13))
					{
						if (GameBoard[x][y] != 2 && GameBoard[x][y] == 1 && (TowerData[x][y][2] != 1 || (TowerData[x][y][2] == 1 && TowerLevel[x][y] < 3)))
						{
							paint.setColor(Color.WHITE);
							paint.setStyle(Style.STROKE);
							canvas.drawCircle((float)((x+.5)*32), (float)((y+.5)*32), TowerData[x][y][1]*3/4, paint);
							paint.setStyle(Style.FILL_AND_STROKE);
							if (gold >= 200)
							{
								paint.setARGB(150, 0, 255, 0);
							}
							else
							{
								paint.setARGB(150, 255, 0, 0);
							}
						}
						else
						{
							paint.setARGB(150, 255, 0, 0);
						}
						
						if (gold < 200*(TowerLevel[x][y]+1) && TowerData[x][y][2] == 1)
						{
							paint.setARGB(150, 255, 0, 0);
						}
						
						
						
						if (GameBoard[x][y] == 2 && gold >= 1000 && towerPower != 1) // Tower Upgrade Selection
						{
							paint.setARGB(150, 255, 255, 0);
						}
						
						
						
						
						float drawX = x*32;	
						float drawY = y*32;
						canvas.drawRect(drawX, 0, drawX + 32, screenHeight - 100, paint);
						canvas.drawRect(0, drawY, screenWidth, drawY + 32, paint);
						
												
						
					}
					else
					{
						paint.setARGB(150, 255, 0, 0);
					}
				}
				else
				{
					paint.setARGB(150, 255, 0, 0);
					x = 0;
					y = 0;
				}
				
				int cost = 0;
				if (GameBoard[x][y] == 2 && towerPower != 1)
				{
					cost = 1000;
				}
				else if (GameBoard[x][y] == 1)
				{
					if (TowerData[x][y][2] == 1)
					{
						cost = 200*(TowerLevel[x][y]+1);
					}
					else
					{
						cost = 200;
					}
				}
				paint.setAlpha(255);
				canvas.drawBitmap(FireThumb, 16, 0, paint);
				
				
				if (cost != 0)
					canvas.drawText(Integer.toString(cost), 56, 26, paint);
				else
					canvas.drawText("---", 56, 26, paint);
			}
			
			if (IceButtonRect.touched())
			{
				int x = (int)(fingerData[IceButtonRect.touchFinger()][0] / (32));
				int y = (int)(fingerData[IceButtonRect.touchFinger()][1] / (32));
				if (x < 30 && y < 14)
				{
					if ((x != 0) && (x != 29) && (y != 13))
					{
						if (GameBoard[x][y] != 2 && GameBoard[x][y] == 1 && (TowerData[x][y][2] != 2 || (TowerData[x][y][2] == 2 && TowerLevel[x][y] < 3)))
						{
							paint.setColor(Color.WHITE);
							paint.setStyle(Style.STROKE);
							canvas.drawCircle((float)((x+.5)*32), (float)((y+.5)*32), TowerData[x][y][1]*1/2, paint);
							paint.setStyle(Style.FILL_AND_STROKE);
							
							if (gold >= 200)
							{
								paint.setARGB(150, 0, 255, 0);
							}
							else
							{
								paint.setARGB(150, 255, 0, 0);
							}
						}
						else
						{
							paint.setARGB(150, 255, 0, 0);
						}
						
						if (gold < 200*(TowerLevel[x][y]+1) && TowerData[x][y][2] == 2)
						{
							paint.setARGB(150, 255, 0, 0);
						}
						
						if (GameBoard[x][y] == 2 && gold >= 1000 && towerPower != 2) // Tower Upgrade Selection
						{
							paint.setARGB(150, 255, 255, 0);
						}
						
						float drawX = x*32;	
						float drawY = y*32;
						canvas.drawRect(drawX, 0, drawX + 32, screenHeight - 100, paint);
						canvas.drawRect(0, drawY, screenWidth, drawY + 32, paint);
						
						
					}
					else
					{
						paint.setARGB(150, 255, 0, 0);
					}
				}
				else
				{
					paint.setARGB(150, 255, 0, 0);
					x = 0;
					y = 0;
				}
				
				int cost = 0;
				if (GameBoard[x][y] == 2 && towerPower != 2)
				{
					cost = 1000;
				}
				else if (GameBoard[x][y] == 1)
				{
					if (TowerData[x][y][2] == 2)
					{
						cost = 200*(TowerLevel[x][y]+1);
					}
					else
					{
						cost = 200;
					}
				}
				
				
				if (cost != 0)
				{
					if (FireButtonRect.touched())
					{
						paint.setAlpha(255);
						canvas.drawBitmap(IceThumb, 141, 0, paint);
						canvas.drawText(Integer.toString(cost), 181, 26, paint);
					}
					else
					{
						paint.setAlpha(255);
						canvas.drawBitmap(IceThumb, 16, 0, paint);
						canvas.drawText(Integer.toString(cost), 56, 26, paint);
					}							
				}
				else
				{
					if (FireButtonRect.touched())
					{
						paint.setAlpha(255);
						canvas.drawBitmap(IceThumb, 141, 0, paint);
						canvas.drawText("---", 181, 26, paint);
					}
					else
					{
						paint.setAlpha(255);
						canvas.drawBitmap(IceThumb, 16, 0, paint);
						canvas.drawText("---", 56, 26, paint);
					}							
				}
			}
			
			if (HealButtonRect.touched())
			{
				int x = (int)(fingerData[HealButtonRect.touchFinger()][0] / (32));
				int y = (int)(fingerData[HealButtonRect.touchFinger()][1] / (32));
				if (x < 30 && y < 14)
				{
					if ((x != 0) && (x != 29) && (y != 13))
					{
						if (GameBoard[x][y] != 2 && GameBoard[x][y] == 1 && (TowerData[x][y][2] != 3 || (TowerData[x][y][2] == 3 && TowerLevel[x][y] < 3)))
						{
							//paint.setColor(Color.RED);
							//paint.setStyle(Style.STROKE);
							//canvas.drawCircle((float)((x+.5)*32), (float)((y+.5)*32), TowerData[x][y][1]*3/4, paint);
							//paint.setStyle(Style.FILL_AND_STROKE);
							paint.setColor(Color.WHITE);
							paint.setStyle(Style.STROKE);
							float temp = paint.getStrokeWidth();
							paint.setStrokeWidth(2);
							if (TowerLevel[x][y] == 0)
							{
								canvas.drawRect(x*32, y*32-32, x*32+32, y*32, paint);
								canvas.drawRect(x*32, y*32+32, x*32+32, y*32+64, paint);
								canvas.drawRect(x*32-32, y*32, x*32, y*32+32, paint);
								canvas.drawRect(x*32+32, y*32, x*32+64, y*32+32, paint);
							}
							else if (TowerLevel[x][y] == 1)
							{
								canvas.drawRect(x*32-32, y*32-32, x*32+64, y*32+64, paint);
								canvas.drawRect(x*32,y*32,x*32+32,y*32+32,paint);
							}
							else if (TowerLevel[x][y] == 2)
							{
								canvas.drawRect(x*32-32, y*32-32, x*32+64, y*32+64,paint);
							}
							paint.setStrokeWidth(temp);
							paint.setStyle(Style.FILL_AND_STROKE);
							if (gold >= 200)
							{
								paint.setARGB(150, 0, 255, 0);
							}
							else
							{
								paint.setARGB(150, 255, 0, 0);
							}
						}
						else
						{
							paint.setARGB(150, 255, 0, 0);
						}
											
						if (gold < 200*(TowerLevel[x][y]+1) && TowerData[x][y][2] == 3)
						{
							paint.setARGB(150, 255, 0, 0);
						}
						
						if (GameBoard[x][y] == 2 && gold >= 1000 && towerPower != 3) // Tower Upgrade Selection
						{
							paint.setARGB(150, 255, 255, 0);
						}
						
						float drawX = x*32;	
						float drawY = y*32;
						canvas.drawRect(drawX, 0, drawX + 32, screenHeight - 100, paint);
						canvas.drawRect(0, drawY, screenWidth, drawY + 32, paint);					
						
					}		
					else
					{
						paint.setARGB(150, 255, 0, 0);
					}
					
				}
				else
				{
					paint.setARGB(150, 255, 0, 0);
					x = 0;
					y = 0;
				}
				
				int cost = 0;
				if (GameBoard[x][y] == 2 && towerPower != 3)
				{
					cost = 1000;
				}
				else if (GameBoard[x][y] == 1)
				{
					if (TowerData[x][y][2] == 3)
					{
						cost = 200*(TowerLevel[x][y]+1);
					}
					else
					{
						cost = 200;
					}
				}
				
				
				if (cost != 0)
				{
					
					if (FireButtonRect.touched() && IceButtonRect.touched())
					{
						paint.setAlpha(255);
						canvas.drawBitmap(HealThumb, 266, 0, paint);
						canvas.drawText(Integer.toString(cost), 306, 26, paint);
					}
					else if ((FireButtonRect.touched() && !IceButtonRect.touched()) || (!FireButtonRect.touched() && IceButtonRect.touched()))
					{
						paint.setAlpha(255);
						canvas.drawBitmap(HealThumb, 141, 0, paint);
						canvas.drawText(Integer.toString(cost), 181, 26, paint);
					}
					else
					{
						paint.setAlpha(255);
						canvas.drawBitmap(HealThumb, 16, 0, paint);
						canvas.drawText(Integer.toString(cost), 56, 26, paint);
					}							
				}
				else
				{
					if (FireButtonRect.touched() && IceButtonRect.touched())
					{
						paint.setAlpha(255);
						canvas.drawBitmap(HealThumb, 266, 0, paint);
						canvas.drawText("---", 306, 26, paint);
					}
					else if ((FireButtonRect.touched() && !IceButtonRect.touched()) || (!FireButtonRect.touched() && IceButtonRect.touched()))
					{
						paint.setAlpha(255);
						canvas.drawBitmap(HealThumb, 141, 0, paint);
						canvas.drawText("---", 181, 26, paint);
					}
					else
					{
						paint.setAlpha(255);
						canvas.drawBitmap(HealThumb, 16, 0, paint);
						canvas.drawText("---", 56, 26, paint);
					}							
				}
			}
			
			//Draw Finger Cross Hair Things (So you know where you are drawing)
			if (!pauseGame && (!showTutorial || (showTutorial && tutorialIndex > 3)))
			{
				for (int i = 0; i < fingerRegion.length; i++)
				{
					if (fingerData[i][1] < screenHeight - 100) // Above control panel
					{
						if (fingerRegion[i] == 1)
						{
							if (fingerData[i][2] == 1)
							{
								//This means it is in the top, is touching the screen, and started on top
								
								int x = (int)(fingerData[i][0] / (32));
								int y = (int)(fingerData[i][1] / (32));
								if (showTutorial && y < 3)
									break;
								if (x < 30 && y < 14)
								{
									if ((x != 0) && (x != 29) && (y != 13))
									{
										if (TowerData[x][y][2] != 0)
										{
											paint.setStyle(Style.STROKE);
											if (TowerData[x][y][2] == 1)
											{
												paint.setColor(Color.WHITE);
												canvas.drawCircle((float)((x+.5)*32), (float)((y+.5)*32), TowerData[x][y][1]*3/4, paint);
											}
											else if (TowerData[x][y][2] == 2)
											{
												paint.setColor(Color.WHITE);
												canvas.drawCircle((float)((x+.5)*32), (float)((y+.5)*32), TowerData[x][y][1]*1/2, paint);
											}
											else if (TowerData[x][y][2] == 3)
											{
												paint.setColor(Color.WHITE);
												paint.setStyle(Style.STROKE);
												float temp = paint.getStrokeWidth();
												paint.setStrokeWidth(2);
												if (TowerLevel[x][y] == 1)
												{
													canvas.drawRect(x*32, y*32-32, x*32+32, y*32, paint);
													canvas.drawRect(x*32, y*32+32, x*32+32, y*32+64, paint);
													canvas.drawRect(x*32-32, y*32, x*32, y*32+32, paint);
													canvas.drawRect(x*32+32, y*32, x*32+64, y*32+32, paint);
												}
												else if (TowerLevel[x][y] == 2)
												{
													canvas.drawRect(x*32-32, y*32-32, x*32+64, y*32+64, paint);
													canvas.drawRect(x*32,y*32,x*32+32,y*32+32,paint);
												}
												else if (TowerLevel[x][y] == 3)
												{
													canvas.drawRect(x*32-32, y*32-32, x*32+64, y*32+64,paint);
												}
												paint.setStrokeWidth(temp);
												paint.setStyle(Style.FILL_AND_STROKE);
												//No sphere of influence.  Simply exists. 
												//paint.setARGB(255, 34, 177, 76);
												//canvas.drawCircle((float)((x+.5)*32), (float)((y+.5)*32), TowerData[x][y][1]*1/2, paint);
											}
											
											paint.setStyle(Style.FILL_AND_STROKE);
										}
										//Get rows and columns to draw in
										float drawX = x*32;	
										float drawY = y*32;
										paint.setARGB(150, (int)(255 - TowerData[x][y][0]), (int)TowerData[x][y][0], 0);
										//paint.setColor(Color.GRAY); //Will eventually reflect the resources dumped into a given piece of land
										//paint.setAlpha(150);
										canvas.drawRect(drawX, 0, drawX + 32, screenHeight - 100, paint);
										canvas.drawRect(0, drawY, screenWidth, drawY + 32, paint);
										
									}
									
								}
								
							}
						}
					}
				}
			}
			
			paint.setAlpha(255);
			
			//Tower.draw(canvas, paint);
			
			
			
			
			
			for (int x = 0; x < TowerData.length; x++)
			{
				for (int y = 0; y < TowerData[x].length; y++)
				{
					if (TowerData[x][y][2] == 1)
					{
						int lazers = TowerLevel[x][y];
						//Log.d("Laser Count: ", Integer.toString(lazers));
						for (int z = 0; z < Units.size(); z++)
						{					
							//Get Center of Tower
							float i = x*32+16;
							float j = y*32+16;
							//Get Center of Unit
							float k = Units.get(z).getX() + 16;
							float l = Units.get(z).getY() + 16;
							float dist = SquareDistance(i, j, k, l);
							if (dist < (TowerData[x][y][1]*3/4)*(TowerData[x][y][1]*3/4))
							{
								//Log.d("Within Distance! ", "Laser Count: "+Integer.toString(lazers));
								if (lazers > 0)
								{
									float temp = paint.getStrokeWidth();
									float percent = (TowerTimer[x][y] / (250*255/TowerData[x][y][1]));
									//Log.d("Percent: ",Float.toString(percent));
									//Log.d("TowerTimer: ",Float.toString(TowerTimer[x][y]));
									//Log.d("Other Number: ",Float.toString((250*255/TowerData[x][y][1])));
									paint.setStrokeWidth(3* percent);
									/*int alpha = (int) (255*(1-percent));
									alpha = (int) (alpha * 1.2);
									if (alpha >= 0 && alpha <= 255)
										paint.setAlpha(alpha);
									else if (alpha > 255)
										paint.setAlpha(255);
									else
										paint.setAlpha(0);*/
									paint.setColor(Color.RED);
									canvas.drawLine(i, j, k, l, paint);
									paint.setAlpha(255);
									paint.setStrokeWidth(temp);
									lazers--;
								}
							}
						}		
					}
					
					if (TowerData[x][y][2] == 2)
					{
						
						//Replaced with blue effects, loop is run above. 
						
						/*
						float[] c = getIceTowerCircle(x, y);
						float i = x*32+16;
						float j = y*32+16;
						paint.setColor(Color.RED);
						canvas.drawLine(i,j,c[0],c[1], paint);
						paint.setColor(Color.BLUE);
						canvas.drawCircle( c[0] , c[1], 8, paint);
						if (TowerLevel[x][y] == 2)
						{
							canvas.drawCircle(c[2] ,c[3], 8, paint);
							paint.setColor(Color.RED);
							canvas.drawLine(i,j,c[2],c[3], paint);
						}
						if (TowerLevel[x][y] == 3)
						{
							canvas.drawCircle(c[4] ,c[5], 8, paint);
							canvas.drawCircle(c[6] ,c[7], 8, paint);
							paint.setColor(Color.RED);
							canvas.drawLine(i,j,c[4],c[5], paint);
							canvas.drawLine(i,j,c[6],c[7], paint);
						}*/
						
						
					}
					
					if (TowerData[x][y][2] == 3)
					{
						//Tower Special Effects
						
					}
				}
			}
			
			for (int i = 0; i < SimpleBullets.size(); i++)
			{
				if (SimpleBullets.get(i).iceBullet())
					SimpleBullets.get(i).draw(canvas, paint);
			}
			
			paint.setARGB(150, (int)(255 - 255*health/maxHealth), (int)(255*health/maxHealth), 0);
			if (maxHealth <= 200)
			{
				canvas.drawRect(0, DEFAULT_HEIGHT-110, DEFAULT_WIDTH*health/maxHealth, DEFAULT_HEIGHT-100, paint);
			}
			else
			{
				if (health > 200)
				{
					canvas.drawRect(0, DEFAULT_HEIGHT-110, DEFAULT_WIDTH, DEFAULT_HEIGHT-100, paint);
					canvas.drawRect(0, DEFAULT_HEIGHT-120, DEFAULT_WIDTH*(health-200)/(maxHealth-200), DEFAULT_HEIGHT-110, paint);
				}
				else
				{
					canvas.drawRect(0, DEFAULT_HEIGHT-110, DEFAULT_WIDTH*health/(maxHealth-200), DEFAULT_HEIGHT-100, paint);
				}
			}
			
			paint.setAlpha(255);
			
			//canvas.drawText("Storm the Castle", 30, 30, paint);
			//canvas.drawText(String.valueOf(Angle) ,30,60, paint);
			ControlBackground.drawStraight(canvas, paint);
			if (slidingControlScheme)
				canvas.drawBitmap(SlideBar, 35, 513, paint);
			else
				canvas.drawBitmap(JoystickStart, 227, 465, paint);
			if (FireButtonRect.touched() && FireButtonThumbnail != null)
			{
				canvas.drawBitmap(FireButtonThumbnail, (int)(screenWidth - 365), (int)(screenHeight - 100), paint);
				canvas.drawBitmap(control_overlay, (int)(screenWidth - 365), (int)(screenHeight - 100), paint);
			}
			if (IceButtonRect.touched() && IceButtonThumbnail != null)
			{
				canvas.drawBitmap(IceButtonThumbnail, (int)(screenWidth - 250), (int)(screenHeight - 100), paint);
				canvas.drawBitmap(control_overlay, (int)(screenWidth - 250), (int)(screenHeight - 100), paint);
			}
			if (HealButtonRect.touched() && HealButtonThumbnail != null)
			{
				canvas.drawBitmap(HealButtonThumbnail, (int)(screenWidth - 480), (int)(screenHeight - 100), paint);
				canvas.drawBitmap(control_overlay, (int)(screenWidth - 480), (int)(screenHeight - 100), paint);
			}
			
			paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT);
			paint.setTextSize(25);
			canvas.drawText("Gold: " + Integer.toString((int)gold), 30, 477, paint );
			canvas.drawText("Level: " + Integer.toString((int)maxLevel), 325, 477, paint );
			//Log.d("Experience Points", Integer.toString(experiencePoints));
			if (slidingControlScheme)
				Slider.drawStraight(canvas, paint);
			else
				canvas.drawBitmap(joystick, SliderRect.getRect().left, SliderRect.getRect().top, paint);
				//canvas.drawRect(SliderRect.getRect().left, SliderRect.getRect().top, SliderRect.getRect().right, SliderRect.getRect().bottom, paint);
			//SliderRect.draw(canvas, paint);					
			
			if (ShooterButtonRect.touched())
			{
				ShootButtonIn.drawStraight(canvas, paint);
				
			}
			else
			{
				ShootButtonOut.drawStraight(canvas, paint);
			}
			
			//paint.setColor(Color.BLACK);
			//canvas.drawLine(CannonCenterX, CannonCenterY, CannonCenterX + CannonEndX, CannonCenterY + CannonEndY, paint);
					
			
		
			
			
			for (int i = 0; i < FadingText.size(); i++)
			{
				FadingText.get(i).Draw(canvas, paint);
			}
			
			if (FireButtonRect.touched())
			{
				int x = FireButtonRect.getX();
				int y = FireButtonRect.getY();
				int height = FireButtonRect.getHeight();
				int width = FireButtonRect.getWidth();
				
				x = (int) (x - width/4);
			    y = (int) (y - height/4);
			    
			    if (y < 0)
			    {
			        y = 0;
			    }
			    else if (screenHeight < height*1.5 + y)
			    {
			        y = (int) (screenHeight - height*1.5);
			    }
			    
			    if (x < 0)
			    {
			        x = 0;
			    }
			    else if (screenWidth < width*1.5 + x)
			    {
			        x = (int) (screenWidth - width*1.5);
			    }
				
				FireButtonThumbnail = Bitmap.createBitmap(screen, x, y, (int)(width*1.5), (int)(height*1.5));
				FireButtonThumbnail = Bitmap.createScaledBitmap(FireButtonThumbnail, width, height, false);
			}
			
			if (IceButtonRect.touched())
			{
			    int x = IceButtonRect.getX();
			    int y = IceButtonRect.getY();
			    int height = IceButtonRect.getHeight();
			    int width = IceButtonRect.getWidth();

			    x = (int) (x - width/4);
			    y = (int) (y - height/4);
			    
			    if (y < 0)
			    {
			        y = 0;
			    }
			    else if (screenHeight < height*1.5 + y)
			    {
			        y = (int) (screenHeight - height*1.5);
			    }
			    
			    if (x < 0)
			    {
			        x = 0;
			    }
			    else if (screenWidth < width*1.5 + x)
			    {
			        x = (int) (screenWidth - width*1.5);
			    }
			    
			    IceButtonThumbnail = Bitmap.createBitmap(screen, x, y, (int)(width*1.5), (int)(height*1.5));
			    IceButtonThumbnail = Bitmap.createScaledBitmap(IceButtonThumbnail, width, height, false);
			}
			
			if (HealButtonRect.touched())
			{
			    int x = HealButtonRect.getX();
			    int y = HealButtonRect.getY();
			    int height = HealButtonRect.getHeight();
			    int width = HealButtonRect.getWidth();
			    x = (int) (x - width/4);
			    y = (int) (y - height/4);
			    
			    if (y < 0)
			    {
			        y = 0;
			    }
			    else if (screenHeight < height*1.5 + y)
			    {
			        y = (int) (screenHeight - height*1.5);
			    }
			    
			    if (x < 0)
			    {
			        x = 0;
			    }
			    else if (screenWidth < width*1.5 + x)
			    {
			        x = (int) (screenWidth - width*1.5);
			    }
			    
			    HealButtonThumbnail = Bitmap.createBitmap(screen, x, y, (int)(width*1.5), (int)(height*1.5));
			    HealButtonThumbnail = Bitmap.createScaledBitmap(HealButtonThumbnail, width, height, false);
			}
			
			
			paint.setAlpha(255);
			
			if (FireButtonRect.touched())
			{
				FireButtonIn.drawStraight(canvas, paint);
			}
			else
			{
				FireButtonOut.drawStraight(canvas, paint);
			}
			
			if (IceButtonRect.touched())
			{
				IceButtonIn.drawStraight(canvas, paint);
			}
			else
			{
				IceButtonOut.drawStraight(canvas, paint);
			}
			
			if (HealButtonRect.touched())
			{
				HealButtonIn.drawStraight(canvas, paint);
			}
			else
			{
				HealButtonOut.drawStraight(canvas, paint);
			}
			
			if (experiencePoints > highscore && !showTutorial)
			{
				highscore = experiencePoints;
				if (newHighscore)
				{
					FadeText tempText = new FadeText("Highscore", 300, 320, 100, -100, 1500, Color.YELLOW, 100);
					FadingText.add(tempText);
					newHighscore = false;
				}
			}
			
			if (showTutorial)
			{
				//TODO: Draw Tutorial Parts
				switch(tutorialIndex)
				{
				case 0:
					paint.setTextSize(25);
					paint.setARGB(150, 69, 69, 69);
					canvas.drawRect(295, 182, 667, 287, paint);
					
					paint.setARGB(255, 255, 194, 0);
					paint.setStyle(Style.STROKE);
					canvas.drawRect(295, 182, 667, 287, paint);
					
					paint.setColor(Color.WHITE);
					canvas.drawText("Welcome to the Tutorial!", 305, 212, paint);
					//canvas.drawText("Touch this prompt to continue.", 305, 242, paint);
					canvas.drawText("(Use navigation buttons", 305, 242, paint);
					canvas.drawText("in the corners to progress.)", 305, 272, paint);
					paint.setStyle(Style.FILL_AND_STROKE);
					break;
				case 1:
					paint.setTextSize(25);
					paint.setARGB(150, 69, 69, 69);
					canvas.drawRect(295, 162, 667, 237, paint);
					canvas.drawRect(295, 282, 667, 357, paint);					
					
					paint.setARGB(255, 255, 194, 0);
					paint.setStyle(Style.STROKE);
					canvas.drawRect(295, 162, 667, 237, paint);
					canvas.drawRect(295, 282, 667, 357, paint);
					
					paint.setColor(Color.WHITE);
					canvas.drawText("This is the central tower.", 305, 192, paint);
					//canvas.drawText("Touch this prompt to continue.", 305, 242, paint);
					canvas.drawText("The goal is to protect it.", 305, 222, paint);
					canvas.drawText("Health is shown below. The", 305, 312, paint);
					canvas.drawText("game ends when it is gone.", 305, 342, paint);
					
					paint.setAlpha(150);
					canvas.drawBitmap(downArrow, 461, 362, paint);
					canvas.drawBitmap(upArrow, 461, 97, paint);
					
					paint.setStyle(Style.FILL_AND_STROKE);
					break;
				case 2:
					paint.setTextSize(25);
					paint.setARGB(150, 69, 69, 69);
					canvas.drawRect(15, 67, 350, 327, paint);
					
					paint.setARGB(255, 255, 194, 0);
					paint.setStyle(Style.STROKE);
					canvas.drawRect(15, 67, 350, 327, paint);
					paint.setStyle(Style.FILL_AND_STROKE);
					paint.setColor(Color.WHITE);
					canvas.drawText("The central tower can also", 25, 97, paint);
					canvas.drawText("attack. In fact, it is the", 25, 127, paint);
					canvas.drawText("most powerful unit in the", 25, 157, paint);
					canvas.drawText("game. Use the blue controls", 25, 187, paint);
					canvas.drawText("to attack with the tower.", 25, 217, paint);
					canvas.drawText("Change control style below.", 25, 257, paint);
					if (slidingControlScheme)
					{
						paint.setARGB(150, 69, 69, 69);
						canvas.drawRect(610, 67, 945, 257, paint);
						
						paint.setARGB(255, 255, 194, 0);
						paint.setStyle(Style.STROKE);
						canvas.drawRect(610, 67, 945, 257, paint);
						
						paint.setColor(Color.WHITE);
						canvas.drawText("     Slider:", 620, 97, paint);
						canvas.drawText("The bar adjusts tower angle.", 620, 127, paint);
						canvas.drawText("Hold the button to adjust", 620, 157, paint);
						canvas.drawText("power. The longer the", 620, 187, paint);
						canvas.drawText("button is held, the farther", 620, 217, paint);
						canvas.drawText("it can shoot.  Release to fire.", 620, 247, paint);
						
						
						canvas.drawText("Joystick", 35, 307, paint);
						paint.setColor(Color.YELLOW);
						canvas.drawText("Slider", 255, 307, paint);
					}
					else
					{
						paint.setARGB(150, 69, 69, 69);
						canvas.drawRect(610, 67, 945, 232, paint);
						
						paint.setARGB(255, 255, 194, 0);
						paint.setStyle(Style.STROKE);
						canvas.drawRect(610, 67, 945, 232, paint);
						
						paint.setColor(Color.WHITE);
						canvas.drawText("     Joystick:", 620, 97, paint);
						canvas.drawText("Hold the button to start", 620, 127, paint);
						canvas.drawText("shooting. Use the blue ring", 620, 157, paint);
						canvas.drawText("to aim. Release the button", 620, 187, paint);
						canvas.drawText("to fire.", 620, 217, paint);
						
						
						canvas.drawText("Slider", 255, 307, paint);
						paint.setColor(Color.YELLOW);
						canvas.drawText("Joystick", 35, 307, paint);						
					}
					
					paint.setStyle(Style.FILL_AND_STROKE);
					paint.setARGB(150, 69, 69, 69);
					canvas.drawRect(610, 272, 945, 412, paint);
					
					paint.setARGB(255, 255, 194, 0);
					paint.setStyle(Style.STROKE);
					canvas.drawRect(610, 272, 945, 412, paint);
					
					paint.setColor(Color.WHITE);
					canvas.drawText("Note: The central tower", 620, 302, paint);
					canvas.drawText("shoots in an arc, and will", 620, 332, paint);
					canvas.drawText("only hit where the", 620, 362, paint);
					canvas.drawText("crosshairs are pointing.", 620, 392, paint);
					
					paint.setStyle(Style.FILL_AND_STROKE);
					break;
				case 3:
					paint.setTextSize(25);
					paint.setARGB(150, 69, 69, 69);
					canvas.drawRect(15, 67, 350, 143, paint);
					
					paint.setARGB(255, 255, 194, 0);
					paint.setStyle(Style.STROKE);
					canvas.drawRect(15, 67, 350, 143, paint);
					paint.setStyle(Style.FILL_AND_STROKE);
					paint.setColor(Color.WHITE);
					canvas.drawText("Practice killing enemies.", 25, 97, paint);
					canvas.drawText("Tap here to spawn one.", 25, 127, paint);
					break;
				case 4:
					paint.setTextSize(25);
					paint.setARGB(150, 69, 69, 69);
					canvas.drawRect(15, 67, 350, 271, paint);
					
					paint.setARGB(255, 255, 194, 0);
					paint.setStyle(Style.STROKE);
					canvas.drawRect(15, 67, 350, 271, paint);
					paint.setStyle(Style.FILL_AND_STROKE);
					paint.setColor(Color.WHITE);
					canvas.drawText("Tap the screen lightly to", 25, 97, paint);
					canvas.drawText("build a weak wall. Did it", 25, 127, paint);
					canvas.drawText("disappear? Hold longer to", 25, 157, paint);
					canvas.drawText("build a stronger wall.", 25, 187, paint);
					canvas.drawText("Stronger walls cost more", 25, 217, paint);
					canvas.drawText("gold but do not decay.", 25, 247, paint);
					break;
				case 5:
					paint.setTextSize(25);
					paint.setARGB(150, 69, 69, 69);
					canvas.drawRect(15, 67, 350, 334, paint);
					
					paint.setARGB(255, 255, 194, 0);
					paint.setStyle(Style.STROKE);
					canvas.drawRect(15, 67, 350, 334, paint);
					paint.setStyle(Style.FILL_AND_STROKE);
					paint.setColor(Color.WHITE);					

					canvas.drawText("To build a tower on a wall", 25, 97, paint);
					canvas.drawText("drag the tower token from", 25, 127, paint);
					canvas.drawText("the bottom of the screen", 25, 157, paint);
					canvas.drawText("onto the wall. ", 25, 187, paint);
					canvas.drawText("   -Fire Attacks Enemies", 25, 217, paint);
					canvas.drawText("   -Ice Slows Enemies", 25, 247, paint);
					canvas.drawText("   -Health repairs", 25, 277, paint);
					canvas.drawText("     nearby towers.", 25, 307, paint);
					break;
				case 6:
					paint.setTextSize(25);
					paint.setARGB(150, 69, 69, 69);
					canvas.drawRect(15, 67, 350, 267, paint);
					canvas.drawRect(610, 67, 945, 237, paint);
					
					paint.setARGB(255, 255, 194, 0);
					paint.setStyle(Style.STROKE);
					canvas.drawRect(15, 67, 350, 267, paint);
					canvas.drawRect(610, 67, 945, 237, paint);
					paint.setStyle(Style.FILL_AND_STROKE);
					paint.setColor(Color.WHITE);					

					canvas.drawText("A tower can be upgraded", 25, 97, paint);
					canvas.drawText("by dragging another token", 25, 127, paint);
					canvas.drawText("onto it (same type). Cost", 25, 157, paint);
					canvas.drawText("appears in the top left.", 25, 187, paint);
					canvas.drawText("corner. The central tower", 25, 217, paint);
					canvas.drawText("can be upgraded as well.", 25, 247, paint);
					
					canvas.drawText("The central tower also", 620, 97, paint);
					canvas.drawText("has a special attack that", 620, 127, paint);
					canvas.drawText("is ready when a gold ring", 620, 157, paint);
					canvas.drawText("appears on the tower.",620, 187,paint);
					canvas.drawText("Click the tower to use.", 620, 217, paint);
					break;
				case 7:
					paint.setTextSize(25);
					paint.setARGB(150, 69, 69, 69);
					canvas.drawRect(295, 182, 667, 287, paint);
					
					paint.setARGB(255, 255, 194, 0);
					paint.setStyle(Style.STROKE);
					canvas.drawRect(295, 182, 667, 287, paint);
					
					paint.setColor(Color.WHITE);
					canvas.drawText("          Tutorial completed!", 305, 212, paint);
					//canvas.drawText("Touch this prompt to continue.", 305, 242, paint);
					canvas.drawText("Click the next page arrow to", 305, 242, paint);
					canvas.drawText("start the game. Enjoy!", 305, 272, paint);
					paint.setStyle(Style.FILL_AND_STROKE);
					break;
				default:
					break;
				}
				paint.setAlpha(255);
				if (tutorialIndex != 0)
					canvas.drawBitmap(leftArrow, 10, 14, paint);
				canvas.drawBitmap(rightArrow, 890, 14, paint);
			}
			
			if (pauseGame)
			{
				paint.setARGB(150, 0, 0, 0);
				canvas.drawPaint(paint);
				//paint.setColor(Color.RED);
				//paint.setTextSize(100);
				//canvas.drawRect(ButtonOne.getRect(), paint);
				//canvas.drawRect(ButtonTwo.getRect(), paint);
				paint.setAlpha(255);
				if (gameOver)
				{
					if (HardModeShowUpgrade)
					{
						paint.setTextSize(30);
						paint.setARGB(255, 255, 194, 0);
						paint.setTypeface(LargeNine);
						canvas.drawText("Hard Mode Unlocked!", 120, 200, paint);
						
					}
					//canvas.drawText("Game Over", 50, 100, paint);
					canvas.drawBitmap(gameOverText, 0,0, paint);
				}
				else
				{
					//canvas.drawText("Paused",50,100,paint);
					canvas.drawBitmap(pausedText,0,0,paint);
				}
				
				//paint.setColor(Color.WHITE);
				//paint.setTextSize(50);
				//canvas.drawText("Quit", ButtonTwo.getX()+25, ButtonTwo.getY()+75, paint);
				canvas.drawBitmap(quitButton, DEFAULT_WIDTH-quitButton.getWidth()-25, DEFAULT_HEIGHT-quitButton.getHeight()-25, paint);
				if (gameOver)
				{
					canvas.drawBitmap(newgameButton, 25, DEFAULT_HEIGHT-quitButton.getHeight()-25, paint);
				}
				else
				{
					canvas.drawBitmap(resumeButton, 25, DEFAULT_HEIGHT-quitButton.getHeight()-25, paint);
				}
				
				paint.setTextSize(30);
				paint.setColor(Color.WHITE);
				paint.setTypeface(LargeNine);
				canvas.drawText("Score: "+Integer.toString(experiencePoints), 120, 250, paint);
				canvas.drawText("Highscore: "+Integer.toString(highscore), 120, 300, paint);
			}
		}
		//Debug.stopMethodTracing();
		
		if (DevMode)
		{
			paint.setColor(Color.WHITE);
			paint.setTextSize(20);
			paint.setTypeface(Typeface.DEFAULT);
			canvas.drawText(Float.toString(ElapsedTime), 10, 20, paint);
		}
	}
	
	
	private float[] getIceTowerCircle(int x, int y) 
	{		
		float[] retVal = new float[8];
		
		retVal[0] = (float)( x*32+16 + (TowerData[x][y][1]*1/2)*Math.cos(Math.toRadians(TowerTimer[x][y])));
		retVal[1] = (float)( y*32+16 + (TowerData[x][y][1]*1/2)*Math.sin(Math.toRadians(TowerTimer[x][y])));
		retVal[2] = (float)( x*32+16 + (TowerData[x][y][1]*1/2)*Math.cos(Math.PI + Math.toRadians(TowerTimer[x][y])));
		retVal[3] = (float)( y*32+16 + (TowerData[x][y][1]*1/2)*Math.sin(Math.PI + Math.toRadians(TowerTimer[x][y])));
		retVal[4] = (float)( x*32+16 + (TowerData[x][y][1]*1/2)*Math.cos(2*Math.PI/3+ Math.toRadians(TowerTimer[x][y])));
		retVal[5] = (float)( y*32+16 + (TowerData[x][y][1]*1/2)*Math.sin(2*Math.PI/3 + Math.toRadians(TowerTimer[x][y])));
		retVal[6] = (float)( x*32+16 + (TowerData[x][y][1]*1/2)*Math.cos(-2*Math.PI/3 + Math.toRadians(TowerTimer[x][y])));
		retVal[7] = (float)( y*32+16 + (TowerData[x][y][1]*1/2)*Math.sin(-2*Math.PI/3 + Math.toRadians(TowerTimer[x][y])));
		
		return retVal;
	}

	public void update(float elapsedTime) {
		//Log.d("Elapsed Time: ", Float.toString(elapsedTime));
		
		//AudioService.HalfOverSwitch();
		if (enableMusic)
			AudioService.update(gameState, pauseGame, maxLevel);
		
		//if (gold < 20000)
//			gold += 1000;
		
		ElapsedTime = elapsedTime;
		SetFingerRects();
		
		if (gameState == -1)
		{
			if (IntroTimer == 0)
			{
				initVariables();
				initMenu();
			}
			IntroTimer += elapsedTime;
			if (IntroTimer > 2000)
			{				
				gameState = 0;
			}
		}
		
		if (gameState == 0)
		{
			int action = 0;
			//int fingerCount = 0;
			for(int i = 0; i < fingerData.length; i++)
			{
				if (!fingerIgnore[i] && fingerData[i][2] == 1)
				{
					if (DialogOption != 2)
					{
						if (ButtonOne.collides(fingerRects[i]))
						{
							action = 1;
							fingerIgnore[i] = true;
						}
						if (ButtonTwo.collides(fingerRects[i]))
						{
							action = 2;
						}
						if (ButtonThree.collides(fingerRects[i]))
						{
							action = 3;
						}
						
						if (fingerData[i][0] > 25 && fingerData[i][0] < 90 && fingerData[i][1] > 450 && fingerData[i][1] < 500)
						{
							//final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.NathanTornquist.com"));
							//gameContext.startActivity(intent);
							if (enableSound)
								AudioService.PlaySound(SoundEffect.Menu_Select);
							ShowDialog = true;
							DialogOption = 1;
							ButtonTwo = new Rectangle( 180+27, 170+132, 223-27, 173-132);
							ButtonThree = new Rectangle( 180+377, 170+132, 223-27, 173-132);
						}
					}
					else
					{
						if (fingerData[i][0] > 63 && fingerData[i][0] < 257 && fingerData[i][1] > 256 && fingerData[i][1] < 294)
						{
							action = 1;		
							fingerIgnore[i] = true;
						}
						if (fingerData[i][0] > 349 && fingerData[i][0] < 610 && fingerData[i][1] > 256 && fingerData[i][1] < 294)
						{
							action = 2;		
							fingerIgnore[i] = true;
						}
						if (fingerData[i][0] > 702 && fingerData[i][0] < 896 && fingerData[i][1] > 256 && fingerData[i][1] < 294)
						{
							if (HardModeEnabled)
							{
								action = 3;		
								fingerIgnore[i] = true;
							}
						}
						
						if (fingerData[i][0] > 25 && fingerData[i][0] < 312 && fingerData[i][1] > 422 && fingerData[i][1] < 515)
						{
							action = 4;		
							fingerIgnore[i] = true;
						}
					}
					//fingerCount += 1;
					fingerRegion[i] = 2;
				}
			}
			
			//if (fingerCount > 1)
			if (!ShowDialog)
			{
				if (action == 1)
				{			
					ShowDialog = true;
					DialogOption = 2;
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
				}
				
				if (action == 2)
				{
					//temp
					//System.exit(0);
					gameState = 3;
					initAbout();
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
				}		
				
				if (action == 3)
				{
					gameState = 2;
					initHelp();
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
				}
			}
			else
			{
				if (DialogOption != 2)
				{		
					if (action == 2)
					{
						ButtonOne = new Rectangle( 632, 166, 283, 88); 
						ButtonThree = new Rectangle( 632, 286, 283, 88);
						ButtonTwo = new Rectangle( 632, 405, 283, 88);
						if (enableSound)
							AudioService.PlaySound(SoundEffect.Menu_Select);
						if (DialogOption == 1)
						{
	
							ShowDialog = false;
							DialogOption = 0;
							final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.NathanTornquist.com"));
							gameContext.startActivity(intent);
						}
					}
					else if (action == 3)
					{
						if (enableSound)
							AudioService.PlaySound(SoundEffect.Menu_Select);
						ButtonOne = new Rectangle( 632, 166, 283, 88); 
						ButtonThree = new Rectangle( 632, 286, 283, 88);
						ButtonTwo = new Rectangle( 632, 405, 283, 88);
						ShowDialog = false;
						DialogOption = 0;
					}
				}
				else
				{
					if (action != 0 && action != 4)
					{
						if (action == 1)//Easy
						{
							HardModeActive = false;
							EasyModeActive = true;
						}
						else if (action == 2)//Normal
						{
							HardModeActive = false;
							EasyModeActive = false;
						}
						else if (action == 3)//Hard
						{
							HardModeActive = true;
							EasyModeActive = false;
						}
						
						pauseGame = false;
						initVariables();
						gameOver = false;

						CrossHairs.setX((float) (CannonCenterX + 2*((Speed)*Math.cos(Math.PI * Angle / 180)*(-1))));
						CrossHairs.setY((float) (CannonCenterY + 2*((Speed)*Math.sin(Math.PI * Angle / 180))));
						
						//AudioService.StartMusic();
						
						gameState = 1;
						if (enableSound)
							AudioService.PlaySound(SoundEffect.Menu_Select);
					}
					
					if (action == 4) //Back Button
					{
						ShowDialog = false;
						DialogOption = 0;
						if (enableSound)
							AudioService.PlaySound(SoundEffect.Menu_Select);
					}
				}
			}
			
			for (int i = 0; i < fingerData.length; i++)
			{
				if (fingerData[i][2] == 1)
				{
					fingerIgnore[i] = true;
				}
			}
			

			ButtonOne.setX(ButtonOne.getX());
			ButtonTwo.setX(ButtonTwo.getX());
			ButtonThree.setX(ButtonThree.getX());
		}
		
		if (gameState == 3)
		{
			int action = 0;
			//int fingerCount = 0;
			for(int i = 0; i < fingerData.length; i++)
			{
				if (!fingerIgnore[i] && fingerData[i][2] == 1)
				{
					if (ButtonOne.collides(fingerRects[i]))
					{
						action = 1;
					}
					else if (ButtonTwo.collides(fingerRects[i]))
					{
						action = 2;
					}
					else if (ButtonThree.collides(fingerRects[i]))
					{
						action = 3;
					}
					
					if (fingerData[i][0] > 860 && fingerData[i][1] > 500)
					{
						if (DevMode)
							DevMode = false;
						else
							DevMode = true;
					}
					
					if (fingerData[i][0] > (DEFAULT_WIDTH - donate.getWidth()) && fingerData[i][1] < donate.getHeight())
					{
						if (enableSound)
							AudioService.PlaySound(SoundEffect.Menu_Select);
						gameState = 4;
						//if(BillingHelper.isBillingSupported())
						//	BillingHelper.requestPurchase(gameContext, "android.test.purchased"); 
					}
					
					//fingerCount += 1;
					fingerRegion[i] = 2;
				}
			}
			
			//if (fingerCount > 1)
			if (!ShowDialog)
			{
				if (action == 1)
				{
					pauseGame = false;
					initMenu();
					gameState = 0;
					gameOver = false;
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
				}
				else if (action == 2)
				{				
					//180, 170
					//27, 132    223, 173
					ButtonTwo = new Rectangle( 180+27, 170+132, 223-27, 173-132);
					ButtonThree = new Rectangle( 180+377, 170+132, 223-27, 173-132);
					DialogOption = 1;
					ShowDialog = true;
					//final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.NathanTornquist.com"));
					//gameContext.startActivity(intent);
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
	
				}
				else if (action == 3)
				{
					ButtonTwo = new Rectangle( 180+27, 170+132, 223-27, 173-132);
					ButtonThree = new Rectangle( 180+377, 170+132, 223-27, 173-132);
					DialogOption = 2;
					ShowDialog = true;
					//final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.MichaelBetzMusic.com"));
					//gameContext.startActivity(intent);
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
				}
			}
			else
			{
				if (action == 2)
				{
					ButtonOne = new Rectangle( 25, screenHeight - 118, 287, 93);
					ButtonTwo = new Rectangle( 437, 181, 457, 70);
					ButtonThree = new Rectangle( 357, 283, 314, 17);
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
					if (DialogOption == 1)
					{

						ShowDialog = false;
						DialogOption = 0;
						final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.NathanTornquist.com"));
						gameContext.startActivity(intent);
					}
					else
					{
						if (enableSound)
							AudioService.PlaySound(SoundEffect.Menu_Select);
						ShowDialog = false;
						DialogOption = 0;
						final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.MichaelBetzMusic.com"));
						gameContext.startActivity(intent);
					}
				}
				else if (action == 3)
				{
					ButtonOne = new Rectangle( 25, screenHeight - 118, 287, 93);
					ButtonTwo = new Rectangle( 437, 181, 457, 70);
					ButtonThree = new Rectangle( 357, 283, 314, 17);
					ShowDialog = false;
					DialogOption = 0;
				}
			}
			
			for (int i = 0; i < fingerData.length; i++)
			{
				if (fingerData[i][2] == 1)
				{
					fingerIgnore[i] = true;
				}
			}
			

			ButtonOne.setX(ButtonOne.getX());
			ButtonTwo.setX(ButtonTwo.getX());
			ButtonThree.setX(ButtonThree.getX());
		}
		
		if (gameState == 4)
		{
			int action = 0;
			//int fingerCount = 0;
			for(int i = 0; i < fingerData.length; i++)
			{
				if (!fingerIgnore[i] && fingerData[i][2] == 1)
				{
					if (ButtonOne.collides(fingerRects[i]))
					{
						action = 1;
					}
					
					if (fingerData[i][0] > 200 && fingerData[i][0] < 325 && fingerData[i][1] > 230 && fingerData[i][1] < 275)
					{
						//1
						action = 2;
					}
					else if (fingerData[i][0] > 200 && fingerData[i][0] < 325 && fingerData[i][1] > 280 && fingerData[i][1] < 325)
					{
						//5
						action = 3;
					}
					else if (fingerData[i][0] > 200 && fingerData[i][0] < 325 && fingerData[i][1] > 330 && fingerData[i][1] < 375)
					{
						//10
						action = 4;
					}
					else if (fingerData[i][0] > 500 && fingerData[i][0] < 625 && fingerData[i][1] > 230 && fingerData[i][1] < 275)
					{
						//15
						action = 5;
					}
					else if (fingerData[i][0] > 500 && fingerData[i][0] < 625 && fingerData[i][1] > 280 && fingerData[i][1] < 325)
					{
						//25
						action = 6;
					}
					else if (fingerData[i][0] > 500 && fingerData[i][0] < 625 && fingerData[i][1] > 330 && fingerData[i][1] < 375)
					{
						//50
						action = 7;
					}
					
					fingerRegion[i] = 2;
				}
			}
			
			if (action == 1)
			{
				gameState = 3;
				initAbout();
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Menu_Select);
			}
			else if (action == 2) //1
			{
			    if (enableSound)
					AudioService.PlaySound(SoundEffect.Menu_Select);
				if(BillingHelper.isBillingSupported())
					BillingHelper.requestPurchase(gameContext, "onedollar"); 
			}
			else if (action == 3) //5
			{
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Menu_Select);
				if(BillingHelper.isBillingSupported())
					BillingHelper.requestPurchase(gameContext, "fivedollars"); 
			}
			else if (action == 4) //10
			{
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Menu_Select);
				if(BillingHelper.isBillingSupported())
					BillingHelper.requestPurchase(gameContext, "tendollars"); 
			}
			else if (action == 5) //15
			{
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Menu_Select);
				if(BillingHelper.isBillingSupported())
					BillingHelper.requestPurchase(gameContext, "fifteendollars"); 
			}
			else if (action == 6) //25
			{
				if (enableSound)	
					AudioService.PlaySound(SoundEffect.Menu_Select);
				if(BillingHelper.isBillingSupported())
					BillingHelper.requestPurchase(gameContext, "twentyfivedollars"); 
			}
			else if (action == 7) //50
			{
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Menu_Select);
				if(BillingHelper.isBillingSupported())
					BillingHelper.requestPurchase(gameContext, "fiftydollars"); 
			}
			
			for (int i = 0; i < fingerData.length; i++)
			{
				if (fingerData[i][2] == 1)
				{
					fingerIgnore[i] = true;
				}
			}
			

			ButtonOne.setX(ButtonOne.getX());
		}

		if (gameState == 2) // Instructions Scrolling
		{			
//			if (InstructionsNextLoad && InstructionsX == 0)
//			{
//				gameActivity.runOnUiThread(new Runnable() { public void run() { ImageLoader task = new ImageLoader(gameContext, InstructionsNextImage,InstructionsNextReplace, InstructionsNextIndex); task.execute();} });
//				InstructionsNextLoad = false;
//			}
			
			int action = 0;
			//int fingerCount = 0;
			for(int i = 0; i < fingerData.length; i++)
			{
				if (!fingerIgnore[i] && fingerData[i][2] == 1)
				{
					if (ButtonOne.collides(fingerRects[i]))
					{
						action = 1;
						fingerIgnore[i] = true;
					}
					if (ButtonTwo.collides(fingerRects[i]))
					{
						action = 2;
						fingerIgnore[i] = true;
					}
					if (ButtonThree.collides(fingerRects[i]))
					{
						action = 3;
						fingerIgnore[i] = true;
					}
					//fingerCount += 1;
					fingerRegion[i] = 2;
				}
			}
			
			//if (fingerCount > 1)
			if (action == 1 && !ShowDialog)
			{
				if (InstructionsIndex != 0)
				{
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
					Inst1 = Instructions_1;
					Inst2 = Instructions_1;
					Inst3 = Instructions_2;
										
					InstructionsY = 0;
					InstructionsIndex = 0;
					
					InstructionsNextImage = "Instructions_Full_1.png";
				    InstructionsNextIndex = InstructionsIndex;
				    InstructionsNextReplace = 2;
				    InstructionsNextLoad = true;	
				}
				else
				{
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
					pauseGame = false;
					initMenu();
					gameState = 0;
					gameOver = false;
				}			
			}
			else
			{
				if (action == 2)
				{
					ButtonThree = new Rectangle( 632, 286, 283, 88);
					ButtonTwo = new Rectangle( 632, 405, 283, 88);
					if (enableSound && ShowDialog)
						AudioService.PlaySound(SoundEffect.Menu_Select);
					if (DialogOption == 1)
					{
	
						ShowDialog = false;
						DialogOption = 0;
						highscore = 0;
					}
					skipButton = true;
				}
				else if (action == 3)
				{
					if (enableSound && ShowDialog)
						AudioService.PlaySound(SoundEffect.Menu_Select);
					ButtonThree = new Rectangle( 632, 286, 283, 88);
					ButtonTwo = new Rectangle( 632, 405, 283, 88);
					ShowDialog = false;
					DialogOption = 0;
					skipButton = true;
				}
			}
			
			
			for (int i = 0; i < fingerData.length; i++)
			{
				if (fingerData[i][2] == 1)
				{
					fingerIgnore[i] = true;
				}
			}
			
			ButtonOne.setX(ButtonOne.getX());
			ButtonTwo.setX(ButtonTwo.getX());
			ButtonThree.setX(ButtonThree.getX());
			
			if (fingerData[0][2] == 1 && !ButtonOne.touched() && !instTransitioning && !ShowDialog)
			{
				if (instStartX == -10 || instStartY == -10)
				{
					instStartX = fingerData[0][0];
					instStartY = fingerData[0][1];
				}
				else
				{
				    if (instScrollDirection == 0)
				    {
				    	if (fingerData[0][0] < 100)
				    		InstructionsBumpScroll = 1;
				    	else if (fingerData[0][0] > DEFAULT_WIDTH - 100)
				    		InstructionsBumpScroll = 2;
				    	if (InstructionsIndex == 0)
				    	{
				    		
				    		if (fingerData[0][0] > 133 && fingerData[0][0] < 320)
			    			{
			    				if (fingerData[0][1] > 171 && fingerData[0][1] < 240)
			    				{
			    					instructionsJump = 1;
			    				}
			    			}
				    		if (fingerData[0][0] > 133 && fingerData[0][0] < 417)
			    			{
			    				if (fingerData[0][1] > 294 && fingerData[0][1] < 322)
			    				{
			    					instructionsJump = 2;
			    				}
			    			}
				    		if (fingerData[0][0] > 133 && fingerData[0][0] < 558)
			    			{
			    				if (fingerData[0][1] > 335 && fingerData[0][1] < 363)
			    				{
			    					instructionsJump = 3;
			    				}
			    			}
				    		if (fingerData[0][0] > 481 && fingerData[0][0] < 845)
			    			{
			    				if (fingerData[0][1] > 171 && fingerData[0][1] < 199)
			    				{
			    					instructionsJump = 4;
			    				}
			    			}
				    		if (fingerData[0][0] > 329 && fingerData[0][0] < 633)
			    			{
			    				if (fingerData[0][1] > 417 && fingerData[0][1] < 445)
			    				{
			    					instructionsJump = 5;
			    				}
			    			}
//				    		if (fingerData[0][0] > 481 && fingerData[0][0] < 726)
//			    			{
//			    				if (fingerData[0][1] > 212 && fingerData[0][1] < 240)
//			    				{
//			    					instructionsJump = 6;
//			    				}
//			    			}
				    	}
				    	if (InstructionsIndex == 1)
				    	{
				    		if (fingerData[0][0] > 452 && fingerData[0][0] < 860)
				    		{
				    			if (fingerData[0][1] > 20 && fingerData[0][1] < 510)
				    			{
				    				instructionsJump = 6;
				    			}
				    		}
				    	}
				    	if (InstructionsIndex == 2)
				    	{
				    		if (fingerData[0][0] > 130 && fingerData[0][0] < 510)
				    		{
				    			if (fingerData[0][1] > 30 && fingerData[0][1] < 510)
				    			{
				    				instructionsJump = 7;
				    			}
				    		}
			    			if (fingerData[0][0] > 630 && fingerData[0][0] < 925)
				    		{
				    			if (fingerData[0][1] > 30 && fingerData[0][1] < 500)
				    			{
				    				instructionsJump = 8;
				    			}
				    		}					    		
				    	}
				    	
				    	/*
				    	if (InstructionsIndex == 0)
				    	{
				    		if (fingerData[0][0] > 100 && fingerData[0][0] < 340)
				    		{
				    			if (fingerData[0][1] > 130 + InstructionsY && fingerData[0][1] < 160 + InstructionsY)
				    			{
				    				instructionsJump = 1;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 100 && fingerData[0][0] < 300)
				    		{
				    			if (fingerData[0][1] > 170 + InstructionsY && fingerData[0][1] < 200 + InstructionsY)
				    			{
				    				instructionsJump = 2;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 100 && fingerData[0][0] < 380)
				    		{
				    			if (fingerData[0][1] > 210 + InstructionsY && fingerData[0][1] < 240 + InstructionsY)
				    			{
				    				instructionsJump = 3;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 100 && fingerData[0][0] < 415)
				    		{
				    			if (fingerData[0][1] > 250 + InstructionsY && fingerData[0][1] < 280 + InstructionsY)
				    			{
				    				instructionsJump = 4;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 100 && fingerData[0][0] < 290)
				    		{
				    			if (fingerData[0][1] > 290 + InstructionsY && fingerData[0][1] < 320 + InstructionsY)
				    			{
				    				instructionsJump = 5;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 100 && fingerData[0][0] < 250)
				    		{
				    			if (fingerData[0][1] > 330 + InstructionsY && fingerData[0][1] < 360 + InstructionsY)
				    			{
				    				instructionsJump = 6;
				    			}
				    		}
				    	}
				    	
				    	if (InstructionsIndex == 2)
				    	{
				    		if (fingerData[0][0] > 494 + 160 && fingerData[0][0] < 540 + 160)
				    		{
				    			if (fingerData[0][1] > 500 + InstructionsY && fingerData[0][1] < 550 + InstructionsY)
				    			{
				    				instructionsJump = 11;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 494 + 80 && fingerData[0][0] < 540 + 80)
				    		{
				    			if (fingerData[0][1] > 500 + InstructionsY && fingerData[0][1] < 550 + InstructionsY)
				    			{
				    				instructionsJump = 10;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 494 && fingerData[0][0] < 540)
				    		{
				    			if (fingerData[0][1] > 500 + InstructionsY && fingerData[0][1] < 550 + InstructionsY)
				    			{
				    				instructionsJump = 9;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 413 && fingerData[0][0] < 456)
				    		{
				    			if (fingerData[0][1] > 486 + InstructionsY && fingerData[0][1] < 535 + InstructionsY)
				    			{
				    				instructionsJump = 8;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 160 && fingerData[0][0] < 203)
				    		{
				    			if (fingerData[0][1] > 483 + InstructionsY && fingerData[0][1] < 532 + InstructionsY)
				    			{
				    				instructionsJump = 7;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 766 && fingerData[0][0] < 809)
				    		{
				    			if (fingerData[0][1] > 503 + InstructionsY && fingerData[0][1] < 551 + InstructionsY)
				    			{
				    				instructionsJump = 6;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 289 && fingerData[0][0] < 331)
				    		{
				    			if (fingerData[0][1] > 522 + InstructionsY && fingerData[0][1] < 571 + InstructionsY)
				    			{
				    				instructionsJump = 5;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 290 && fingerData[0][0] < 333)
				    		{
				    			if (fingerData[0][1] > 461 + InstructionsY && fingerData[0][1] < 509 + InstructionsY)
				    			{
				    				instructionsJump = 4;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 312 && fingerData[0][0] < 354)
				    		{
				    			if (fingerData[0][1] > 162 + InstructionsY && fingerData[0][1] < 211 + InstructionsY)
				    			{
				    				instructionsJump = 3;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 457 && fingerData[0][0] < 500)
				    		{
				    			if (fingerData[0][1] > 177 + InstructionsY && fingerData[0][1] < 224 + InstructionsY)
				    			{
				    				instructionsJump = 2;
				    			}
				    		}
				    		
				    		if (fingerData[0][0] > 152 && fingerData[0][0] < 196)
				    		{
				    			if (fingerData[0][1] > 196 + InstructionsY && fingerData[0][1] < 245 + InstructionsY)
				    			{
				    				instructionsJump = 1;
				    			}
				    		}
				    	}
				    	*/
						if ((fingerData[0][0] - instStartX < -25) || (instStartX - fingerData[0][0] < -25))
						{
							instScrollDirection = 1;
							InstructionsBumpScroll = 0;
							instStartX = fingerData[0][0];
							instStartY = fingerData[0][1];
							//Log.d("Scrolling Set","Horizontal");
							instructionsJump = 0;
						}
						else if ((fingerData[0][1] - instStartY < -25) || (instStartY - fingerData[0][1] < -25))
						{
							 instScrollDirection = 2;
							 InstructionsBumpScroll = 0;
							 instStartX = fingerData[0][0];
							 instStartY = fingerData[0][1];
							 //Log.d("Scrolling Set","Vertical");
							 instructionsJump = 0;
						}
				    }
				    else
				    {
				    	if (instScrollDirection == 2)
				    	{
				    		InstructionsY += fingerData[0][1] - instStartY;
				    		InstructionsDriftY = fingerData[0][1] - instStartY;
				    		if (InstructionsY > 0)
				    			InstructionsY = 0;
				    		else if (InstructionsY < -Inst2.getHeight() + 540)
				    			InstructionsY = -Inst2.getHeight() + 540;
				    	}
				    	if (instScrollDirection == 1)
				    	{
				    		InstructionsX += fingerData[0][0] - instStartX;
				    		if (InstructionsIndex == 0)
				    		{
				    			if (InstructionsX > 0)
				    				InstructionsX = 0;
				    		}
				    		if (InstructionsIndex == InstructionsCount - 1)
				    		{
				    			if (InstructionsX < 0)
				    				InstructionsX = 0;
				    		}
				    	}
				    	
				    	if (instScrollDirection != 1) //Just keeps things in place. 
				    	{
				    		if (InstructionsX != 0)
				    		{
				    			if (InstructionsX < 0)
				    			{
				    				InstructionsX += elapsedTime*2;
				    				if (InstructionsX > 0)
				    				{
				    					InstructionsX = 0;
				    				}
				    			}
				    			if (InstructionsX > 0)
				    			{
				    				InstructionsX -= elapsedTime*2;
				    				{
				    					if (InstructionsX < 0)
				    						InstructionsX = 0;
				    				}
				    			}
				    		}
				    	}
				    	
				    
				    	instStartX = fingerData[0][0];
						instStartY = fingerData[0][1];
				    }
				}
				
			}
			else
			{
				instStartX = -10;
				instStartY = -10;
				instScrollDirection = 0;
				
				if (instructionsJump != 0)
				{
					if (InstructionsIndex == 0)
					{
						if (skipButton)
						{
							skipButton = false;
						}
						else
						{
							switch(instructionsJump)
							{
							case 1:
								if (slidingControlScheme)
		    					{
		    						slidingControlScheme = false;
		    					}
		    					else
		    					{
		    						slidingControlScheme = true;
		    					}
								if (enableSound)
									AudioService.PlaySound(SoundEffect.Menu_Select);
								break;
							case 2:
								if (enableMusic)
		    					{
									enableMusic = false;
									AudioService.StopMusic();
		    					}
		    					else
		    					{
		    						enableMusic = true;
		    						AudioService.StartMusic();
		    					}
								if (enableSound)
									AudioService.PlaySound(SoundEffect.Menu_Select);
								break;
							case 3:
								if (enableSound)
		    					{
									enableSound = false;
		    					}
		    					else
		    					{
		    						enableSound = true;
		    					}
								if (enableSound)
									AudioService.PlaySound(SoundEffect.Menu_Select);
								break;
							case 4:
								if (showTutorial)
		    					{
									showTutorial = false;
		    					}
		    					else
		    					{
		    						showTutorial = true;
		    					}
								if (enableSound)
									AudioService.PlaySound(SoundEffect.Menu_Select);
								break;
							case 5:
								DialogOption = 1;
								ShowDialog = true;
	
								ButtonTwo = new Rectangle( 180+27, 170+132, 223-27, 173-132);
								ButtonThree = new Rectangle( 180+377, 170+132, 223-27, 173-132);
								if (enableSound)
									AudioService.PlaySound(SoundEffect.Menu_Select);
								break;
//							case 6:
//								if (HardModeActive)
//		    					{
//									HardModeActive = false;
//		    					}
//		    					else
//		    					{
//		    						HardModeActive = true;
//		    					}
//								if (enableSound)
//									AudioService.PlaySound(SoundEffect.Menu_Select);
//								break;
							default:
								break;
							}
						}
						instructionsJump = 0;
					}
					else if (InstructionsIndex == 1)
					{
						
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.petronicarts.solodefense&hl=en"));
						gameContext.startActivity(browserIntent);
						instructionsJump = 0;
					}
					else if (InstructionsIndex == 2)
					{
						if (instructionsJump == 7)
						{
							Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://store.michaelbetzmusic.com/album/arcis-original-game-soundtrack/"));
							gameContext.startActivity(browserIntent);
						}
						else if (instructionsJump == 8)
						{
							Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://michaelbetzmusic.com/"));
							gameContext.startActivity(browserIntent);
						}
					
						instructionsJump = 0;
					}
				}
				
				/*
				if (instructionsJump != 0)
				{
					if (InstructionsIndex == 0)
					{
						switch(instructionsJump)
						{
						case 0:
						    Inst1 = Instructions_1;
						    Inst2 = Instructions_1;
						    Inst3 = Instructions_2;
						    

						    InstructionsNextImage = "Instructions_Full_1.png";
						    InstructionsNextIndex = instructionsJump;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 1:
						    Inst1 = Instructions_1;
						    Inst2 = Instructions_2;
						    Inst3 = Instructions_3;
						    break;
						case 2:
						    Inst1 = Instructions_2;
						    Inst2 = Instructions_3;
						    Inst3 = Instructions_4;
						    
						    InstructionsNextImage = "Instructions_Full_3.png";
						    InstructionsNextIndex = instructionsJump;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 3:
						    Inst1 = Instructions_3;
						    Inst2 = Instructions_4;
						    Inst3 = Instructions_5;

						    InstructionsNextImage = "Instructions_Full_4.png";
						    InstructionsNextIndex = instructionsJump;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 4:
						    Inst1 = Instructions_4;
						    Inst2 = Instructions_5;
						    Inst3 = Instructions_6;

						    InstructionsNextImage = "Instructions_Full_5.png";
						    InstructionsNextIndex = instructionsJump;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 5:
						    Inst1 = Instructions_5;
						    Inst2 = Instructions_6;
						    Inst3 = Instructions_7;

						    InstructionsNextImage = "Instructions_Full_6.png";
						    InstructionsNextIndex = instructionsJump;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 6:
						    Inst1 = Instructions_6;
						    Inst2 = Instructions_7;
						    Inst3 = Instructions_7;
						    break;
						default:
						    Inst1 = Instructions_1;
						    Inst2 = Instructions_1;
						    Inst3 = Instructions_2;
						    break;
						}
						
						InstructionsY = 0;
						InstructionsIndex = instructionsJump;
					}
					else if (InstructionsIndex == 2)
					{
						switch(instructionsJump)
						{
							case 1:
								InstructionsY = -570;
								break;
							case 2:
								InstructionsY = -740;
								break;
							case 3:
								InstructionsY = -860;
								break;
							case 4:
								InstructionsY = -1020;
								break;
							case 5:
								InstructionsY = -1110;
								break;
							case 6:
								InstructionsY = -1230;
								break;
							case 7:
								InstructionsY = -1350;
								break;
							case 8:
								InstructionsY = -1440;
								break;
							case 9:
								InstructionsY = -1520;
								break;
							case 10:
								InstructionsY = -1560;
								break;
							case 11:
								InstructionsY = -1560;
								break;
							default:
								break;
						}
					}
					instructionsJump = 0;
				}
				*/
				if (((InstructionsX > 200 || InstructionsX < -200) || InstructionsBumpScroll != 0) && !instTransitioning)
				{
					if (InstructionsBumpScroll == 0)
					{
						if (InstructionsX < 0)
						{
							InstructionsIndex += 1;
							InstructionsX = InstructionsX + 960;
						}
						else
						{
							InstructionsIndex -= 1;
							InstructionsX = InstructionsX - 960;
						}
					}
					
					if (InstructionsBumpScroll == 1)
					{
						if (InstructionsIndex != 0)
						{
							InstructionsIndex -= 1;
							InstructionsX = InstructionsX - 960;
							InstructionsBumpScroll = 0;
						}
						else
						{
							InstructionsBumpScroll = 3;
						}
					}
					else if (InstructionsBumpScroll == 2)
					{
						if (InstructionsIndex != InstructionsCount - 1)
						{
							InstructionsIndex += 1;
							InstructionsX = InstructionsX + 960;
							InstructionsBumpScroll = 0;
						}
						else
						{
							InstructionsBumpScroll = 3;
						}
					}
					if (InstructionsBumpScroll != 3)
					{
						if (InstructionsIndex < 0)
						{
							InstructionsIndex = 0;
						}
						if (InstructionsIndex >= InstructionsCount)
						{
							InstructionsIndex = InstructionsCount - 1;
						}
						//Log.d("Instructions Index: ",Integer.toString(InstructionsIndex));
						switch(InstructionsIndex)
						{
						case 0:
						    Inst1 = Instructions_1;
						    Inst2 = Instructions_1;
						    Inst3 = Instructions_2;
						    break;
						case 1:
						    Inst1 = Instructions_1;
						    Inst2 = Instructions_2;
						    Inst3 = Instructions_3;
						    break;
						case 2:
						    Inst1 = Instructions_2;
						    Inst2 = Instructions_3;
						    Inst3 = Instructions_3;
						    break;
						default:
						    Inst1 = Instructions_1;
						    Inst2 = Instructions_1;
						    Inst3 = Instructions_2;
						    break;
						}
						
						/*
						switch(InstructionsIndex)
						{
						case 0:
						    Inst1 = Instructions_1;
						    Inst2 = Instructions_1;
						    Inst3 = Instructions_2;
						    
						    InstructionsNextImage = "Instructions_Full_1.png";
						    InstructionsNextIndex = InstructionsIndex;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 1:
						    Inst1 = Instructions_1;
						    Inst2 = Instructions_2;
						    Inst3 = Instructions_3;
						    break;
						case 2:
						    Inst1 = Instructions_2;
						    Inst2 = Instructions_3;
						    Inst3 = Instructions_4;
						    
						    InstructionsNextImage = "Instructions_Full_3.png";
						    InstructionsNextIndex = InstructionsIndex;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 3:
						    Inst1 = Instructions_3;
						    Inst2 = Instructions_4;
						    Inst3 = Instructions_5;

						    InstructionsNextImage = "Instructions_Full_4.png";
						    InstructionsNextIndex = InstructionsIndex;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 4:
						    Inst1 = Instructions_4;
						    Inst2 = Instructions_5;
						    Inst3 = Instructions_6;

						    InstructionsNextImage = "Instructions_Full_5.png";
						    InstructionsNextIndex = InstructionsIndex;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 5:
						    Inst1 = Instructions_5;
						    Inst2 = Instructions_6;
						    Inst3 = Instructions_7;

						    InstructionsNextImage = "Instructions_Full_6.png";
						    InstructionsNextIndex = InstructionsIndex;
						    InstructionsNextReplace = 2;
						    InstructionsNextLoad = true;
						    break;
						case 6:
						    Inst1 = Instructions_6;
						    Inst2 = Instructions_7;
						    Inst3 = Instructions_7;
						    break;
						default:
						    Inst1 = Instructions_1;
						    Inst2 = Instructions_1;
						    Inst3 = Instructions_2;
						    break;
						}						
						*/
						instTransitioning = true;
						InstructionsOldY = InstructionsY;
						InstructionsY = 0;
					}
				}
				
				if (InstructionsX != 0)
	    		{
	    			if (InstructionsX < 0)
	    			{
	    				InstructionsX += elapsedTime*2;
	    				if (InstructionsX >= 0)
	    				{
	    					InstructionsX = 0;
//	    					if (InstructionsIndex == 3 || InstructionsIndex == 4 || InstructionsIndex == 5 || InstructionsIndex == 6)
//	    						InstructionsNextLoad = true;
	    					instTransitioning = false;
	    				}
	    			}
	    			if (InstructionsX > 0)
	    			{
	    				InstructionsX -= elapsedTime*2;
	    				{
	    					if (InstructionsX <= 0)
	    					{
	    						InstructionsX = 0;
//	    						if (InstructionsIndex == 3 || InstructionsIndex == 4 || InstructionsIndex == 5 || InstructionsIndex == 6)
//	    							InstructionsNextLoad = true;
	    						instTransitioning = false;
	    					}
	    				}
	    			}
	    		}
				
				if (InstructionsDriftY != 0)
				{
					InstructionsY += InstructionsDriftY*elapsedTime/7;
					InstructionsDriftY*=.65;
					if (InstructionsDriftY < .25)
						InstructionsDriftY = 0;
					if (InstructionsY > 0)
					{
		    			InstructionsY = 0;
		    			InstructionsDriftY = 0;
					}
		    		else if (InstructionsY < -Inst2.getHeight() + 540)
		    		{
		    			InstructionsY = -Inst2.getHeight() + 540;
		    			InstructionsDriftY = 0;
		    		}					
				}
			}
		}
		
		if (gameState == 1) //Play State
		{
			if (justPause)
			{
				elapsedTime = 0;
				justPause = false;
			}
			
			if (pauseGame)
			{
				int action = 0;
				//int fingerCount = 0;
				for(int i = 0; i < fingerData.length; i++)
				{
					if (!fingerIgnore[i] && fingerData[i][2] == 1)
					{
						if (ButtonOne.collides(fingerRects[i]))
						{
							action = 1;
						}
						if (ButtonTwo.collides(fingerRects[i]))
						{
							action = 2;
						}
						//fingerCount += 1;
						fingerRegion[i] = 2;
					}
				}
				
				//if (fingerCount > 1)
				if (action == 1)
				{
					HardModeShowUpgrade = false;
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
					pauseGame = false;
					if (gameOver)
					{
						initVariables();

						CrossHairs.setX((float) (CannonCenterX + 2*((Speed)*Math.cos(Math.PI * Angle / 180)*(-1))));
						CrossHairs.setY((float) (CannonCenterY + 2*((Speed)*Math.sin(Math.PI * Angle / 180))));
						
					}
					gameOver = false;
					
				}
				
				if (action == 2)
				{
					HardModeShowUpgrade = false;
					//temp
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Menu_Select);
					gameState = 0;
					initMenu();
					//AudioService.StopMusic();
				}
				
				if (action != 0)
				{
					for (int i = 0; i < fingerData.length; i++)
					{
						if (fingerData[i][2] == 1)
						{
							fingerIgnore[i] = true;
						}
					}
				}
				
	
				ButtonOne.setX(ButtonOne.getX());
				ButtonTwo.setX(ButtonTwo.getX());
			}
			
			if (!pauseGame)
			{
				//Set Finger Rectangles based on Current Touch Data
				
				SetFingerRegions();
				
				if (!showTutorial || (showTutorial && tutorialIndex > 3))
				{
					UpdateTowers(elapsedTime);
					DrawTowers();		
				}
				UpdateControls(elapsedTime);
						
				UpdateBullets(elapsedTime);
				
				UpdateUnits(elapsedTime);
				
				if (!showTutorial || (showTutorial && tutorialIndex > 80))
					SpawnEnemies(elapsedTime);
				
				for (int i = 0; i < Units.size(); i++)
				{
					//Damage Towers if unit is on them.
					int x = (int)(Units.get(i).getX() / (32));
					int y = (int)(Units.get(i).getY() / (32));
					if (TowerData[x][y][0] != 0)
					{
						TowerData[x][y][0] -= Units.get(i).getAttack()*((float)(elapsedTime)/100.0);
					}
					
					//Spawn Minions
					if (Units.get(i).getLevel() == 5 && Units.get(i).powerupReady())
					{
						int q = Units.get(i).getNodeX();
						int w = Units.get(i).getNodeY();
						Unit newUnit = new Unit(q, w, 2, 32, 32, maxLevel);
						
						int goalX = newUnit.getParentX(DirectionMap[q][w]);
						int goalY = newUnit.getParentY(DirectionMap[q][w]);
						newUnit.setGoal(goalX, goalY);
						newUnit.setDelta();
						Units.add(newUnit);
					}
				}
				
				int fireRangeCount = 0;
				for (int x = 0; x < TowerTimer.length; x++)
				{
					for (int y = 0; y < TowerTimer[x].length; y++)
					{
						if (TowerData[x][y][2] == 1)
						{
							TowerTimer[x][y] += elapsedTime;
							int shots = TowerLevel[x][y];
							for (int z = 0; z < Units.size(); z++)
							{
								//Put outside for fireTimer stuff
								//Get Center of Tower
								float i = x*32+16;
								float j = y*32+16;
								//Get Center of Unit
								float k = Units.get(z).getX() + 16;
								float l = Units.get(z).getY() + 16;
								float dist = SquareDistance(i, j, k, l);
								
								if (dist < (TowerData[x][y][1]*3/4)*(TowerData[x][y][1]*3/4))
								{
								
								if (TowerTimer[x][y] > (250*255/TowerData[x][y][1]))
								{
									
								//	if (dist < (TowerData[x][y][1]*3/4)*(TowerData[x][y][1]*3/4))
								//	{
										int damage = TowerLevel[x][y]*3;
										//SimpleBullet newBullet = new SimpleBullet(i, j, (k-i), (l-j), (float).5, (TowerData[x][y][1]*3/4), damage, fireBullet, 1);
										//SimpleBullets.add(newBullet);
										Units.get(z).takeDamage(damage);
										
										if (!Units.get(z).stillAlive())
										{
											int money = Units.get(z).getGold();
											gold += money;
											experiencePoints += money;
											if (money != 0)
											{
												FadeText tempText = new FadeText(Integer.toString(money), Units.get(z).getX(), Units.get(z).getY(), 0, -100, 500, Color.YELLOW, 25);
												FadingText.add(tempText);
												
												FadeBlock tempBlock = new FadeBlock(Units.get(z).getLevel(), Units.get(z).getX()+16, Units.get(z).getY()+16, 16, 32, 250);
												FadingBlocks.add(tempBlock);
												if (enableSound)
													AudioService.PlaySound(SoundEffect.EnemyDestroyed);
											}
										}
										
										shots -= 1;
										if (shots <= 0)
											TowerTimer[x][y] = 0;
								//	}						
									
								}
								
								
									fireRangeCount++;
								}
								
							}
							if (TowerLevel[x][y] != shots)
								TowerTimer[x][y] = 0;
						}
						if (TowerData[x][y][2] == 2)
						{
							//Convert Time to an angle measurement
							//Add Radians
							TowerTimer[x][y] += elapsedTime/10;
							int shoot = 0;
							if (TowerTimer[x][y] > 360)
							{
								TowerTimer[x][y] -= 360;
								shoot = 1;
							}
												
							float[] circles = getIceTowerCircle(x,y);
							
							boolean inRange = false;
							
							for (int z = 0; z < Units.size(); z++)
							{
								if (SquareDistance(Units.get(z).getX()+16, Units.get(z).getY()+16, x*32+16, y*32+16) <= (TowerData[x][y][1]*1/2)*(TowerData[x][y][1]*1/2))
								{
									inRange = true;
									if (TowerLevel[x][y] == 1)
									{
										FadeCircle circ = new FadeCircle(1, circles[0], circles[1], 8, 8, 400);
										FadingCircles.add(circ);
										if (Units.get(z).onLine(circles[0], circles[1], x*32+16, y*32+16, 10))
										{
											Units.get(z).slow();
										}
									}
									else if (TowerLevel[x][y] == 2)
									{
										FadeCircle circ = new FadeCircle(1, circles[0], circles[1], 8, 8, 400);
										FadingCircles.add(circ);
										circ = new FadeCircle(1, circles[2], circles[3], 8, 8, 400);
										FadingCircles.add(circ);
										if (Units.get(z).onLine(circles[0], circles[1], circles[2], circles[3], 10))
										{
											Units.get(z).slow();
										}						
									}
									else if (TowerLevel[x][y] == 3)
									{
										FadeCircle circ = new FadeCircle(1, circles[0], circles[1], 8, 8, 400);
										FadingCircles.add(circ);
										circ = new FadeCircle(1, circles[4], circles[5], 8, 8, 400);
										FadingCircles.add(circ);
										circ = new FadeCircle(1, circles[6], circles[7], 8, 8, 400);
										FadingCircles.add(circ);
										if (Units.get(z).onLine(circles[0], circles[1], x*32+16, y*32+16, 10))
										{
											Units.get(z).slow();
										}
										else if (Units.get(z).onLine(circles[4], circles[5], x*32+16, y*32+16, 10))
										{
											Units.get(z).slow();
										}
										else if (Units.get(z).onLine(circles[6], circles[7], x*32+16, y*32+16, 10))
										{
											Units.get(z).slow();
										}
									}
								}
								
								if (TowerLevel[x][y] == 3 && shoot == 1)
								{
									float i = x*32+16;
									float j = y*32+16;
									//Get Center of Unit
									float k = Units.get(z).getX() + 16;
									float l = Units.get(z).getY() + 16;
									float dist = SquareDistance(i, j, k, l);
									if (dist < (TowerData[x][y][1]*1/2)*(TowerData[x][y][1]*1/2))
									{
										if (!Units.get(z).Stopped())
										{
											//-number indicates a x millisecond pause
											SimpleBullet newBullet = new SimpleBullet(i, j, (k-i), (l-j), (float).5, (TowerData[x][y][1]*1/2), -5000, fireBullet, 2);
											SimpleBullets.add(newBullet);
											
											shoot = 0;				
										}
									}
								}							
							}	
							
							
							if (IceTowerActive[x][y] != inRange)
							{
								if (inRange)
								{
									if (enableSound)
										AudioService.PlaySound(SoundEffect.Ice_Charge);
								}
								IceTowerActive[x][y] = inRange;
							}
						}
						
						if (TowerData[x][y][2] == 3)
						{
							float healing = 0;
							if (TowerLevel[x][y] == 1)
								healing = (float) (15.0 / 1600.0 * elapsedTime); //Half a single #4 unit
							else if (TowerLevel[x][y] == 2)
								healing = (float) (15.0 / 1200.0 * elapsedTime);  //Equal to a #4 unit
							else if (TowerLevel[x][y] == 3)
								healing = (float) (15.0 / 800.0 * elapsedTime);  //Equal to 2 #4 units.
							//Log.d("Healing: ", Float.toString(healing));
							if (y != 0)
							{
								TowerData[x][y-1][0] += healing;
								if(TowerData[x][y-1][0] > TowerData[x][y-1][1])
									TowerData[x][y-1][0] = TowerData[x][y-1][1];
							}
							TowerData[x-1][y][0] += healing;
							if(TowerData[x-1][y][0] > TowerData[x-1][y][1])
								TowerData[x-1][y][0] = TowerData[x-1][y][1];
							TowerData[x+1][y][0] += healing;
							if(TowerData[x+1][y][0] > TowerData[x+1][y][1])
								TowerData[x+1][y][0] = TowerData[x+1][y][1];
							TowerData[x][y+1][0] += healing;
							if(TowerData[x][y+1][0] > TowerData[x][y+1][1])
								TowerData[x][y+1][0] = TowerData[x][y+1][1];
							if (TowerLevel[x][y] > 1)
							{
								if (y != 0)
								{
									TowerData[x-1][y-1][0] += healing;
									if(TowerData[x-1][y-1][0] > TowerData[x-1][y-1][1])
										TowerData[x-1][y-1][0] = TowerData[x-1][y-1][1];
									TowerData[x+1][y-1][0] += healing;
									if(TowerData[x+1][y-1][0] > TowerData[x+1][y-1][1])
										TowerData[x+1][y-1][0] = TowerData[x+1][y-1][1];
								}
								
								TowerData[x][y+1][0] += healing;
								if(TowerData[x][y+1][0] > TowerData[x][y+1][1])
									TowerData[x][y+1][0] = TowerData[x][y+1][1];
								TowerData[x-1][y+1][0] += healing;
								if(TowerData[x-1][y+1][0] > TowerData[x-1][y+1][1])
									TowerData[x-1][y+1][0] = TowerData[x-1][y+1][1];
								TowerData[x+1][y+1][0] += healing;
								if(TowerData[x+1][y+1][0] > TowerData[x+1][y+1][1])
									TowerData[x+1][y+1][0] = TowerData[x+1][y+1][1];
							}
							
							if (TowerLevel[x][y] == 3)
							{
								healing = (float) (15.0 / 200.0 * elapsedTime * TowerData[x][y][0] / 255.0); //Heals itself based on the percent of 4 #4 units damage
								TowerData[x][y][0] += healing;
								if(TowerData[x][y][0] > TowerData[x][y][1])
									TowerData[x][y][0] = TowerData[x][y][1];
							}
						}
					}
				}

				if (fireRangeCount != 0 && !fireSoundPlay && enableSound)
				{
					AudioService.PlayLoopedSound(SoundEffect.Fire_Active);
					fireSoundPlay = true;
					Log.d("Fire Sound","Start");
				}
				else if (fireRangeCount == 0 && fireSoundPlay && enableSound)
				{
					AudioService.StopSound(SoundEffect.Fire_Active);
					fireSoundPlay = false;
					Log.d("Fire Sound","Stop");
				}
				
				if (maxLevel != oldLevel)
				{
					FadeText tempText = new FadeText("Level Up", 300, 320, 100, -100, 1500, Color.WHITE, 100);
					if (enableSound)
						AudioService.PlaySound(SoundEffect.Level_Up);
					FadingText.add(tempText);
					oldLevel = maxLevel;
					if (health < maxHealth)
					{
						health += 15;
					}
					if (health > maxHealth)
					{
						health = maxHealth;
					}
				}				
				
				for (int i = 0; i < FadingText.size(); i++)
				{
					FadingText.get(i).Update(elapsedTime);
				}
				
				for (int i = 0; i < FadingBlocks.size(); i++)
				{
					FadingBlocks.get(i).Update(elapsedTime);
				}
				
				for (int i = (FadingText.size() - 1); i > -1 ; i-- )
				{	
					if (FadingText.get(i).Kill())
					{						
						FadingText.remove(i);
					}
				}
				
				for (int i = (FadingBlocks.size() - 1); i > -1 ; i-- )
				{	
					if (FadingBlocks.get(i).Kill())
					{						
						FadingBlocks.remove(i);
					}
				}
				
				for (int i = 0; i < FadingCircles.size(); i++)
				{
					FadingCircles.get(i).Update(elapsedTime);
				}
				
				for (int i = (FadingCircles.size() - 1); i > -1 ; i-- )
				{	
					if (FadingCircles.get(i).Kill())
					{						
						FadingCircles.remove(i);
					}
				}
					
				for (int i = 0; i < TowerEffects.size(); i++)
				{
					TowerEffects.get(i).Update(elapsedTime);
				}
				
				for (int i = (TowerEffects.size() - 1); i > -1 ; i-- )
				{	
					if (TowerEffects.get(i).Kill())
					{						
						TowerEffects.remove(i);
					}
				}	
				
				for (int i = 0; i < Shockwaves.size(); i++)
				{
					Shockwaves.get(i).Update(elapsedTime);
					for (int q = 0; q < Units.size(); q++)
					{
						float distance = SquareDistance(Units.get(q).getX()+16, Units.get(q).getY()+16, CannonCenterX, CannonCenterY);
						if (distance >= (Shockwaves.get(i).getRadius() - (Shockwaves.get(i).getRadiusDelta()*elapsedTime))*(Shockwaves.get(i).getRadius() - (Shockwaves.get(i).getRadiusDelta()*elapsedTime)) && distance <= Shockwaves.get(i).getRadius()*Shockwaves.get(i).getRadius())
						{
							Units.get(q).takeDamage(300);
							if (!Units.get(i).stillAlive())
							{
								int money = Units.get(i).getGold();
								gold += money;
								experiencePoints += money;
								if (money != 0)
								{
									FadeText tempText = new FadeText(Integer.toString(money), Units.get(i).getX(), Units.get(i).getY(), 0, -100, 500, Color.YELLOW, 25);
									FadingText.add(tempText);
									
									FadeBlock tempBlock = new FadeBlock(Units.get(i).getLevel(), Units.get(i).getX()+16, Units.get(i).getY()+16, 16, 32, 250);
									FadingBlocks.add(tempBlock);
									if (enableSound)
										AudioService.PlaySound(SoundEffect.EnemyDestroyed);
								}								
							}
						}
					}
				}
				
				for (int i = (Shockwaves.size() - 1); i > -1 ; i-- )
				{	
					if (Shockwaves.get(i).Kill())
					{						
						Shockwaves.remove(i);
					}
				}	
				
				if (health <= 0)
				{
					
					justPause = true;
					pauseGame = true;
					gameOver = true;
					ResetTowers();
					if (enableSound)
						AudioService.PlaySound(SoundEffect.GameOver);
					//health = maxHealth;
					if (fireSoundPlay)
					{
						fireSoundPlay = false;
						AudioService.StopSound(SoundEffect.Fire_Active);
						Log.d("Fire Sound","Stop");
					}
					//Force User to lift fingers
					for (int i = 0; i < fingerData.length; i++)
					{
						if (fingerData[i][2] == 1)
						{
							fingerIgnore[i] = true;
						}
					}
					
					if (experiencePoints > 50000)//if (experiencePoints > 54321)
					{
						if (HardModeEnabled == false)
						{
							HardModeEnabled = true;
							HardModeShowUpgrade = true;
						}
					}
				}
				
				//TODO: Full Tutorial Logic
				if (showTutorial)
				{
					if (health < maxHealth)
						health = maxHealth;
					if (gold <= 600)
						gold += 2000;
					for (int i = 0; i < fingerData.length; i++)
					{
						if (!fingerIgnore[i] && fingerData[i][2] == 1)
						{
							if (fingerData[i][0] > 0 && fingerData[i][0] < 80*2)
							{
								if (fingerData[i][1] > 0 && fingerData[i][1] < 67)
								{
									fingerIgnore[i] = true;
									tutorialIndex--;
								}
							}
							if (fingerData[i][0] > 880-80 && fingerData[i][0] < 960)
							{
								if (fingerData[i][1] > 0 && fingerData[i][1] < 67)
								{
									fingerIgnore[i] = true;
									tutorialIndex++;
								}
							}
							
							
							switch(tutorialIndex)
							{
							case 0:
//								if (fingerData[i][0] > 295 && fingerData[i][0] < 667)
//								{
//									if (fingerData[i][1] > 182 && fingerData[i][1] < 257)
//									{
//										fingerIgnore[i] = true;
//										tutorialIndex++;								
//									}
//								}
								break;
							case 1:
//								if (((fingerData[i][0] > 295 && fingerData[i][0] < 667) && (fingerData[i][1] > 132 && fingerData[i][1] < 207)) 
//										|| ((fingerData[i][0] > 295 && fingerData[i][0] < 667) && (fingerData[i][1] > 282 && fingerData[i][1] < 357)))
//								{
//									fingerIgnore[i] = true;
//									tutorialIndex++;	
//								}
								break;
							case 2:
//								if ((fingerData[i][0] > 15 && fingerData[i][0] < 350) && (fingerData[i][1] > 23 && fingerData[i][1] < 200)) 
//								{
//									fingerIgnore[i] = true;
//									tutorialIndex++;	
//								}
								
								if ((fingerData[i][0] > 15 && fingerData[i][0] < 350) && (fingerData[i][1] > 240+47 && fingerData[i][1] < 280+47)) 
								{
									if (slidingControlScheme)
									{
										slidingControlScheme = false;
									}
									else
									{
										slidingControlScheme = true;
									}
									
									if (slidingControlScheme)
									{
										SliderMin = 25;
										SliderMax = screenWidth/2 - 50;//screenWidth/2 - 25;
										SliderRect = new Rectangle((int) (SliderMin + ((SliderMin+SliderMax)/2) - 50), (int) (screenHeight - 50), (int) (75),(int)(50));
									}
									else
									{
										SliderMin = 177;
										SliderMax = 277;
										SliderRect = new Rectangle(202, 490-50, 75, 75);
									}
									
									fingerIgnore[i] = true;
								}
								break;
							case 3:
								if ((fingerData[i][0] > 15 && fingerData[i][0] < 350) && (fingerData[i][1] > 67 && fingerData[i][1] < 143)) 
								{
									int x = 20;
									int y = 13;
									Unit newUnit = new Unit(x, y, 1, 32, 32, 1);
									
									int goalX = newUnit.getParentX(DirectionMap[x][y]);
									int goalY = newUnit.getParentY(DirectionMap[x][y]);
									newUnit.setGoal(goalX, goalY);
									newUnit.setDelta();
									Units.add(newUnit);
									fingerIgnore[i] = true;
								}
								break;
							default:
								break;
							}
						}
					}		
					
					if (tutorialIndex >= tutorialCount)
					{
						showTutorial = false;
						for (int i = 0; i < fingerRegion.length; i++)
						{
							fingerRegion[i] = 2;
						}
						initVariables();
					}	
					if (tutorialIndex < 0)
					{
						tutorialIndex = 0;
					}
				}
			}
		}
	}

	public void pause() {
		justPause = true;
		pauseGame = true;
		

		SharedPreferences fileStore = this.getContext().getSharedPreferences("userData", 0);
		SharedPreferences.Editor editor = fileStore.edit();
		editor.putInt("highscore", highscore);
    	editor.putBoolean("controlScheme", slidingControlScheme);
    	editor.putBoolean("enableSound", enableSound);
    	editor.putBoolean("enableMusic", enableMusic);
    	editor.putBoolean("showTutorial", showTutorial);
    	editor.putBoolean("HardModeEnabled", HardModeEnabled);
    	//editor.putBoolean("HardModeActive", HardModeActive);
		editor.commit();
		
		AudioService.StopMusic();

		if (fireSoundPlay)
		{
			fireSoundPlay = false;
			AudioService.StopSound(SoundEffect.Fire_Active);
			Log.d("Fire Sound","Stop");
		}
	}

	public void resume(Context context) {
		//AudioService.StartMusic();
		//gold += 1000;
	}
	
	public void gainedFocus()
	{
		if (enableMusic)
			AudioService.StartMusic();
	}

	public void destroy() {
		thread.setRunning(false);
	
		
		if (thread != null)
		{
			Thread killThread = thread;
		    thread = null;
		    killThread.interrupt();
		}	
		
		BillingHelper.stopService();
	}
	
	public int[][] AStar(int[][] Board)
	{		
		//Initialize All Needed Lists
		int width = Board.length;
		int height = Board[0].length;
		int retVal[][] = new int[width][height];
		for (int i = 0; i < retVal.length; i++)
		{
			for (int j = 0; j < retVal[i].length; j++)
			{
				retVal[i][j] = Board[i][j];
			}
		}
		int goalX = 0;
		int goalY = 0;
		
		
		Node fieldArray[][] = new Node[width][height];
		
		for (int i = 0; i < fieldArray.length; i++)
		{
			for (int j = 0; j < fieldArray[i].length; j++)
			{
				fieldArray[i][j] = new Node(i, j);
				if (retVal[i][j] == 2)
				{
					fieldArray[i][j].setOpen(1);
					fieldArray[i][j].setDirection(10); //Set as target
					goalX = i;
					goalY = j;
				}
				if (retVal[i][j] == 1)
				{
					fieldArray[i][j].setOpen(0);
					fieldArray[i][j].setDirection(9); //Set as wall
				}
			}
		}
		
		//AStar Logic
		int openCount = 0;
		//Set initial open count;
		for (int i = 0; i < fieldArray.length; i++)
		{
			for (int j = 0; j < fieldArray[i].length; j++)
			{
				if (fieldArray[i][j].getOpen() == 1)
				{
					openCount++;
				}
			}
		}
		while (openCount!= 0)
		{
			//ASTAR
			for (int i = 0; i < fieldArray.length; i++)
			{
				for (int j = 0; j < fieldArray[i].length; j++)
				{
					if ((fieldArray[i][j].getOpen() == 1) && (fieldArray[i][j].getDirection() != 9))
					{
						//To the Right
						if (i < width - 1)
						{
							if (fieldArray[i+1][j].getOpen() == 2)
							{
								fieldArray[i+1][j].setOpen(1);
								fieldArray[i+1][j].setCost(fieldArray[i][j].getCost()+10);
								fieldArray[i+1][j].setParentX(i);
								fieldArray[i+1][j].setParentY(j);
							}
							else
							{
								if (fieldArray[i+1][j].getCost() > fieldArray[i][j].getCost()+10)
								{
									fieldArray[i+1][j].setCost(fieldArray[i][j].getCost()+10);
									fieldArray[i+1][j].setParentX(i);
									fieldArray[i+1][j].setParentY(j);
								}
							}
						}
						//To the Left
						if (i > 0)
						{
							if (fieldArray[i-1][j].getOpen() == 2)
							{
								fieldArray[i-1][j].setOpen(1);
								fieldArray[i-1][j].setCost(fieldArray[i][j].getCost()+10);
								fieldArray[i-1][j].setParentX(i);
								fieldArray[i-1][j].setParentY(j);
							}
							else
							{
								if (fieldArray[i-1][j].getCost() > fieldArray[i][j].getCost()+10)
								{
									fieldArray[i-1][j].setCost(fieldArray[i][j].getCost()+10);
									fieldArray[i-1][j].setParentX(i);
									fieldArray[i-1][j].setParentY(j);
								}
							}
						}
						
						
						//Down
						if (j < height - 1)
						{
							if (fieldArray[i][j+1].getOpen() == 2)
							{
								fieldArray[i][j+1].setOpen(1);
								fieldArray[i][j+1].setCost(fieldArray[i][j].getCost()+10);
								fieldArray[i][j+1].setParentX(i);
								fieldArray[i][j+1].setParentY(j);
							}
							else
							{
								if (fieldArray[i][j+1].getCost() > fieldArray[i][j].getCost()+10)
								{
									fieldArray[i][j+1].setCost(fieldArray[i][j].getCost()+10);
									fieldArray[i][j+1].setParentX(i);
									fieldArray[i][j+1].setParentY(j);
								}
							}
						}
						//Up
						if (j > 0)
						{
							if (fieldArray[i][j-1].getOpen() == 2)
							{
								fieldArray[i][j-1].setOpen(1);
								fieldArray[i][j-1].setCost(fieldArray[i][j].getCost()+10);
								fieldArray[i][j-1].setParentX(i);
								fieldArray[i][j-1].setParentY(j);
							}
							else
							{
								if (fieldArray[i][j-1].getCost() > fieldArray[i][j].getCost()+10)
								{
									fieldArray[i][j-1].setCost(fieldArray[i][j].getCost()+10);
									fieldArray[i][j-1].setParentX(i);
									fieldArray[i][j-1].setParentY(j);
								}
							}
						}
						
						//Diagonal Up Right
						if (j > 0 && (i < width - 1))
						{
							if ((fieldArray[i][j-1].getDirection() != 9) && (fieldArray[i+1][j].getDirection() != 9))
							{
								if (fieldArray[i+1][j-1].getOpen() == 2)
								{
									fieldArray[i+1][j-1].setOpen(1);
									fieldArray[i+1][j-1].setCost(fieldArray[i][j].getCost()+14);
									fieldArray[i+1][j-1].setParentX(i);
									fieldArray[i+1][j-1].setParentY(j);
								}
								else
								{
									if (fieldArray[i+1][j-1].getCost() > fieldArray[i][j].getCost()+14)
									{
										fieldArray[i+1][j-1].setCost(fieldArray[i][j].getCost()+14);
										fieldArray[i+1][j-1].setParentX(i);
										fieldArray[i+1][j-1].setParentY(j);
									}
								}
							}
						}
						
						//Diagonal Up Left
						if (j > 0 && (i > 0))
						{
							if ((fieldArray[i-1][j].getDirection() != 9) && (fieldArray[i][j-1].getDirection() != 9))
							{
								if (fieldArray[i-1][j-1].getOpen() == 2)
								{
									fieldArray[i-1][j-1].setOpen(1);
									fieldArray[i-1][j-1].setCost(fieldArray[i][j].getCost()+14);
									fieldArray[i-1][j-1].setParentX(i);
									fieldArray[i-1][j-1].setParentY(j);
								}
								else
								{
									if (fieldArray[i-1][j-1].getCost() > fieldArray[i][j].getCost()+14)
									{
										fieldArray[i-1][j-1].setCost(fieldArray[i][j].getCost()+14);
										fieldArray[i-1][j-1].setParentX(i);
										fieldArray[i-1][j-1].setParentY(j);
									}
								}
							}
						}
						
						//Diagonal Down Left
						if ((j < height - 1) && (i > 0))
						{
							if ((fieldArray[i-1][j].getDirection() != 9) && (fieldArray[i][j+1].getDirection() != 9))
							{
								if (fieldArray[i-1][j+1].getOpen() == 2)
								{
									fieldArray[i-1][j+1].setOpen(1);
									fieldArray[i-1][j+1].setCost(fieldArray[i][j].getCost()+14);
									fieldArray[i-1][j+1].setParentX(i);
									fieldArray[i-1][j+1].setParentY(j);
								}
								else
								{
									if (fieldArray[i-1][j+1].getCost() > fieldArray[i][j].getCost()+14)
									{
										fieldArray[i-1][j+1].setCost(fieldArray[i][j].getCost()+14);
										fieldArray[i-1][j+1].setParentX(i);
										fieldArray[i-1][j+1].setParentY(j);
									}
								}
							}
						}
						
						//Diagonal Down Right
						if ((j < height - 1) && (i < width - 1))
						{
							if ((fieldArray[i+1][j].getDirection() != 9) && (fieldArray[i][j+1].getDirection() != 9))
							{
								if (fieldArray[i+1][j+1].getOpen() == 2)
								{
									fieldArray[i+1][j+1].setOpen(1);
									fieldArray[i+1][j+1].setCost(fieldArray[i][j].getCost()+14);
									fieldArray[i+1][j+1].setParentX(i);
									fieldArray[i+1][j+1].setParentY(j);
								}
								else
								{
									if (fieldArray[i+1][j+1].getCost() > fieldArray[i][j].getCost()+14)
									{
										fieldArray[i+1][j+1].setCost(fieldArray[i][j].getCost()+14);
										fieldArray[i+1][j+1].setParentX(i);
										fieldArray[i+1][j+1].setParentY(j);
									}
								}
							}
						}
						
						fieldArray[i][j].setOpen(0);
					}
				}
			}
			
			
			openCount = 0;
			//Set initial open count;
			for (int i = 0; i < fieldArray.length; i++)
			{
				for (int j = 0; j < fieldArray[i].length; j++)
				{
					if (fieldArray[i][j].getOpen() == 1)
					{
						openCount++;
					}
				}
			}
		}
		
	
		
		
		//Fill in any missing values
		for (int i = 0; i < fieldArray.length; i++)
		{
			for (int j = 0; j < fieldArray[i].length; j++)
			{
				if ((fieldArray[i][j].getParentX() != -1) && (fieldArray[i][j].getParentY() != -1))
				{
					if ((fieldArray[i][j].getParentX() == (i-1)) && (fieldArray[i][j].getParentY() == (j)))
						fieldArray[i][j].setDirection(7);
					if ((fieldArray[i][j].getParentX() == (i+1)) && (fieldArray[i][j].getParentY() == (j)))
						fieldArray[i][j].setDirection(3);
					if ((fieldArray[i][j].getParentX() == (i)) && (fieldArray[i][j].getParentY() == (j+1)))
						fieldArray[i][j].setDirection(5);
					if ((fieldArray[i][j].getParentX() == (i)) && (fieldArray[i][j].getParentY() == (j-1)))
						fieldArray[i][j].setDirection(1);
					if ((fieldArray[i][j].getParentX() == (i+1)) && (fieldArray[i][j].getParentY() == (j-1)))
						fieldArray[i][j].setDirection(2);
					if ((fieldArray[i][j].getParentX() == (i+1)) && (fieldArray[i][j].getParentY() == (j+1)))
						fieldArray[i][j].setDirection(4);
					if ((fieldArray[i][j].getParentX() == (i-1)) && (fieldArray[i][j].getParentY() == (j+1)))
						fieldArray[i][j].setDirection(6);
					if ((fieldArray[i][j].getParentX() == (i-1)) && (fieldArray[i][j].getParentY() == (j-1)))
						fieldArray[i][j].setDirection(8);
				}
				if (fieldArray[i][j].getDirection() == 0)
				{				
					if (i > goalX && j == goalY)
					{
						fieldArray[i][j].setDirection(7);
					}
					if (i < goalX && j == goalY)
					{
						fieldArray[i][j].setDirection(3);
					}
					if (i == goalX && j > goalY)
					{
						fieldArray[i][j].setDirection(1);
					}
					if (i > goalX && j > goalY)
					{
						fieldArray[i][j].setDirection(8);
					}
					if (i < goalX && j > goalY)
					{
						fieldArray[i][j].setDirection(2);
					}
					
					//Hotfix for weird lose top row thing.
					if (i < goalX && j == 0)
					{
						fieldArray[i][j].setDirection(3);
					}
					
					if (i > goalX && j == 0)
					{
						fieldArray[i][j].setDirection(7);
					}
					
				}
			}
		}
		
		for (int i = 0; i < fieldArray.length; i++)
		{
			for (int j = 0; j < fieldArray[i].length; j++)
			{
				retVal[i][j] = fieldArray[i][j].getDirection();
				if (fieldArray[i][j].getCost() != -1)
				{
					CostArray[i][j] = fieldArray[i][j].getCost();
				}
				else
				{
					if (LastCostArray[i][j] != -1)
						CostArray[i][j] = LastCostArray[i][j];
					else
					{
						CostArray[i][j] = 0;
					}
				}		
			}
		}
		
		LastCostArray = CostArray;
		
		return retVal;
	}
	
	public void SetFingerRects()
	{
		for (int i = 0; i<fingerRects.length; i++)
			fingerRects[i].set((int) fingerData[i][0] - 20, (int)fingerData[i][1] - 20, (int)fingerData[i][0]+20, (int)fingerData[i][1]+20);
	}
	
	public void SetFingerRegions()
	{
		for (int i = 0; i < fingerState.length; i++)
		{
			if (fingerState[i] != fingerData[i][2] && fingerIgnore[i] == false)
			{
				if (fingerData[i][1] < screenHeight - 100) // Above control panel
				{
					fingerRegion[i] = 1;
				}
				else 
				{
					fingerRegion[i] = 2;
				}
				
				int x = (int)(fingerData[i][0] / (32));
				int y = (int)(fingerData[i][1] / (32));
				if (x < 30 && y < 14)
				{
					if ((x != 0) && (x != 29) && (y != 13))
					{
						if (GameBoard[x][y] == 2)
							fingerCentralTower[i] = true;
						else
							fingerCentralTower[i] = false;
					}
				}
			}
			
			fingerState[i] = (int)fingerData[i][2];
		}
	}
	
	public void SpawnEnemies(float elapsedTime)
	{
	
		//spawnCounter += elapsedTime/20.0;
		if (EasyModeActive)
			spawnCounter += elapsedTime/40.0;
		else
			spawnCounter += elapsedTime/20.0;
		
		if (initSpawnTime > 50)
		{
			//initSpawnCounter += elapsedTime/20;
			if (EasyModeActive)
				initSpawnCounter += elapsedTime/40.0;
			else
				initSpawnCounter += elapsedTime/20.0;
			
			
			if (initSpawnCounter >= initSpawnTime)
			{
				allowSpawn = true;
				initSpawnCounter = 0;
				initSpawnTime = (int)(((float)initSpawnTime) / 2);
			}
		}
		else
		{
			allowSpawn = true;
		}
		
		maxLevel = (int) (((float)experiencePoints) / 1000);
		maxLevel += 1;
		
		if (
				((spawnCounter > 113 - maxLevel*2) && (maxLevel <= 25)) || 
				(spawnCounter > 60 && maxLevel > 25 && maxLevel <= 40) ||
				(spawnCounter > 60 - (maxLevel-40)*3 && maxLevel > 40) ||
				maxLevel == 45 && spawnCounter > 15 ||
				maxLevel == 55 && spawnCounter > 15 ||
				maxLevel == 65 && spawnCounter > 15 ||
				maxLevel == 75 && spawnCounter > 15 ||
				maxLevel == 85 && spawnCounter > 15 ||
				maxLevel == 95 && spawnCounter > 15 ||
				maxLevel > 100
				)
		{
			if (allowSpawn)
			{
				Random randomGenerator = new Random();
				int number = randomGenerator.nextInt(58);
				
				int level = randomGenerator.nextInt(maxLevel + 1);
				//TODO: SpawnEnemies -- Set specific spawn level
				//level = 4;
				
				if (level > 6)
				{
					level = randomGenerator.nextInt(4) + 3;
				}
				if (level == 0)
				{
					level = 1;
				}
				if (number < 30)
				{
					//Add Unit
					int x = number;
					int y = 13;
					Unit newUnit = new Unit(x, y, level, 32, 32, maxLevel);
					
					int goalX = newUnit.getParentX(DirectionMap[x][y]);
					int goalY = newUnit.getParentY(DirectionMap[x][y]);
					newUnit.setGoal(goalX, goalY);
					newUnit.setDelta();
					Units.add(newUnit);
				}
				else
				{
					number = number - 30;
					if (number < 14)
					{
						int x = 29;
						int y = number;
						Unit newUnit = new Unit(x, y, level, 32, 32, maxLevel);
						
						int goalX = newUnit.getParentX(DirectionMap[x][y]);
						int goalY = newUnit.getParentY(DirectionMap[x][y]);
						newUnit.setGoal(goalX, goalY);
						newUnit.setDelta();
						Units.add(newUnit);
					}
					else
					{
						number = number - 14;
						int x = 0;
						int y = number;
						Unit newUnit = new Unit(x, y, level, 32, 32, maxLevel);
						
						int goalX = newUnit.getParentX(DirectionMap[x][y]);
						int goalY = newUnit.getParentY(DirectionMap[x][y]);
						newUnit.setGoal(goalX, goalY);
						newUnit.setDelta();
						Units.add(newUnit);
					}
				}
				spawnCounter = 0;
				if (initSpawnCounter < initSpawnTime)
					allowSpawn = false;
			}
		}
	}
		
	public void UpdateControls(float elapsedTime)
	{
		boolean updateImageArray = false;
		
		
		for (int i = 0; i < fingerRects.length; i++)
		{
			if (fingerRegion[i] == 2)
			{
				if (ShooterButtonRect.touchFinger() != i && FireButtonRect.touchFinger() != i && SliderRect.touchFinger() != i && IceButtonRect.touchFinger() != i && HealButtonRect.touchFinger() != i)
				{
					if (!fingerIgnore[i])
					{
						
						
						if (!FireButtonRect.touched() && (!showTutorial || (showTutorial && tutorialIndex > 4)))
						{
							if ((int)(fingerData[i][2]) == 1)
							{
								if (FireButtonRect.collides(fingerRects[i]))
								{
									FireButtonRect.setTouched(i);		
									FireButtonRect.setX((int) fingerData[i][0] - FireButtonRect.getWidth()/2);
									FireButtonRect.setY((int) fingerData[i][1] - FireButtonRect.getHeight()/2);
									
									FireButtonRect.setOffset((int) fingerData[i][0], (int) fingerData[i][1]);		
									break;
								}
							}
							
						}
						
						if (!IceButtonRect.touched() && (!showTutorial || (showTutorial && tutorialIndex > 4)))
						{
							if ((int)(fingerData[i][2]) == 1)
							{
								if (IceButtonRect.collides(fingerRects[i]))
								{
									IceButtonRect.setTouched(i);
									IceButtonRect.setX((int) fingerData[i][0] - IceButtonRect.getWidth()/2);
									IceButtonRect.setY((int) fingerData[i][1] - IceButtonRect.getHeight()/2);
									
									IceButtonRect.setOffset((int) fingerData[i][0], (int) fingerData[i][1]);
									break;
								}
							}
							
						}
						
						if (!HealButtonRect.touched() && (!showTutorial || (showTutorial && tutorialIndex > 4)))
						{
							if ((int)(fingerData[i][2]) == 1)
							{
								if (HealButtonRect.collides(fingerRects[i]))
								{
									HealButtonRect.setTouched(i);		
									HealButtonRect.setX((int) fingerData[i][0] - HealButtonRect.getWidth()/2);
									HealButtonRect.setY((int) fingerData[i][1] - HealButtonRect.getHeight()/2);
									
									HealButtonRect.setOffset((int) fingerData[i][0], (int) fingerData[i][1]);
									break;
								}
							}
							
						}
						
						if (!SliderRect.touched() && (!showTutorial || (showTutorial && tutorialIndex > 1)))
						{
							if ((int)(fingerData[i][2]) == 1)
							{
								if (SliderRect.collides(fingerRects[i]))
								{
									SliderRect.setTouched(i);
									SliderRect.setOffset((int) fingerData[i][0], (int) fingerData[i][1]);	
									break;
								}
							}
						}
			
						if (!ShooterButtonRect.touched() && (!showTutorial || (showTutorial && tutorialIndex > 1)))
						{
						if ((int)(fingerData[i][2]) == 1)
						{
							if (ShooterButtonRect.collides(fingerRects[i]))
							{
								ShooterButtonRect.setTouched(i);
								PrepBullet = 1;	
								CrossHairs.setX((float) (CannonCenterX + 2*((Speed)*Math.cos(Math.PI * Angle / 180)*(-1))));
								CrossHairs.setY((float) (CannonCenterY + 2*((Speed)*Math.sin(Math.PI * Angle / 180))));
								break;
							}
						}
					
					}
				
					}
				}
			}
		}
		
		
		if (SliderRect.touched())
		{
			if (fingerData[SliderRect.touchFinger()][2] == 0)
			{
				SliderRect.liftFinger();
				if (!slidingControlScheme)
				{
					SliderRect.setX(202);
					SliderRect.setY(490-50);
				}
			}
			else
			{
				SliderRect.dragX((int) fingerData[SliderRect.touchFinger()][0]);	
				if (!slidingControlScheme)
				{
					SliderRect.dragY((int) fingerData[SliderRect.touchFinger()][1]);
				}
			}
		}	
		
		if (ShooterButtonRect.touched())
		{
			if (fingerData[ShooterButtonRect.touchFinger()][2] == 0)
			{
				ShooterButtonRect.liftFinger();
			}
		}
				
		if (FireButtonRect.touched())
		{
			if (fingerData[FireButtonRect.touchFinger()][2] == 0)
			{
				int x = (int)(fingerData[FireButtonRect.touchFinger()][0] / (32));
				int y = (int)(fingerData[FireButtonRect.touchFinger()][1] / (32));
				if (x < 30 && y < 14)
				{
					if ((x != 0) && (x != 29) && (y != 13))
					{
						if (GameBoard[x][y] != 2 && GameBoard[x][y] == 1)
						{
							if (TowerData[x][y][2] != 1)
							{
								if (gold >= 200)
								{
									TowerData[x][y][2] = 1;
									TowerLevel[x][y] = 1;
									gold -= 200;
									updateImageArray = true;
									if (enableSound)
										AudioService.PlaySound(SoundEffect.TowerFire);
								}
							}
							else 
							{
								if (gold >= (TowerLevel[x][y]+1)*200)
								{
									TowerLevel[x][y] += 1;
									
									if (TowerLevel[x][y] > 3)
										TowerLevel[x][y] = 3;
									else
									{
										if (enableSound)
											AudioService.PlaySound(SoundEffect.TowerFire);
										gold -= TowerLevel[x][y]*200;
										updateImageArray = true;
									}
								}
							}
						}
						else
						{
							if (gold >= 1000 && towerPower != 1 && GameBoard[x][y] == 2)
							{
								gold -= 1000;
								towerPower = 1;
								if (enableSound)
									AudioService.PlaySound(SoundEffect.TowerFire);
								if (health > 200)
									health = 200;
								maxHealth = 200;
							}
						}
					}
				}
				
				FireButtonRect.liftFinger();
				FireButtonRect.setX((int)(screenWidth - 365));
				FireButtonRect.setY((int)(screenHeight - 100));
				FireButtonIn.setX((int)(screenWidth - 365));
				FireButtonIn.setY((int)(screenHeight - 100));
				FireButtonOut.setX((int)(screenWidth - 365));
				FireButtonOut.setY((int)(screenHeight - 100));
				
				
			}
			else
			{
				FireButtonRect.dragX((int) fingerData[FireButtonRect.touchFinger()][0]);
				FireButtonRect.dragY((int) fingerData[FireButtonRect.touchFinger()][1]);
				FireButtonIn.setX((float) (FireButtonRect.getX() - 0));
				FireButtonIn.setY((float) (FireButtonRect.getY() - 0));
				FireButtonOut.setX((float) (FireButtonRect.getX() - 0));
				FireButtonOut.setY((float) (FireButtonRect.getY() - 0));
			}
		}
		
		if (HealButtonRect.touched())
		{
			if (fingerData[HealButtonRect.touchFinger()][2] == 0)
			{
				int x = (int)(fingerData[HealButtonRect.touchFinger()][0] / (32));
				int y = (int)(fingerData[HealButtonRect.touchFinger()][1] / (32));
				if (x < 30 && y < 14)
				{
					if ((x != 0) && (x != 29) && (y != 13))
					{
						if (GameBoard[x][y] != 2 && GameBoard[x][y] == 1)
						{
							if (TowerData[x][y][2] != 3)
							{
								if (gold >= 200)
								{
									TowerData[x][y][2] = 3;
									TowerLevel[x][y] = 1;
									gold -= 200;
									updateImageArray = true;
									if (enableSound)
										AudioService.PlaySound(SoundEffect.TowerHeal);
								}
							}
							else 
							{
								if (gold >= (TowerLevel[x][y]+1)*200)
								{
									TowerLevel[x][y] += 1;
									if (TowerLevel[x][y] > 3)
										TowerLevel[x][y] = 3;
									else
									{
										gold -= TowerLevel[x][y]*200;
										updateImageArray = true;
										if (enableSound)
											AudioService.PlaySound(SoundEffect.TowerHeal);
									}
								}
							}
						}
						else
						{
							if (gold >= 1000 && towerPower != 3 && GameBoard[x][y] == 2)
							{
								gold -= 1000;
								towerPower = 3;
								maxHealth = 400;
								health += 200;
								if (enableSound)
									AudioService.PlaySound(SoundEffect.TowerHeal);
							}
						}
					}
				}
				
				HealButtonRect.liftFinger();
				HealButtonRect.setX((int)(screenWidth - 480));
				HealButtonRect.setY((int)(screenHeight - 100));
				HealButtonIn.setX((int)(screenWidth - 480));
				HealButtonIn.setY((int)(screenHeight - 100));
				HealButtonOut.setX((int)(screenWidth - 480));
				HealButtonOut.setY((int)(screenHeight - 100));
				
				
			}
			else
			{
				HealButtonRect.dragX((int) fingerData[HealButtonRect.touchFinger()][0]);
				HealButtonRect.dragY((int) fingerData[HealButtonRect.touchFinger()][1]);
				HealButtonIn.setX((float) (HealButtonRect.getX() - 0));
				HealButtonIn.setY((float) (HealButtonRect.getY() - 0));
				HealButtonOut.setX((float) (HealButtonRect.getX() - 0));
				HealButtonOut.setY((float) (HealButtonRect.getY() - 0));
			}
		}
		
		if (IceButtonRect.touched())
		{
			if (fingerData[IceButtonRect.touchFinger()][2] == 0)
			{
				int x = (int)(fingerData[IceButtonRect.touchFinger()][0] / (32));
				int y = (int)(fingerData[IceButtonRect.touchFinger()][1] / (32));
				if (x < 30 && y < 14)
				{
					if ((x != 0) && (x != 29) && (y != 13))
					{
						if (GameBoard[x][y] != 2 && GameBoard[x][y] == 1)
						{
							if (TowerData[x][y][2] != 2)
							{
								if (gold >= 200)
								{
									TowerData[x][y][2] = 2;
									TowerLevel[x][y] = 1;
									gold -= 200;
									updateImageArray = true;
									if (enableSound)
										AudioService.PlaySound(SoundEffect.TowerIce);
								}
							}
							else 
							{
								if (gold >= (TowerLevel[x][y]+1)*200)
								{
									TowerLevel[x][y] += 1;
									if (TowerLevel[x][y] > 3)
										TowerLevel[x][y] = 3;
									else
									{
										gold -= TowerLevel[x][y]*200;
										updateImageArray = true;
										if (enableSound)
											AudioService.PlaySound(SoundEffect.TowerIce);
									}
								}
							}
						}
						else
						{
							if (gold >= 1000 && towerPower != 2 && GameBoard[x][y] == 2)
							{
								gold -= 1000;
								towerPower = 2;
								if (enableSound)
									AudioService.PlaySound(SoundEffect.TowerIce);
								if (health > 200)
									health = 200;
								maxHealth = 200;
							}
						}
					}
				}
				
				IceButtonRect.liftFinger();
				IceButtonRect.setX((int)(screenWidth - 250));
				IceButtonRect.setY((int)(screenHeight - 100));
				IceButtonIn.setX((int)(screenWidth - 250));
				IceButtonIn.setY((int)(screenHeight - 100));
				IceButtonOut.setX((int)(screenWidth - 250));
				IceButtonOut.setY((int)(screenHeight - 100));
			}
			else
			{
				IceButtonRect.dragX((int) fingerData[IceButtonRect.touchFinger()][0]);
				IceButtonRect.dragY((int) fingerData[IceButtonRect.touchFinger()][1]);
				IceButtonIn.setX((float) (IceButtonRect.getX() - 0));
				IceButtonIn.setY((float) (IceButtonRect.getY() - 0));
				IceButtonOut.setX((float) (IceButtonRect.getX() - 0));
				IceButtonOut.setY((float) (IceButtonRect.getY() - 0));
			}
		}
		
		
		
		if (SliderRect.getX()+25 < SliderMin)
		{
			SliderRect.setX((int) SliderMin-25);
		}
		if (SliderRect.getX()+25 > SliderMax)
		{
			SliderRect.setX((int) SliderMax-25);
		}
		
		if (slidingControlScheme)
		{
			Angle = 180 * (SliderRect.getX()-SliderMin+25) / (SliderMax - SliderMin);
		}
		else
		{
			if (SliderRect.getY() < 440-50)
			{
				SliderRect.setY(440-50);
			}
			if (SliderRect.getY() > 540-50)
			{
				SliderRect.setY(540-50);
			}
			
			if (ShooterButtonRect.touched())
			{
				if (SliderRect.getX() > 202) // Right
				{
					//Angle += (elapsedTime / 40);
					CrossHairs.setX(CrossHairs.getX()+elapsedTime/20);
					
					if (SliderRect.getX() > 212)
					{
						//Angle += (elapsedTime / 40);
						CrossHairs.setX(CrossHairs.getX()+elapsedTime/20);
					}
					if (SliderRect.getX() > 222)
					{
						//Angle += (elapsedTime / 40);
						CrossHairs.setX(CrossHairs.getX()+elapsedTime/20);
					}
					if (SliderRect.getX() > 232)
					{
						//Angle += (elapsedTime / 40);
						CrossHairs.setX(CrossHairs.getX()+elapsedTime/20);
					}
					if (SliderRect.getX() > 242)
					{
						//Angle += (elapsedTime / 20);
						CrossHairs.setX(CrossHairs.getX()+elapsedTime/20);
					}
				}
				else if (SliderRect.getX() < 202) // Left
				{
					//Angle -= (elapsedTime / 40);
					CrossHairs.setX(CrossHairs.getX()-elapsedTime/20);
					
					if (SliderRect.getX() < 192)
					{
						//Angle -= (elapsedTime / 40);
						CrossHairs.setX(CrossHairs.getX()-elapsedTime/20);
					}
					if (SliderRect.getX() < 182)
					{
						//Angle -= (elapsedTime / 40);
						CrossHairs.setX(CrossHairs.getX()-elapsedTime/20);
					}
					if (SliderRect.getX() < 172)
					{
						//Angle -= (elapsedTime / 40);
						CrossHairs.setX(CrossHairs.getX()-elapsedTime/20);
					}
					if (SliderRect.getX() < 162)
					{
						//Angle -= (elapsedTime / 20);
						CrossHairs.setX(CrossHairs.getX()-elapsedTime/20);
					}
				}
				
//				if (Angle > 180)
//				{
//					Angle = 180;
//				}
//				if (Angle < 0)
//				{
//					Angle = 0;
//				}			
			
				
				
				if (SliderRect.getY() < 490-50)
				{
					//CrossHairMag -= (elapsedTime / 40);
					CrossHairs.setY(CrossHairs.getY()-elapsedTime/20);
					
					if (SliderRect.getY() < 480-50)
					{
						//CrossHairMag -= (elapsedTime / 40);
						CrossHairs.setY(CrossHairs.getY()-elapsedTime/20);
					}
					if (SliderRect.getY() < 470-50)
					{
						//CrossHairMag -= (elapsedTime / 40);
						CrossHairs.setY(CrossHairs.getY()-elapsedTime/20);
					}
					if (SliderRect.getY() < 460-50)
					{
						//CrossHairMag -= (elapsedTime / 40);
						CrossHairs.setY(CrossHairs.getY()-elapsedTime/20);
					}
					if (SliderRect.getY() < 450-50)
					{
						//CrossHairMag -= (elapsedTime / 20);
						CrossHairs.setY(CrossHairs.getY()-elapsedTime/20);
					}
				}
				else if (SliderRect.getY() > 490-50)
				{
					//CrossHairMag += (elapsedTime / 40);
					CrossHairs.setY(CrossHairs.getY()+elapsedTime/20);
					
					if (SliderRect.getY() > 500-50)
					{
						//CrossHairMag += (elapsedTime / 40);
						CrossHairs.setY(CrossHairs.getY()+elapsedTime/20);
					}
					if (SliderRect.getY() > 510-50)
					{
						//CrossHairMag += (elapsedTime / 40);
						CrossHairs.setY(CrossHairs.getY()+elapsedTime/20);
					}
					if (SliderRect.getY() > 520-50)
					{
						//CrossHairMag += (elapsedTime / 40);
						CrossHairs.setY(CrossHairs.getY()+elapsedTime/20);
					}
					if (SliderRect.getY() > 530-50)
					{
						//CrossHairMag += (elapsedTime / 20);
						CrossHairs.setY(CrossHairs.getY()+elapsedTime/20);
					}
				}
			}
			
			Angle = (float) Math.atan2(CrossHairs.getY()-CannonCenterY, CrossHairs.getX()-CannonCenterX);
			Angle = -(float) Math.toDegrees(Angle)+180;
		}
		CannonEndX = (float) ((Speed)*Math.cos(Math.PI * Angle / 180)*(-1));
		CannonEndY = (float) ((Speed)*Math.sin(Math.PI * Angle / 180));
		Tower.setAngle(360-Angle);
				
		if (ShooterButtonRect.touched())
		{		
			if (slidingControlScheme)
			{
				CrossHairs.setX((float) (CrossHairMag*Math.cos(Math.PI * Angle / 180)*(-1)) + (CannonCenterX));
				CrossHairs.setY((float) (CrossHairMag*Math.sin(Math.PI * Angle / 180)) + (CannonCenterY));
			}
			
			if (CrossHairMag < 100)
			{
				CrossHairDelta = (float) 3;
			}
			if (CrossHairs.getX() < 0) 
			{
				CrossHairs.setX(0);
				CrossHairDelta = (float) -3;
			}
			if (CrossHairs.getX() > screenWidth) 
			{
				CrossHairs.setX(screenWidth);
				CrossHairDelta = (float) -3;
			}
			if (CrossHairs.getY() > (ControlBackground.getY()))
			{
				CrossHairs.setY(ControlBackground.getY());
				CrossHairDelta = (float) -3;
			}
			if (CrossHairs.getY() < 0) 
			{
				CrossHairs.setY(0);
				CrossHairDelta = (float) -3; //Doesn't matter.  Only hit in joystick mode.
			}
			
			if (slidingControlScheme)
				CrossHairMag += CrossHairDelta * elapsedTime / 20; //The 20 is roughly where my phone runs at.  By doing this, I add in elapsed time without changing the delta.
			//Removed ^^^^ elapsedTime stuff because it added lag. 
		}
		else
		{
			if (PrepBullet == 1)
			{
				//That velocity should go the diagonal length of the screen in a little over a two seconds. 
				AssetManager assetMgr = gameContext.getAssets();
				Bullet newBullet = new Bullet((int) CannonCenterX, 
						(int) CannonCenterY, 
						(float) (CrossHairs.getX() - CannonCenterX), 
						(float) (CrossHairs.getY() - CannonCenterY), 
						(float) .48953, //Same as below value
						//(float) (Math.pow((screenWidth)*(screenWidth)+(screenHeight)*(screenHeight), .5)/2250), 
						"SmallBullet",
						Angle, 
						assetMgr);
				Bullets.add(newBullet);
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Bullet_Shoot);
				FadeCircle circ = new FadeCircle(4, CannonCenterX, CannonCenterY, 50, 60, 400);
				FadingCircles.add(circ);
				PrepBullet = 0;
			}
			
			CrossHairMag = 70;
			CrossHairDelta = 3;
		}
		ShooterButtonRect.setX(ShooterButtonRect.getX());
		FireButtonRect.setX(FireButtonRect.getX());
		IceButtonRect.setX(IceButtonRect.getX());
		HealButtonRect.setX(HealButtonRect.getX());
		Slider.setX((float) (SliderRect.getX() - 5.5+25));
		Slider.setY((float) SliderRect.getY());
		if (updateImageArray)
			UpdateTowerWallMap();
	}

	
	
	public void UpdateTowers(float elapsedTime)
	{
		for (int i = 0; i < fingerRegion.length; i++)
		{
			if (fingerData[i][1] < screenHeight - 100) // Above control panel
			{
				if (fingerRegion[i] == 1)
				{
					if (fingerData[i][2] == 1)
					{
						//This means it is in the top, is touching the screen, and started on top
						
						int x = (int)(fingerData[i][0] / (32));
						int y = (int)(fingerData[i][1] / (32));
						if (x < 30 && y < 14)
						{
							if ((x != 0) && (x != 29) && (y != 13))
							{
								if (TowerData[x][y][0] < 255 && GameBoard[x][y] != 2)
								{
									if (gold >= elapsedTime/10)
									{
										if (TowerData[x][y][0] < TowerData[x][y][1])
										{
											TowerData[x][y][0] += elapsedTime/10;
											gold -= elapsedTime/10;
										}
										else
										{
											TowerData[x][y][1] += elapsedTime/10;
											TowerData[x][y][0] += elapsedTime/10;
											gold -= elapsedTime/10;
										}
										
										if (TowerData[x][y][1] > 255)
											TowerData[x][y][1] = 255;
										if (TowerData[x][y][0] > 255)
											TowerData[x][y][0] = 255;
									}
								}
								
								if (GameBoard[x][y] == 2 && fingerCentralTower[i] && gold >= ShockwaveCost)
								{
									Shockwave shock = new Shockwave(CannonCenterX , CannonCenterY, 1, 700, 500, Color.DKGRAY);
									if (enableSound)
										AudioService.PlaySound(SoundEffect.Shockwave);
									Shockwaves.add(shock);
									fingerIgnore[i] = true;
									fingerRegion[i] = 2;
									gold -= 1000;
									ShockwaveCost += 100;
								}
							}
						}
					}
				}
			}
		}				
		
		int k = 0;
		for (int i = 0; i < TowerData.length; i++)
		{
			for (int j = 0; j < TowerData[i].length; j++)
			{
				if (TowerData[i][j][1] < 50 && TowerData[i][j][1] != 0)
				{
					TowerData[i][j][0] -= elapsedTime/100;
				}
				if (TowerData[i][j][0] < 0)
				{
					TowerData[i][j][0] = 0;
					TowerData[i][j][1] = 0;
					TowerData[i][j][2] = 0;
					TowerLevel[i][j] = 0;
					GameBoard[i][j] = 0;
					k++;
				}
			}
		}
		if (k != 0)
		{
			DirectionMap = AStar(GameBoard);
			UpdateTowerWallMap();
		}
	}

	public void DrawTowers()
	{		
		int k = 0;
		for (int i = 0; i< fingerData.length; i++)
		{
			if (fingerData[i][2] == 1)
			{
				if (fingerRegion[i] == 1 && !fingerIgnore[i])
				{
					int x = (int)(fingerData[i][0] / (32));
					int y = (int)(fingerData[i][1] / (32));
					if (showTutorial && y < 3)
						break;
					if (x < 30 && y < 14)
					{
						if ((x != 0) && (x != 29) && (y != 13))
						{
							int badCount = 0; //An attempt to keep you from building towers on blocks.
							
							for (int j = 0; j < Units.size(); j++)
							{
								if ((Units.get(j).getGoalX() == x) && (Units.get(j).getGoalY() == y))
								{
									badCount++;
								}
								if ((Units.get(j).getNodeX() == x) && (Units.get(j).getNodeY() == y))
								{
									badCount++;
								}
							}
							if (badCount == 0)
							{
								if (GameBoard[x][y] != 2 && GameBoard[x][y] != 1) //This was added because of the initial cost
								{
									if (gold > 10)
									{
										GameBoard[x][y] = 1;
										//Game Board Changed, update Direction Map.

										//This keeps you from moving so quickly that nothing gets drawn. 
										//Also prevents towers from never decaying.
										TowerData[x][y][0] = 10;
										TowerData[x][y][1] = 10;
										gold -= 10;
										k++;
									}
								}
							}
						}
						else
						{
							//Add Unit
							
							//In place for testing
							/*
							Unit newUnit = new Unit(x, y, 1, 32, 32);
							
							int goalX = newUnit.getParentX(DirectionMap[x][y]);
							int goalY = newUnit.getParentY(DirectionMap[x][y]);
							newUnit.setGoal(goalX, goalY);
							newUnit.setDelta();
							Units.add(newUnit);
							*/
						}
					}
				}
			}
		}
		
		if (k != 0)
		{
			DirectionMap = AStar(GameBoard);
			UpdateTowerWallMap();
		}
	}
	
	public void UpdateUnits(float elapsedTime)
	{
		for (int i = 0; i < Units.size(); i++)
		{
			int goalX = Units.get(i).getParentX(DirectionMap[Units.get(i).getNodeX()][Units.get(i).getNodeY()]);
			int goalY = Units.get(i).getParentY(DirectionMap[Units.get(i).getNodeX()][Units.get(i).getNodeY()]);
						
			Units.get(i).Update(goalX, goalY, elapsedTime);
			
			if (Units.get(i).getLevel() == 4)
			{
				for (int x = 0; x < TowerData.length; x++)
				{
					for (int y = 0; y < TowerData[x].length; y++)
					{
						if (SquareDistance(Units.get(i).getX()+16, Units.get(i).getY()+16, x*32+16, y*32+16) < 2500)
						{
							if (TowerData[x][y][0] > 0)
							{
								TowerData[x][y][0] -= Units.get(i).getAttack() / 800 * elapsedTime;
							}
						}
					}
				}
			}
			
			if (GameBoard[Units.get(i).getNodeX()][Units.get(i).getNodeY()] == 2) //Hits a central tower
			{
				Units.get(i).setTerminate();
				health -= Units.get(i).getAttack();
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Damage);
			}
			
			if (Units.get(i).getLevel() == 6 && Units.get(i).powerupReady())
			{
				for (int j = 0; j < Units.size(); j++)
				{
					if (SquareDistance(Units.get(i).getX()+16, Units.get(i).getY()+16, Units.get(j).getX()+16, Units.get(j).getY()+16) < 2500)
					{
						Units.get(j).heal(elapsedTime);
					}
				}
			}
		}
		
		//this.postInvalidate();
		
		for (int i = (Units.size() - 1); i > -1 ; i-- )
		{	
			if (Units.get(i).getKill() == 1)
			{				
				Units.remove(i);
			}
		}
		
		for (int i = 0; i < Units.size(); i++)
		{
			for (int j = 0; j < Bullets.size(); j++)
			{
				if (Units.get(i).getRect().contains(Bullets.get(j).rect()))
				{
					if (Bullets.get(j).Active())
					{
						//Units.get(i).setTerminate();
						Units.get(i).takeDamage(50);
						if (enableSound)
							AudioService.PlaySound(SoundEffect.Hit);
						Bullets.get(j).setTerminate(); //Splash Damage?
						if (!Units.get(i).stillAlive())
						{
							int money = Units.get(i).getGold();
							gold += money;
							experiencePoints += money;
							if (money != 0)
							{
								FadeText tempText = new FadeText(Integer.toString(money), Units.get(i).getX(), Units.get(i).getY(), 0, -100, 500, Color.YELLOW, 25);
								FadingText.add(tempText);
								

								FadeBlock tempBlock = new FadeBlock(Units.get(i).getLevel(), Units.get(i).getX()+16, Units.get(i).getY()+16, 16, 32, 250);
								FadingBlocks.add(tempBlock);
								if (enableSound)
									AudioService.PlaySound(SoundEffect.EnemyDestroyed);
							}
						}
					}
				}
			}
			for (int j = 0; j < SimpleBullets.size(); j++)
			{
				if (Units.get(i).getRect().contains(SimpleBullets.get(j).rect()))
				{
					if (!SimpleBullets.get(j).kill())
					{
						if (Units.get(i).getLevel() != 5 || (Units.get(i).getLevel() == 5 && SimpleBullets.get(j).getDamage() > 6))
						{
							if (Units.get(i).getLevel() != 5)
								Units.get(i).takeDamage(SimpleBullets.get(j).getDamage());
							else
								Units.get(i).takeDamage(SimpleBullets.get(j).getDamage() / 2);
							SimpleBullets.get(j).setTerminate();
							if (!Units.get(i).stillAlive())
							{
								int money = Units.get(i).getGold();
								gold += money;
								experiencePoints += money;
								if (money != 0)
								{
									FadeText tempText = new FadeText(Integer.toString(money), Units.get(i).getX(), Units.get(i).getY(), 0, -100, 500, Color.YELLOW, 25);
									FadingText.add(tempText);
									

									FadeBlock tempBlock = new FadeBlock(Units.get(i).getLevel(), Units.get(i).getX()+16, Units.get(i).getY()+16, 16, 32, 250);
									FadingBlocks.add(tempBlock);
									if (enableSound)
										AudioService.PlaySound(SoundEffect.EnemyDestroyed);
								}
							}
						}
					}
				}
			}
		}
	}

	public void UpdateBullets(float elapsedTime)
	{
		UpdateMainTowerBullets(elapsedTime);
		UpdateSubTowerBullets(elapsedTime);
	}
	
	public void UpdateMainTowerBullets(float elapsedTime)
	{
		for (int i = 0; i < Bullets.size(); i++)
		{
			Bullets.get(i).update(elapsedTime);
			FadeCircle circ = new FadeCircle(3, Bullets.get(i).BulletImage.getX(), Bullets.get(i).BulletImage.getY(), Bullets.get(i).BulletImage.image.getWidth()/2, 0, Bullets.get(i).getZ()*3);
			FadingCircles.add(circ);
		}
		for (int i = (Bullets.size() - 1); i > -1 ; i-- )
		{			
			if (Bullets.get(i).kill())
			{
				if (towerPower != 0 && towerPower != 3)
				{
					//int color = Color.TRANSPARENT;
					int x = Bullets.get(i).getX();
					int y = Bullets.get(i).getY();
					if (towerPower == 1)
					{
						//TowerEffect effect = new TowerEffect(x, y, 255, 0, 0, 250, 50);
						//TowerEffects.add(effect);
						FadeCircle circ = new FadeCircle(2, x, y, 0, 50, 350);
						FadingCircles.add(circ);
					}
					else if (towerPower == 2)
					{
						//TowerEffect effect = new TowerEffect(x, y, 0, 128, 192, 250, 50);
						//TowerEffects.add(effect);
						FadeCircle circ = new FadeCircle(1, x, y, 0, 50, 350);
						FadingCircles.add(circ);
					}
					
					for (int j = 0; j < Units.size(); j++)
					{
						if (SquareDistance(x, y, Units.get(j).getX(), Units.get(j).getY()) < 2500)
						{
							if (towerPower == 1)
							{
								if (maxLevel < 11)
								{
									Units.get(j).takeDamage(10);
								}
								else
								{
									Units.get(j).takeDamage(maxLevel);
								}
							}
							if (towerPower == 2)
							{
								if (maxLevel < 25)
								{
									Units.get(j).slow();
								}
								else
								{
									Units.get(j).takeDamage(-2500);
								}
							}
						}
					}
				}
				Bullets.remove(i);
				if (enableSound)
					AudioService.PlaySound(SoundEffect.Bullet_Land);
//				if (towerPower == 1)
//				{
//					AudioService.PlaySound(SoundEffect.TowerFire);
//				}
//				else if (towerPower == 2)
//				{
//					AudioService.PlaySound(SoundEffect.TowerIce);
//				}
			}
		}	
	}
	
	public void UpdateSubTowerBullets(float elapsedTime)
	{
		for (int i = 0; i < SimpleBullets.size(); i++)
		{
			SimpleBullets.get(i).update(elapsedTime);
		}
		for (int i = (SimpleBullets.size() - 1); i > -1 ; i-- )
		{
			
			if (SimpleBullets.get(i).kill())
			{
				SimpleBullets.remove(i);
			}
		}	
	}
	
	
	float Distance(float x1, float y1, float x2, float y2)
	{
		float retVal = 0;
		retVal = (float) (Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
		retVal = (float) Math.pow(retVal, .5);
		return retVal;
	}
	
	float SquareDistance(float x1, float y1, float x2, float y2)
	{
		float retVal = 0;
		float dX = x2 - x1;
		float dY = y2 - y1;
		retVal = dX*dX + dY*dY;
		return retVal;
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) 
    {
        super.onConfigurationChanged(newConfig);
    }

	public void setScreenBitmap(Bitmap screenBitmap) {
		this.screen = screenBitmap;
		
	}
	
	public void UpdateTowerWallMap()
	{
		for (int x = 0; x < TowerWallMap.length; x++)
		{
			for (int y = 0; y < TowerWallMap[x].length; y++)
			{
				if (GameBoard[x][y] == 1) //Wall Exists
				{
					if (TowerData[x][y][2] != 0) //Tower
					{
						if (TowerData[x][y][2] == 1) //Fire
						{
							if (TowerLevel[x][y] == 1)
							{
								TowerWallMap[x][y] = 16;
							}
							else if (TowerLevel[x][y] == 2)
							{
								TowerWallMap[x][y] = 16;
							}
							else if (TowerLevel[x][y] == 3)
							{
								TowerWallMap[x][y] = 16;
							}
						}
						else if (TowerData[x][y][2] == 2) //Ice
						{
							if (TowerLevel[x][y] == 1)
							{
								TowerWallMap[x][y] = 16;
							}
							else if (TowerLevel[x][y] == 2)
							{
								TowerWallMap[x][y] = 16;
							}
							else if (TowerLevel[x][y] == 3)
							{
								TowerWallMap[x][y] = 16;
							}
						}
						else if (TowerData[x][y][2] == 3) //Heal
						{
							if (TowerLevel[x][y] == 1)
							{
								TowerWallMap[x][y] = 16;
							}
							else if (TowerLevel[x][y] == 2)
							{
								TowerWallMap[x][y] = 16;
							}
							else if (TowerLevel[x][y] == 3)
							{
								TowerWallMap[x][y] = 16;
							}
						}
					}
					else //Normal Wall
					{
						boolean above = false;
						boolean below = false;
						boolean left = false;
						boolean right = false;
						if (x == 0 && y == 0) //Top Left
						{
							above = false;
							left = false;
							if (GameBoard[x+1][y] == 1)
								right = true;
							if (GameBoard[x][y+1] == 1)
								below = true;
						}
						else if (x == 29 && y == 0) //Top Right
						{							
							above = false;
							right = false;
							if (GameBoard[x-1][y] == 1)
								left = true;
							if (GameBoard[x][y+1] == 1)
								below = true;
						}
						else if (x == 29 && y == 13) //Bottom Right
						{
							below = false;
							right = false;
							if (GameBoard[x-1][y] == 1)
								left = true;
							if (GameBoard[x][y-1] == 1)
								above = true;
						}
						else if (x == 0 && y == 13) //Bottom Left
						{
							below = false;
							left = false;
							if (GameBoard[x+1][y] == 1)
								right = true;
							if (GameBoard[x][y-1] == 1)
								above = true;
						}
						else if (y == 0) //Top Bar
						{
							above = false;
							if (GameBoard[x+1][y] == 1)
								right = true;
							if (GameBoard[x-1][y] == 1)
								left = true;
							if (GameBoard[x][y+1] == 1)
								below = true;
						}
						else if (x == 0) //Left Bar
						{
							left = false;
							if (GameBoard[x+1][y] == 1)
								right = true;
							if (GameBoard[x][y-1] == 1)
								above = true;
							if (GameBoard[x][y+1] == 1)
								below = true;
						}
						else if (x == 29) //Right Bar
						{
							right = false;
							if (GameBoard[x-1][y] == 1)
								left = true;
							if (GameBoard[x][y-1] == 1)
								above = true;
							if (GameBoard[x][y+1] == 1)
								below = true;
						}
						else if (y == 13) //Bottom Bar
						{
							below = false;
							if (GameBoard[x+1][y] == 1)
								right = true;
							if (GameBoard[x-1][y] == 1)
								left = true;
							if (GameBoard[x][y-1] == 1)
								above = true;
						}
						else
						{
							if (GameBoard[x+1][y] == 1)
								right = true;
							if (GameBoard[x-1][y] == 1)
								left = true;
							if (GameBoard[x][y-1] == 1)
								above = true;
							if (GameBoard[x][y+1] == 1)
								below = true;
						}
						
						if (above && below && !left && !right)
						{
							TowerWallMap[x][y] = 1;
						}
						else if (!above && !below && left && right)
						{
							TowerWallMap[x][y] = 2;
						}
						else if (!above && !below && left && !right)
						{
							TowerWallMap[x][y] = 3;
						}
						else if (above && !below && !left && !right)
						{
							TowerWallMap[x][y] = 4;
						}
						else if (!above && !below && !left && right)
						{
							TowerWallMap[x][y] = 5;
						}
						else if (!above && below && !left && !right)
						{
							TowerWallMap[x][y] = 6;
						}
						else if (!above && below && left && !right)
						{
							TowerWallMap[x][y] = 7;
						}
						else if (above && !below && left && !right)
						{
							TowerWallMap[x][y] = 8;
						}
						else if (above && !below && !left && right)
						{
							TowerWallMap[x][y] = 9;
						}
						else if (!above && below && !left && right)
						{
							TowerWallMap[x][y] = 10;
						}
						else if (above && below && !left && right)
						{
							TowerWallMap[x][y] = 11;
						}
						else if (!above && below && left && right)
						{
							TowerWallMap[x][y] = 12;
						}
						else if (above && below && left && !right)
						{
							TowerWallMap[x][y] = 13;
						}
						else if (above && !below && left && right)
						{
							TowerWallMap[x][y] = 14;
						}
						else if (above && below && left && right)
						{
							TowerWallMap[x][y] = 15;
						}
						else if (!above && !below && !left && !right)
						{
							TowerWallMap[x][y] = 17;
						}
					}
				}
				else
				{
					TowerWallMap[x][y] = 0;
				}
			}
		}
	
	}

	public void onStart() {
		//Intent intent = new Intent(gameContext, AudioService.class);
        //gameContext.bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
		
	}

//	public void onStop() {
//		if (AudioServiceBound) {
//            gameContext.unbindService(myConnection);
//            AudioServiceBound = false;
//        }
//		
//	}

	public class ImageLoader extends AsyncTask <Void, Void, Bitmap>{
	
		private String URL;
		private int type;
		private Context context;
		private InputStream in;
		private int index;
		
		ImageLoader(Context Context, String Url, int Type, int Index)
		{
			URL = Url;
			type = Type;
			context = Context;
			index = Index;
		}
		
		@Override
		protected void onPreExecute()
		{
		   AssetManager assetMgr = context.getAssets();

		   try {

		       in = assetMgr.open(URL);
		   } catch (IOException e) {

		       e.printStackTrace();
		   }

		}
		
		@Override
		protected Bitmap doInBackground(Void... arg0) {
						
			Bitmap bitmap = null;
			bitmap = BitmapFactory.decodeStream(in);			
			return bitmap;
		}
	
		@Override
		protected void onPostExecute( Bitmap result )  {
		      super.onPostExecute(result);
		      if (index == InstructionsIndex)
		      {
			      if (type == 1)
			    	  Inst1 = result;
			      else if (type == 2)
			    	  Inst2 = result;
			      else if (type == 3)
			    	  Inst3 = result;
		      }
		}
	}
}
