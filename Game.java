//*
//Richard Liu and Daniel Niu
//ICS Final Project - Snake game
//January 16th 2019
//class Game.java (main)
//*

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

//class main
public class Game{
	public static void main(String[]args){
		//creates a new board
		Board s = new Board();
		//creates a new snake and sets its inital values
		Snake snake = new Snake(550,300);
		JFrame f = new JFrame();
		f.add(s);
		f.setVisible(true);
		f.setSize(1200, 600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Snake Game");
	}
}
