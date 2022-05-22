import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.*;

public class Main extends JPanel implements Runnable, KeyListener {
	
	public static int winWidth = 520;
	public static int winHeight = 400;
	// 13 x 10 tiles
	public static int tileSize = 40;
	// keeps track of the tiles onscreen
	public static Tile[][] tileGrid = new Tile[winHeight/tileSize][winWidth/tileSize];
	
	public Main() {
		setPreferredSize(new Dimension(winWidth,winHeight));
		setBackground(new Color(50, 250, 250));
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
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Main panel = new Main();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		Tile tile1 = new Tile();
		tileGrid[360/40][0/40] = tile1;
		System.out.println(tileGrid[360/40][0/40].checkSolid());
		
		Character dog = new Character();
		dog.setBounds(0, 320, Images.pHDog.getWidth(), Images.pHDog.getHeight());
	}
	public void paintComponent(Graphics g) {
		for (int i = 0; i < winHeight; i += tileSize) {
			g.drawLine(0, i, winWidth, i);
		}
		for (int i = 0; i < winWidth; i += tileSize) {
			g.drawLine(i, 0, i, winHeight);
		}
		g.drawImage(Images.pHTile, 0, 360, null);
		g.drawImage(Images.pHDog, 0, 320, null);
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
