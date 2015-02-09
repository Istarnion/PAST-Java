package past.gameobjects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import roundsquare.Sprite;
import roundsquare.Vector2;

public class GameObject {

	public final static int TILE_SIZE = 64;

	public int xPos;

	public int yPos;

	public int width;

	public int height;

	protected float rotation = 0;

	private boolean solid;

	private Sprite sprite;

	private boolean[][] solidityMap;

	public GameObject(Sprite sprite, boolean solid, int xPos, int yPos, int width, int height) {
		this.sprite = sprite;
		this.solid = solid;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;

		// Setup the soliditymap
		if (sprite != null) {
			BufferedImage tex = sprite.getSpritemap();
			Rectangle r = sprite.getRegion();

			solidityMap = new boolean[(int)r.getWidth()][(int)r.getHeight()];
			for (int y = 0; y < r.getHeight(); y++)
			{
				for (int x = 0; x < r.getWidth(); x++)
				{
					solidityMap[x][y] = (tex.getRGB(x, y) & 0xFF000000) > 0;
				}
			}
		}
		else {
			solid = false;
		}
	}

	public GameObject(Sprite sprite, boolean solid, int xPos, int yPos, boolean[][] solidityMap) {
		this.sprite = sprite;
		this.solid = solid;
		this.xPos = xPos;
		this.yPos = yPos;
		this.solidityMap = solidityMap;
	}

	public boolean collisionCheck(GameObject other) {
		return !(other.xPos > xPos + width / 2 ||
			other.xPos + other.width / 2 < xPos-width/2 ||
			other.yPos > yPos + height / 2 ||
			other.yPos + other.height / 2 < yPos - height / 2);
	}

	public boolean collisionCheck(int x, int y, int w, int h) {
		return !(	x >= xPos + width ||
					x + w / 2 < xPos - width / 2 ||
					y >= yPos + height ||
					y + h / 2 < yPos - height / 2);
	}

	public boolean pxPerfCollision(int x, int y) {
		if (!solid) return false;

		int w = solidityMap.length;
		int h = solidityMap[0].length;

		int left = xPos - w / 2;
		int top = yPos - h / 2;

		// Early out with seperating axis theorem
		if (x < left || x >= left + w || y < top || y >= top + h) return false;

		// Now we know the point is inside us, so we need to check with the solidity map
		int relX = x - left;
		int relY = y - top;

		return solidityMap[relX][relY];
	}

	public void update(float delta)
	{ }

	public void hitWithLaser(Laser l)
	{ }

	public void draw(float delta, Vector2 camOffset)
	{
		sprite.draw((int)(xPos - camOffset.x), (int)(yPos - camOffset.y), width, height, rotation);
	}

	public GameObject copy()
	{
		return new GameObject(sprite, solid, 0, 0, solidityMap);
	}

}
