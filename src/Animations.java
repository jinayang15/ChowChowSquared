
public class Animations {
	public static int idleLeftIndex = 0;
	public static int idleRightIndex = 1;
	public static int idleTick, idleSpeed = 30;
	public static int runTick, runIndex, runSpeed = 30;
	public static int jumpTick, jumpIndex, jumpSpeed = 30;
	
	// the update methods loops through the image arrays at a set speed
	public static void updateAnimationIdle() {
		idleTick++;
		if(idleTick >= idleSpeed) {
			idleTick = 0;
			if (Main.dog.getHorizontalDirection() == 1) {
				idleRightIndex++;
				if(idleRightIndex >= Images.rightIdleDog1.length) {
					idleRightIndex = 0;
				}
			}
			if (Main.dog.getHorizontalDirection() == -1) {
				idleLeftIndex++;
				if(idleLeftIndex >= Images.leftIdleDog1.length) {
					idleLeftIndex = 0;
				}
			}
		}
	}
	
	public static void updateAnimationRun() {
		runTick++;
		if(runTick >= runSpeed) {
			runTick = 0;
			runIndex++;
			if(runIndex >= Images.rightRunDog1.length) {
				runIndex = 0;
			}
		}
	}
	
	public static void updateAnimationJump() {
		jumpTick++;
		if(jumpTick >= jumpSpeed) {
			jumpTick = 0;
			jumpIndex++;
			if(jumpIndex >= Images.rightJumpDog1.length) {
				jumpIndex = 0;
			}
		}
	}
	
//	public void draw(Graphics2D g2) {
//		BufferedImage img = null;
//		if (direction.equals("right")) {
//			if (jump!) {
//				for (int i = 0; i < rightWalkDog1.length; i++) {
//					g2.draw(rightWalkDog1[i], main.x, main.y, 40, 40);
//				}
//			}
//		}
//	}
	// idk what im doing
}
