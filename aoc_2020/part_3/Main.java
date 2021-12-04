package hjul3;

import java.util.ArrayList;

import java.io.File;
import java.util.Scanner;

public class Main {
	

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-3.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<String> strs = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
				strs.add(scanner.nextLine());
			}
			
			scanner.close();
			
			System.out.println("part 1: " + count_trees(strs, 3, 1));
			
			long a = count_trees(strs, 1,1);
			long b = count_trees(strs, 3,1);
			long c = count_trees(strs, 5,1);
			long d = count_trees(strs, 7,1);
			long e = count_trees(strs, 1,2);		
			
			System.out.println("part 2: Multiplied answer: " + a*b*c*d*e);

		} catch(Exception e) { 
			System.out.println("Couldn't find file");
		}
		
	}

	public static int count_trees(ArrayList<String> map, int move_rigth, int move_down) {
		
		int rigth = 0;
		int trees = 0;
		char tree = '#';
		
		for ( int i = move_down; i < map.size(); i += move_down ) {
			String line = map.get(i);
			rigth += move_rigth;
			char c = line.charAt( rigth % line.length() );
			
			if (c == tree) {
				trees++;
			}
		}
		
		return trees;
	}
	
}
