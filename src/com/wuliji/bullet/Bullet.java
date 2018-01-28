/**
 * 
 */
package com.wuliji.bullet;

import java.awt.image.BufferedImage;

import com.wuliji.main.PlaneFrame;
import com.wuliji.plane.Plane;


/**
 * <p>Title: Bullet</p>
 * <p>Description:子弹的定义 </p>
 * <p>Company: 乌力吉</p> 
 * @author 乌力吉
 * @date 2017年6月19日 下午7:54:34
 */
public class Bullet extends Plane{
	 
    private int speed = 10; //子弹上升的速度为10
    public static BufferedImage bulletImage = PlaneFrame.maps.get("m4.png");//主机子弹图片
    public static int i = 0;//控制子弹类型
      
    /** 
     * 子弹类的带参构造方法 
     * 因为子弹对象创造的位置要根据英雄机的位置决定 
     * 所以子弹对名的x和y要从外界传入 
     * @param x 英雄机指定子弹创造位置的x坐标 
     * @param y 英雄机指定子弹创造位置的y坐标 
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
        //子弹每次向上移动一个speed长度  
        y -= speed;
    }  
  
    @Override  
    public boolean outOfBounds() {  
        //子弹的y坐标+子弹的高度<0，越界  
        return (y + height) < 0;  
    }	
}
