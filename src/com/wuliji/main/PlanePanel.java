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
 * <p>Description:飞机界面的面板 </p>
 * <p>Company: 乌力吉</p> 
 * @author 乌力吉
 * @date 2017年6月20日 下午4:04:58
 */
//创建一个面板
class PlanePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//设置主机
	MainPlane mainPlane;
	//设置背景图片的位置
	Point bgPoint;
	//设置敌军飞机
	Vector<EnemyPlane> enemysPlane;
	//设置boss飞机
	Vector<BossPlane> bossPlane;
	//设置主机子弹
	Vector<Bullet> planeBullet;
	//设置敌军子弹
	Vector<EnemyBullet> enemyBullet;
	//设置Boss子弹
	Vector<BossBullet> bossBullet;
	//设置爆炸
	Vector<Explode> explode;
	//定义游戏状态：当前状态变量：默认为开始状态  
    public static int state ;
    //定义游戏状态的备选项常量：  
    public static final int START = 0;  
    public static final int RUNNING = 1;  
    public static final int GAME_OVER = 2;

    BgThread bgThread;//背景线程
    BulletThread bulletThread;//子弹线程
    EnemyThread enemyThread;//敌军线程
    AddBulletThread addBullet;//加子弹线程
    BossThread bossThread;//boss线程
    TestThread testThread;//测试线程
    AddBossBullet addBossBullet;//boss子弹线程
    AddEnemyBullet addEnemyBullet;
	
	/**
	 * 设置主运行代码块
	 */
	public PlanePanel() {		
				//增加监听器
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
				 
					//添加监听事件
					addKeyListener(l);					
			        
	}	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//创建一个画板,实现双缓冲
		BufferedImage image = new BufferedImage(640, 700, BufferedImage.TYPE_INT_RGB);
		Graphics gs = image.getGraphics();

		if(state == START){
			paintStart(gs);//绘制开始界面
		}else if(state == RUNNING){
			paintGame(gs);//绘制游戏界面
		}else if(state == GAME_OVER){
			paintOver(gs);//绘制结束界面
		}
		g.drawImage(image, 0, 0, this);//重新画背景防止背景白化
		
	}
	
	//添加线程控制图片移动
	class BgThread implements Runnable {
		private boolean flag = true;//添加结束信号
		public void run() {
			while (flag) {
				if(bgPoint.y == 700){
					bgPoint.y = -2180;
				}							
				bgPoint.y += 5;
				try {
					Thread.sleep(30);//每隔30毫秒休眠一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();//更新画板
			}			
		}
		
		//结束线程
		public void stop(){
			flag = false;
		}
	}
	
	//添加线程控制子弹移动
	class BulletThread implements Runnable{
		private boolean flag = true;//添加结束信号
		public void run() {	
			while (flag) {
				move();
			}
		}
		public synchronized void move(){
			//控制主机子弹移动
			for(int i =0;i < planeBullet.size(); i++){
				planeBullet.get(i).step();
			}
			//控制敌军子弹移动
			for(int i = 0;i < enemyBullet.size();i++){
				enemyBullet.get(i).step();
			}
			//控制Boss子弹移动
			for(int i =0;i < bossBullet.size(); i++){
				bossBullet.get(i).step();
			}			
			try {
				Thread.sleep(30);//30毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//更新画板
		}
		
		//结束线程
		public void stop(){
			flag = false;
		}
	}
	
	//添加线程控制主机子弹的发射
	class AddBulletThread implements Runnable{
		private boolean flag = true;//添加结束信号
		public void run() {
			while(flag){
				shoot();
			}			
		}
		
		public synchronized void shoot(){
			planeBullet.add(mainPlane.fire());
			try {
				Thread.sleep(200);//200毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//更新画板
		}
		
		//结束线程
		public void stop(){
			flag = false;
		}
	}
	
	//线程控制敌军子弹添加
	class AddEnemyBullet implements Runnable{
		private boolean flag = true;//添加结束信号
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
				Thread.sleep(800);//800毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//更新画板
		}
		
		//结束线程
		public void stop(){
			flag = false;
		}
		
	}
	
	//线程控制boss子弹添加
	class AddBossBullet implements Runnable{
		private boolean flag = true;//添加结束信号
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
				Thread.sleep(3000);//3000毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//更新画板
		}
		
		//结束线程
		public void stop(){
			flag = false;
		}
	}
		
	//添加线程生成敌方飞机
	class EnemyThread implements Runnable{
		private boolean flag = true;//添加结束信号
		public void run() {
			while(flag){
				enemy();
			}			
		}
		
		public synchronized void enemy(){
			EnemyPlane f = new EnemyPlane();
			//将新敌人放入数组末尾  
			enemysPlane.add(f);
			try {
				Thread.sleep(650);//650毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//更新画板		
		}
		
		//结束线程
		public void stop(){
			flag = false;
		}
	}
	
	//添加线程生成Boss飞机
	class BossThread implements Runnable{
		private boolean flag = true;//添加结束信号
		public void run() {
			while(flag){
				boss();
			}
		}
		
		public synchronized void boss(){
			BossPlane f = new BossPlane();
			//将boss放入数组末尾  
            bossPlane.add(f);
            try {
				Thread.sleep(16000);//16000毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//更新画板	
		}
		
		//结束线程
		public void stop(){
			flag = false;
		}		
	}
	
	//添加线程检测
	class TestThread implements Runnable{
		private boolean flag = true;//添加结束信号
		public void run() {
			while(flag){
				test();	
			}
		}
		
		//线程安全
		public synchronized void test(){
			//遍历每一个对象，调用对象的step方法，移动一次对象的位置  
        	for(int i = 0;i < enemysPlane.size();i++){  
        		enemysPlane.get(i).step();    	          	
            }       	
            for(int i = 0;i < bossPlane.size(); i++){
            	bossPlane.get(i).step();
            }
            mainPlane.step();
            
            //碰撞检测
            hit();
            		                    
            //添加子弹和敌人的碰撞检测  
            boom();
            
            //添加越界检测  
            outOfBounds();
            
            try {
				Thread.sleep(10);//10毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();//更新画板	
		}

		//结束线程
		public void stop(){
			flag = false;
		}		
	}
	
	/**
	 * 绘制开始界面
	 */
	public void paintStart(Graphics g){
		g.drawImage(PlaneFrame.maps.get("startbg1.jpg"), 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.drawString("请按Enter键开始游戏!", 150, 600);
	}
	
	/**
	 * 绘制游戏界面
	 */
	public void paintGame(Graphics g){
		//画背景
		g.drawImage(PlaneFrame.maps.get("background2a.bmp"), bgPoint.x, bgPoint.y, this);
		g.drawImage(PlaneFrame.maps.get("background2a.bmp"), bgPoint.x, bgPoint.y - 2880, this);
		paintScore_Life(g);//画分数信息
		paintEnemys(g);//画敌军
		paintMainPlane(g);//画主飞机
		paintMainBullet(g);//画主机子弹
		paintBossBullet(g);//画boss子弹
		paintEnemyBullet(g);//画敌军子弹
		paintExplode(g);//画爆炸效果
		
		}
	
	/**
	 * 绘制游戏结束界面
	 */
	public void paintOver(Graphics g){

		g.drawImage(PlaneFrame.maps.get("background2b.bmp"), 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.drawString("游戏结束!", 220, 300);	  
	    g.drawString("最终得分为: " + mainPlane.getScore(), 200, 400);  
		g.drawString("按Enter键重新游戏!", 150, 500);
		stop();//结束线程
	}
	
	/**
	 * 初始化数据，启动线程
	 */
	public void start(){
		//游戏初始化
		bgPoint= new Point(0, -2180);
		mainPlane = new MainPlane();
		enemysPlane = new Vector<EnemyPlane>();
		bossPlane = new Vector<BossPlane>();
		planeBullet = new Vector<Bullet>();
		bossBullet = new Vector<BossBullet>();
		enemyBullet = new Vector<EnemyBullet>();
		explode = new Vector<Explode>();

		bgThread = new BgThread();
		new Thread(bgThread).start();//启动背景线程
		
		bossThread = new BossThread();
		new Thread(bossThread).start();//启动boss线程
		
		addBullet = new AddBulletThread();
		new Thread(addBullet).start();//启动加子弹线程
		
		addEnemyBullet = new AddEnemyBullet();
		new Thread(addEnemyBullet).start();//启动敌军加子弹线程
		
		addBossBullet = new AddBossBullet();
		new Thread(addBossBullet).start();//启动boss加子弹线程
		
		bulletThread = new BulletThread();
		new Thread(bulletThread).start();//启动子弹移动线程
		
		enemyThread = new EnemyThread();								
		new Thread(enemyThread).start();//启动敌军线程
		
		testThread = new TestThread();
		new Thread(testThread).start();//启动测试线程
										
	}
	
	/**
	 * 结束各种线程
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
	 * 绘制分数和命数
	 */
	public void paintScore_Life(Graphics g){  
	    int x = 10; //文字在左上角的x坐标  
	    int y = 15; //文字在左上角的y坐标  
	    Font font = new Font(Font.SANS_SERIF,Font.BOLD,14);  
	    g.setFont(font); //设置字体的画笔对象  
	    //绘制第一行:分数  
	    g.drawString("SCORE: " + mainPlane.getScore(), x, y);  
	    //绘制第二行：生命值，y坐标下移20个单位  
	    y += 20;  
	    g.drawString("LIFE: " + mainPlane.getLife(), x, y);  
	}
	
	/**
	 * 绘制主飞机
	 */
	public void paintMainPlane(Graphics g){
		g.drawImage(mainPlane.image, mainPlane.x, mainPlane.y, null);
	}
	
	/**
	 * 绘制主机子弹
	 */
	public void paintMainBullet(Graphics g){
		for(int i = 0; i < planeBullet.size(); i++){
			g.drawImage(Bullet.bulletImage, 
					planeBullet.get(i).x, planeBullet.get(i).y, this);
			}
		}
	
	/**
	 * 绘制敌军子弹
	 */
	public void paintEnemyBullet(Graphics g){
		for(int i = 0;i < enemyBullet.size(); i++){
			g.drawImage(EnemyBullet.enemyBulletImage, enemyBullet.get(i).x, enemyBullet.get(i).y, this);
		}
	}
		
	/**
	 * 绘制Boss机子弹
	 */
	public void paintBossBullet(Graphics g){
		for(int i = 0; i < bossBullet.size(); i++){
			g.drawImage(BossBullet.bossBulletImage, 
					bossBullet.get(i).x, bossBullet.get(i).y, this);	
			}
		}
	
	/**
	 * 绘制爆炸效果 
	 */
	public void paintExplode(Graphics g){
		for(int i = 0;i < explode.size(); i++){
			g.drawImage(explode.get(i).image, explode.get(i).x, explode.get(i).y, null);
		}
		//控制爆炸效果时间
		for(int i = 0; i < explode.size(); i++){
			if(explode.get(i).remove()){
				explode.remove(i);
			}
		}
	}
	
	/** 
     * 遍历敌人数组，批量绘制所有敌人的方法  
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
     * 遍历敌人数组，判断英雄机和每个敌人是否碰撞 
     */  
    public void hit(){
    	//判断敌军和主机
    	for(int i = 0;i < enemysPlane.size();i++){  
            if(mainPlane.hit(enemysPlane.get(i))){  
                enemysPlane.remove(i);
            }  
        }
    	//判断boss和主机
        for(int i = 0;i < bossPlane.size();i++){
        	if(mainPlane.hit(bossPlane.get(i))){  
                bossPlane.remove(i);
            }  
        }
        if(mainPlane.getLife() <= 0){ //如果英雄机生命值小于等于0，游戏结束   
            state = GAME_OVER;  
        }           
    } 
    
    /** 
     * 遍历子弹数组和敌人数组，进行碰撞检测 
     * 一旦发生碰撞，子弹和敌人都减少一个 
     */  
    public void boom(){  
        //检测主机子弹与敌人的碰撞
    	for(int i = 0;i < planeBullet.size();i++){  
            for(int j = 0;j < enemysPlane.size();j++){  
                if(Plane.boom(planeBullet.get(i), enemysPlane.get(j))){              	         	
            		//为英雄机获得分数和奖励  
                    mainPlane.getScore_Award(enemysPlane.get(j));                                                
                    //绘制爆炸
                    explode.add(new Explode(Explode.explodeImage[0], 
                    		enemysPlane.get(j).x - enemysPlane.get(j).width / 2, enemysPlane.get(j).y));
                    //从敌人数组中删除被击中的敌机 
                    enemysPlane.remove(j);
                    //从子弹数组中删除击中敌机的子弹  
                    planeBullet.remove(i);
                    i--; //第发现一次碰撞，子弹就要退一个元素，重新检测当前位置  
                    break; //只要发现碰撞就退出当前敌人数组的循环                  
                } 
            }      
        }
    	
    	//检测敌军子弹和主机碰撞
    	for(int i = 0; i < enemyBullet.size(); i++){
    		if(Plane.boom(enemyBullet.get(i), mainPlane)){
    			enemyBullet.remove(i);//删除碰撞的子弹
    			mainPlane.setLife(mainPlane.getLife() - 1);
    			if(mainPlane.getLife() <= 0){ //如果英雄机生命值小于等于0，游戏结束   
    		           state = GAME_OVER;  
    		    	}
    			}
    		}
    	
    	//检测boss机和主机碰撞
    	for(int i = 0;i < planeBullet.size();i++){
    		for(int n = 0;n < bossPlane.size();n++){
            	if(Plane.boom(planeBullet.get(i), bossPlane.get(n))){
	            		if(bossPlane.get(n).getLife() >= 0){
	            			bossPlane.get(n).life -= 1;
	            			//从子弹数组中删除击中敌机的子弹  
	                        planeBullet.remove(i);
	                        i--; //第发现一次碰撞，子弹就要退一个元素，重新检测当前位置  
	                        break; //只要发现碰撞就退出当前敌人数组的循环 
	            		}
	            		else
	            		{   
	            			//为英雄机获得分数和奖励  
	                        mainPlane.getScore_Award(bossPlane.get(n));                                                
	                        //绘制爆炸
	                        explode.add(new Explode(Explode.explodeImage[1], bossPlane.get(n).x, bossPlane.get(n).y));
	                        //从敌人数组中删除被击中的敌机    
	                        bossPlane.remove(n);
	                        //从子弹数组中删除击中敌机的子弹  
	                        planeBullet.remove(i);
	                        i--; //第发现一次碰撞，子弹就要退一个元素，重新检测当前位置  
	                        break; //只要发现碰撞就退出当前敌人数组的循环 
	            		}
            		}
            	} 		
    		}
    	
    	//检测Boss机子弹与主机碰撞
    	for(int i = 0; i < bossBullet.size(); i++){
    		if(Plane.boom(bossBullet.get(i), mainPlane)){
    			bossBullet.remove(i);//删除碰撞的子弹
    			mainPlane.setLife(mainPlane.getLife() - 1);
    			if(mainPlane.getLife() <= 0){ //如果英雄机生命值小于等于0，游戏结束   
    		           state = GAME_OVER;  
    		    	}
    			}
    		}
		}
      
    /** 
     * 检查所有飞行物是否越界 
     */  
    public void outOfBounds(){  
        //检查所有敌人是否越界  
        for(int i = 0;i < enemysPlane.size();i++){  
            if(enemysPlane.get(i).outOfBounds()){ 
                enemysPlane.remove(i);            
            } 
        }
                
        //检测主机子弹是否越界  
        for(int i = 0;i < planeBullet.size();i++){  
            if(planeBullet.get(i).outOfBounds()){  
                planeBullet.remove(i);
            }  
        }
        
        //检测敌军子弹是否越界
        for(int i = 0;i < enemyBullet.size();i ++){
        	if(enemyBullet.get(i).outOfBounds()){
        		enemyBullet.remove(i);
        	}
        }
        
        //检测boss机子弹是否越界
        for(int i = 0; i < bossBullet.size(); i++){
        	if(bossBullet.get(i).outOfBounds()){
        		bossBullet.remove(i);
        	}
        }
    }  

}

