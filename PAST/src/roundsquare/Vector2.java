package roundsquare;

public class Vector2 {

	public float x = 0, y = 0;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2() {}
	
	public float length() {
		return (float)Math.sqrt(x*x + y*y);
	}

	public float lengthSquared() {
		return (x*x + y*y);
	}
	
	public Vector2 normalize() {
		float l = length();
		if(l == 0) return this;
		
		x /= l;
		y /= l;
		return this;
	}
	
	public Vector2 scale(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}
	
	public Vector2 add(Vector2 vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}
	
	public Vector2 subtract(Vector2 vec) {
		x -= vec.x;
		y -= vec.y;
		return this;
	}
	
	public float distanceTo(Vector2 vec) {
		Vector2 v = this.subtract(vec);
		return v.length();
	}
	
	public static float distanceBetween(Vector2 v1, Vector2 v2) {
		return v1.distanceTo(v2);
	}
	
	public float dot(Vector2 vec) {
		return ((x * vec.x)+(y * vec.y));
	}
	
	public static float dot(Vector2 v1, Vector2 v2) {
		return v1.dot(v2);
	}
	
	public float angle() {
		return (float)Math.atan(y / x);
	}
	
	public float angleTo(Vector2 vec) {
		float dot = this.dot(vec);
		return (dot / (this.length() * vec.length()));
	}
	
	public static float angleBetween(Vector2 v1, Vector2 v2) {
		return v1.angleTo(v2);
	}
}
