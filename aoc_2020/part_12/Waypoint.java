package hjul12;

public class Waypoint extends Point{
	
	private char[][] pointPairs = {{'E','N'}, {'S','E'}, {'W', 'S'}, {'N', 'W'}};
	
	public Waypoint() {
		point.put('N', 1);
		point.put('E', 10);
	}
	
	private char[] findPair() {
		char x = ((point.get('N') - point.get('S')) >= 0) ? 'N' : 'S';
		char y = ((point.get('E') - point.get('W')) >= 0) ? 'E' : 'W';
		
		int i = getIndex(new char[]{x,y});

		return pointPairs[i];
	}
	
	private int getIndex(char[] pair) {
		String asStr = new String(pair);
		int i=0;
		for (char[] pair2 : pointPairs) {
			String v1 = new String(pair2);
			String v2 = new String(pair2[1]+""+pair2[0]);
			if (asStr.equals(v1) || asStr.equals(v2)) {
				break;
			}
			i++;
		}
		return i;
	}

	
	public void turn(int degrees) {
		char[] currentPair = findPair();
		int currentI = getIndex(currentPair);
		int turns = degrees/90;
		int newI = (turns + currentI) % pointPairs.length;
		
		char[] newPair = pointPairs[newI];
		int[] values = new int[] {point.get(currentPair[0]), point.get(currentPair[1])};
		
		//emptying the current pairs
		point.put(currentPair[0], 0);
		point.put(currentPair[1], 0);
		
		//add on the values to the new pairs
		point.put(newPair[0], values[0]);
		point.put(newPair[1], values[1]);
		
	}

}
