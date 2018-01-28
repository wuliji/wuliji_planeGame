/**
 * 
 */
package com.wuliji.plane;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.wuliji.bullet.EnemyBullet;
import com.wuliji.main.PlaneFrame;

/**
 * <p>Title: EnemyPlane</p>
 * <p>Description: 定义敌方机器</p>
 * <p>Company: 乌力吉</p> 
 * @author 乌力吉
 * @date 2017年6月19日 下午8:01:42
 */
public class EnemyPlane extends Plane{
	private BufferedImage[] enemyImage = {PlaneFrame.maps.get("enemy1.png"), PlaneFrame.maps.get("enemy9.png"), 
			PlaneFrame.maps.get("enemy15.png"), PlaneFrame.maps.get("enemy12.png")};
	private int xspeed = 2; //水平移动的速度为1 
	private int yspeed = 3; //垂直移动的速度为2
    private int score = 5; //敌机包含的奖励分数  
    private static int i = 0;//控制敌军的飞机类型
      
    //对外提供的读取敌机奖励分数的方法  
    public int getScore(){  
        return score;  
    }  
      
    /** 
     * 敌机类的无参构造方法 
     */  
    public EnemyPlane(){
        image = enemyImage[i];
        width = image.getWidth();
        height = image.getHeight();  
        y = -height;
        Random r = new Random();
        x = r.nextInt(PlaneFrame.WIDTH - width);     
        //控制敌军飞机类型
        if(i == 3){
        	i = 0;
        }else
        	i++;
        //控制敌军子弹类型
        
    }  
  
    @Override  
    public void step() {  
    	//每次x移动一个xspeed，y移动一个yspeed  
        x += xspeed;
        y += yspeed;
        //飞机不能起出边界，一旦超出那么xspeed*（-1），相当于反向移动  
        if(x < 0 || x > PlaneFrame.WIDTH - width){  
            xspeed *= -1;  
        }
    }  
  
    @Override  
    public boolean outOfBounds() {  
        //敌机y坐标>游戏界面，越界  
        return y > PlaneFrame.HEIGHT;
    }
    
	/**
	 * 控制敌军子弹的发射
	 */
	public EnemyBullet enemyFire(){		
		return new EnemyBullet(x + width / 2, y + height / 2);
	}
}
