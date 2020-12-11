package character;



import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;

import SpriteSheet.SpriteSheet;
import bullet.BulletControl;
import lombok.Data;
import objectSize.Gap;
import objectSize.IsaacSize;
import objectSize.StructureSize;
import objectSize.ViewDirect;
import structure.Rock;
import structure.Spike;

@Data

public class Isaac extends Character{

	private final static String TAG = "Isaac : ";
	private Isaac isaac = this;
	
	private SpriteSheet ssHead, ssBody;
	private Vector<Rock> rock;
	private Vector<Spike> spike;
	private int xPlusBody = 8, yPlusBody = 30;
	
	
	public Isaac(JFrame app, Vector<Rock> rock, Vector<Spike> spike) {
		super(app);
		System.out.println(TAG + "make Isaac");
		init(rock, spike);
		setting();
		batch();
	}
	public void init(Vector<Rock> rock, Vector<Spike> spike) {
		ssHead = new SpriteSheet("isaac/isaac.png", "isaacssHead", 0, 0, IsaacSize.HEADWIDTH, IsaacSize.HEADHEIGHT);
		ssBody = new SpriteSheet("isaac/isaac.png", "isaacBody", 0, (IsaacSize.HEADHEIGHT + Gap.ROWGAP), IsaacSize.BODYWIDTH, IsaacSize.BODYHEIGHT);
		this.rock = rock;
		this.spike = spike;
		setBulletControl(new BulletControl(getApp()));
	}
	public void setting() {
		setViewDirect(ViewDirect.DOWN);
		setXChar(480);	// 아이작 초반 x위치 480 설정
		setYChar(430);	// 아이작 초반 y위치 430 설정
		setAttackDamge(1);	// 아이작 공격력 1 세팅
		setLife(6);	//	생명력 6 설정
		setMaxLife(5);	//	최대 생명력 6설정
		ssHead.drawObject(getXChar(), getYChar());	// 아이작 머리 위치 설정
		ssBody.drawObject(getXChar() + xPlusBody, getYChar() + yPlusBody);	//아이작 몸 위치 설정
	}
	public void batch() {
		getApp().add(ssHead, 0);	// 머리 배치
		getApp().add(ssBody, 1);	// 몸 배치
	}
	
	@Override
	public void moveRight() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(isRight() == false) {
					System.out.println(TAG + "오른쪽으로 움직임");
					setRight(true);
					ssHead.setXPos(IsaacSize.HEADWIDTH * 2 + Gap.COLUMNGAP * 2);	// 머리 이미지 x좌표
					ssBody.setYPos(IsaacSize.HEADHEIGHT + IsaacSize.BODYHEIGHT + Gap.ROWGAP * 2);	// 몸 이미지 y좌표
					int motion = 0;
					setViewDirect(ViewDirect.RIGHT);
					while(isRight()) {
						// 벽 충돌 검사
						if(getXChar() + IsaacSize.BODYWIDTH > 810) {
							setRight(false);
							refreshDirect();
							break;
						}
						boolean isRockCollision = false;
						// 바위 충돌 검사.
						for(int i = 0; i < rock.size(); i++) {
							if(getXChar() + IsaacSize.HEADWIDTH > rock.get(i).getXStructure() && getXChar() + IsaacSize.HEADWIDTH < rock.get(i).getXStructure() + StructureSize.WIDTH 
								&& getYChar() + IsaacSize.HEADHEIGHT > rock.get(i).getYStructure() - 10 && getYChar() < rock.get(i).getYStructure() + StructureSize.HEIGHT - 20) {
								isRockCollision = true;
								break;
							}
						}
						if(isRockCollision) {
							setRight(false);
							refreshDirect();
							break;
						}
						// 바위 충돌 검사. 끝
						if(motion > 9)
							motion = 0;
						setXChar(getXChar() + 3);
						ssBody.setXPos((IsaacSize.BODYWIDTH * motion) + (Gap.COLUMNGAP * motion));
						if(getViewDirect() == ViewDirect.RIGHT) {
							ssHead.setXPos(IsaacSize.HEADWIDTH * 2 + Gap.COLUMNGAP * 2);	// 머리 이미지 x좌표
							ssBody.setYPos(IsaacSize.HEADHEIGHT + IsaacSize.BODYHEIGHT + Gap.ROWGAP * 2);	// 몸 이미지 y좌표
							ssHead.drawObject(getXChar(), getYChar());
							ssBody.drawObject(getXChar() + xPlusBody, getYChar() + yPlusBody);
							motion += 1;
						}
						try {
							Thread.sleep(30);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ssBody.setXPos(0);
					ssBody.drawObject(getXChar() + xPlusBody, getYChar() + yPlusBody);
				}
			}
		}).start();
	}
	@Override
	public void moveLeft() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(isLeft() == false) {
					System.out.println(TAG + "왼쪽으로 움직임");
					int motion = 9;
					setLeft(true);
					ssHead.setXPos(IsaacSize.HEADWIDTH * 6 + Gap.COLUMNGAP * 6);
					ssBody.setYPos(IsaacSize.HEADHEIGHT + IsaacSize.BODYHEIGHT * 2 + Gap.ROWGAP * 3);
					setViewDirect(ViewDirect.LEFT);
					while(isLeft()) {
						if(getXChar() < 130) {
							setLeft(false);	
							refreshDirect();
							break;
						}
						boolean isRockCollision = false;
						// 바위 충돌 검사.
						for(int i = 0; i < rock.size(); i++) {
							if(getXChar() > rock.get(i).getXStructure() && getXChar()< rock.get(i).getXStructure() + StructureSize.WIDTH 
								&& getYChar() + IsaacSize.HEADHEIGHT > rock.get(i).getYStructure() - 10 && getYChar() < rock.get(i).getYStructure() + StructureSize.HEIGHT - 20) {
								isRockCollision = true;
								break;
							}
						}
						// 바위 충돌 검사. 끝
						if(isRockCollision) {
							setLeft(false);
							refreshDirect();
							break;
						}
						if(motion > 9)
							motion = 0;
						setXChar(getXChar() - 3);
						ssBody.setXPos((IsaacSize.BODYWIDTH * motion) + (Gap.COLUMNGAP * motion));
						if(getViewDirect() == ViewDirect.LEFT) {
							ssHead.setXPos(IsaacSize.HEADWIDTH * 6 + Gap.COLUMNGAP * 6);
							ssBody.setYPos(IsaacSize.HEADHEIGHT + IsaacSize.BODYHEIGHT * 2 + Gap.ROWGAP * 3);
							ssHead.drawObject(getXChar(), getYChar());
							ssBody.drawObject(getXChar() + xPlusBody, getYChar() + yPlusBody);
							motion += 1;
						}
						try {
							Thread.sleep(30);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ssBody.setXPos(0);
					ssBody.drawObject(getXChar() + xPlusBody, getYChar() + yPlusBody);
				}
			}
		}).start();
	}
	@Override
	public void moveDown() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(isDown() == false) {
					System.out.println(TAG + "아래쪽으로 움직임");
					int motion = 0;
					setDown(true);
					ssHead.setXPos(0);
					ssBody.setYPos(IsaacSize.HEADWIDTH + Gap.COLUMNGAP);
					setViewDirect(ViewDirect.DOWN);
					while(isDown()) {
						if(getYChar() > 440) {
							setDown(false);
							refreshDirect();
							break;
						}
						boolean isRockCollision = false;
						// 바위 충돌 검사.
						for(int i = 0; i < rock.size(); i++) {
							if(getXChar() + IsaacSize.HEADWIDTH > rock.get(i).getXStructure() + 5 && getXChar() < rock.get(i).getXStructure() + StructureSize.WIDTH - 5
								&& getYChar() + IsaacSize.HEADHEIGHT + IsaacSize.BODYHEIGHT > rock.get(i).getYStructure() && getYChar() + IsaacSize.HEADHEIGHT + IsaacSize.BODYHEIGHT < rock.get(i).getYStructure() + StructureSize.HEIGHT) {
								isRockCollision = true;
								break;
							}
						}
						if(isRockCollision) {
							setUp(false);
							refreshDirect();
							break;
						}
						// 바위 충돌 검사. 끝
						if(motion > 9)
							motion = 0;
						setYChar(getYChar() + 3);
						// 모션
						ssBody.setXPos((IsaacSize.BODYWIDTH * motion) + (Gap.COLUMNGAP * motion));
						if(getViewDirect() == ViewDirect.DOWN) {
							ssHead.setXPos(0);
							ssBody.setYPos(IsaacSize.HEADWIDTH + Gap.COLUMNGAP);
							ssHead.drawObject(getXChar(), getYChar());
							ssBody.drawObject(getXChar() + xPlusBody, getYChar() + yPlusBody);
							motion += 1;
						}
						try {
							Thread.sleep(30);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ssBody.setXPos(0);
					ssBody.drawObject(getXChar() + xPlusBody, getYChar() + yPlusBody);
				}
			}
		}).start();
	}
	@Override
	public void moveUp() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(isUp() == false) {
					System.out.println(TAG + "위쪽으로 움직임");
					int motion = 0;
					setUp(true);
					ssHead.setXPos(IsaacSize.HEADWIDTH * 4 + Gap.COLUMNGAP * 4);
					ssBody.setYPos(IsaacSize.HEADHEIGHT + Gap.ROWGAP);
					setViewDirect(ViewDirect.UP);
					while(isUp()) {
						if(getYChar() < 100) {
							setUp(false);
							refreshDirect();
							break;
						}
						boolean isRockCollision = false;
						// 바위 충돌 검사.
						for(int i = 0; i < rock.size(); i++) {
							if(getXChar() + IsaacSize.HEADWIDTH > rock.get(i).getXStructure() + 5 && getXChar() < rock.get(i).getXStructure() + StructureSize.WIDTH - 10
								&& getYChar() > rock.get(i).getYStructure() - 20 && getYChar() < rock.get(i).getYStructure() + StructureSize.HEIGHT - 10) {
								isRockCollision = true;
								break;
							}
						}
						if(isRockCollision) {
							setUp(false);
							refreshDirect();
							break;
						}
						// 바위 충돌 검사. 끝
						if(motion > 9)
							motion = 0;
						setYChar(getYChar() - 3);
						ssBody.setXPos((IsaacSize.BODYWIDTH * motion) + (Gap.COLUMNGAP * motion));
						if(getViewDirect() == ViewDirect.UP) {
							ssHead.setXPos(IsaacSize.HEADWIDTH * 4 + Gap.COLUMNGAP * 4);
							ssBody.setYPos(IsaacSize.HEADWIDTH + Gap.COLUMNGAP);
							ssHead.drawObject(getXChar(), getYChar());
							ssBody.drawObject(getXChar() + xPlusBody, getYChar() + yPlusBody);
							motion += 1;
						}
						try {
							Thread.sleep(30);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ssBody.setXPos(0);
					ssBody.drawObject(getXChar() + xPlusBody, getYChar() + yPlusBody);
				}
			}
		}).start();
	}
	public void move() {
		
	}
	public void refreshDirect() {
		if(isaac.isDown()) {
			isaac.setViewDirect(ViewDirect.DOWN);
		}
		if(isaac.isLeft()) {
			isaac.setViewDirect(ViewDirect.LEFT);
		}
		if(isaac.isUp()) {
			isaac.setViewDirect(ViewDirect.UP);
		}
		if(isaac.isRight()) {
			isaac.setViewDirect(ViewDirect.RIGHT);
		}
	}
	@Override
	public void attack() {
		new Thread(new Runnable() {
			@Override
			public void run() {
					closeEye(isaac.getViewDirect());
					ssHead.drawObject(getXChar(), getYChar());
					try {
						Thread.sleep(300);
					} catch (Exception e) {
						e.printStackTrace();
					}
					openEye(isaac.getViewDirect());
					ssHead.drawObject(getXChar(), getYChar());
					isaac.getBulletControl().addBullet("isaac", isaac.getAttackDamge(), isaac.getViewDirect(), isaac.getXChar(), isaac.getYChar());
			}
		}).start();
	}
	
	public void closeEye(int viewDirect) {
		switch (viewDirect) {
		case 1:	// 보는 방향 아래
			ssHead.setXPos(IsaacSize.HEADWIDTH + Gap.COLUMNGAP);
			break;
		case 2:	// 보는 방향 왼쪽
			ssHead.setXPos(IsaacSize.HEADWIDTH * 7 + Gap.COLUMNGAP * 7);
			break;
		case 3:	// 보는 방향 위
			ssHead.setXPos(IsaacSize.HEADWIDTH * 5 + Gap.COLUMNGAP * 5);
			break;
		case 4:	// 보는 방향 오른쪽
			ssHead.setXPos(IsaacSize.HEADWIDTH * 3 + Gap.COLUMNGAP * 3);
			break;
		}
	}
	public void openEye(int viewDirect) {
		switch (viewDirect) {
		case 1:	// 보는 방향 아래
			ssHead.setXPos(0);
			break;
		case 2:	// 보는 방향 왼쪽
			ssHead.setXPos(IsaacSize.HEADWIDTH * 6 + Gap.COLUMNGAP * 6);
			break;
		case 3:	// 보는 방향 위
			ssHead.setXPos(IsaacSize.HEADWIDTH * 4 + Gap.COLUMNGAP * 4);
			break;
		case 4:	// 보는 방향 오른쪽
			ssHead.setXPos(IsaacSize.HEADWIDTH * 2 + Gap.COLUMNGAP * 2);
			break;
		}
	}
}




