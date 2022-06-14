import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.awt.*;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;

public class Main extends JPanel implements Runnable, KeyListener, MouseListener {

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
	public static int[][] levelGrid40 = new int[tileHeight/2][levelTileWidth/2];
	public static int[][] levelGrid20 = new int[tileHeight][levelTileWidth];
	public static int[][] currentGrid = new int[tileHeight][tileWidth + 2];
	public static String currentLvl;
	public static int imageWidth = 40;
	public static int imageHeight = 40;
	public static Scanner in;
	
	public static Character dog = new Character();
	public static Enemy bug = new Enemy();
	// Game states:
	// 0 --> menu
	// 1 --> level select
	// 2 --> tutorial
	// 3 --> lvl 1
	// 4 --> 
	// 5 --> you won (enter name)
	// 6 --> options
	// 7 --> winners
	// 8 --> you died
	public static int gameState = 0;
	Clip menuBGM, gameBGM, jumpSFX, dieSFX, attackSFX;

	public Main() {
		setPreferredSize(new Dimension(winWidth, winHeight));
		setBackground(new Color(255, 255, 255));
		this.setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		try {
			// imports all images
			Images.importImages();
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File("menumusic.wav"));
			menuBGM = AudioSystem.getClip();
			menuBGM.open(sound);
			sound = AudioSystem.getAudioInputStream(new File("gamemusic.wav"));
			gameBGM = AudioSystem.getClip();
			gameBGM.open(sound);
			sound = AudioSystem.getAudioInputStream(new File("jump.wav"));
			jumpSFX = AudioSystem.getClip();
			jumpSFX.open(sound);
			sound = AudioSystem.getAudioInputStream(new File("die.wav"));
			dieSFX = AudioSystem.getClip();
			dieSFX.open(sound);
//			sound = AudioSystem.getAudioInputStream(new File("attack.wav"));
//			attackSFX = AudioSystem.getClip();
//			attackSFX.open(sound);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Thread thread = new Thread(this);
		thread.start();

	}

	public void paintComponent(Graphics g) {
		if (gameState == 0) {
			dieSFX.stop();
			super.paintComponent(g);
			g.drawImage(Images.menu, 0, 0, null);
			menuBGM.start();
			menuBGM.loop(Clip.LOOP_CONTINUOUSLY);

		} else if (gameState == 1) {
			g.drawImage(Images.level, 0, 0, null);
			g.drawImage(Images.back, 450, 340, null);
		} else if (gameState == 2) {
			dieSFX.stop();
			super.paintComponent(g);
			menuBGM.stop();
			gameBGM.start();
			gameBGM.loop(Clip.LOOP_CONTINUOUSLY);
			g.drawImage(Images.skyBG, bgX, bgY, null);
			GameFunctions.drawTiles(g, GameFunctions.genCurrentGrid());
//			for (int i = 0; i < winHeight; i += tileSize) {
//				g.drawLine(0, i, winWidth, i);
//			}
//			for (int i = 0; i < winWidth; i += tileSize) {
//				g.drawLine(i, 0, i, winHeight);
//			}
			// g.drawImage(Images.pHBug, (int)bug.getX(), (int)bug.getY(), null);
			g.drawImage(Images.currentDogImage, (int) dog.getX(),
					(int) dog.getY(), null);
			// image box
//			g.setColor(new Color(0, 0, 255));
//			g.drawRect((int) dog.getX(), (int) dog.getY(), imageWidth, imageHeight);
			// hitbox
//			g.setColor(new Color(255, 255, 255));
//			g.drawRect((int) dog.getX() + dog.imageAdjustX, (int) dog.getY() + dog.imageAdjustY, dog.hitboxWidth, dog.hitboxHeight);
		}
		else if (gameState == 6) {
			super.paintComponent(g);
			g.drawImage(Images.options, 0, 0, null);
			g.drawImage(Images.back, 450, 340, null);
		
		}
		else if (gameState == 7) {
			super.paintComponent(g);
			g.drawImage(Images.winners, 0, 0, null);
			g.drawImage(Images.back, 450, 340, null);
		
		}
		else if (gameState == 8) {
			gameBGM.stop();
			dieSFX.setMicrosecondPosition(0);
			dieSFX.start();
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
				bug.update();

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
			if (!dog.isJumping() && dog.checkBlockBelow()[0] != Character.noCollide) {
				dog.setJumping(true);
				dog.setVerticalDirection(-1);
			}
		}
		if (e.getKeyChar() == 'a') {
			dog.setMovingLeft(true);
			dog.moveLeft();
			dog.setHorizontalDirection(-1);
			Images.currentDogImage = Images.leftIdleDog1[0];
		}
		if (e.getKeyChar() == 'd') {
			dog.setMovingRight(true);
			dog.moveRight();
			dog.setHorizontalDirection(1);
			Images.currentDogImage = Images.rightIdleDog1[1];
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'a') {
			dog.setMovingLeft(false);
			Animations.runLeftIndex = Images.leftRunDog1.length-1;
			Images.currentDogImage = Images.defaultLeftImage;
		}
		if (e.getKeyChar() == 'd') {
			dog.setMovingRight(false);
			Animations.runRightIndex = 0;
			Images.currentDogImage = Images.defaultRightImage;
		}
	}

	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (gameState == 0) {
			if (mouseX >= 187 && mouseX <= 323 && mouseY >= 160 && mouseY <= 207) {
				gameState = 1;
			}
			else if (mouseX >= 187 && mouseX <= 323 && mouseY >= 220 && mouseY <= 266) {
				gameState = 6;
			}
			else if (mouseX >= 187 && mouseX <= 323 && mouseY >= 279 && mouseY <= 328) {
				gameState = 7;
			}
		} else if (gameState == 1) {
			if (mouseX >= 198 && mouseX <= 323 && mouseY >= 146 && mouseY <= 194) {
				gameState = 2;
			} 
			if (mouseX >= 450 && mouseX <= 500 && mouseY >= 340 && mouseY <= 387) {
				gameState = 0;
			} 
		} else if (gameState == 6) {
			if (mouseX >= 450 && mouseX <= 500 && mouseY >= 340 && mouseY <= 387) {
				gameState = 0;
			} 
		} else if (gameState == 7) {
			if (mouseX >= 450 && mouseX <= 500 && mouseY >= 340 && mouseY <= 387) {
				gameState = 0;
			} 
		} else if (gameState == 8) {
			GameFunctions.restartGame();
			if (mouseX >= 170 && mouseX <= 220 && mouseY >= 240 && mouseY <= 287) {
				gameState = 2;
			} 
			else if (mouseX >= 300 && mouseX <= 350 && mouseY >= 240 && mouseY <= 287) {
				
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
