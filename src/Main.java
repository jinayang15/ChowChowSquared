import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.*;

public class Main extends JPanel implements Runnable, KeyListener {
	
	public static int winWidth = 520;
	public static int winHeight = 400;
	// 13 x 10 tiles
	public static int tileSize = 40;
	public Main() {
		setPreferredSize(new Dimension(winWidth,winHeight));
		setBackground(new Color(50, 250, 250));
		this.setFocusable(true);
		addKeyListener(this);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Main panel = new Main();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	public void paintComponent(Graphics g) {
		for (int i = 0; i < winHeight; i += tileSize) {
			g.drawLine(0, i, winWidth, i);
		}
		for (int i = 0; i < winWidth; i += tileSize) {
			g.drawLine(i, 0, i, winHeight);
		}
	}
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(42);
			}
			catch (Exception e) {
				
			}
		}
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// unused
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
