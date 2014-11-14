package de.dakror.gamesetup.layer;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.ui.Container;

/**
 * @author Dakror
 */
public abstract class Layer extends Container {
	protected boolean modal;
	public boolean consistent;
	
	public Layer() {
		modal = false;
		consistent = false;
	}
	
	public abstract void init();
	
	public boolean isModal() {
		return modal;
	}
	
	public static void drawModality(Graphics2D g) {
		Composite c = g.getComposite();
		Color o = g.getColor();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.setColor(Color.black);
		g.fillRect(0, 0, GameFrame.getWidth(), GameFrame.getHeight());
		g.setColor(o);
		g.setComposite(c);
	}
}
