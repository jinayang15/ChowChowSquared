import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

public class GameFunctions {
	public static char[] strArrtoCharArr(String[] input) {
		char[] out = new char[input.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = input[i].charAt(0);
		}
		return out;
	}

	public static void load40Grid(Scanner sc) {
		for (int i = 0; i < Main.levelGrid40.length; i++) {
			String[] temp = Main.in.nextLine().split(" ");
			char[] input = strArrtoCharArr(temp);
			for (int j = 0; j < Main.levelGrid40[0].length; j++) {
				Main.levelGrid40[i][j] = input[j];
				if (Main.levelGrid40[i][j] == Enemy.spikeChar) {
					Enemy enmSpike = new Enemy(Enemy.spikeChar, Enemy.spikeWidth, Enemy.spikeHeight);
					enmSpike.setCoords(i, j);
					enmSpike.setImage(Images.spike);
					enmSpike.setImageAdjust(0, 0, 20, 0);
					Enemy.enemies.add(enmSpike);
				}
				else if (Main.levelGrid40[i][j] == Enemy.slimeChar) {
					Enemy enmSlime = new Enemy(Enemy.slimeChar, Enemy.slimeWidth, Enemy.slimeHeight);
					enmSlime.setCoords(i,j);
					enmSlime.setImage(Images.slime);
					enmSlime.setImageAdjust(8, 8, 8, 8);
					Enemy.enemies.add(enmSlime);
				}
			}
		}

	}

	public static void load20Grid() {
		for (int i = 0; i < Main.levelGrid40.length; i++) {
			for (int j = 0; j < Main.levelGrid40[0].length; j++) {
				Main.levelGrid20[i * 2][j * 2] = Main.levelGrid40[i][j];
				Main.levelGrid20[i * 2][j * 2 + 1] = Main.levelGrid40[i][j];
				Main.levelGrid20[i * 2 + 1][j * 2] = Main.levelGrid40[i][j];
				Main.levelGrid20[i * 2 + 1][j * 2 + 1] = Main.levelGrid40[i][j];
			}
		}
	}

	public static int genCurrentGrid() {
		int start = 0, end = 0;
		if (Math.abs(Main.bgX / Main.tileSize) == 0) {
			start = 0;
		} else {
			start = Math.abs(Main.bgX / Main.tileSize) - 1;
		}
		if (start + Main.tileWidth + 2 >= Main.levelTileWidth) {
			end = Main.levelTileWidth-1;
		} else {
			end = start + Main.tileWidth+1;
		}
		for (int i = 0; i < Main.tileHeight; i++) {
			for (int j = start; j <= end; j++) {
				Main.currentGrid[i][j - start] = Main.levelGrid20[i][j];
			}
		}
		return start;
	}

	public static void drawTiles(Graphics g, int start) {
		BufferedImage image = null;
		int startPoint = 0;
		if (start%2 == 1) {
			startPoint = 20;
		}
		start/=2;
		for (int i = 0; i < Main.tileHeight / 2; i++) {
			for (int j = start; j < start + Main.tileWidth/2+1; j++) {
				image = null;
				if (Main.levelGrid40[i][j] == '1') {
					image = Images.grassTile;
				} else if (Main.levelGrid40[i][j] == '2') {
					image = Images.dirtTile;
				}
				g.drawImage(image, (j - start)*40 - startPoint, i * 40, null);
			}
		}
	}
	public static void restartGame() {
		Main.dog = new Character();
		Main.bgX = 0;
		Main.bgY = 0;
		Main.currentGrid = new char[Main.tileHeight][Main.tileWidth + 2];
		Enemy.enemies = new ArrayList<Enemy>();
		Enemy.onScreenEnemies = new ArrayList<Enemy>();
		Enemy.removedEnemies = new ArrayList<Enemy>();
	}
}
