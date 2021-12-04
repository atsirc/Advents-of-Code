package hjul4;

import java.util.ArrayList;
import java.util.Arrays;

public class Passport2 extends Passport {
	
	public Passport2(String str) {
		super(str);
	}
	
	@Override
	public boolean isValid() {
		
		if (super.isValid()) {
			for (String key: obligatory_fields) {		
				if ( !test( key ) ) {
					return false;
				}
				
			}
			return true;
		}
		return false;
	}

	public boolean test(String current_key) {
				
		switch (current_key) {
		
		case "byr" :
			int byr = Integer.parseInt(fields.get(current_key));
			if  ( byr >= 1920 && byr <= 2002 ) {
				break;
			} else 
				return false;
				
		case "iyr" :
			int iyr = Integer.parseInt(fields.get(current_key));
			if (iyr >= 2010 && iyr <= 2020) {
				break;
			} else
				return false;
		
		
		case "eyr" :
			int eyr = Integer.parseInt(fields.get(current_key));
			if (eyr >= 2020 && eyr <= 2030) {
				break;
			} else
				return false;
			
		case "hgt" :
			String hgt = fields.get(current_key);
			String cm_or_in = hgt.substring(hgt.length()-2);
			int height = Integer.parseInt(hgt.substring(0, hgt.length()-2));
			
			if ( cm_or_in.equals("cm") ) {
				if ( height >= 150 && height <= 193 ) {
					break;
				} else 
					return false;
			}
			
			if (cm_or_in.equals("in")) {
				if ( height >= 59 && height <= 76 ) {
					break;
				} else
					return false;
			}
			else return false;
			
		case "hcl" :
			String hcl = fields.get(current_key);
			
			if (hcl.matches("#[a-f0-9]{6}$")) {
				break;
			} else
				return false;
			
		case "ecl" :
			String ecl = fields.get(current_key);
			ArrayList<String> colors = new ArrayList<String>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));
			if (colors.contains(ecl)) {
				break;
			} else
				return false;
		
		case "pid" :
			String pid = fields.get(current_key);
			if (pid.matches("[0-9]{9}$")) {
				return true;
			} else
				return false;
		}
		
		return true;
		
	}

}
