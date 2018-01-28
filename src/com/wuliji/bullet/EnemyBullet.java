/**
 * 
 */
package com.wuliji.bullet;

import java.awt.image.BufferedImage;

import com.wuliji.main.PlaneFrame;
import com.wuliji.plane.Plane;

/**
 * @author 4-2
 *
 */
public class EnemyBullet extends Plane{
	private int speed = 10; //�ӵ��½����ٶ�Ϊ10
	public static BufferedImage enemyBulletImage = PlaneFrame.maps.get("m8.png");//�о��ӵ�ͼƬ
	
	public EnemyBullet(int x, int y){
		image = PlaneFrame.maps.get(enemyBulletImage);
        width = enemyBulletImage.getWidth();
        height = enemyBulletImage.getHeight(); 
        this.x = x;
        this.y = y;
	}
	
	@Override
	public boolean outOfBounds() {
		return y + height > PlaneFrame.HEIGHT;
	}

	@Override
	public void step() {
		y += speed;
	}
	
}
