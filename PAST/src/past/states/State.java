package past.states;

public abstract class State {

	protected StateManager stateManager;
	
	public State(StateManager sm) {
		stateManager = sm;
	}
	
	public abstract void update(float delta);
	
	public abstract void init();
	
	public abstract void resume();
	
	public abstract void pause();

}
