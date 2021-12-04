package hjul11;

import java.util.ArrayList;

public class Layout {
	
	private ArrayList<ArrayList<Square>> layout;
	private ArrayList<ArrayList<Square>> holder;
	int occupiedMax = 4;
	
	public Layout(ArrayList<ArrayList<Square>> layout) {
		this.layout = layout;
		this.holder = copy(layout);
	}
	
	public boolean equals(Layout layout2) {
		for (int y = 0; y < height(); y++ ) {
			for (int x = 0; x < width(); x++) {
				if (!this.current(x, y).equals(layout2.current(x, y))) {
					return false;
				}
			}
		}
		return true;
	}
	
	private ArrayList<ArrayList<Square>> copy(ArrayList<ArrayList<Square>> grid) {
		ArrayList<ArrayList<Square>> newCopy = new ArrayList<ArrayList<Square>>();
		for (int y = 0; y < height(); y++ ) {
			ArrayList<Square> row = new ArrayList<Square>();
			for (int x = 0; x < width(); x++) {
				Square copy = grid.get(y).get(x).clone();
				row.add(copy);
			}
			newCopy.add(row);
		}
		return newCopy;
	}
	
	public void copy(Layout grid) {
		this.layout = this.copy(grid.layout);
	}
	
	public Layout getCopy() {
		return new Layout(copy(this.layout));
	}
	
	public int width() {
		return this.layout.get(0).size();
	}
	public int height() {
		return this.layout.size();
	}
	
	public Square current(int x, int y) {
		return this.layout.get(y).get(x);
	}
	
	public void change(int x, int y) {
		this.holder.get(y).get(x).change();
	}
	
	public int adjacentSquares(int i, int j) {
		int x = (i == 0) ? 0 : i - 1;
		int y = (j == 0) ? 0 : j - 1;
		int adjacent = 0;
		while (y <= j+1 && y < height()) {
			
			while(x <= i + 1 && x < width()) {
				if (!(x == i && y == j)) adjacent++;
				x++;
			}
			x = (i == 0) ? 0 : i - 1;
			y++;
		}
		return adjacent;
	}
	
	public int flipSeats(int i, int j) {
		int x = (i == 0) ? 0 : i - 1;
		int y = (j == 0) ? 0 : j - 1;
		int occupied = 0;
		
		if (!current(i,j).isSeat()) {
			return occupied;
		}
		
		while (y <= j+1 && y < height()) {
			
			while(x <= i + 1 && x < width()) {
				if (current(x,y).isOccupied()) {
					if (!(x == i && y == j))
						occupied++;
				}
				x++;
			}
			x = (i == 0) ? 0 : i - 1;
			y++;
		}
		return occupied;
	}
	
	public Layout turn() {
		
		for (int y = 0; y < height(); y++ ) {
			for (int x = 0; x < width(); x++) {

				int occupied = flipSeats(x,y);
				if (occupied >= occupiedMax && current(x,y).isOccupied()) {
					change(x,y);
				} else
					if (occupied == 0 && !current(x,y).isOccupied()) {
						change(x,y);
					}
				
			}
		}
		this.layout = copy(this.holder);
		return new Layout(holder);
	}
	

	public int occuppiedSeats() {
		int i= 0;
		for (int y = 0; y < height(); y++ ) {
			for (int x = 0; x < width(); x++) {
				if (this.current(x, y).isOccupied()) {
					i++;
				}
			}
		}
		return i;
	}
	
	public String toString() {
		String str = "";
		for (ArrayList<Square> list: this.layout) {
			for (Square square : list) {
				str += square + " ";
			}
			str += "\n";
		}
		return str;
	}

}
