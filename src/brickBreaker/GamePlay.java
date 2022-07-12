package brickBreaker;

import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0 ;
	private int totalBricks = 21;   //7X3 map
	
	private Timer timer;
	private int delay = 8; 
	
	private int playerPosX= 310;
	private int ballPosX= 120;
	private int ballPosY= 350;
	private int ballDirectionX = -1;
	private int ballDirectionY = -2;
	
	private MapGenerator map;
	public GamePlay()   //constructor
	{
		map= new MapGenerator(3,7);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);   //this refers to action
		timer.start();
	}
	
	public void paint(Graphics g)
	{   // Backgrounds
		g.setColor(Color.black);
		g.fillRect(1,1,692,592); //x axis, y axis, width, height
		
		
		// Drawing map
		map.draw((Graphics2D)g);
		
		// Borders
		g.setColor(Color.yellow);
		g.fillRect(0 , 0 ,3 ,592);
		g.fillRect(0 , 0 ,692 ,3);
		g.fillRect(684,0,3,592);
		
		// Score
		g.setColor(Color.white);
		g.setFont(new Font("serif" , Font.BOLD ,25));
		g.drawString(""+score,590,30);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerPosX,550,100,8);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX , ballPosY , 20,20 );
		
		//End Detector
		if(totalBricks<=0)
		{
			play=false;
			ballDirectionX= 0;
			ballDirectionY=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif" , Font.BOLD ,30));
			g.drawString("You Won!  Your Score = "+score,150,300);
			
			g.setFont(new Font("serif" , Font.BOLD ,20));
			g.drawString("Press Enter To Restart ",230,350);	
		}
		
		
		if(ballPosY>570)
		{
			play=false;
			ballDirectionX= 0;
			ballDirectionY=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif" , Font.BOLD ,30));
			g.drawString("Game Over!  Your Score = "+score,150,300);
			
			g.setFont(new Font("serif" , Font.BOLD ,20));
			g.drawString("Press Enter To Restart ",230,350);
			
		}
		
		g.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play)
		{
			if(new Rectangle(ballPosX, ballPosY , 20 ,20).intersects(new Rectangle(playerPosX ,550,100,8)))
					ballDirectionY = - ballDirectionY;
			
			A: for(int i = 0 ; i <map.map.length;i++)
				for(int j = 0 ; j<map.map[0].length; j++)
					if(map.map[i][j]>0)
					{
						int brickX= j*map.brickWidth+80;
						int brickY = i*map.brickHeight+50;
						int brickWidth = map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY,brickWidth,brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);
						Rectangle brickRect =rect;
						
						if(ballRect.intersects(brickRect))
						{
							map.setBrickValue(0, i,j);
							totalBricks--;
							score+=5;
							
							if(ballPosX+19<=brickRect.x  || ballPosY+1>= brickRect.x + brickRect.width)
								ballDirectionX= -ballDirectionX;
							else
								ballDirectionY= -ballDirectionY;
							
							//break A;
						}
					}
			
			ballPosX+=ballDirectionX;
			ballPosY+=ballDirectionY;
			
			if(ballPosX<0)
				ballDirectionX = -ballDirectionX;
			if(ballPosY<0)
				ballDirectionY = -ballDirectionY;
			if(ballPosX>670)
				ballDirectionX = -ballDirectionX;
			
			
		}
		repaint();        //re-call the paint fuction when the movement is being done in game
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(playerPosX>=600)
			{
				playerPosX=600;
			}
			else
				moveRight();
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(playerPosX<10)
			{
				playerPosX=10;
			}
			else
				moveLeft();
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			if(!play)
			{
				play=true;
				ballPosX=120;
				ballPosY=350;
				ballDirectionX = -1;
				ballDirectionY = -2;
				playerPosX=310;
				score=0;
				totalBricks = 21;
				map=new MapGenerator(3,7);
				repaint();
			}
	}
	public void moveRight()
	{
		play =true;
		playerPosX+=20;
	}
	public void moveLeft()
	{
		play =true;
		playerPosX-=20;
	}
}
