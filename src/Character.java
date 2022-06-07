import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

public class Character extends Rectangle {
	// Class & Object Variables
	public int hitboxWidth;
	public int hitboxHeight;
	public int imageAdjustX;
	public int imageAdjustY;
	private ArrayList<Integer> tilesX = new ArrayList<Integer>();
	private ArrayList<Integer> tilesY = new ArrayList<Integer>();
	private boolean left = false;
	private boolean right = false;
	// pixels moved left and right
	private static int moveX = 10;
	public static int noCollide = -100;
	// jump = true, character is jumping
	// jump = false, character is not jumping
	private boolean jump = false;
	// how high the character will jump
	private static int gravity = 30;
	// direction determines whether the character is jumping up or falling down
	// -1 max jump -> 1 max fall
	private double direction = 0;
	// how fast the character will jump or fall
	private static double jumpSpeed = 0.2;
	private static double fallSpeed = 0.05;

	// Class Methods
	// will add comments for the changes later\
	public void setHitbox(int width, int height) {
		this.hitboxWidth = width;
		this.hitboxHeight = height;
		this.imageAdjustX = (Main.imageWidth-hitboxWidth)/2;
		this.imageAdjustY = (Main.imageHeight-hitboxHeight)/2;;
	}
	
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
		System.out.println("Occupies: ");
		for (int i = 0; i < tilesX.size(); i++) {
			System.out.println(tilesY.get(i) + " " + tilesX.get(i));
		}
		System.out.println();
	}

	public ArrayList<Integer> getTilesX(ArrayList<Integer> tilesX) {
		int x1 = (int) this.getX() + imageAdjustX;
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
		System.out.println("X: " + x1 + " " + x2);
		System.out.println("Round X: " + roundx1 + " " + roundx2);
		return tilesX;

	}

	public ArrayList<Integer> getTilesY(ArrayList<Integer> tilesY) {
		int y1 = (int) this.getY() + imageAdjustY;
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
		System.out.println("Y: " + y1 + " " + y2);
		System.out.println("Round Y: " + roundy1 + " " + roundy2);
		System.out.println();
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
		this.fixPosition();
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
		int[] blockCollides = checkTileCollisionBelow();
		if (blockCollides[0] != noCollide && !isJumping()) {
			this.setLocation((int) this.getX(),
					blockCollides[0] * Main.tileSize - Main.imageHeight + imageAdjustY);
			direction = 0;
		} else if (blockCollides[0] == noCollide) {
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
			if (blockCollides[0] != noCollide) {
				this.setLocation((int) this.getX(),
						(blockCollides[0] + 1) * Main.tileSize - imageAdjustY);
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
			if (blockCollides[1] != noCollide) {
				this.setLocation((blockCollides[1] + 1) * Main.tileSize - imageAdjustX,
						(int) this.getY());
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
			if (blockCollides[1] != noCollide) {
				this.setLocation(blockCollides[1] * Main.tileSize - Main.imageWidth + imageAdjustX,
						(int) this.getY());
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

	public void fixPosition() {
		int[] blockAboveCollide = checkTileCollisionAbove();
		int[] blockBelowCollide = checkTileCollisionBelow();
		if (blockAboveCollide[0] != noCollide && isJumping()) {
			this.setLocation((int) this.getX(),
					(blockAboveCollide[0] + 1) * Main.tileSize - imageAdjustY);
			direction = 0;
		} else if (blockBelowCollide[0] != noCollide && !isJumping()) {
			this.setLocation((int) this.getX(),
					blockBelowCollide[0] * Main.tileSize - Main.imageHeight + imageAdjustY);
			direction = 0;
		}
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
