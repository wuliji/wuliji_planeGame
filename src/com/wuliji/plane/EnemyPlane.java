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
 * <p>Description: ����з�����</p>
 * <p>Company: ������</p> 
 * @author ������
 * @date 2017��6��19�� ����8:01:42
 */
public class EnemyPlane extends Plane{
	private BufferedImage[] enemyImage = {PlaneFrame.maps.get("enemy1.png"), PlaneFrame.maps.get("enemy9.png"), 
			PlaneFrame.maps.get("enemy15.png"), PlaneFrame.maps.get("enemy12.png")};
	private int xspeed = 2; //ˮƽ�ƶ����ٶ�Ϊ1 
	private int yspeed = 3; //��ֱ�ƶ����ٶ�Ϊ2
    private int score = 5; //�л������Ľ�������  
    private static int i = 0;//���Ƶо��ķɻ�����
      
    //�����ṩ�Ķ�ȡ�л����������ķ���  
    public int getScore(){  
        return score;  
    }  
      
    /** 
     * �л�����޲ι��췽�� 
     */  
    public EnemyPlane(){
        image = enemyImage[i];
        width = image.getWidth();
        height = image.getHeight();  
        y = -height;
        Random r = new Random();
        x = r.nextInt(PlaneFrame.WIDTH - width);     
        //���Ƶо��ɻ�����
        if(i == 3){
        	i = 0;
        }else
        	i++;
        //���Ƶо��ӵ�����
        
    }  
  
    @Override  
    public void step() {  
    	//ÿ��x�ƶ�һ��xspeed��y�ƶ�һ��yspeed  
        x += xspeed;
        y += yspeed;
        //�ɻ���������߽磬һ��������ôxspeed*��-1�����൱�ڷ����ƶ�  
        if(x < 0 || x > PlaneFrame.WIDTH - width){  
            xspeed *= -1;  
        }
    }  
  
    @Override  
    public boolean outOfBounds() {  
        //�л�y����>��Ϸ���棬Խ��  
        return y > PlaneFrame.HEIGHT;
    }
    
	/**
	 * ���Ƶо��ӵ��ķ���
	 */
	public EnemyBullet enemyFire(){		
		return new EnemyBullet(x + width / 2, y + height / 2);
	}
}
