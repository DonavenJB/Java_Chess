package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;

// To run the game loop use Thread class, implement Runnable, create run method

public class GamePanel extends JPanel implements Runnable {
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 800;

	// unified tile size used by board and pieces, computed each repaint from panel size
	public static int TILE = Math.min(WIDTH / 8, HEIGHT / 8); // safe initial value

	final int FPS = 60;
	Thread gameThread;  // to use this thread we implement runnable interface
	Board board = new Board();
	Mouse mouse = new Mouse();
	
	// PIECES
	public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
	Piece activeP;
	
	//COLOR
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	int currentColor = WHITE;

	// throttle for panel size logging
	private int lastW = -1, lastH = -1;
	
	public GamePanel() {
		// preferred size communicates desired client area to layout and pack
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.black);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		setPieces();
		copyPieces(pieces, simPieces); // maintain a simulated copy 
	}
	
	public void launchGame() {
		gameThread = new Thread(this); // instantiate the thread
		gameThread.start();  // run executes on a separate call stack
	}
	
	public void setPieces() {
		// initial placement, white rows 6 and 7, black rows 0 and 1
		
		//White Team
		pieces.add(new Pawn(WHITE,0,6));
		pieces.add(new Pawn(WHITE,1,6));
		pieces.add(new Pawn(WHITE,2,6));
		pieces.add(new Pawn(WHITE,3,6));
		pieces.add(new Pawn(WHITE,4,6));
		pieces.add(new Pawn(WHITE,5,6));
		pieces.add(new Pawn(WHITE,6,6));
		pieces.add(new Pawn(WHITE,7,6));
		pieces.add(new Rook(WHITE,0,7));
		pieces.add(new Rook(WHITE,7,7));
		pieces.add(new Knight(WHITE,1,7));
		pieces.add(new Knight(WHITE,6,7));
		pieces.add(new Bishop(WHITE,2,7));
		pieces.add(new Bishop(WHITE,5,7));
		pieces.add(new Queen(WHITE,3,7));
		pieces.add(new King(WHITE,4,7));
		
		//Black Team
		pieces.add(new Pawn(BLACK,0,1));
		pieces.add(new Pawn(BLACK,1,1));
		pieces.add(new Pawn(BLACK,2,1));
		pieces.add(new Pawn(BLACK,3,1));
		pieces.add(new Pawn(BLACK,4,1));
	    pieces.add(new Pawn(BLACK,5,1));
		pieces.add(new Pawn(BLACK,6,1));
		pieces.add(new Pawn(BLACK,7,1));
		pieces.add(new Rook(BLACK,0,0));
		pieces.add(new Rook(BLACK,7,0));
		pieces.add(new Knight(BLACK,1,0));
		pieces.add(new Knight(BLACK,6,0));
		pieces.add(new Bishop(BLACK,2,0));
		pieces.add(new Bishop(BLACK,5,0));
		pieces.add(new Queen(BLACK,3,0));
		pieces.add(new King(BLACK,4,0));		
	}
	private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
		// shallow copy, piece instances are reused
		target.clear();
		for(int i = 0; i < source.size(); i++) {
			target.add(source.get(i));
		}
	}
	// Runnable is a functional interface, its single abstract method is run
	// the game loop runs continuously while the game is active
	@Override
	public void run() {
		// fixed timestep with accumulator, 60 frames per second target
		double drawInterval = 1000000000.0/FPS; // nanoseconds per frame
		double delta = 0;
		// use System.nanoTime to measure elapsed time, call update and repaint at the target rate
		long lastTime = System.nanoTime(); 
		long currentTime;
			
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime)/drawInterval;
			lastTime = currentTime;
				
			// call update and repaint at the fixed rate
			if(delta >= 1) {
				update();
				repaint(); 
				delta--;
			}
		}
	}
	// update information such as positions and state
	private void update() {
		
		////// MOUSE BUTTON PRESSED //////
		if (mouse.pressed) {
			final int tile = TILE; // single dynamic source of truth

			if (activeP == null) {
				int mCol = mouse.x / tile;
				int mRow = mouse.y / tile;

				// clamp to board
				if (mCol < 0) mCol = 0; if (mCol > 7) mCol = 7;
				if (mRow < 0) mRow = 0; if (mRow > 7) mRow = 7;

				System.out.printf("[Update] pressed=%s mouse px=(%d,%d) grid=(%d,%d) turn=%s tile=%d%n",
						mouse.pressed, mouse.x, mouse.y, mCol, mRow,
						currentColor==WHITE?"WHITE":"BLACK", tile);

				for (Piece piece : simPieces) {
					// If the mouse is on an ally piece, pick it up as the activeP
					if (piece.col == mCol && piece.row == mRow) {
						System.out.printf("  [Hit] %s at (%d,%d) color=%s%n",
								piece.getClass().getSimpleName(), piece.col, piece.row,
								piece.color==WHITE?"WHITE":"BLACK");
						if (piece.color == currentColor) {
							activeP = piece;
							System.out.printf("  [Select] activeP=%s%n", piece.getClass().getSimpleName());
						} else {
							System.out.println("  [Select] blocked: wrong color for current turn");
						}
						break;
					}
				}
			}
			else {
				// If the player is holding a piece, simulate the move
				simulate();
			}
		}
	}
	private void simulate() {
		// If a piece is being held, update its position 
		activeP.x = mouse.x - TILE/2;
		activeP.y = mouse.y - TILE/2;
		System.out.printf("[Sim] %s at px=(%d,%d)%n",
				activeP.getClass().getSimpleName(), activeP.x, activeP.y);
	}

	// paint component handles all drawing, board then pieces then messages
	@Override
	public void paintComponent(Graphics g) { 
		// paintComponent is defined in JComponent, JPanel inherits it, used to draw on the panel
		super.paintComponent(g);
		
	    // debug, verify client area 
		if (getWidth()!=lastW || getHeight()!=lastH) {
			lastW = getWidth(); lastH = getHeight();
			System.out.println("Panel size: " + lastW + "×" + lastH);
		}

		Graphics2D g2 = (Graphics2D)g;
		
		// unified tile size for board and pieces, derived from current panel size
		TILE = Math.min(getWidth() / 8, getHeight() / 8);

		// draw board using the same tile
		board.draw(g2, getWidth(), getHeight());

		// draw the pieces after the board, ensures pieces render on top
		for(Piece p : simPieces) {
			if (p != activeP) p.draw(g2); // draw everything except the held piece
		}
		if (activeP != null) activeP.draw(g2); // draw held piece last
	}
}
