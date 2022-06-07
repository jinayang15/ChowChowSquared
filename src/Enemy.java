import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Rectangle{
	private ArrayList<Integer> tilesX = new ArrayList<Integer>();
	private ArrayList<Integer> tilesY = new ArrayList<Integer>();
	public int hitboxWidth;
	public int hitboxHeight;
	public int imageAdjustX;
	public int imageAdjustY;
	private boolean left = false;
	private boolean right = false;
	public int health = 1;
	
	// pixels moved left and right
	private static int moveX = 10;
	public static int noCollide = -100;
	
	private Rectangle bounds;
	public int aniIndex, enemyState, enemyType;
	public int aniSpeed = 25;

	
//	public Enemy(int x, int y, int width, int height) {
//		this.x = x;
//		this.y = y;
//		this.width = width;
//		this.height = height;
//		bounds = new Rectangle ((int)x, (int)y, 20, 20);
//		
//	}
	
	public void update(){
		
		moveLeft();
		moveRight();
		// do we need refresh? idk
		// needs refresh yes
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public void moveLeft() {
		if (checkBlockLeft()[0]==-1) {
			this.translate(-moveX, 0);
			int[] blockCollides = checkTileCollisionLeft();
			if (blockCollides[1] != -1) {
				this.setLocation((blockCollides[1] + 1) * Main.tileSize, (int) this.getY());
				setRight(true);
				moveRight();
			}
		}
	}
	public void moveRight() {
		if (checkBlockLeft()[0]==-1) {
			this.translate(moveX, 0);
			int[] blockCollides = checkTileCollisionRight();
			if (blockCollides[1] != -1) {
				this.setLocation(blockCollides[1] * Main.tileSize - Main.imageWidth, (int) this.getY());
				setLeft(true);
				moveLeft();
			}
		}
	}
	
	// set moving right
	public void setRight(boolean bool) {
		right = bool;
	}
	// set moving left
	public void setLeft(boolean bool) {
		left = bool;
	}
	
	// checks for block above it
		public int[] checkBlockAbove() {
			// {-1, -1} means no block above
			int[] blockAbove = { noCollide, noCollide };
			int x, y;
			// check all the blocks that the character is in
			for (int i = 0; i < tilesX.size(); i++) {
				x = tilesX.get(i);
				y = tilesY.get(i);
				// make sure we are checking within the grid
				if (y - 1 >= 0) {
					// check if the blockAbove is a tile
					// if it is set blockAbove to the tile coords
					if (Main.currentGrid[y - 1][x] == 1) {
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

		// tells me whether the character has crossed into block above it
		public int[] checkTileCollisionAbove() {
			int[] blockCollides = checkBlockAbove();
			if (blockCollides[0] != noCollide) {
				if (this.getY() - imageAdjustY >= (blockCollides[0] + 1) * Main.tileSize) {
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
				// make sure we are checking within the grid
				if (y + 1 < Main.tileHeight) {
					// check if the blockUnder is a tile
					// if it is set blockUnder to the tile coords
					if (Main.currentGrid[y + 1][x] == 1) {
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
				if (this.getY() + Main.imageHeight - imageAdjustY <= blockCollides[0] * Main.tileSize) {
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
				if (x + 1 < Main.tileWidth) {
					// check if the blockRight is a tile
					// if it is set blockRight to the tile coords
					if (Main.currentGrid[y][x + 1] == 1) {
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
				if (this.getX() + imageAdjustX + hitboxWidth <= blockCollides[1] * Main.tileSize) {
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
				y = tilesY.get(i); // make sure we are checking within the grid
				if (x - 1 >= 0) {
					// check if the blockLeft is a tile
					// if it is set blockLeft to the tile coords
					if (Main.currentGrid[y][x - 1] == 1) {
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
				if (this.getX() - imageAdjustX >= (blockCollides[1] + 1) * Main.tileSize) {
					blockCollides[1] = noCollide;
				}
			}
			return blockCollides;
		}
}
