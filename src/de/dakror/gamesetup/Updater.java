package de.dakror.gamesetup;

import de.dakror.gamesetup.layer.Layer;

/**
 * @author Dakror
 */
public abstract class Updater extends Thread
{
	public static int TIMEOUT = 16;
	
	public int tick, ticks;
	long time;
	
	public int speed = 1;
	
	public boolean closeRequested = false;
	
	
	public Updater()
	{
		setPriority(Thread.MAX_PRIORITY);
		start();
	}
	
	@Override
	public void run()
	{
		tick = 0;
		time = System.currentTimeMillis();
		while (!closeRequested)
		{
			if (tick == Integer.MAX_VALUE) tick = 0;
			
			if (GameFrame.currentFrame.fade == true)
			{
				if (GameFrame.currentFrame.alpha != GameFrame.currentFrame.fadeTo)
				{
					float dif = GameFrame.currentFrame.fadeTo - GameFrame.currentFrame.alpha;
					GameFrame.currentFrame.alpha += dif > 0 ? (dif > GameFrame.currentFrame.speed ? GameFrame.currentFrame.speed : dif) : (dif < -GameFrame.currentFrame.speed ? -GameFrame.currentFrame.speed : dif);
				}
				else GameFrame.currentFrame.fade = false;
			}
			
			for (Layer l : GameFrame.currentFrame.layers)
				l.update(tick);
			
			update();
			
			try
			{
				tick++;
				ticks++;
				Thread.sleep(Math.round(TIMEOUT / (float) speed));
			}
			catch (InterruptedException e)
			{}
		}
	}
	
	public abstract void update();
}
