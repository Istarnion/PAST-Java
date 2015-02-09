package past.main;

import past.input.Input;
import past.states.MainMenuScreen;
import past.states.SplashScreen;
import past.states.StateManager;
import roundsquare.GameWindow;
import roundsquare.Timer;
import roundsquare.TimerWatcher;


public class PAST implements TimerWatcher {

	private GameWindow window;
	
	private Timer timer;
	
	private StateManager stateManager;
	
	public PAST() {
		window = new GameWindow("P.A.S.T.", 800, 600);
		timer = new Timer(30);
		
		timer.addWatcher(window);
		timer.addWatcher(this);
		
		Input.init(window);
		
		
		stateManager = new StateManager();
		stateManager.init(this, new MainMenuScreen(stateManager));
		stateManager.push(new SplashScreen(stateManager));
		
		timer.start();
	}

	@Override
	public void update(float delta) {
		Input.pollInput();
		
		stateManager.update(delta);
	}
	
	public void emptyStackCallback() {
		window.exit(0);
	}
}
