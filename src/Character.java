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
	private static double jumpSpeed = 0.2;
	private static double fallSpeed = 0.1;

	// Class Methods
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
	}

	// movement is currently incomplete, have not considered tiles yet
	// basic mechanics are down
	public double getDirection() {
		return direction;
	}

	public void setDirection(int num) {
		direction = num;
	}

	public void fall() {
		if (!checkBelow()) {
			this.translate(0, (int) (gravity * direction));
			if (direction < 1) {
				direction += fallSpeed;
			}
		}
		else if (checkBelow() && !isJumping()) {
			this.setLocation((int) this.getX(), tileY * Main.tileSize);
			direction = 0;
		}
	}

	// check if character is jumping
	public boolean isJumping() {
		return jump;
	}

	// set to jumping or not jumping
	public void setJumping(boolean bool) {
		jump = bool;
	}

	// change the character's y to simulate a jump
	public void jump() {
		if (isJumping() && direction < 0) {
			this.translate(0, (int) (gravity * direction));
			direction += jumpSpeed;
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

	public void moveRight() {
		if (this.isRight() && !this.isLeft()) {
			if (checkRight()) {
				this.setLocation(tileX * Main.tileSize, (int) this.getY());
			} else {
				this.translate(moveX, 0);
				// background scrolls right only
				if (Main.bgX > -(Main.levelWidth - Main.winWidth) && this.getX() >= Main.winWidth - Main.tileSize * 7) {
					Main.bgX -= moveX;
				}
			}
		}
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
