package roundsquare;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GameWindow  implements TimerWatcher {

	private Canvas canvas;
	
	private RenderingHints hints;
	
	public GameWindow(String title, int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.BLACK);
		
		Gfx.setup(width, height);
		
		hints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		canvas = new Canvas();
		
		canvas.setPreferredSize(new Dimension(width, height));
		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(2);
			return;
		}
		
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		g.setRenderingHints(hints);
		
		g.drawImage(Gfx.gfx.getCanvas(), 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		Gfx.gfx.clearCanvas();
		
		g.dispose();
		
		bs.show();
		Toolkit.getDefaultToolkit().sync();
		
		canvas.requestFocus();
	}
	
	public void addKeyListener(KeyListener kl) {
		canvas.addKeyListener(kl);
		canvas.setFocusable(true);
	}
	
	public void addMouseListener(MouseListener ml) {
		canvas.addMouseListener(ml);;
		canvas.setFocusable(true);
	}
	
	public void addMouseMotionListener(MouseMotionListener mml) {
		canvas.addMouseMotionListener(mml);
		canvas.setFocusable(true);
	}
	
	@Override
	public void update(float delta) {
		render();
	}
}
