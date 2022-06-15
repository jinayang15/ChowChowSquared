
public class Animations {
	public static int idleLeftIndex = 1;
	public static int idleRightIndex = 0;
	public static int idleTick, idleSpeed = 30;
	public static int runLeftIndex = Images.leftRunDog1.length - 1;
	public static int runRightIndex = 0;
	public static int runTick, runSpeed = 5;
	public static int jumpRightIndex = 0;
	public static int jumpLeftIndex = Images.leftJumpDog1.length - 1;
	public static int jumpTick, jumpSpeed = 3;
	public static int fallRightIndex = 0;
	public static int fallLeftIndex = Images.leftFallDog1.length - 1;
	public static int fallTick, fallSpeed = 10;

	// the update methods loops through the image arrays at a set speed
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
	public static void stopIdle(Character dog) {
		dog.setIdleRight(false);
		dog.setIdleLeft(false);
		Animations.idleTick = 0;
	}

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
	public static void resetRunAnimation() {
		runLeftIndex = Images.leftRunDog1.length - 1;
		runRightIndex = 0;
		runTick = 0;
		runSpeed = 5;
	}

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
	public static void resetJumpAnimation() {
		jumpRightIndex = 0;
		jumpLeftIndex = Images.leftJumpDog1.length - 1;
		jumpTick = 0;
		jumpSpeed = 3;
	}

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
	public static void resetFallAnimation() {
		fallRightIndex = 0;
		fallLeftIndex = Images.leftFallDog1.length - 1;
		fallTick = 0;
		fallSpeed = 10;
	}
}
