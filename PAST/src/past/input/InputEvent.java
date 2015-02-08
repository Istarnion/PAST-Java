package past.input;

public class InputEvent {

	public final boolean pressEvent;
	
	public final InputType type;
	
	public InputEvent(boolean pressEvent, InputType type) {
		this.pressEvent = pressEvent;
		this.type = type;
	}

	public enum InputType {
		LEFT,
		RIGHT,
		UP,
		DOWN,
		SELECT,
		BACK
	}
}
