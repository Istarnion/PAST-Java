package past.rooms;

import java.util.ArrayList;

import past.gameobjects.GameObject;
import roundsquare.Sprite;
import roundsquare.Vector2;

public abstract class Room
{

	public final int width;
	public final int height;

	private ArrayList<GameObject> gameObjects;

	protected Sprite background;

	public Room(int width, int height)
	{
		this.width = width;
		this.height = height;
		gameObjects = new ArrayList<GameObject>(50);
	}

	public void add(GameObject obj) {
		gameObjects.add(obj);
	}

	public void update(float delta) {
		for(GameObject obj : gameObjects) {
			obj.update(delta);
		}
	}

	public void draw(float delta, Vector2 camOffset)
	{
		if(background != null)
		{
			background.draw((int)(width / 2 - camOffset.x), (int)(height / 2 - camOffset.y), width, height, 0);
		}

		for(GameObject obj : gameObjects)
		{
			obj.draw(delta, camOffset);
		}
	}

	public boolean collisionCheck(GameObject go) {
		int goHalfWidth		= go.width	/ 2;
		int goHalfHeight	= go.height	/ 2;
		if(go.xPos - goHalfWidth < 0 || go.xPos + goHalfWidth > width
			|| go.yPos - goHalfHeight < 0 || go.yPos + goHalfHeight > height)
		{
			return true;
		}

		for(GameObject g : gameObjects)
		{
			if (go.collisionCheck(g)) return true;
		}

		return false;
	}

	public boolean CollisionCheck(int x, int y, int w, int h) {
		int halfWidth = w / 2;
		int halfHeight = h / 2;
		if (x - halfWidth < 0 || x + halfWidth > width
			|| y - halfHeight < 0 || y + halfHeight > height)
		{
			return true;
		}

		for(GameObject g : gameObjects)
		{
			if (g.collisionCheck(x, y, w, h)) return true;
		}

		return false;
	}

	public RayHitInfo raycast(Vector2 origin, Vector2 dir) {
		dir = dir.normalize();
		Vector2 endpoint = origin;
		GameObject victim = null;

		float dx = dir.x;
		float dy = dir.y;

		float x = origin.x;
		float y = origin.y;

		boolean hitObject = false;
		int length = 0;
		while (true)
		{
			x += dx;
			y += dy;

			// Then, check if we are still inside the bounds of the room
			if (x < 0 || x >= width || y < 0 || y >= height)
			{
				endpoint = new Vector2(x, y);
				break;
			}

			// If we are, chek against all game objects. This may be optimized with something like a quad tree if needed
			for(GameObject gObject : gameObjects)
			{
				if (gObject.pxPerfCollision((int)x, (int)y))
				{
					victim = gObject;
					endpoint = new Vector2(x, y);
					hitObject = true;
					break;
				}
			}
			if (hitObject) break;

			length++;
		}

		RayHitInfo info = new RayHitInfo(origin, endpoint, victim, length);
		return info;
	}
}
