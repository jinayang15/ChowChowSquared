
public class Animations {
	public static int idleTick, idleIndex, idleSpeed = 30;
	public static int runTick, runIndex, runSpeed = 30;
	public static int jumpTick, jumpIndex, jumpSpeed = 30;
	public static String direction = "right";
	
	public static void updateAnimationIdle() {
		idleTick++;
		if(idleTick >= idleSpeed) {
			idleTick = 0;
			idleIndex++;
			if(idleIndex >= Images.rightIdleDog1.length) {
				idleIndex = 0;
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
			if(jumpIndex >= Images.rightRunDog1.length) {
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
