
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	private HangmanCanvas canvas;
	private String word;
	private String wordInterface;
	private int attempt = 8;
	
	//this method provides initialization of the game
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
		canvas.reset();
	}
	//this method provides game process.
	public void run() {
		welcomeMessage();
		defineWord();
			gameProcess();
		}
	

	//this method prints welcome message
	private void welcomeMessage() {
		println("Welcome to Hangman!");
	}
	
	// this method chooses word randomly from HangmanLexicon.java file and prints
	// dashes instead of letters
	private void defineWord() {
		HangmanLexicon getNewWord = new HangmanLexicon();
		RandomGenerator rgen = RandomGenerator.getInstance();
		word = getNewWord.getWord(rgen.nextInt(0, getNewWord.getWordCount()));
		int lettersAmount = countLetters();
		wordInterface = printDashes(lettersAmount);
	}
	
	// this method provides game process
	private void gameProcess() {
		while (attempt > 0) {
			canvas.displayWord(wordInterface);
			printInitialMessages();
			String letter = readLine("Your guess: ");
			letter = checkLetterAccuracy(letter);
			// program responds accordingly
			if (letterFound(letter)) {
				replaceDash(letter);
				println("That guess is correct");
			} else {
				canvas.noteIncorrectGuess(letter.charAt(0), attempt);
				attempt--;
				println("There are no " + letter + "'s in the word");
			}
			if(!wordInterface.contains("-")) {
				winningCase();
				break;
			}
		}
		losingCase();
	}
	
	//this method prints initial messages of the game
	private void printInitialMessages() {
		println("Your word now looks like this: " + wordInterface);
		println("You have " + attempt + " guesses left.");
	}
	

	// this method counts the amount of letters in randomly chosen word
	//pre-condition: the program has randomly chosen the word.
	//post-condition: the program has counted the amount of letters in randomly chosen word.
	private int countLetters() {
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			count++;
		}
		return count;
	}

	// this method prints dashes instead of letters as soon as the program randomly chooses word
	//pre-condition: the program has randomly chosen the word.
	//post-condition: the program has printed dashes instead of every letter in the randomly chosen word.
	private String printDashes(int lettersAmount) {
		String dashes = "";
		for (int i = 0; i < lettersAmount; i++) {
			dashes = dashes + "-";
		}
		return dashes;
	}
	
	//this method checks whether the symbol entered by player is accurate or not.
	//pre-condition: the player entered the symbol.
	//post-condition: the program has checked whether entered symbol is letter or not and whether the player has entered only one symbol or not. 
	//if the symbol is not accurate, the program asks player to enter new one.
	private String checkLetterAccuracy(String letter) {
		while(letter.charAt(0) <= 64 || (letter.charAt(0) >= 91 && letter.charAt(0) <= 96) || letter.charAt(0) >= 123 || letter.length() > 1 ) {
			letter = readLine("Wrong information, please enter your new guess: ");
		}
		return letter;
	}
	
	//this method returns the answer whether the randomly chosen word contains the letter entered by player or not
	//pre-condition: the player has entered the letter.
	//post-condition: the program has checked whether the letter is correct or not and returned corresponding result.
	private boolean letterFound(String letter) {
		for (int i = 0; i < word.length(); i++) {
			if (letter.charAt(0) == word.charAt(i) || letter.charAt(0) - 32 == word.charAt(i))
				return true;
		}
		return false;
	}
	
	//this method replaces dash with inserted letter, if player inserts the correct letter.
	//pre-condition: the word contains the letter entered by the player.
	//post-condition: the program has replaced the dash with the guessed letter.
	private String replaceDash(String letter) {
		String newWord = "";
		for(int i = 0; i < word.length(); i++) {
			if(letter.charAt(0) == word.charAt(i) || letter.charAt(0) - 32 == word.charAt(i) ) {
				newWord += letter.charAt(0); 
			}else {
				newWord += wordInterface.charAt(i);
			}
		}
			wordInterface = newWord;
		return wordInterface;
	}
	
	//this method announces player that he/she has won the game.
	//pre-condition: player has guessed the word.
	//post-condition: the program tells the player that he/she has won the game
	private void winningCase() {
		println("You guessed the word: " + word);
		println("You win.");
		canvas.displayWord(word);
	}
	
	//this method announces player that he/she has lost the game.
	//pre-condition: there are no more attempts left and the player has not guessed the word yet.
	//post-condition: the program tells the player that he/she has lost the game and tells the word that he/she should have guessed.
	private void losingCase() {
		if(wordInterface.contains("-")) {
			println("You're completely hung.");
			println("The word was: " + word);
			println("You lose.");
			canvas.displayWord(word);
		}
	}

}