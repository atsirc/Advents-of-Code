package hjul5;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.util.Scanner;

public class Main {
	

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-5.txt";
		
		/*
		 * Testsvar
		 * 
		 * FBFBBFFRLR: row 44, column 5, seat ID 357
		 * BFFFBBFRRR: row 70, column 7, seat ID 567
		 * FFFBBBFRRR: row 14, column 7, seat ID 119
		 * BBFFBBFRLL: row 102, column 4, seat ID 820
		 * 
		 * */
		
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<BoardingPass> passes = new ArrayList<BoardingPass>();
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				char[] row = line.substring(0, 7).toCharArray();
				char[] column = line.substring(7).toCharArray();
				passes.add( new BoardingPass(row, column));
			}
			
			scanner.close();
						
			//Answer to part one
			biggestId(passes);	
			//Answer to part two
			missingId(passes);	
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	
	public static void biggestId(ArrayList<BoardingPass> passes) {
		BoardingPass pass = passes.stream().reduce(BoardingPass::compare).get();
		
		System.out.println("\n***");
		System.out.println("Boarding pass with biggest seat ID: " + pass);
		System.out.println("***\n");
		
	}

	public static void missingId(ArrayList<BoardingPass> passes) {
		Collections.sort(passes);
		
		int i = 0;
		int startID = passes.get(0).getId();
		
		for (BoardingPass pass : passes) {
			
			if ((startID+i) != pass.getId()) {
				break;
			} else {
				i++;
			}
			
		}
		
		if (startID < passes.get(i+1).getId()) {
			System.out.println("Seat before me: " + passes.get(i-1));
			System.out.println("Missing: " + (startID + i));
			System.out.println("Seat after me: " + passes.get(i));
		}
		
	}
	
}