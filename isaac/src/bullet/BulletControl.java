package bullet;

import java.util.Vector;

import javax.swing.JFrame;
import SpriteSheet.SpriteSheet;
import lombok.Data;
import objectSize.ViewDirect;

@Data

public class BulletControl {
	private final static String TAG = "BulletControl : ";
	
	private JFrame app;
	private Vector<Bullet> bullets = new Vector<Bullet>();
	private boolean delayBullet = false;
	private boolean isAttacking = false;
	
	public BulletControl(JFrame app) {
		this.app = app;
		
	}
	
	public void addBullet(String gubun, double attackDamage, int direct, int xBullet, int yBullet) {
		if(!delayBullet) {
			if(direct == ViewDirect.DOWN) {
				bullets.add(new Bullet(gubun, attackDamage, direct, xBullet + 6, yBullet + 18));
			} else if(direct == ViewDirect.LEFT) {
				bullets.add(new Bullet(gubun, attackDamage, direct, xBullet - 13, yBullet + 8));
			} else if(direct == ViewDirect.UP) {
				bullets.add(new Bullet(gubun, attackDamage, direct, xBullet + 6, yBullet - 14));
			} else {
				bullets.add(new Bullet(gubun, attackDamage, direct, xBullet + 26, yBullet + 8));
			}
			drawBullet();
			// 불릿을 쏘고 난뒤 다시 쏠수 있도록 바꾸는 부분
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						delayBullet = true;
						Thread.sleep(300);
						delayBullet = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}					
				}
			}).start();;
		}
	}
	public void drawBullet() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(isAttacking == false) {
					isAttacking = true;
					int collisionCount = 0;
					while(isAttacking) {
						if(bullets.size() != 0) {
							for (int i = 0; i < bullets.size(); i++) {
								if(bullets.get(i).getXBullet() < 110 || bullets.get(i).getXBullet() > 835 || bullets.get(i).getYBullet() < 90 || bullets.get(i).getYBullet() > 500) {
									// 충돌 검사
									if(!bullets.get(i).isCollide()) {
										collisionCount++;
									}
									bullets.get(i).setCollide(true);
								}
								// 충돌이 아니면 계속 그림
								if(!bullets.get(i).isCollide()) {
									if(bullets.get(i).getDirect() == ViewDirect.DOWN) {
										bullets.get(i).setYBullet(bullets.get(i).getYBullet() + 1);
									} else if(bullets.get(i).getDirect() == ViewDirect.LEFT) {
										bullets.get(i).setXBullet(bullets.get(i).getXBullet() - 1);
									} else if(bullets.get(i).getDirect() == ViewDirect.UP) {
										bullets.get(i).setYBullet(bullets.get(i).getYBullet() - 1);
									} else {	// 보는 방향 오른쪽
										bullets.get(i).setXBullet(bullets.get(i).getXBullet() + 1);
									}
									app.add(bullets.get(i).getSsBullet(), 0);
									bullets.get(i).getSsBullet().drawObject(bullets.get(i).getXBullet(), bullets.get(i).getYBullet());
								} else {
									// 충돌 시 불릿 제거
									app.remove(bullets.get(i).getSsBullet());
									app.repaint();
								}
							}
							if(collisionCount == bullets.size()) {
								isAttacking = false;
								bullets.removeAllElements();
							}
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				}
				
			}
		}).start();
	}
	public void removingMotion(Bullet bullet) {
		
	}
	
	// 구현되야 하는 것들
	// 몹 충돌
	// 구조물 충돌
}