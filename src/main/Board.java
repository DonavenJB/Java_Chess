package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Board {
	
	// The chess board consists of 8 x 8 squares and I set the single square size to 100 so a single square is 100 x 100 pixels 
	// The board size is 800 x 800 
	final int MAX_COL = 8;
	final int MAX_ROW = 8;
	public static final int SQUARE_SIZE = 100;
	public static final int HALF_SQUARE_SIZE = SQUARE_SIZE/2;
	
	//Here we create a draw method and receive Graphic2D (called from paint component)
	public void draw(Graphics2D g2) { 
		// We use this Graphics2D class and draw the board
		
		int c = 0;
		for(int row = 0; row < MAX_ROW; row++) {
			// Here we increase this column one by one
			// Once this column loop is done then we increase row by one then go to the next row
			
			for(int col = 0; col < MAX_COL; col++) {
				
				// We need to switch colors as drawing squares so we set two colors on this Graphics2D
				if(c == 0) {
					g2.setColor(new Color(210,165,125));
					c = 1; // Every time we draw a square, we switch the color
				}
				else {
					g2.setColor(new Color(175,115,70));
					c = 0; // Every time we draw a square, we switch the color
				}
				// we draw a square one by one (x, y, width, height)
				// we get the x and the y by multiplying the col and the row by SQUARE_SIZE
				// width and height is SQUARE_SIZE
				g2.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
			
			// Reset the change so that c becomes 1 again after column loop completes
			if(c == 0) {
				c = 1;
			}
			else {
				c = 0;
			}
		}
	}
}
