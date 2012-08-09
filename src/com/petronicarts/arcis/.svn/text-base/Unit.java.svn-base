package com.petronicarts.arcis;

import android.graphics.Rect;

public class Unit{
	int currentNodeX;
	int currentNodeY;
	int goalNodeX;
	int goalNodeY;
	float dX;
	float dY;
	float blockX;
	float blockY;
	float speed;
	float x;
	float y;
	float goalX;
	float goalY;
	int kill;
	int updateNeeded;
	float attack;
	float health;
	float maxHealth;
	float slowTimer;
	float stopTimer;
	float stopLength;
	public int gold;
	public boolean giveGold; 
	public int level;
	public long powerTimer;
	public long powerTime;
	public long powerLength;
	public float healRate;
	
	public Unit(int NodeX, int NodeY, int Level, float BlockX, float BlockY, int CurrentLevel)
	{
		this.level = Level;
		switch(level)
		{
			case 1:
				this.speed = 40;
				this.stopLength = 1000;
				this.health = 30;
				this.attack = 10;
				this.gold = 30;
				this.healRate = (float) (1.0/20.0);
				break;
			case 2:
				this.speed = 80;
				this.stopLength = 500;
				this.health = 15;
				this.attack = 5;
				this.gold = 10;
				this.healRate = (float) (1.0/20.0);
				break;
			case 3:
				this.speed = 20;
				this.stopLength = 0;
				this.health = 200;
				this.attack = 30;
				this.gold = 50;
				this.healRate = (float) (3.0/20.0);
				break;
			case 4:
				this.speed = 40;
				this.stopLength = 1000;
				if (CurrentLevel <= 25)
				{
					this.health = 350 + (CurrentLevel - 4)*10;
				}
				else
				{
					this.health = 560 + (CurrentLevel-25)*6;
				}
				this.attack = 15;
				this.gold = 100;
				this.healRate = (float) (3.0/20.0);
				break;
			case 5:
				this.speed = 40;
				this.stopLength = 1000;
				if (CurrentLevel <= 25)
				{
					this.health = 175 + (CurrentLevel - 5)*10;
				}
				else
				{
					this.health = 375 + (CurrentLevel-25)*6;
				}
				this.attack = 40;
				this.gold = 150;
				this.powerTimer = 2500;
				this.powerLength = 0;
				this.healRate = (float) (5.0/20.0);
				break;
			case 6:
				this.speed = 40;
				this.stopLength = 1000;
				if (CurrentLevel <= 25)
				{
					this.health = 400 + (CurrentLevel - 6)*10;
				}
				else
				{
					this.health = 590 + (CurrentLevel - 25)*6;
				}
				this.attack = 10;
				this.gold = 125;
				this.powerTimer = 2000;
				this.powerLength = 500;
				this.healRate = (float) (5.0/20.0);
				break;
			default: //Basic Tower (Case 1)					
				this.speed = 40;
				this.stopLength = 1000;
				this.health = 30;
				this.attack = 10;
				this.gold = 30;
				this.healRate = (float) (1.0/20.0);
				break;
		}
		
		this.powerTime = 0;
		this.currentNodeX = NodeX;
		this.currentNodeY = NodeY;
		this.blockX = BlockX;
		this.blockY = BlockY;
		this.x = currentNodeX * blockX;
		this.y = currentNodeY * blockY;
		this.kill = 0;
		this.updateNeeded = 0;
		this.maxHealth = health;
		this.slowTimer = 5000;
		this.stopTimer = 5000;
		this.giveGold = true;
		
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getKill()
	{
		return kill;
	}
	
	public void setTerminate()
	{
		kill = 1;
	}
	
	public void heal(float elapsedTime)
	{
		this.health += healRate * elapsedTime;
		if (health > maxHealth)
			health = maxHealth;
	}
	
	public void setGoal(int GoalX, int GoalY)
	{
		this.goalNodeX = GoalX;
		this.goalNodeY = GoalY;
		this.goalX = goalNodeX * blockX;
		this.goalY = goalNodeY * blockY;
	}
	
	
	
	public Rect getRect()
	{
		Rect retVal = new Rect();
		retVal.set((int)(x - 5), (int) (y - 5),(int) (x + ((blockX + 5))),(int) (y + ((blockY +5))));
		return retVal;
	}
	
	public boolean onLine(float startX, float startY, float endX, float endY, int step)
	{
		boolean retVal = false;
		Rect rect = getRect();
		float dx = (endX - startX)/step;
		float dy = (endY - startY)/step;
		while (step > 0)
		{
			if (rect.contains((int)startX, (int)startY))
				retVal = true;
			startX += dx;
			startY += dy;
			step--;
		}
		return retVal;
	}
	
	public void setDelta()
	{
		dX = goalNodeX - currentNodeX;
		dY = goalNodeY - currentNodeY;
		
	}
	
	public void checkDelta()
	{
		if (dX > 1 || dX < -1 || dY > 1 || dY < -1)
		{
			goalY = y;
			goalX = x;
			dX = 0;
			dY = 0;
		}
	}
	
	public void slow()
	{
		slowTimer = 0;
	}
	
	public boolean Stopped()
	{
		if (stopTimer > stopLength)
			return false;
		else
			return true;
	}
	
	public boolean powerupReady()
	{
		if (powerTime > powerTimer)
		{
			if ((powerTime - powerLength) > powerTimer)
				powerTime = 0;
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public void Update(int GoalX, int GoalY, float elapsedTime)
	{
		//checkDelta();
		if (updateNeeded == 1)
		{
			setGoal(GoalX, GoalY);
			setDelta();
			updateNeeded = 0;
		}
		
		
		
		slowTimer += elapsedTime;
		stopTimer += elapsedTime;
		powerTime += elapsedTime;
		if (stopTimer > stopLength)
		{
			if (slowTimer < 1000)
			{
				x += dX * speed / 2 * elapsedTime/1000;
				y += dY * speed / 2 * elapsedTime/1000; 
			}
			else
			{
				x += dX * speed * elapsedTime/1000;
				y += dY * speed * elapsedTime/1000; 
			}
		}
			
		if (((dX > 0) && (x > goalX)) || ((dX == 0) && (x == goalX)) || ((dX < 0) && (x < goalX)))
		{
			if (((dY > 0) && (y > goalY)) || ((dY == 0) && (y == goalY)) || ((dY < 0) && (y < goalY)))
			{
				//Unit has passed goal location
				currentNodeX = goalNodeX;
				currentNodeY = goalNodeY;
				this.x = currentNodeX * blockX;
				this.y = currentNodeY * blockY;
				updateNeeded = 1;
			}
		}
		
		if (health <= 0)
		{
			setTerminate();
		}
		
		//Subject to change based on changing turret location
		//if (currentNodeX == 14 && currentNodeY == 0)
		//{
		//	kill = 1;
		//}
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public int getNodeX()
	{
		return currentNodeX;
	}
	
	public int getNodeY()
	{
		return currentNodeY;
	}
	
	public int getGoalX()
	{
		return goalNodeX;
	}
	
	public int getGoalY()
	{
		return goalNodeY;
	}
	
	public int getParentX(int X)
	{
		int retVal = 0;
		if (X == 1)
		{
			retVal = currentNodeX;
		}
		if (X == 2)
		{
			retVal = currentNodeX + 1;
		}
		if (X == 3 )
		{
			retVal = currentNodeX + 1;
		}
		if (X == 4)
		{
			retVal = currentNodeX + 1;
		}
		if (X == 5)
		{
			retVal = currentNodeX;
		}
		if (X == 6)
		{
			retVal = currentNodeX - 1;
		}
		if (X == 7)
		{
			retVal = currentNodeX - 1;
		}
		if (X == 8)
		{
			retVal = currentNodeX - 1;
		}
		if (X == 9)
		{
			retVal = currentNodeX;
		}
		return retVal;
	}
	
	
	public int getParentY(int Y)
	{
		int retVal = 0;
		if (Y == 1)
		{
			retVal = currentNodeY - 1;
		}
		if (Y == 2)
		{
			retVal = currentNodeY - 1;
		}
		if (Y == 3)
		{
			retVal = currentNodeY;
		}
		if (Y == 4)
		{
			retVal = currentNodeY + 1;
		}
		if (Y == 5)
		{
			retVal = currentNodeY + 1;
		}
		if (Y == 6)
		{
			retVal = currentNodeY + 1;
		}
		if (Y == 7)
		{
			retVal = currentNodeY;
		}
		if (Y == 8)
		{
			retVal = currentNodeY - 1;
		}
		if (Y == 9)
		{
			retVal = currentNodeY;
		}
		return retVal;
	}

	public float getAttack() {
		return attack;
	}

	public void takeDamage(float damage) 
	{
		//if (level == 6)
		//	powerTime = 0;
		
		if (damage > 0)
		{
			health -= damage;
		}
		else
		{
			stopTimer = 0;
			stopLength = -damage;
		}
		
	}
	
	public float healthPercent()
	{
		float retVal;
		retVal = health/maxHealth;
		return retVal;
	}

	public int getGold() {
		if (giveGold)
		{
			giveGold = false;
			return gold;
		}
		else
		{
			return 0;
		}
	}
	
	public boolean stillAlive()
	{
		if (health > 0)
		{
			return true;
		}
		else
		{
			setTerminate();
			return false;
		}
	}
	
}
