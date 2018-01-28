/**
 * 
 */
package com.wuliji.bullet;

import java.awt.image.BufferedImage;

import com.wuliji.main.PlaneFrame;
import com.wuliji.plane.Plane;

/**
 * <p>Title: BossBullet</p>
 * <p>Description:Boss�����ӵ� </p>
 * <p>Company: ������</p> 
 * @author ������
 * @date 2017��6��22�� ����7:49:26
 */
public class BossBullet extends Plane{
	
	private int speed = 10; //�ӵ��������ٶ�Ϊ10
	private double degree = Math.random() * Math.PI * 2;//�����ӵ�ƫת�Ƕ�
	public static BufferedImage bossBulletImage = PlaneFrame.maps.get("m6.png");//Boss�ӵ�ͼƬ
	
	/** 
     * �ӵ���Ĵ��ι��췽�� 
     * ��Ϊ�ӵ��������λ��Ҫ����Ӣ�ۻ���λ�þ��� 
     * �����ӵ�������x��yҪ����紫�� 
     * @param x boss��ָ���ӵ�����λ�õ�x���� 
     * @param y boss��ָ���ӵ�����λ�õ�y���� 
     */  
	public BossBullet(int x, int y) {
		image = PlaneFrame.maps.get(bossBulletImage);
        width = bossBulletImage.getWidth();
        height = bossBulletImage.getHeight(); 
        this.x = x;
        this.y = y;
	}

	@Override
	public void step() {
		x += speed * Math.cos(degree);
		y += speed * Math.sin(degree);
	}

	@Override
	public boolean outOfBounds() {
		//�ӵ���y����+�ӵ��ĸ߶�< - 500��Խ��  ע:Boss���ӵ�Ϊ����ɢ�䣬��y<boss���߶ȿ�ʼ����  
		//�ӵ���y����-�ӵ��ĸ߶�>�ܸ߶�,Խ��
		//�ӵ���x����+�ӵ��Ŀ��<0��Խ�� 
		//�ӵ���x����-�ӵ��Ŀ��>�ܿ��,Խ��
        return (y + height) < - 500 || (y - height) > PlaneFrame.HEIGHT || (x + width) < 0 || (x - width) > PlaneFrame.WIDTH; 
		
	}

}
