import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Enemy extends Rectangle {
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static ArrayList<Enemy> onScreenEnemies = new ArrayList<Enemy>();
	public int inArr = -1;
	public static char spikeChar = '#';
	public static char slimeChar = '$';
	public int hitboxWidth;
	public int hitboxHeight;
	public int imageAdjustXLeft;
	public int imageAdjustXRight;
	public int imageAdjustYTop;
	public int imageAdjustYBot;
	private ArrayList<Integer> tilesX = new ArrayList<Integer>();
	private ArrayList<Integer> tilesY = new ArrayList<Integer>();
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private int moveX = 5;
	public static int noCollide = -100;
	private double verticalDirection = 0;
	private double horizontalDirection = -1;
	public int enemyType;
	public static int spikeWidth = 40;
	public static int spikeHeight = 20;
	public static int slimeWidth = 24;
	public static int slimeHeight = 24;
	public int coordY;
	public int coordX;
	public BufferedImage enmImage;

	public Enemy() {
		setBounds(noCollide, noCollide, Main.imageWidth, Main.imageHeight);
		setHitbox(20, 20);
	}

	public Enemy(int type, int width, int height) {
		setBounds(noCollide, noCollide, Main.imageWidth, Main.imageHeight);
		setHitbox(width, height);
		enemyType = type;
	}

	public void setCoords(int y, int x) {
		coordY = y;
		coordX = x;
	}

	public int[] getCoords() {
		int[] coords = { coordY, coordX };
		return coords;
	}

	public void setHitbox(int width, int height) {
		hitboxWidth = width;
		hitboxHeight = height;
	}

	public void setImage(BufferedImage image) {
		enmImage = image;
	}

	public void setImageAdjust(int left, int right, int top, int bot) {
		imageAdjustXLeft = left;
		imageAdjustXRight = right;
		imageAdjustYTop = top;
		imageAdjustYBot = bot;
	}

	public void refreshTile() {
		int numX, numY;
		ArrayList<Integer> copyX, copyY;
		tilesX = new ArrayList<Integer>();
		tilesY = new ArrayList<Integer>();
		tilesX = getTilesX(tilesX);
		tilesY = getTilesY(tilesY);
		copyX = (ArrayList<Integer>) tilesX.clone();
		copyY = (ArrayList<Integer>) tilesY.clone();
		numX = tilesX.size();
		numY = tilesY.size();
		for (int i = 1; i < numY; i++) {
			tilesX.addAll(copyX);
		}
		Collections.sort(tilesX);
		for (int i = 1; i < numX; i++) {
			tilesY.addAll(copyY);
		}

//		System.out.println("Occupies: ");
//		for (int i = 0; i < tilesX.size(); i++) {
//			System.out.println(tilesY.get(i) + " " + tilesX.get(i));
//		}
//		System.out.println();
	}

	public ArrayList<Integer> getTilesX(ArrayList<Integer> tilesX) {
		int x1 = (int) getX() + imageAdjustXLeft;
		int x2 = x1 + hitboxWidth;
//		System.out.println("X: " + x1 + " " + x2);
		int roundx1;
		int roundx2;

		if (x1 % Main.tileSize != 0) {
			roundx1 = x1 - x1 % Main.tileSize;
		} else {
			roundx1 = x1;
		}
		if (x2 % Main.tileSize != 0) {
			roundx2 = x2 - x2 % Main.tileSize;
		} else {
			roundx2 = x2;
		}
		if (x1 - roundx1 >= Main.tileSize / 2) {
			roundx1 += Main.tileSize;
		}
		if (x2 - roundx2 >= Main.tileSize / 2) {
			roundx2 += Main.tileSize;
		}
		for (int i = roundx1; i < roundx2; i += Main.tileSize) {
			tilesX.add(i / Main.tileSize);
		}
		return tilesX;

	}

	public ArrayList<Integer> getTilesY(ArrayList<Integer> tilesY) {
		int y1 = (int) getY() + imageAdjustYTop;
		int y2 = y1 + hitboxHeight;
		// System.out.println("Y: " + y1 + " " + y2);
		int roundy1;
		int roundy2;
		if (y1 % Main.tileSize != 0) {
			roundy1 = y1 - Math.abs(y1 % Main.tileSize);
		} else {
			roundy1 = y1;
		}
		if (y2 % Main.tileSize != 0) {
			roundy2 = y2 - y1 % Math.abs(y2 % Main.tileSize);
		} else {
			roundy2 = y2;
		}
		if (Math.abs(y1 - roundy1) >= Main.tileSize / 2) {
			roundy1 += Main.tileSize;
		}
		if (Math.abs(y2 - roundy2) >= Main.tileSize / 2) {
			roundy2 += Main.tileSize;
		}
		for (int i = roundy1; i < roundy2; i += Main.tileSize) {
			tilesY.add(i / Main.tileSize);
		}
		return tilesY;
	}

	public static void loadOnScreenEnemies(Graphics g, int start) {
		int startPoint = 0;
		int enmX, enmY;
		if (start % 2 == 1) {
			startPoint = 20;
		}
		start /= 2;
		for (int i = 0; i < enemies.size(); i++) {
			Enemy temp = (Enemy) enemies.get(i).clone();
			temp.inArr = -1;
			enmX = temp.getCoords()[1];
			enmY = temp.getCoords()[0];
			temp.setBounds((enmX - start) * Main.imageWidth - startPoint + temp.imageAdjustXLeft,
					enmY * Main.imageHeight + temp.imageAdjustYBot, Main.imageWidth, Main.imageHeight);
			for (int k = 0; k < onScreenEnemies.size(); k++) {
				Enemy compare = onScreenEnemies.get(k);
				if (compare.getCoords()[0] == enmY && compare.getCoords()[1] == enmX) {
					temp.inArr = k;
					break;
				}
			}
			if (temp.inArr != -1 && onScreenEnemies.get(temp.inArr).getX() < 0) {
				onScreenEnemies.remove(temp.inArr);
			} else if (temp.inArr == -1) {
				if (temp.getX() >= 0 && temp.getX() <= Main.winWidth) {
					onScreenEnemies.add(temp);
				}
			}

		}
		for (int i = 0; i < onScreenEnemies.size(); i++) {
			Enemy draw = onScreenEnemies.get(i);
			g.drawImage(draw.enmImage, (int) draw.getX(), (int) draw.getY(), null);
			g.setColor(new Color(0, 0, 255));
			g.drawRect((int) draw.getX(), (int) draw.getY(), Main.imageWidth, Main.imageHeight);
			g.setColor(new Color(255, 255, 255));
			g.drawRect((int) draw.getX() + draw.imageAdjustXLeft, (int) draw.getY() + draw.imageAdjustYTop,
					draw.hitboxWidth, draw.hitboxHeight);
		}
	}

	public static void moveEnemies() {
		for (Enemy enm : onScreenEnemies) {
			if (enm.enemyType == slimeChar) {
				enm.refreshTile();
				System.out.println("Start2: " + enm.getX() + " " + enm.getY());
				enm.moveLeft();
				System.out.println("Left: " + enm.getX() + " " + enm.getY());
				enm.refreshTile();
				enm.moveRight();
				System.out.println("Right: " + enm.getX() + " " + enm.getY());
				enm.refreshTile();
				//enm.fixPosition();
			}
		}
	}

	public static void shiftEnemies() {
		for (Enemy enm : onScreenEnemies) {
			enm.translate(-Main.tileSize, 0);
		}
	}

	// basic mechanics are down
	// retrieves current verticalDirection
	// value -1 to 1
	public double getVerticalDirection() {
		return verticalDirection;
	}

	// sets verticalDirection to specific value
	// default 0
	public void setVerticalDirection(int num) {
		verticalDirection = num;
	}

	public double getHorizontalDirection() {
		return horizontalDirection;
	}

	// sets verticalDirection to specific value
	// default 0
	public void setHorizontalDirection(int num) {
		horizontalDirection = num;
	}

	public boolean isMovingUp() {
		return up;
	}

	// set moving left
	public void setMovingUp(boolean bool) {
		up = bool;
	}

	// same as other two
	public boolean isMovingDown() {
		return down;
	}

	public void setMovingDown(boolean bool) {
		down = bool;
	}

	public boolean isMovingLeft() {
		return left;
	}

	// set moving left
	public void setMovingLeft(boolean bool) {
		left = bool;
	}

	// same as other two
	public boolean isMovingRight() {
		return right;
	}

	public void setMovingRight(boolean bool) {
		right = bool;
	}

	public void moveLeft() {
		if (getHorizontalDirection() == -1) {
			int[] blockUnder = checkBlockBelow();
			if (blockUnder[0] != noCollide) {
				translate(-moveX, 0);
				if (blockUnder[1] - 1 >= 0 && Main.currentGrid[blockUnder[0]][blockUnder[1]-1] == '0') {
					if (getX() - imageAdjustXLeft <= blockUnder[1] * Main.tileSize) {
						setHorizontalDirection(1);
						setLocation(blockUnder[1] * Main.tileSize - imageAdjustXLeft, (int) getY());
					}
				}
			}
		}
	}

	public void moveRight() {
		if (getHorizontalDirection() == 1) {
			int[] blockUnder = checkBlockBelow();
			if (blockUnder[0] != noCollide) {
				translate(moveX, 0);
				if (blockUnder[1] + 1 < Main.tileWidth && Main.currentGrid[blockUnder[0]][blockUnder[1]+1] == '0') {
					if (getX() + hitboxWidth + imageAdjustXLeft >= (blockUnder[1]+1) * Main.tileSize ) {
						setHorizontalDirection(-1);
						setLocation((blockUnder[1]+1) * Main.tileSize - hitboxWidth - imageAdjustXLeft, (int) getY());
					}
				}
			}
		}
	}
	
	public void fixPosition() {
		int[] blockLeft = checkTileCollisionLeft();
		int[] blockRight = checkTileCollisionRight();
		if (checkBlockBelow()[0] == noCollide) {
			setHorizontalDirection(1);
			setLocation((checkBlockBelow()[1]+1) * Main.tileSize - imageAdjustXLeft, (int) getY());
		} else if (blockLeft[1] != noCollide) {
			setHorizontalDirection(1);
			setLocation((blockLeft[1] + 1) * Main.tileSize - imageAdjustXLeft, (int) getY());
		}
		if (checkBlockBelow()[0] == noCollide) {
			setHorizontalDirection(-1);
			setLocation((checkBlockBelow()[1]) * Main.tileSize - Main.imageWidth + imageAdjustXRight,
					(int) getY());
		} else if (blockRight[1] != noCollide) {
			setHorizontalDirection(-1);
			setLocation(blockRight[1] * Main.tileSize - Main.imageWidth + imageAdjustXRight, (int) getY());
		}
	}
	public int[] checkBlockAbove() {
		// {noCollide, noCollide} means no block above
		int[] blockAbove = { noCollide, noCollide };
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			// make sure we are checking within the grid
			if (Main.currentGrid[y][x] > '0' && Main.currentGrid[y][x] <= '9') {
				blockAbove[0] = y;
				blockAbove[1] = x;
				return blockAbove;
			}
			if (y - 1 >= 0) {
				// check if the blockAbove is a tile
				// if it is set blockAbove to the tile coords
				if (Main.currentGrid[y - 1][x] > '0' && Main.currentGrid[y - 1][x] <= '9') {
					if (blockAbove[0] == noCollide || y - 1 > blockAbove[0]) {
						blockAbove[0] = y - 1;
						blockAbove[1] = x;
					}
				}
			} else {
				blockAbove[0] = y - 1;
				blockAbove[1] = x;
			}
		}
		// return tile coords
		return blockAbove;
	}

	public int[] checkTileCollisionAbove() {
		int[] blockCollides = checkBlockAbove();
		if (blockCollides[0] != noCollide) {
			if (getY() - imageAdjustYTop > (blockCollides[0] + 1) * Main.tileSize) {
				blockCollides[0] = noCollide;
			}
		}
		return blockCollides;
	}

	// checks for block below character
	public int[] checkBlockBelow() {
		// {-1, -1} means no block under
		int[] blockUnder = { noCollide, noCollide };
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			if (Main.currentGrid[y][x] > '0' && Main.currentGrid[y][x] <= '9') {
				blockUnder[0] = y;
				blockUnder[1] = x;
				return blockUnder;
			}
			// make sure we are checking within the grid
			if (y + 1 < Main.tileHeight) {
				// check if the blockUnder is a tile
				// if it is set blockUnder to the tile coords
				if (Main.currentGrid[y + 1][x] > '0' && Main.currentGrid[y + 1][x] <= '9') {
					if (blockUnder[0] == noCollide || y + 1 < blockUnder[0]) {
						blockUnder[0] = y + 1;
						blockUnder[1] = x;
					}
				}
			} else {
				blockUnder[0] = y + 1;
				blockUnder[1] = x;
			}
		}
		// return tile coords
		return blockUnder;
	}

	// checks whether the character has crossed into block below it
	public int[] checkTileCollisionBelow() {
		int[] blockCollides = checkBlockBelow();
		if (blockCollides[0] != noCollide) {
			if (getY() + Main.imageHeight - imageAdjustYBot < blockCollides[0] * Main.tileSize) {
				blockCollides[0] = noCollide;
			}
		}
		return blockCollides;
	}

	// checks whether there is a block to the characters right
	public int[] checkBlockRight() {
		// {-1, -1} means no block right
		int[] blockRight = { noCollide, noCollide };
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			// make sure we are checking within the grid
			if (x + 1 < Main.tileWidth && y < Main.tileHeight) {
				// check if the blockRight is a tile
				// if it is set blockRight to the tile coords
				if (Main.currentGrid[y][x + 1] > '0' && Main.currentGrid[y][x + 1] <= '9') {
					if (blockRight[0] == noCollide || x + 1 < blockRight[0]) {
						blockRight[0] = y;
						blockRight[1] = x + 1;
					}
				}
			} else {
				blockRight[0] = y;
				blockRight[1] = x + 1;
			}
		}
		// return tile coords
		return blockRight;
	}

	// checks whether the character has crossed into block to its right
	public int[] checkTileCollisionRight() {
		int[] blockCollides = checkBlockRight();
		if (blockCollides[1] != noCollide) {
			if (getX() + imageAdjustXRight + hitboxWidth < blockCollides[1] * Main.tileSize) {
				blockCollides[1] = noCollide;
			}
		}
		return blockCollides;
	}

	// checks whether there is a block to the characters left
	public int[] checkBlockLeft() {
		// {-1, -1} means no block left
		int[] blockLeft = { noCollide, noCollide };
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			// check within grid
			if (x - 1 >= 0 && y < Main.tileHeight) {
				// check if the blockLeft is a tile
				// if it is set blockLeft to the tile coords
				if (Main.currentGrid[y][x - 1] > '0' && Main.currentGrid[y][x - 1] <= '9') {
					if (blockLeft[0] == noCollide || x - 1 > blockLeft[0]) {
						blockLeft[0] = y;
						blockLeft[1] = x - 1;
					}
				}
			} else {
				blockLeft[0] = y;
				blockLeft[1] = x - 1;
			}
		}
		// return tile coords
		return blockLeft;
	}

	// checks whether the character has crossed into block to its left
	public int[] checkTileCollisionLeft() {
		int[] blockCollides = checkBlockLeft();
		if (blockCollides[1] != noCollide) {
			if (getX() - imageAdjustXLeft > (blockCollides[1] + 1) * Main.tileSize) {
				blockCollides[1] = noCollide;
			}
		}
		return blockCollides;
	}

}
