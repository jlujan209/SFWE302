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
import java.util.Map;
import java.util.HashMap;

public class Tester {

	private static final int FILE_NAME = 1;
	private static final int OPTION = 0;

	private static int readOption(String[] args) {
		Integer option = null;
		//2 for options 1,3,4 and 4 for option 2 which has extra arguments
		if (args.length != 2 && args.length != 4) {
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
			}else if(option == 2){
				if(!args[2].equals("makeName") && !args[2].equals("model")){
					System.err.println("USAGE: java tester 2 <filename> <columnName> <value>");
					System.err.println("<columnName> only considers \"makeName\" or \"model\" ");
					System.exit(1);
				}
				optionTwo(makes);
			}else if(option == 3){
				
			}else if(option == 4){
				
			}else{
				System.err.println("USAGE: java tester <1...4> <filename>");
				System.exit(1);
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

	public static void optionTwo(Set<Makes> makes){
		
	}

	public static void optionThree(Set<Makes> makes){
		Set<String> vClasses = new TreeSet<>((m1, m2)-> m1.compareToIgnoreCase(m2));
		Map<String, Integer> vClassMPG = new HashMap<>();
		for(Make m : makes){
			Set<ModelSettings> fit = m.getModelSettingSet();
			for(ModelSettings ms : fit){
				MPG mpg = ms.getMpg();
				String vClass = ms.getVClass();
				if(vClassMPG.containsKey(ms.getVClass())){
					int comb = vClassMPG.get(vClass + mpg.getComb();
					vClassMPG.put(vClass, comb);
				}else{
					vClassMPG.put(vClass, mpg.getComb());
				}
				vClasses.add(vClass);
			}
		}

		System.out.println("EPA Vehicle Size Classes: ");
		System.out.println("==========================");
		
		for(String vClass : vClasses){
			System.out.println(vClass + ", Total Combined MPG: " + vClassMPG.get(vClass));
		}
	}

	public static void optionFour(Set<Makes> makes){
		
	}
}
