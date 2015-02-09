package roundsquare;

public class Animation {

	private Sprite[] frames;

	private float frameTime;

	private double lastFrame = 0;

	public boolean pause;

	private int currFrame = 0;

	public Animation(Sprite[] frames, float frameTime)
	{
		this.frames = frames;
		this.frameTime = frameTime;
	}

	public void update(float delta)
	{
		if (!pause) lastFrame += delta;
		if(lastFrame >= frameTime)
		{
			lastFrame = 0;
			currFrame++;
			if (currFrame >= frames.length) currFrame = 0;
		}
	}

	public Sprite currentFrame()
	{
		return frames[currFrame];
	}
}
