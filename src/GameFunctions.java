import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class GameFunctions {
	public static int[] strArrtoIntArr(String[] input) {
		int[] out = new int[input.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = Integer.parseInt(input[i]);
		}
		return out;
	}
	public static void loadGrid(Scanner sc) {
		for (int i = 0; i < Main.winHeight/Main.tileSize; i++) {
			String[] temp = Main.in.nextLine().split(" ");
			int[] input = strArrtoIntArr(temp);
			for (int j = 0; j < input.length; j++) {
				Main.levelGrid[i][j] = input[j];
			}
		}
		
	}
	public static void drawTiles(Graphics g) {
		BufferedImage image;
		int start;
		int end;
		for (int i = 0; i < Main.tileHeight; i++) {
			if ((-1*Main.bgX)/Main.tileSize == 0) {
				start = 0;
			}
			else {
				start = (-1*Main.bgX)/Main.tileSize - 1;
			}
			if (start + Main.tileWidth + 2>= Main.levelTileWidth) {
				end = Main.levelTileWidth;
			}
			else {
				end = start + Main.tileWidth + 2;
			}
			for (int j = start; j < end; j++) {
				image = null;
				Main.currentGrid[i][j-start] = Main.levelGrid[i][j];
				if (Main.levelGrid[i][j] == 1) {
					image = Images.pHTile;
				}
				else if (Main.levelGrid[i][j] == 2) {
					image = Images.grassTiles[(int) Math.random()*Images.grassTiles.length];
				}
				g.drawImage(image, (j-start)*Main.tileSize, i*Main.tileSize, null);
			}
		}
	}	
}