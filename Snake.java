//*
//Richard Liu and Daniel Niu
//ICS Final Project - Snake game
//January 16th 2019
//class Snake.java
//*

public class Snake{
	//SIZE is the size of each pixel/joint in the snake and food
	final static int SIZE = 15;
	//this is the total amount of snake joints that would fit inside the frame
	final static int TOTAL = (1200/SIZE)*(600/SIZE); 
	//create 2 arrays, 1 that stores x value of snake and 1 that stores y value of snake
	//array length is the maximum amount of snake joints that would fit on the screen
	private final int arrx[] = new int[TOTAL];
	private final int arry[] = new int[TOTAL];
	//constructor
	public Snake(int x, int y){
		//initiallizes the snakes position
		arrx [0] = x;
		arry [0] = y;
	}		
	
	public int movex(int x) {
		//returns a specified x value in the array of x co-ordinates 
		return arrx[x];
	}
	public int movey(int y) {
		//returns a specified y value in the array of y co-ordinates 
		return arry[y];
	}
	public void move (int i){
		//moves snake parts up the chain
		arrx[i] = arrx[i-1];
		arry[i] = arry[i-1];
	}
	
	//takes the direction the snake is moving in and changes the x or y value of snake head depending on which direction it is moving in
	public void movingLeft(boolean movingLeft){
		if (movingLeft){
			//snake head value (x/y) subtract/add the size of a pixel
			arrx[0] -= SIZE;
		}
	}
	public void movingRight(boolean movingRight){
		if (movingRight){
			arrx[0] += SIZE;
		}
	}
	public void movingDown(boolean movingDown){
		if (movingDown){
			arry[0] += SIZE;
		}
	}
	public void movingUp(boolean movingUp){
		if (movingUp){
			arry[0] -= SIZE;
		}
	}

}
