package de.dakror.gamesetup;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.dakror.gamesetup.applet.GameApplet;
import de.dakror.gamesetup.layer.Layer;
import de.dakror.gamesetup.util.EventListener;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public abstract class GameFrame extends EventListener
{
	public static File screenshotDir;
	
	public static JFrame w;
	public static GameFrame currentFrame;
	public Updater updater;
	public Point mouse = new Point(0, 0);
	
	static HashMap<String, BufferedImage> imageCache = new HashMap<>();
	
	public int frames, framesSolid;
	public long start, last;
	
	public float alpha = 0;
	float speed = 0;
	float fadeTo = 0;
	boolean fade = false;
	protected boolean screenshot = false;
	
	public CopyOnWriteArrayList<Layer> layers;
	
	public GameFrame()
	{
		currentFrame = this;
	}
	
	public void init(String title)
	{
		w = new JFrame(title);
		w.addKeyListener(this);
		w.addMouseListener(this);
		w.addMouseMotionListener(this);
		w.addMouseWheelListener(this);
		w.addWindowStateListener(new WindowStateListener()
		{
			
			@Override
			public void windowStateChanged(WindowEvent e)
			{
				w.setExtendedState(JFrame.NORMAL);
			}
		});
		w.setBackground(Color.black);
		w.setForeground(Color.white);
		w.getContentPane().setBackground(Color.black);
		w.getContentPane().setIgnoreRepaint(true);
		
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frames = 0;
		start = 0;
		layers = new CopyOnWriteArrayList<>();
		
		initGame();
	}
	
	public abstract void initGame();
	
	public int getUPS()
	{
		return Math.round(updater.ticks / ((System.currentTimeMillis() - start) / 1000f));
	}
	
	public int getUPS2()
	{
		return Helper.round(Math.round(updater.ticks / ((System.currentTimeMillis() - start) / 1000f)), 30);
	}
	
	public int getFPS()
	{
		return framesSolid;
	}
	
	public void addLayer(Layer l)
	{
		l.init();
		layers.add(0, l);
	}
	
	public void setLayer(Layer l)
	{
		for (Layer layer : layers)
		{
			if (!layer.consistent) removeLayer(layer);
		}
		
		addLayer(l);
	}
	
	public void removeLayer(Layer l)
	{
		layers.remove(l);
	}
	
	public Layer getActiveLayer()
	{
		if (layers.size() == 0) return null;
		return layers.get(0);
	}
	
	public void toggleLayer(Layer l)
	{
		for (Layer layer : layers)
		{
			if (layer.getClass().equals(l.getClass()))
			{
				layers.remove(layer);
				return;
			}
		}
		
		l.init();
		layers.add(0, l);
		
	}
	
	public void fadeTo(float target, float speed)
	{
		fade = true;
		fadeTo = target;
		this.speed = speed;
	}
	
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
		
		if (!w.isVisible()) return;
		
		BufferStrategy s = null;
		Graphics2D g = null;
		
		try
		{
			s = w.getBufferStrategy();
			g = (Graphics2D) s.getDrawGraphics();
		}
		catch (Exception e)
		{
			return;
		}
		
		g.translate(w.getInsets().left, w.getInsets().top);
		
		g.clearRect(0, 0, getWidth(), getHeight());
		
		BufferedImage screenshot = null;
		if (this.screenshot)
		{
			screenshot = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			g = (Graphics2D) screenshot.getGraphics();
			g.setFont(w.getFont());
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
		
		if (screenshot != null)
		{
			try
			{
				screenshotDir.mkdirs();
				File file = new File(screenshotDir, "screenshot " + new Date().toString().replace(":", "-") + ".png");
				ImageIO.write(screenshot, "png", file);
				this.screenshot = false;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
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
	
	public void drawLayers(Graphics2D g)
	{
		try
		{
			for (int i = layers.size() - 1; i >= 0; i--)
				layers.get(i).draw(g);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{}
	}
	
	public abstract void draw(Graphics2D g);
	
	public void setFullscreen()
	{
		w.dispose();
		if (w.getWidth() == 0) w.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
		
		w.setExtendedState(JFrame.NORMAL);
		w.setUndecorated(true);
		w.setVisible(true);
		w.createBufferStrategy(2);
	}
	
	public void setWindowed(int width, int height)
	{
		w.dispose();
		w.setSize(width, height);
		w.setMinimumSize(new Dimension(width, height));
		w.setUndecorated(false);
		w.setVisible(true);
		w.createBufferStrategy(2);
	}
	
	public void setWindowed()
	{
		w.dispose();
		w.setUndecorated(false);
		w.setVisible(true);
		w.createBufferStrategy(2);
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		for (Layer l : layers)
		{
			l.keyPressed(e);
			if (l.isModal() && l.isEnabled()) break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		for (Layer l : layers)
		{
			l.keyReleased(e);
			if (l.isModal() && l.isEnabled()) break;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_F12 && screenshotDir != null) screenshot = true;
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		for (Layer l : layers)
		{
			l.keyTyped(e);
			if (l.isModal() && l.isEnabled()) break;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (w != null) e.translatePoint(-w.getInsets().left, -w.getInsets().top);
		mouse = e.getPoint();
		
		if (layers == null) return;
		
		for (Layer l : layers)
		{
			l.mouseMoved(e);
			if (l.isModal() && l.isEnabled()) break;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (w != null) e.translatePoint(-w.getInsets().left, -w.getInsets().top);
		
		for (Layer l : layers)
		{
			l.mousePressed(e);
			if (l.isModal() && l.isEnabled()) break;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (w != null) e.translatePoint(-w.getInsets().left, -w.getInsets().top);
		for (Layer l : layers)
		{
			l.mouseReleased(e);
			if (l.isModal() && l.isEnabled()) break;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (w != null) e.translatePoint(-w.getInsets().left, -w.getInsets().top);
		for (Layer l : layers)
		{
			l.mouseClicked(e);
			if (l.isModal() && l.isEnabled()) break;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (w != null) e.translatePoint(-w.getInsets().left, -w.getInsets().top);
		for (Layer l : layers)
		{
			l.mouseDragged(e);
			if (l.isModal() && l.isEnabled()) break;
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if (w != null) e.translatePoint(-w.getInsets().left, -w.getInsets().top);
		for (Layer l : layers)
		{
			l.mouseWheelMoved(e);
			if (l.isModal() && l.isEnabled()) break;
		}
	}
	
	public static int getFrameWidth()
	{
		return w.getWidth() - (w.getInsets().left + w.getInsets().right);
	}
	
	public static int getFrameHeight()
	{
		return w.getHeight() - (w.getInsets().top + w.getInsets().bottom);
	}
	
	public static int getWidth()
	{
		return GameApplet.currentApplet != null ? GameApplet.getWidth() : getFrameWidth();
	}
	
	public static int getHeight()
	{
		return GameApplet.currentApplet != null ? GameApplet.getHeight() : getFrameWidth();
	}
	
	public static BufferedImage getImage(String p)
	{
		if (imageCache.containsKey(p)) return imageCache.get(p);
		BufferedImage img = currentFrame.loadImage(p);
		imageCache.put(p, img);
		return img;
	}
	
	public BufferedImage loadImage(String p)
	{
		try
		{
			BufferedImage i = ImageIO.read(GameFrame.class.getResource((p.startsWith("/") ? "" : "/img/") + p));
			
			return i;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
