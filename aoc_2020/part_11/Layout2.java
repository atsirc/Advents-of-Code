package hjul11;

import java.util.ArrayList;

public class Layout2 extends Layout {
	
	
	public Layout2(ArrayList<ArrayList<Square>> layout) {
		super(layout);
		occupiedMax = 5;
	}
	
	@Override
	public int flipSeats(int i, int j) {
		int maxAdj = adjacentSquares(i, j);
		if (super.flipSeats(i, j) == maxAdj) {
			return maxAdj;
		}
		ArrayList<String> riktningar = new ArrayList<String>();
		int k = 1;
		int kMax = 0;
		int occ =0;
		if (j * 1.00 < height()/2) {
			kMax = height()- 1 - j;
		} else {
			kMax = j;
		}
		
		if (!current(i,j).isSeat()) {
			return occ;
		}
		
		while (k < kMax) {
			int y = ((j - k) < 0) ? j : j - k;
			while (y <= j + k && y < height()) {
				int x = ((i - k) < 0) ? i : i - k;
				while (x <= i + k && x < width()) {
					if (!(x == i && y == j)) {
						if (current(x,y).isSeat()) {
							String compass = compass(i,j,x,y);
							if (!riktningar.contains(compass)) {
								riktningar.add(compass);
								if (current(x,y).isOccupied())
									occ++;
							}
						}
					}
					x += k;
				}
				y += k;
			}
			
			k++;

			if (riktningar.size() == maxAdj || occ >= occupiedMax) {
				break;
			}
		}
		
		return occ;
	}

	
	private String compass(int x1, int y1, int x2, int y2) {
		
		String riktning = "";
		int i = x1 - x2;
		riktning += (i == 0) ? "level" : (i < 0) ? "höger" : "vänster";
		int j = y1 - y2;
		riktning += (j == 0) ? "level" : (j < 0) ? "nedan" : "ovan";
		return riktning;
		
	}

}