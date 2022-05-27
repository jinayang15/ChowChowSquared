import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
public class Images {
	// anything starting with "pH" is a placeholder, not final
	public static final BufferedImage[] dogSprites = new BufferedImage[5];
	public static final BufferedImage[] dirtTiles = new BufferedImage[5];
	public static final BufferedImage[] grassTiles = new BufferedImage[2];
	public static BufferedImage menu;
	
	public static BufferedImage pHDog;
	public static BufferedImage pHTile;
	public static BufferedImage pHBG;
	
	
	public static void importImage() throws IOException {
		menu = ImageIO.read(new File("chowchowmenu.png"));
		pHTile = ImageIO.read(new File("dirt1.png"));
		pHDog = ImageIO.read(new File("pHChar.png"));
		pHBG = ImageIO.read(new File("scrollBackground.png"));
		for (int i = 0; i < grassTiles.length; i++) {
			grassTiles[i] = ImageIO.read(new File("grass" + (i+1) + ".png"));
		}
	}
}
