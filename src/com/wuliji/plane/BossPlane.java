/**
 * 
 */
package com.wuliji.plane;

import java.awt.image.BufferedImage;
import java.util.Vector;

import com.wuliji.bullet.BossBullet;
import com.wuliji.main.PlaneFrame;


/**
 * <p>Title: BossPlane</p>
 * <p>Description: 定义Boss飞机</p>
 * <p>Company: 乌力吉</p> 
 * @author 乌力吉
 * @date 2017年6月19日 下午7:57:58
 */
public class BossPlane extends Plane{ 
	private String[] enemyImage = {"boss1.png", "boss2.png"};//获取Boss的图片
	public BufferedImage bulletImage = PlaneFrame.maps.get("m6.png");
    private double xspeed = 1; //水平移动的速度为1  
    private double yspeed = 1; //垂直移动的速度为0.5
    private static int i = 0;//控制BOSS的飞机类型
    private int score = 50;//Boss的分数
    public int life = 20;//Boss的生命值
    
    
    /** 
     * boss的无参构造方法 
     */  
    public BossPlane(){
    	
        //step1: 从主程序中获取大飞机图片的静态变量——bigplane  
        image = PlaneFrame.maps.get(enemyImage[i]);  
        //step2: 使用图片宽高设置对象宽高  
        width = image.getWidth();  
        height= image.getHeight();  
        //step3: 设置大飞机开始下落的高度  
        y = - height;
        x = PlaneFrame.WIDTH / 2 - width / 2;
        
      //控制BOSS飞机类型
        if(i == 1){
        	i = 0;
        }else
        	i++;       
    }
     
    public int getLife(){
    	return life;
    }
      
    public int getScore(){
    	return score;
    }
  
    @Override  
    public void step() {  
        //每次x移动一个xspeed，y移动一个yspeed  
        x += xspeed;
        y += yspeed;
        //大飞机不能起出边界，一旦超出那么xspeed*（-1），相当于反向移动  
        if(x < 0 || x > PlaneFrame.WIDTH - width){  
            xspeed *= -1;  
        }
        //当大飞下落到一定程度时，就左右晃动
        if(y > height / 4 ){
        	y = height / 4;
        }
    }  
  
    @Override  
    public boolean outOfBounds() {            
        return false;
    }

	public Vector<BossBullet> enemyFire() {
		Vector<BossBullet> vector = new Vector<BossBullet>();
		for(int i = 0; i < 50; i++){
			BossBullet b = new BossBullet(x + width/2 - bulletImage.getWidth()/2, y + height);
			vector.add(b);
		}
		return vector;
	}  
}
