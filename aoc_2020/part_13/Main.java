package hjul13;

import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-13.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			int ready = Integer.parseInt(scanner.nextLine());
			String bussesString = scanner.nextLine();
			bussesString = bussesString.replace("x", "0");
			
			int busses[] = Arrays.stream(bussesString.split(",")).mapToInt(Integer::parseInt).toArray();
			
			scanner.close();
			
			int firstbus = 0;
			int busId = 0;
			
			for (int i = ready; busId == 0; i++) {
				for (int bus : busses) {
					if ( bus != 0 ) {
						if ( i % bus == 0) {
							firstbus = i;
							busId = bus;
						}
					}
				}
				
			}
			
			System.out.println("First possible time you can depart is at: " 
					+ firstbus + " with bus " + busId + " this computes to "
					+ ((firstbus - ready)* busId) + "\n");
			

			//PART 2
			
			long time = 0;
			long interval = busses[0];
			boolean found = false;
			int foundI = 0;
			while (!found) {
				time += interval;
				for (int i = 0; i < busses.length; i++) {
					long minute = time + (long)i;
					
					if ( busses[i] == 0) {
						continue;
					}
					if ( minute % busses[i] == 0) {
						if (i > foundI) {
							interval *= busses[i];
							foundI = i;
						}
					} else {
						break;
					}
					
					if (i == busses.length -1) {
						found = true;
					}
				}
			}
			
			System.out.println();
			System.out.println("PART 2");
			System.out.println(time);

			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}

}
