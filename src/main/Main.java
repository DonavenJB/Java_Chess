package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame("Java Chess");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		// Add GamePanel to the window
		GamePanel gp = new GamePanel(); // Instantiate the GamePanel as "gp"
		window.add(gp); // Add gp to the window
		window.pack(); 	// By packing like this, the window adjusts  its size to this GamePanel

		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		// Once the window is created we call launchGame method which starts the thread and call the run method  
		gp.launchGame();
	}
}
