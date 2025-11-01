package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Board {

    // The chess board consists of 8 x 8 squares
    final int MAX_COL = 8;
    final int MAX_ROW = 8;

    // create a draw method and receive Graphic2D, called from paint component
    public void draw(Graphics2D g2, int panelWidth, int panelHeight) { 
        // use the unified tile computed by GamePanel so board and pieces match
        int effectiveSquare = GamePanel.TILE;

        int c = 0; // color toggle, 0 light, 1 dark
        for(int row = 0; row < MAX_ROW; row++) {

            for(int col = 0; col < MAX_COL; col++) {

                // switch colors as squares are drawn
                if(c == 0) {
                    g2.setColor(new Color(210,165,125));
                    c = 1; // toggle after each square
                }
                else {
                    g2.setColor(new Color(175,115,70));
                    c = 0; // toggle after each square
                }

                // draw one square, grid position times tile size
                g2.fillRect(col*effectiveSquare, row*effectiveSquare, effectiveSquare, effectiveSquare);
            }

            // flip starting color at the next row
            if(c == 0) {
                c = 1;
            }
            else {
                c = 0;
            }
        }
    }
}
