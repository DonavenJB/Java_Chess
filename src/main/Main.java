package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame("Java Chess");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		// add GamePanel to the window
		GamePanel gp = new GamePanel(); // instantiate the GamePanel as gp
		window.add(gp); // add gp to the window
		window.pack(); 	// pack sizes the frame to the preferred size of its contents
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		// once the window is created call launchGame which starts the thread and runs the loop  
		gp.launchGame();
	}
}
