package hjul16;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.Range;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-16.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<String> lines = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
			
			scanner.close();
			
			HashMap<String, ArrayList<Range<Integer>>> fields = new HashMap<String, ArrayList<Range<Integer>>>();
			ArrayList<Integer> myTicket = new ArrayList<Integer>();
			ArrayList<ArrayList<Integer>> otherTickets = new ArrayList<ArrayList<Integer>>();
			
			int emptyLine = 0;
			
			for (String line: lines) {
				if (line.isEmpty()) {
					emptyLine++;
					continue;
				}
				if (emptyLine == 0) {
					String[] strAndRanges = line.split(": ");
					String str = strAndRanges[0];
					String[] rangesStr = strAndRanges[1].split(" or ");
					ArrayList<Range<Integer>> array = new ArrayList<Range<Integer>>();
					
					for (String range: rangesStr) {
						String[] ranges = range.split("-");
						Range<Integer> realRange = Range.between(Integer.parseInt(ranges[0]), Integer.parseInt(ranges[1]));
						array.add(realRange);
					}
					fields.put(str, array);
				}
				if (emptyLine == 1) {
					if (line.contains("your")) continue;
					String list[] = line.split(",");
					for (String item : list) {
						myTicket.add(Integer.parseInt(item));
					}
				}
				if (emptyLine == 2) {
					if (line.contains("nearby")) continue;
					String list[] = line.split(",");
					ArrayList<Integer> ticket = new ArrayList<Integer>();
					for (String item : list) {
						ticket.add(Integer.parseInt(item));
					}
					otherTickets.add(ticket);
				}
			}
			
			ArrayList<Range<Integer>> trueRanges = findTrueRanges(fields);			
			ArrayList<Integer> allInvalidNumbers = new ArrayList<Integer>();
			
			for (int i = otherTickets.size()-1; i > 0; i--) {
				ArrayList<Integer> ticket = otherTickets.get(i); 
					ArrayList<Integer> invalidVals = findInvalidValues(ticket,trueRanges);
					if (invalidVals.size() > 0) {
						otherTickets.remove(i);
						allInvalidNumbers.addAll(invalidVals);
					}
			}
			
		//	otherTickets.add(myTicket);
			
			System.out.println("Part 1: ");

			System.out.println("Ticket scanning error rate: " + allInvalidNumbers.stream().mapToInt(n->n).sum());
			System.out.println();

			
			System.out.println("Part 2");
			
			HashMap<String, ArrayList<Integer>> answer = compatibleFields(fields, otherTickets);
			String[] answerStr = new String[20];
			
			long myTicketValue = 1;
			
			for (String key: answer.keySet()) {
				answer.get(key);
				int number = answer.get(key).get(0);
				answerStr[(number-1)] = key;
				if (key.contains("departure")) {
					long val = myTicket.get(number-1);
					myTicketValue *= val;
				}
			}
			
			System.out.println("Multiplied value " + myTicketValue);
			System.out.println();	
			
			System.out.println();
			System.out.println();
			System.out.println();
			
			System.out.println(":----------------------------------------------------------------------------------------:");
			System.out.println("|  " + answerStr[0]+ ": " + myTicket.get(0) + "\t   " + answerStr[1]+ ": " + myTicket.get(1) + "  " + answerStr[2]+ ": " + myTicket.get(2) + "\t " + answerStr[3]+ ": " + myTicket.get(3) + "  |");
			System.out.println("|  " + answerStr[4]+ ": " + myTicket.get(4) + "\t\t   " + answerStr[5]+ ": " + myTicket.get(5) + "\t  " + answerStr[6]+ ": " + myTicket.get(6) + "\t " + answerStr[7]+ ": " + myTicket.get(7) + "\t\t |");
			System.out.println("|  " + answerStr[8]+ ": " + myTicket.get(8) + "   " + answerStr[9]+ ": " + myTicket.get(9) + "\t  " + answerStr[10]+ ": " + myTicket.get(10) + "\t " + answerStr[11]+ ": " + myTicket.get(11) + "  \t\t |");
			System.out.println("|  " + answerStr[12]+ ": " + myTicket.get(12) + "  " + answerStr[13]+ ": " + myTicket.get(13) + "\t  " + answerStr[14]+ ": " + myTicket.get(14) + "\t " + answerStr[15]+ ": " + myTicket.get(15) + "  |");
			System.out.println("|  " + answerStr[16]+ ": " + myTicket.get(16) + "\t\t   " + answerStr[17]+ ": " + myTicket.get(17) + "\t  " + answerStr[18]+ ": " + myTicket.get(18) + "\t " + answerStr[19]+ ": " + myTicket.get(19) + "\t\t |");
			System.out.println(":----------------------------------------------------------------------------------------:");

			
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	
	//Part 1
	
	static ArrayList<Range<Integer>> findTrueRanges(HashMap<String, ArrayList<Range<Integer>>> fields) {
		ArrayList<Range<Integer>> trueList = new ArrayList<Range<Integer>>();
		for (String key : fields.keySet()) {
			ArrayList<Range<Integer>> array = fields.get(key);
			
			for (Range<Integer> range: array) {
				trueList.add(range);
			}
		}
		
		trueList = removeUnnescessaryRanges(trueList);

		return trueList;
	}
	
	static ArrayList<Range<Integer>> removeUnnescessaryRanges(ArrayList<Range<Integer>> list) {
		list.sort((a,b) -> a.getMinimum() > b.getMinimum() ? 1 : -1);

		for (int i = 0; i < list.size() -1 ; i++) {
			Range<Integer> range1 = list.get(i);
			Range<Integer> range2 = list.get(i+1);
			Range<Integer> newRange = Range.between(-2, -1);
			if (range1.isOverlappedBy(range2)) {
				int min = Math.min(range1.getMinimum(), range2.getMinimum());
				int max = Math.max(range1.getMaximum(), range2.getMaximum());
				newRange = Range.between(min, max);
			} else if( range2.getMinimum() - range1.getMaximum() == 1) {
				newRange = Range.between(range1.getMinimum(), range2.getMaximum());
			}
			
			if (newRange.getMaximum() != -1) {
				list.set(i, newRange);
				list.remove(i+1);
				i--;
			}
			
		}
		return list;
	}
	
	static ArrayList<Integer> findInvalidValues(ArrayList<Integer> ticketVals,  ArrayList<Range<Integer>> validVals) {
		ArrayList<Integer> invalidValues = new ArrayList<Integer>();
		
		for (int val : ticketVals  ) {
			boolean found = false;
			for (Range<Integer> range: validVals) {
				if (range.contains(val)) {
					found = true;
					break;
				}
			}
			if (!found) {
				invalidValues.add(val);
			}
		}
		return invalidValues;
	}

	//PART 2
	
	
	static HashMap<String, ArrayList<Integer>> compatibleFields(HashMap<String, ArrayList<Range<Integer>>> fields, ArrayList<ArrayList<Integer>> tickets) {
		HashMap<String, ArrayList<Integer>> fieldMatches = new HashMap<String, ArrayList<Integer>>();
		
		
		for (String key: fields.keySet()) {
			ArrayList<Integer> compatibleFields = new ArrayList<Integer>();
			
			ArrayList<Range<Integer>> ranges = fields.get(key);
			Range<Integer> range1 = ranges.get(0);
			Range<Integer> range2 = ranges.get(1);
			
			for (int i = 0; i < fields.size(); i++) {
				int matches = 0;
				for (ArrayList<Integer> list: tickets) {
					int value = list.get(i);
					if (range1.contains(value) || range2.contains(value)) {
						matches++;
					} else {
						break;
					}
				}
				if (matches == tickets.size()) {
					compatibleFields.add(i+1);
				}
			}
			fieldMatches.put(key, compatibleFields);
		}
		return findTrueMatches(fieldMatches);
	}	
	
	static HashMap<String, ArrayList<Integer>> findTrueMatches(HashMap<String, ArrayList<Integer>> alternatives) {
		ArrayList<Integer> singles = new ArrayList<Integer>();

		while (singles.size() < alternatives.size()) {
			
			for (String key: alternatives.keySet()) {	
				ArrayList<Integer> possibleAnswers = alternatives.get(key);
				
				if (possibleAnswers.size() == 1 ) {
					int toBeRemoved = possibleAnswers.get(0);
					
					if ( !singles.contains(toBeRemoved)) {
						singles.add(toBeRemoved);
						
						for (String key2: alternatives.keySet()) {
							if (!key2.equals(key)) {
								ArrayList<Integer> matches = alternatives.get(key2);
								matches.remove(new Integer(toBeRemoved));
								alternatives.put(key2, matches);
							}
						}
					}
				}
			}
			
		}

		
		return alternatives;
	}
		
}


