//*
//Richard Liu and Daniel Niu
//ICS Final Project - Snake game
//January 16th 2019
//class Board.java
//*

//importing everything
import javax.swing.*;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;


public class Board extends JPanel implements ActionListener, KeyListener{
	//class snake
	Snake snake; 
	//final variables
	final static int SIZE = 15;
	final static int CLOSENESS = SIZE/3*2;
	final static int HE = 600;
	final static int WID = 1200;
	//other variables
	public boolean ingame;
	private int counter;
	public int foodx;
	public int foody;
	public boolean leave;
	public boolean isfood;
	public boolean movingLeft;
	public boolean movingRight;	
	public boolean movingUp;
	public boolean movingDown;
	public int score;
	public int joints;
	public String stringscore;
	private JButton exit;
	//Timers for difficulty
	Timer t; 
	Timer t2; 
	Timer t3; 
	Timer t4; 
	Timer t5; 
	
	public Board(){
		//initializing everything in the constructor
		t = new Timer (75, this);
		t2 = new Timer (60, this);
		t3 = new Timer (45, this);
		t4 = new Timer (30, this);
		t5 = new Timer (15, this);
		snake = new Snake(550,300);
		ingame = true;
		counter = 0;
		isfood = false;
		movingLeft = true;
		movingRight = false;	
		movingUp = false;
		movingDown = false;
		score = 0;
		stringscore = "";
		//snake starts with 4 joints
		joints = 4;
		//making board
        setBackground(Color.BLACK);
        addKeyListener(this);
        this.setFocusable(true);
        setFocusTraversalKeysEnabled(false);
		//timer start
		t.start();
    }
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		g.setFont(font);
		g.setColor(Color.white);
		//score is the amount of food the snake has eaten, so since snake starts with 4 joints, and gains 1 joint per food, score is joints-4
		score = joints-4;
		//initialize to level 1
		if (score >= 0 && score <= 4){
			g.drawString("Level 1: ", 10, 30);
		}
		//levels 2-5
		if (score >= 5 && score <= 9){
			//stops timer from previous level, and starts a faster timer
			t.stop();
			t2.start();
			g.drawString("Level 2: ", 10, 30);
		}else if (score >= 10 && score <= 14){
			t2.stop();
			t3.start();
			g.drawString("Level 3: ", 10, 30);
		}else if (score >= 15 && score <= 19){
			t3.stop();
			t4.start();
			g.drawString("Level 4: ", 10, 30);
		}else if (score >=20){
			t4.stop();
			t5.start();
			g.drawString("Level 5: ", 10, 30);
		}
		//d.drawString only accepts string as a paramater so score needs to be converted to string
		stringscore = Integer.toString(score);
		g.drawString("Score: "+stringscore, WID/2-80, 40);
		if (!isfood){
			//if there is no food on the board, change co-ordinates of the next food location
			foodx = rand();
			//divide by 2 because height is 600 and width is 1200, width is exactly 2 times longer than height
			foody = (rand())/2;
			isfood = true;
		}
		//repaints the location of the food
		food(g);
		//paints the snake
		for (int i = 0; i < joints; i++) {
			g.setColor(Color.RED);
            g.fillRect(snake.movex(i),snake.movey(i), SIZE, SIZE);
        }
		//checks for collisions and foodcollisions
		collisions();
		foodcollisions(g);
		if (!ingame){
			//if you have died
			t.stop();
			t2.stop();
			t3.stop();
			t4.stop();
			t5.stop();
			endgame(g);
		}
	}

	public void actionPerformed(ActionEvent e){
		//to count the ticks
		counter ++;
		repaint();
		//this moves all the snake parts up the chain
		for (int i = joints; i > 0; i--) {
			snake.move(i);
		}	
		//this adds a pixel to the snake head at the direction it is moving
		snake.movingLeft(movingLeft);
		snake.movingRight(movingRight);
		snake.movingUp(movingUp);
		snake.movingDown(movingDown);
	}
	
	public void collisions(){
		//if snake has hit the borders
		if (snake.movex(0) > WID || snake.movey(0) > HE-50 || snake.movex(0) < 0 ||snake.movey(0) < 0){
			ingame = false;
		}
		//as the snake spawns, it "collides" with itself so the next line prevents that from happening
		//one snake joint spawns in per tick, so if the amount of ticks are greater than the amount of joints, the entire snake has spawned
		if (counter > joints){
			//snake can only hit itself if it has more than 4 joints
			if (joints > 4){
				//checks if snake hit itself
				for (int i = joints-1; i > 0; i--){
					if (snake.movex(0) == snake.movex(i) && snake.movey(0) == snake.movey(i)){
						ingame = false;
					}
				}
			}
		}
		
	}
	
	public void foodcollisions(Graphics g){
		//checks to see if the snake head is close enough to the food, if it is, increase number of joints and tell program to spawn another food
		//closeness is how close the snake head needs to be to successfully eat a food
		if (snake.movex(0) >= foodx-CLOSENESS && snake.movex(0) <= foodx+CLOSENESS && snake.movey(0) <= foody+CLOSENESS && snake.movey(0) >= foody-CLOSENESS){
			joints++;
			//spawns another food in a different location
			food(g);
			isfood = false;
		}			
	}
	
	//tells if moving up
    public void up(){
		movingLeft = false;
		movingRight = false;	
		movingUp = true;
		movingDown = false;
    }

	//tells if moving down
    public void down(){
		movingLeft = false;
		movingRight = false;	
		movingUp = false;
		movingDown = true;
    }

	//tells if moving left
    public void left(){
		movingLeft = true;
		movingRight = false;	
		movingUp = false;
		movingDown = false;
    }
	
	//tells if snake is moving right
    public void right(){
		movingLeft = false;
		movingRight = true;	
		movingUp = false;
		movingDown = false;
	
    }
	//checks what key is pressed
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
		//if snake wants to move in the opposite direction, don't do anything
		if (code == KeyEvent.VK_UP && !isdown()){
            up();
        }

        if (code == KeyEvent.VK_DOWN && !isup()){
            down();
        }

        if (code == KeyEvent.VK_LEFT && !isright()){
            left();
        }

        if (code == KeyEvent.VK_RIGHT && !isleft()){
            right();
        }
    }
	//checks which direction the snake is moving in
	public boolean isup(){
		//compares the snake's head and the snake's second portion to determine direction
		if (snake.movey(0) < snake.movey(1)){
			return true;
		}else{
			return false;
		}
    }

    public boolean isdown(){
		if (snake.movey(0) > snake.movey(1)){
			return true;
		}else{
			return false;
		}
    }

    public boolean isleft(){
		if (snake.movex(0) < snake.movex(1)){
			return true;
		}else{
			return false;
		}
    }

    public boolean isright(){
		if (snake.movex(0) > snake.movex(1)){
			return true;
		}else{
			return false;
		}
    }
	
    public void keyTyped(KeyEvent e){}

	//don't do anything when key is released
    public void keyReleased(KeyEvent e){}
	
	//generates a random number from 0 to width of frame for the food
	public int rand(){
		int rand = (int)((Math.random())*1000);
		return rand;
	}
	public void food (Graphics g){
		//draws food at a specific location
		g.setColor(Color.YELLOW);
		g.fillRect(foodx, foody, SIZE, SIZE);
	}
	
	public void endgame(Graphics g){
		//when snake dies
		Timer t = new Timer (100, this);
		t.start();
		repaint();
		int newcounter = 0;
		counter++;
		Font font = new Font("Times New Roman", Font.BOLD, 80);
		g.setColor(Color.red);
		g.setFont(font);
		//print game over
		g.drawString("GAME OVER", WID/2-250, HE/2);
		if (counter > 1500 && counter < 9999){
			g.setColor(Color.black);
			g.fillRect(0,0,1200,600);
			g.setColor(Color.white);
			//print player score
			g.drawString("YOUR SCORE: "+stringscore, WID/2-300, HE/2);
		}else if(counter > 10000){
			//after a while, exit automatically and return to main menu
			leave();
		}
	}
	
	public void leave(){
		 System.exit(0);
	}
	
}
