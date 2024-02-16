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
				if(!args[2].equals("make") && !args[2].equals("model") && !args[2].equals("transmission")
				   && !args[2].equals("vClass")  && !args[2].equals("engineDispl") && !args[2].equals("cylinders")
				  	&& !args[2].equals("year") && !args[2].equals("fuelType")){
					System.err.println("USAGE: java tester 2 <filename> <columnName> <value>");
					System.err.println("<columnName> should be one of the following options: 
							make, model, transmission, vClass, engineDispl, cylinders,
							year, fuelType");
					System.exit(1);
				}
				optionTwo(makes, args[2], args[3]);
			}else if(option == 3){
				optionThree(makes);
			}else if(option == 4){
				optionFour(makes);
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

	public static void optionTwo(Set<Make> makes, String column, String value){
		if(column.equals("make")){
			filterByMake(makes, value);
		}
		else if(column.equals("model")){
			filterByModel(makes, value);
		}
		else if(column.equals("cylinders")){
			filterByCylinders(makes, value);
		}
		else if(column.equals("engineDispl")){
			filterByEngineDispl(makes, value);
		}
		else if(column.equals("transmission")){
			filterByTransmission(makes, value);
		}
		else if(column.equals("vClass")){
			filterByVClass(makes, value);
		}
		else if(column.equals("fuelType")){
			filterByFuelType(makes, value);
		}
		else if(column.equals("year")){
			filterByYear(makes, value);
		}
	}

	private static Set<ModelSettings> setToFilter(Set<Makes> makes){
		Set<ModelSettings> result = new TreeSet<>((m1, m2)-> m1.getMake().compareToIgnoreCase(m2.getMake()));
		for(Make m : makes){
			Set<ModelSettings> cur = m.getModelSettingSet();
			for(ModelSettings ms : cur){
				result.add(ms);
			}
		}
		return result;
	}

	private static void filterByMake(Set<Makes> makes, String value){
		Set<ModelSettings> set = setToFilter(makes);
		set = set.stream().filter(m -> m.getMake().contains(value)).collect(Collectors.toCollection(LinkedHashSet::new));

		System.out.println("Results");
		System.out.println("================");
		for(ModelSettings ms : set){
			System.out.println(ms.getMake() +  ", " + ms.getModelName() + ", " );
		}
	}

	
	private static void filterByModel(Set<Makes> makes, String value){
		Set<ModelSettings> set = setToFilter(makes);
		set = set.stream().filter(m -> m.getModelName().contains(value)).collect(Collectors.toCollection(LinkedHashSet::new));

		System.out.println("Results");
		System.out.println("================");
		for(ModelSettings ms : set){
			System.out.println(ms.getMake() +  ", " + ms.getModelName());
		}
	}

	
	private static void filterByCylinders(Set<Makes> makes, String value){
		Set<ModelSettings> set = setToFilter(makes);
		set = set.stream().filter(m -> m.getCylinders() == Integer.parseInt(value)).collect(Collectors.toCollection(LinkedHashSet::new));

		System.out.println("Results");
		System.out.println("================");
		for(ModelSettings ms : set){
			System.out.println(ms.getMake() +  ", " + ms.getModelName() + ", cylinders: "  + ms.getCylinders());
		}
	}

	
	private static void filterByEngineDispl(Set<Makes> makes, String value){
		Set<ModelSettings> set = setToFilter(makes);
		set = set.stream().filter(m -> Math.abs(m.getDsiplacement() - Double.parseDouble(value)) < 0.00001).collect(Collectors.toCollection(LinkedHashSet::new));

		System.out.println("Results");
		System.out.println("================");
		for(ModelSettings ms : set){
			System.out.println(ms.getMake() +  ", " + ms.getModelName() + ", displacement: " + ms.getDisplacement());
		}
	}
	private static void filterByTransmission(Set<Makes> makes, String value){
		Set<ModelSettings> set = setToFilter(makes);
		set = set.stream().filter(m -> m.getTransmission().contains(value)).collect(Collectors.toCollection(LinkedHashSet::new));

		System.out.println("Results");
		System.out.println("================");
		for(ModelSettings ms : set){
			System.out.println(ms.getMake() +  ", " + ms.getModelName() + ", transmission: " + ms.getTransmission());
		}
	}
	private static void filterByVClass(Set<Makes> makes, String value){
		Set<ModelSettings> set = setToFilter(makes);
		set = set.stream().filter(m -> m.getVClass().contains(value)).collect(Collectors.toCollection(LinkedHashSet::new));

		System.out.println("Results");
		System.out.println("================");
		for(ModelSettings ms : set){
			System.out.println(ms.getMake() +  ", " + ms.getModelName() + ", VClass: " + ms.getVClass());
		}
	}
	private static void filterByFuelType(Set<Makes> makes, String value){
		Set<ModelSettings> set = setToFilter(makes);
		set = set.stream().filter(m -> m.getFuelType().contains(value)).collect(Collectors.toCollection(LinkedHashSet::new));

		System.out.println("Results");
		System.out.println("================");
		for(ModelSettings ms : set){
			System.out.println(ms.getMake() +  ", " + ms.getModelName() + ", Fuel Type: " + ms.getFuelType());
		}
	}
	private static void filterByYear(Set<Makes> makes, String value){
		Set<ModelSettings> set = setToFilter(makes);
		set = set.stream().filter(m -> m.getYear() == Integer.parseInt(value)).collect(Collectors.toCollection(LinkedHashSet::new));

		System.out.println("Results");
		System.out.println("================");
		for(ModelSettings ms : set){
			System.out.println(ms.getMake() +  ", " + ms.getModelName() + ", year: " + ms.getYear() );
		}
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
					int comb = vClassMPG.get(vClass + mpg.getComb());
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
		Set<String> makeNames = new TreeSet<>((m1, m2) -> m1.compareToIgnoreCase(m2));
		Map<String , Map<Integer, Integer>> numModels = new HashMap<>();

		for(Make m : makes){
			String name = m.getMakeName();
			makeNames.add(name);
			numModels.put(name, new HashMap<>());
			Map<Integer, Integer> modelsByYear = numModels.get(name);
			Set<ModelSettings> msSet = m.getModelSettingSet();
			for(ModelSettings ms : msSet){
				int Year = ms.getYear();
				if(modelsByYear.containsKey(Year)){
					int cur = modelsByYear.get(Year);
					modelsByYear.put(Year, cur + 1);
				}else{
					modelsByYear.put(Year, 1);
				}
			}
		}

		System.out.println("Models produced by each make in a given year");
		System.out.println("============================================");

		for(String name : makeNames){
			System.out.println(name + "------------- ");
			Map<Integer, Integer> modelsByYear = numModels.get(name);
			for(Map.Entry<Integer, Integer> entry : modelsByYear.entrySet()){
				System.out.println("\t" + entry.getKey() + ": " + entry.getValue() + " models");
			}
		}
	}


}
