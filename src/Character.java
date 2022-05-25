import java.awt.Rectangle;

public class Character extends Rectangle {
	// jumping mechanics are currently incomplete

	// Class Variables
	private static boolean left = false;
	private static boolean right = false;
	// pixels moved left and right
	private static int moveX = 10;
	
	// jump = true, character is jumping
	// jump = false, character is not jumping
	private static boolean jump = false;
	// how high the character will jump
	private static int gravity = 40;
	// direction determines whether the character is jumping up or falling down
	// -1 max jump -> 1 max fall
	private static double direction = -1;
	// how fast the character will jump or fall
	private static double jumpSpeed = 0.2;
	private static double fallSpeed = 0.05;
	

	// Class Methods
	// updates character model
	public void update() {
		this.jump();
		this.moveLeft();
		this.moveRight();
	}
	
	// movement is currently incomplete, have not considered tiles yet
	// basic mechanics are down
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
		if (isJumping()) {		
			this.translate(0, (int) (gravity*direction));
			if (direction < 1) {
				if(direction < 0) {
					direction += jumpSpeed;
				}
				else {
					direction += fallSpeed;
				}
			}
			if (this.getY() >= Main.winHeight - Main.tileSize) {
				direction = -1;
				setJumping(false);
				this.setLocation((int) this.getX(), Main.winHeight - Main.tileSize);
			}
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
		if (this.isLeft()) {
			this.translate(-moveX, 0);
			if (this.getX() <= 0) {
				this.setLocation(0, (int) this.getY());
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
		if (this.isRight()) {
			this.translate(moveX, 0);
			if (this.getX() >= Main.winWidth - Main.tileSize) {
				this.setLocation(Main.winWidth - Main.tileSize, (int) this.getY());
			}
		}
	}
}
