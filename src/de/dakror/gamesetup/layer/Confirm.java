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


package de.dakror.gamesetup.layer;

import java.awt.Graphics2D;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.ui.ClickEvent;
import de.dakror.gamesetup.ui.button.TextButton;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class Confirm extends Layer {
	String text;
	ClickEvent eventYes, eventNo;
	int width, height;
	
	public Confirm(String text, ClickEvent eventYes, ClickEvent eventNo) {
		this.text = text;
		this.eventYes = eventYes;
		this.eventNo = eventNo;
		modal = true;
		width = TextButton.WIDTH * 2 + 30;
		height = 0;
	}
	
	@Override
	public void draw(Graphics2D g) {
		drawModality(g);
		int x = (GameFrame.getWidth() - width) / 2;
		int y = (GameFrame.getHeight() - height) / 2;
		if (height == 0) {
			height = 20 + Helper.getLineCount(text, width - 30, g, 30) * 30 + TextButton.HEIGHT + 50;
			components.clear();
			init();
		}
		Helper.drawContainer(x, y, width, height, true, false, g);
		Helper.drawHorizontallyCenteredStringWrapped(text, x, width, y + 45, width - 30, g, 30);
		
		drawComponents(g);
	}
	
	@Override
	public void update(int tick) {
		updateComponents(tick);
	}
	
	@Override
	public void init() {
		TextButton cnc = new TextButton(GameFrame.getWidth() / 2 - TextButton.WIDTH, (GameFrame.getHeight() - height) / 2 + height - 15 - TextButton.HEIGHT, "Abbruch");
		if (eventNo != null) cnc.addClickEvent(eventNo);
		cnc.addClickEvent(new ClickEvent() {
			@Override
			public void trigger() {
				GameFrame.currentFrame.removeLayer(Confirm.this);
			}
		});
		components.add(cnc);
		
		TextButton ok = new TextButton(GameFrame.getWidth() / 2, (GameFrame.getHeight() - height) / 2 + height - 15 - TextButton.HEIGHT, "Ok");
		if (eventYes != null) ok.addClickEvent(eventYes);
		ok.addClickEvent(new ClickEvent() {
			@Override
			public void trigger() {
				GameFrame.currentFrame.removeLayer(Confirm.this);
			}
		});
		components.add(ok);
	}
}
