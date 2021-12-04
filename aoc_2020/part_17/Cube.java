package hjul17;

public class Cube {

	private char cube [][][];
	private int height;
	private int width;
	private int depth = 1;
	private char active = '#';
	private char inactive = '.';
	
	
	public Cube(String[] startValues) {
		init(startValues);
	}
	
	public void init(String[] values ) {
		
		height = values.length;
		width = values[0].length();
		char[][][] startValues = new char[depth][height][width];
		int i = 0;
		for (String str : values) {
			int j = 0;
			for (char c : str.toCharArray()) {
				startValues[0][i][j] = c;
				j++;
			}
			i++;
		}
		populate(startValues);
	}
	
	public void populate(char[][][] newValues) {

		cube = new char[depth][height + 2][width +2 ];
		
		for (int z = 0; z < depth; z++) {
			for (int y = -1; y <= width; y++) {
				for (int x = -1; x <= width; x++) {
					if  (y == -1 || y == height ||
						 x == -1 || x == width) {
						cube[z][y+1][x+1] = inactive;
					} else {
						cube[z][y+1][x+1] = newValues[z][y][x];
					}
				}
			}
		}
		width += 2;
		height += 2;
	}
	
	private int[][] nearby(int x, int y, int z) {
		int initX = ( x > 0 ) ? x-1 : 0;
		int initY = ( y > 0 ) ? y-1 : 0;
		int initZ = ( z > 0 ) ? z-1 : 0;
		int endZ = ( z + 1 < depth) ? z + 1 : (z==depth) ? z-1 : z;
		int endX = (x + 1 < width) ? x+1 : x;
		int endY = (y + 1 < height) ? y+1 : y;
		int isPresent = (z<depth) ? 1 : 0;
		int amount = (endX - initX + 1) * (endY - initY +1) * (endZ - initZ +1) - isPresent;
		int[][] coordinates = new int[amount][3];
		int i = 0;
		
		for (int zz= initZ; zz <= endZ; zz++) {
			for (int yy = initY; yy <= endY; yy++) {
				for (int xx = initX; xx <= endX; xx++) {
					if (!(zz == z && yy==y && xx==x )) {
						coordinates[i] = new int[] {zz,yy,xx};
						i++;
					}
				}
			}
		}
		return coordinates;
	}
	
	private char getNewValue(int x, int y, int z) {
		char currentVal;
		if (x >= width || y >= height || z >= depth) {
			currentVal = inactive;
		} else {
			currentVal = cube[z][y][x];
		}
		int[][] coordinates = nearby(x,y,z);
		int activeNearby = 0;
		for ( int[] cell : coordinates ) {
			char state = cube[cell[0]][cell[1]][cell[2]];
			activeNearby += (state == active) ? 1 : 0;
			if (z == 0 && !(cell[0] == 0)) {
				activeNearby += (state == active) ? 1 : 0;
			}
		}

		if (currentVal == active) {
			return (activeNearby == 2 || activeNearby == 3) ? active : inactive;
		} else {
			return (activeNearby == 3) ? active : inactive;
		}
	}
	
	
	public void loop() {
		char[][][] newCube = new char[depth+1][width+2][height+2];
		for (int z = 0; z <= depth; z++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					
					newCube[z][y][x] = getNewValue(x,y,z);
				}
			}
		}
		depth++;
		populate(newCube);
	}
	
	private int countActive() {
		int i = 0;
		for (int z = 0; z < depth; z++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					char c = cube[z][y][x];
					i += (c == active) ? 1 : 0;
					if (z > 0) {
						i += (c == active) ? 1 : 0;
					}
				}
			}
		}
		return i;
	}
	
	@Override
	public String toString() {
		String str = "";
		int i= 0;
		for (char[][] z : cube) {
			str += "Z = " + i + "\n";
			for (char[] y : z) {
				for (char x : y) {
					str+=x;
				}
				str += "\n";
			}
			str += "\n\n\n";
			i++;
		}
		
		str += "\nActive: " + countActive();
		return str; 
	}
	
}
