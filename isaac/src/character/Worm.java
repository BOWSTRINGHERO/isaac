package character;

import java.util.Vector;

import javax.swing.JFrame;

import SpriteSheet.SpriteSheet;
import lombok.Data;
import objectSize.Gap;
import objectSize.IsaacSize;
import objectSize.ViewDirect;
import objectSize.WormSize;
import structure.Rock;

@Data

public class Worm extends Character {
	
	private Worm worm = this;
	private final static String GUBUN = "Worm : ";
	
	private Isaac isaac;
	private SpriteSheet ssBody;
	private boolean moveStart = false;

	private Vector<Rock> rock;

	public Worm(JFrame app, Isaac isaac, Vector<Rock> rock) {
		super(app);
		this.isaac = isaac;
		init(rock);
		setting();
		batch();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!isDead()) {
					moveDirectCheck();
					collisionRock();
					moveRight();
					moveLeft();
					moveUp();
					moveDown();
					moveMotion();
					ssBody.drawObject(getXChar(), getYChar());
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	public void init(Vector<Rock> rock) {
		ssBody = new SpriteSheet("monster/worm.png", "monster", 0, 0, WormSize.RLWIDTH, WormSize.RLHEIGHT);
		this.rock = rock;
	}
	public void setting() {
		setViewDirect(ViewDirect.RIGHT);
		setXChar(180);
		setYChar(130);	
		setLife(8);
		setAttackDamge(1);
	}
	public void batch() {
		ssBody.drawObject(getXChar(), getYChar());
		getApp().add(ssBody, 1);
	}
	public void moveDirectCheck() {
		if(isaac.getXChar() > getXChar()) {
			setRight(true);
		} else {
			setRight(false);
		}
		if(isaac.getXChar() < getXChar()) {
			setLeft(true);
		} else {
			setLeft(false);
		}
		if(isaac.getYChar() + IsaacSize.BODYHEIGHT > getYChar()) {
			setDown(true);
		}else {
			setDown(false);
		}
		if(isaac.getYChar() + IsaacSize.BODYHEIGHT < getYChar()) {
			setUp(true);
		}else {
			setUp(false);
		}
			
	}
	public void collisionRock() {
		
	}
	@Override
	public void moveRight() {
		if(isRight()) {
			setViewDirect(ViewDirect.RIGHT);
			setXChar(getXChar() + 1);
		}
	}
	@Override
	public void moveLeft() {
		if(isLeft()) {
			setViewDirect(ViewDirect.LEFT);
			setXChar(getXChar() - 1);
		}
	}
	@Override
	public void moveUp() {
		if(isUp()) {
			setViewDirect(ViewDirect.UP);
			setYChar(getYChar() - 1);
		}
	}
	@Override
	public void moveDown() {
		if(isDown()) {
			setViewDirect(ViewDirect.DOWN);
			setYChar(getYChar() + 1);
		}
	}
	public void moveMotion() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int motion = 0;
				if(moveStart == false) {
					setMoveStart(true);
					while(!isDead()) {
						if(isRight()) {
							if(motion > 3)
								motion = 0;
							ssBody.setXPos((WormSize.RLWIDTH * motion) + (Gap.COLUMNGAP * motion));
							ssBody.setWidth(WormSize.RLWIDTH);
							ssBody.setHeight(WormSize.RLHEIGHT);
							if(getViewDirect() == ViewDirect.RIGHT) {
								ssBody.setYPos(0);	// 몸 이미지 y좌표
								ssBody.drawObject(getXChar(), getYChar());
								motion += 1;
							}
						}
						if(isLeft()) {
							if(motion > 3)
								motion = 0;
							ssBody.setXPos((WormSize.RLWIDTH * motion) + (Gap.COLUMNGAP * motion));
							ssBody.setWidth(WormSize.RLWIDTH);
							ssBody.setHeight(WormSize.RLHEIGHT);
							if(getViewDirect() == ViewDirect.LEFT) {
								ssBody.setYPos(WormSize.RLHEIGHT + Gap.ROWGAP);	// 몸 이미지 y좌표
								ssBody.drawObject(getXChar(), getYChar());
								motion += 1;
							}
						}
						if(isUp()) {
							if(motion > 3)
								motion = 0;
							ssBody.setXPos((WormSize.UDWIDTH * motion) + (Gap.COLUMNGAP * motion));
							ssBody.setWidth(WormSize.UDWIDTH);
							ssBody.setHeight(WormSize.UDHEIGHT);
							if(getViewDirect() == ViewDirect.UP) {
								ssBody.setYPos(WormSize.RLHEIGHT * 2 + Gap.ROWGAP * 2);	// 몸 이미지 y좌표
								ssBody.drawObject(getXChar(), getYChar());
								motion += 1;
							}
						}
						if(isDown()) {
							if(motion > 3)
								motion = 0;
							ssBody.setXPos((WormSize.UDWIDTH * motion) + (Gap.COLUMNGAP * motion));
							ssBody.setWidth(WormSize.UDWIDTH);
							ssBody.setHeight(WormSize.UDHEIGHT);
							if(getViewDirect() == ViewDirect.DOWN) {
								ssBody.setYPos(WormSize.RLHEIGHT * 2 + Gap.ROWGAP * 3 + WormSize.UDHEIGHT);	// 몸 이미지 y좌표
								ssBody.drawObject(getXChar(), getYChar());
								motion += 1;
							}
						}
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			}
		}).start();
	}
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		super.attack();
	}
}
