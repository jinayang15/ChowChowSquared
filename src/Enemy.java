import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
// Enemy Class that the determines characteristics of
// Enemy instances
public class Enemy extends Rectangle {
	// Yes, inconsistency w private and public var as well as setters and getters
	// shh.....
	
	// All enemies are in this array
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	// Currently onScreenEnemies are in this array
	public static ArrayList<Enemy> onScreenEnemies = new ArrayList<Enemy>();
	// Enemies that have been unloaded
	public static ArrayList<Enemy> removedEnemies = new ArrayList<Enemy>();
	// checks if the current enemy is onScreen or removed
	public int onScreenIndex = -1;
	public int removedIndex = -1;
	// char that represents spike or slime in the grid
	public static char spikeChar = '#';
	public static char slimeChar = '$';
	// Hitbox
	public int hitboxWidth;
	public int hitboxHeight;
	// Image Adjustments for whitespace
	public int imageAdjustLeft;
	public int imageAdjustRight;
	public int imageAdjustTop;
	public int imageAdjustBot;
	// checks what tile the enemy occupies
	private ArrayList<Integer> tilesX = new ArrayList<Integer>();
	private ArrayList<Integer> tilesY = new ArrayList<Integer>();
	// how far the enemy moves
	private int moveX = 5;
	// arbitrary value for air block/no collision
	public static int noCollide = -100;
	// horizontal direction -1 for left 1 for right
	private int horizontalDirection = -1;
	// enemy type slime or spike
	public char enemyType;
	// spike hitbox
	public static int spikeWidth = 40;
	public static int spikeHeight = 20;
	// slime hitbox
	public static int slimeWidth = 22;
	public static int slimeHeight = 22;
	// coordinates Y, X on 40x40 tiles level grid
	public int coordY;
	public int coordX;
	// image
	public BufferedImage enmImage;
	// default enemy
	public Enemy() {
		setBounds(noCollide, noCollide, Main.imageWidth, Main.imageHeight);
		setHitbox(20, 20);
	}
	// enemy w info
	public Enemy(char type, int width, int height) {
		setBounds(noCollide, noCollide, Main.imageWidth, Main.imageHeight);
		setHitbox(width, height);
		enemyType = type;
	}
	// set coordinates on 40x40 tiles level grid
	public void setCoords(int y, int x) {
		coordY = y;
		coordX = x;
	}
	// get coordinates
	public int[] getCoords() {
		int[] coords = { coordY, coordX };
		return coords;
	}
	// set hitbox width and height
	public void setHitbox(int width, int height) {
		hitboxWidth = width;
		hitboxHeight = height;
	}
	// set the image
	public void setImage(BufferedImage image) {
		enmImage = image;
	}
	// set image adjustments
	public void setImageAdjust(int left, int right, int top, int bot) {
		imageAdjustLeft = left;
		imageAdjustRight = right;
		imageAdjustTop = top;
		imageAdjustBot = bot;
	}
	// same
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
	}
	// same
	public ArrayList<Integer> getTilesX(ArrayList<Integer> tilesX) {
		int x1 = (int) getX() + imageAdjustLeft;
		int x2 = x1 + hitboxWidth;
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
	// same
	public ArrayList<Integer> getTilesY(ArrayList<Integer> tilesY) {
		int y1 = (int) getY() + imageAdjustTop;
		int y2 = y1 + hitboxHeight;
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
	// loads the onscreen enemies
	public static void loadOnScreenEnemies(Graphics g, int start) {
		int startPoint = 0;
		int enmX, enmY;
		if (start % 2 == 1) {
			startPoint = 20;
		}
		start /= 2;
		for (int i = 0; i < enemies.size(); i++) {
			// check if enemies should be onscreen
			Enemy temp = (Enemy) enemies.get(i).clone();
			temp.onScreenIndex = -1;
			enmX = temp.getCoords()[1];
			enmY = temp.getCoords()[0];
			temp.setBounds((enmX - start) * Main.imageWidth - startPoint + temp.imageAdjustLeft,
					enmY * Main.imageHeight + temp.imageAdjustBot, Main.imageWidth, Main.imageHeight);
			temp.onScreenIndex = enmInArr(enmY, enmX, onScreenEnemies);
			temp.removedIndex = enmInArr(enmY, enmX, removedEnemies);
			// remove from onScreen if its off screen
			if (temp.onScreenIndex != -1 && onScreenEnemies.get(temp.onScreenIndex).getX() + Main.imageWidth < 0) {
				removedEnemies.add(onScreenEnemies.get(temp.onScreenIndex));
				onScreenEnemies.remove(temp.onScreenIndex);
			}
			// add to onScreen enemies list if its not already there or if it wasn't already removed
			else if (temp.onScreenIndex == -1 && temp.removedIndex == -1) {
				if (temp.getX() >= 0 && temp.getX() <= Main.winWidth) {
					onScreenEnemies.add(temp);
				}
			}

		}
		// draw all onscreen enemies
		for (int i = 0; i < onScreenEnemies.size(); i++) {
			Enemy draw = onScreenEnemies.get(i);
			g.drawImage(draw.enmImage, (int) draw.getX(), (int) draw.getY(), null);
		}
	}
	// check if enemy is in the current list (onScreenEnemies or removed)
	public static int enmInArr(int enmY, int enmX, ArrayList<Enemy> arr) {
		for (int k = 0; k < arr.size(); k++) {
			Enemy compare = arr.get(k);
			if (compare.getCoords()[0] == enmY && compare.getCoords()[1] == enmX) {
				// index it is at
				return k;
			}
		}
		// not in array
		return -1;
	}
	// move enemies if it is a slime
	public static void moveEnemies() {
		Enemy enm;
		for (int i = 0; i < onScreenEnemies.size(); i++) {
			enm = onScreenEnemies.get(i);
			if (enm.enemyType == slimeChar && enm.getX() + enm.hitboxWidth >= 0) {
				enm.refreshTile();
				if (enm.getHorizontalDirection() == -1) {
					enm.moveLeft();
					enm.refreshTile();
				} else if (enm.getHorizontalDirection() == 1) {
					enm.moveRight();
					enm.refreshTile();
				}
			}
		}
	}
	// shift enemies when the screen scrolls
	public static void shiftEnemies() {
		Enemy enm;
		for (int i = 0; i < onScreenEnemies.size(); i++) {
			enm = onScreenEnemies.get(i);
			enm.translate(-Main.tileSize, 0);
			if (enm.enemyType == slimeChar) {
				// adjust if the enemy gets shifted over the edge of platforms or into blocks
				int[] blockUnder = enm.checkBlockBelow();
				int[] blockLeft = enm.checkTileCollisionLeft();
				int[] blockRight = enm.checkTileCollisionRight();
				// into blocks left right
				if (blockLeft[1] != noCollide && blockLeft[1] >= 0) {
					enm.setHorizontalDirection(1);
					enm.setLocation((blockLeft[1] + 1) * Main.tileSize - enm.imageAdjustLeft, (int) enm.getY());
				}
				if (blockRight[1] != noCollide) {
					enm.setHorizontalDirection(-1);
					enm.setLocation(blockRight[1] * Main.tileSize - Main.imageWidth + enm.imageAdjustRight, (int) enm.getY());
				} 
				// edge of block
				if (blockUnder[1] - 1 >= 0 && Main.currentGrid[blockUnder[0]][blockUnder[1] - 1] == '0') {
					if (enm.getX() - enm.imageAdjustLeft <= blockUnder[1] * Main.tileSize) {
						enm.setHorizontalDirection((int) (enm.getHorizontalDirection() * -1));
						enm.setLocation(blockUnder[1] * Main.tileSize - enm.imageAdjustLeft, (int) enm.getY());
					}
				} else if (blockUnder[1] + 1 >= 0 && blockUnder[1] + 1 < Main.tileWidth
						&& Main.currentGrid[blockUnder[0]][blockUnder[1] + 1] == '0') {
					if (enm.getX() + enm.hitboxWidth + enm.imageAdjustLeft >= blockUnder[1] * Main.tileSize) {
						enm.setHorizontalDirection((int) (enm.getHorizontalDirection() * -1));
						enm.setLocation(blockUnder[1] * Main.tileSize - enm.hitboxWidth - enm.imageAdjustLeft,
								(int) enm.getY());
					}
				}
			}
		}
	}
	// get the horizontal direction
	public int getHorizontalDirection() {
		return horizontalDirection;
	}

	// sets verticalDirection to specific value
	// default 0
	public void setHorizontalDirection(int num) {
		horizontalDirection = num;
	}
	// Character move left
	public void moveLeft() {
		int[] blockUnder = checkBlockBelow();
		int[] blockLeft = checkTileCollisionLeft();
		if (blockUnder[0] != noCollide) {
			translate(-moveX, 0);
			if (getX() >= 0) {
				// adjust if there are collisions or over the edge of platform
				if (blockLeft[1] != noCollide) {
					setHorizontalDirection(1);
					setLocation((blockLeft[1] + 1) * Main.tileSize - imageAdjustLeft, (int) getY());
				} else if (blockUnder[1] - 1 >= 0 && Main.currentGrid[blockUnder[0]][blockUnder[1] - 1] == '0') {
					if (getX() - imageAdjustLeft <= blockUnder[1] * Main.tileSize) {
						setHorizontalDirection(1);
						setLocation(blockUnder[1] * Main.tileSize - imageAdjustLeft, (int) getY());
					}
				}
			}
		}
	}
	// Character move right
	public void moveRight() {
		int[] blockUnder = checkBlockBelow();
		int[] blockRight = checkTileCollisionRight();
		if (blockUnder[0] != noCollide) {
			translate(moveX, 0);
			if (getX() >= 0) {
				// same
				if (blockRight[1] != noCollide) {
					setHorizontalDirection(-1);
					setLocation(blockRight[1] * Main.tileSize - Main.imageWidth + imageAdjustRight, (int) getY());
				} else if (blockUnder[1] + 1 < Main.tileWidth
						&& Main.currentGrid[blockUnder[0]][blockUnder[1] + 1] == '0') {
					if (getX() + hitboxWidth + imageAdjustLeft >= (blockUnder[1] + 1) * Main.tileSize) {
						setHorizontalDirection(-1);
						setLocation((blockUnder[1] + 1) * Main.tileSize - hitboxWidth - imageAdjustLeft, (int) getY());
					}
				}
			}
		}
	}
	// All the same as the Character methods
	// -------------------------------------
	public int[] checkBlockBelow() {
		// {-1, -1} means no block under
		int[] blockUnder = { noCollide, noCollide };
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			if (x >= 0 && x < Main.tileWidth && y >= 0 && y < Main.tileHeight) {
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
		}
		// return tile coords
		return blockUnder;
	}

	// checks whether the character has crossed into block below it
	public int[] checkTileCollisionBelow() {
		int[] blockCollides = checkBlockBelow();
		if (blockCollides[0] != noCollide) {
			if (getY() + Main.imageHeight - imageAdjustBot < blockCollides[0] * Main.tileSize) {
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
			if (x >= 0 && x < Main.tileWidth && y >= 0 && y < Main.tileHeight) {
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
		}
		// return tile coords
		return blockRight;
	}

	// checks whether the character has crossed into block to its right
	public int[] checkTileCollisionRight() {
		int[] blockCollides = checkBlockRight();
		if (blockCollides[1] != noCollide) {
			if (getX() + imageAdjustRight + hitboxWidth < blockCollides[1] * Main.tileSize) {
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
			if (x >= 0 && x < Main.tileWidth && y >= 0 && y < Main.tileHeight) {
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
		}
		// return tile coords
		return blockLeft;
	}

	// checks whether the character has crossed into block to its left
	public int[] checkTileCollisionLeft() {
		int[] blockCollides = checkBlockLeft();
		if (blockCollides[1] != noCollide) {
			if (getX() - imageAdjustLeft > (blockCollides[1] + 1) * Main.tileSize) {
				blockCollides[1] = noCollide;
			}
		}
		return blockCollides;
	}

}
