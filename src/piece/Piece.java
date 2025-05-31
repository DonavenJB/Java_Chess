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
            // load PNG resource from classpath, extension added here
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

    public void draw(Graphics2D g2) {
        // recompute pixel coordinates from current grid coordinates each frame
        x = getX(col);
        y = getY(row);

        // draw the image scaled to the current tile
        g2.drawImage(image, x, y, GamePanel.TILE, GamePanel.TILE, null);
    }
}
