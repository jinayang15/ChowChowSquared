import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameFunctions {
	public static int[] strArrtoIntArr(String[] input) {
		int[] out = new int[input.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = Integer.parseInt(input[i]);
		}
		return out;
	}
	public static void loadGrid() {
		for (int i = 0; i < Main.winHeight/Main.tileSize; i++) {
			String[] temp = Main.in.nextLine().split("");
			int[] input = strArrtoIntArr(temp);
			for (int j = 0; j < input.length; j++) {
				Main.levelGrid[i][j] = input[j];
			}
		}
		
	}
	public static void drawTiles(Graphics g) {
		BufferedImage image = null;
		for (int i = 0; i < Main.levelGrid.length; i++) {
			for (int j = 0; j < Main.levelGrid[0].length; j++) {
				if (Main.levelGrid[i][j] == 1) {
					image = Images.pHTile;
				}
				if (Main.levelGrid[i][j] == 2) {
					image = Images.grassTiles[(int) Math.random()*2];
				}
				g.drawImage(image, i*40, j*40, null);
			}
		}
	}	
}