package past.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import past.input.InputEvent.InputType;
import roundsquare.GameWindow;
import roundsquare.Vector2;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

	private static Input input = new Input();	// Static singleton instance
	
	private boolean usingGamepad;
	
	private Controller gamepad;

	private float[] gamepadState;
	
	private Vector2 moveVector = new Vector2(0, 0);
	
	private Vector2 aimVector = new Vector2(0, 0);
	
	private boolean fireBtn, backBtn, shiftRightBtn, shiftLeftBtn;
	
	private InputSettings keyboardSettings;
	
	private InputSettings gamepadSettings;
	
	private InputMode inputMode = InputMode.GUI;
	
	private ArrayList<GUIInputListener> guiInputListeners;
	
	/* Private constructor */
	private Input() {
		gamepadState = new float[GamepadCode.values().length];
		keyboardSettings = InputSettings.getDefaultKeyboardSettings();
	}
	
	public static void init(GameWindow gw) {
		gw.addKeyListener(input);
		gw.addMouseListener(input);
		gw.addMouseMotionListener(input);
		
		input.resetGamepad();
	}
	
	/**
	 * This is the update method.
	 * This must be called from the main loop
	 */
	public static void pollInput() {
		if(input.gamepad != null) {
			pollGamepad(input.gamepad);
			// TODO: Set the stuff according to the gamepadState and the gamepad InputSettings
		}
	}
	
	public static Vector2 getMovement() {
		return input.moveVector;
	}
	
	public static Vector2 getAim(Vector2 posOnScreen) {
		if(input.usingGamepad) return input.aimVector;
		else {
			return input.aimVector.subtract(posOnScreen).normalize();
		}
	}
	
	public static boolean getFireButtonDown() {
		return input.fireBtn;
	}
	
	public static boolean getBackButtonDown() {
		return input.backBtn;
	}
	
	public static boolean getShiftLeftButtonDown() {
		return input.shiftLeftBtn;
	}
	
	public static boolean getShiftRightButtonDown() {
		return input.shiftRightBtn;
	}
	
	public static boolean isUsingGamepad() {
		return input.usingGamepad;
	}
	
	public static InputMode getInputMode() {
		return input.inputMode;
	}

	public static void setInputMode(InputMode inputMode) {
		input.inputMode = inputMode;
	}

	public enum InputMode {
		GUI,
		GAME
	}

	private static void pollGamepad(Controller gamepad) {
		try {
			if(!input.gamepad.poll()) {
				System.out.println("DEBUG");
				throw new Exception();
			}
			
			Component[] comps = input.gamepad.getComponents();
			for (Component comp : comps) {
				switch(comp.getIdentifier().toString()) {
					case "x":	// The left thumbstick x axis
					{
						input.gamepadState[GamepadCode.LEFT_X.ordinal()] = comp.getPollData();
					} break;
					
					case "y":	// The left thumbstick y axis
					{
						input.gamepadState[GamepadCode.LEFT_Y.ordinal()] = comp.getPollData();
					} break;
					
					case "rx":	// The right thumbstick x axis
					{
						input.gamepadState[GamepadCode.RIGHT_X.ordinal()] = comp.getPollData();
					} break;
					
					case "ry":	// The right thumbstick y axis
					{
						input.gamepadState[GamepadCode.RIGHT_Y.ordinal()] = comp.getPollData();
					} break;
					
					case "z":	// The triggers
					{
						int lt = GamepadCode.LEFT_TRIGGER.ordinal();
						int rt = GamepadCode.RIGHT_TRIGGER.ordinal();
						float value = comp.getPollData();
						if(value > 0.18f) input.gamepadState[lt] = value;
						else if(value < -0.18f) input.gamepadState[rt] = value*-1;
						else {
							input.gamepadState[lt]	= 0;
							input.gamepadState[rt]	= 0;
						}
					} break;
					
					case "0":	// The A button
					{
						input.gamepadState[GamepadCode.A.ordinal()] = comp.getPollData();
					} break;
					
					case "1":	// The B button
					{
						input.gamepadState[GamepadCode.B.ordinal()] = comp.getPollData();
					} break;
					
					case "2":	// The X button
					{
						input.gamepadState[GamepadCode.X.ordinal()] = comp.getPollData();
					} break;
					
					case "3":	// The Y button
					{
						input.gamepadState[GamepadCode.Y.ordinal()] = comp.getPollData();
					} break;
					
					case "4":	// The left shoulder button
					{
						input.gamepadState[GamepadCode.LEFT_SHOULDER.ordinal()] = comp.getPollData();
					} break;
					
					case "5":	// The right shoulder button
					{
						input.gamepadState[GamepadCode.RIGHT_SHOULDER.ordinal()] = comp.getPollData();
					} break;
					
					case "6":	// The back button
					{
						input.gamepadState[GamepadCode.BACK.ordinal()] = comp.getPollData();
					} break;
					
					case "7":	// The start button
					{
						input.gamepadState[GamepadCode.START.ordinal()] = comp.getPollData();
					} break;
					default: break;
				}
			}
		}
		catch(Exception e) {
			if(input.resetGamepad()) {
				pollGamepad(input.gamepad);
			}
			else {
				System.out.println("failed polling gamepad. Disabeling..");
				input.usingGamepad = false;
			}
		}
	}
	
	private boolean resetGamepad() {
		Controller[] controllers;
		try {
			controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
			for (int i = 0; i < controllers.length; i++) {
				if(controllers[i].getType() == Type.GAMEPAD) {
					input.gamepad = controllers[i];
					return true;	// We found our gamepad.
				}
			}
		}
		catch(Exception e) {
			return false;			// If JInput fails somehow
		}
		
		return false;				// If it can't find any gamepads
	}
	
	public static void addGUIInputListener(GUIInputListener gil) {
		if(input.guiInputListeners == null) input.guiInputListeners = new ArrayList<GUIInputListener>(3);
		input.guiInputListeners.add(gil);
	}
	
	public static void removeGUIInputListener(GUIInputListener gil) {
		if(input.guiInputListeners == null) {
			System.out.println("Trying to remove guiListener with none added.");
			return;
		}
		if(!input.guiInputListeners.remove(gil)) {
			System.out.println("Trying to remove a guiListener that has not been added.");
		}
	}
	
	private void broadcastInputEvent(InputEvent ie) {
		if(guiInputListeners != null) {
			for(GUIInputListener gil : guiInputListeners) {
				gil.handleInput(ie);
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent me) {
	}

	@Override
	public void mouseMoved(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		usingGamepad = false;
		if(inputMode == InputMode.GUI && guiInputListeners != null) {
			int kcode = ke.getKeyCode();
			if(kcode == keyboardSettings.UP) {
				broadcastInputEvent(new InputEvent(true, InputType.UP));
			}
			else if(kcode == keyboardSettings.DOWN) {
				broadcastInputEvent(new InputEvent(true, InputType.DOWN));
			}
			else if(kcode == keyboardSettings.LEFT) {
				broadcastInputEvent(new InputEvent(true, InputType.LEFT));
			}
			else if(kcode == keyboardSettings.RIGHT) {
				broadcastInputEvent(new InputEvent(true, InputType.RIGHT));
			}
			else if(kcode == keyboardSettings.FIRE) {
				broadcastInputEvent(new InputEvent(true, InputType.SELECT));
			}
			else if(kcode == keyboardSettings.BACK) {
				broadcastInputEvent(new InputEvent(true, InputType.BACK));
			}
		}
		else {
			int kcode = ke.getKeyCode();
			if(kcode == keyboardSettings.UP) {
				// update movement vector
			}
			else if(kcode == keyboardSettings.DOWN) {
				// Same
			}
			else if(kcode == keyboardSettings.LEFT) {
				// Same
			}
			else if(kcode == keyboardSettings.RIGHT) {
				// AAAAAnd same
			}
			else if(kcode == keyboardSettings.FIRE) {
				fireBtn = true;
			}
			else if(kcode == keyboardSettings.BACK) {
				backBtn = true;
			}
			else if(kcode == keyboardSettings.CHANGE_LEFT) {
				shiftLeftBtn = true;
			}
			else if(kcode == keyboardSettings.CHANGE_RIGHT) {
				shiftRightBtn = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		if(inputMode == InputMode.GUI && input.guiInputListeners != null) {
			int kcode = ke.getKeyCode();
			if(kcode == keyboardSettings.UP) {
				broadcastInputEvent(new InputEvent(false, InputType.UP));
			}
			else if(kcode == keyboardSettings.DOWN) {
				broadcastInputEvent(new InputEvent(false, InputType.DOWN));
			}
			else if(kcode == keyboardSettings.LEFT) {
				broadcastInputEvent(new InputEvent(false, InputType.LEFT));
			}
			else if(kcode == keyboardSettings.RIGHT) {
				broadcastInputEvent(new InputEvent(false, InputType.RIGHT));
			}
			else if(kcode == keyboardSettings.FIRE) {
				broadcastInputEvent(new InputEvent(false, InputType.SELECT));
			}
			else if(kcode == keyboardSettings.BACK) {
				broadcastInputEvent(new InputEvent(false, InputType.BACK));
			}
		}
		else {
			int kcode = ke.getKeyCode();
			if(kcode == keyboardSettings.UP) {
				// update movement vector
			}
			else if(kcode == keyboardSettings.DOWN) {
				// Same
			}
			else if(kcode == keyboardSettings.LEFT) {
				// Same
			}
			else if(kcode == keyboardSettings.RIGHT) {
				// AAAAAnd same
			}
			else if(kcode == keyboardSettings.FIRE) {
				fireBtn = false;
			}
			else if(kcode == keyboardSettings.BACK) {
				backBtn = false;
			}
			else if(kcode == keyboardSettings.CHANGE_LEFT) {
				shiftLeftBtn = false;
			}
			else if(kcode == keyboardSettings.CHANGE_RIGHT) {
				shiftRightBtn = false;
			}
		}
	}
	
	/* Not needed functionality from the implemented interfaces */
	@Override
	public void mouseEntered(MouseEvent me) {}
	
	@Override
	public void mouseExited(MouseEvent me) {}

	@Override
	public void mouseClicked(MouseEvent me) {}

	@Override
	public void keyTyped(KeyEvent ke) {}
}
