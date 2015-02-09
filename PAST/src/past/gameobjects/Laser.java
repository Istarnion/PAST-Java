package past.gameobjects;

import java.awt.Rectangle;

import past.main.ServiceProvider;
import roundsquare.Animation;
import roundsquare.Sprite;
import roundsquare.Vector2;

public class Laser {
	private Sprite sprite;

	public boolean active;

	private Vector2 startPoint = new Vector2(0, 0);

	private Vector2 endPoint = new Vector2(0, 0);

	private Animation hitAnim;

	private float animRotSpeed = 0.01f;

	private float hitAnimRotation = 0;

	public Laser(LaserColor color)
	{
		switch(color)
		{
			case RED:
				sprite = ServiceProvider.sprites.get("redLaser");
				hitAnim = ServiceProvider.animations.get("rLaserHit");
				break;
			case BLUE:
				sprite = ServiceProvider.sprites.get("redLaser");
				break;
			case GREEN:
				sprite = ServiceProvider.sprites.get("redLaser");
				break;
			default:
				break;
		}
	}

	public void draw(Vector2 camOffset, float delta)
	{
		if(startPoint.x == endPoint.x)	// If perfectly vertical
		{
			sprite.draw((int)(startPoint.x - camOffset.x), (int)((startPoint.y + endPoint.y)/2 - camOffset.y),
					8, (int)(startPoint.subtract(endPoint)).length(),
					0);
		}
		else if(startPoint.y == endPoint.y)	// If perfectly horizontal
		{
			sprite.draw((int)((startPoint.x + endPoint.x) / 2 - camOffset.x), (int)(startPoint.y - camOffset.y),
					8, (int)(startPoint.subtract(endPoint)).length(),
					(float)(Math.PI / 2));
		}
		else	// Any other case
		{
			Vector2 dir = (endPoint.subtract(startPoint));
			float angle = (float)Math.atan(dir.y / dir.x);
			angle += (float)Math.PI/2;
			if (dir.x < 0) angle += (float)Math.PI;
			float length = dir.length();
			dir.normalize();

			sprite.draw(
					(int)((startPoint.x + dir.x * (length / 2)) - camOffset.x),
					(int)((startPoint.y + dir.y * (length / 2)) - camOffset.y),
					8, (int)length,
					angle);
		}

		Rectangle rect = hitAnim.currentFrame().getRegion();
		hitAnim.currentFrame().draw(
				(int)(endPoint.x-camOffset.x), (int)(endPoint.y-camOffset.y),
				(int)rect.getWidth(), (int)rect.getHeight(),
				hitAnimRotation);
		hitAnimRotation += animRotSpeed;
		hitAnim.update(delta);
	}

	public void SetStartPoint(Vector2 pos)
	{
		startPoint = pos;
	}

	public void SetEndPoint(Vector2 pos)
	{
		endPoint = pos;
	}
	
	public enum LaserColor {
		RED,
		BLUE,
		GREEN
	}
}
