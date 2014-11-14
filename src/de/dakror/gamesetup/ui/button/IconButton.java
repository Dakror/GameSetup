package de.dakror.gamesetup.ui.button;

import java.awt.Graphics2D;
import java.awt.Image;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.ui.ClickableComponent;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class IconButton extends ClickableComponent {
	Image img;
	boolean biggerOnHover;
	public boolean mode1, mode2, doubled, shadow;
	public String tooltip;
	
	public IconButton(int x, int y, int width, int height, String img) {
		this(x, y, width, height, GameFrame.getImage(img));
	}
	
	public IconButton(int x, int y, int width, int height, Image img) {
		super(x, y, width, height);
		
		this.img = img.getScaledInstance(width + 10, height + 20, Image.SCALE_SMOOTH);
		mode1 = false;
		mode2 = false;
		doubled = false;
		setBiggerOnHover(true);
		
		shadow = true;
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (mode1) {
			Helper.drawShadow(x - (state > 0 && biggerOnHover ? 5 : 0) - 10, y - (state > 0 && biggerOnHover ? 5 : 0) - 10, width + (state > 0 && biggerOnHover ? 10 : 0) + 20, height + (state > 0 && biggerOnHover ? 10 : 0) + 20, g);
			if (state != 1) Helper.drawOutline(x - (state > 0 && biggerOnHover ? 5 : 0) - 10, y - (state > 0 && biggerOnHover ? 5 : 0) - 10, width + (state > 0 && biggerOnHover ? 10 : 0) + 20, height + (state > 0 && biggerOnHover ? 10 : 0) + 20, state == 1, g);
			else Helper.drawContainer(x - (state > 0 && biggerOnHover ? 5 : 0) - 10, y - (state > 0 && biggerOnHover ? 5 : 0) - 10, width + (state > 0 && biggerOnHover ? 10 : 0) + 20, height + (state > 0 && biggerOnHover ? 10 : 0) + 20, false, false, shadow, g);
		} else if (mode2) {
			Helper.drawContainer(x - (state > 0 && biggerOnHover ? 5 : 0) - 10, y - (state > 0 && biggerOnHover ? 5 : 0) - 10, width + (state > 0 && biggerOnHover ? 10 : 0) + 20, height + (state > 0 && biggerOnHover ? 10 : 0) + 20, doubled, state == 1, shadow, g);
		}
		
		g.drawImage(img, x - (state > 0 && biggerOnHover ? 5 : 0), y - (state > 0 && biggerOnHover ? 5 : 0), width + (state > 0 && biggerOnHover ? 10 : 0), height + (state > 0 && biggerOnHover ? 10 : 0), null);
		
		if (!enabled) Helper.drawShadow(x - (state > 0 && biggerOnHover ? 5 : 0) - (mode1 ? 10 : 20), y - (state > 0 && biggerOnHover ? 5 : 0) - (mode1 ? 10 : 20), width + (state > 0 && biggerOnHover ? 10 : 0) + (mode1 ? 20 : 40), height + (state > 0 && biggerOnHover ? 10 : 0) + (mode1 ? 20 : 40), g);
	}
	
	public void setBiggerOnHover(boolean bigger) {
		biggerOnHover = bigger;
	}
	
	@Override
	public void update(int tick) {}
	
	@Override
	public void drawTooltip(int x, int y, Graphics2D g) {
		if (tooltip != null) {
			int width = g.getFontMetrics(g.getFont().deriveFont(30f)).stringWidth(tooltip) + 30;
			int height = 64;
			int x1 = x;
			int y1 = y;
			
			if (x1 + width > GameFrame.getWidth()) x1 -= (x1 + width) - GameFrame.getWidth();
			if (y1 + height > GameFrame.getHeight()) y1 -= (y1 + height) - GameFrame.getHeight();
			
			Helper.drawShadow(x1, y1, g.getFontMetrics(g.getFont().deriveFont(30f)).stringWidth(tooltip) + 30, height, g);
			Helper.drawString(tooltip, x1 + 15, y1 + 40, g, 30);
		}
	}
}
