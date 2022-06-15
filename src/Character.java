import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

public class Character extends Rectangle {
	// Class & Object Variables
	public int hitboxWidth;
	public int hitboxHeight;
	public int imageAdjustX = 10;
	public int imageAdjustY = 10;
	private ArrayList<Integer> tilesX = new ArrayList<Integer>();
	private ArrayList<Integer> tilesY = new ArrayList<Integer>();
	private boolean left = false;
	private boolean right = false;
	private boolean idleLeft = false;
	private boolean idleRight = false;
	// pixels moved left and right
	private static int moveX = 5;
	public static int noCollide = -100;
	// jump = true, character is jumping
	// jump = false, character is not jumping
	private boolean jump = false;
	private boolean jumpCD = false;
	// how high the character will jump
	private static int gravity = 30;
	// verticalDirection determines whether the character is jumping up or falling
	// down (only vertical)
	// -1 max jump -> 1 max fall
	private double verticalDirection = 0;
	// -1 = left -> 1 = right
	private int horizontalDirection = 1;
	// how fast the character will jump or fall
	private static double jumpSpeed = 0.2;
	private static double fallSpeed = 0.03;

	// Class Methods
	// will add comments for the changes later
	public Character() {
		this.setBounds(20, 280, Main.imageWidth, Main.imageHeight);
		this.setHitbox(22, 20);
	}

	public void setHitbox(int width, int height) {
		this.hitboxWidth = width;
		this.hitboxHeight = height;
		this.imageAdjustX = (Main.imageWidth - hitboxWidth) / 2;
		this.imageAdjustY = (Main.imageWidth - hitboxHeight) / 2;
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

		System.out.println("Occupies: ");
		for (int i = 0; i < tilesX.size(); i++) {
			System.out.println(tilesY.get(i) + " " + tilesX.get(i));
		}
		System.out.println();
	}

	public ArrayList<Integer> getTilesX(ArrayList<Integer> tilesX) {
		int x1 = (int) this.getX() + imageAdjustX;
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
		int y1 = (int) this.getY() + imageAdjustY;
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

	public boolean collisionDetect(Rectangle obj1, Rectangle obj2) {
		return obj1.intersects(obj2);
	}

	// updates character model
	public void update() {
		// char position
		refreshTile();
		fall();
		refreshTile();
		jump();
		refreshTile();
		moveLeft();
		refreshTile();
		moveRight();
		refreshTile();
		fixPosition();
		// animations
		chanceIdle();
		idleRight();
		idleLeft();
		// checkDeath();
	}

	public void checkDeath() {
		if (this.getY() + Main.imageHeight >= 400) {
			Main.gameState = 8;
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

	// check if character is jumping based on true or false
	public boolean isJumping() {
		return jump;
	}

	// set to jumping or not jumping based on true or false
	public void setJumping(boolean bool) {
		jump = bool;
	}
	public boolean getJumpCD() {
		return jumpCD;
	}
	public void setJumpCD(boolean bool) {
		jumpCD = bool;
	}

	public boolean isFalling() {
		return verticalDirection > 0;
	}

	// character will fall if there is no block below it
	// character will be set on the ground if there is a ground tile below it
	// check if moving left
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

	public boolean isIdleRight() {
		return idleRight;
	}

	public void setIdleRight(boolean bool) {
		idleRight = bool;
	}

	public boolean isIdleLeft() {
		return idleLeft;
	}

	public void setIdleLeft(boolean bool) {
		idleLeft = bool;
	}


	public void chanceIdle() {
		if (!isIdleRight() && !isIdleLeft()) {
			if (!isJumping() && !isFalling()) {
				if (!isMovingLeft() && !isMovingRight()) {
					double chance = Math.random() * 100;
					if (chance <= 1) {
						if (getHorizontalDirection() == 1) {
							setIdleRight(true);
						} else if (getHorizontalDirection() == -1) {
							setIdleLeft(true);
						}
					}
				}
			}
		}
	}

	public void idleRight() {
		if (isIdleRight()) {
			Animations.updateAnimationIdle(this);
		}
	}

	public void idleLeft() {
		if (isIdleLeft()) {
			Animations.updateAnimationIdle(this);
		}
	}

	public void fall() {
		this.translate(0, (int) (gravity * verticalDirection));
		int[] blockCollides = checkTileCollisionBelow();
		if (blockCollides[0] != noCollide && !isJumping()) {
			this.setLocation((int) this.getX(), blockCollides[0] * Main.tileSize - Main.imageHeight + imageAdjustY);
			setVerticalDirection(0);
			Animations.resetFallAnimation();
			this.setJumpCD(false);
			if (horizontalDirection == 1 && !(isIdleRight() || isIdleLeft())) {
				Images.currentDogImage = Images.defaultRightImage;
			} else if (horizontalDirection == -1 && !(isIdleRight() || isIdleLeft())) {
				Images.currentDogImage = Images.defaultLeftImage;
			}
		} else if (blockCollides[0] == noCollide) {
			Animations.stopIdle(this);
			Animations.updateAnimationFall(this);
			if (verticalDirection < 1) {
				verticalDirection += fallSpeed;
			}
		}
	}

	// change the character's y to simulate a jump if:
	// - it is supposed to be jumping
	// - verticalDirection is set to jumping
	// - there is no block above it
	public void jump() {
		if (isJumping() && verticalDirection < 0) {
			Animations.stopIdle(this);
			this.translate(0, (int) (gravity * verticalDirection));
			Animations.updateAnimationJump(this);
			int[] blockCollides = checkTileCollisionAbove();
			if (blockCollides[0] != noCollide) {
				this.setLocation((int) this.getX(), (blockCollides[0] + 1) * Main.tileSize - imageAdjustY);
				setVerticalDirection(0);
			} else {
				verticalDirection += jumpSpeed;
			}
		} else {
			setJumping(false);
			Animations.resetJumpAnimation();
		}
	}

	// move left if:
	// - it is supposed to be moving left
	// - it is not moving right
	// - there is nothing blocking it left
	public void moveLeft() {
		if (this.isMovingLeft() && !this.isMovingRight()) {
			Animations.stopIdle(this);
			this.translate(-moveX, 0);
			Images.currentDogImage = Images.leftRunDog1[Animations.runLeftIndex];
			Animations.updateAnimationRun(this);
			int[] blockCollides = checkTileCollisionLeft();
			if (blockCollides[1] != noCollide) {
				this.setLocation((blockCollides[1] + 1) * Main.tileSize - imageAdjustX, (int) this.getY());
				Animations.resetRunAnimation();
				Images.currentDogImage = Images.defaultLeftImage;
			}
		}
	}

	// move right if:
	// - it is supposed to be moving right
	// - it is not moving left
	// - there is nothing blocking it right
	public void moveRight() {
		if (this.isMovingRight() && !this.isMovingLeft()) {
			Animations.stopIdle(this);
			this.translate(moveX, 0);
			Images.currentDogImage = Images.rightRunDog1[Animations.runRightIndex];
			Animations.updateAnimationRun(this);
			int[] blockCollides = checkTileCollisionRight();
			if (blockCollides[1] != noCollide) {
				this.setLocation(blockCollides[1] * Main.tileSize - Main.imageWidth + imageAdjustX, (int) this.getY());
				Animations.resetRunAnimation();
				Images.currentDogImage = Images.defaultRightImage;
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
		int[] blockLeftCollide = checkTileCollisionLeft();
		int[] blockRightCollide = checkTileCollisionRight();
		if (blockAboveCollide[0] != noCollide && isJumping()) {
			this.setLocation((int) this.getX(), (blockAboveCollide[0] + 1) * Main.tileSize - imageAdjustY);
			setVerticalDirection(0);
		} else if (blockBelowCollide[0] != noCollide && !isJumping()) {
			this.setLocation((int) this.getX(), blockBelowCollide[0] * Main.tileSize - Main.imageHeight + imageAdjustY);
			setVerticalDirection(0);
		}
		if (blockLeftCollide[1] != noCollide && !isMovingRight()) {
			this.setLocation((blockLeftCollide[1] + 1) * Main.tileSize - imageAdjustX, (int) this.getY());
		} else if (blockRightCollide[1] != noCollide && !isMovingLeft()) {
			this.setLocation(blockRightCollide[1] * Main.tileSize - Main.imageWidth + imageAdjustX, (int) this.getY());
		}
	}

	// checks for block above it
	public int[] checkBlockAbove() {
		// {noCollide, noCollide} means no block above
		int[] blockAbove = {noCollide, noCollide};
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			// make sure we are checking within the grid
			if (Main.currentGrid[y][x] > 0) {
				blockAbove[0] = y;
				blockAbove[1] = x;
				return blockAbove;
			}
			if (y - 1 >= 0) {
				// check if the blockAbove is a tile
				// if it is set blockAbove to the tile coords
				if (Main.currentGrid[y - 1][x] > 0) {
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
			if (this.getY() - imageAdjustY > (blockCollides[0] + 1) * Main.tileSize) {
				blockCollides[0] = noCollide;
			}
		}
		return blockCollides;
	}

	// checks for block below character
	public int[] checkBlockBelow() {
		// {-1, -1} means no block under
		int[] blockUnder = {noCollide, noCollide };
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			if (Main.currentGrid[y][x] > 0) {
				blockUnder[0] = y;
				blockUnder[1] = x;
				return blockUnder;
			}
			// make sure we are checking within the grid
			if (y + 1 < Main.tileHeight) {
				// check if the blockUnder is a tile
				// if it is set blockUnder to the tile coords
				if (Main.currentGrid[y + 1][x] > 0) {
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
			if (blockCollides[0] >= Main.tileHeight) {
				Main.gameState = 8;
			}
			else if (this.getY() + Main.imageHeight - imageAdjustY < blockCollides[0] * Main.tileSize) {
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
				if (Main.currentGrid[y][x + 1] > 0) {
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
			if (this.getX() + imageAdjustX + hitboxWidth < blockCollides[1] * Main.tileSize) {
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
				if (Main.currentGrid[y][x - 1] > 0) {
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
			if (this.getX() - imageAdjustX > (blockCollides[1] + 1) * Main.tileSize) {
				blockCollides[1] = noCollide;
			}
		}
		return blockCollides;
	}

}
