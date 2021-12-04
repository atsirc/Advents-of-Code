package hjul19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-19.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			HashMap<Integer, String> rules = new HashMap<Integer, String>();
			ArrayList<String> messages = new ArrayList<String>();
			ArrayList<Integer> done = new ArrayList<Integer>();
			ArrayList<String> all = new ArrayList<String>();
			boolean doneWithRules = false;
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				all.add(line);
				if (line.isEmpty()) {
					doneWithRules = true;
					continue;
				}
				if (!doneWithRules) {
					String[] parts = line.split(": ");
					Integer index = Integer.parseInt(parts[0]);
					String rule = parts[1].replace("\"", "");
					if (rule.length() == 1) {
						done.add(index);
					}
					rules.put(index, rule);
					
				} else {
					messages.add(line);
				}
			}
			scanner.close();

			
			//PART 1:
			System.out.println(all);
						
 			HashMap<Integer, String> rulesReadable = simplify(rules, done);
			String rule0 = rulesReadable.get(0);
			List<String> rules0 = Arrays.asList(rule0.split(","));
			
			int sum = 0;
			for (String rule : rules0) {
				List<String> possibleMessages = messages.stream().filter(n -> n.length() == rule.length()).collect(Collectors.toList());
				sum += possibleMessages.stream().filter(n -> n.equals(rule)).count();
			}
			
			System.out.println("Matches with rule 0: " + sum);
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	
	static HashMap<Integer, String> simplify(HashMap<Integer, String> rules, ArrayList<Integer> readyList) {
		while (readyList.size() < rules.size()) {
			for (Integer key: rules.keySet()) {
				
				if (!readyList.contains(key) && canBeRead(rules.get(key), readyList)) {
					read(key, rules);
					readyList.add(key);
				}
			}
		}
		return rules;
	}
	
	static void read(Integer index, HashMap<Integer, String> rules) {
		String[] integers = rules.get(index).split(" ");
		rules.put(index, "");
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		int i = 0;
		while (i <= integers.length) {
			if (i == integers.length || integers[i].equals("|")) {
				String answers = combine(indexes, rules);
				String oldRules = rules.get(index);
				String newAnswers = oldRules.length() > 0 ? oldRules + "," + answers : answers;
				rules.put(index, newAnswers);
				indexes.clear();
			} else {
				indexes.add(Integer.parseInt(integers[i]));
			}
			i++;
		}
	}
	
	static String combine(ArrayList<Integer> indexes, HashMap<Integer, String> rules) {
		ArrayList<String> combinationes = new ArrayList<String> ();
		List<String> initial = Arrays.asList(rules.get(indexes.get(0)).split(","));
		combinationes.addAll(initial);
		for (int i = 1; i < indexes.size(); i++) {
			List<String> temp = new ArrayList<String>();
			List<String> addons =  Arrays.asList(rules.get(indexes.get(i)).split(","));
			for (String addon : addons) {
				for (int j = combinationes.size()-1; j >= 0; j--) {
					String beginning = combinationes.get(j);
					temp.add(beginning + addon);
				}
			}
			combinationes.clear();
			combinationes.addAll(temp);
		}
		return String.join(",", combinationes);
	}
	
	static boolean canBeRead(String rule, ArrayList<Integer> readyList) {
		String[] parts = rule.split(" ");
		for (String part : parts) {
			if (!part.equals("|")) {
				int i = Integer.parseInt(part);
				if (!readyList.contains(i)) 
					return false;
			}
		}
		return true;
	}

}
