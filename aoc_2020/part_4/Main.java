package hjul4;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {
	

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-4.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<Passport> passports = new ArrayList<Passport>();
			ArrayList<Passport> passports2 = new ArrayList<Passport>();
			
			String passport = "";
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					passports.add(new Passport(passport));
					passports2.add(new Passport2(passport));
					
					passport = "";
				} else {
					passport += line + " ";
				}
			}
			
			scanner.close();

			System.out.println("PART 1: \n" + "Valid passports: " + validPassports(passports));
			System.out.println("");
			System.out.println("PART 2: \n" + "Valid passports: " + validPassports(passports2));
			System.out.println("");
			System.out.println("Passports in total: " + passports.size());
			
			
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("Couldn't find file");
		}
		
	}
	
	public static int validPassports(ArrayList<Passport> passports) {
		int valid_passports = 0;
		
		for (Passport pass : passports) {
			if (pass.isValid()) {
				valid_passports++;
			}
		}
		return valid_passports;
	}

}
