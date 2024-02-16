package edu.baylor.cs.csi3471;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Tester {

	private static final int FILE_NAME = 1;
	private static final int OPTION = 0;

	private static int readOption(String[] args) {
		Integer option = null;
		if (args.length != 2) {
			System.err.println("USAGE: java Tester <1...4> <filename>");
			System.exit(1);
		} else {
			try {
				option = Integer.parseInt(args[OPTION]);
			} catch (NumberFormatException e) {
				System.err.println("call as java Tester <1...4> <filename>");
				System.exit(1);
			}
		}
		return option;
	}

	
	 public static Collection<Make> populateSet(Collection<Make> set, String[] line){ 
		 Make make = null;
		 for(Make m : set) {
			 if(m.getMakeName().equals(line[6])) {
				 make = m;
				 break;
			 }
		 }
		 if(make == null) {
			 make = new Make(line);
			 set.add(make);
		 }
		 
		 ModelSettings modelSettings = new ModelSettings(line);
		 make.addModelSettings(modelSettings);
		 
		 return set;
	 }
	 

	private static Set<Make> loadCSV(String file) throws FileNotFoundException {
		BufferedReader reader = null;
		try {
			// ok, this is much faster than scanner :)
			reader = new BufferedReader(new FileReader(new File(file)));
			String line = null;
			Collection<Make> set = new HashSet<>();
			int skipLine = 0;
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(",");
				
				if(skipLine > 1) set = populateSet(set, split);
				skipLine++;
			} 

			return new HashSet<>(set);
		} catch (IOException e) {
			String hint = "";
			try {
				hint = "Current dir is: " + new File(".").getCanonicalPath();
			} catch (Exception local) {
				hint = local.getLocalizedMessage();
			}
			throw new FileNotFoundException(e.getLocalizedMessage() + "\n" + hint);

		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		}

	}

	public static void main(String[] args) {
		int option = readOption(args);

		Set<Make> makes = null;
		try {
			makes = loadCSV(args[FILE_NAME]);
			
			if(option == 1) {
				optionOne(makes);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}
	
	public static void optionOne(Set<Make> makes) {
		Set<Make> SortedSet = new TreeSet<>((m1, m2)-> m2.getMakeName().compareToIgnoreCase(m1.getMakeName()));
		SortedSet.addAll(makes);
		
		System.out.println("Total Makes: " + makes.size());
		System.out.println("===============");
		
		for(Make m : SortedSet) {
			System.out.println(m.getMakeName() + ", Models: " + m.getModelSettingSet().size());
			System.out.println("    -  -  -     ");
		}
		
		System.out.println("===============");
		
		for(Make m : SortedSet) {
			System.out.println(m.toString());
			System.out.println("    -  -  -  -  -  -  -  -  -  -  -   ");
		}
		
	}
}
