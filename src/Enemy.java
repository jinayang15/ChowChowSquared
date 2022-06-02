import java.awt.*;

public class Enemy extends Rectangle{
	private float x, y;
	public int health;
	private Rectangle bounds;
	public int aniIndex, enemyState, enemyType;
	public int aniSpeed = 25;
	
	public Enemy(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle ((int)x, (int)y, 20, 20);
		
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void setX(float x) {
		this.x = x;
	}
	
}
