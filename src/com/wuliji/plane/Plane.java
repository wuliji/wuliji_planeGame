/**
 * 
 */
package com.wuliji.plane;

import java.awt.image.BufferedImage;


/**
 * <p>Title: Flyer</p>
 * <p>Description:�ܷɻ����ʵ�� </p>
 * <p>Company: ������</p> 
 * @author ������
 * @date 2017��6��19�� ����7:41:19
 */
public abstract class Plane {
	 	public int x; //���������Ͻ�x����  
	    public int y; //���������Ͻ�y����  
	    public int height; //������ĸ߶�  
	    public int width; //������Ŀ��  
	    public BufferedImage image; //�������ͼƬ 
	      
	    /** 
	     * Ҫ�����з�������붼���ƶ� 
	     * ���ƶ��ķ�ʽ�������Լ�ʵ�� 
	     */  
	    public abstract void step();  
	      
	    /** 
	     * ���Խ��ķ��� 
	     * @return �Ƿ�Խ�� 
	     */  
	    public abstract boolean outOfBounds();  
	      
	    /** 
	     * ר�ż���������η������Ƿ���ײ�Ĺ��߷��� 
	     * �;�������޹أ����Զ���Ϊ��̬���� 
	     * @param f1 ���ж���1 
	     * @param f2 ���ж���2 
	     * @return �Ƿ���ײ 
	     */  
	    public static boolean boom(Plane f1,Plane f2){  
	        //step1: ����������ε����ĵ�  
	        int f1x = f1.x + f1.width/2;  
	        int f1y = f1.y + f1.height/2;  
	        int f2x = f2.x + f2.width/2;  
	        int f2y = f2.y + f2.height/2;  
	        //step2: �����������ײ���  
	        boolean H = Math.abs(f1x - f2x) < (f1.width + f2.width)/2;  
	        boolean V = Math.abs(f1y -f2y) < (f1.height + f2.height)/2;  
	        //step3: ������������ͬʱ��ײ  
	        return H&V;
	    }
	    
}
