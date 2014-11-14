package de.dakror.gamesetup.util.path;

import java.awt.Color;
import java.awt.Graphics2D;

import de.dakror.gamesetup.util.Vector;

/**
 * @author Dakror
 */
public class Node {
	public float F, G, H;
	public Node p;
	public Vector t;
	public boolean inPath;
	
	public Node(float G, float H, Vector t, Node p) {
		this.G = G;
		this.H = H;
		F = G + H;
		this.p = p;
		this.t = t;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node n = (Node) obj;
			return t.equals(n.t);
		}
		
		return false;
	}
	
	public void draw(int scale, Color c, Graphics2D g) {
		Color o = g.getColor();
		g.setColor(c);
		if (inPath) g.setColor(Color.red);
		if (p != null) {
			g.drawLine((int) ((t.x + 0.5f) * scale), (int) ((t.y + 0.5f) * scale), (int) ((p.t.x + 0.5f) * scale), (int) ((p.t.y + 0.5f) * scale));
		}
		g.fillOval((int) ((t.x + 0.5f) * scale) - 5, (int) ((t.y + 0.5f) * scale) - 5, 10, 10);
		g.setColor(o);
	}
	
	@Override
	public Node clone() {
		inPath = true;
		return new Node(G, H, t.clone(), p != null ? p.clone() : null);
	}
	
	@Override
	public String toString() {
		return t.toString();
	}
}
