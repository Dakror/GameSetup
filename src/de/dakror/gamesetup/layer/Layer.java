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
