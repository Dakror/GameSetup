package de.dakror.gamesetup.ui;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Dakror
 */
public abstract class ClickableComponent extends Component
{
	ArrayList<ClickEvent> events;
	
	public ClickableComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		events = new ArrayList<>();
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (contains(e.getX(), e.getY()) && enabled)
		{
			triggerEvents();
			state = 2;
		}
	}
	
	public void addClickEvent(ClickEvent e)
	{
		events.add(e);
	}
	
	public void triggerEvents()
	{
		for (ClickEvent e1 : events)
			e1.trigger();
	}
}
