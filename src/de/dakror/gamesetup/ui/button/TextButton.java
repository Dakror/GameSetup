/*******************************************************************************
 * Copyright 2015 Maximilian Stark | Dakror <mail@dakror.de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.dakror.gamesetup.ui.button;

import java.awt.Color;
import java.awt.Graphics2D;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.ui.ClickEvent;
import de.dakror.gamesetup.ui.ClickableComponent;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class TextButton extends ClickableComponent {
	public static final int WIDTH = 288;
	public static final int HEIGHT = 59;
	
	public int size = 30;
	public int shiftY = 9;
	
	boolean toggle;
	boolean selected;
	
	String text;
	
	public TextButton(int x, int y, String text) {
		super(x, y, WIDTH, HEIGHT);
		this.text = text;
		addClickEvent(new ClickEvent() {
			@Override
			public void trigger() {
				if (toggle) selected = !selected;
			}
		});
		selected = false;
	}
	
	@Override
	public void draw(Graphics2D g) {
		int ty = state == 0 ? 124 : (state == 1 ? 202 : 280);
		
		if (toggle) {
			ty = selected ? 202 : 124;
		}
		
		if (!enabled) ty = 358;
		Helper.drawImage(GameFrame.getImage("gui/gui.png"), x, y, width, height, 12, ty, WIDTH, HEIGHT, g);
		
		Color c = g.getColor();
		
		if (!enabled) g.setColor(Color.gray);
		else g.setColor(Color.white);
		
		if (toggle && selected) g.setColor(Color.decode("#7cde6a"));
		Helper.drawHorizontallyCenteredString(text, x, width, y + height / 2 + shiftY, g, size);
		
		g.setColor(c);
	}
	
	@Override
	public void update(int tick) {}
	
	public boolean isSelected() {
		if (!toggle) return false;
		
		return selected;
	}
	
	public void setSelected(boolean b) {
		selected = b;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setToggleMode(boolean toggle) {
		this.toggle = toggle;
	}
}
