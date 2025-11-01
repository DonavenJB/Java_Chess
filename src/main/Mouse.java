package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
	
	public int x,y;
	public boolean pressed;
	
	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
		x = e.getX(); // capture press location so the first hit-test is correct
		y = e.getY();
		System.out.printf("[Mouse] PRESSED at px=(%d,%d)%n", x, y);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		System.out.printf("[Mouse] RELEASED at last px=(%d,%d)%n", x, y);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX(); 
		y = e.getY();
		// System.out.printf("[Mouse] DRAG px=(%d,%d)%n", x, y);
	}
	@Override 
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		// System.out.printf("[Mouse] MOVE px=(%d,%d)%n", x, y);
	}
}
