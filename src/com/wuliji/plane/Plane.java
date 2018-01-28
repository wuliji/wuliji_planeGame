/**
 * 
 */
package com.wuliji.plane;

import java.awt.image.BufferedImage;


/**
 * <p>Title: Flyer</p>
 * <p>Description:总飞机类的实现 </p>
 * <p>Company: 乌力吉</p> 
 * @author 乌力吉
 * @date 2017年6月19日 下午7:41:19
 */
public abstract class Plane {
	 	public int x; //飞行物左上角x坐标  
	    public int y; //飞行物左上角y坐标  
	    public int height; //飞行物的高度  
	    public int width; //飞行物的宽度  
	    public BufferedImage image; //飞行物的图片 
	      
	    /** 
	     * 要求所有飞行物必须都能移动 
	     * 但移动的方式由子类自己实现 
	     */  
	    public abstract void step();  
	      
	    /** 
	     * 检查越界的方法 
	     * @return 是否越界 
	     */  
	    public abstract boolean outOfBounds();  
	      
	    /** 
	     * 专门检测两个矩形飞行物是否碰撞的工具方法 
	     * 和具体对象无关，所以定义为静态方法 
	     * @param f1 飞行对象1 
	     * @param f2 飞行对象2 
	     * @return 是否碰撞 
	     */  
	    public static boolean boom(Plane f1,Plane f2){  
	        //step1: 求出两个矩形的中心点  
	        int f1x = f1.x + f1.width/2;  
	        int f1y = f1.y + f1.height/2;  
	        int f2x = f2.x + f2.width/2;  
	        int f2y = f2.y + f2.height/2;  
	        //step2: 横向和纵向碰撞检测  
	        boolean H = Math.abs(f1x - f2x) < (f1.width + f2.width)/2;  
	        boolean V = Math.abs(f1y -f2y) < (f1.height + f2.height)/2;  
	        //step3: 必须两个方向同时碰撞  
	        return H&V;
	    }
	    
}
