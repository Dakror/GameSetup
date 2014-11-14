package de.dakror.gamesetup.ui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.CopyOnWriteArrayList;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.util.Drawable;
import de.dakror.gamesetup.util.EventListener;


/**
 * @author Dakror
 */
public abstract class Container extends EventListener implements Drawable {
	public static class DefaultContainer extends Container {
		@Override
		public void draw(Graphics2D g) {
			drawComponents(g);
		}
		
		@Override
		public void update(int tick) {
			updateComponents(tick);
		}
	}
	
	public CopyOnWriteArrayList<Component> components;
	protected boolean enabled;
	
	public int translateX, translateY;
	
	public Container() {
		components = new CopyOnWriteArrayList<>();
		enabled = true;
		translateX = translateY = 0;
	}
	
	protected void drawComponents(Graphics2D g) {
		Component hovered = null;
		for (Component c : components) {
			c.draw(g);
			if (c.state == 2) hovered = c;
		}
		if (hovered != null) hovered.drawTooltip(GameFrame.currentFrame.mouse.x, GameFrame.currentFrame.mouse.y, g);
	}
	
	protected void updateComponents(int tick) {
		if (!enabled) return;
		
		for (Component c : components)
			c.update(tick);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (!enabled) return;
		
		for (Component c : components)
			c.mouseWheelMoved(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!enabled) return;
		
		e.translatePoint(translateX, translateY);
		
		for (Component c : components)
			c.mouseDragged(e);
		
		e.translatePoint(-translateX, -translateY);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (!enabled) return;
		
		e.translatePoint(translateX, translateY);
		
		for (Component c : components)
			c.mouseMoved(e);
		
		e.translatePoint(-translateX, -translateY);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!enabled) return;
		
		e.translatePoint(translateX, translateY);
		
		for (Component c : components)
			c.mouseClicked(e);
		
		e.translatePoint(-translateX, -translateY);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (!enabled) return;
		
		e.translatePoint(translateX, translateY);
		
		for (Component c : components)
			c.mousePressed(e);
		
		e.translatePoint(-translateX, -translateY);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (!enabled) return;
		
		e.translatePoint(translateX, translateY);
		
		for (Component c : components)
			c.mouseReleased(e);
		
		e.translatePoint(-translateX, -translateY);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if (!enabled) return;
		
		e.translatePoint(translateX, translateY);
		
		for (Component c : components)
			c.mouseEntered(e);
		
		e.translatePoint(-translateX, -translateY);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		if (!enabled) return;
		
		e.translatePoint(translateX, translateY);
		
		for (Component c : components)
			c.mouseExited(e);
		
		e.translatePoint(-translateX, -translateY);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (!enabled) return;
		
		for (Component c : components)
			c.keyTyped(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!enabled) return;
		
		for (Component c : components)
			c.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (!enabled) return;
		
		for (Component c : components)
			c.keyReleased(e);
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
