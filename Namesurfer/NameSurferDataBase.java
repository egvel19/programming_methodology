import java.io.BufferedReader;
import java.io.IOException;

import acmx.export.java.io.FileReader;
import java.util.*;

import acm.util.ErrorException;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	private HashMap <String, NameSurferEntry> dataBase;

	
/* Constructor: NameSurferDataBase(filename) */
// Creates a new NameSurferDataBase and initializes it using the
// data in the specified file.  The constructor throws an error
// exception if the requested file does not exist or if an error
// occurs as the file is being read.
// @throws IOException 
	public NameSurferDataBase(String filename) throws IOException {
		dataBase = new HashMap<String, NameSurferEntry>();
		try {
			BufferedReader rd = new BufferedReader(new FileReader(NAMES_DATA_FILE));
			while(true) {
				String line = rd.readLine();
				if(line == null) break;
				NameSurferEntry nameSurfer = new NameSurferEntry(line);
				dataBase.put(nameSurfer.getName(), nameSurfer);
				}
			rd.close();
		}catch (IOException e) {
			throw new IOException(e);
		}
	}
	
	
/* Method: findEntry(name) */
// Returns the NameSurferEntry associated with this name, if one
// exists.  If the name does not appear in the database, this
// method returns null.
	public NameSurferEntry findEntry(String name) {
		String firstLetter = name.substring(0,1).toUpperCase();
		String convertedName = firstLetter + name.substring(1).toLowerCase();
		if(dataBase.containsKey(convertedName)) {
			return dataBase.get(convertedName);
	}else {
		return null;
	}
}
	
}

