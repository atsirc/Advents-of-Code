package hjul1;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int total = 2020;
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-1.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<Integer> integers = new ArrayList<Integer>();
			
			while (scanner.hasNextLine()) {
				integers.add(Integer.parseInt(scanner.nextLine()));
			}
			
			scanner.close();
			
			for (int i = 0; i < integers.size(); i++) {
				int x = integers.get(i);
				boolean part2found = false;

				
				for (int j = i+1; j < integers.size() - 1; j++) {					
					int y = integers.get(j);
										
					for (int k = i + 2; k < integers.size() - 2; k++) {
							int z = integers.get(k);
							
							if ((x+y+z)==total &! part2found) {
								part2found = true;
								
								System.out.println("PART 2: ");
								System.out.println(x + " + " + y + " + " + z + " = " + (x+y+z));
								System.out.println(x + " * " + y + " + " + z + " = " + (x*y*z));
							}
					}
					
					if (x+y==total) {	
						System.out.println("PART 1: ");
						System.out.println(x + " + " +  y + " = " + (x+y));
						System.out.println(x + " * " + y + " = " + (x*y));
					}							
				}
				
			}
			
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}

}
