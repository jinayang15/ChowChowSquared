import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class Main extends JPanel implements Runnable, KeyListener, MouseListener {

	public static final Character dog = new Character();
	public static final int levelWidth = 6760;
	public static int bgX = 0;
	public static int bgY = -400;
	public static int mouseX;
	public static int mouseY;
	public static int screenNum = 1;
	public static final int winWidth = 520;
	public static final int winHeight = 400;
	// 13 x 10 tiles
	public static final int tileSize = 40;
	public static final int tileWidth = winWidth/tileSize;
	public static final int tileHeight = winHeight/tileSize;
	public static final int fps = 30;
	// keeps track of the tiles onscreen
	public static int[][] levelGrid = new int[tileHeight][tileWidth];
	public static String currentLvl;
	public static Scanner in;
	// Game states:
	// 0  --> menu
	// 1  --> level select
	// 2  --> in game?
	public static int gameState = 0;
	
	public Main() {
		setPreferredSize(new Dimension(winWidth,winHeight));
		setBackground(new Color(255,255,255));;
		this.setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
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
		if (gameState == 0) {
			super.paintComponent(g);
			g.drawImage(Images.menu, 0, 0, null);

		}
		else if(gameState == 1) {
			
		}
		else if (gameState == 2) {
			super.paintComponent(g);
			
			g.drawImage(Images.pHBG, bgX, bgY, null);
			GameFunctions.drawTiles(g);
			for (int i = 0; i < winHeight; i += tileSize) {
				g.drawLine(0, i, winWidth, i);
			}
			for (int i = 0; i < winWidth; i += tileSize) {
				g.drawLine(i, 0, i, winHeight);
			}
			g.drawImage(Images.pHDog, (int) dog.getX(), (int) dog.getY(), null);
		}
		 
	}
	@Override
	public void run() {
		while(true) {
			repaint();
			if (gameState == 2) {
				dog.update();
			}
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
			if (!dog.isJumping() && dog.checkBlockBelow()) {
				dog.setJumping(true);
				dog.setDirection(-1);
				
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
	
	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (gameState == 0) {
			if (mouseX >= 187 && mouseX <=323 && mouseY >= 160 && mouseY <=207)
			{
				gameState = 2;
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new JFrame();
		Main panel = new Main();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		currentLvl = "testerLvl1.txt";
		in = new Scanner(new File(currentLvl));
		GameFunctions.loadGrid(in);
		dog.setBounds(0, 0, Images.pHDog.getWidth(), Images.pHDog.getHeight());
	}
	
	// unused
	public void keyTyped(KeyEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {	
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}

}
