/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	//this is run method
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	//this method provides game process
	private void playGame() {
		selectedCategories = new int[nPlayers][N_CATEGORIES];
		results = new int[nPlayers][N_CATEGORIES];
		for(int i=0; i<N_SCORING_CATEGORIES; i++) {
			for(int j=1; j<nPlayers+1; j++) {
				rollDice(j);
				chooseCategory(j);
			}
		}
		findOutWinner();
	}
	
	//this method provides dice rolls
	//pre-condition: the game has started.
	//post-condition: the player has rolled the dice 3 times and has not got any rolls left.
	private void rollDice(int j) {
		randomDice();
		display.printMessage(playerNames[j - 1] + "'s turn! Click 'Roll Dice' button to roll the dice");
		//roll dice for the first time
		display.waitForPlayerToClickRoll(j);
		display.displayDice(dice);
		//next 2 rolls
		for(int i=0; i<2; i++) {
			display.printMessage("Select the dice you wish to re-roll and click 'Roll Again'.");
			display.waitForPlayerToSelectDice();
			for(int k=0; k<N_DICE; k++) {
				if(display.isDieSelected(k) == true) 
				dice[k]=rgen.nextInt(1,6);
			}
			display.displayDice(dice);
		}
	}
	
	//this method provides that dices will be random int from 1 to 6
	//pre-condition:the game has started.
	//post-condition:the method has returned array of random dices.
	private int[] randomDice() {
		dice = new int[N_DICE];
		for(int i=0; i<N_DICE; i++) {
			dice[i] = rgen.nextInt(1,6);
		}
		return dice;
	}
	
	//this method provides choosing category by the player and getting score for the chosen category
	//pre-condition: the player has rolled the dice 3 times and has not got any rolls left.
	//post-condition: the player has chosen category and got the corresponding score in it.
	private void chooseCategory(int j) {
		display.printMessage("Select the category for this roll.");
		category = display.waitForPlayerToSelectCategory();
		if(alreadySelected(j))	playerSelectsAnotherOne(j);
		if(!alreadySelected(j)){
			selectCategory(j);
			boolean p = checkCategory(dice,category);
			if(p==true) {
				display.updateScorecard(category, j, countScore(category));
				updateResults(j,countScore(category));
			}else {
				display.updateScorecard(category, j, 0);
				updateResults(j,0);
			}
		}	
	}
	
	//if the player has already chosen selected category, program asks him/her to choose another one.
	//pre-condition: player has selected category, which has already been chosen by her/him.
	//post-condition: player has selected category which has not been chosen by him/her.
	private void playerSelectsAnotherOne(int j) {
		while(alreadySelected(j)) {
			display.printMessage("Category already selected. Please select new one.");
			category = display.waitForPlayerToSelectCategory();
		}
	}
	
	//this method selects category 
	//pre-condition: player has selected category, the program has checked whether it had been already chosen or not, and if not then lets the player to choose it.
	//post-condition: player has chosen the selected category. (1 means selected)
	private void selectCategory(int j) {
		selectedCategories[j-1][category-1]=1;
	}
	
	//this method checks if selected category has already been chosen
	//pre-condition: player selects category
	//post-condition: the method returns true if the category has already been chosen by the player. In other case, returns false 
	private boolean alreadySelected(int j) {
		if(selectedCategories[j-1][category-1]==1) 
			return true;
		return false;	
	}
	
	//this method counts scores for selected category.
	//pre-condition: player has chosen the category. program has checked whether dices are suitable for specific category.
	//post-condition: program has returned the score which player should get for chosen category.
	private int countScore(int category) {
	 score = 0;
		if(category >=1 && category <=6) {
			for(int i=0; i<N_DICE;i++) {
				if(dice[i] == category)
					score+=category;
			}
		}	
		if(category == 9 || category == 10 || category == 15 ) {
			for(int i=0; i<N_DICE;i++) {
				score+=dice[i];
			}
		}
		if(category == 11) score=25;
		if(category == 12) score = 30;
		if(category == 13) score = 40;
		if(category == 14) score = 50;
		return score;
	}

	//this method updates score board for each player after every move.
	//pre-condition: any player has rolled the dices, has chosen category and has gotten score for it.
	//post-condition: score board was updated, new scores are visible for everyone.
	private void updateResults(int j, int score) {
		results[j-1][category-1] +=score;
		upperScores = calculateScores(1,7);
		lowerScores = calculateScores(9,16);
		upperBonus = upperBonus(upperScores);
		totalScores = calculateTotalScores(upperScores, lowerScores,upperBonus);
	}
	
	//this method calculates total scores in categories from number x to number y
	//pre-condition: player has played its turn and score board should be updated. this program has gotten category numbers as inputs.
	//post-condition: this program has counted and returned total scores for each player in categories from x to y. (including x, but excluding y; x<y).
	private int[] calculateScores(int x, int y) {
		int[] eachPlayersScoreList = new int[nPlayers];
		for(int r=1; r<nPlayers+1; r++) {
			int totalScore=0;
			for(int c=x; c<y; c++) {
				totalScore+=results[r-1][c-1];
			}
			eachPlayersScoreList[r-1]=totalScore;
			display.updateScorecard(y, r, eachPlayersScoreList[r-1]);
		}
		return eachPlayersScoreList;
	}
	
	//this method counts the upper scores so that player can get upper bonus
	//pre-condition: any player has played its turn, score board has been updated and this method has gotten each player's upper scores as input
	//post-condition: the program has counted if each player's upper score is above 63, and if it is, then gives the player additional 35 point as bonus.
	private int[] upperBonus(int [] upperScores) {
		int[] upperBonus = new int [nPlayers];
		int bonus = 35;
		for(int i=0; i<nPlayers; i++) {
			if(upperScores[i]>63) {
				upperBonus[i]+=bonus;
			}
			display.updateScorecard(8, i+1, upperBonus[i]);
		}
		return upperBonus;
	}
	
	//this method calculates total score of each player.
	//pre-condition: this method gets upper, lower and bonus scores as inputs.
	//post-condition: the method has summed all the scores for each player and updated total score column.
	private int[] calculateTotalScores(int [] upper, int[] lower, int[] bonus) {
		totalScores = new int [nPlayers];
		for(int i=0; i<nPlayers; i++) {
			totalScores[i]=upper[i]+lower[i]+bonus[i];
			display.updateScorecard(17, i+1, totalScores[i]);
		}
		return totalScores;
	}
	
	//this method finds out winner
	//pre-condition: the game has ended (all categories has been chosen by all players)
	//post-condition: the method found out the winner and congratulated him/her with a message.
	private void findOutWinner() {
		int max = findMaxScore();
		for(int i=0; i<nPlayers;i++) {
			if(totalScores[i]==max) 
				display.printMessage("Congretulations, " + playerNames[i] + ", you're the winner with a total score of " + max + "!");
		}
	}
	
	//this method finds max score of all the players' scores.
	//pre-condition: the game has ended.
	//post-condition: the method has found the max score in order to find out winner.
	private int findMaxScore() {
		int max = totalScores[0];
		for(int i=0; i<nPlayers;i++) {
			if(totalScores[i]>max) totalScores[i]=max;
		}
		return max;
	}

	//this method checks if the dice numbers are appropriate for selected category. 
	//pre-condition:the program has checked that the selected category hasn't been chosen before by the player.
	//post-condition:this method has checked whether the dice numbers are appropriate for selected category.
	private boolean checkCategory(int [] dice, int category) {
		if(category>=1 && category<=6 || category == 15) return true;
		if(category==9) {
			if(checkForSameCards(3)==true) 
				return true;
		}
		if(category==10) {
			if(checkForSameCards(4)==true) return true;
		}
		if(category==14) {
			if(checkForSameCards(5)==true) return true;
		}
		if(category==11) {
			if(checkForSameCards(3)==true && checkForSameCards(2)==true ) return true;
		}
		if(category==12) {
			if(checkForSmallStraight(4)==true) return true;
		}
		if(category==13) {
			if(checkForLargeStraight(5)==true) return true;
		}
		return false;
	}
	
	//this method checks if specific categories are appropriate or not.
	//pre-condition: the method gets x number as input
	//post-condition: if any dice number is repeated for x times in dice array, then the method returns true, in other case it returns false.
	private boolean checkForSameCards(int x) {
		for(int i=0; i<N_DICE; i++) {
			int sameNumber=0;
			for(int j=0; j<N_DICE; j++) {
				if(dice[i]==dice[j]) sameNumber++;
			}
			if(sameNumber==x) return true; 
		}
		return false;
	}
	
	//this method checks if the dice numbers are appropriate for small straight.
	//pre-condition: the player has chosen 12th category (small straight).
	//post-condition: the method defines whether the dice numbers are appropriate for this category or not.
	private boolean checkForSmallStraight(int x) {
		int[] mins = findMins();
		boolean firstVariant = checkForStraight(x, mins[0]);
		boolean secondVariant = checkForStraight(x, mins[1]);
		if(firstVariant==true || secondVariant ==true) return true;
		return false;
	}
	
	//this method checks if the dice numbers are appropriate for large straight.
	//pre-condition: the player has chosen 13th category (large straight).
	//post-condition: the method defines whether the dice numbers are appropriate for this category or not.
	private boolean checkForLargeStraight(int x) {
		int min = findMin();
		if(checkForStraight(x,min)==true) return true;
		return false;
	}
	
	//this method is common for checking large and small straight categories.
	//pre-condition: the method gets the minimum number of dice array, and x (how many numbers should be sorted by growth).
	//post-condition: the method returns true if x numbers are sorted by growths, in other case returns false.
	private boolean checkForStraight(int x, int min) {
		int result = 0;
		for(int i=1; i<N_DICE+1;i++) {
			int nextNumber=0;
			for(int j=0; j<N_DICE; j++) {
				if(dice[j]==min+i) nextNumber++;
			}
			if(nextNumber>0) {
				result++;
			}else {
				break;
			}
		}
		if(result >= x-1) return true;
		return false;
	}
	
	//this method finds minimum number out of dice array.
	private int findMin() {
		min = 6;
		for(int i=0; i<N_DICE; i++) {
			if(dice[i]<min) min=dice[i];
		}
		return min;
	}

	//this method finds 2 minimum numbers out of dice array. mins 0 < mins 1
	private int[] findMins() {
		int mins[] = new int[2];
		mins[0] = Integer.MAX_VALUE;
		mins[1] = Integer.MAX_VALUE;
		
		for(int i=0; i<N_DICE; i++) {
			if(dice[i] < mins[1]) {
				if(dice[i]<mins[0]) {
					mins[1]=mins[0];
					mins[0]=dice[i];
				}else {
					mins[1]=dice[i];
				}
			}
		}
		return mins;
	}
	
	
	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice;
	private int category;
	private int score;
	private int[][] selectedCategories;
	private int [][] results; 
	private int[] upperScores;
	private int[] lowerScores;
	private int [] upperBonus;
	private int[] totalScores;
	private int min;
}
