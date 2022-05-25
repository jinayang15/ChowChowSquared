import java.awt.Rectangle;

public class Tile extends Rectangle {
	// if solid = true, you cannot pass thru the block
	// if solid = false, you can pass thru the block
	private boolean solid = true;
	// check if Tile is solid
	// returns boolean
	public boolean checkSolid() {
		return this.solid;
	}
	// set Tile to solid or not solid with boolean
	public void setSolid(boolean solidity) {
		this.solid = solidity;
	}

}
