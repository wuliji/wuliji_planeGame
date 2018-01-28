/**
 * 
 */
package com.wuliji.bullet;

import java.awt.image.BufferedImage;

import com.wuliji.main.PlaneFrame;
import com.wuliji.plane.Plane;


/**
 * <p>Title: Bullet</p>
 * <p>Description:�ӵ��Ķ��� </p>
 * <p>Company: ������</p> 
 * @author ������
 * @date 2017��6��19�� ����7:54:34
 */
public class Bullet extends Plane{
	 
    private int speed = 10; //�ӵ��������ٶ�Ϊ10
    public static BufferedImage bulletImage = PlaneFrame.maps.get("m4.png");//�����ӵ�ͼƬ
    public static int i = 0;//�����ӵ�����
      
    /** 
     * �ӵ���Ĵ��ι��췽�� 
     * ��Ϊ�ӵ��������λ��Ҫ����Ӣ�ۻ���λ�þ��� 
     * �����ӵ�������x��yҪ����紫�� 
     * @param x Ӣ�ۻ�ָ���ӵ�����λ�õ�x���� 
     * @param y Ӣ�ۻ�ָ���ӵ�����λ�õ�y���� 
     */  
    public Bullet(int x,int y){  
        image = bulletImage;  
        width = image.getWidth();  
        height = image.getHeight();  
        this.x = x;  
        this.y = y;
        
        if(i == 2)
        	i = 0;
        else
        	i ++;
    }  
  
    @Override  
    public void step() {  
        //�ӵ�ÿ�������ƶ�һ��speed����  
        y -= speed;
    }  
  
    @Override  
    public boolean outOfBounds() {  
        //�ӵ���y����+�ӵ��ĸ߶�<0��Խ��  
        return (y + height) < 0;  
    }	
}
