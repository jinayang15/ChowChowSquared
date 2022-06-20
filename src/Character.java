import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
// Character Stuff
// - variables
// - controls
//**Not explaining the math...
public class Character extends Rectangle {
	// Class & Instance Variables
	// Completely Unnecessary to have non-static variables
	// but originally had an idea to do multiple characters and too lazy to change it
	// Also not necessary to have private var + setters and getters but it looks better
	
	// Hitbox
	public int hitboxWidth;
	public int hitboxHeight;
	// Adjusting for image Whitespace
	public int imageAdjustLeft;
	public int imageAdjustRight;
	public int imageAdjustTop;
	public int imageAdjustBot;
	// Tiles Occupied on Current Grid
	private ArrayList<Integer> tilesX = new ArrayList<Integer>();
	private ArrayList<Integer> tilesY = new ArrayList<Integer>();
	// Moving Left or Right Booleans
	private boolean left = false;
	private boolean right = false;
	// Idling Booleans
	private boolean idleLeft = false;
	private boolean idleRight = false;
	// # of Pixels moved left or right
	private static int moveX = 6;
	// arbitrary number represents air block/no collision with solid block
	public static int noCollide = -100;
	// Jumping boolean
	private boolean jump = false;
	// Jump Cooldown
	private boolean jumpCD = false;
	// How high character will jump essentially
	// not technically gravity, I don't have a better name for it
	private static int gravity = 20;
	// how fast the character will jump or fall
	private static double jumpSpeed = 0.075;
	private static double fallSpeed = 0.075;
	// Vertical Direction
	// -1 max jump -> 1 max fall
	private double verticalDirection = 0;
	// Horizontal Direction
	// -1 = left -> 1 = right
	private int horizontalDirection = 1;
	
	// Class & Instance Methods
	// Character Default Properties
	public Character() {
		setBounds(20, 280, Main.imageWidth, Main.imageHeight);
		setHitbox(22, 20);
		setImageAdjust(10, 10, 10, 10);
	}
	// Set hitbox width and height
	public void setHitbox(int width, int height) {
		hitboxWidth = width;
		hitboxHeight = height;
	}
	// set image adjustments
	public void setImageAdjust(int left, int right, int top, int bot) {
		imageAdjustLeft = left;
		imageAdjustRight = right;
		imageAdjustTop = top;
		imageAdjustBot = bot;
	}
	
	// refresh the tiles that are being occupied
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
	
	// Check which columns are being occupied on current grid
	// if the hitbox of the character occupies over half of a column
	// it counts as being in that column
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
	// Check which rows are being occupied on current grid
	// if the hitbox of the character occupies over half of a row
	// it counts as being in that row
	public ArrayList<Integer> getTilesY(ArrayList<Integer> tilesY) {
		int y1 = (int) getY() + imageAdjustTop;
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

	// updates character model
	public void update() {
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
		checkDeath();
		// Idle Animation
		chanceIdle();
		idleRight();
		idleLeft();

	}
	// Check for collision with Enemy
	// Simple Rectangle collision detection
	public void checkDeath() {
		int charX = (int) (getX() + imageAdjustLeft);
		int charY = (int) (getY() + imageAdjustTop);
		int enmX, enmY;
		for (int i = 0; i < Enemy.onScreenEnemies.size(); i++) {
			Enemy temp = Enemy.onScreenEnemies.get(i);
			if (temp.getX() != noCollide) {
				enmX = (int) (temp.getX() + temp.imageAdjustLeft);
				enmY = (int) (temp.getY() + temp.imageAdjustTop);
				if ((charX >= enmX && charX <= enmX + temp.hitboxWidth)
						|| (charX + hitboxWidth >= enmX && charX + hitboxWidth <= enmX + temp.hitboxWidth)) {
					if ((charY >= enmY && charY <= enmY + temp.hitboxHeight)
							|| (charY + hitboxHeight >= enmY && charY + hitboxHeight <= enmY + temp.hitboxHeight)) {
						Main.gameState = 8;
						break;
					}
				}

			}
		}
	}

	
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

	// sets horizontalDirection to specific value
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
	
	// check for jump cooldown returns true or false
	public boolean getJumpCD() {
		return jumpCD;
	}
	
	// set jump cooldown as true or false
	public void setJumpCD(boolean bool) {
		jumpCD = bool;
	}
	
	// check if character is falling
	public boolean isFalling() {
		return verticalDirection > 0;
	}

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
	
	// check if idling right
	public boolean isIdleRight() {
		return idleRight;
	}
	// set idling right
	public void setIdleRight(boolean bool) {
		idleRight = bool;
	}
	// same as idleRight
	public boolean isIdleLeft() {
		return idleLeft;
	}

	public void setIdleLeft(boolean bool) {
		idleLeft = bool;
	}
	
	// Check if the dog is idle
	// Chance to start idle animation
	public void chanceIdle() {
		if (!isIdleRight() && !isIdleLeft()) {
			if (!isJumping() && !isFalling()) {
				if (!isMovingLeft() && !isMovingRight()) {
					double chance = Math.random() * 200;
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
	// Idle Right animation
	public void idleRight() {
		if (isIdleRight()) {
			Animations.updateAnimationIdle(this);
		}
	}
	// Idle Left Animation
	public void idleLeft() {
		if (isIdleLeft()) {
			Animations.updateAnimationIdle(this);
		}
	}
	// Character fall
	public void fall() {
		translate(0, (int) (gravity * verticalDirection));
		// check for tile collision below
		int[] blockCollides = checkTileCollisionBelow();
		// if character is on the ground and character is not jumping
		if (blockCollides[0] != noCollide && !isJumping()) {
			// reset jump cooldown
			setJumpCD(false);
			// prevent clipping
			setLocation((int) getX(), blockCollides[0] * Main.tileSize - Main.imageHeight + imageAdjustBot);
			// reset vertical direction
			setVerticalDirection(0);
			// reset animation
			Animations.resetFallAnimation();
			// change to default image
			if (horizontalDirection == 1 && !(isIdleRight() || isIdleLeft())) {
				Images.currentDogImage = Images.defaultRightImage;
			} else if (horizontalDirection == -1 && !(isIdleRight() || isIdleLeft())) {
				Images.currentDogImage = Images.defaultLeftImage;
			}
		}
		// if no block below
		else if (blockCollides[0] == noCollide) {
			// stop idle animation
			Animations.stopIdle(this);
			// fall animation
			Animations.updateAnimationFall(this);
			// add to verticalDirection
			if (verticalDirection < 1) {
				verticalDirection += fallSpeed;
			}
		}
	}
	// Character jump
	public void jump() {
		// if the character is supposed to be jumping
		if (isJumping() && verticalDirection < 0) {
			// stop idle animation
			Animations.stopIdle(this);
			translate(0, (int) (gravity * verticalDirection));
			// jump animation
			Animations.updateAnimationJump(this);
			// check for tile collision above
			int[] blockCollides = checkTileCollisionAbove();
			// if the character does collide, prevent clipping
			if (blockCollides[0] != noCollide) {
				setLocation((int) getX(), (blockCollides[0] + 1) * Main.tileSize - imageAdjustTop);
				setVerticalDirection(0);
			}
			// add to vertical direction
			else {
				verticalDirection += jumpSpeed;
			}
		}
		// stop jumping
		else {
			setJumping(false);
			Animations.resetJumpAnimation();
		}
	}

	// Character move left
	public void moveLeft() {
		// if character is supposed to be moving left and is not moving right
		if (isMovingLeft() && !isMovingRight()) {
			// stop idling
			Animations.stopIdle(this);
			translate(-moveX, 0);
			// Run animation
			Animations.updateAnimationRun(this);
			// check for tile collison
			int[] blockCollides = checkTileCollisionLeft();
			if (blockCollides[1] != noCollide) {
				// prevent clipping
				setLocation((blockCollides[1] + 1) * Main.tileSize - imageAdjustLeft, (int) getY());
				Animations.resetRunAnimation();
				Images.currentDogImage = Images.defaultLeftImage;
			}
		}
	}

	// Character move right
	// same as others
	public void moveRight() {
		if (isMovingRight() && !isMovingLeft()) {
			Animations.stopIdle(this);
			translate(moveX, 0);
			Images.currentDogImage = Images.rightRunDog1[Animations.runRightIndex];
			Animations.updateAnimationRun(this);
			int[] blockCollides = checkTileCollisionRight();
			if (blockCollides[1] != noCollide) {
				setLocation(blockCollides[1] * Main.tileSize - Main.imageWidth + imageAdjustRight, (int) getY());
				Animations.resetRunAnimation();
				Images.currentDogImage = Images.defaultRightImage;
			} else {
				// screen scrolls if the character is moving right:
				// - if it is not the last screen
				// - if it is going to move past the middle of the screen
				// keeps the character in the middle of the screen until the last screen
				if (Main.bgX > -(Main.levelWidth - Main.winWidth) && getX() >= Main.winWidth / 2 - Main.tileSize / 2) {
					Main.bgX -= Main.tileSize;
					setLocation(Main.winWidth / 2 - Main.tileSize / 2, (int) getY());
					Enemy.shiftEnemies();
				}
				// if the character passes a certain point on the last screen, you win!
				else if (Main.bgX <= -(Main.levelWidth - Main.winWidth) && getX() >= 375) {
					Main.gameState = 5;
				}
			}
		}
	}
	// fixes vertical positioning after left and right corrections
	public void fixPosition() {
		int[] blockAboveCollide = checkTileCollisionAbove();
		int[] blockBelowCollide = checkTileCollisionBelow();
		if (blockAboveCollide[0] != noCollide && isJumping()) {
			setLocation((int) getX(), (blockAboveCollide[0] + 1) * Main.tileSize - imageAdjustTop);
			setVerticalDirection(0);
		} else if (blockBelowCollide[0] != noCollide && !isJumping()) {
			setLocation((int) getX(), blockBelowCollide[0] * Main.tileSize - Main.imageHeight + imageAdjustBot);
			setVerticalDirection(0);
		}
	}

	// checks for block above it
	public int[] checkBlockAbove() {
		// {noCollide, noCollide} means no block above
		int[] blockAbove = { noCollide, noCollide };
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			// make sure we are checking within the grid
			if (y < Main.tileHeight) {
				// check for solid tiles
				// if the current tile the character is in is a block return this block
				if (Main.currentGrid[y][x] > '0' && Main.currentGrid[y][x] <= '8') {
					blockAbove[0] = y;
					blockAbove[1] = x;
					return blockAbove;
				}
				if (y - 1 >= 0) {
					// check if the blockAbove is a tile
					// if it is set blockAbove to the tile coords
					if (Main.currentGrid[y - 1][x] > '0' && Main.currentGrid[y - 1][x] <= '8') {
						if (blockAbove[0] == noCollide || y - 1 > blockAbove[0]) {
							blockAbove[0] = y - 1;
							blockAbove[1] = x;
						}
					}
				} else {
					// return negative to represent ceiling
					blockAbove[0] = y - 1;
					blockAbove[1] = x;
				}
			}
		}
		// return tile coords
		return blockAbove;
	}

	// Checks for collision with tile above
	// if it does collide, returns the block that the character collides with
	public int[] checkTileCollisionAbove() {
		int[] blockCollides = checkBlockAbove();
		if (blockCollides[0] != noCollide) {
			if (getY() - imageAdjustTop > (blockCollides[0] + 1) * Main.tileSize) {
				blockCollides[0] = noCollide;
			}
		}
		return blockCollides;
	}

	// checks for block below character
	public int[] checkBlockBelow() {
		// {noCollide, noCollide} means no block under
		int[] blockUnder = { noCollide, noCollide };
		int x, y;
		// check all the blocks that the character is in
		for (int i = 0; i < tilesX.size(); i++) {
			x = tilesX.get(i);
			y = tilesY.get(i);
			if (y < Main.tileHeight) {
				if (Main.currentGrid[y][x] > '0' && Main.currentGrid[y][x] <= '8') {
					blockUnder[0] = y;
					blockUnder[1] = x;
					return blockUnder;
				}
				// make sure we are checking within the grid
				if (y + 1 < Main.tileHeight) {
					// check if the blockUnder is a tile
					// if it is set blockUnder to the tile coords
					if (Main.currentGrid[y + 1][x] > '0' && Main.currentGrid[y + 1][x] <= '8') {
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
	// returns the block collided with
	public int[] checkTileCollisionBelow() {
		int[] blockCollides = checkBlockBelow();
		if (blockCollides[0] != noCollide) {
			if (blockCollides[0] >= Main.tileHeight) {
				Main.gameState = 8;
			} else if (getY() + Main.imageHeight - imageAdjustBot < blockCollides[0] * Main.tileSize) {
				blockCollides[0] = noCollide;
			}
		}
		return blockCollides;
	}

	// checks whether there is a block to the characters right
	public int[] checkBlockRight() {
		// {noCollide, noCollide} means no block right
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
				if (Main.currentGrid[y][x + 1] > '0' && Main.currentGrid[y][x + 1] <= '8') {
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
			if (getX() + imageAdjustRight + hitboxWidth < blockCollides[1] * Main.tileSize) {
				blockCollides[1] = noCollide;
			}
		}
		return blockCollides;
	}

	// checks whether there is a block to the characters left
	public int[] checkBlockLeft() {
		// {noCollide, noCollide} means no block left
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
				if (Main.currentGrid[y][x - 1] > '0' && Main.currentGrid[y][x - 1] <= '8') {
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
			if (getX() - imageAdjustLeft > (blockCollides[1] + 1) * Main.tileSize) {
				blockCollides[1] = noCollide;
			}
		}
		return blockCollides;
	}

}
