package roundsquare;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Sprite {

	private BufferedImage spritemap;
	
	private Rectangle region;
	
	public Sprite(BufferedImage img, Rectangle rect) {
		this.spritemap = img;
		this.region = rect;
	}

	/**
	 * WARNING: There is currently a bug in this method, where you cannot draw the sprite both scaled and rotated at the same time.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public void draw(int x, int y, int width, int height, double rotation) {
		Graphics2D g = Gfx.gfx.getGraphics();
		if(rotation == 0) {
			g.drawImage(spritemap, x-width/2, y-height/2, width, height, null);
		}
		else {
			BufferedImage bImg = spritemap.getSubimage((int)region.getX(), (int)region.getY(), (int)region.getWidth(), (int)region.getHeight());
			AffineTransform transform = AffineTransform.getRotateInstance(rotation, width/2, height/2);
			g.drawImage(bImg, transform, null);
		}
	}
	
	public BufferedImage getSpritemap() {
		return spritemap;
	}

	public Rectangle getRegion() {
		return region;
	}
}
