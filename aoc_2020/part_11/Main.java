package hjul11;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-11.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			ArrayList<ArrayList<Square>> grid = new ArrayList<ArrayList<Square>>();
			
			while (scanner.hasNextLine()) {
				ArrayList<Square> row = new ArrayList<Square>();
				char[] charRow = scanner.nextLine().toCharArray();
				for (char c : charRow) {
					row.add(new Square(c));
				}
				grid.add(row);
			}
			
			scanner.close();
			
			Layout layout = new Layout(grid);
			Layout2 test = new Layout2(grid);
			
			loop(layout, 1);
			
			loop(test, 2);
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	
	
	public static void loop(Layout layout, int i) {
		Layout previous = layout.getCopy();
		while (true) {
			Layout newArrangement = layout.turn();
			if (newArrangement.equals(previous)) {
				break;
			}
			previous.copy(newArrangement);
		}
		
		System.out.println("svar " + i);
		System.out.println("upptagna säten " + previous.occuppiedSeats());
		
		
	}

}
