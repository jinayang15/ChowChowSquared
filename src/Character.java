import java.awt.Rectangle;

public class Character extends Rectangle {
	// jumping mechanics are currently incomplete
	private int tileX;
	private int tileY;
	// Class Variables
	private static boolean left = false;
	private static boolean right = false;
	// pixels moved left and right
	private static int moveX = 10;

	// jump = true, character is jumping
	// jump = false, character is not jumping
	private static boolean jump = false;
	// how high the character will jump
	private static int gravity = 20;
	// direction determines whether the character is jumping up or falling down
	// -1 max jump -> 1 max fall
	private static double direction = 0;
	// how fast the character will jump or fall
	private static double jumpSpeed = 0.1;
	private static double fallSpeed = 0.05;

	// Class Methods
	// will add comments for the changes later
	public void refreshTile() {
		tileX = (int) (this.getX() + Main.tileSize / 2) / Main.tileSize;
		tileY = (int) (this.getY() + Main.tileSize / 2) / Main.tileSize;
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
	
	// magnet effect on character atm will be fixed*
	// character will fall if there is no block below it
	// character will be set on the ground if there is a ground tile below it
	public void fall() {
		if (checkBelow() && !isJumping()) {
			this.setLocation((int) this.getX(), tileY * Main.tileSize);
			direction = 0;
		}
		else if (!checkBelow()) {
			this.translate(0, (int) (gravity * direction));
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
			if (checkAbove()) {
				this.setLocation((int) this.getX(), tileY*Main.tileSize);
				direction = 0;
			} else {
				this.translate(0, (int) (gravity * direction));
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
			if (checkLeft()) {
				this.setLocation(tileX * Main.tileSize, (int) this.getY());
			} else {
				this.translate(-moveX, 0);
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
			if (checkRight()) {
				this.setLocation(tileX * Main.tileSize, (int) this.getY());
			} else {
				this.translate(moveX, 0);
				// screen scrolls if the character is moving right:
				// - if it is not the last screen
				// - if it is gonna move past the middle of the screen
				// keeps the character in the middle of the screen until the last screen
				if (Main.bgX > -(Main.levelWidth - Main.winWidth)
						&& this.getX() >= Main.winWidth / 2 - Main.tileSize / 2) {
					Main.bgX -= moveX;
					this.setLocation(Main.winWidth / 2 - Main.tileSize / 2, (int) this.getY());
				}
			}
		}
	}

	public void fixPosition() {
		if (checkAbove() && isJumping()) {
			this.setLocation((int) this.getX(), tileY*Main.tileSize);
			direction = 0;
		}
		if (checkBelow() && !isJumping()) {
			this.setLocation((int) this.getX(), tileY * Main.tileSize);
			direction = 0;
		}
		if (checkRight() && !isLeft()) {
			this.setLocation(tileX * Main.tileSize, (int) this.getY());
		}
		if (checkLeft() && !isRight()) {
			this.setLocation(tileX * Main.tileSize, (int) this.getY());
		}
	}
	// returns if there is a block above or its at the ceiling
	// true if there is a block
	// true if at ceiling
	// false if no block
	public boolean checkAbove() {
		if (tileY - 1 >= 0) {
			return (Main.levelGrid[tileY - 1][tileX] == 1) ? true : false;
		}
		return true;
	}
	// returns if there is a block below or if it is at the bottom
	// true if there is block
	// true if at the bottom of frame
	// false if there is no block
	public boolean checkBelow() {
		if (tileY + 1 < Main.tileHeight) {
			return (Main.levelGrid[tileY + 1][tileX] == 1) ? true : false;
		}
		return true;
	}
	
	// same idea
	public boolean checkRight() {
		if (tileX + 1 < Main.tileWidth) {
			return (Main.levelGrid[tileY][tileX + 1] == 1) ? true : false;
		}
		return true;
	}

	public boolean checkLeft() {
		if (tileX - 1 >= 0) {
			return (Main.levelGrid[tileY][tileX - 1] == 1) ? true : false;
		}
		return true;
	}


}
