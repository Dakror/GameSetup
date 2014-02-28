package de.dakror.gamesetup.applet;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JApplet;

import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public abstract class DoubleBufferApplet extends JApplet
{
	private static final long serialVersionUID = 1L;
	
	protected Graphics2D g;
	protected Image offscreen;
	Dimension size;
	
	@Override
	public void init()
	{
		size = getSize();
		
		offscreen = Helper.toBufferedImage(createImage(size.width, size.height));
		g = (Graphics2D) offscreen.getGraphics();
	}
	
	@Override
	public void paint(Graphics g)
	{
		this.g.clearRect(0, 0, size.width, size.height);
		GameApplet.currentApplet.main(this.g);
		
		g.drawImage(offscreen, 0, 0, this);
	}
}
