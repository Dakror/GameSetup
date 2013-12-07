package de.dakror.gamesetup.ui.button;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import de.dakror.gamesetup.ui.ClickEvent;
import de.dakror.gamesetup.ui.Component;
import de.dakror.gamesetup.ui.button.ArrowButton.ArrowType;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class Spinner extends Component
{
	int scrollSpeed = 4;
	long timeDown = 0;
	
	public static int h = 25;
	
	HashMap<Integer, String> aliases;
	
	public int min, max, value, step;
	
	ArrowButton minus, plus;
	
	public Spinner(int x, int y, int width, int min, int max, int step, int value)
	{
		this(x, y, width, min, max, step, value, ArrowType.MINUS_HOR, ArrowType.PLUS_HOR);
	}
	
	public Spinner(int x, int y, int width, int min, int max, int step, int value, ArrowType minus, ArrowType plus)
	{
		super(x, y, width, 32);
		this.min = min;
		this.max = max;
		this.step = step;
		this.value = value;
		
		aliases = new HashMap<>();
		
		this.minus = new ArrowButton(x, y, minus);
		this.minus.addClickEvent(new ClickEvent()
		{
			@Override
			public void trigger()
			{
				Spinner.this.value = (Spinner.this.value - Spinner.this.step >= Spinner.this.min) ? Spinner.this.value - Spinner.this.step : Spinner.this.min;
			}
		});
		this.plus = new ArrowButton(x + width - 32, y, plus);
		this.plus.addClickEvent(new ClickEvent()
		{
			@Override
			public void trigger()
			{
				Spinner.this.value = (Spinner.this.value + Spinner.this.step <= Spinner.this.max) ? Spinner.this.value + Spinner.this.step : Spinner.this.max;
			}
		});
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		minus.draw(g);
		Helper.drawHorizontallyCenteredString((aliases.containsKey(value) ? aliases.get(value) : value) + "", x, width, y + h, g, 25);
		plus.draw(g);
	}
	
	@Override
	public void update(int tick)
	{
		plus.enabled = value != max;
		minus.enabled = value != min;
		
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
	
	public void addAlias(int i, String alias)
	{
		aliases.put(i, alias);
	}
}
