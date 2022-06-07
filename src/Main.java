import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class Main extends JPanel implements Runnable, KeyListener, MouseListener {

	public static final Character dog = new Character();
	public static final Enemy bug = new Enemy();
	public static Character player2 = new Character();
	public static final int levelWidth = 6760;
	public static int bgX = 0;
	public static int bgY = 0;
	public static int mouseX;
	public static int mouseY;
	public static final int winWidth = 520;
	public static final int winHeight = 400;
	// 13 x 10 tiles
	public static final int tileSize = 20;
	public static final int tileWidth = winWidth / tileSize;
	public static final int tileHeight = winHeight / tileSize;
	public static final int levelTileWidth = levelWidth / tileSize;
	public static final int fps = 30;
	// keeps track of the tiles onscreen
	public static int[][] levelGrid = new int[tileHeight][levelTileWidth];
	public static int[][] currentGrid = new int[tileHeight][tileWidth + 2];
	public static String currentLvl;
	public static int imageWidth;
	public static int imageHeight;
	public static Scanner in;
	// Game states:
	// 0 --> menu
	// 1 --> level select
	// 2 --> tutorial
	// 3 --> lvl 1
	// 4 --> lvl 2
	// 5 --> boss
	// 6 --> options
	// 8 --> winners
	// 9 --> you died
	public static int gameState = 0;

	public Main() {
		setPreferredSize(new Dimension(winWidth, winHeight));
		setBackground(new Color(255, 255, 255));
		;
		this.setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		try {
			// imports all images
			Images.importImages();
		} catch (Exception e) {

		}

		Thread thread = new Thread(this);
		thread.start();

	}

	public void paintComponent(Graphics g) {
		if (gameState == 0) {
			super.paintComponent(g);
			g.drawImage(Images.menu, 0, 0, null);

		} else if (gameState == 1) {
			g.drawImage(Images.level, 0, 0, null);
		} else if (gameState == 2) {
			super.paintComponent(g);

			Animations.updateAnimation();
			g.drawImage(Images.skyBG, bgX, bgY, null);
			// g.drawImage(Images.dogRight1, 0, 0, null);
			for (int i = 0; i < winHeight; i += tileSize) {
				g.drawLine(0, i, winWidth, i);
			}
			for (int i = 0; i < winWidth; i += tileSize) {
				g.drawLine(i, 0, i, winHeight);
			}
			GameFunctions.drawTiles(g);
			// g.drawImage(Images.pHBug, (int)bug.getX(), (int)bug.getY(), null);
			g.drawImage(Images.rightIdleDog1[Animations.index], (int) dog.getX(),
					(int) dog.getY(), null);
			// image box
			g.setColor(new Color(0, 0, 255));
			g.drawRect((int) dog.getX(), (int) dog.getY(), imageWidth, imageHeight);
			// hitbox
			g.setColor(new Color(255, 255, 255));
			g.drawRect((int) dog.getX() + dog.imageAdjustX, (int) dog.getY() + dog.imageAdjustY, dog.hitboxWidth, dog.hitboxHeight);
		}

	}

	@Override
	public void run() {
		while (true) {
			repaint();
			if (gameState == 2) {
				dog.update();
				bug.update();

			}
			try {
				Thread.sleep(1000 / fps);
			} catch (Exception e) {

			}
		}
	}

	@Override
	// basic key controls jump, left, right
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ' || e.getKeyChar() == 'w') {
			if (!dog.isJumping() && dog.checkBlockBelow()[0] != Character.noCollide) {
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
			if (mouseX >= 187 && mouseX <= 323 && mouseY >= 160 && mouseY <= 207) {
				gameState = 1;
			}
		} else if (gameState == 1) {
			if (mouseX >= 212 && mouseX <= 308 && mouseY >= 144 && mouseY <= 180) {
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

		currentLvl = "testerLvl.txt";
		in = new Scanner(new File(currentLvl));
		GameFunctions.loadGrid(in);
		imageWidth = Images.pHDog.getWidth();
		imageHeight = Images.pHDog.getHeight();
		dog.setBounds(0, 0, imageWidth, imageHeight);
		dog.setHitbox(22, 20);
		
		in.close();
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
