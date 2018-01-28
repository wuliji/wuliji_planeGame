/**
 * 
 */
package com.wuliji.plane;

import java.awt.image.BufferedImage;

import com.wuliji.main.PlaneFrame;

/**
 * <p>Title: Explode</p>
 * <p>Description: 爆炸显示</p>
 * <p>Company: 乌力吉</p> 
 * @author 乌力吉
 * @date 2017年6月26日 下午7:02:52
 */
public class Explode {
	public int x;//爆炸位置x
	public int y;//爆炸位置y
	public BufferedImage image;//爆炸图片类型
	int count;//爆炸延时计数
	public static BufferedImage[] explodeImage = {PlaneFrame.maps.get("circle1.png"),PlaneFrame.maps.get("circle3.png")};
	
	public Explode(BufferedImage image, int x, int y){
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 删除条件
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
