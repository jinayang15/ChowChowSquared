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
<<<<<<< HEAD
			String[] temp = Main.in.nextLine().split(" ");
=======
			String[] temp = sc.nextLine().split(" ");
>>>>>>> branch 'main' of https://github.com/jinayang15/ICS3UCulminating
			int[] input = strArrtoIntArr(temp);
			for (int j = 0; j < input.length; j++) {
				Main.levelGrid[i][j] = input[j];
			}
		}
		
	}
	public static void drawTiles(Graphics g) {
		BufferedImage image;
		for (int i = 0; i < Main.levelGrid.length; i++) {
			for (int j = 0; j < Main.levelGrid[0].length; j++) {
				image = null;
				if (Main.levelGrid[i][j] == 1) {
					image = Images.pHTile;
				}
				if (Main.levelGrid[i][j] == 2) {
					image = Images.grassTiles[(int) Math.random()*Images.grassTiles.length];
				}
				g.drawImage(image, j*Main.tileSize, i*Main.tileSize, null);
			}
		}
	}	
}