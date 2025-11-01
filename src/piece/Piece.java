package piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Piece {

    public BufferedImage image;
    public int x, y;                  // pixel coordinates, derived from col and row using GamePanel.TILE
    public int col, row, preCol, preRow; // grid coordinates, pre values useful for undo or simulation
    public int color;

    public Piece(int color, int col, int row) {

        this.color = color;
        this.col = col;
        this.row = row;

        // GamePanel.TILE is computed during painting, set x and y later in draw
        x = 0;
        y = 0;

        preCol = col;
        preRow = row;
    }

    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;

        try {
            // load PNG resource from classpath
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // convert grid column to pixel x using unified tile size
    public int getX(int col) {
        return col * GamePanel.TILE;
    }

    // convert grid row to pixel y using unified tile size
    public int getY(int row) {
        return row * GamePanel.TILE;
    }
    
    public int getCol(int xPxTopLeft) {
        return Math.max(0, Math.min(7, (xPxTopLeft + GamePanel.TILE/2) / GamePanel.TILE));
    }
    public int getRow(int yPxTopLeft) {
        return Math.max(0, Math.min(7, (yPxTopLeft + GamePanel.TILE/2) / GamePanel.TILE));
    }
    
    public void updatePosition() {
    	
    	x = getX(col);
    	y = getY(row);
    	preCol = getCol(x);
    	preRow = getRow(y);
    }

    public void draw(Graphics2D g2) {
        // Initialize pixel coordinates from current grid on first draw only.
        // Do NOT overwrite every frame; simulate() updates x/y while dragging.
        if (x == 0 && y == 0) {
            x = getX(col);
            y = getY(row);
        }

        // draw the image scaled to the current tile
        g2.drawImage(image, x, y, GamePanel.TILE, GamePanel.TILE, null);
    }
}
