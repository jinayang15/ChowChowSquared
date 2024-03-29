import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Images {
	// Image Arrays for animations and multiple tiles
	// Images for backgrounds, buttons, etc.
	public static final BufferedImage[] rightIdleDog1 = new BufferedImage[2];
	public static final BufferedImage[] rightRunDog1 = new BufferedImage[6];
	public static final BufferedImage[] rightJumpDog1 = new BufferedImage[2];
	public static final BufferedImage[] rightFallDog1 = new BufferedImage[3];
	public static final BufferedImage[] leftIdleDog1 = new BufferedImage[2];
	public static final BufferedImage[] leftRunDog1 = new BufferedImage[6];
	public static final BufferedImage[] leftJumpDog1 = new BufferedImage[2];
	public static final BufferedImage[] leftFallDog1 = new BufferedImage[3];
	public static final BufferedImage[] pipes = new BufferedImage[6];
	public static final BufferedImage[] flagpole = new BufferedImage[3];
	public static final BufferedImage[] win = new BufferedImage[5];

	public static BufferedImage menu;
	public static BufferedImage level;
	public static BufferedImage options;
	public static BufferedImage winners;
	public static BufferedImage back;
	public static BufferedImage empty;
	public static BufferedImage gameMusic;
	public static BufferedImage menuMusic;
	public static BufferedImage gameSFX;
	public static BufferedImage enterName;
	public static BufferedImage retry;
	public static BufferedImage icon;
	public static BufferedImage gameOver;

	public static BufferedImage dogRight1;
	public static BufferedImage dogLeft1;
	public static BufferedImage skyBG;
	public static BufferedImage tutorialBG;
	public static BufferedImage currentDogImage;
	public static BufferedImage defaultRightImage;
	public static BufferedImage defaultLeftImage;

	public static BufferedImage dirtTile;
	public static BufferedImage grassTile;
	public static BufferedImage spike;
	public static BufferedImage slime;
	// imports all images
	public static void importImages() throws IOException {
		// Method Body
		icon = ImageIO.read(new File("icon.png"));
		menu = ImageIO.read(new File("chowchowmenu.png"));
		level = ImageIO.read(new File("level.png"));
		options = ImageIO.read(new File("options.png"));
		winners = ImageIO.read(new File("winners.png"));
		back = ImageIO.read(new File("back.png"));
		empty = ImageIO.read(new File("empty.png"));
		retry = ImageIO.read(new File("retry.png"));
		spike = ImageIO.read(new File("spike.png"));
		slime = ImageIO.read(new File("slime.png"));
		gameMusic = ImageIO.read(new File("gamemusic.png"));
		menuMusic = ImageIO.read(new File("menumusic.png"));
		gameSFX = ImageIO.read(new File("muteSFX.png"));
		gameOver = ImageIO.read(new File("gameover.png"));
		enterName = ImageIO.read(new File("entername.png"));
		skyBG = ImageIO.read(new File("basiclevelbg.png"));
		tutorialBG = ImageIO.read(new File("tutorialbg.png"));
		dogRight1 = ImageIO.read(new File("whitedogright.png"));
		dogLeft1 = ImageIO.read(new File("whitedogleft.png"));

		dirtTile = ImageIO.read(new File("newdirt.png"));
		grassTile = ImageIO.read(new File("newgrass.png"));
		defaultRightImage = dogRight1.getSubimage(40, 0, 40, 40);
		defaultLeftImage = dogLeft1.getSubimage(440, 0, 40, 40);
		for (int i = 0; i < rightIdleDog1.length; i++) {
			rightIdleDog1[i] = dogRight1.getSubimage(i * 40, 0, 40, 40);
		}
		for (int i = 0; i < rightRunDog1.length; i++) {
			rightRunDog1[i] = dogRight1.getSubimage((i + 7) * 40, 40, 40, 40);
		}
		for (int i = 0; i < rightJumpDog1.length; i++) {
			rightJumpDog1[i] = dogRight1.getSubimage((i + 7) * 40, 0, 40, 40);
		}
		for (int i = 0; i < rightFallDog1.length; i++) {
			rightFallDog1[i] = dogRight1.getSubimage((i + 9) * 40, 0, 40, 40);
		}
		for (int i = 0; i < leftIdleDog1.length; i++) {
			leftIdleDog1[i] = dogLeft1.getSubimage((i + 11) * 40, 0, 40, 40);
		}
		for (int i = 0; i < leftRunDog1.length; i++) {
			leftRunDog1[i] = dogLeft1.getSubimage(i * 40, 40, 40, 40);
		}
		for (int i = 0; i < leftJumpDog1.length; i++) {
			leftJumpDog1[i] = dogLeft1.getSubimage((i + 4) * 40, 0, 40, 40);
		}
		for (int i = 0; i < leftFallDog1.length; i++) {
			leftFallDog1[i] = dogLeft1.getSubimage((i + 1) * 40, 0, 40, 40);
		}
		for (int i = 0; i < win.length; i++) {
			win[i] = ImageIO.read(new File("win" + i + ".png"));		
		}
		for (int i = 1; i <= pipes.length; i++) {
			pipes[i-1] = ImageIO.read(new File("pipe_" + i + ".png"));
		}
		for (int i = 1; i <= flagpole.length; i++) {
			flagpole[i-1] = ImageIO.read(new File("flagpole_" + i + ".png"));
		}
	}

}
