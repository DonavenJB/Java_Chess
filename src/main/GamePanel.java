package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

// To run the game loop use Thread class -> implement Runnable -> Create run method

public class GamePanel extends JPanel implements Runnable {
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 800;
	final int FPS = 60;
	Thread gameThread;  // To use this thread we implement runnable interface
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.black);
	}
	
	public void launchGame() {
		gameThread = new Thread(this); // Here we instantiate the thread
		gameThread.start();  // Launches this thread; run() executes on a separate call stack.
	}
	// Runnable is a functional interface; its single abstract method is run().
	// The game loop is a sequence of processes that run continuously as long as the game is running
		@Override
		public void run() {
			
			// GAME LOOP
			double drawInterval = 1000000000/FPS;
			double delta = 0;
			// Here we use System.nanoTime() to measure the elapsed time and call update and repaint methods once every 1/60 of a second
			long lastTime = System.nanoTime(); 
			long currentTime;
			
			while(gameThread != null) {
				
				currentTime = System.nanoTime();
				
				delta += (currentTime - lastTime)/drawInterval;
				lastTime = currentTime;
				
				// calls update method and paintComponent method 60 times per second
				if(delta >= 1) {
					update();
					repaint(); 
					delta--;
				}
			}
		}
	// Update information such as pieces X and Y position or number of pieces left on the board
	private void update() {
		
	}
	// Paint component handles all drawling (chess board, pieces, messages)
	public void paintComponent(Graphics g) { 
		//paintComponent is a method in JComponent that JPanel inherits and is used to draw objects on the panel
		super.paintComponent(g);
	}

}
