package past.input;

public class InputSettings {

	public final int UP;
	
	public final int DOWN;
	
	public final int RIGHT;
	
	public final int LEFT;
	
	public final int AIM_UP;
	
	public final int AIM_DOWN;
	
	public final int AIM_RIGHT;
	
	public final int AIM_LEFT;
	
	public final int FIRE;
	
	public final int BACK;
	
	public final int CHANGE_LEFT;
	
	public final int CHANGE_RIGHT;

	public InputSettings(int uP, int dOWN, int rIGHT, int lEFT,
			int aIM_UP, int aIM_DOWN, int aIM_RIGHT, int aIM_LEFT,
			int fIRE, int bACK, int cHANGE_LEFT, int cHANGE_RIGHT) {
		UP = uP;
		DOWN = dOWN;
		RIGHT = rIGHT;
		LEFT = lEFT;
		AIM_UP = aIM_UP;
		AIM_DOWN = aIM_DOWN;
		AIM_RIGHT = aIM_RIGHT;
		AIM_LEFT = aIM_LEFT;
		FIRE = fIRE;
		BACK = bACK;
		CHANGE_LEFT = cHANGE_LEFT;
		CHANGE_RIGHT = cHANGE_RIGHT;
	}
	
	public static InputSettings getDefaultKeyboardSettings() {
		return new InputSettings(
				KeyCode.VK_W, KeyCode.VK_S, KeyCode.VK_D, KeyCode.VK_A,
				MouseCode.AXIS_Y.ordinal(), MouseCode.AXIS_Y.ordinal(), MouseCode.AXIS_X.ordinal(), MouseCode.AXIS_X.ordinal(),
				KeyCode.VK_SPACE, KeyCode.VK_ESCAPE,
				KeyCode.VK_Q, KeyCode.VK_E);
	}

}
