import java.awt.Rectangle;

public class Character extends Rectangle {
	// jumping mechanics are currently incomplete

	// Class Variables
	private static boolean left = false;
	private static boolean right = false;
	// jump = true, character is jumping
	// jump = false, character is not jumping
	private static boolean jump = false;
	// how many pixels the character will move down
	// in the air
	private static int gravity = 1;
	// max value gravity can be
	private static int terminalVelocity = 40;

	// Class Methods
	// updates character model
	public void update() {
		this.jump();
		this.moveLeft();
		this.moveRight();
	}
	
	// movement is currently incomplete, have not considered tiles yet
	// basic mechanics are down, maybe improve jump animation
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
			this.translate(0, gravity);
			gravity += 2;
			if (this.getY() >= 360) {
				gravity = 1;
				setJumping(false);
				this.setLocation((int) this.getX(), 360);
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
			this.translate(-20, 0);
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
			this.translate(20, 0);
			if (this.getX() >= 480) {
				this.setLocation(480, (int) this.getY());
			}
		}
	}
}
