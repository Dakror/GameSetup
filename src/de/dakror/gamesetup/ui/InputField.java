package de.dakror.gamesetup.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.util.Helper;

public class InputField extends ClickableComponent
{
	String text;
	String hint;
	String allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	boolean password;
	boolean centered;
	int size;
	int maxlength;
	public static int h = 5;
	
	BufferedImage bg;
	
	public InputField(int x, int y, int width, int size)
	{
		super(x, y, width, size + 20);
		this.size = size;
		maxlength = 20;
		text = "";
		
		bg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bg.getGraphics();
		Helper.drawShadow(0, 0, width, height, g);
		Helper.drawOutline(0, 0, width, height, false, g);
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		g.drawImage(bg, x, y, GameFrame.w);
		
		FontMetrics fm = g.getFontMetrics(g.getFont().deriveFont((float) size));
		
		String text = this.text;
		if (text.length() == 0 && hint != null) text = hint;
		
		if (password) text = text.replaceAll(".{1}", "\\*");
		int x = this.x;
		if (fm.stringWidth(text) > width - 30) x -= fm.stringWidth(text) - width + 30;
		
		Shape c = g.getClip();
		g.setClip(this.x + 15, y, width, height);
		
		Color o = g.getColor();
		if (!enabled) g.setColor(Color.gray);
		
		
		Helper.drawString(text, x + 15, y + fm.getAscent() + h, g, size);
		g.setClip(c);
		if (state == 1)
		{
			g.setColor(Color.white);
			g.fillRect(x + 15 + fm.stringWidth(text), y + 10, 5, height - 20);
		}
		
		g.setColor(o);
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (state != 1 || !enabled) return;
		
		if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown() && !password)
		{
			try
			{
				text += Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown() && !password)
		{
			try
			{
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
			return;
		}
		
		String key = new String(new char[] { e.getKeyChar() });
		if (allowed.contains(key) && text.length() < maxlength) text += key;
		
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && text.length() > 0) text = text.substring(0, text.length() - 1);
	}
	
	@Override
	public void update(int tick)
	{
		if (!enabled) state = 0;
	}
	
	public String getText()
	{
		return text;
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (contains(e.getX(), e.getY()) && enabled) state = 1;
		else state = 0;
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getAllowed()
	{
		return allowed;
	}
	
	public void setAllowed(String allowed)
	{
		this.allowed = allowed;
	}
	
	public boolean isPassword()
	{
		return password;
	}
	
	public void setPassword(boolean password)
	{
		this.password = password;
	}
	
	public boolean isCentered()
	{
		return centered;
	}
	
	public void setCentered(boolean centered)
	{
		this.centered = centered;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public int getMaxlength()
	{
		return maxlength;
	}
	
	public void setMaxlength(int maxlength)
	{
		this.maxlength = maxlength;
	}
	
	public String getHint()
	{
		return hint;
	}
	
	public void setHint(String hint)
	{
		this.hint = hint;
	}
	
}
