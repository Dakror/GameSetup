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
	Image img;
	Image bigger;
	boolean biggerOnHover;
	public boolean mode1, mode2, doubled, tooltipOnBottom, shadow;
	public String tooltip;
	
	public IconButton(int x, int y, int width, int height, String img)
	{
		this(x, y, width, height, GameFrame.getImage(img));
	}
	
	public IconButton(int x, int y, int width, int height, Image img)
	{
		super(x, y, width, height);
		
		this.img = img.getScaledInstance(width, height, Image.SCALE_REPLICATE);
		mode1 = false;
		mode2 = false;
		doubled = false;
		setBiggerOnHover(true);
		
		shadow = true;
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		if (mode1)
		{
			Helper.drawShadow(x - (state > 0 && biggerOnHover ? 5 : 0) - 10, y - (state > 0 && biggerOnHover ? 5 : 0) - 10, width + (state > 0 && biggerOnHover ? 10 : 0) + 20, height + (state > 0 && biggerOnHover ? 10 : 0) + 20, g);
			if (state != 1) Helper.drawOutline(x - (state > 0 && biggerOnHover ? 5 : 0) - 10, y - (state > 0 && biggerOnHover ? 5 : 0) - 10, width + (state > 0 && biggerOnHover ? 10 : 0) + 20, height + (state > 0 && biggerOnHover ? 10 : 0) + 20, state == 1, g);
			else Helper.drawContainer(x - (state > 0 && biggerOnHover ? 5 : 0) - 10, y - (state > 0 && biggerOnHover ? 5 : 0) - 10, width + (state > 0 && biggerOnHover ? 10 : 0) + 20, height + (state > 0 && biggerOnHover ? 10 : 0) + 20, false, false, g);
		}
		else if (mode2)
		{
			Helper.drawContainer(x - (state > 0 && biggerOnHover ? 5 : 0) - 10, y - (state > 0 && biggerOnHover ? 5 : 0) - 10, width + (state > 0 && biggerOnHover ? 10 : 0) + 20, height + (state > 0 && biggerOnHover ? 10 : 0) + 20, doubled, state == 1, shadow, g);
		}
		
		if (state == 0 || !biggerOnHover) g.drawImage(img, x, y, width, height, null);
		if (state > 0 && biggerOnHover) g.drawImage(bigger, x - 5, y - 5, width + 10, height + 10, null);
		
		if (!enabled) Helper.drawShadow(x - (state > 0 && biggerOnHover ? 5 : 0) - 10, y - (state > 0 && biggerOnHover ? 5 : 0) - 10, width + (state > 0 && biggerOnHover ? 10 : 0) + 20, height + (state > 0 && biggerOnHover ? 10 : 0) + 20, g);
	}
	
	public void setBiggerOnHover(boolean bigger)
	{
		biggerOnHover = bigger;
		if (bigger) this.bigger = img.getScaledInstance(width + 10, height + 10, Image.SCALE_REPLICATE);
		
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
			Helper.drawShadow(x, y - (tooltipOnBottom ? 54 : 0), g.getFontMetrics(g.getFont().deriveFont(30f)).stringWidth(tooltip) + 30, 64, g);
			Helper.drawString(tooltip, x + 15, y + 40 - (tooltipOnBottom ? 54 : 0), g, 30);
		}
	}
}
