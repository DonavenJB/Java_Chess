package piece;

import main.GamePanel;

public class Queen extends Piece{

	public Queen(int color, int col, int row) {
		super(color, col, row);

		// load the sprite based on side color
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/w-queen");
		}
		else {
			image = getImage("/piece/b-queen");
		}
	}
}
