package piece;

import main.GamePanel;

public class Rook extends Piece {

	public Rook(int color, int col, int row) {
		super(color, col, row);
		
		// load the sprite based on side color
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/w-rook");
		}
		else {
			image = getImage("/piece/b-rook");
		}
	}
}
