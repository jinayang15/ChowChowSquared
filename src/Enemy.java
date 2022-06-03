import java.awt.*;

public class Enemy extends Rectangle{
	public int health;
	private Rectangle bounds;
	public int aniIndex, enemyState, enemyType;
	public int aniSpeed = 25;
	
	public Enemy(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle ((int)x, (int)y, 20, 20);
		
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
