package de.dakror.gamesetup.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import de.dakror.gamesetup.GameFrame;

/**
 * @author Dakror
 */
public class Helper
{
	// -- draw helper methods -- //
	public static int[] drawHorizontallyCenteredString(String s, int w, int h, Graphics2D g, int size)
	{
		FontMetrics fm = g.getFontMetrics(g.getFont().deriveFont((float) size));
		int x = (w - fm.stringWidth(s)) / 2;
		
		drawString(s, x, h, g, size);
		
		return new int[] { x, fm.stringWidth(s) };
	}
	
	public static int[] drawHorizontallyCenteredString(String s, int x1, int w, int h, Graphics2D g, int size)
	{
		FontMetrics fm = g.getFontMetrics(g.getFont().deriveFont((float) size));
		int x = x1 + (w - fm.stringWidth(s)) / 2;
		drawString(s, x, h, g, size);
		
		return new int[] { x, fm.stringWidth(s) };
	}
	
	public static int[] drawRightAlignedString(String s, int x1, int y, Graphics2D g, int size)
	{
		FontMetrics fm = g.getFontMetrics(g.getFont().deriveFont((float) size));
		int x = x1 - fm.stringWidth(s);
		drawString(s, x, y, g, size);
		
		return new int[] { x, fm.stringWidth(s) };
	}
	
	public static void drawString(String s, int x, int y, Graphics2D g, int size)
	{
		Font old = g.getFont();
		g.setFont(old.deriveFont((float) size));
		g.drawString(s, x, y);
		
		g.setFont(old);
	}
	
	public static void drawOutline(int x, int y, int width, int height, boolean doubled, Graphics2D g)
	{
		BufferedImage gui = GameFrame.getImage("gui/gui.png");
		
		int cornerSize = (doubled) ? 24 : 19;
		int lineThickness = (doubled) ? 17 : 12;
		int lineHeight = (doubled) ? 55 : 59;
		int lineWidth = (doubled) ? 73 : 74;
		
		// x1 y1 y2 x2
		int[] c = (doubled) ? new int[] { 856, 189, 294, 978 } : new int[] { 865, 398, 498, 982 };
		int[] m = (doubled) ? new int[] { 893, 227, 301, 985 } : new int[] { 899, 428, 505, 989 };
		
		drawImage(gui, x, y, cornerSize, cornerSize, c[0], c[1], cornerSize, cornerSize, g); // lt
		
		for (int i = 0; i < (width - cornerSize * 2) / lineWidth; i++)
			drawImage(gui, x + cornerSize + i * lineWidth, y, lineWidth, lineThickness, m[0], c[1], lineWidth, lineThickness, g); // mt
		drawImage(gui, x + cornerSize + (width - cornerSize * 2) / lineWidth * lineWidth, y, (width - cornerSize * 2) % lineWidth, lineThickness, m[0], c[1], ((width - cornerSize * 2) % lineWidth), lineThickness, g);
		
		drawImage(gui, x + width - cornerSize, y, cornerSize, cornerSize, c[3], c[1], cornerSize, cornerSize, g); // rt
		
		for (int i = 0; i < (height - cornerSize * 2) / lineHeight; i++)
			drawImage(gui, x, y + cornerSize + i * lineHeight, lineThickness, lineHeight, c[0], m[1], lineThickness, lineHeight, g); // ml
		drawImage(gui, x, y + cornerSize + (height - cornerSize * 2) / lineHeight * lineHeight, lineThickness, (height - cornerSize * 2) % lineHeight, c[0], m[1], lineThickness, ((height - cornerSize * 2) % lineHeight), g);
		
		for (int i = 0; i < (height - cornerSize * 2) / lineHeight; i++)
			drawImage(gui, x + width - lineThickness, y + cornerSize + i * lineHeight, lineThickness, lineHeight, m[3], m[1], lineThickness, lineHeight, g); // mr
		drawImage(gui, x + width - lineThickness, y + cornerSize + (height - cornerSize * 2) / lineHeight * lineHeight, lineThickness, (height - cornerSize * 2) % lineHeight, m[3], m[1], lineThickness, ((height - cornerSize * 2) % lineHeight), g);
		
		drawImage(gui, x, y + height - cornerSize, cornerSize, cornerSize, c[0], c[2], cornerSize, cornerSize, g); // lb
		
		for (int i = 0; i < (width - cornerSize * 2) / lineWidth; i++)
			drawImage(gui, x + cornerSize + i * lineWidth, y + height - lineThickness, lineWidth, lineThickness, m[0], m[2], lineWidth, lineThickness, g); // mb
		drawImage(gui, x + cornerSize + (width - cornerSize * 2) / lineWidth * lineWidth, y + height - lineThickness, (width - cornerSize * 2) % lineWidth, lineThickness, m[0], m[2], ((width - cornerSize * 2) % lineWidth), lineThickness, g);
		
		drawImage(gui, x + width - cornerSize, y + height - cornerSize, cornerSize, cornerSize, c[3], c[2], cornerSize, cornerSize, g); // rb
		
	}
	
	public static void drawContainer(int x, int y, int width, int height, boolean doubled, boolean wood, Graphics2D g)
	{
		drawContainer(x, y, width, height, doubled, wood, true, g);
	}
	
	public static void drawContainer(int x, int y, int width, int height, boolean doubled, boolean wood, boolean shadow, Graphics2D g)
	{
		if (shadow) drawShadow(x - 10, y - 10, width + 20, height + 20, g);
		Image image = GameFrame.getImage(wood ? "gui/wood.png" : "gui/paper.png");
		
		Shape oldClip = g.getClip();
		g.setClip(x, y, width, height);
		
		for (int i = x; i < x + width; i += 512)
		{
			for (int j = y; j < y + height; j += 512)
			{
				g.drawImage(image, i, j, GameFrame.w);
			}
		}
		
		g.setClip(oldClip);
		drawOutline(x, y, width, height, doubled, g);
	}
	
	public static void drawShadow(int x, int y, int width, int height, Graphics2D g)
	{
		BufferedImage shadow = GameFrame.getImage("gui/shadow.png");
		
		int size = 32;
		if (width == height && width < 64)
		{
			g.drawImage(shadow, x, y, width, height, GameFrame.w);
			return;
		}
		else if (height < 64)
		{
			shadow = toBufferedImage(shadow.getScaledInstance(height * 3 / 2, height * 3 / 2, Image.SCALE_FAST));
			size = height / 2;
		}
		
		drawImage(shadow, x, y, size, size, 0, 0, size, size, g); // lt
		drawImage(shadow, x + width - size, y, size, size, size * 2, 0, size, size, g); // rt
		drawImage(shadow, x, y + height - size, size, size, 0, size * 2, size, size, g); // lb
		drawImage(shadow, x + width - size, y + height - size, size, size, size * 2, size * 2, size, size, g); // rb
		
		for (int i = x + size; i <= x + width - size * 2; i += size)
			drawImage(shadow, i, y, size, size, size, 0, size, size, g);// t
		drawImage(shadow, x + width - size - (width - size * 2) % size, y, (width - size * 2) % size, size, size, 0, (width - size * 2) % size, size, g);
		
		for (int i = x + size; i <= x + width - size * 2; i += size)
			drawImage(shadow, i, y + height - size, size, size, size, size * 2, size, size, g); // b
		drawImage(shadow, x + width - size - (width - size * 2) % size, y + height - size, (width - size * 2) % size, size, size, size * 2, (width - size * 2) % size, size, g);
		
		for (int i = y + size; i <= y + height - size * 2; i += size)
			drawImage(shadow, x, i, size, size, 0, size, size, size, g); // l
		drawImage(shadow, x, y + height - size - (height - size * 2) % size, size, (height - size * 2) % size, 0, size, size, (height - size * 2) % size, g);
		
		for (int i = y + size; i <= y + height - size * 2; i += size)
			drawImage(shadow, x + width - size, i, size, size, size * 2, size, size, size, g); // r
		drawImage(shadow, x + width - size, y + height - size - (height - size * 2) % size, size, (height - size * 2) % size, size * 2, size, size, (height - size * 2) % size, g);
		
		drawImage(shadow, x + size, y + size, width - size * 2, height - size * 2, size, size, size, size, g); // m
	}
	
	public static void drawImage(Image img, int x, int y, int width, int height, int sx, int sy, int swidth, int sheight, Graphics2D g)
	{
		g.drawImage(img, x, y, x + width, y + height, sx, sy, sx + swidth, sy + sheight, GameFrame.w);
	}
	
	public static void drawImage2(Image img, int x, int y, int width, int height, int sx, int sy, int swidth, int sheight, Graphics2D g)
	{
		g.drawImage(img, x, y, x + width, y + height, sx, sy, sx + swidth, sy + sheight, null);
	}
	
	public static void drawImageCenteredRelativeScaled(Image img, int y, int width, int height, int scaleW, int scaleH, int nowW, int nowH, Graphics2D g)
	{
		Dimension s = getRelativeScaled(new Dimension(width, height), new Dimension(scaleW, scaleH), new Dimension(nowW, nowH));
		
		g.drawImage(img, (GameFrame.getWidth() - s.width) / 2, y, s.width, s.height, GameFrame.w);
	}
	
	public static void drawCooldownCircle(int x, int y, int size, float alpha, Color color, float percentage, Graphics2D g)
	{
		Arc2D arc = new Arc2D.Float(x, y, size, size, 90, percentage * 360, Arc2D.PIE);
		Composite oc = g.getComposite();
		Color o = g.getColor();
		Shape c = g.getClip();
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.setColor(color);
		
		int s = (int) (size / Math.sqrt(2));
		
		g.setClip(new Rectangle(x + (size - s) / 2, y + (size - s) / 2, s, s));
		
		g.fill(arc);
		
		g.setComposite(oc);
		g.setColor(o);
		g.setClip(c);
	}
	
	public static Dimension getRelativeScaled(Dimension src, Dimension scale, Dimension target)
	{
		int w = (src.width * target.width) / scale.width;
		int h = (src.height * target.height) / scale.height;
		
		float sourceRatio = src.width / (float) src.height;
		float targetRatio = w / (float) h;
		
		int width1 = 0;
		int height1 = 0;
		
		if (sourceRatio >= targetRatio)
		{
			width1 = w;
			height1 = Math.round(w / sourceRatio);
		}
		else
		{
			height1 = h;
			width1 = Math.round(h * sourceRatio);
		}
		
		return new Dimension(width1, height1);
	}
	
	public static void drawImageCenteredRelativeScaled(Image img, int y, int scaleW, int scaleH, int nowW, int nowH, Graphics2D g)
	{
		drawImageCenteredRelativeScaled(img, y, img.getWidth(null), img.getHeight(null), scaleW, scaleH, nowW, nowH, g);
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
		BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(img, 0, 0, null);
		
		return image;
	}
	
	/**
	 * @param percent 0 - 1
	 */
	public static void drawProgressBar(int x, int y, int width, float percent, String color, Graphics2D g)
	{
		Image filling = GameFrame.getImage("gui/bar/Bar-" + color + ".png");
		Image base = GameFrame.getImage("gui/bar/BarBase.png");
		
		drawImage(base, x, y, 6, 23, 0, 0, 6, 23, g);
		drawImage(base, x + width - 6, y, 6, 23, 7, 0, 6, 23, g);
		for (int i = x + 6; i < x + width - 6; i++)
			drawImage(base, i, y, 1, 23, 6, 0, 1, 23, g);
		
		int fillWidth = (int) ((width - 12) * percent);
		
		
		if (percent > 0) drawImage(filling, x, y, 6, 23, 0, 0, 6, 23, g);
		if (percent == 1) drawImage(filling, x + width - 6, y, 6, 23, 7, 0, 6, 23, g);
		for (int i = x + 6; i < x + 6 + fillWidth; i++)
			drawImage(filling, i, y, 1, 23, 6, 0, 1, 23, g);
	}
	
	public static void drawStringWrapped(String raw, int x, int y, int maxWidth, Graphics2D g, int size)
	{
		String[] words = raw.split(" ");
		ArrayList<String> lines = new ArrayList<>();
		int lW = 0;
		
		String line = "";
		for (int i = 0; i < words.length; i++)
		{
			String word = words[i] + " ";
			int w = g.getFontMetrics(g.getFont().deriveFont((float) size)).stringWidth(word);
			if (w + lW > maxWidth)
			{
				lW = 0;
				lines.add(line);
				line = "";
			}
			
			line += word;
			lW += w;
		}
		
		lines.add(line);
		
		for (int i = 0; i < lines.size(); i++)
		{
			drawString(lines.get(i), x, y + size * i, g, size);
		}
	}
	
	public static void drawHorizontallyCenteredStringWrapped(String raw, int x, int width, int y, int maxWidth, Graphics2D g, int size)
	{
		String[] words = raw.split(" ");
		ArrayList<String> lines = new ArrayList<>();
		int lW = 0;
		
		String line = "";
		for (int i = 0; i < words.length; i++)
		{
			String word = words[i] + " ";
			int w = g.getFontMetrics(g.getFont().deriveFont((float) size)).stringWidth(word);
			if (w + lW > maxWidth)
			{
				lW = 0;
				lines.add(line);
				line = "";
			}
			
			line += word;
			lW += w;
		}
		
		lines.add(line);
		
		for (int i = 0; i < lines.size(); i++)
		{
			drawHorizontallyCenteredString(lines.get(i), x, width, y + size * i, g, size);
		}
	}
	
	public static int getLineCount(String raw, int maxWidth, Graphics2D g, int size)
	{
		String[] words = raw.split(" ");
		
		int lW = 0;
		
		int rows = 1;
		
		for (int i = 0; i < words.length; i++)
		{
			String word = words[i] + " ";
			int w = g.getFontMetrics(g.getFont().deriveFont((float) size)).stringWidth(word);
			if (w + lW > maxWidth)
			{
				lW = 0;
				rows++;
			}
			lW += w;
		}
		
		return rows;
	}
	
	public static void setRenderingHints(Graphics2D g, boolean on)
	{
		if (on)
		{
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		else
		{
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
	}
	
	// -- file helper methods -- //
	public static void setFileContent(File f, String s)
	{
		f.getParentFile().mkdirs();
		try
		{
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
			osw.write(s);
			osw.close();
		}
		catch (Exception e)
		{}
	}
	
	public static void setFileContent(File f, String s, String charset)
	{
		f.getParentFile().mkdirs();
		try
		{
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f), charset);
			osw.write(s);
			osw.close();
		}
		catch (Exception e)
		{}
	}
	
	public static String getFileContent(File f)
	{
		String res = "", line = "";
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null)
				res += line + "\r\n";
			br.close();
		}
		catch (IOException e)
		{
			return null;
		}
		return res;
	}
	
	public static String getURLContent(URL u)
	{
		String res = "", line = "";
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()));
			while ((line = br.readLine()) != null)
				res += line + "\r\n";
			br.close();
		}
		catch (IOException e)
		{
			return null;
		}
		return res;
	}
	
	public static String getURLContent(URL u, String charset)
	{
		String res = "", line = "";
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream(), charset));
			while ((line = br.readLine()) != null)
				res += line + "\r\n";
			br.close();
		}
		catch (IOException e)
		{
			return null;
		}
		return res;
	}
	
	public static boolean isInternetReachable()
	{
		try
		{
			return InetAddress.getByName("dakror.de").isReachable(60000);
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public static void copyInputStream(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len >= 0)
		{
			out.write(buffer, 0, len);
			len = in.read(buffer);
		}
		in.close();
		out.close();
	}
	
	// -- math helper methods -- //
	
	public static Dimension scaleTo(Dimension input, Dimension wanted)
	{
		float rw = 0;
		float rh = 0;
		float tr = wanted.width / (float) wanted.height;
		float sr = input.width / (float) input.height;
		if (sr >= tr)
		{
			rw = wanted.width;
			rh = rw / sr;
		}
		else
		{
			rh = wanted.height;
			rw = rh * sr;
		}
		
		return new Dimension(Math.round(rw), Math.round(rh));
	}
	
	public static Rectangle round(Rectangle r, int gridSize)
	{
		return new Rectangle(round(r.x, gridSize), round(r.y, gridSize), round(r.width, gridSize), round(r.height, gridSize));
	}
	
	public static int round(int i, int step)
	{
		if (i % step > step / 2.0f) return i + (step - (i % step));
		else return i - (i % step);
	}
	
	public static int round2(int i, int step)
	{
		if (i % step >= step / 2.0f) return i + (step - (i % step));
		else return i - (i % step);
	}
	
	public static String formatBinarySize(long size, int digits)
	{
		final String[] levels = { "", "K", "M", "G", "T" };
		for (int i = levels.length - 1; i > -1; i--)
			if (size > (long) Math.pow(1024, i))
			{
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(digits);
				df.setMinimumFractionDigits(digits);
				return df.format(size / Math.pow(1024, i)) + levels[i] + "B";
			}
		return null;
	}
}
