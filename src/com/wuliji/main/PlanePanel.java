/**
 * 
 */
package com.wuliji.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import com.wuliji.bullet.BossBullet;
import com.wuliji.bullet.Bullet;
import com.wuliji.bullet.EnemyBullet;
import com.wuliji.plane.BossPlane;
import com.wuliji.plane.EnemyPlane;
import com.wuliji.plane.Explode;
import com.wuliji.plane.MainPlane;
import com.wuliji.plane.Plane;




/**
 * <p>Title: PlanePanel</p>
 * <p>Description:�ɻ��������� </p>
 * <p>Company: ������</p> 
 * @author ������
 * @date 2017��6��20�� ����4:04:58
 */
//����һ�����
class PlanePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//��������
	MainPlane mainPlane;
	//���ñ���ͼƬ��λ��
	Point bgPoint;
	//���õо��ɻ�
	Vector<EnemyPlane> enemysPlane;
	//����boss�ɻ�
	Vector<BossPlane> bossPlane;
	//���������ӵ�
	Vector<Bullet> planeBullet;
	//���õо��ӵ�
	Vector<EnemyBullet> enemyBullet;
	//����Boss�ӵ�
	Vector<BossBullet> bossBullet;
	//���ñ�ը
	Vector<Explode> explode;
	//������Ϸ״̬����ǰ״̬������Ĭ��Ϊ��ʼ״̬  
    public static int state ;
    //������Ϸ״̬�ı�ѡ�����  
    public static final int START = 0;  
    public static final int RUNNING = 1;  
    public static final int GAME_OVER = 2;

    BgThread bgThread;//�����߳�
    BulletThread bulletThread;//�ӵ��߳�
    EnemyThread enemyThread;//�о��߳�
    AddBulletThread addBullet;//���ӵ��߳�
    BossThread bossThread;//boss�߳�
    TestThread testThread;//�����߳�
    AddBossBullet addBossBullet;//boss�ӵ��߳�
    AddEnemyBullet addEnemyBullet;
	
	/**
	 * ���������д����
	 */
	public PlanePanel() {		
				//���Ӽ�����
				KeyAdapter l = new KeyAdapter(){
					 @Override
						public void keyPressed(KeyEvent e) {
						if(state == RUNNING){
						 switch(e.getKeyCode()){
							case KeyEvent.VK_LEFT: 
								mainPlane.left = true;
								break;
								
							case KeyEvent.VK_UP: 
								mainPlane.up = true;
								break;
							
							case KeyEvent.VK_DOWN: 
								mainPlane.down = true;
								break;
								
							case KeyEvent.VK_RIGHT: 
								mainPlane.right = true;
								break;					
						 	}
						}else if(state == START){
							if(e.getKeyCode() == KeyEvent.VK_ENTER){
								state = RUNNING;
								start();
							}
						}
						else {
							if(e.getKeyCode() == KeyEvent.VK_ENTER){
								state = START;
							}
						}					
					 }
						
						@Override
						public void keyReleased(KeyEvent e) {
						if(state == RUNNING || state == GAME_OVER){	
							switch(e.getKeyCode()){
							case KeyEvent.VK_LEFT: 
								mainPlane.left = false;
								break;
								
							case KeyEvent.VK_UP: 
								mainPlane.up = false;
								break;
							
							case KeyEvent.VK_DOWN: 
								mainPlane.down = false;
								break;
								
							case KeyEvent.VK_RIGHT: 
								mainPlane.right = false;
								break;
							}
						}
					}
				 };
				 
					//��Ӽ����¼�
					addKeyListener(l);					
			        
	}	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//����һ������,ʵ��˫����
		BufferedImage image = new BufferedImage(640, 700, BufferedImage.TYPE_INT_RGB);
		Graphics gs = image.getGraphics();

		if(state == START){
			paintStart(gs);//���ƿ�ʼ����
		}else if(state == RUNNING){
			paintGame(gs);//������Ϸ����
		}else if(state == GAME_OVER){
			paintOver(gs);//���ƽ�������
		}
		g.drawImage(image, 0, 0, this);//���»�������ֹ�����׻�
		
	}
	
	//����߳̿���ͼƬ�ƶ�
	class BgThread implements Runnable {
		private boolean flag = true;//��ӽ����ź�
		public void run() {
			while (flag) {
				if(bgPoint.y == 700){
					bgPoint.y = -2180;
				}							
				bgPoint.y += 5;
				try {
					Thread.sleep(30);//ÿ��30��������һ��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();//���»���
			}			
		}
		
		//�����߳�
		public void stop(){
			flag = false;
		}
	}
	
	//����߳̿����ӵ��ƶ�
	class BulletThread implements Runnable{
		private boolean flag = true;//��ӽ����ź�
		public void run() {	
			while (flag) {
				move();
			}
		}
		public synchronized void move(){
			//���������ӵ��ƶ�
			for(int i =0;i < planeBullet.size(); i++){
				planeBullet.get(i).step();
			}
			//���Ƶо��ӵ��ƶ�
			for(int i = 0;i < enemyBullet.size();i++){
				enemyBullet.get(i).step();
			}
			//����Boss�ӵ��ƶ�
			for(int i =0;i < bossBullet.size(); i++){
				bossBullet.get(i).step();
			}			
			try {
				Thread.sleep(30);//30����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//���»���
		}
		
		//�����߳�
		public void stop(){
			flag = false;
		}
	}
	
	//����߳̿��������ӵ��ķ���
	class AddBulletThread implements Runnable{
		private boolean flag = true;//��ӽ����ź�
		public void run() {
			while(flag){
				shoot();
			}			
		}
		
		public synchronized void shoot(){
			planeBullet.add(mainPlane.fire());
			try {
				Thread.sleep(200);//200����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//���»���
		}
		
		//�����߳�
		public void stop(){
			flag = false;
		}
	}
	
	//�߳̿��Ƶо��ӵ����
	class AddEnemyBullet implements Runnable{
		private boolean flag = true;//��ӽ����ź�
		public void run() {
			while(flag){
				shoot();
			}			
		}
		
		public synchronized void shoot(){
			for(int i = 0;i < enemysPlane.size(); i++){
				enemyBullet.add(enemysPlane.get(i).enemyFire());
			}
			try {
				Thread.sleep(800);//800����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//���»���
		}
		
		//�����߳�
		public void stop(){
			flag = false;
		}
		
	}
	
	//�߳̿���boss�ӵ����
	class AddBossBullet implements Runnable{
		private boolean flag = true;//��ӽ����ź�
		public void run() {
			while(flag){
				shoot();
			}			
		}
		
		public synchronized void shoot(){
			for(int i = 0;i < bossPlane.size(); i++){
				bossBullet.addAll(bossPlane.get(i).enemyFire());			
			}
			try {
				Thread.sleep(3000);//3000����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//���»���
		}
		
		//�����߳�
		public void stop(){
			flag = false;
		}
	}
		
	//����߳����ɵз��ɻ�
	class EnemyThread implements Runnable{
		private boolean flag = true;//��ӽ����ź�
		public void run() {
			while(flag){
				enemy();
			}			
		}
		
		public synchronized void enemy(){
			EnemyPlane f = new EnemyPlane();
			//���µ��˷�������ĩβ  
			enemysPlane.add(f);
			try {
				Thread.sleep(650);//650����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//���»���		
		}
		
		//�����߳�
		public void stop(){
			flag = false;
		}
	}
	
	//����߳�����Boss�ɻ�
	class BossThread implements Runnable{
		private boolean flag = true;//��ӽ����ź�
		public void run() {
			while(flag){
				boss();
			}
		}
		
		public synchronized void boss(){
			BossPlane f = new BossPlane();
			//��boss��������ĩβ  
            bossPlane.add(f);
            try {
				Thread.sleep(16000);//16000����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//���»���	
		}
		
		//�����߳�
		public void stop(){
			flag = false;
		}		
	}
	
	//����̼߳��
	class TestThread implements Runnable{
		private boolean flag = true;//��ӽ����ź�
		public void run() {
			while(flag){
				test();	
			}
		}
		
		//�̰߳�ȫ
		public synchronized void test(){
			//����ÿһ�����󣬵��ö����step�������ƶ�һ�ζ����λ��  
        	for(int i = 0;i < enemysPlane.size();i++){  
        		enemysPlane.get(i).step();    	          	
            }       	
            for(int i = 0;i < bossPlane.size(); i++){
            	bossPlane.get(i).step();
            }
            mainPlane.step();
            
            //��ײ���
            hit();
            		                    
            //����ӵ��͵��˵���ײ���  
            boom();
            
            //���Խ����  
            outOfBounds();
            
            try {
				Thread.sleep(10);//10����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//���»���	
		}

		//�����߳�
		public void stop(){
			flag = false;
		}		
	}
	
	/**
	 * ���ƿ�ʼ����
	 */
	public void paintStart(Graphics g){
		g.drawImage(PlaneFrame.maps.get("startbg1.jpg"), 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.drawString("�밴Enter����ʼ��Ϸ!", 150, 600);
	}
	
	/**
	 * ������Ϸ����
	 */
	public void paintGame(Graphics g){
		//������
		g.drawImage(PlaneFrame.maps.get("background2a.bmp"), bgPoint.x, bgPoint.y, this);
		g.drawImage(PlaneFrame.maps.get("background2a.bmp"), bgPoint.x, bgPoint.y - 2880, this);
		paintScore_Life(g);//��������Ϣ
		paintEnemys(g);//���о�
		paintMainPlane(g);//�����ɻ�
		paintMainBullet(g);//�������ӵ�
		paintBossBullet(g);//��boss�ӵ�
		paintEnemyBullet(g);//���о��ӵ�
		paintExplode(g);//����ըЧ��
		
		}
	
	/**
	 * ������Ϸ��������
	 */
	public void paintOver(Graphics g){

		g.drawImage(PlaneFrame.maps.get("background2b.bmp"), 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.drawString("��Ϸ����!", 220, 300);	  
	    g.drawString("���յ÷�Ϊ: " + mainPlane.getScore(), 200, 400);  
		g.drawString("��Enter��������Ϸ!", 150, 500);
		stop();//�����߳�
	}
	
	/**
	 * ��ʼ�����ݣ������߳�
	 */
	public void start(){
		//��Ϸ��ʼ��
		bgPoint= new Point(0, -2180);
		mainPlane = new MainPlane();
		enemysPlane = new Vector<EnemyPlane>();
		bossPlane = new Vector<BossPlane>();
		planeBullet = new Vector<Bullet>();
		bossBullet = new Vector<BossBullet>();
		enemyBullet = new Vector<EnemyBullet>();
		explode = new Vector<Explode>();

		bgThread = new BgThread();
		new Thread(bgThread).start();//���������߳�
		
		bossThread = new BossThread();
		new Thread(bossThread).start();//����boss�߳�
		
		addBullet = new AddBulletThread();
		new Thread(addBullet).start();//�������ӵ��߳�
		
		addEnemyBullet = new AddEnemyBullet();
		new Thread(addEnemyBullet).start();//�����о����ӵ��߳�
		
		addBossBullet = new AddBossBullet();
		new Thread(addBossBullet).start();//����boss���ӵ��߳�
		
		bulletThread = new BulletThread();
		new Thread(bulletThread).start();//�����ӵ��ƶ��߳�
		
		enemyThread = new EnemyThread();								
		new Thread(enemyThread).start();//�����о��߳�
		
		testThread = new TestThread();
		new Thread(testThread).start();//���������߳�
										
	}
	
	/**
	 * ���������߳�
	 */
	public void stop(){
		bgThread.stop();
		bulletThread.stop();
		enemyThread.stop();
		addBullet.stop();
		bossThread.stop();
		testThread.stop();
		addBossBullet.stop();
		addEnemyBullet.stop();
	}
	
	/**
	 * ���Ʒ���������
	 */
	public void paintScore_Life(Graphics g){  
	    int x = 10; //���������Ͻǵ�x����  
	    int y = 15; //���������Ͻǵ�y����  
	    Font font = new Font(Font.SANS_SERIF,Font.BOLD,14);  
	    g.setFont(font); //��������Ļ��ʶ���  
	    //���Ƶ�һ��:����  
	    g.drawString("SCORE: " + mainPlane.getScore(), x, y);  
	    //���Ƶڶ��У�����ֵ��y��������20����λ  
	    y += 20;  
	    g.drawString("LIFE: " + mainPlane.getLife(), x, y);  
	}
	
	/**
	 * �������ɻ�
	 */
	public void paintMainPlane(Graphics g){
		g.drawImage(mainPlane.image, mainPlane.x, mainPlane.y, null);
	}
	
	/**
	 * ���������ӵ�
	 */
	public void paintMainBullet(Graphics g){
		for(int i = 0; i < planeBullet.size(); i++){
			g.drawImage(Bullet.bulletImage, 
					planeBullet.get(i).x, planeBullet.get(i).y, this);
			}
		}
	
	/**
	 * ���Ƶо��ӵ�
	 */
	public void paintEnemyBullet(Graphics g){
		for(int i = 0;i < enemyBullet.size(); i++){
			g.drawImage(EnemyBullet.enemyBulletImage, enemyBullet.get(i).x, enemyBullet.get(i).y, this);
		}
	}
		
	/**
	 * ����Boss���ӵ�
	 */
	public void paintBossBullet(Graphics g){
		for(int i = 0; i < bossBullet.size(); i++){
			g.drawImage(BossBullet.bossBulletImage, 
					bossBullet.get(i).x, bossBullet.get(i).y, this);	
			}
		}
	
	/**
	 * ���Ʊ�ըЧ�� 
	 */
	public void paintExplode(Graphics g){
		for(int i = 0;i < explode.size(); i++){
			g.drawImage(explode.get(i).image, explode.get(i).x, explode.get(i).y, null);
		}
		//���Ʊ�ըЧ��ʱ��
		for(int i = 0; i < explode.size(); i++){
			if(explode.get(i).remove()){
				explode.remove(i);
			}
		}
	}
	
	/** 
     * �����������飬�����������е��˵ķ���  
     */  
    public void paintEnemys(Graphics g){  
    	for(int i = 0;i < enemysPlane.size();i++){  
            g.drawImage(enemysPlane.get(i).image, enemysPlane.get(i).x, enemysPlane.get(i).y, null);
    	}
    	for(int i = 0;i < bossPlane.size(); i++){
    		g.drawImage(bossPlane.get(i).image, bossPlane.get(i).x, bossPlane.get(i).y, null);
    	}
    } 
       
    /** 
     * �����������飬�ж�Ӣ�ۻ���ÿ�������Ƿ���ײ 
     */  
    public void hit(){
    	//�жϵо�������
    	for(int i = 0;i < enemysPlane.size();i++){  
            if(mainPlane.hit(enemysPlane.get(i))){  
                enemysPlane.remove(i);
            }  
        }
    	//�ж�boss������
        for(int i = 0;i < bossPlane.size();i++){
        	if(mainPlane.hit(bossPlane.get(i))){  
                bossPlane.remove(i);
            }  
        }
        if(mainPlane.getLife() <= 0){ //���Ӣ�ۻ�����ֵС�ڵ���0����Ϸ����   
            state = GAME_OVER;  
        }           
    } 
    
    /** 
     * �����ӵ�����͵������飬������ײ��� 
     * һ��������ײ���ӵ��͵��˶�����һ�� 
     */  
    public void boom(){  
        //��������ӵ�����˵���ײ
    	for(int i = 0;i < planeBullet.size();i++){  
            for(int j = 0;j < enemysPlane.size();j++){  
                if(Plane.boom(planeBullet.get(i), enemysPlane.get(j))){              	         	
            		//ΪӢ�ۻ���÷����ͽ���  
                    mainPlane.getScore_Award(enemysPlane.get(j));                                                
                    //���Ʊ�ը
                    explode.add(new Explode(Explode.explodeImage[0], 
                    		enemysPlane.get(j).x - enemysPlane.get(j).width / 2, enemysPlane.get(j).y));
                    //�ӵ���������ɾ�������еĵл� 
                    enemysPlane.remove(j);
                    //���ӵ�������ɾ�����ел����ӵ�  
                    planeBullet.remove(i);
                    i--; //�ڷ���һ����ײ���ӵ���Ҫ��һ��Ԫ�أ����¼�⵱ǰλ��  
                    break; //ֻҪ������ײ���˳���ǰ���������ѭ��                  
                } 
            }      
        }
    	
    	//���о��ӵ���������ײ
    	for(int i = 0; i < enemyBullet.size(); i++){
    		if(Plane.boom(enemyBullet.get(i), mainPlane)){
    			enemyBullet.remove(i);//ɾ����ײ���ӵ�
    			mainPlane.setLife(mainPlane.getLife() - 1);
    			if(mainPlane.getLife() <= 0){ //���Ӣ�ۻ�����ֵС�ڵ���0����Ϸ����   
    		           state = GAME_OVER;  
    		    	}
    			}
    		}
    	
    	//���boss����������ײ
    	for(int i = 0;i < planeBullet.size();i++){
    		for(int n = 0;n < bossPlane.size();n++){
            	if(Plane.boom(planeBullet.get(i), bossPlane.get(n))){
	            		if(bossPlane.get(n).getLife() >= 0){
	            			bossPlane.get(n).life -= 1;
	            			//���ӵ�������ɾ�����ел����ӵ�  
	                        planeBullet.remove(i);
	                        i--; //�ڷ���һ����ײ���ӵ���Ҫ��һ��Ԫ�أ����¼�⵱ǰλ��  
	                        break; //ֻҪ������ײ���˳���ǰ���������ѭ�� 
	            		}
	            		else
	            		{   
	            			//ΪӢ�ۻ���÷����ͽ���  
	                        mainPlane.getScore_Award(bossPlane.get(n));                                                
	                        //���Ʊ�ը
	                        explode.add(new Explode(Explode.explodeImage[1], bossPlane.get(n).x, bossPlane.get(n).y));
	                        //�ӵ���������ɾ�������еĵл�    
	                        bossPlane.remove(n);
	                        //���ӵ�������ɾ�����ел����ӵ�  
	                        planeBullet.remove(i);
	                        i--; //�ڷ���һ����ײ���ӵ���Ҫ��һ��Ԫ�أ����¼�⵱ǰλ��  
	                        break; //ֻҪ������ײ���˳���ǰ���������ѭ�� 
	            		}
            		}
            	} 		
    		}
    	
    	//���Boss���ӵ���������ײ
    	for(int i = 0; i < bossBullet.size(); i++){
    		if(Plane.boom(bossBullet.get(i), mainPlane)){
    			bossBullet.remove(i);//ɾ����ײ���ӵ�
    			mainPlane.setLife(mainPlane.getLife() - 1);
    			if(mainPlane.getLife() <= 0){ //���Ӣ�ۻ�����ֵС�ڵ���0����Ϸ����   
    		           state = GAME_OVER;  
    		    	}
    			}
    		}
		}
      
    /** 
     * ������з������Ƿ�Խ�� 
     */  
    public void outOfBounds(){  
        //������е����Ƿ�Խ��  
        for(int i = 0;i < enemysPlane.size();i++){  
            if(enemysPlane.get(i).outOfBounds()){ 
                enemysPlane.remove(i);            
            } 
        }
                
        //��������ӵ��Ƿ�Խ��  
        for(int i = 0;i < planeBullet.size();i++){  
            if(planeBullet.get(i).outOfBounds()){  
                planeBullet.remove(i);
            }  
        }
        
        //���о��ӵ��Ƿ�Խ��
        for(int i = 0;i < enemyBullet.size();i ++){
        	if(enemyBullet.get(i).outOfBounds()){
        		enemyBullet.remove(i);
        	}
        }
        
        //���boss���ӵ��Ƿ�Խ��
        for(int i = 0; i < bossBullet.size(); i++){
        	if(bossBullet.get(i).outOfBounds()){
        		bossBullet.remove(i);
        	}
        }
    }  

}

