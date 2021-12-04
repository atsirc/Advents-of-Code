package hjul17;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/test-17.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<String> lines = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
			
			scanner.close();
			
			String[] startValues = new String[lines.size()];
			
			int i = 0;
			for (String line : lines) {
				startValues[i] = line;
				i++;
			}
			
			Cube cube = new Cube(startValues);
			Cube2 cube2 = new Cube2(startValues);

			int j = 0;
			while (j < 6) {
				cube.loop();
				cube2.loop();
				j++;
			}
			System.out.println(cube);

			
			System.out.println("\nPart 2: ");
			System.out.println(cube2);

			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}

}
