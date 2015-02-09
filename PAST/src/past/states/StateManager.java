package past.states;

import java.util.Stack;

import past.main.PAST;

public class StateManager {

	private Stack<State> states;
	
	private PAST game;
	
	public StateManager() {
		states = new Stack<State>();
	}
	
	public void init(PAST game, State state) {
		this.game = game;
		states.push(state);
	}
	
	public State pop() {
		State s = states.pop();
		if(states.isEmpty()) game.emptyStackCallback();
		else states.peek().resume();
		return s;
	}

	public void push(State s) {
		states.peek().pause();
		states.push(s).init();
	}
	
	public State peek() {
		return states.peek();
	}
	
	public void update(float delta) {
		if(!states.isEmpty()) {
			states.peek().update(delta);
		}
	}
}
