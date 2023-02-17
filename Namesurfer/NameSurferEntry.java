/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import acmx.export.java.io.FileReader;

import java.io.BufferedReader;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {
private ArrayList<Integer> statistics;
private String name;
private String nums;
/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		statistics = new ArrayList<Integer>();
		name = line.substring(0, line.indexOf(" "));
		nums = line.substring(line.indexOf(" ") + 1);
		
		StringTokenizer tk = new StringTokenizer(nums);
		while(tk.hasMoreTokens()) {
			statistics.add(Integer.parseInt(tk.nextToken()));
		}
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return statistics.get(decade);
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		return name + nums;
	}
}

