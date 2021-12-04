package hjul23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		String input = "562893147";
		ArrayList<Integer> clock = Arrays.asList(input.split("")).stream()
				.mapToInt(s -> Integer.parseInt(s)).boxed().collect(Collectors.toCollection(ArrayList::new));
		
		
		System.out.println("Part 1:");
		game(clock, 100);
		int[] places = setup(input, 1000000);
		System.out.println("Part 2:");
		playTheBigGame(places, 10_000_000);
	}
	
	
	//slice and dice version
	
	public static void game(ArrayList<Integer> list, int turns) {
		
		while (turns > 0 ) {
			int current = list.get(0);
			ArrayList<Integer> pickUp = new ArrayList<>(list.subList(0, 4));
			list.removeAll(pickUp);
			pickUp.remove(new Integer(current));
			list.add(current);
			int destinationIndex = getDestinationIndex(list, current);
			for (int i = 0; i < 3; i++) {
				list.add(destinationIndex + i + 1, pickUp.get(i));
			}
			turns--;
		}
		
		int splitI = list.indexOf(1);
		ArrayList<Integer> answer = new ArrayList<Integer>(list.subList(splitI + 1, list.size()));		
		answer.addAll(list.subList(0, splitI));
		
		System.out.println(answer.stream().map(Object::toString).collect(Collectors.joining("")));
	}
	
	public static int getDestinationIndex(ArrayList<Integer> list, int current) {
		int i = current;
		int max = list.stream().mapToInt(n -> n).max().getAsInt();
		int index = 0;
		
		do {
			i--;
			index = list.indexOf(i);
		} while ( index == -1 && i > 1);
		return index > -1 ? index : list.indexOf(max);
	}
	
	//eric wastls version
	
	public static int[] setup(String ints, int size) {
		char[] integers = ints.toCharArray();
		int[] cups = new int[size + 1];
		for (int i = 0; i <= integers.length-2; i++) {
			cups[Integer.parseInt(integers[i]+"")] = Integer.parseInt(integers[i+1]+"");
		}
		cups[Integer.parseInt(integers[ints.length()-1] + "")] = 10;

		for (int i = 10; i < size; i++) {
			cups[i] = i+1;
		}
		cups[size] = Integer.parseInt(integers[0]+"");
		return cups;
	}
		
	public static void playTheBigGame(int[] array, int turns) {
		
		int current = array[array.length-1];
		for (int i = 0; i < turns; i++) {
			int first = array[current];
			int second = array[first];
			int third = array[second];
			
			int tail = current -1;
			
			if (tail == 0) 
				tail = array.length - 1;
				
			while (tail == first || tail == second || tail == third) {
				tail--;
				if (tail == 0)
					tail = array.length - 1;
			}
			
			array[current] = array[third];
			int temp = array[tail];
			array[tail] = first;
			array[third] = temp;
			current = array[current];
		}
		
		System.out.println( (long)array[1] * (long)array[array[1]] );
		
	}

}