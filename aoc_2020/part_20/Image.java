package hjul20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Image {

	ArrayList<String> image;
	public HashMap<String, Integer> matches = new HashMap<String, Integer>();
	int id;
	boolean ready = false;
	HashMap<String, ArrayList<String>> orientations = new HashMap<String, ArrayList<String>>();
	
	public Image(ArrayList<String> array, int id) {
		this.image = array;
		this.id = id;
		addOrientation("0");
	}
	
	public boolean sideIsEmpty(int sideNo) {
		ArrayList<String> sides = getSides();
		String side = sides.get(sideNo);
		return !matches.containsKey(side);
	}
	
	public int getMatchForSide(int sideNo) {
		ArrayList<String> sides = getSides();
		String side = sides.get(sideNo);
		return matches.get(side);
	}
	
	public int findSide(String str) {
		ArrayList<String> sides = getSides();
		int i = sides.indexOf(str);
			return i;
	}
	
	public String findRotation(ArrayList<String> sides) {
		if (orientations.size() > 0) {
			for (String key: orientations.keySet()) {
				ArrayList<String> values = orientations.get(key);
				if ( values.equals(sides)) {
					return key;
				}	
			}
		}
		return "";
	}
	
	public void sidesMatch(Image other) {
		if (other != this && sidesMatched() < 4) {
			ArrayList<String> sides = getSides();
			ArrayList<String> otherSides = other.getSides();
			for (int i = 0; i < 4; i++) {
				if (otherSides.contains(sides.get(i))) {
					String side = sides.get(i);
						matches.put(side, other.id);
						other.matches.put(side, this.id);	
				}
			}
		}
	}
	
	public void addOrientation(String name) {
		if (!orientations.containsKey(name)) {
			orientations.put(name, getSides());
		}
	}
	
	public ArrayList<String> getSides() {
		ArrayList<String> sides = new ArrayList<String>();
		sides.add(0, image.get(0));
		sides.add(1, "");
		sides.add(2, image.get(image.size()-1));
		sides.add(3, "");
		for (String line: image) {
			sides.set(1, sides.get(1) + line.charAt(line.length()-1));
			sides.set(3, sides.get(3) + line.charAt(0));
		}
		return sides;
	}
	
	public void flip() {
		if (!ready) {
			for (int i = 0; i < image.size(); i++) {
				String line = image.get(i);
				line = new StringBuilder(line).reverse().toString();
				image.set(i, line);			
			}
		}
	}
	
	public void flipUpSideDown() {
		if (!ready) {
			Collections.reverse(image);
		}
	}
	
	public void turn() {
		if (!ready) {
			ArrayList<String> rotatedImage = new ArrayList<String>();
			for (int i = image.size()-1; i >= 0; i--) {
				char[] chars = image.get(i).toCharArray();
				for (int j = 0; j < image.size(); j++) {
					String str = (rotatedImage.size() <= j) ? "" : rotatedImage.get(j);
					str += chars[j];
					if ( str.length() == 1)
						rotatedImage.add(str);
					else	
						rotatedImage.set(j, str);
				}
			}
			image = rotatedImage;
		}
	}
	
	public int sidesMatched() {
		return matches.size();
	}
	
	public ArrayList<Integer> getNeighbours() {
		return (ArrayList<Integer>) matches.values().stream().map(n -> n).collect(Collectors.toList());
	}
	
	public String matches() {
		int amount = matches.size();
		String str = matches.entrySet().toString();
		
		return "id "+ id + " " + amount + " MATCHES \n" + str;
	}
	

	@Override
	public String toString() {
		String str = "\n";
		for (String line : image) {
			str += line + "\n";
		}
		return str;
	}
	
}
