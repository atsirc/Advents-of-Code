package hjul9;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class Main {
	

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-9.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<Long> numbers = new ArrayList<Long>();
			
			while (scanner.hasNextLine()) {
				String number = scanner.nextLine();
				numbers.add(Long.parseLong(number));
			}
			
			scanner.close();
			int preamble = 25;
			
			System.out.println("PART 1:");
			long flaw = findFlaw(numbers, preamble);
			System.out.println("The failing number " + flaw);
			
			System.out.println("PART 2:");
			if (flaw != -1) {
				List<Long> answer = findContiguousSet(numbers, flaw);
				System.out.println("Biggest and smallest number in the contigious set that add up to "
						+ flaw + " adds up to "
						+ (answer.stream().mapToLong(n -> n).min().getAsLong()
						+ answer.stream().mapToLong(n -> n).max().getAsLong()));
			}
									
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	public static boolean findPair(List<Long> numbers, long total) {
		for (int j = 0; j < numbers.size(); j++) {					
			long y = numbers.get(j);
			
			for (int k = j+1; k < numbers.size(); k++) {
				long z = numbers.get(k);
				
				if (y + z == total) {
					return true;
				}
				
			}
		}
		return false;
		
	}
	
	public static long addNumbers(List<Long> numbers) {
		return numbers.stream().mapToLong(n -> n).sum();
	}
	
	public static List<Long> findContiguousSet(ArrayList<Long> numbers, long total) {
		
		for (int contigiousAmount = 2; contigiousAmount <= numbers.size(); contigiousAmount++) {
			for (int i = 0; i <= numbers.size()-contigiousAmount; i++) {
				List<Long> sublist = numbers.subList(i, i + contigiousAmount);
				if (addNumbers(sublist) == total) {
					return sublist;
				}
			}
		}
		return null;
	}
	
	public static long findFlaw(ArrayList<Long> numbers, int preamble) {
		long answer = -1;
		for (int i = 0; i < numbers.size(); i++) {
			List<Long> sublist = numbers.subList(i, i + preamble);
			long total = numbers.get(preamble + i);
			if (!findPair(sublist, total)) {
				answer = total;
				break;
			}		
		}
		return answer;
	}
	
}