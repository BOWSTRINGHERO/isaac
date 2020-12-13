import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JFrame;

import character.Isaac;
import map.Background;
import monster.Monster;
import monster.Stone;
import monster.Worm;
import objectSize.StoneSize;
import objectSize.WormSize;
import structure.Rock;
import structure.Spike;

public class IsaacApp extends JFrame {
	private JFrame app;
	private Background bg;	// background
	private Isaac isaac;	// player
	private Vector<Rock> rock;
	private Vector<Spike> spike;
	private Vector<Monster> monster;
	
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
		rock = new Vector<Rock>();
		spike = new Vector<Spike>();
		monster = new Vector<Monster>();
		
		// 구석자리 바위
		rock.add(new Rock(app, 125, 115));
		rock.add(new Rock(app, 640, 115));
		rock.add(new Rock(app, 740, 115));
		rock.add(new Rock(app, 785, 160));
		// center 바위
		rock.add(new Rock(app, 425, 225));
		rock.add(new Rock(app, 475, 225));
		rock.add(new Rock(app, 425, 275));
		rock.add(new Rock(app, 475, 275));
		rock.add(new Rock(app, 425, 325));
		rock.add(new Rock(app, 475, 325));
		

		spike.add(new Spike(app, 125, 165));
		spike.add(new Spike(app, 130, 445));
		spike.add(new Spike(app, 180, 445));
		spike.add(new Spike(app, 675, 275));
		spike.add(new Spike(app, 675, 325));
		
		
		isaac = new Isaac(app, rock, spike, monster);
		monster.add(new Worm(app, isaac, rock, "monster/worm.png", WormSize.WIDTH, WormSize.HEIGHT));
		monster.add(new Stone(app, isaac, rock, "monster/stone.png", StoneSize.WIDTH, StoneSize.HEIGHT));
		
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
