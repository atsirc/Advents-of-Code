package hjul10;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-10.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<Long> integers = new ArrayList<Long>();
			
			while (scanner.hasNextLine()) {
				long i = Long.parseLong(scanner.nextLine());
				integers.add(i);
			}
			
			scanner.close();

			integers.sort((a, b) -> a.compareTo(b));
			
			long starter = 0;
			long last = integers.get(integers.size() - 1 ) + 3;
			integers.add(0, starter);
			integers.add(last);
			System.out.println(integers);
			long[] numbers = new long[integers.size()];
			
			for (int i = 0; i < integers.size(); i++) {
				numbers[i] = integers.get(i);
			}
			
			int one = 0;
			int three = 0;
			
			for (int i = 0; i < numbers.length; i++) {
				if (next(numbers, i, 1)) {
					one++;
				} else if (next(numbers, i, 3)) {
					three++;
				}
			}
			
			System.out.println("One jolt differences " + one); 
			System.out.println("Three jolt differences " + three);
			System.out.println("Answer part 1: " + one*three);


			// sumarrayn anger antalet möjligheter per steg i serien
			long[] sumArray = new long[numbers.length];

			//initvalue 1 because original version is 1 version.
			sumArray[0] = 1;
			for( int i = 1; i < numbers.length; i++ ){
				sumArray[i] = 0;
				int j = i-1;
				while(j >= 0 && numbers[i] - numbers[j] <= 3){
					sumArray[i] += sumArray[j];
					j--;
				}
			}
			
			ArrayList<Long> alternatives = new ArrayList<Long>();
			alternatives.add(0, 1L);
			
			for (int i = 1; i < numbers.length; i++ ) {
				alternatives.add(i, 0L);
				int j = i -1;
				while (j >=0 && numbers[i] - numbers[j] <=3) {
					long newVal = alternatives.get(i);
					newVal += alternatives.get(j);
					alternatives.set(i, newVal);
					j--;
				}
			}
			
			
			System.out.println("Part 2 real answer: ");
			System.out.println(sumArray[sumArray.length-1]);
			System.out.println(alternatives.get(alternatives.size()-1));

		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	
	public static boolean next(long[] array, int current, int amount){
			if (current + 1 < array.length) {
				if (array[current + 1] - array[current] == amount) {
					return true;
				}
			}
			return false;
		}


	/*
	* So this solution works, but not with a bigger dataset.
	* Beecause I don't know anything about optimization I started by using ArayList<Integer>
	* but changed to long[] because the answer would be too big. Though it is clear I got closer to an
	* answer with ArrayList.
	 */

		public static boolean twoSteps(long[] array, int current, int amount) {
			if (current + 2 < array.length) {
				if (array[current+2]-array[current] == amount) {
					return true;
				}
			}
			return false;
		}


		public static long[][] findAll( long[] array ) {
		int len = array.length;
		long[][] all = new long[1][len];
		all[0] = array.clone();
		int i = 0;
		
		while (i < len - 3 ) {
			
			long[][] newSeqs = new long[1][len];
			
			for (long[] arr : all) {
				
				if (arr.length - i - 1 > 0) {
					
					long[] copy = arr.clone();	
					while (true) {
						if (arr.length - i - 1 > 0) {
							long[] curr = copy.clone();
							if (twoSteps(curr, i, 2) || twoSteps(curr, i, 3)) {
								copy = new long[curr.length - 1];

								for (int x = 0, y = 0; x < curr.length; x++) {
								    if (x != i+1) {
								        copy[y++] = curr[x];
								    }
								}
								int newSeqslen = newSeqs.length;
								if (newSeqs[0][1] != 0) newSeqslen++;
								long[][] newSeqsHolder = new long[newSeqslen][len];
								for (int x = 0; x < newSeqslen-1; x++) {
								    newSeqsHolder[x] = newSeqs[x].clone();
								    
								}
								newSeqsHolder[newSeqslen -1] = copy.clone();
								newSeqs = newSeqsHolder.clone();
								
							}
							else break;
						} else break;
					}
				}
			}

			long[][] newAll = Arrays.copyOf(all, all.length + newSeqs.length);
			int start = all.length;
			for (int j = start; j < newAll.length; j++) {
				newAll[j] = newSeqs[j-start].clone();
			}
			all = newAll;
			i++;
		}
		return all;
	}
	

}
