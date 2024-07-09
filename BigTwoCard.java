/** 
 * A subclass of the Card class and is used to model a card used in a 
 * Big Two card game.
 *
 * @author Loki
 *
 */
public class BigTwoCard extends Card{
	
	/**
	 * A constructor for building a card with the specified suit and rank. 
	 * 
	 * @param suit is an integer between 0 and 3
	 * @param rank is an integer between 0 and 12.
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit,rank);
	}
	
	/**
	 * a method for comparing the order of this card with the specified card.
	 * 
	 * @return a negative integer, zero, or a positive integer when this card is less than, equal to, or greater than the specified card.
	 */
	public int compareTo(Card card) {
		int thisrank = this.rank;
		int cardrank = card.rank;
		
		if(thisrank ==0) 
			thisrank = 13;
		else if(thisrank == 1)                           //comparing cards with big two standard, with 2 being the biggest rank
			thisrank = 14;
		if(cardrank == 0) 
			cardrank = 13;
		else if(cardrank == 1) 
			cardrank = 14;
		
		if(thisrank < cardrank) 
			return -1;
		else if(thisrank > cardrank) 
			return 1;
		else if(this.getSuit()< card.getSuit()) 
			return -1;
		else if(this.getSuit() > card.getSuit()) 
			return 1;
		return 0;
	}

}