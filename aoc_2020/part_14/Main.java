package hjul14;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.HashMap;
public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-14.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<String> strs = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
				strs.add(scanner.nextLine());
			}
			scanner.close();
			
			HashMap<Integer, Long> memory = new HashMap<Integer, Long>();
			String currentMask = "";
			
			for (String line: strs) {
				if (line.contains("mask")) {
					currentMask = line.substring(7);
				} else {
					line = line.replaceAll("[mem\\[\\]]", "");
					String[] parts = line.split(" = ");
					int key = Integer.parseInt(parts[0]);
					long value = Long.parseLong(parts[1]);
					long newVal = applyMask1(currentMask, value);
					memory.put(key, newVal);
				}
			}
			
			System.out.println("PART 1");
			long sum1 = memory.values().stream().mapToLong(Long::longValue).sum();
			System.out.println(sum1);
			
			HashMap<Long, Long> memory2 = new HashMap<Long, Long>();
			String currentMask2 = "";
			
			for (String line: strs) {
				if (line.contains("mask")) {
					currentMask2 = line.substring(7);
				} else {
					line = line.replace("mem[", "");
					line = line.replace("]", "");
					String[] parts = line.split(" = ");
					long slot = Long.parseLong(parts[0]);
					long value = Long.parseLong(parts[1]);
					String newVal = applyMask2(currentMask2, slot);
					List<Long> keys = getKeys(newVal);
					for (long key: keys) {
						memory2.put(key, value);
					}
				}
			}
			
			System.out.println("PART 2");
			long sum2 = memory2.values().stream().mapToLong(Long::longValue).sum();
			System.out.println(sum2);
			
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	
	static long applyMask1(String mask, long value) {
		String bitValue = getAsBit(value);
		for (int i = 0; i < bitValue.length(); i++) {
			if (mask.charAt(i) != 'X') {
				char c = mask.charAt(i);
				bitValue = bitValue.substring(0, i) + c + bitValue.substring(i+1);
			}
		}
		return getAsNumber(bitValue);
		
	}
	
	static String applyMask2(String mask, long value) {
		String bitValue = getAsBit(value);
		for (int i = 0; i < bitValue.length(); i++) {
			if (mask.charAt(i) == '1' || mask.charAt(i) == 'X') {
				char c = mask.charAt(i);
				bitValue = bitValue.substring(0, i) + c + bitValue.substring(i+1);
			}
		}
		return bitValue;
	}
	
	static List<Long> getKeys(String newBitValue) {
		ArrayList<String> keys = new ArrayList<String>();

		keys.add(newBitValue);
		
		for (int i = 0; i < newBitValue.length(); i++) {
			if (newBitValue.charAt(i) == 'X') {
				int initialSize = keys.size();
				for (int j = 0; j < initialSize; j++) {
					String str = keys.get(j);
					String v0 = str.substring(0, i) + '0' + str.substring(i+1);
					String v1 = str.substring(0, i) + '1' + str.substring(i+1);
					
					keys.set(j, v0);
					keys.add(v1);
				}
			}
		}
		return keys.stream().map(n -> getAsNumber(n)).collect(Collectors.toList());
	}
	
	
	static String getAsBit(long number) {
		String bit = Long.toBinaryString(number);
		
		while (bit.length() < 36) {
			bit = "0" + bit;
		}
		return bit;
	}
	
	static long getAsNumber(String str) {
		long bit = Long.parseLong(str, 2);
		return bit;
	}

}
