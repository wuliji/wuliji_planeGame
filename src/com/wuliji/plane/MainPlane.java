/**
 * 
 */
package com.wuliji.plane;

import java.awt.Point;

import com.wuliji.bullet.Bullet;
import com.wuliji.main.PlaneFrame;

/**
 * <p>Title: MainPlane</p>
 * <p>Description:�������ɻ� </p>
 * <p>Company: ������</p> 
 * @author ������
 * @date 2017��6��19�� ����7:44:33
 */
public class MainPlane extends Plane{

		private int life; //����ֵ  
	    private int score; //�÷�  
		static Point planePoint = new Point(280,500);//����λ��
		//�����ӵ�
		int buff;
		//�趨�ĸ���������
		public boolean left, up, right, down;
	      
	    //�����ṩ��ȡ����ֵ�ķ���  
	    public int getLife(){  
	        return life;  
	    }
	    
	    //�����ṩ�޸�����ֵ�ķ���
	    public void setLife(int life){
	    	this.life = life;
	    }
	      
	    //�����ṩ�Ļ�ȡ�÷ֵķ���  
	    public int getScore(){  
	        return score;  
	    }  
	      
	    /** 
	     * Ӣ�ۻ�������޲ι��췽�� 
	     */  
	    public MainPlane(){  
	        image = PlaneFrame.maps.get("plane.png");  
	        height = image.getHeight();  
	        width = image.getWidth();  
	        x = planePoint.x;
	        y = planePoint.y;
	        life = 5;//��ʼ������ֵΪ5  
	        score = 0;  
	    }  
	    
	    /**
		 * ���Ʒɻ�������
		 */	
	    @Override
		public void step() {
			if(up){
				if(y == 0){
					y = 0;
				}else
					y -= 5;
			}
			
			if(down){
				if(y == 600){
					y = 600;
				}else
					y += 5;
			}
			
			if(right){
				if(x == 560){
					x = 560;
				}else
					x += 5;
			}
			
			if(left){
				if(x == 0){
					x = 0;
				}else
					x -= 5;
			}		
			
		}
				
		/**
		 * Խ�紦��
		 */
		@Override
		public boolean outOfBounds() {
			return false;
		}
				
		/**
		 * �ӵ�����
		 */
		public Bullet fire(){
			if(buff == 0){
				buff = 1;
				return new Bullet(x + width/2 - Bullet.bulletImage.getWidth()/2 - 12, y - 60);
			}
			else {
				buff = 0;
				return new Bullet(x + width/2 - Bullet.bulletImage.getWidth()/2 + 12, y - 60);
			}
		}
		
		 /** 
	     * Ӣ�ۻ��Դ��͵�����ײ��ⷽ�� 
	     * @param f ���ܷ�����ײ�ĵ��� 
	     *          �����ǵл�Ҳ�����Ǵ�ɻ� 
	     * @return �Ƿ���ײ 
	     */  
	    public boolean hit(Plane f){  
	        //������ײ��ⷽ��������Ƿ���ײ  
	        boolean r = Plane.boom(this, f);  
	        if(r){ //�����ײ  
	            life--;
	        }  
	        return r;  
	    }
	    
	    /** 
	     * Ӣ�ۻ���÷��������ķ��� 
	     * @param f ��һ�������︸�෽��������ָ��л�����boss�� 
	     */  
	    public void getScore_Award(Plane f){  
	        int lastScore = score;
	    	//���жϵ��˶��������  
	        if(f instanceof EnemyPlane){ //��������ǵл�  
	            //��õл������еķ������ӵ����ַ�����  
	            score += ((EnemyPlane)f).getScore();  
	        }else{ //��������Ǵ�ɻ�  
	        	score += ((BossPlane)f).getScore();  
	            }
	        //ÿ100��ʹ��������һ
	        if(score / 100 - lastScore / 100 >= 1){
	        	life ++;
	        }
	        
	    }  	          	      	     	
}
