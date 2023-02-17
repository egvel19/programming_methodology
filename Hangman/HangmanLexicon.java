
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.IOException;

import acm.util.*;
import acmx.export.java.io.FileReader;
import acmx.export.java.util.ArrayList;

public class HangmanLexicon {
	private ArrayList newList = new ArrayList();
	
	//reads words from file HangmanLexicon.txt
	public HangmanLexicon() {
		try {
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while (true) {
				String line = rd.readLine();
				if (line == null)
					break;
				newList.add(line);
			}
			rd.close();
		} catch (IOException e) {
			throw new ErrorException(e);
		}
	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return newList.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return (String) newList.get(index);
		
	}
}
