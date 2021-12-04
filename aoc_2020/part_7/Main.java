package hjul7;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;


public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-7.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<Bag> bags = new ArrayList<Bag>();
			
			while (scanner.hasNextLine()) {
				String bagInfo = scanner.nextLine().replace(".", "");
				bagInfo = bagInfo.replace("bags", "");
				bagInfo = bagInfo.replace("bag", "");
				String[] parts = bagInfo.split("contain", 2);
				String description = parts[0].trim();
				String[] content = parts[1].split(",");
				
				HashMap<String, Integer> bagContents = new HashMap<String, Integer>();
				
				for ( int i = 0; i < content.length; i++ ) {
					if (content[i].contains("no other")) {
						break;
					}
					content[i] = content[i].trim();
					String[] contentInfo = content[i].split(" ", 2);
					bagContents.put(contentInfo[1], Integer.parseInt(contentInfo[0].trim()));
				}
				
				bags.add(new Bag(description, bagContents));
			}
			
			scanner.close();
			
			String specifiedBag = "shiny gold";
			
			ArrayList<String> allContainers = getAllContainers(bags, specifiedBag);
			ArrayList<String> allContents = getAllBags(bags, specifiedBag);

			System.out.println("Part 1: ");
			System.out.println("All possible containers: " + allContainers.size() );
			
			System.out.println("Part 2: ");
			System.out.println("All the bags a " + specifiedBag + " must contain " + allContents.size());
		
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	
	
	public static Bag findBag(ArrayList<Bag> bags, String description) {
		for (Bag bag: bags) {
			if (bag.getDescription().equals(description)) {
				return bag;
			}
		}
		return null;
	}
	
	public static ArrayList<String> findContainers(ArrayList<Bag> bags, String specificBag) {
		ArrayList<String> matches = new ArrayList<String>();

		for (Bag bag : bags) {
			if (bag.contains(specificBag) ) {
				matches.add(bag.getDescription());
			}
		}
			
		return matches;	
	}
	
	//allt detta är alla väskor som väskan måste innehålla
	
	public static ArrayList<String> getAllBags(ArrayList<Bag> rules, String bag) {
		ArrayList<String> contents = findBag(rules, bag).getContents();
		int start = 0;

		do {
			int startsize = contents.size();
			for (int i = start; i < startsize; i++) {
				contents.addAll(findBag(rules, contents.get(i)).getContents());	
			}
			start = startsize;
			
		} while (start != contents.size());
		
		return contents;
	}

	
	//alla dessa är för att hitta möjliga väskor väskan är innanför
	
	public static ArrayList<String> getAllContainers(ArrayList<Bag> bags, String firstBag) {
		ArrayList<String> containers = findContainers(bags, firstBag);
		int start = 0;
		
		do {
			int startsize = containers.size();
			for (int i = start ; i < startsize; i++ ) {
				ArrayList<String> newMatches = findContainers(bags, containers.get(i));
				for (String bag: newMatches) {
					if (!containers.contains(bag)) {
						containers.add(bag);
					}
				}
			}
			start = startsize;
			
		} while (start != containers.size());
		
		return containers;
	}

}
