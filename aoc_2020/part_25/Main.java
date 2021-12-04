package hjul25;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		long card = /* 5764801 */ 15628416;
		long door = /*17807724*/ 11161639;

		long doorLoopSize = getLoopSize(door, 7);
		
		long key = getKey(doorLoopSize, card);

		System.out.println("PART 1: ");
		System.out.println(key);

	}
	
	public static long getLoopSize(long key, long subjectNumber) {
		long i = 0;
		long val = 1;
		
		while (val != key) {
			val *= subjectNumber;
			val %= 20201227;
			i++;
		}
		return i;
	}
	
	public static long getKey(long loopSize, long subjectNumber) {
		int i = 0;
		long val = 1;
		
		while (i<loopSize) {
			val *= subjectNumber;
			val %= 20201227;
			i++;
		}
		return val;
	}

}


