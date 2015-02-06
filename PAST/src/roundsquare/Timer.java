package roundsquare;

import java.util.ArrayList;

/**
 * Timer.java
 * 
 * Creates a new thread that will run in an infinite loop, attempting to keep a fixed
 * timestep, at a given rate.
 * 
 * Every iteration, the timer will call out to it's listeners, informing them that it's time to update the game.
 * 
 * @author Hallgeir Løkken
 */

public class Timer extends Thread {

	ArrayList<TimerWatcher> watchers;
	
	private boolean running = false;
	
	private long targetDelay;
	private float averageDelta; // The average delta time for the last second.
	private long lastUpdate;	// The timestamp for the last update
	private long elapsedTime = 0;
	
	private long tempAvgDelta = 0;
	private int numSamples = 0;
	
	public Timer(int FPS) {
		targetDelay = 1000000000/FPS;
		averageDelta = targetDelay;		// This is a lie, but it's better than having 0 until we can calculate it.
		watchers  = new ArrayList<TimerWatcher>();
		
	}
	
	@Override
	public void run() {
		lastUpdate = System.nanoTime();
		long delta = 0;
		long uDelta = 0;
		long sleep;
		
		running = true;
		
		while(running) {
			
			uDelta = System.nanoTime();
			notifyWatchers(delta/1000000000.0);	// nano to second
			uDelta = System.nanoTime() - uDelta;
			
			delta = System.nanoTime() - lastUpdate;	// calc delta
			elapsedTime += delta;
			
			lastUpdate = System.nanoTime();	// reset lastUpdate			
			
			sleep = (targetDelay - uDelta)/1000000;	// nano to milli
			
			tempAvgDelta += delta;
			numSamples++;
			
			// If it's time to calculate average FPS
			if(numSamples >= 100) {
				averageDelta = tempAvgDelta/numSamples;
				tempAvgDelta = 0;
				numSamples = 0;
			}
			
			if(sleep < 0) {
				sleep = 2;
			}
			try {
				Thread.sleep(sleep);
			}
			catch(InterruptedException e) {
				System.out.println("The timer-thread was interrupted!");
			}
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void cancel() {
		running = false;
	}
	
	public float getFPS() {
		if(averageDelta != 0) 
			return (1000000000/averageDelta);
		else
			return 0;
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}
	
	public void addWatcher(TimerWatcher tw) {
		watchers.add(tw);
	}
	
	private void notifyWatchers(double delta) {
		for(TimerWatcher tw : watchers) {
			tw.update((float)delta);
		}
	}
}
