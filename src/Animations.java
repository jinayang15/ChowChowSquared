import java.awt.Graphics2D;

public class Animations {
	public static int tick, index, speed = 30;
	public static String direction = "right";
	
	public static void updateAnimation() {
		tick++;
		if(tick >= speed) {
			tick = 0;
			index++;
			if(index >= Images.rightIdleDog1.length) {
				index = 0;
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
