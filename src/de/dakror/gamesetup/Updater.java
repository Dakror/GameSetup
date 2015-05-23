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
 

package de.dakror.gamesetup;


/**
 * @author Dakror
 */
public abstract class Updater extends Thread {
	public static int TIMEOUT = 16;
	
	public int tick, ticks;
	long time;
	
	public int speed = 1;
	
	public boolean closeRequested = false;
	
	
	public Updater() {
		setPriority(Thread.MAX_PRIORITY);
		start();
	}
	
	@Override
	public void run() {
		tick = 0;
		time = System.currentTimeMillis();
		while (!closeRequested) {
			if (tick == Integer.MAX_VALUE) tick = 0;
			
			if (GameFrame.currentFrame.fade == true) {
				if (GameFrame.currentFrame.alpha != GameFrame.currentFrame.fadeTo) {
					float dif = GameFrame.currentFrame.fadeTo - GameFrame.currentFrame.alpha;
					GameFrame.currentFrame.alpha += dif > 0 ? (dif > GameFrame.currentFrame.speed ? GameFrame.currentFrame.speed : dif) : (dif < -GameFrame.currentFrame.speed
							? -GameFrame.currentFrame.speed : dif);
				} else GameFrame.currentFrame.fade = false;
			}
			
			updateBefore();
			
			for (int i = GameFrame.currentFrame.layers.size() - 1; i >= 0; i--)
				GameFrame.currentFrame.layers.get(i).update(tick);
			
			update();
			
			try {
				tick++;
				ticks++;
				Thread.sleep(Math.round(TIMEOUT / (float) speed));
			} catch (InterruptedException e) {}
		}
	}
	
	public void updateBefore() {}
	
	public abstract void update();
}
