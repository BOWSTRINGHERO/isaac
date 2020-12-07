import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import character.Isaac;
import map.Background;

public class IsaacApp extends JFrame {
	private JFrame app;
	private Background bg;	// background
	private Isaac isaac;	// player
	
	public IsaacApp() {
		init();
		setting();
		batch();
		listener();
		setVisible(true);
	}
	
	public void init() {
		app = this;
		bg = new Background(app);
		isaac = new Isaac(app);
	}
	
	public void setting() {
		app.setTitle("IsaacApp");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setLocationRelativeTo(null);
		app.setSize(960, 640);
		app.setLayout(null);
	}
	
	public void batch() {
		
	}
	
	public void listener() {
		keyboardEvent();
	}
	
	
	public static void main(String[] args) {
		new IsaacApp();
	}

	public void keyboardEvent() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					isaac.moveRight();
				} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					isaac.moveLeft();
				} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					isaac.moveDown();
				} else if(e.getKeyCode() == KeyEvent.VK_UP) {
					isaac.moveUp();
				} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					isaac.attack();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					isaac.setRight(false);
					isaac.refreshDirect();
				} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					isaac.setLeft(false);
					isaac.refreshDirect();
				} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					isaac.setDown(false);
					isaac.refreshDirect();
				} else if(e.getKeyCode() == KeyEvent.VK_UP) {
					isaac.setUp(false);
					isaac.refreshDirect();
				} 
			}
		});
	}
	

}