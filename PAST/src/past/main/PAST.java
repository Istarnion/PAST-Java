package past.main;

import past.input.Input;
import roundsquare.GameWindow;
import roundsquare.Timer;
import roundsquare.TimerWatcher;


public class PAST implements TimerWatcher {

	private GameWindow window;
	
	private Timer timer;
	
	public PAST() {
		window = new GameWindow("P.A.S.T.", 800, 600);
		timer = new Timer(30);
		
		timer.addWatcher(this);
		timer.addWatcher(window);
		
		Input.init(window);
		
		timer.start();
	}

	@Override
	public void update(float delta) {
		Input.pollInput();
	}
}
