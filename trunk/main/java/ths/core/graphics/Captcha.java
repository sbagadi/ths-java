package ths.core.graphics;

import java.util.Random;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Captcha 
{
	private Random random;
	private int imgWidth;
	private int imgHeight;
	private boolean useDisturbLine;
	
	public Captcha()
	{
		this.random = new Random();
		this.imgWidth = 100;
		this.imgHeight = 40;
		this.useDisturbLine = false;
	}
	
	public void setImageSize(int width, int height)
	{
		this.imgWidth = width;
		this.imgHeight = height;
	}
	
	public void setDrawDisturbLine(boolean isDraw)
	{
		this.useDisturbLine = isDraw;
	}
	
	public String getRandString(int length)
	{
		//char[] chars = "abcdefghijkmnpqrstuvwxyz23456789".toCharArray();
		char[] chars = "ABCDEFGHJKMNPQRSTUVWXYZ23456789".toCharArray();
		char[] buf = new char[length];
		
		for(int i=0; i<length; i++)
		{
			buf[i] = chars[random.nextInt(chars.length)];
		}
		
		return new String(buf);
	}
	
	public BufferedImage getImage(String words)
	{
		Color fontColor = getRandomFontColor();
			
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graph = (Graphics2D)image.getGraphics();
		
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		graph.setRenderingHints(hints);
		
		graph.setColor(Color.WHITE);   
		graph.fillRect(0, 0, imgWidth, imgHeight);
		
		graph.setFont(new Font("Arial", Font.PLAIN, 30));
		graph.setColor(fontColor);
		graph.drawString(words, 10, 30);

		int randHeight = random.nextInt(imgHeight/2)+(imgHeight/4);
		if (this.useDisturbLine) {
			drawDisturbLine(graph, 2, randHeight, imgWidth-2, randHeight, 1, fontColor);
		}
		contort(graph, imgWidth, imgHeight, Color.WHITE);
		
		graph.dispose();
		return image;
	}
	
	private void contort(Graphics g, int w1, int h1, Color color) 
	{
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	private void shearX(Graphics g, int w1, int h1, Color color) 
	{
		int period = random.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (6.2831853071795862D * (double) phase)
							/ (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}
	}

	private void shearY(Graphics g, int w1, int h1, Color color) 
	{
		int period = random.nextInt(4) + 6; // 50;

		boolean borderGap = true;
		int frames = 2;
		int phase = 4;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (6.2831853071795862D * (double) phase)
							/ (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}
		}
	}
	
	private void drawDisturbLine(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c) 
	{

		// The thick line is in fact a filled polygon
		g.setColor(c);
		int dX = x2 - x1;
		int dY = y2 - y1;
		
		// line length
		double lineLength = Math.sqrt(dX * dX + dY * dY);
		double scale = (double) (thickness) / (2 * lineLength);

		// The x and y increments from an endpoint needed to create a rectangle...
		double ddx = -scale * (double) dY;
		double ddy = scale * (double) dX;
		ddx += (ddx > 0) ? 0.5 : -0.5;
		ddy += (ddy > 0) ? 0.5 : -0.5;
		int dx = (int) ddx;
		int dy = (int) ddy;

		// Now we can compute the corner points...
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];

		xPoints[0] = x1 + dx;
		yPoints[0] = y1 + dy;
		xPoints[1] = x1 - dx;
		yPoints[1] = y1 - dy;
		xPoints[2] = x2 - dx;
		yPoints[2] = y2 - dy;
		xPoints[3] = x2 + dx;
		yPoints[3] = y2 + dy;

		g.fillPolygon(xPoints, yPoints, 4);
	}	
	
	private Color getRandomFontColor()
	{
		Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
		return colors[random.nextInt(colors.length)];
	}	
	
}
