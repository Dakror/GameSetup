package de.dakror.gamesetup.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class Slider extends ClickableComponent
{
	HashMap<Float, String> customTitles;
	
	public static final int WIDTH = 205;
	public static final int HEIGHT = 18;
	
	protected int value;
	public int minValue, maxValue;
	public float sliderPos = 2.5f;
	public boolean horizontal;
	public boolean integerMode = false;
	
	private boolean stepMode = false;
	private int stepSize;
	public String title;
	
	public Slider(int x, int y, int size, int min, int max, boolean horizontal)
	{
		this(x, y, size, min, max, min, horizontal);
	}
	
	public Slider(int x, int y, int size, int min, int max, int startValue, boolean horizontal)
	{
		super(x, y, 0, 0);
		this.x = x;
		this.y = y;
		if (horizontal)
		{
			height = HEIGHT;
			width = size;
		}
		else
		{
			height = size;
			width = HEIGHT;
		}
		
		this.horizontal = horizontal;
		minValue = min;
		maxValue = max;
		value = startValue;
		sliderPos = (value - min) / (max - min) * (size - 18) + 4;
		
		customTitles = new HashMap<>();
	}
	
	public void addCustomTitle(float value, String title)
	{
		customTitles.put(value, title);
	}
	
	@Override
	public void update(int tick)
	{}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (e.getModifiers() == 16 && state != 0) // LMB
		{
			if (!horizontal)
			{
				float percent = Helper.clamp(e.getY() - y - 8, 0, height - 18) / (height - 18) * 100;
				
				if (stepMode) percent = Helper.round(Math.round(percent), stepSize);
				
				sliderPos = percent / 100 * (height - 18) + 4;
				value = Math.round((maxValue - minValue) * (percent / 100) + minValue);
			}
			else
			{
				float percent = Helper.clamp(e.getX() - x - 8, 0, width - 18) / (width - 18) * 100;
				
				if (stepMode) percent = Helper.round(Math.round(percent), stepSize);
				
				sliderPos = percent / 100 * (width - 18) + 4;
				value = Math.round((maxValue - minValue) * (percent / 100) + minValue);
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		Image img = GameFrame.getImage("gui/gui.png");
		if (horizontal)
		{
			Helper.drawImage2(img, x, y, 7, height, 794, 72, 7, 20, g);
			Helper.drawImage2(img, x + width - 7, y, 7, height, 994, 72, 7, 20, g);
			
			for (int i = 0; i < (width - 14) / 193; i++)
				Helper.drawImage2(img, x + 7 + i * 193, y, 193, height, 801, 72, 193, 20, g);
			Helper.drawImage2(img, x + 7 + (width - 14) / 193 * 193, y, (width - 14) % 193, height, 801, 72, (width - 14) % 193, 20, g);
			
			Helper.drawImage2(img, (int) (x + sliderPos - 5), y - (38 - height) / 2, 20, 35, 889, 16, 25, 44, g);
		}
		else
		{
			AffineTransform old = g.getTransform();
			AffineTransform at = g.getTransform();
			at.rotate(Math.toRadians(90), x + HEIGHT / 2, y + HEIGHT / 2);
			g.setTransform(at);
			
			Helper.drawImage2(img, x, y, 7, width, 794, 72, 7, 20, g);
			Helper.drawImage2(img, x + height - 7, y, 7, width, 994, 72, 7, 20, g);
			for (int i = 0; i < (height - 14) / 193; i++)
				Helper.drawImage2(img, x + 7 + i * 193, y, 193, width, 801, 72, 193, 20, g);
			Helper.drawImage2(img, x + 7 + (height - 14) / 193 * 193, y, (height - 14) % 193, width, 801, 72, (height - 14) % 193, 20, g);
			
			Helper.drawImage2(img, (int) (x + sliderPos - 5), y - (38 - width) / 2, 20, 35, 889, 16, 25, 44, g);
			
			g.setTransform(old);
		}
		
		String displayString = ((title != null) ? title + ": " : "") + ((integerMode) ? (value) + "" : value);
		
		if (customTitles.containsKey(value)) displayString = customTitles.get(value);
		
		Helper.drawHorizontallyCenteredString(displayString, x, width, y - 5, g, 30);
	}
	
	public void setIntegerMode(boolean b)
	{
		integerMode = b;
	}
	
	public float getValue()
	{
		return value;
	}
	
	public void setStepSize(int f)
	{
		stepSize = f / (maxValue - minValue) * 100;
		stepMode = true;
	}
	
	public void setTitle(String s)
	{
		title = s;
	}
	
	public String getTitle()
	{
		return title;
	}
}
