package hjul8;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {
	
	private static int acc;

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-8.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<Instruction> lines = new ArrayList<Instruction>();
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(" ");
				lines.add(new Instruction(parts[0], Integer.parseInt(parts[1])));
			}
			
			scanner.close();
			
			System.out.println("PART 1:");
			play(lines);
			System.out.println("Last value " + acc);
			
			System.out.println("PART 2:");
			
			for (Instruction line : lines) {
				switchNopJmp(line);
				boolean result = play(lines);
				if (result){	
					break;
				}
				switchNopJmp(line);
			}
			
			System.out.println("Last value " + acc);
						
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	public static boolean play(ArrayList<Instruction> list) {
		acc = 0;
		int currentRow = 0;
		ArrayList<Integer> visited = new ArrayList<Integer>();
		
		while (visited.indexOf(currentRow) == -1 && currentRow < list.size()) {
			Instruction todo = list.get(currentRow);
						
			visited.add(currentRow);
			
			switch(todo.getTodo()) {
			
			case "nop" : 
				currentRow++;
				break;
			
			case "acc" :
				acc += todo.steps();
				currentRow++;
				break;
			
			case "jmp" :
				currentRow += todo.steps();
				break;
				
			}
		}
		
		return (currentRow == list.size());
			
	}
	
	public static void switchNopJmp(Instruction toDo) {
		switch (toDo.getTodo()) {
			case "nop" : toDo.setTodo("jmp"); break;
			case "acc" : break;
			case "jmp" : toDo.setTodo("nop"); break;
		}
	}

}