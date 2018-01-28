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
 * <p>Description: ����Boss�ɻ�</p>
 * <p>Company: ������</p> 
 * @author ������
 * @date 2017��6��19�� ����7:57:58
 */
public class BossPlane extends Plane{ 
	private String[] enemyImage = {"boss1.png", "boss2.png"};//��ȡBoss��ͼƬ
	public BufferedImage bulletImage = PlaneFrame.maps.get("m6.png");
    private double xspeed = 1; //ˮƽ�ƶ����ٶ�Ϊ1  
    private double yspeed = 1; //��ֱ�ƶ����ٶ�Ϊ0.5
    private static int i = 0;//����BOSS�ķɻ�����
    private int score = 50;//Boss�ķ���
    public int life = 20;//Boss������ֵ
    
    
    /** 
     * boss���޲ι��췽�� 
     */  
    public BossPlane(){
    	
        //step1: ���������л�ȡ��ɻ�ͼƬ�ľ�̬��������bigplane  
        image = PlaneFrame.maps.get(enemyImage[i]);  
        //step2: ʹ��ͼƬ������ö�����  
        width = image.getWidth();  
        height= image.getHeight();  
        //step3: ���ô�ɻ���ʼ����ĸ߶�  
        y = - height;
        x = PlaneFrame.WIDTH / 2 - width / 2;
        
      //����BOSS�ɻ�����
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
        //ÿ��x�ƶ�һ��xspeed��y�ƶ�һ��yspeed  
        x += xspeed;
        y += yspeed;
        //��ɻ���������߽磬һ��������ôxspeed*��-1�����൱�ڷ����ƶ�  
        if(x < 0 || x > PlaneFrame.WIDTH - width){  
            xspeed *= -1;  
        }
        //��������䵽һ���̶�ʱ�������һζ�
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
