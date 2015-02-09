package past.rooms;

import past.gameobjects.GameObject;
import roundsquare.Vector2;

public class RayHitInfo {

	public final Vector2 origin;

	public final Vector2 end;

	public final GameObject victim;

	public final int length;

	public RayHitInfo(Vector2 o, Vector2 e, GameObject v, int length)
	{
		origin = o;
		end = e;
		victim = v;
		this.length = length;
	}
}
