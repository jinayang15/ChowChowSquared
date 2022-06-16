import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;

public class Main extends JPanel implements Runnable, KeyListener, MouseListener {

	public static final int levelWidth = 6760;
	public static int prevBGX = -40;
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
	public static int[][] levelGrid40 = new int[tileHeight / 2][levelTileWidth / 2];
	public static int[][] levelGrid20 = new int[tileHeight][levelTileWidth];
	public static int[][] currentGrid = new int[tileHeight][tileWidth + 2];
	public static String currentLvl;
	public static int imageWidth = 40;
	public static int imageHeight = 40;
	public static Scanner in;
	public static long lastLeftPress = 0;
	public static long lastRightPress = 0;
	public static int inputDelay = 1000;

	public static Character dog = new Character();
	public static Enemy bug = new Enemy();
	// Game states:
	// 0 --> menu
	// 1 --> level select
	// 2 --> lvl 1
	// 3 --> tutorial
	// 4 --> 
	// 5 --> you won (enter name)
	// 6 --> options
	// 7 --> winners
	// 8 --> you died
	public static int gameState = 0;
	Clip menuBGM, gameBGM;
	public static boolean muteMenu = false;
	public static boolean muteGame = false;

	public Main() {
		setPreferredSize(new Dimension(winWidth, winHeight));
		setBackground(new Color(255, 255, 255));
		this.setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		try {
			// imports all images
			Images.importImages();
			// import all sounds
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File("menumusic.wav"));
			menuBGM = AudioSystem.getClip();
			menuBGM.open(sound);
			sound = AudioSystem.getAudioInputStream(new File("gamemusic.wav"));
			gameBGM = AudioSystem.getClip();
			gameBGM.open(sound);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Thread thread = new Thread(this);
		thread.start();

	}

	public void paintComponent(Graphics g) {
		if (gameState == 0) {
			super.paintComponent(g);
			g.drawImage(Images.menu, 0, 0, null);
			if (!muteMenu) {
				menuBGM.start();
				menuBGM.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				menuBGM.stop();
				gameBGM.stop();
			}

		} else if (gameState == 3) {
			super.paintComponent(g);
			if (!muteGame) {
				menuBGM.stop();
				gameBGM.start();
				gameBGM.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				menuBGM.stop();
				gameBGM.stop();
			}
			g.drawImage(Images.tutorialBG, bgX, bgY, null);
			
		} else if (gameState == 1) {
			g.drawImage(Images.level, 0, 0, null);
			g.drawImage(Images.back, 450, 340, null);
		} else if (gameState == 2) {
			super.paintComponent(g);
			if (!muteGame) {
				menuBGM.stop();
				gameBGM.start();
				gameBGM.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				menuBGM.stop();
				gameBGM.stop();
			}
			g.drawImage(Images.skyBG, bgX, bgY, null);
			GameFunctions.drawTiles(g, GameFunctions.genCurrentGrid());
			for (int i = 0; i < winHeight; i += tileSize) {
				g.drawLine(0, i, winWidth, i);
			}
			for (int i = 0; i < winWidth; i += tileSize) {
				g.drawLine(i, 0, i, winHeight);
			}
			g.drawImage(Images.spike, 0, 0, null);
			// g.drawImage(Images.pHBug, (int)bug.getX(), (int)bug.getY(), null);
			g.drawImage(Images.currentDogImage, (int) dog.getX(), (int) dog.getY(), null);
//			for (int i = 0; i < currentGrid.length; i++) {
//				for (int k = 0; k < currentGrid[0].length; k++) {
//					System.out.print(currentGrid[i][k]);
//				}
//				System.out.println();
//			}
			// image box
//			g.setColor(new Color(0, 0, 255));
//			g.drawRect((int) dog.getX(), (int) dog.getY(), imageWidth, imageHeight);
			// hitbox
			g.setColor(new Color(255, 255, 255));
			g.drawRect((int) dog.getX() + dog.imageAdjustX, (int) dog.getY() + dog.imageAdjustY, dog.hitboxWidth,
					dog.hitboxHeight);
		} else if (gameState == 6) {
			super.paintComponent(g);
			g.drawImage(Images.options, 0, 0, null);
			g.drawImage(Images.back, 450, 340, null);
			g.drawImage(Images.menuMusic, 100, 160, null);
			g.drawImage(Images.gameMusic, 100, 215, null);
			if (!muteMenu) {
				menuBGM.start();
				g.drawImage(Images.empty, 330, 144, null);
			}
			if (muteMenu) {
				menuBGM.stop();
				g.drawImage(Images.back, 330, 144, null);
			}
			if (!muteGame) {
				g.drawImage(Images.empty, 330, 200, null);
			}
			if (muteGame) {
				gameBGM.stop();
				g.drawImage(Images.back, 330, 200, null);
			}

		} else if (gameState == 7) {
			super.paintComponent(g);
			g.drawImage(Images.winners, 0, 0, null);
			g.drawImage(Images.back, 450, 340, null);

		} else if (gameState == 8) {
			gameBGM.stop();
			g.drawImage(Images.gameOver, 0, 0, null);
			g.drawImage(Images.retry, 170, 240, null);
			g.drawImage(Images.back, 300, 240, null);
		}

	}

	@Override
	public void run() {
		while (true) {
			repaint();
			if (gameState == 2) {
				dog.update();
				//bug.update();

			}
			try {
				Thread.sleep(1000 / fps);
			} catch (Exception e) {

			}
		}
	}

	// basic key controls jump, left, right
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyChar() == ' ' || e.getKeyChar() == 'w') {
			if (!dog.getJumpCD()) {
				if (!dog.isJumping() && dog.checkBlockBelow()[0] != Character.noCollide) {
					dog.setJumping(true);
					dog.setVerticalDirection(-1);
					dog.setJumpCD(true);
				}
			}
		}
		if (e.getKeyChar() == 'a') {
			if(System.currentTimeMillis() - lastLeftPress > inputDelay) {
				dog.setMovingLeft(true);
				dog.moveLeft();
				dog.setHorizontalDirection(-1);
				Images.currentDogImage = Images.leftIdleDog1[0];
	            lastLeftPress = System.currentTimeMillis();
	        }
			
		}
		if (e.getKeyChar() == 'd') {
			if(System.currentTimeMillis() - lastRightPress > inputDelay) {
				dog.setMovingRight(true);
				dog.moveRight();
				dog.setHorizontalDirection(1);
				Images.currentDogImage = Images.rightIdleDog1[1];
				lastRightPress = System.currentTimeMillis();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'a') {
			dog.setMovingLeft(false);
			Images.currentDogImage = Images.defaultLeftImage;
			lastLeftPress = 0;
		}
		if (e.getKeyChar() == 'd') {
			dog.setMovingRight(false);
			Images.currentDogImage = Images.defaultRightImage;
			lastRightPress = 0;
		}
		Animations.resetRunAnimation();
	}

	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (gameState == 0) {
			if (mouseX >= 187 && mouseX <= 323 && mouseY >= 160 && mouseY <= 207) {
				gameState = 1;
			} else if (mouseX >= 187 && mouseX <= 323 && mouseY >= 220 && mouseY <= 266) {
				gameState = 6;
			} else if (mouseX >= 187 && mouseX <= 323 && mouseY >= 279 && mouseY <= 328) {
				gameState = 7;
			}
		} else if (gameState == 1) {
			if (mouseX >= 198 && mouseX <= 323 && mouseY >= 146 && mouseY <= 194) {
				gameState = 3;
			}
			else if (mouseX >= 198 && mouseX <= 323 && mouseY >= 248 && mouseY <= 296) {
				gameState = 2;
			}
			else if (mouseX >= 450 && mouseX <= 500 && mouseY >= 340 && mouseY <= 387) {
				gameState = 0;
			}
		} else if (gameState == 6) {
			if (mouseX >= 450 && mouseX <= 500 && mouseY >= 340 && mouseY <= 387) {
				gameState = 0;
			}
			if (mouseX >= 330 && mouseX <= 380 && mouseY >= 144 && mouseY <= 187) {
				muteMenu = !muteMenu;
			}
			if (mouseX >= 330 && mouseX <= 380 && mouseY >= 200 && mouseY <= 247) {
				muteGame = !muteGame;
			}
		} else if (gameState == 7) {
			if (mouseX >= 450 && mouseX <= 500 && mouseY >= 340 && mouseY <= 387) {
				gameState = 0;
			}
		} else if (gameState == 8) {
			GameFunctions.restartGame();
			if (mouseX >= 170 && mouseX <= 220 && mouseY >= 240 && mouseY <= 287) {
				gameState = 2;
			} else if (mouseX >= 300 && mouseX <= 350 && mouseY >= 240 && mouseY <= 287) {

				gameState = 0;
			}
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new JFrame();
		Main panel = new Main();
		frame.add(panel);
		frame.pack();
		frame.setIconImage(Images.icon);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		currentLvl = "40lvl1.txt";
		in = new Scanner(new File(currentLvl));
		GameFunctions.load40Grid(in);
		GameFunctions.load20Grid();
//		for (int i = 0; i < levelGrid20.length; i++) {
//			for (int k = 0; k < levelGrid20[0].length; k++) {
//				System.out.print(levelGrid20[i][k]);
//			}
//			System.out.println();
//		}
//		
		Images.currentDogImage = Images.rightIdleDog1[1];
//		dog.setBounds(0, 0, imageWidth, imageHeight);
//		dog.setHitbox(20, 20);

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
