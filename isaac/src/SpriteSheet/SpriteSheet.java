package SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Data;

@Data
public class SpriteSheet extends JLabel {
	private final static String TAG = "SpriteSheet : ";
    private BufferedImage imgSprite;	// 객체 이미지, 스프리트시
    private String url;			// 이미지 url
    private String gubun;
    private int xPos, yPos;		// 이미지 위치 x, y 좌표
    private int width, height;	// 이미지 가로 세로 길이
	private ImageIcon imgObject;

	public SpriteSheet() {}

	public SpriteSheet(String url, String gubun, int xPos, int yPos, int width, int height) {
		this.url = url;
		this.gubun = gubun;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		loadSpriteImage(url);
	} 
	public void loadSpriteImage(String url) {
		try {
			imgSprite = ImageIO.read(new File("images/" + url));
		} catch (Exception e) {
			System.out.println(TAG + "이미지 로드 실패");
		}	
	}
	
	public BufferedImage getObjectImage() {
		return imgSprite.getSubimage(xPos, yPos, width, height);
	}
    public void drawObject(int xChar, int yChar) {
    	imgObject = new ImageIcon(getObjectImage());
		setIcon(imgObject);
		setSize(width, height);
		setLocation(xChar, yChar);
//		System.out.println(TAG + gubun + "그려짐");
    	
    }



	
}
