package de.dakror.gamesetup.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import de.dakror.gamesetup.util.Helper;

public class Label extends Component
{
	String text;
	int size;
	int margin;
	Color color;
	
	public Label(int x, int y, String text)
	{
		super(x, y, 1, 1);
		this.text = text;
		size = 25;
		margin = size;
		color = Color.white;
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		Color c = g.getColor();
		g.setColor(color);
		
		if (width > 1) Helper.drawHorizontallyCenteredString(text, x, width, y + margin, g, size);
		else Helper.drawString(text, x, y, g, size);
		
		g.setColor(c);
	}
	
	@Override
	public void update(int tick)
	{}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public int getMargin()
	{
		return margin;
	}
	
	public void setMargin(int margin)
	{
		this.margin = margin;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
}
