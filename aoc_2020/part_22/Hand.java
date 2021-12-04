package hjul22;

import java.util.ArrayList;

public class Hand {
	
	private ArrayList<Integer> cards;
	private ArrayList<ArrayList<Integer>> history = new ArrayList<>();
	
	public Hand(ArrayList<Integer> cards) {
		this.cards = cards;
		history.add(new ArrayList<>(cards));
	}
	
	public boolean currentIsRepeat() {
		ArrayList<ArrayList<Integer>> previousHistory = new ArrayList<>(history.subList(0, history.size()-1));
		return previousHistory.contains(cards);
	}
	
	public ArrayList<Integer> getFirstHand() {
		return history.get(0);
	}
	
	public int getTop() {
		int top = cards.get(0);
		cards.remove(0);
		return top;
	}
	public int size() {
		return cards.size();
	}
	
	public Boolean equals(Hand otherhand) {
		return  (cards.equals(otherhand.getFirstHand())) ? true : false;
	}
	
	public void addCards(int i, int j) {
		cards.add(Math.max(i,j));
		cards.add(Math.min(i,j));
		history.add(new ArrayList<>(cards));
	}
	
	public void addCardsV2(int i, int j) {
		cards.add(i);
		cards.add(j);
		history.add(new ArrayList<>(cards));
	}
	
	
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	public long getScore() {
		long j = 1;
		long sum = 0;
		while (j <= cards.size()) {
			int card = cards.get((int)(cards.size()-j));
			sum += card * j;
			j++;
		}
		return sum;
	}
	public ArrayList<Integer> getCards() {
		return new ArrayList<Integer>(this.cards);
	}

}
