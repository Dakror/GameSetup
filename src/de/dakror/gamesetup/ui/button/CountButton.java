package de.dakror.gamesetup.ui.button;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import de.dakror.gamesetup.ui.ClickEvent;
import de.dakror.gamesetup.ui.Component;
import de.dakror.gamesetup.ui.button.ArrowButton.ArrowType;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class CountButton extends Component
{
	int scrollSpeed = 4;
	long timeDown = 0;
	
	public static int h = 25;
	
	public int min, max, value, step;
	
	ArrowButton minus, plus;
	
	public CountButton(int x, int y, int width, int min, int max, int step, int value)
	{
		super(x, y, width, 32);
		this.min = min;
		this.max = max;
		this.step = step;
		this.value = value;
		
		minus = new ArrowButton(x, y, ArrowType.MINUS_HOR);
		minus.addClickEvent(new ClickEvent()
		{
			@Override
			public void trigger()
			{
				CountButton.this.value = (CountButton.this.value - CountButton.this.step >= CountButton.this.min) ? CountButton.this.value - CountButton.this.step : CountButton.this.min;
			}
		});
		plus = new ArrowButton(x + width - 32, y, ArrowType.PLUS_HOR);
		plus.addClickEvent(new ClickEvent()
		{
			@Override
			public void trigger()
			{
				CountButton.this.value = (CountButton.this.value + CountButton.this.step <= CountButton.this.max) ? CountButton.this.value + CountButton.this.step : CountButton.this.max;
			}
		});
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		minus.draw(g);
		Helper.drawHorizontallyCenteredString(value + "", x, width, y + h, g, 25);
		plus.draw(g);
	}
	
	@Override
	public void update(int tick)
	{
		if (timeDown == 0) return;
		
		int scrollSpeed = this.scrollSpeed;
		if (System.currentTimeMillis() - timeDown > 5000) scrollSpeed = 2;
		if (System.currentTimeMillis() - timeDown > 7500) scrollSpeed = 1;
		
		if (System.currentTimeMillis() - timeDown > 300 && (scrollSpeed == 1 || tick % scrollSpeed == 0))
		{
			if (minus.state == 1) minus.triggerEvents();
			if (plus.state == 1) plus.triggerEvents();
			
			if (scrollSpeed == 1)
			{
				for (int i = 0; i < 10; i++)
				{
					if (minus.state == 1) minus.triggerEvents();
					if (plus.state == 1) plus.triggerEvents();
				}
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		minus.mousePressed(e);
		plus.mousePressed(e);
		
		if (minus.state == 1 || plus.state == 1) timeDown = System.currentTimeMillis();
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		minus.mouseReleased(e);
		plus.mouseReleased(e);
		
		timeDown = 0;
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		minus.mouseMoved(e);
		plus.mouseMoved(e);
	}
}
