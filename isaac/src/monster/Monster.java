package monster;

import java.util.Vector;

import javax.swing.JFrame;

import SpriteSheet.SpriteSheet;
import character.Character;
import character.Isaac;
import lombok.Data;
import objectSize.BulletSize;
import objectSize.DeadSize;
import objectSize.Gap;
import objectSize.IsaacSize;
import objectSize.StructureSize;
import objectSize.ViewDirect;
import objectSize.WormSize;
import structure.Rock;

@Data

public class Monster extends Character{
	private final static String TAG = "Monster : ";
	
	private Isaac isaac;
	private SpriteSheet ssMonster;
	private SpriteSheet ssDead;
	private String url;
	private int imgWidth, imgHeight;
	
	private Vector<Rock> rock;

	public Monster(JFrame app, Isaac isaac, Vector<Rock> rock, String url, int imgWidth, int imgHeight) {
		super(app);
		this.isaac = isaac;
		this.rock = rock;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.url = url;
		ssDead = new SpriteSheet("monster/deadMonster.png", "dead", 0, 0, DeadSize.WIDTH, DeadSize.HEIGHT);
		init();
		setting();
		batch();
	}
	public void init() {
	}
	public void setting() {
		
	}
	public void batch() {
		
	}
	public void moveDirectCheck() {
		// 오른쪽 
		if(getXChar() + WormSize.WIDTH < getIsaac().getXChar() + 15) {
			setRight(true);
		} else {
			setRight(false);
		}
		// 왼쪽
		if(getXChar() > getIsaac().getXChar() + IsaacSize.HEADWIDTH - 15) {
			setLeft(true);
		} else {
			setLeft(false);
		}
		// 아래쪽
		if(getYChar() + WormSize.HEIGHT < getIsaac().getYChar() + 15) {
			setDown(true);
		}else {
			setDown(false);
		}
		// 위쪽
		if(getYChar() > getIsaac().getYChar() + IsaacSize.HEADHEIGHT ) {
			setUp(true);
		}else {
			setUp(false);
		}
	}
	public void collisionRock() {
		for(int i = 0; i < rock.size(); i++) {
			// 오른쪽으로 이동중일 때 
			if(getXChar() + getImgWidth() > rock.get(i).getXStructure() && getXChar() + getImgWidth() < rock.get(i).getXStructure() + StructureSize.WIDTH 
				&& getYChar() + getImgHeight() > rock.get(i).getYStructure() && getYChar() < rock.get(i).getYStructure() + StructureSize.HEIGHT) {
				setRight(false);
			}
			// 왼쪽으로 이동중일때
			if(getXChar() > rock.get(i).getXStructure() && getXChar()< rock.get(i).getXStructure() + StructureSize.WIDTH 
				&& getYChar() + getImgHeight() > rock.get(i).getYStructure() && getYChar() < rock.get(i).getYStructure() + StructureSize.HEIGHT) {
				setLeft(false);
			}
			// 아래쪽으로 이동중 일때
			if(getXChar() + getImgWidth() > rock.get(i).getXStructure() && getXChar() < rock.get(i).getXStructure() + StructureSize.WIDTH 
				&& getYChar() + getImgHeight() > rock.get(i).getYStructure() && getYChar() < rock.get(i).getYStructure() + StructureSize.HEIGHT) {
				setDown(false);
			}
			// 위쪽으로 이동중 일때
			if(getXChar() + getImgWidth() > rock.get(i).getXStructure() && getXChar() < rock.get(i).getXStructure() + StructureSize.WIDTH 
				&& getYChar() > rock.get(i).getYStructure() && getYChar() < rock.get(i).getYStructure() + StructureSize.HEIGHT) {
				setUp(false);
			}
		}
	}
	@Override
	public void moveRight() {
		if(isRight()) {
			setViewDirect(ViewDirect.RIGHT);
			if(getXChar() + WormSize.WIDTH < isaac.getXChar() + 15) {
				setXChar(getXChar() + 1);
			}
		}
	}
	@Override
	public void moveLeft() {
		if(isLeft()) {
			setViewDirect(ViewDirect.LEFT);
			if(getXChar() > isaac.getXChar() + IsaacSize.HEADWIDTH - 15) {
				setXChar(getXChar() - 1);
			}
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
			if(getYChar() + WormSize.HEIGHT < isaac.getYChar() + 15) {
				setYChar(getYChar() + 1);
			}
		}
	}
	public void moveMotion() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int motion = 0;
				if(isMoveStart() == false) {
					setMoveStart(true);
					while(!isDead()) {
						if(isDown() && getViewDirect() == ViewDirect.DOWN) {
							if(motion > 3)
								motion = 0;
							ssMonster.setXPos((getImgWidth() * motion) + (Gap.COLUMNGAP * motion));
							if(getViewDirect() == ViewDirect.DOWN) {
								ssMonster.setYPos(getImgHeight() * 0 + Gap.ROWGAP * 0);	// 몸 이미지 y좌표
								ssMonster.drawObject(getXChar(), getYChar());
								motion += 1;
							}
						}
						else if(isLeft() && getViewDirect() == ViewDirect.LEFT) {
							if(motion > 3)
								motion = 0;
							ssMonster.setXPos((getImgWidth() * motion) + (Gap.COLUMNGAP * motion));
							if(getViewDirect() == ViewDirect.LEFT) {
								ssMonster.setYPos(getImgHeight() * 1 + Gap.ROWGAP * 1);	// 몸 이미지 y좌표
								ssMonster.drawObject(getXChar(), getYChar());
								motion += 1;
							}
						}
						else if(isUp() && getViewDirect() == ViewDirect.UP) {
							if(motion > 3)
								motion = 0;
							ssMonster.setXPos((getImgWidth() * motion) + (Gap.COLUMNGAP * motion));
							if(getViewDirect() == ViewDirect.UP) {
								ssMonster.setYPos(getImgHeight() * 2 + Gap.ROWGAP * 2);	// 몸 이미지 y좌표
								ssMonster.drawObject(getXChar(), getYChar());
								motion += 1;
							}
						}
						else if(isRight() && getViewDirect() == ViewDirect.RIGHT) {
							if(motion > 3)
								motion = 0;
							ssMonster.setXPos((getImgWidth() * motion) + (Gap.COLUMNGAP * motion));
							if(getViewDirect() == ViewDirect.RIGHT) {
								ssMonster.setYPos(getImgHeight() * 3 + Gap.ROWGAP * 3);	// 몸 이미지 y좌표
								ssMonster.drawObject(getXChar(), getYChar());
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
	public void dead() {
		int rowCount = 0;
		getApp().remove(ssMonster);
		getApp().repaint();
		for(int i = 0; i < 11; i ++) {
			if(i % 4 == 0 && i > 0) {
				rowCount += 1;
			}
			int x = DeadSize.WIDTH * (i % 4) + Gap.COLUMNGAP * (i % 4);
			int y = DeadSize.HEIGHT * rowCount  + (Gap.ROWGAP * rowCount);
			ssDead.setXPos(x);
			ssDead.setYPos(y);
			ssDead.drawObject(getXChar(), getYChar());
			if(i == 0) {
				getApp().add(ssDead, 2);
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}