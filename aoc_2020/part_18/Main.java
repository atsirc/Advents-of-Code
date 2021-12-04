package hjul18;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.mariuszgromada.math.mxparser.*;


public class Main {

	public static void main(String[] args) {
	
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-18.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<String> lines = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
			
			scanner.close();

			
			System.out.println("Sum: " + lines.stream().mapToLong(n -> count(n)).sum());

	
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
	
	}
	
	static Long count(String str) {
		str = str.replace(" ", "");
		int startI = -1;
		
		int i = 0;
		while (str.contains("(")) {
			char c = str.charAt(i);
			
			switch(c) {
			
			case '(' : startI = i; break;
			case ')' : String prob = str.substring(startI + 1, i);
					String result = solve(prob);
					str = str.substring(0, startI) + result + str.substring(i+1);
					startI = -1;
					i = -1;
			}
			i++;
		}
		
		str = solve(str);
		return Long.parseLong(str);
	}
	
	/* For PART 1 */
	
	static String solve(String str) {
		while(str.contains("+") || str.contains("*")) {
			int end = getLength(str, -1);
			Expression e = new Expression(str.substring(0,end));
			long result = (long)e.calculate();
			str = result + str.substring(end);
		}
		return str;
	}
	
	/* For  Part 2
	 
	static String solve(String str) {
		while(str.contains("+")) {
			int index = str.indexOf("+");
			int start = index -1;
			for ( int i = start; i >= 0; i--) {
				if (!Character.isDigit(str.charAt(i))) {
					break;
				}
				start = i;
			}
			int end = getLength(str, index);
			String expression = str.substring(start,end);
			Expression e = new Expression(expression);
			long result = (long)e.calculate();
			str = str.substring(0, start) + result + str.substring(end);
		}
		while (str.contains("*")) {
			int end = getLength(str, -1);
			String expression = str.substring(0,end);
			Expression e = new Expression(expression);
			long result = (long)e.calculate();
			str = result + str.substring(end);
		}
		
		return str;
	}
	*/
	
	static int getLength(String str, int index) {
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		int start = index == -1 ? 0 : index;
		
		for (int i = start; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '*' || c == '+') {
				indexes.add(i);
			}
		}
		
		if (indexes.size() == 1) {
			return str.length();
		} else {
			return indexes.get(1);
		}
	}
}
