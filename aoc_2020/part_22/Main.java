package hjul22;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-22.txt";
		String absolutePath = directory + File.separator + fileName;
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			
			Hand player1 = null;
			Hand player2 = null;
			ArrayList<Integer> cards = new ArrayList<>();
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("Player")) {
					 continue;
				}
				if (!line.isEmpty()) {
					cards.add(Integer.parseInt(line));
				} else {
					if (player1 == null) {
						player1 = new Hand(cards);
						cards = new ArrayList<>();
					} else {
						player2 = new Hand(cards);
					}
				}
			}
			player2 = new Hand(cards);
			scanner.close();
			
		//	play(player1, player2);
			
		Hand winner = playRecursiveCombat(player1, player2);
		System.out.println("The winner has the score: "+ winner.getScore());

			
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	public static void play(Hand player1, Hand player2) {
		
		while (!player1.isEmpty() && !player2.isEmpty()) {
			int player1Card = player1.getTop();
			int player2Card = player2.getTop();
			if (player1Card > player2Card) player1.addCards(player1Card, player2Card);
			else player2.addCards(player1Card, player2Card);
		}
		Hand winner = (player1.isEmpty()) ? player2 : player1;
	
		System.out.println("The winner has the score: "+ winner.getScore());
	}
	
	public static Hand playRecursiveCombat(Hand player1, Hand player2) {
		Hand player1Copy = new Hand(new ArrayList<>(player1.getCards()));
		Hand player2Copy = new Hand(new ArrayList<>(player2.getCards()));

		do {
			int player1Card = player1Copy.getTop();
			int player2Card = player2Copy.getTop();
			if (player1Copy.size() >= player1Card && player2Copy.size() >= player2Card) {
				Hand sub1 = new Hand(new ArrayList<>(player1Copy.getCards().subList(0, player1Card)));
				Hand sub2 = new Hand(new ArrayList<>(player2Copy.getCards().subList(0, player2Card)));
				Hand winner = playRecursiveCombat(sub1, sub2);
				if (winner.getFirstHand().equals(player1Copy.getCards().subList(0, player1Card))) player1Copy.addCardsV2(player1Card, player2Card);
				else player2Copy.addCardsV2(player2Card, player1Card);
			} else {
				if (player1Card > player2Card) player1Copy.addCards(player1Card, player2Card);
				else player2Copy.addCards(player1Card, player2Card);
			}
			
		} while( (!player1Copy.currentIsRepeat() && !player2Copy.currentIsRepeat()) && (!player1Copy.isEmpty() && !player2Copy.isEmpty()));
		
		if (player1Copy.currentIsRepeat() && player2Copy.currentIsRepeat()) {
			return player1Copy;
		}
		return (player1Copy.isEmpty()) ? player2Copy : player1Copy;
	}
	
		
}
