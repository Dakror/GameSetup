package de.dakror.gamesetup.applet;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JApplet;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.util.Helper;


/**
 * @author Dakror
 */
public abstract class GameApplet extends GameFrame
{
	public static GameApplet currentApplet;
	public static JApplet applet;
	static Dimension size;
	
	protected Canvas canvas;
	
	public GameApplet()
	{
		currentApplet = this;
	}
	
	@Override
	@Deprecated
	public void init(String title)
	{}
	
	public void init(JApplet a)
	{
		applet = a;
		
		size = applet.getSize();
		canvas = new Canvas();
		canvas.setSize(applet.getSize());
		applet.add(canvas);
		canvas.createBufferStrategy(2);
		
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		canvas.setBackground(Color.black);
		canvas.setForeground(Color.white);
		
		frames = 0;
		start = 0;
		layers = new CopyOnWriteArrayList<>();
		
		initGame();
	}
	
	@Override
	@Deprecated
	public void setFullscreen()
	{}
	
	@Override
	@Deprecated
	public void setWindowed()
	{}
	
	@Override
	@Deprecated
	public void setWindowed(int width, int height)
	{}
	
	@Override
	public void main()
	{
		if (start == 0) start = System.currentTimeMillis();
		if (last == 0) last = System.currentTimeMillis();
		if (System.currentTimeMillis() - last >= 1000)
		{
			framesSolid = frames;
			frames = 0;
			last = System.currentTimeMillis();
		}
		
		BufferStrategy s = null;
		Graphics2D g = null;
		
		try
		{
			s = canvas.getBufferStrategy();
			g = (Graphics2D) s.getDrawGraphics();
		}
		catch (Exception e)
		{
			return;
		}
		
		g.clearRect(0, 0, getWidth(), getHeight());
		
		Helper.setRenderingHints(g, true);
		
		draw(g);
		
		if (alpha > 0)
		{
			Composite c1 = g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.setComposite(c1);
		}
		
		g.dispose();
		
		try
		{
			if (!s.contentsLost()) s.show();
		}
		catch (Exception e)
		{
			return;
		}
		
		frames++;
	}
	
	public static int getWidth()
	{
		return size.width;
	}
	
	public static int getHeight()
	{
		return size.height;
	}
}
