/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	private String s="";


	/** Resets the display so that only the scaffold appears */
	public void reset() {
		addScaffold();
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		/* You fill this in */
		if(getElementAt(getWidth()/2 - 100, getHeight() - 50) != null) remove(getElementAt(getWidth()/2 - 100, getHeight() - 50));
		GLabel displayWord = new GLabel(word);
		add(displayWord, getWidth()/2 - 100, getHeight() - 50);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter, int attempt) {
		/* You fill this in */
			if(attempt == 8 ) addHead();
			if(attempt == 7 ) addBody();
			if(attempt == 6) addLeftHand();
			if(attempt == 5) addRightHand();
			if(attempt == 4) addLeftLeg();
			if(attempt == 3) addLeftFoot();
			if(attempt == 2) addRightLeg();
			if(attempt == 1) addRightFoot();
			incorrectGuessesLabel(letter);
			
	}
	
	//this method adds player's new incorrect guess (letter) on the canvas
	//pre-condition: the player has entered incorrect guess in console.
	//post-condition: program has added the incorrect letter on the canvas.
	private String incorrectGuessesLabel(char letter) {
		s+=letter;
		GLabel guesses = new GLabel(s);
		add(guesses, getWidth()/2 - 100, getHeight() - 25);
		return s;
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	
	//this method adds scafflold on the canvas\
	//pre-condition: the canvas is empty.
	//post-condition: there is a scaffold on the canvas
	private void addScaffold() {
		GLine scaffold = new GLine(30, 30, 30, 30 + SCAFFOLD_HEIGHT);
		GLine beam = new GLine(30, 30, 30 + BEAM_LENGTH, 30);
		GLine rope = new GLine(30 + BEAM_LENGTH, 30, 30 + BEAM_LENGTH, 30 + ROPE_LENGTH);
		add(scaffold);
		add(beam);
		add(rope);
	}
	
	//this method paints man's head on the canvas.
	private void addHead() {
		GOval head = new GOval(2*HEAD_RADIUS, 2*HEAD_RADIUS);
		add(head, 30 + BEAM_LENGTH - HEAD_RADIUS, 30 + ROPE_LENGTH);
	}
	
	//this method paints man's body on the canvas.
	private void addBody() {
		GLine body = new GLine(30 + BEAM_LENGTH, 30 + ROPE_LENGTH + 2*HEAD_RADIUS, 30 + BEAM_LENGTH , 30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH);
		add(body);
	}
	
	//this method paints man's left hand on the canvas.
	private void addLeftHand() {
		GLine leftUpperArm = new GLine(30 + BEAM_LENGTH, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, 30 + BEAM_LENGTH - UPPER_ARM_LENGTH, 
				30 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		add(leftUpperArm);
		GLine leftLowerArm = new GLine(30 + BEAM_LENGTH - UPPER_ARM_LENGTH, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, 30 + BEAM_LENGTH - UPPER_ARM_LENGTH,
				30 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(leftLowerArm);
		
	}
	
	//this method paints man's right hand on the canvas.
	private void addRightHand() {
		GLine rightUpperArm = new GLine(30 + BEAM_LENGTH, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, 30 + BEAM_LENGTH + UPPER_ARM_LENGTH, 
				30 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		add(rightUpperArm);
		GLine rightLowerArm = new GLine(30 + BEAM_LENGTH + UPPER_ARM_LENGTH, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, 30 + BEAM_LENGTH + UPPER_ARM_LENGTH,
				30 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(rightLowerArm);
	}
	
	//this method paints man's left leg on the canvas.
	private void addLeftLeg() {
		GLine leftHip = new GLine(30 + BEAM_LENGTH , 30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH, 30 + BEAM_LENGTH - HIP_WIDTH/2, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH);
		add(leftHip);
		GLine leftLeg = new GLine(30 + BEAM_LENGTH - HIP_WIDTH/2, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH, 30 + BEAM_LENGTH - HIP_WIDTH/2, 
				30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftLeg);
	}
	
	//this method paints man's left foot on the canvas.
	private void addLeftFoot() {
		GLine leftFoot = new GLine(30 + BEAM_LENGTH - HIP_WIDTH/2, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH, 30 + BEAM_LENGTH - HIP_WIDTH/2 - FOOT_LENGTH, 
				30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftFoot);
	}
	
	//this method paints man's right leg on the canvas.
	private void addRightLeg() {
		GLine rightHip = new GLine(30 + BEAM_LENGTH , 30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH, 30 + BEAM_LENGTH + HIP_WIDTH/2, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH);
		add(rightHip);
		GLine rightLeg = new GLine(30 + BEAM_LENGTH + HIP_WIDTH/2, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH, 30 + BEAM_LENGTH + HIP_WIDTH/2, 
				30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightLeg);
	}
	
	//this method paints man's right foot on the canvas.
	private void addRightFoot() {
		GLine rightFoot = new GLine(30 + BEAM_LENGTH + HIP_WIDTH/2, 30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH, 30 + BEAM_LENGTH + HIP_WIDTH/2 + FOOT_LENGTH, 
				30 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightFoot);
	}
}
