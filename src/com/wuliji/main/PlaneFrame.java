package com.wuliji.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * <p>Title: PlaneFrame</p>
 * <p>Description: 飞机程序主窗体</p>
 * <p>Company: 乌力吉</p> 
 * @author 乌力吉
 * @date 2017年6月14日 下午8:10:10
 */
public class PlaneFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//定义全局面板
	PlanePanel pane;
	//定义窗体的宽和高
	public static final int WIDTH = 640;
	public static final int HEIGHT = 700;
	
	//找到资源所在的目录
	public static String path = System.getProperty("user.dir") + "\\src\\" + "\\images\\";
	//获取所有的图片
	public static HashMap<String, BufferedImage> maps = new HashMap<String, BufferedImage>();
	//加载图片资源
	static {
		File[] images = new File(path).listFiles();
			try {
				for(File image : images){
					maps.put(image.getName(), ImageIO.read(image));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
  	   
	/**
	 * 构造函数
	 */
	public PlaneFrame(){
		//设置标题
		setTitle("简易版飞机大战");
		//设置窗口大小
		setSize(WIDTH, HEIGHT);
		//设置居中
		setLocationRelativeTo(null);
		//设置窗口可见
		setVisible(true);
		//设置窗口固定
		setResizable(false);
		//添加面板
		pane = new PlanePanel();
		setContentPane(pane);
		//设置关闭窗口
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//启动
		pane.requestFocus();	
	}


	public static void main(String[] args) {
		new PlaneFrame();
	}
	
}
