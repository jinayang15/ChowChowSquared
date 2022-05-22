import java.awt.Rectangle;

public class Character extends Rectangle {
	// jumping mechanics are currently incomplete
	
	// Class Variables
	// jump = true, character is jumping
	// jump = false, character is not jumping
	private static boolean jump = false;
	// how many pixels the character will move down
	// in the air
	private static int gravity = 1;
	// max value gravity can be
	private static int terminalVelocity = 40;
	
	// Class Methods
	// check if character is jumping
	public boolean isJumping() { return jump; }
	
	// change the character's y to simulate a jump
	public void jump() {
		if (isJumping()) {
			this.y = (int) this.getY() + gravity;
			gravity += gravity;
		}
	}
}
