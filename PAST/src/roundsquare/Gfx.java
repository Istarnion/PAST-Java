package roundsquare;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Gfx {

	/**
	 * The singleton instance
	 */
	public static Gfx gfx;
	
	private BufferedImage canvas;
	
	public final int WIDTH, HEIGHT;
	
	private final Color mainColor = Color.BLACK;
	private final Color borderColor = Color.WHITE;
	
	private Graphics2D g;
	
	private Gfx(int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		
		canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		g = canvas.createGraphics();
	}

	/**
	 * Draws a shape of the given size, at the given position.
	 * This methods simply forwards the call to the more specific methods also provided in this class.
	 * <br>
	 * Please note that these methods for drawing is implemented with simplicity in mind, not performance.
	 * A Graphics2D object is created and disposed of for each call.
	 *  
	 * @param s	The shape to draw
	 * @param x	The x-pos at which to draw the circle
	 * @param y	The y-pos at which to draw the circle
	 * @param w	The width at which to draw the circle
	 * @param h	The height at which to draw the circle
	 */
	public void draw(Shape s, double x, double y, double w, double h) {
		switch(s) {
			case SQUARE:
			{
				drawSquare(x, y, w, h);
			} break;
			
			case CIRCLE:
			{
				drawCircle(x, y, w, h);
			} break;
			
			default: break;
		}
	}
	
	/**
	 * Draws a rectangle of the given size, at the given position.
	 *  
	 * @param x	The x-pos at which to draw the rect
	 * @param y	The y-pos at which to draw the rect
	 * @param w	The width at which to draw the rect
	 * @param h	The height at which to draw the rect
	 */
	public void drawSquare(double x, double y, double w, double h) {
		g.setColor(mainColor);
		g.fillRect((int)(x-w/2), (int)(y-h/2), (int)w, (int)h);
		g.setColor(borderColor);
		g.drawRect((int)(x-w/2), (int)(y-h/2), (int)w, (int)h);
	}
	
	/**
	 * Draws a circle of the given size, at the given position.
	 *  
	 * @param x	The x-pos at which to draw the circle
	 * @param y	The y-pos at which to draw the circle
	 * @param w	The width at which to draw the circle
	 * @param h	The height at which to draw the circle
	 */
	public void drawCircle(double x, double y, double w, double h) {
		Graphics2D g = canvas.createGraphics();
		
		g.setColor(mainColor);
		g.fillOval((int)(x-w/2), (int)(y-h/2), (int)w, (int)h);
		g.setColor(borderColor);
		g.drawOval((int)(x-w/2), (int)(y-h/2), (int)w, (int)h);
		
		g.dispose();
	}
	
	/**
	 * Draws a line from (x1, y1) to (x2, y2).
	 *  
	 * @param x1	The x-pos of the first point
	 * @param y1	The y-pos of the first point
	 * @param x2	The x-pos of the other point
	 * @param y2	The y-pos of the other point
	 */
	public void drawLine(double x1, double y1, double x2, double y2) {
		g.setColor(borderColor);
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
	
	public void drawString(String text, double x, double y) {
		g.setColor(borderColor);
		g.drawString(text, (int)x, (int)y);
	}
	
	/**
	 * Gives access to the Graphics2D object directly, for more advanced drawing.
	 * 
	 * @return The Graphics2D object connected to the internal BufferedImage of this class
	 */
	public Graphics2D getGraphics() {
		return g;
	}
	
	public Color getMainColor() {
		return mainColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public BufferedImage getCanvas() {
		return canvas;
	}
	
	public void clearCanvas() {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	static void setup(int w, int h) {
		gfx = new Gfx(w, h);
	}
}
