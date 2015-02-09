package past.states;

import roundsquare.Gfx;

public class SplashScreen extends State {

	private float targetLifetime = 3.0f;
	
	private float lifetime = 0;
	
	public SplashScreen(StateManager sm) {
		super(sm);
	}

	@Override
	public void update(float delta) {
		lifetime += delta;
		if(lifetime >= targetLifetime) {
			stateManager.pop();
		}
		
		Gfx.gfx.drawCircle(Gfx.gfx.WIDTH/2, Gfx.gfx.HEIGHT/2, 100, 100);
	}
	
	@Override
	public void init() {
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void pause() {
	}

}
