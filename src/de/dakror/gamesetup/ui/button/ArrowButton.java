package de.dakror.gamesetup.ui.button;

import java.awt.Graphics2D;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.ui.ClickableComponent;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class ArrowButton extends ClickableComponent {
	public enum ArrowType {
		MINUS_HOR(322, 5),
		PLUS_HOR(360, 5),
		MINUS_VER(419, 5),
		PLUS_VER(457, 5),
		ARROW_L_HOR(556, 5),
		ARROW_R_HOR(595, 5),
		ARROW_U_VER(653, 5),
		ARROW_D_VER(691, 5),
		
		;
		
		int x, y;
		
		private ArrowType(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static int MARGIN = 52;
	
	int tx, ty;
	
	public ArrowButton(int x, int y, ArrowType type) {
		super(x, y, 32, 52);
		
		tx = type.x;
		ty = type.y;
	}
	
	@Override
	public void draw(Graphics2D g) {
		int ty = this.ty + MARGIN * state;
		
		if (!enabled) ty = this.ty + MARGIN * 3;
		
		Helper.drawImage(GameFrame.getImage("gui/gui.png"), x, y, width, height, tx, ty, width, height, g);
	}
	
	@Override
	public void update(int tick) {}
}
