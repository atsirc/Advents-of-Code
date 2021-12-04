package hjul11;

public class Square {
	
	private char content;
	private char empty = 'L';
	private char floor = '.';
	private char occupied = '#';
	
	public Square(char content) {
		this.content = content;
	}
	
	public char get() {
		return this.content;
	}
	
	public void change() {
		if (content != floor) {
			this.content = (this.content == empty) ? occupied : empty;
		}
	}
	
	public boolean isOccupied() {
		return this.content == occupied;
	}
	
	public boolean isSeat() {
		if (this.content == empty || isOccupied()) return true;
		else return false;
	}
	
	public boolean equals(Square other) {
		return this.content == other.content;
	}
	
	public Square clone() {
		return new Square(this.content);
	}
	
	@Override
	public String toString() {
		return this.content + "";
	}

}
