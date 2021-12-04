package hjul4;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class Passport {
	
	public HashMap<String, String> fields = new HashMap<String, String>();
	public ArrayList<String> obligatory_fields = new ArrayList<String>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
	
	public Passport(String str) {
		String[] parts = str.split(" ");
		
		for (int i = 0; i < parts.length; i++) {
			String[] key_value = parts[i].split(":");
			
			fields.put(key_value[0], key_value[1]);
			
		}	
		
	}
	
	public boolean isValid() {
		
		for (String key: obligatory_fields) {
			if (!key.equals("cid") && !fields.containsKey(key)) {
				return false;
			}
		}
		return true;
			
	}
	
	@Override
	public String toString() {		
		return fields.toString();
	}
	
}
