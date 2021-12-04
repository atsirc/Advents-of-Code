package hjul17;

import javafx.geometry.Point3D;

public class Cell extends Point3D {
	
	char state;
	
	public Cell(int x, int y, int z, char state) {
		super(x,y,z);
		this.state = state;
	}
	
	

}
