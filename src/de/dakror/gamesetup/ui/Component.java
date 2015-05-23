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
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import de.dakror.gamesetup.util.Drawable;
import de.dakror.gamesetup.util.EventListener;

/**
 * @author Dakror
 */
public abstract class Component extends EventListener implements Drawable {
	public int x, y, width, height;
	/**
	 * 0 = default<br>
	 * 1 = pressed<br>
	 * 2 = hovered<br>
	 */
	public int state;
	public boolean enabled;
	
	public Component(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		state = 0;
		enabled = true;
	}
	
	public void drawTooltip(int x, int y, Graphics2D g) {}
	
	public boolean contains(int x, int y) {
		return new Rectangle(this.x, this.y, width, height).contains(x, y);
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (contains(e.getX(), e.getY()) && enabled) state = 1;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (contains(e.getX(), e.getY()) && enabled) state = 2;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		state = contains(e.getX(), e.getY()) ? 2 : 0;
	}
}
