package hjul7;

import java.util.ArrayList;
import java.util.HashMap;

public class Bag {
	
	private String description;
	private HashMap<String, Integer> bagContents;

	public Bag(String description, HashMap<String, Integer> contents) {
		this.description = description;
		this.bagContents = contents;
	}
	
	public boolean contains(String bag) {
		return bagContents.containsKey(bag);
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public ArrayList<String> getContents() {
		
		ArrayList<String> contents = new ArrayList<String>();
		
		for (HashMap.Entry<String, Integer> entry: bagContents.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				contents.add(entry.getKey());
			}
		}
		return contents;
	}
	
	@Override 
	public String toString() {
		String contents = "";
		
		for (HashMap.Entry<String, Integer> entry: bagContents.entrySet()) {
			contents += entry.getValue() + " " + entry.getKey() + "; ";
		}
		
		return this.description + ": " + contents;
		
	}
	
}
