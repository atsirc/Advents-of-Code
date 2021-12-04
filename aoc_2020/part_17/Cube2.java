package hjul17;

import java.util.HashMap;
import java.util.Arrays;

public class Cube2 {
	
	HashMap<String, Boolean> grid;
	char active = '#';
	
	
	public Cube2( String[] input) {
		init(input);
	}
	
	
	private void init(String[] lines) {
		grid = new HashMap<String, Boolean>();
		for (int y = 0; y < lines.length; y++) {
			for (int x=0; x <lines[y].length(); x++) {
				boolean value = lines[y].charAt(x) == active;
				String key = String.join(",", ""+x, ""+y, "0", "0");
				grid.put(key, value);
			}
		}
		
	}
	
	private int findAdjacent(int x, int y, int z, int w) {
		int found = 0;
		for (int i = x -1; i <= x+1; i++ ) {
			for (int j = y - 1; j <= y+1; j++) {
				for (int k = z-1; k <= z+1; k++) {
					for (int l = w-1; l <=  w+1; l++) {
						if (i != x || j != y || k != z || l != w) {
							String key = String.join(",", ""+i, ""+j, ""+k, ""+l);
							
							if (grid.containsKey(key)) {
								found += grid.get(key) ? 1 : 0;
							}
						}
						
					}
				}
			}
		}
		return found;
	}
	
	public void loop() {
		int minx, miny, minz, minw, maxx, maxy, maxz, maxw;
		minx = miny = minz = minw = maxx = maxy = maxz = maxw = 0;

		
		for (String key : grid.keySet()) {
			int[] coordinates = Arrays.asList(key.split(",")).stream().mapToInt(Integer::parseInt).toArray();
			int x = coordinates[0];
			int y = coordinates[1];
			int z = coordinates[2];
			int w = coordinates[3];
			
			if (x < minx) minx = x;
			if (x > maxx) maxx = x;
			if (y < miny) miny = y;
			if (y > maxy) maxy = y;
			if (z < minz) minz = z;
			if (z > maxz) maxz = z;
			if (w < minw) minw = w;
			if (w > maxw) maxw = w;
		}
		
		HashMap<String, Boolean> newGrid = new HashMap<String, Boolean>();
		
		for (int i = minx -1; i <= maxx+1; i++ ) {
			for (int j = miny - 1; j <= maxy+1; j++) {
				for (int k = minz-1; k <= maxz+1; k++) {
					for (int l = minw-1; l <=  maxw+1; l++) {
						int found = findAdjacent(i,j,k,l);
						String key = String.join(",", ""+i, ""+j, ""+k , ""+l);
						boolean active = grid.getOrDefault(key, false);
						active = (found == 2 && active) ? true : (found == 3) ? true : false;
						newGrid.put(key, active);
					}
				}
			}
		}
		
		grid = newGrid;
		
	}
		
	long countActive() {
		return grid.values().stream().filter(n -> n==true).count();
	}
	
	
	@Override
	public String toString() {
		String str = "";

		str += "\nActive: " + countActive();
		return str; 
	}
	
}
