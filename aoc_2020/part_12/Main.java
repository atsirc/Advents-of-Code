package hjul12;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-12.txt";
		String absolutePath = directory + File.separator + fileName;
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<String> path = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
				path.add(scanner.nextLine());
			}
			
			scanner.close();
			
			
			System.out.println(path);
			HashMap<Character, Integer> map = new HashMap<Character, Integer>();
			
			map.put('N', 0);
			map.put('E', 0);
			map.put('S', 0);
			map.put('W', 0);
			
			String riktningar = "NESW";

			
			int current = 1;			
						
			for (String inst: path) {
				char facing = inst.charAt(0);
				int steps = Integer.parseInt(inst.replace(facing+"", ""));
				
				switch (facing) {
				
				case 'F' : facing = riktningar.charAt(current);
				case 'N' :
				case 'E' :
				case 'S' :
				case 'W' : map.put(facing, map.get(facing) + steps); break;
				case 'L' :
					steps = 360 - steps;
				case 'R' :
					int turn = steps/90;
					current = (turn + current)%riktningar.length();
					break;
				}
			}
			
			System.out.println(
					"N: " + map.get('N') + " +  S: " + map.get('S') + " + " +
					"E: " + map.get('E') +  " - W: "  + map.get('W') + " = " +
					(Math.abs(map.get('N') - map.get('S')) +  Math.abs(map.get('E') - map.get('W'))));
			
			
			
			Waypoint waypoint  = new Waypoint();
			Point ship  = new Point();
			
			for (String inst: path) {
				char move = inst.charAt(0);
				int steps = Integer.parseInt(inst.replace(move+"", ""));
				
				switch (move) {
				
				case 'F' :
					HashMap<Character, Integer>  waypointMap = waypoint.get();
					for (char key: waypointMap.keySet()) {
						ship.move(key, waypointMap.get(key) * steps);
					}
					break;
				case 'N' :
				case 'E' :
				case 'S' :
				case 'W' : waypoint.move(move, steps); break;
				case 'L' :
					steps = 360 - steps;
				case 'R' :
					int degrees = steps;
					waypoint.turn(degrees);
					break;
				}
			}

			System.out.println(
					"N: " + ship.get().get('N') + " +  S: " + ship.get().get('S') + " + " +
					"E: " + ship.get().get('E') +  " - W: "  + ship.get().get('W') + " = " +
					(Math.abs(ship.get().get('N') - ship.get().get('S')) +  Math.abs(ship.get().get('E') - ship.get().get('W'))));
						
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}

}
