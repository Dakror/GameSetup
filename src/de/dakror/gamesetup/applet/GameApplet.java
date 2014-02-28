package de.dakror.gamesetup.applet;

import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.concurrent.CopyOnWriteArrayList;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.util.Helper;


/**
 * @author Dakror
 */
public abstract class GameApplet extends GameFrame
{
	public static GameApplet currentApplet;
	static Dimension size;
	
	Graphics2D g;
	Image offImg;
	
	public GameApplet()
	{
		currentApplet = this;
	}
	
	@Override
	@Deprecated
	public void init(String title)
	{}
	
	public void init(Applet applet)
	{
		size = applet.getSize();
		applet.addKeyListener(this);
		applet.addMouseListener(this);
		applet.addMouseMotionListener(this);
		applet.addMouseWheelListener(this);
		applet.setBackground(Color.black);
		applet.setForeground(Color.white);
		
		offImg = applet.createImage(size.width, size.height);
		
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
	@Deprecated
	public void main()
	{}
	
	public void main(Graphics2D g)
	{
		if (start == 0) start = System.currentTimeMillis();
		if (last == 0) last = System.currentTimeMillis();
		if (System.currentTimeMillis() - last >= 1000)
		{
			framesSolid = frames;
			frames = 0;
			last = System.currentTimeMillis();
		}
		
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
