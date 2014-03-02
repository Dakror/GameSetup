package de.dakror.gamesetup.ui.button;

import java.awt.Graphics2D;
import java.awt.Image;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.ui.ClickableComponent;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class IconButton extends ClickableComponent
{
	String img;
	Image bigger;
	boolean biggerOnHover;
	public boolean container, woodOnHover, doubled;
	public String tooltip;
	
	public IconButton(int x, int y, int width, int height, String img)
	{
		super(x, y, width, height);
		
		this.img = img;
		container = false;
		woodOnHover = false;
		setBiggerOnHover(true);
		doubled = false;
		woodOnHover = false;
		container = false;
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		if (container) Helper.drawContainer(x - (state > 0 ? 5 : 0) - 10, y - (state > 0 ? 5 : 0) - 10, width + (state > 0 ? 10 : 0) + 20, height + (state > 0 ? 10 : 0) + 20, doubled, state == 1, g);
		if (state == 0 || !biggerOnHover) g.drawImage(GameFrame.getImage(img), x, y, width, height, null);
		if (state > 0 && biggerOnHover) g.drawImage(bigger, x - 5, y - 5, width + 10, height + 10, null);
	}
	
	public void setBiggerOnHover(boolean bigger)
	{
		biggerOnHover = bigger;
		if (bigger) this.bigger = GameFrame.getImage(img).getScaledInstance(width + 10, height + 10, Image.SCALE_REPLICATE);
		
		else this.bigger = null;
	}
	
	@Override
	public void update(int tick)
	{}
	
	@Override
	public void drawTooltip(int x, int y, Graphics2D g)
	{
		if (tooltip != null)
		{
			Helper.drawShadow(x, y, g.getFontMetrics(g.getFont().deriveFont(30f)).stringWidth(tooltip) + 30, 64, g);
			Helper.drawString(tooltip, x + 15, y + 40, g, 30);
		}
	}
}
