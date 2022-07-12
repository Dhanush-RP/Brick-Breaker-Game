package brickBreaker;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) 
	{
		JFrame frame = new JFrame();  //use in-built JFrame package
		
		GamePlay gamePlay = new GamePlay();
		
		frame.setBounds(10,10,700,600);  //x axis, y axis, width, height  and co-ordinates are for top-left corner
		frame.setTitle("Brick Breaker Game");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to exit instead of minimizing
		frame.add(gamePlay);

	}

}
