package com.wuliji.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * <p>Title: PlaneFrame</p>
 * <p>Description: �ɻ�����������</p>
 * <p>Company: ������</p> 
 * @author ������
 * @date 2017��6��14�� ����8:10:10
 */
public class PlaneFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//����ȫ�����
	PlanePanel pane;
	//���崰��Ŀ�͸�
	public static final int WIDTH = 640;
	public static final int HEIGHT = 700;
	
	//�ҵ���Դ���ڵ�Ŀ¼
	public static String path = System.getProperty("user.dir") + "\\src\\" + "\\images\\";
	//��ȡ���е�ͼƬ
	public static HashMap<String, BufferedImage> maps = new HashMap<String, BufferedImage>();
	//����ͼƬ��Դ
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
	 * ���캯��
	 */
	public PlaneFrame(){
		//���ñ���
		setTitle("���װ�ɻ���ս");
		//���ô��ڴ�С
		setSize(WIDTH, HEIGHT);
		//���þ���
		setLocationRelativeTo(null);
		//���ô��ڿɼ�
		setVisible(true);
		//���ô��ڹ̶�
		setResizable(false);
		//������
		pane = new PlanePanel();
		setContentPane(pane);
		//���ùرմ���
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//����
		pane.requestFocus();	
	}


	public static void main(String[] args) {
		new PlaneFrame();
	}
	
}
