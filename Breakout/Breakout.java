/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private static final double DELAY = 10;
	private GOval ball;
	private GRect paddle;
	private GRect brick;
	private double NBRICKS;
	
	//this method provides breakout game process and design;
	public void run() {
		design();
		process();
	}
	
	//this method sets up all the needed graphics for breakout.
	//pre-condition: the world is empty.
	//post-condition: there are paddle and bricks in the world.
	private void design() {
		setUpBricks();
		setUpPaddle();
		addMouseListeners();
	}
	
	//this method sets up bricks as required.
	//pre-condition:the world is empty.
	//post-condition: there are rows of colourful bricks in the world.
	private void setUpBricks() {
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			for (int r = 0; r < NBRICK_ROWS; r++) {
				brick = new GRect(BRICK_SEP / 2 + i * (BRICK_SEP + BRICK_WIDTH),
						BRICK_Y_OFFSET + r * (BRICK_SEP + BRICK_HEIGHT), BRICK_WIDTH, BRICK_HEIGHT);
				add(brick);
				brick.setFilled(true);
				if (r < 2)
					brick.setColor(Color.red);
				if (r >= 2 && r < 4)
					brick.setColor(Color.orange);
				if (r >= 4 && r < 6)
					brick.setColor(Color.yellow);
				if (r >= 6 && r < 8)
					brick.setColor(Color.green);
				if (r >= 8 && r < 10)
					brick.setColor(Color.cyan);
			}
		}
	}

	//this method sets up paddle as required.
	//pre-condition: there are only bricks in the world.
	//there are paddle and bricks in the world.
	private void setUpPaddle() {
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		add(paddle, getWidth() / 2 - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		paddle.setFilled(true);
	}

	//this method relocates paddle as mouse moves.
	public void mouseMoved(MouseEvent e) {
		if(e.getX()<getWidth()-paddle.getWidth()) {
			paddle.setLocation(e.getX(), getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}else {
			paddle.setLocation(getWidth()-paddle.getWidth(), getHeight()-PADDLE_Y_OFFSET-PADDLE_HEIGHT);
		}
	}
	
	//this method provides game to be played until there are 0 bricks left 
	//or until player has no more turns to play
	private void process() {
		//define number of bricks in the beginning of the game
		NBRICKS = NBRICKS_PER_ROW * NBRICK_ROWS;
		
		//starting game process until there are number of turns left.
		for (int i = 0; i < NTURNS; i++) {
			//if there are any bricks in the world it means that the player has not won the game yet, 
			//so the programme creates the ball and let the player play the game
			if (NBRICKS > 0) {
				createBall();
				playGame();
				remove(ball);
			}
			if(NBRICKS==0)labelWin();
		}
		if (NBRICKS>0) labelLoss();
	}
	
	//playing game 
	//pre-condition: there are number of turns left to play, amount of bricks>0 and the bouncing ball has been created.
	//post-condition: the game lasts until the ball is below the paddle or until NBRICKS==0.
	private void playGame() {
		while (ball.getY() < getHeight() - PADDLE_Y_OFFSET - (PADDLE_HEIGHT-1) ) {
			moveBall();
			checkForCollision();
			pause(DELAY);
			if (NBRICKS == 0)
				break;
		}
	}
	
	//this method creates the ball and defines its velocity.
	//pre-condition: there are only bricks and paddle in the world. game process has already started.
	//post-condition: the ball has been created and started moving.
	private void createBall() {
		ball = new GOval(2 * BALL_RADIUS, 2 * BALL_RADIUS);
		add(ball, getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
		ball.setFilled(true);
		vy = 3;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5))
			vx = -vx;
	}
	
	//this method announces player that he/she won.
	//pre-condition:there are no bricks left to remove and the player still has NTURNS left.
	//post-condition: the programme draws the label "You Won".
	private void labelWin() {
		GLabel labelWin = new GLabel("You Won");
		add(labelWin, getWidth() / 2 - labelWin.getWidth()/2, HEIGHT / 2);
		labelWin.setColor(Color.green);
	}

	//this method announces player that he/she lost.
	//pre-condition: there are no more available turns left.
	//post-condition: the programme draws the label "Game Over, You Lost".
	private void labelLoss() {
		GLabel labelLoss = new GLabel("Game Over, You Lost");
		add(labelLoss, getWidth() / 2 - labelLoss.getWidth() / 2, HEIGHT / 2);
		labelLoss.setColor(Color.red);
	}
	
	//this method provides moving process of the ball
	private void moveBall() {
		ball.move(vx, vy);
	}
	
	//this method provides the process that happens after ball collides to any object(including walls).
	private void checkForCollision() {
		checkWallsAndUpperBound();
		checkPaddleAndBricks();
	}
	
	//this method provides ball bouncing process in the world. 
	//pre-condition:ball collides to the walls or to the upper bound.
	//post-condition: ball has bounced and now starts moving to opposite direction.
	private void checkWallsAndUpperBound() {
		if (ball.getX() < 0 && vx<0) vx=-vx;
		if(ball.getX() > getWidth() - 2*BALL_RADIUS && vx>0 )	vx = -vx;
		if (ball.getY() < 0 && vy<0 ) vy = -vy;
	}
	
	//this method checks whether collided object is paddle or bricks.
	//pre-condition: ball has collided to paddle or bricks.
	//post-condition: if the collided object is paddle, ball starts moving in opposite direction,
	//if collided object is brick, brick is removed and ball starts moving in opposite direction.
	private void checkPaddleAndBricks() {
		GObject collider = getCollidingObject();
				if (collider==paddle && vy>0) vy=-vy;
		if (collider != null && collider != paddle) {
			vy = -vy;
			remove(collider);
			NBRICKS--;
		}
	}
	
	//this method returns the object that ball has collided.
	private GObject getCollidingObject() {
		if (getElementAt(ball.getX(), ball.getY()) != null)
			return (getElementAt(ball.getX(), ball.getY()));
		if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null)
			return (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()));
		if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS) != null)
			return (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS));
		if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null)
			return (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS));
		return (null);
	}

}