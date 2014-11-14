package de.dakror.gamesetup.layer;

import java.awt.Graphics2D;

import de.dakror.gamesetup.GameFrame;
import de.dakror.gamesetup.ui.ClickEvent;
import de.dakror.gamesetup.ui.button.TextButton;
import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class Alert extends Layer {
	String text;
	ClickEvent event;
	int width, height;
	
	public Alert(String text, ClickEvent event) {
		this.text = text;
		this.event = event;
		modal = true;
		width = 500;
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
		TextButton ok = new TextButton((GameFrame.getWidth() - TextButton.WIDTH) / 2, (GameFrame.getHeight() - height) / 2 + height - 15 - TextButton.HEIGHT, "Ok");
		if (event != null) ok.addClickEvent(event);
		ok.addClickEvent(new ClickEvent() {
			@Override
			public void trigger() {
				GameFrame.currentFrame.removeLayer(Alert.this);
			}
		});
		components.add(ok);
	}
}
