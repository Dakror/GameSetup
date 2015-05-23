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
 

package de.dakror.gamesetup.ui;

import java.awt.Graphics2D;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class Checkbox extends ClickableComponent {
	public static final int WIDTH = 44;
	public static final int HEIGHT = 44;
	
	boolean selected;
	
	public Checkbox(int x, int y) {
		super(x, y, WIDTH, HEIGHT);
		addClickEvent(new ClickEvent() {
			@Override
			public void trigger() {
				selected = !selected;
			}
		});
		selected = false;
	}
	
	@Override
	public void draw(Graphics2D g) {
		int tx = 138;
		if (state == 2) tx = 183;
		if (!enabled) tx = 228;
		
		Helper.drawImage(GameFrame.getImage("gui/gui.png"), x, y, width, height, tx, selected ? 47 : 3, WIDTH, HEIGHT, g);
	}
	
	@Override
	public void update(int tick) {}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
