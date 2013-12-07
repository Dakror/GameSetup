package de.dakror.gamesetup.ui;

import java.awt.Graphics2D;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class Checkbox extends ClickableComponent
{
	public static final int WIDTH = 44;
	public static final int HEIGHT = 44;
	
	boolean selected;
	
	public Checkbox(int x, int y)
	{
		super(x, y, WIDTH, HEIGHT);
		addClickEvent(new ClickEvent()
		{
			@Override
			public void trigger()
			{
				selected = !selected;
			}
		});
		selected = false;
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		int tx = 138;
		if (state == 2) tx = 183;
		if (!enabled) tx = 228;
		
		Helper.drawImage(GameFrame.getImage("gui/gui.png"), x, y, width, height, tx, selected ? 47 : 3, WIDTH, HEIGHT, g);
	}
	
	@Override
	public void update(int tick)
	{}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
}
