package hjul12;

import java.util.HashMap;

public class Point {
	public HashMap<Character, Integer> point;
	
	public Point() {
		point = new HashMap<Character, Integer>();
		point.put('N', 0);
		point.put('E', 0);
		point.put('S', 0);
		point.put('W', 0);
	}
	
	public void move(char c, int i) {
		switch (c) {
			case 'N' :
			case 'E' :
			case 'S' :
			case 'W' : point.put(c, point.get(c) + i); break;
		}
		cleanUp();
	}

	public void cleanUp() {
		char x1 = ((point.get('N') - point.get('S')) >= 0) ? 'N' : 'S';
		char x2 = (x1 == 'N') ? 'S' : 'N';
		point.put(x1, Math.abs(point.get(x1) - point.get(x2)));
		point.put(x2, 0);
		
		char y1 = ((point.get('E') - point.get('W')) >= 0) ? 'E' : 'W';
		char y2 = (y1 == 'E') ? 'W' : 'E';
		point.put(y1, Math.abs(point.get(y1) - point.get(y2)));
		point.put(y2, 0);
	}
	
	public 	HashMap<Character, Integer> get() {
		return point;
	}

}
