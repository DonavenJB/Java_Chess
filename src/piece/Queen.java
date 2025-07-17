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
	public boolean canMove(int targetCol, int targetRow) {
		
		if(isWithinBoard(targetCol,targetRow) && isSameSquare(targetCol,targetRow) == false) {
			
			// Vertical & Horizontal
			if(targetCol == preCol || targetRow == preRow) {
				if(pieceIsOnStraightLine(targetCol,targetRow) == false && isValidSquare(targetCol,targetRow)) {
					return true;
				}
			}
			
			// Diagonal
			if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
				if(pieceIsOnDiagonalLine(targetCol,targetRow) == false && isValidSquare(targetCol, targetRow)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
