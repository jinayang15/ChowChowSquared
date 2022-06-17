import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Images {
	// anything starting with "pH" is a placeholder, not final
	public static final BufferedImage[] rightIdleDog1 = new BufferedImage[2];
	public static final BufferedImage[] rightRunDog1 = new BufferedImage[6];
	public static final BufferedImage[] rightJumpDog1 = new BufferedImage[2];
	public static final BufferedImage[] rightFallDog1 = new BufferedImage[3];
	public static final BufferedImage[] leftIdleDog1 = new BufferedImage[2];
	public static final BufferedImage[] leftRunDog1 = new BufferedImage[6];
	public static final BufferedImage[] leftJumpDog1 = new BufferedImage[2];
	public static final BufferedImage[] leftFallDog1 = new BufferedImage[3];

	public static BufferedImage menu;
	public static BufferedImage level;
	public static BufferedImage options;
	public static BufferedImage winners;
	public static BufferedImage back;
	public static BufferedImage empty;
	public static BufferedImage gameMusic;
	public static BufferedImage menuMusic;
	public static BufferedImage retry;
	public static BufferedImage icon;
	public static BufferedImage gameOver;
	public static BufferedImage win;

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

	
	public static BufferedImage pHDog;
	public static BufferedImage pHBug;
	public static BufferedImage pHTile;
	public static BufferedImage pHBG;
	
	
	public static void importImages() throws IOException {
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
		gameOver = ImageIO.read(new File("gameover.png"));
		win = ImageIO.read(new File("win.png"));
		pHBug = ImageIO.read(new File("mad.png"));
		skyBG = ImageIO.read(new File("basiclevelbg.png"));
		tutorialBG = ImageIO.read(new File("tutorialbg.png"));
		dogRight1 = ImageIO.read(new File("whitedogright.png"));
		dogLeft1 = ImageIO.read(new File("whitedogleft.png"));

		dirtTile = ImageIO.read(new File("newdirt.png"));
		grassTile = ImageIO.read(new File("newgrass.png"));
		defaultRightImage = dogRight1.getSubimage(40, 0, 40, 40);
		defaultLeftImage = dogLeft1.getSubimage(440,0,40,40);
		for (int i = 0; i < rightIdleDog1.length; i++) {
			rightIdleDog1[i] = dogRight1.getSubimage(i*40,0,40, 40);
		}
		for (int i = 0; i < rightRunDog1.length; i++) {
			rightRunDog1[i] = dogRight1.getSubimage((i+7)*40, 40, 40, 40);
		}
		for (int i = 0; i < rightJumpDog1.length; i++) {
			rightJumpDog1[i] = dogRight1.getSubimage((i+7)*40, 0, 40, 40);
		}
		for (int i = 0; i < rightFallDog1.length; i++) {
			rightFallDog1[i] = dogRight1.getSubimage((i+9)*40, 0, 40, 40);
		}
		for (int i = 0; i < leftIdleDog1.length; i++) {
			leftIdleDog1[i] = dogLeft1.getSubimage((i+11)*40,0,40,40);
		}
		for (int i = 0; i < leftRunDog1.length; i++) {
			leftRunDog1[i] = dogLeft1.getSubimage(i*40, 40, 40, 40);
		}
		for (int i = 0; i < leftJumpDog1.length; i++) {
			leftJumpDog1[i] = dogLeft1.getSubimage((i+4)*40, 0, 40, 40);
		}
		for (int i = 0; i < leftFallDog1.length; i++) {
			leftFallDog1[i] = dogLeft1.getSubimage((i+1)*40, 0, 40, 40);
		}
		
//		for (int i = 0; i < leftRunDog1.length; i++) {
//			leftRunDog1[i] = dogLeft1.getSubimage(i*40, 40, 40, 40);
//		}
//		leftJumpDog1[0] = dogLeft1.getSubimage(2*40, 40, 40, 40);
//		for (int i = 3; i >= 0; i--) {
//			leftJumpDog1[i] = dogLeft1.getSubimage(i*40, 0, 40, 40);
//		}
//		for (int i = 0;i < rightWalkDog1.length; i++) {
//			rightWalkDog1[i] = grabImage(i+7,2,dogRight1);
//		}
//		for (int i = 0; i < rightWalkDog1.length; i++) {
//			lefttWalkDog1[i] = grabImage(i,2,dogLeft1);
//		}
//		rightJumpDog1[0] = dogRight1.getSubimage(10*40, 40, 40, 40);
		
//		for (int i = 0; i < newDirtTiles.length; i++) {
//			for (int j = 0; i < 4; j++) {
//				newDirtTiles[i] = ImageIO.read(new File("newgrass" + (i+1) + "_" + (j+1) + ".png"));
//			}
//		}

	}

}
