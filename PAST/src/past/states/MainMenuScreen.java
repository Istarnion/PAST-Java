package past.states;

import past.input.GUIInputListener;
import past.input.Input;
import past.input.Input.InputMode;
import past.input.InputEvent;
import roundsquare.Gfx;

public class MainMenuScreen extends State implements GUIInputListener {

	public final String[] MENU_OPTIONS = {
		"PLAY", "QUIT"	
	};
	
	private int selectedOption = 0;
	
	public MainMenuScreen(StateManager sm) {
		super(sm);
	}

	@Override
	public void update(float delta) {
		for(int i=0; i<MENU_OPTIONS.length; i++) {
			Gfx.gfx.drawString(MENU_OPTIONS[i]+(selectedOption==i?"<":""), 200, 100+i*50);
		}
	}

	@Override
	public void init() {
		// 	Special case for the MainMenuScreen: init() is never called
	}

	@Override
	public void resume() {
		Input.addGUIInputListener(this);
		Input.setInputMode(InputMode.GUI);
	}

	@Override
	public void pause() {
	}

	@Override
	public void handleInput(InputEvent ie) {
		if(!ie.pressEvent) {
			int menuLength = MENU_OPTIONS.length;
			switch(ie.type) {
				case UP:
				{
					selectedOption = (((selectedOption -1) % menuLength)+menuLength)%menuLength;
				} break;
				
				case DOWN:
				{
					selectedOption = (selectedOption +1) % MENU_OPTIONS.length;
				} break;
				
				case SELECT:
				{
					switch(selectedOption) {
						case 0:	// Play game
						{
							System.out.println("PLAY THE GAME");
						} break;
						
						case 1:	// Quit the application
						{
							stateManager.pop();
						} break;
						
						default: break;
					}
				} break;
				
				case BACK:
				{
					
				} break;
				
				default: break;
			}
		}
	}
}
