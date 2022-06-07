import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
public class Images {
	// anything starting with "pH" is a placeholder, not final
	public static final BufferedImage[] rightRunDog1 = new BufferedImage[3];
	public static final BufferedImage[] rightWalkDog1 = new BufferedImage[3];
	public static final BufferedImage[] rightJumpDog1 = new BufferedImage[5];
	public static final BufferedImage[] rightIdleDog1 = new BufferedImage[2];
	public static final BufferedImage[] leftRunDog1 = new BufferedImage[3];
	public static final BufferedImage[] lefttWalkDog1 = new BufferedImage[3];
	public static final BufferedImage[] leftJumpDog1 = new BufferedImage[5];
	public static final BufferedImage[] leftIdleDog1 = new BufferedImage[2];
	public static final BufferedImage[] dirtTiles = new BufferedImage[5];
	public static final BufferedImage[] grassTiles = new BufferedImage[2];
	public static BufferedImage menu;
	public static BufferedImage level;
	public static BufferedImage dogRight1;
	public static BufferedImage dogLeft1;
	public static BufferedImage pHDog;
	public static BufferedImage pHBug;
	public static BufferedImage pHTile;
	public static BufferedImage pHBG;
	public static BufferedImage skyBG;
	public static BufferedImage spriteSheet;
	
	public static void importImages() throws IOException {
		menu = ImageIO.read(new File("chowchowmenu.png"));
		level = ImageIO.read(new File("level.png"));
		pHTile = ImageIO.read(new File("20dirt.png"));
		pHDog = ImageIO.read(new File("pHChar.png"));
		pHBug = ImageIO.read(new File("mad.png"));
		skyBG = ImageIO.read(new File("basiclevelbg.png"));
		dogRight1 = ImageIO.read(new File("whitedogright.png"));
		dogLeft1 = ImageIO.read(new File("whitedogleft.png"));
		for (int i = 0; i < grassTiles.length; i++) {
			grassTiles[i] = ImageIO.read(new File("20grass" + (i+1) + ".png"));
		}
		for (int i = 0; i < rightIdleDog1.length; i++) {
			rightIdleDog1[i] = dogRight1.getSubimage(i*40,0,40, 40);
		}
//		for (int i = 0;i < rightWalkDog1.length; i++) {
//			rightWalkDog1[i] = grabImage(i+7,2,dogRight1);
//		}
//		for (int i = 0; i < rightRunDog1.length; i++) {
//			rightRunDog1[i] = grabImage(i+10,2,dogRight1);
//		}
//		rightJumpDog1[0] = grabImage(10,2,dogRight1);
//		for (int i = 1;i < rightJumpDog1.length; i++) {
//			rightJumpDog1[i] = grabImage(i+9,1,dogRight1);
//		}
//		for (int i = 0; i < rightIdleDog1.length; i++) {
//			rightIdleDog1[i] = dogRight1.getSubimage(i*40,0,40, 40);
//		}
//		for (int i = 0; i < rightWalkDog1.length; i++) {
//			lefttWalkDog1[i] = grabImage(i,2,dogLeft1);
//		}
//		for (int i = 0; i < rightWalkDog1.length; i++) {
//			leftRunDog1[i] = grabImage(i*3,2,dogLeft1);
//		}
//		leftJumpDog1[0] = grabImage(2,1,dogLeft1);
//		for (int i = rightJumpDog1.length; i >= 1; i--) {
//			leftJumpDog1[i] = grabImage(i+3,1,dogLeft1);
//		}
//		for (int i = 0; i < rightIdleDog1.length; i++) {
//			leftIdleDog1[i] = grabImage(i+11,1,dogLeft1);
//		}
	}
	public static BufferedImage grabImage(int col, int row, BufferedImage img) {
		BufferedImage image = img.getSubimage(col*40, row*40, 40, 40);
		return image;
	}
}
