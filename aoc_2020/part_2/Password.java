package hjul2;

public class Password{
	
	int digit1;
	int digit2;
	char letter;
	String password;
	
	public Password(int digit1, int digit2, char letter, String password) {
		this.digit1 = digit1;
		this.digit2 = digit2;
		this.letter = letter;
		this.password = password;
	}
	
	public boolean isCorrect() {
		int amount  = 0;
		for (int i = 0; i < password.length(); i++) {
			if (this.password.charAt(i) == this.letter) {
				amount++;
			}
		}
		
		return (amount >= digit1) && (amount <= digit2);
	}

}
