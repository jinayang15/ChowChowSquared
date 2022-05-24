import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
public class Images {
	// anything starting with "pH" is a placeholder, not final
	public static BufferedImage[] dogSprites;
	public static BufferedImage[] dirtTiles;
	public static BufferedImage[] grassTiles;
	
	public static BufferedImage pHDog;
	public static BufferedImage pHTile;
	public static void importImage() throws IOException {
		pHTile = ImageIO.read(new File("dirt1.png"));
		pHDog = ImageIO.read(new File("pHChar.png"));
	}
}
