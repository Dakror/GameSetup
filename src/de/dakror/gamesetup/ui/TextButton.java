package de.dakror.gamesetup.ui;

import java.awt.Graphics2D;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class TextButton extends ClickableComponent
{
	public static final int WIDTH = 288;
	public static final int HEIGHT = 59;
	
	public static int size = 30;
	public static int shiftY = 9;
	
	String text;
	
	public TextButton(int x, int y, String text)
	{
		super(x, y, WIDTH, HEIGHT);
		this.text = text;
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		Helper.drawImage(GameFrame.getImage("gui/gui.png"), x, y, width, height, 12, state == 0 ? 124 : (state == 1 ? 202 : 280), WIDTH, HEIGHT, g);
		Helper.drawHorizontallyCenteredString(text, x, width, y + height / 2 + shiftY, g, size);
	}
	
	@Override
	public void update(int tick)
	{}
}
