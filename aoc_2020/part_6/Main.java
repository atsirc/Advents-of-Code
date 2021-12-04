package hjul6;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-6.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
						
			ArrayList<String> groups = new ArrayList<String>();
			String group = "";
						
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					groups.add(group);
					group = "";
				} else {
					group += (line + " ");
				}
			}
			
			groups.add(group);
			
			scanner.close();
			
			System.out.println("PART 1:");
//			System.out.println(countUniqueAnswers(groups));
			System.out.println(groups.stream().mapToInt(n -> uniqueChars(n).length()).sum());
			System.out.println();
			System.out.println("PART 2:");
//			System.out.println(countSameAnswers(groups));
			System.out.println(groups.stream().mapToInt(n -> sameAnswers(n).length()).sum());
		
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
		
	public static String uniqueChars(String str) {
		str = str.replace(" ", "");
		String unique = str.replaceAll("([a-z])(?=[a-z]*?\\1)", "");
		return unique;
	}
	
	public static int countUniqueAnswers(ArrayList<String> list) {
		int sum = 0;
		for (String str: list) {
			sum += uniqueChars(str).length();
		}
		return sum;
	}
	
	public static String sameAnswers(String str) {
		String allAnswers = uniqueChars(str);
		ArrayList<String> individualAnsw = new ArrayList<String>(Arrays.asList(str.split(" ")));
		String answers = "";
		
		for (int i= 0; i < allAnswers.length(); i++) {
			String current = allAnswers.charAt(i) + "";
			
			if (individualAnsw.stream().allMatch(n -> n.contains(current))) {
				answers += current;
			}
			
		}
		return answers;
	}
	
	public static int countSameAnswers(ArrayList<String> list) {
		int sum = 0;
		for (String str: list) {
			sum += sameAnswers(str).length();
		}
		return sum;
	}

}
