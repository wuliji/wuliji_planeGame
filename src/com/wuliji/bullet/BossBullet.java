/**
 * 
 */
package com.wuliji.bullet;

import java.awt.image.BufferedImage;

import com.wuliji.main.PlaneFrame;
import com.wuliji.plane.Plane;

/**
 * <p>Title: BossBullet</p>
 * <p>Description:Boss机的子弹 </p>
 * <p>Company: 乌力吉</p> 
 * @author 乌力吉
 * @date 2017年6月22日 下午7:49:26
 */
public class BossBullet extends Plane{
	
	private int speed = 10; //子弹上升的速度为10
	private double degree = Math.random() * Math.PI * 2;//设置子弹偏转角度
	public static BufferedImage bossBulletImage = PlaneFrame.maps.get("m6.png");//Boss子弹图片
	
	/** 
     * 子弹类的带参构造方法 
     * 因为子弹对象创造的位置要根据英雄机的位置决定 
     * 所以子弹对名的x和y要从外界传入 
     * @param x boss机指定子弹创造位置的x坐标 
     * @param y boss机指定子弹创造位置的y坐标 
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
		//子弹的y坐标+子弹的高度< - 500，越界  注:Boss机子弹为中心散射，从y<boss机高度开始发射  
		//子弹的y坐标-子弹的高度>总高度,越界
		//子弹的x坐标+子弹的宽度<0，越界 
		//子弹的x坐标-子弹的宽度>总宽度,越界
        return (y + height) < - 500 || (y - height) > PlaneFrame.HEIGHT || (x + width) < 0 || (x - width) > PlaneFrame.WIDTH; 
		
	}

}
