import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

public class Character extends Rectangle {
	// jumping mechanics are currently incomplete
	private ArrayList<Integer> tilesX = new ArrayList<Integer>();
	private ArrayList<Integer> tilesY = new ArrayList<Integer>();
	// Class Variables
	private boolean left = false;
	private boolean right = false;
	// pixels moved left and right
	private static int moveX = 10;

	// jump = true, character is jumping
	// jump = false, character is not jumping
	private boolean jump = false;
	// how high the character will jump
	private static int gravity = 40;
	// direction determines whether the character is jumping up or falling down
	// -1 max jump -> 1 max fall
	private double direction = 0;
	// how fast the character will jump or fall
	private static double jumpSpeed = 0.25;
	private static double fallSpeed = 0.015;

	// Class Methods
	// will add comments for the changes later
	public void refreshTile() {
		int numX, numY;
		ArrayList<Integer> copyX, copyY;
		tilesX.clear();
		tilesY.clear();
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

	public ArrayList<Integer> getTilesX(ArrayList<Integer> tilesX) {
		int x1 = (int) this.getX();
		int x2 = x1 + Main.imageWidth;
		System.out.println("Horizontal: " + x1 + " " + x2);
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
		System.out.println("Round Horizontal: " + roundx1 + " " + roundx2);
		return tilesX;

	}

	public ArrayList<Integer> getTilesY(ArrayList<Integer> tilesY) {
		int y1 = (int) this.getY();
		int y2 = y1 + Main.imageHeight;
		System.out.println("Vertical: " + y1 + " " + y2);
		int roundy1;
		int roundy2;
		if (y1 % Main.tileSize != 0) {
			roundy1 = y1 - y1 % Main.tileSize;
		} else {
			roundy1 = y1;
		}
		if (y2 % Main.tileSize != 0) {
			roundy2 = y2 - y2 % Main.tileSize;
		} else {
			roundy2 = y2;
		}
		if (y1 - roundy1 >= Main.tileSize / 2) {
			roundy1 += Main.tileSize;
		}
		if (y2 - roundy2 >= Main.tileSize / 2) {
			roundy2 += Main.tileSize;
		}
		for (int i = roundy1; i < roundy2; i += Main.tileSize) {
			tilesY.add(i / Main.tileSize);
		}
		System.out.println("Round Vertical: " + roundy1 + " " + roundy2);
		return tilesY;
	}

	public boolean collisionDetect(Rectangle obj1, Rectangle obj2) {
		return obj1.intersects(obj2);
	}

	// updates character model
	public void update() {
		this.refreshTile();
		this.fall();
		this.refreshTile();
		this.jump();
		this.refreshTile();
		this.moveLeft();
		this.refreshTile();
		this.moveRight();
		this.refreshTile();
		// this.fixPosition();
		this.refreshTile();
	}

	// basic mechanics are down
	// retrieves current direction
	// value -1 to 1
	public double getDirection() {
		return direction;
	}

	// sets direction to specific value
	// default 0
	public void setDirection(int num) {
		direction = num;
	}

	// character will fall if there is no block below it
	// character will be set on the ground if there is a ground tile below it
	public void fall() {
		this.translate(0, (int) (gravity * direction));
		System.out.println("Occupies: ");
		for (int i = 0; i < tilesX.size(); i++) {
			System.out.println(tilesY.get(i)+ " " + tilesX.get(i));
		}
		System.out.println();
		int[] blockCollides = checkTileCollisionBelow();
		if (blockCollides[0] != -1 && !isJumping()) {
			this.setLocation((int) this.getX(), blockCollides[0] * Main.tileSize - Main.imageHeight);
			direction = 0;
		} else if (blockCollides[0] == -1) {
			if (direction < 1) {
				direction += fallSpeed;
			}
		}
	}

	// check if character is jumping based on true or false
	public boolean isJumping() {
		return jump;
	}

	// set to jumping or not jumping based on true or false
	public void setJumping(boolean bool) {
		jump = bool;
	}

	// change the character's y to simulate a jump if:
	// - it is supposed to be jumping
	// - direction is set to jumping
	// - there is no block above it
	public void jump() {
		if (isJumping() && direction < 0) {
			this.translate(0, (int) (gravity * direction));
			int[] blockCollides = checkTileCollisionAbove();
			if (blockCollides[0] != -1) {
				this.setLocation((int) this.getX(), (blockCollides[0] + 1) * Main.tileSize);
				direction = 0;
			} else {
				direction += jumpSpeed;
			}
		} else {
			setJumping(false);
		}
	}

	// check if moving left
	public boolean isLeft() {
		return left;
	}

	// set moving left
	public void setLeft(boolean bool) {
		left = bool;
	}

	// move left if:
	// - it is supposed to be moving left
	// - it is not moving right
	// - there is nothing blocking it left
	public void moveLeft() {
		if (this.isLeft() && !this.isRight()) {
			this.translate(-moveX, 0);
			int[] blockCollides = checkTileCollisionLeft();
			if (blockCollides[1] != -1) {
				this.setLocation((blockCollides[1] + 1) * Main.tileSize, (int) this.getY());
			}
		}
	}

	// same as other two
	public boolean isRight() {
		return right;
	}

	public void setRight(boolean bool) {
		right = bool;
	}

	// move right if:
	// - it is supposed to be moving right
	// - it is not moving left
	// - there is nothing blocking it right
	public void moveRight() {
		if (this.isRight() && !this.isLeft()) {
			this.translate(moveX, 0);
			int[] blockCollides = checkTileCollisionRight();
			if (blockCollides[1] != -1) {
				this.setLocation(blockCollides[1] * Main.tileSize - Main.imageWidth, (int) this.getY());
			} else {
				// screen scrolls if the character is moving right:
				// - if it is not the last screen
				// - if it is gonna move past the middle of the screen
				// keeps the character in the middle of the screen until the last screen
				if (Main.bgX > -(Main.levelWidth - Main.winWidth)
						&& this.getX() >= Main.winWidth / 2 - Main.tileSize / 2) {
					Main.bgX -= Main.tileSize;
					this.setLocation(Main.winWidth / 2 - Main.tileSize / 2, (int) this.getY());
				}
			}
		}
	}

//	public void fixPosition() {
//		if (checkTileCollisionAbove() && isJumping()) {
//			this.setLocation((int) this.getX(), tileY * Main.tileSize);
//			direction = 0;
//		}
//		if (checkTileCollisionBelow() && !isJumping()) {
//			this.setLocation((int) this.getX(), tileY * Main.tileSize);
//			direction = 0;
//		}
//		if (checkTileCollisionRight() && !isLeft()) {
//			this.setLocation(tileX * Main.tileSize - Main.imageWidth, (int) this.getY());
//		}
//		if (checkTileCollisionLeft() && !isRight()) {
//			this.setLocation(tileX * Main.tileSize + Main.imageWidth, (int) this.getY());
//		}
//	}

	// checks for block above it
	public int[] checkBlockAbove() {
		// {-1, -1} means no block above
		int[] blockAbove = { -1, -1 };
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
					blockAbove[0] = y - 1;
					blockAbove[1] = x;
					break;
				}
			}
		}
		// return tile coords
		return blockAbove;
	}

	// tells me whether the character has crossed into block above it
	public int[] checkTileCollisionAbove() {
		int[] blockCollides = checkBlockAbove();
		if (blockCollides[0] != -1) {
			if (this.getY() >= (blockCollides[0] + 1) * Main.tileSize) {
				blockCollides[0] = -1;
			}
		}
		return blockCollides;
	}

	// checks for block below character
	public int[] checkBlockBelow() {
		// {-1, -1} means no block under
		int[] blockUnder = { -1, -1 };
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
					blockUnder[0] = y + 1;
					blockUnder[1] = x;
					break;
				}
			}
		}
		// return tile coords
		return blockUnder;
	}

	// checks whether the character has crossed into block below it
	public int[] checkTileCollisionBelow() {
		int[] blockCollides = checkBlockBelow();
		if (blockCollides[0] != -1) {
			if (this.getY() + Main.imageHeight <= blockCollides[0] * Main.tileSize) {
				blockCollides[0] = -1;
			}
		}
		return blockCollides;
	}

	// checks whether there is a block to the characters right
	public int[] checkBlockRight() {
		// {-1, -1} means no block right
		int[] blockRight = { -1, -1 };
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
					blockRight[0] = y;
					blockRight[1] = x + 1;
					break;
				}
			}
		}
		// return tile coords
		return blockRight;
	}

	// checks whether the character has crossed into block to its right
	public int[] checkTileCollisionRight() {
		int[] blockCollides = checkBlockRight();
		if (blockCollides[1] != -1) {
			if (this.getX() + Main.imageWidth <= blockCollides[1] * Main.tileSize) {
				blockCollides[1] = -1;
			}
		}
		return blockCollides;
	}

	// checks whether there is a block to the characters left
	public int[] checkBlockLeft() {
		// {-1, -1} means no block left
		int[] blockLeft = { -1, -1 };
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			// make sure we are checking within the grid
			if (x - 1 >= 0) {
				// check if the blockLeft is a tile
				// if it is set blockLeft to the tile coords
				if (Main.currentGrid[y][x - 1] == 1) {
					blockLeft[0] = y;
					blockLeft[1] = x - 1;
					break;
				}
			}
		}
		// return tile coords
		return blockLeft;
	}

	// checks whether the character has crossed into block to its left
	public int[] checkTileCollisionLeft() {
		int[] blockCollides = checkBlockLeft();
		if (blockCollides[1] != -1) {
			if (this.getX() >= (blockCollides[1] + 1) * Main.tileSize) {
				blockCollides[1] = -1;
			}
		}
		return blockCollides;
	}

}
