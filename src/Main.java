import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.*;

public class Main extends JPanel implements Runnable, KeyListener {

	public static final Character dog = new Character();
	public static final int levelWidth = 6760;
	public static int bgX = 0;
	public static int bgY = -400;
	public static int screenNum = 1;
	public static final int winWidth = 520;
	public static final int winHeight = 400;
	// 13 x 10 tiles
	public static final int tileSize = 40;
	public static final int fps = 30;
	// keeps track of the tiles onscreen
	public static int[][] levelTileGrid = new int[levelWidth/tileSize][winHeight/tileSize];
	
	public Main() {
		setPreferredSize(new Dimension(winWidth,winHeight));
		setBackground(new Color(255,255,255));;
		this.setFocusable(true);
		addKeyListener(this);
		try {
			// imports all images
			Images.importImage();
		}
		catch(Exception e) {
			
		}
		
		Thread thread = new Thread(this);
		thread.start();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		for (int i = 0; i < winHeight; i += tileSize) {
			g.drawLine(0, i, winWidth, i);
		}
		for (int i = 0; i < winWidth; i += tileSize) {
			g.drawLine(i, 0, i, winHeight);
		}
		g.drawImage(Images.pHBG, bgX, bgY, null);
		g.drawImage(Images.pHTile, 0, 360, null);
		g.drawImage(Images.pHDog, (int) dog.getX(), (int) dog.getY(), null);
	}
	@Override
	public void run() {
		while(true) {
			repaint();
			dog.update();
			try {
				Thread.sleep(1000/fps);
			}
			catch (Exception e) {
				
			}
		}
		
	}
	
	@Override
	// basic key controls jump, left, right
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ' || e.getKeyChar() == 'w') {
			if (!dog.isJumping()) {
				dog.setJumping(true);
			}
		}
		if (e.getKeyChar() == 'a') {
			dog.setLeft(true);
			dog.moveLeft();
		}
		if (e.getKeyChar() == 'd') {
			dog.setRight(true);
			dog.moveRight();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'a') {
			dog.setLeft(false);
		}
		if (e.getKeyChar() == 'd') {
			dog.setRight(false);
		}
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Main panel = new Main();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		dog.setBounds(0, 320, Images.pHDog.getWidth(), Images.pHDog.getHeight());
	}
	
	// unused
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
