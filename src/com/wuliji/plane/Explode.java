/**
 * 
 */
package com.wuliji.plane;

import java.awt.image.BufferedImage;

import com.wuliji.main.PlaneFrame;

/**
 * <p>Title: Explode</p>
 * <p>Description: ��ը��ʾ</p>
 * <p>Company: ������</p> 
 * @author ������
 * @date 2017��6��26�� ����7:02:52
 */
public class Explode {
	public int x;//��ըλ��x
	public int y;//��ըλ��y
	public BufferedImage image;//��ըͼƬ����
	int count;//��ը��ʱ����
	public static BufferedImage[] explodeImage = {PlaneFrame.maps.get("circle1.png"),PlaneFrame.maps.get("circle3.png")};
	
	public Explode(BufferedImage image, int x, int y){
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * ɾ������
	 */
	public boolean remove(){
		if(count == 15){
			return true;
		}
		else {
			count ++;
		}
		return false;
	}
}
