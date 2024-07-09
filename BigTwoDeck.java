/** 
 * A subclass of the Deck class and is used to model a deck of cards used in a 
 * Big Two card game.
 *
 * @author Loki
 *
 */
public class BigTwoDeck extends Deck{
	
	/**
	 * a method for initializing a deck of Big Two cards.
	 */
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);            //initialize cards with bigtwo standards
				addCard(card);
			}
		}
	}
	
	/**
	 * a method for shuffling a deck of Big Two cards.
	 */
	public void shuffle() {
		for (int i = 0; i < this.size(); i++) {
			int j = (int) (Math.random() * this.size());
			if (i != j) {
				BigTwoCard card = (BigTwoCard) setCard(i, getCard(j)); //shuffling bigtwo cards
				setCard(j, card);
			}
		}
	}

}