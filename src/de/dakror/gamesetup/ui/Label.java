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

import java.awt.Color;
import java.awt.Graphics2D;

import de.dakror.gamesetup.util.Helper;

public class Label extends Component {
	String text;
	int size;
	int margin;
	Color color;
	
	public Label(int x, int y, String text) {
		super(x, y, 1, 1);
		this.text = text;
		size = 25;
		margin = size;
		color = Color.white;
	}
	
	@Override
	public void draw(Graphics2D g) {
		Color c = g.getColor();
		g.setColor(color);
		
		if (width > 1) Helper.drawHorizontallyCenteredString(text, x, width, y + margin, g, size);
		else Helper.drawString(text, x, y, g, size);
		
		g.setColor(c);
	}
	
	@Override
	public void update(int tick) {}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getMargin() {
		return margin;
	}
	
	public void setMargin(int margin) {
		this.margin = margin;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
