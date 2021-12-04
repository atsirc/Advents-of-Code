package hjul5;

import java.util.stream.IntStream;
import java.util.Arrays;

public class BoardingPass implements Comparable<BoardingPass> {
	
	private String pass;
	private char[] row;
	private char[] column;
	private int rows = 128;
	private char front = 'F';
	private char back = 'B';
	private int columns = 8;
	private char left = 'L';
	private char right = 'R';
	
	public BoardingPass(char[] row, char[] column) {
		this.row = row;
		this.column = column;
	}
	
	private int getPlacement(char[] input, int seats, char low, char high) {
		
		int[] stream = IntStream.range(0, seats).toArray();	
		
		for (int i=0; i<input.length; i++) {
			if (input[i] == low ) {
				stream = Arrays.copyOfRange(stream, 0, stream.length/2);
			} else if (input[i] == high) {
				stream = Arrays.copyOfRange(stream, stream.length/2, stream.length);	
			}
		}
		
		return stream[0];
	}
	
	private int getRow() {
		return this.getPlacement(this.row, rows, front, back);
	}
	
	private int getColumn() {
		return this.getPlacement(this.column, columns, left, right);
	}

	public int getId() {
		return (getRow() * 8 + getColumn());
	}
	
	public BoardingPass compare(BoardingPass other) {
		return (this.getId() > other.getId()) ? this : other;
	}
	
	public int compareTo(BoardingPass other) {
		return (this.compare(other).equals(this)) ? 1 : -1;
	}
	
	@Override
	public String toString() {
		return this.pass + ": row: " + this.getRow() + ", column: " + this.getColumn() + ", seat ID: " + this.getId();
	}
	
}