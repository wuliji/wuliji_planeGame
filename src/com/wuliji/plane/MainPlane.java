/**
 * 
 */
package com.wuliji.plane;

import java.awt.Point;

import com.wuliji.bullet.Bullet;
import com.wuliji.main.PlaneFrame;

/**
 * <p>Title: MainPlane</p>
 * <p>Description:设置主飞机 </p>
 * <p>Company: 乌力吉</p> 
 * @author 乌力吉
 * @date 2017年6月19日 下午7:44:33
 */
public class MainPlane extends Plane{

		private int life; //生命值  
	    private int score; //得分  
		static Point planePoint = new Point(280,500);//主机位置
		//控制子弹
		int buff;
		//设定四个方向属性
		public boolean left, up, right, down;
	      
	    //对外提供读取生命值的方法  
	    public int getLife(){  
	        return life;  
	    }
	    
	    //对外提供修改生命值的方法
	    public void setLife(int life){
	    	this.life = life;
	    }
	      
	    //对外提供的获取得分的方法  
	    public int getScore(){  
	        return score;  
	    }  
	      
	    /** 
	     * 英雄机对象的无参构造方法 
	     */  
	    public MainPlane(){  
	        image = PlaneFrame.maps.get("plane.png");  
	        height = image.getHeight();  
	        width = image.getWidth();  
	        x = planePoint.x;
	        y = planePoint.y;
	        life = 5;//初始化生命值为5  
	        score = 0;  
	    }  
	    
	    /**
		 * 控制飞机的运行
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
		 * 越界处理
		 */
		@Override
		public boolean outOfBounds() {
			return false;
		}
				
		/**
		 * 子弹处理
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
	     * 英雄机自带和敌人碰撞检测方法 
	     * @param f 可能发生碰撞的敌人 
	     *          可能是敌机也可能是大飞机 
	     * @return 是否碰撞 
	     */  
	    public boolean hit(Plane f){  
	        //调用碰撞检测方法，检测是否碰撞  
	        boolean r = Plane.boom(this, f);  
	        if(r){ //如果碰撞  
	            life--;
	        }  
	        return r;  
	    }
	    
	    /** 
	     * 英雄机获得分数或奖励的方法 
	     * @param f 是一个飞行物父类方法，可以指向敌机或者boss机 
	     */  
	    public void getScore_Award(Plane f){  
	        int lastScore = score;
	    	//先判断敌人对象的类型  
	        if(f instanceof EnemyPlane){ //如果敌人是敌机  
	            //获得敌机对象中的分数，加到当现分数上  
	            score += ((EnemyPlane)f).getScore();  
	        }else{ //如果对象是大飞机  
	        	score += ((BossPlane)f).getScore();  
	            }
	        //每100分使生命增加一
	        if(score / 100 - lastScore / 100 >= 1){
	        	life ++;
	        }
	        
	    }  	          	      	     	
}
