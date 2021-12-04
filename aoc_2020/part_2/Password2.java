package hjul2;

public class Password2 extends Password {
	
	public Password2(int pos_1, int pos_2, char letter, String password) {
		super(pos_1, pos_2, letter, password);
	}
	
	@Override
	public boolean isCorrect() {
		
		if ((password.charAt(digit1-1) == letter && password.charAt(digit2-1) != letter) ||
				(password.charAt(digit1-1) != letter && password.charAt(digit2-1) == letter)) {
			return true;
		}
		
		return false;
	}

}
