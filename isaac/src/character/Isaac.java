package character;



import javax.swing.JFrame;
import javax.swing.JLabel;

import SpriteSheet.SpriteSheet;
import lombok.Data;
import objectSize.Gap;
import objectSize.IsaacSize;
import objectSize.ViewDirect;

@Data

public class Isaac extends Character{

	private final static String TAG = "Isaac : ";
	
	private Isaac isaac = this;
	private SpriteSheet ssHead, ssBody;
	

	public Isaac(JFrame app) {
		super(app);
		System.out.println(TAG + "make Isaac");
		setApp(app);	// jframe
		init();
		setting();
		batch();
	}
	public void init() {
		ssHead = new SpriteSheet("isaac/isaac.png", "IsaacssHead", 0, 0, IsaacSize.HEADWIDTH, IsaacSize.HEADHEIGHT);
		ssBody = new SpriteSheet("isaac/isaac.png", "IsaacBody", 0, (IsaacSize.HEADHEIGHT + Gap.ROWGAP), IsaacSize.BODYWIDTH, IsaacSize.BODYHEIGHT);
		
	}
	public void setting() {
		setXChar(480);	// 아이작 초반 x위치 480 설정
		setYChar(300);	// 아이작 초반 y위치 480 설정
		setAttackDamge(1);	// 아이작 공격력 1 세팅
		setLife(6);	//	생명력 6 설정
		setMaxLife(6);	//	최대 생명력 6설정
		ssHead.drawObject(getXChar(), getYChar());	// 아이작 머리 위치 설정
		ssBody.drawObject(getXChar() + 4, getYChar() + 20);	//아이작 몸 위치 설정
	}
	public void batch() {
		getApp().add(ssHead,0);	// 머리 배치
		getApp().add(ssBody,1);	// 몸 배치
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
						if(getXChar() + IsaacSize.BODYWIDTH > 820) {
							setRight(false);
							refreshDirect();
							break;
						}
						if(motion == 10)
							motion = 0;
						setXChar(getXChar() + 3);
						ssBody.setXPos((IsaacSize.BODYWIDTH * motion) + (Gap.COLUMNGAP * motion));
						if(getViewDirect() == ViewDirect.RIGHT) {
							ssHead.setXPos(IsaacSize.HEADWIDTH * 2 + Gap.COLUMNGAP * 2);	// 머리 이미지 x좌표
							ssBody.setYPos(IsaacSize.HEADHEIGHT + IsaacSize.BODYHEIGHT + Gap.ROWGAP * 2);	// 몸 이미지 y좌표
							ssHead.drawObject(getXChar(), getYChar());
							ssBody.drawObject(getXChar() + 4, getYChar() + 20);
							motion += 1;
						}
						try {
							Thread.sleep(30);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ssBody.setXPos(0);
					ssBody.drawObject(getXChar() + 4, getYChar() + 20);
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
						if(motion > 9)
							motion = 0;
						setXChar(getXChar() - 3);
						ssBody.setXPos((IsaacSize.BODYWIDTH * motion) + (Gap.COLUMNGAP * motion));
						if(getViewDirect() == ViewDirect.LEFT) {
							ssHead.setXPos(IsaacSize.HEADWIDTH * 6 + Gap.COLUMNGAP * 6);
							ssBody.setYPos(73);
							ssHead.drawObject(getXChar(), getYChar());
							ssBody.drawObject(getXChar() + 4, getYChar() + 20);
							motion += 1;
						}
						try {
							Thread.sleep(30);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ssBody.setXPos(0);
					ssBody.drawObject(getXChar() + 4, getYChar() + 20);
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
						if(getYChar() > 455) {
							setDown(false);
							refreshDirect();
							break;
						}
						if(motion > 9)
							motion = 0;
						setYChar(getYChar() + 3);
						ssBody.setXPos((IsaacSize.BODYWIDTH * motion) + (Gap.COLUMNGAP * motion));
						if(getViewDirect() == ViewDirect.DOWN) {
							ssHead.setXPos(0);
							ssBody.setYPos(IsaacSize.HEADWIDTH + Gap.COLUMNGAP);
							ssHead.drawObject(getXChar(), getYChar());
							ssBody.drawObject(getXChar() + 4, getYChar() + 20);
							motion += 1;
						}
						try {
							Thread.sleep(30);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ssBody.setXPos(0);
					ssBody.drawObject(getXChar() + 4, getYChar() + 20);
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
						if(motion > 9)
							motion = 0;
						setYChar(getYChar() - 3);
						ssBody.setXPos((IsaacSize.BODYWIDTH * motion) + (Gap.COLUMNGAP * motion));
						if(getViewDirect() == ViewDirect.UP) {
							ssHead.setXPos(IsaacSize.HEADWIDTH * 4 + Gap.COLUMNGAP * 4);
							ssBody.setYPos(31);
							ssHead.drawObject(getXChar(), getYChar());
							ssBody.drawObject(getXChar() + 4, getYChar() + 20);
							motion += 1;
						}
						try {
							Thread.sleep(30);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ssBody.setXPos(0);
					ssBody.drawObject(getXChar() + 4, getYChar() + 20);
				}
			}
		}).start();
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
		isaac.getBulletControl().addBullet("isaac", isaac.getAttackDamge(), isaac.getViewDirect(), isaac.getXChar(), isaac.getYChar());
	}
}
// 2초마다 눈깜박임
//new Thread(new Runnable() {
//	@Override
//	public void run() {
//		int xCurrent;
//		int xChange;
//		while(true) {
//			try {
//				Thread.sleep(2000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			closeEye(getViewDirect());
//			head.drawObject(getXChar(), getYChar());
//			try {
//				Thread.sleep(500);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			openEye(getViewDirect());
//			ssHead.drawObject(getXChar(), getYChar());
//			
//		}
//	}
//}).start();

