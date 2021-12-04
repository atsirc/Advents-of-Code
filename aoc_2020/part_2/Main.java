package hjul2;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {
	

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-2.txt";
		String absolutePath = directory + File.separator + fileName;
		
		ArrayList<Password> passwords1 = new ArrayList<Password>();
		ArrayList<Password> passwords2 = new ArrayList<Password>();
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
					
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(" ");
				
				String[] digits = parts[0].split("-");
				int digit1 = Integer.parseInt(digits[0]);
				int digit2 = Integer.parseInt(digits[1]);
				
				char letter = parts[1].replace(":", "").charAt(0);
				
				String password = parts[2];
				
				passwords1.add(new Password(digit1, digit2, letter, password));
				passwords2.add(new Password2(digit1, digit2, letter, password));
				
			}
			
			scanner.close();
			
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
		System.out.println("Part 1: " + check_passwords(passwords1));
		System.out.println("Part 2: " + check_passwords(passwords2));
		
	}
	
	public static String check_passwords(ArrayList<Password> passwords) {
		
		int correct_passwords = 0;
		
		for (Password pass: passwords) {
			if (pass.isCorrect()) {
				correct_passwords++;
			}
		}
		
		return "Number of correct passwords: " + correct_passwords;
		
	}

}