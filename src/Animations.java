import java.awt.Graphics;

// Animates stuff to make it look cool
public class Animations {
	// Idle Animation Variables
	// - index keeps track of image
	// - tick keeps track of current tick
	// - speed determines when tick refreshes
	public static int idleLeftIndex = 1;
	public static int idleRightIndex = 0;
	public static int idleTick, idleSpeed = 30;
	// Run Animation Variables
	public static int runLeftIndex = Images.leftRunDog1.length - 1;
	public static int runRightIndex = 0;
	public static int runTick, runSpeed = 5;
	// Jump Animation Variables
	public static int jumpRightIndex = 0;
	public static int jumpLeftIndex = Images.leftJumpDog1.length - 1;
	public static int jumpTick, jumpSpeed = 3;
	// Fall Animation Variables
	public static int fallRightIndex = 0;
	public static int fallLeftIndex = Images.leftFallDog1.length - 1;
	public static int fallTick, fallSpeed = 10;
	// Winner Fade-in Variables
	public static int fadeIndex = 0;
	public static int fadeTick = 8, fadeSpeed = 8;

	// the update methods loops through the image arrays at a set speed
	// idleTick measures current tick and only refreshes when it reaches the set
	// idleSpeed. When the speed is reached, the next image is queued up
	public static void updateAnimationIdle(Character dog) {
		if (!dog.isJumping() && !dog.isFalling()) {
			if (!dog.isMovingLeft() && !dog.isMovingRight()) {
				idleTick++;
				if (idleTick >= idleSpeed) {
					idleTick = 0;
					if (dog.getHorizontalDirection() == 1) {
						Images.currentDogImage = Images.rightIdleDog1[Animations.idleRightIndex];
						idleRightIndex++;
						if (idleRightIndex >= Images.rightIdleDog1.length) {
							idleRightIndex = 0;
							stopIdle(Main.dog);
						}
					} else if (dog.getHorizontalDirection() == -1) {
						Images.currentDogImage = Images.leftIdleDog1[Animations.idleLeftIndex];
						idleLeftIndex--;
						if (idleLeftIndex < 0) {
							idleLeftIndex = 1;
							stopIdle(Main.dog);
						}
					}
				}
			}
		}
	}
	// resets idle Variables
	public static void stopIdle(Character dog) {
		dog.setIdleRight(false);
		dog.setIdleLeft(false);
		Animations.idleTick = 0;
	}
	
	// same thing
	public static void updateAnimationRun(Character dog) {
		if (!dog.isJumping() && !dog.isFalling()) {
			runTick++;
			if (runTick >= runSpeed) {
				runTick = 0;
				if (dog.getHorizontalDirection() == 1) {
					Images.currentDogImage = Images.rightRunDog1[Animations.runRightIndex];
					runRightIndex++;
					if (runRightIndex >= Images.rightRunDog1.length) {
						runRightIndex = 0;
					}
				} else if (dog.getHorizontalDirection() == -1) {
					Images.currentDogImage = Images.leftRunDog1[Animations.runLeftIndex];
					runLeftIndex--;
					if (runLeftIndex <= 0) {
						runLeftIndex = Images.leftRunDog1.length - 1;
					}
				}
			}
		}
	}
	// resets run variables
	public static void resetRunAnimation() {
		runLeftIndex = Images.leftRunDog1.length - 1;
		runRightIndex = 0;
		runTick = 0;
	}
	// same thing
	public static void updateAnimationJump(Character dog) {
		jumpTick++;
		if (jumpTick >= jumpSpeed) {
			jumpTick = 0;
			if (dog.getHorizontalDirection() == 1) {
				Images.currentDogImage = Images.rightJumpDog1[Animations.jumpRightIndex];
				jumpRightIndex++;
				if (jumpRightIndex >= Images.rightJumpDog1.length) {
					jumpRightIndex = 0;
				}
			} else if (dog.getHorizontalDirection() == -1) {
				Images.currentDogImage = Images.leftJumpDog1[Animations.jumpLeftIndex];
				jumpLeftIndex--;
				if (jumpLeftIndex <= 0) {
					jumpLeftIndex = Images.leftJumpDog1.length - 1;
				}
			}
		}
	}
	// same thing
	public static void resetJumpAnimation() {
		jumpRightIndex = 0;
		jumpLeftIndex = Images.leftJumpDog1.length - 1;
		jumpTick = 0;
		jumpSpeed = 3;
	}
	// same thing
	public static void updateAnimationFall(Character dog) {
		fallTick++;
		if (fallTick >= fallSpeed) {
			fallTick = 0;
			if (dog.getHorizontalDirection() == 1) {
				Images.currentDogImage = Images.rightFallDog1[Animations.fallRightIndex];
				fallRightIndex++;
				if (fallRightIndex >= Images.rightFallDog1.length) {
					fallRightIndex = 0;
				}
			} else if (dog.getHorizontalDirection() == -1) {
				Images.currentDogImage = Images.leftFallDog1[Animations.fallLeftIndex];
				fallLeftIndex--;
				if (fallLeftIndex <= 0) {
					fallLeftIndex = Images.leftFallDog1.length - 1;
				}
			}
		}
	}
	// same thing
	public static void resetFallAnimation() {
		fallRightIndex = 0;
		fallLeftIndex = Images.leftFallDog1.length - 1;
		fallTick = 0;
		fallSpeed = 10;
	}
	// same thing
	public static void fade(Graphics g) {
		fadeTick++;
		if (fadeTick >= fadeSpeed) {
			g.drawImage(Images.win[0], 0, 0, null);
			fadeTick = 0;
			fadeIndex++;
		}
		if (fadeIndex >= 15) {
			Main.gameState = 4;
		}	
	}
}
