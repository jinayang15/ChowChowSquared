import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import javax.swing.JButton;

// Game: Chow Chow Squared
// By: Jina Yang and Vicky Li
// A mini platformer game where the objective is for Biscuit to find Cookie who is lost!
// Be careful of spikes and slimes that get in your way! You don't want to touch them
// Controls: W/Space, A, S, D
public class Main extends JPanel implements Runnable, KeyListener, MouseListener {
	// Main Variables
	// Frames per Second
	public static final int fps = 30;
	// Level Width in Pixels
	public static final int levelWidth = 6760;
	// Position of Background
	public static int bgX = 0;
	public static int bgY = 0;
	// Position of Mouse
	public static int mouseX;
	public static int mouseY;
	// Window Width and Height in Pixels
	public static final int winWidth = 520;
	public static final int winHeight = 400;
	// Tile Side Length in Pixels
	public static final int tileSize = 20;
	// Amount of Tiles that make up the Window Width & Height
	public static final int tileWidth = winWidth / tileSize;
	public static final int tileHeight = winHeight / tileSize;
	// Amount of Tiles that make up the Level Width
	public static final int levelTileWidth = levelWidth / tileSize;
	// Scanner & PrintWriter
	public static Scanner in;
	public static PrintWriter out;
	// Keeps track of Tiles
	// - 40x40 tile grid of the level
	// - 20x20 tile grid of the level
	// - 20x20 tile grid of the window
	public static char[][] levelGrid40 = new char[tileHeight / 2][levelTileWidth / 2];
	public static char[][] levelGrid20 = new char[tileHeight][levelTileWidth];
	public static char[][] currentGrid = new char[tileHeight][tileWidth + 2];
	// Current Level being played
	public static String currentLvl;
	// Size of all sprite images
	public static int imageWidth = 40;
	public static int imageHeight = 40;
	// Keeps track of last time Left and Right were pressed and delay in
	// milliseconds
	public static int inputDelay = 500;
	public static long lastLeftPress = 0;
	public static long lastRightPress = 0;
	// Playable Character
	public static Character dog = new Character();
	// Game states:
	// 0 --> menu
	// 1 --> level select
	// 2 --> lvl 1
	// 3 --> tutorial
	// 4 --> enter name
	// 5 --> you won
	// 6 --> options
	// 7 --> winners
	// 8 --> you died
	public static int gameState = 0;
	// Music + DeathSFX
	Clip menuBGM, gameBGM, dieSFX, winBGM;
	// Mute Music/Sound
	public static boolean muteMenu = false;
	public static boolean muteGame = false;
	public static boolean muteSFX = false;
	// to see if a clip has been played or not
	public static int played = 0;
	// Winner
	public static String winner = "";
	// Hall of Fame Names
	public static ArrayList<String> namesHOF = new ArrayList<String>();
	// Hall of Fame Pages
	public static ArrayList<String[]> pagesHOF = new ArrayList<String[]>();
	// Pages Variables
	public static int namesPerPage = 10;
	public static int currentPage = 0;

	public Main() {
		// Constructor Body
		// Set Up JPanel
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
			sound = AudioSystem.getAudioInputStream(new File("die.wav"));
			dieSFX = AudioSystem.getClip();
			dieSFX.open(sound);
			sound = AudioSystem.getAudioInputStream(new File("victory.wav"));
			winBGM = AudioSystem.getClip();
			winBGM.open(sound);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(this);
		thread.start();
	}

	public void paintComponent(Graphics g) {
		// Menu
		if (gameState == 0) {
			winBGM.stop();
			gameBGM.stop();
			super.paintComponent(g);
			g.drawImage(Images.menu, 0, 0, null);
			if (!muteMenu) {
				dieSFX.stop();
				menuBGM.start();
				menuBGM.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				dieSFX.stop();
				menuBGM.stop();
				gameBGM.stop();
			}

		}
		// Level Select
		else if (gameState == 1) {
			g.drawImage(Images.level, 0, 0, null);
			g.drawImage(Images.back, 450, 340, null);
		}
		// Level One
		else if (gameState == 2) {
			// change current level
			currentLvl = "completelvl1.txt";
			try {
				// scan current level text file
				in = new Scanner(new File(currentLvl));
				GameFunctions.load40Grid(in);
				GameFunctions.load20Grid();
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			super.paintComponent(g);
			// Game BGM
			if (!muteGame) {
				dieSFX.stop();
				menuBGM.stop();
				gameBGM.start();
				gameBGM.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				dieSFX.stop();
				menuBGM.stop();
				gameBGM.stop();
			}
			// Level Generation
			g.drawImage(Images.skyBG, bgX, bgY, null);
			GameFunctions.drawTiles(g, GameFunctions.genCurrentGrid());
			Enemy.loadOnScreenEnemies(g, GameFunctions.genCurrentGrid());
			// Character Generation
			g.drawImage(Images.currentDogImage, (int) dog.getX(), (int) dog.getY(), null);
		}
		// Tutorial
		else if (gameState == 3) {
			// same as Level 1
			currentLvl = "40tutorial.txt";
			try {
				in = new Scanner(new File(currentLvl));
				GameFunctions.load40Grid(in);
				GameFunctions.load20Grid();
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			super.paintComponent(g);
			if (!muteGame) {
				dieSFX.stop();
				menuBGM.stop();
				gameBGM.start();
				gameBGM.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				menuBGM.stop();
				gameBGM.stop();
				dieSFX.stop();
			}
			g.drawImage(Images.tutorialBG, bgX, bgY, null);
			GameFunctions.drawTiles(g, GameFunctions.genCurrentGrid());
			g.drawImage(Images.currentDogImage, (int) dog.getX(), (int) dog.getY(), null);

		}
		// Enter Name
		else if (gameState == 4) {
			g.drawImage(Images.win[4], 0, 0, null);
			g.setColor(new Color(255, 255, 255));
			Font font = new Font("SansSerif", Font.BOLD, 36);
			g.setFont(font);
			g.drawString(winner, 140, 240);
		}
		// Win Screen
		else if (gameState == 5) {
			Animations.fade(g);
			gameBGM.stop();
			if (!muteGame && !winBGM.isRunning() && played == 0) {
				winBGM.setFramePosition(0);
				winBGM.start();
				played++;
			}
		}
		// Options Screen
		else if (gameState == 6) {
			super.paintComponent(g);
			g.drawImage(Images.options, 0, 0, null);
			g.drawImage(Images.back, 450, 340, null);
			g.drawImage(Images.menuMusic, 100, 160, null);
			g.drawImage(Images.gameMusic, 100, 215, null);
			g.drawImage(Images.gameSFX, 100, 270, null);
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
			if (muteSFX) {
				dieSFX.stop();
				g.drawImage(Images.back, 330, 260, null);
			}
			if (!muteSFX) {
				g.drawImage(Images.empty, 330, 260, null);
			}

		}
		// Hall of Fame
		else if (gameState == 7) {
			winBGM.stop();
			super.paintComponent(g);
			g.drawImage(Images.winners, 0, 0, null);
			g.drawImage(Images.back, 450, 340, null);
			g.setColor(new Color(0, 0, 0));
			Font font = new Font("Monospaced", Font.PLAIN, 12);
			g.setFont(font);
			if (pagesHOF.size() > 0) {
				for (int i = 0; i < namesPerPage; i++) {
					if (pagesHOF.get(currentPage)[i] != null) {
						g.drawString(pagesHOF.get(currentPage)[i], 180, i * 20 + 130);
					}
				}
			}
		}
		// Game Over
		else if (gameState == 8) {
			gameBGM.stop();
			if (!muteSFX && !dieSFX.isRunning() && played == 0) {
				dieSFX.setFramePosition(0);
				dieSFX.start();
				played++;
			}
			g.drawImage(Images.gameOver, 0, 0, null);
			g.drawImage(Images.retry, 170, 240, null);
			g.drawImage(Images.back, 300, 240, null);
		}

	}

	@Override
	public void run() {
		while (true) {
			repaint();
			// Level 1/Tutorial
			if (gameState == 2 || gameState == 3) {
				// update dog and enemies
				dog.update();
				Enemy.moveEnemies();
			}
			try {
				// Thread refresh rate
				Thread.sleep(1000 / fps);
			} catch (Exception e) {
			}
		}
	}

	// basic key controls jump, left, right
	public void keyPressed(KeyEvent e) {
		// Jump
		if (e.getKeyChar() == ' ' || e.getKeyChar() == 'w') {
			// Check for jump cooldown
			if (!dog.getJumpCD()) {
				// Dog has to be:
				// - not jumping already
				// - on the ground
				if (!dog.isJumping() && dog.checkBlockBelow()[0] != Character.noCollide) {
					dog.setJumping(true);
					dog.setVerticalDirection(-1);
					dog.setJumpCD(true);
				}
			}
		}
		// Move Left
		if (e.getKeyChar() == 'a') {
			// Check for input delay
			if (System.currentTimeMillis() - lastLeftPress > inputDelay) {
				dog.setMovingLeft(true);
				dog.moveLeft();
				dog.setHorizontalDirection(-1);
				Images.currentDogImage = Images.leftIdleDog1[0];
				lastLeftPress = System.currentTimeMillis();
			}

		}
		// Move Right
		if (e.getKeyChar() == 'd') {
			// Check for input delay
			if (System.currentTimeMillis() - lastRightPress > inputDelay) {
				dog.setMovingRight(true);
				dog.moveRight();
				dog.setHorizontalDirection(1);
				Images.currentDogImage = Images.rightIdleDog1[1];
				lastRightPress = System.currentTimeMillis();
			}
		}
		// To exit tutorial
		if (e.getKeyChar() == 'x') {
			if (gameState == 3) {
				GameFunctions.restartGame();
				gameState = 1;
			}
		}
		if (gameState == 4) {
			if (e.getKeyCode() == 10) {
				try {
					in = new Scanner(new File("halloffame.txt"));
					ArrayList<String> names = new ArrayList<String>();
					String line;
					while (in.hasNextLine()) {
						line = in.nextLine();
						names.add(line);
					}
					in.close();
					names.add(winner);
					out = new PrintWriter(new FileWriter("halloffame.txt"));
					for (String s : names) {
						out.println(s);
					}
					out.close();
					winner = "";
					GameFunctions.restartGame();
					GameFunctions.hallOfFamePages();
					gameState = 7;
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
			if (e.getKeyCode() == 8 && winner.length() > 0) {
				winner = winner.substring(0, winner.length() - 1);
			} else if (e.getKeyCode() != 8  && e.getKeyCode() != 16 && winner.length() < 11) {
				winner += e.getKeyChar();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// Stop Moving Left
		if (e.getKeyChar() == 'a') {
			dog.setMovingLeft(false);
			Images.currentDogImage = Images.defaultLeftImage;
			lastLeftPress = 0;
		}
		// Stop Moving Right
		if (e.getKeyChar() == 'd') {
			dog.setMovingRight(false);
			Images.currentDogImage = Images.defaultRightImage;
			lastRightPress = 0;
		}
		// Reset Run Animations
		Animations.resetRunAnimation();
	}

	public void mousePressed(MouseEvent e) {
		// Buttons
		mouseX = e.getX();
		mouseY = e.getY();
		// Menu
		if (gameState == 0) {
			if (mouseX >= 187 && mouseX <= 323 && mouseY >= 160 && mouseY <= 207) {
				gameState = 1;
			} else if (mouseX >= 187 && mouseX <= 323 && mouseY >= 220 && mouseY <= 266) {
				gameState = 6;
			} else if (mouseX >= 187 && mouseX <= 323 && mouseY >= 279 && mouseY <= 328) {
				gameState = 7;
				GameFunctions.hallOfFamePages();
			}
		}
		// Level Select
		else if (gameState == 1) {
			if (mouseX >= 198 && mouseX <= 323 && mouseY >= 146 && mouseY <= 194) {
				gameState = 3;
			} else if (mouseX >= 198 && mouseX <= 323 && mouseY >= 248 && mouseY <= 296) {
				gameState = 2;
			} else if (mouseX >= 450 && mouseX <= 500 && mouseY >= 340 && mouseY <= 387) {
				gameState = 0;
			}
		}
		// Options
		else if (gameState == 6) {
			if (mouseX >= 450 && mouseX <= 500 && mouseY >= 340 && mouseY <= 387) {
				gameState = 0;
			} else if (mouseX >= 330 && mouseX <= 380 && mouseY >= 144 && mouseY <= 187) {
				muteMenu = !muteMenu;
			} else if (mouseX >= 330 && mouseX <= 380 && mouseY >= 200 && mouseY <= 247) {
				muteGame = !muteGame;
			} else if (mouseX >= 330 && mouseX <= 380 && mouseY >= 260 && mouseY <= 300) {
				muteSFX = !muteSFX;
			}
		}
		// Hall of Fame
		else if (gameState == 7) {
			if (mouseX >= 450 && mouseX <= 500 && mouseY >= 340 && mouseY <= 387) {
				gameState = 0;
				currentPage = 0;
			} else if (mouseX >= 169 && mouseX <= 196 && mouseY >= 330 && mouseY <= 344) {
				if (currentPage > 0) {
					currentPage--;
				}
			} else if (mouseX >= 291 && mouseX <= 317 && mouseY >= 330 && mouseY <= 344) {
				if (currentPage < pagesHOF.size() - 1) {
					currentPage++;
				}
			}
		}
		// Game Over
		else if (gameState == 8) {
			GameFunctions.restartGame();
			if (mouseX >= 170 && mouseX <= 220 && mouseY >= 240 && mouseY <= 287) {
				if (currentLvl.equals("40lvl1.txt") || currentLvl.equals("enemies.txt")
						|| currentLvl.equals("completelvl1.txt")) {
					gameState = 2;
					played = 0;
				} else if (currentLvl.equals("40tutorial.txt")) {
					gameState = 3;
					played = 0;
				}
			} else if (mouseX >= 300 && mouseX <= 350 && mouseY >= 240 && mouseY <= 287) {
				gameState = 0;
				played = 0;
			}
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		// JFrame Stuff
		JFrame frame = new JFrame();
		Main panel = new Main();
		frame.add(panel);
		frame.pack();
		frame.setIconImage(Images.icon);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		// Dog Image
		Images.currentDogImage = Images.rightIdleDog1[1];
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
