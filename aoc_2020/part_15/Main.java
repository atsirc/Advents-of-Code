package hjul15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.*;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-15.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			String[] input = scanner.nextLine().split(",");
			
			scanner.close();

			long[] longs = Stream.of(input).mapToLong(n ->Long.parseLong(n.trim())).toArray();			

			System.out.println("PART 1:");
			
			ArrayList<Long> part1 = game(longs, 2020);
			
			System.out.println(part1.get(part1.size()-1));
			System.out.println(part1);
			System.out.println("\nPART 2:");
			
			long part2 = game2(longs, 30000000);
			
			System.out.println(part2);
						
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	
	public static ArrayList<Long> game(long[] numbers, int stop) {
		
		ArrayList<Long> turns = new ArrayList<Long>();
		
		for (int i = 0; turns.size() < stop; i++) {
			if (numbers.length <= i) {
				long j = turns.get(i-1);
				long k = turns.subList(0, i - 1).lastIndexOf(j); 
				if (k == -1) {
					turns.add(0L);
					
				} else {
					turns.add(i-1-k);
				}
			} else {
				turns.add(numbers[i]);
			}
		}
		return turns;
	}
	
	static long game2(long[] numbers, int stop) {

		HashMap<Long, Long> list = new HashMap<Long, Long>();
		
		for (int i = 0; i < numbers.length; i++) {
			list.put(numbers[i], (long)i);
		}

		long currentKey = numbers[numbers.length -1];
		for (long pos = numbers.length; pos < stop; pos++) {
			long newKey = (list.getOrDefault(currentKey, -1L) >= 0) ? pos - list.get(currentKey) - 1 : 0;
			list.put(currentKey, (pos - 1));
			currentKey = newKey;
		}
		return currentKey;
		
	}


}
