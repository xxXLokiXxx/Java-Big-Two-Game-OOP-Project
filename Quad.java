/** 
 * A subclass of the Hand class and is used to model quad used in a 
 * Big Two card game.
 *
 * @author Loki
 *
 */
public class Quad extends Hand {
	
	/** 
	 * a constructor for building quad 
	 * with the specified player and list of cards.
	 * 
	 * @param player Player who has played the hand.
	 * @param cards list of card that the player has played.
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/** 
	 * A method for checking if this hand beats a specified hand.
	 * @param hand the hand for comparing with this hand.
	 * @return true if this hand beats the hand in the argument, false otherwise.
	 */
	public boolean beats(Hand hand) {
		if (hand == null || !hand.isValid() || !this.isValid() || hand.getType() == "Single" || hand.getType() == "Pair" || hand.getType() == "Triple"|| hand.getType() == "StraightFlush") {
			return false;
		}
		else if (hand.getType() == "Straight" || hand.getType() == "Flush" || hand.getType() == "FullHouse") {           // implementing beats according to the rank of hands' type
			return true;	
		}
		else {
			return (this.getTopCard().compareTo(hand.getTopCard()) > 0);
		}
	}
	
	/**
	 * Getter function for retrieving the top card of this hand.
	 * 
	 * @return top card of player's hand.
	 */
	public Card getTopCard() {
		if (!this.isEmpty()) {
			this.sort();
			BigTwoCard card3 = (BigTwoCard) this.getCard(3);
			BigTwoCard card4 = (BigTwoCard) this.getCard(4);
			if ((card3.rank == card4.rank )) {
				return (this.getCard(this.size()-1));      // top card in the four same rank cards
			}
			else {
				return (this.getCard(this.size()-2));
			}
		}
		else {
			return null;
		}
	}
	
	/**
	 *  a method for checking if this is a valid hand.
	 *  @return true if hand is valid, false otherwise
	 */
	public boolean isValid() {
		this.sort();
		BigTwoCard card0 = (BigTwoCard) this.getCard(0);
		BigTwoCard card1 = (BigTwoCard) this.getCard(1);
		BigTwoCard card2 = (BigTwoCard) this.getCard(2);
		BigTwoCard card3 = (BigTwoCard) this.getCard(3);
		BigTwoCard card4 = (BigTwoCard) this.getCard(4);
		
		if (this.size() != 5) {
			return false;
		}
		
		if ((card3.rank == card4.rank )) {                            //considering two possible types of permutation of full house
			if ((card1.rank == card2.rank) && (card2.rank == card3.rank) && (card3.rank == card4.rank) && (card0.rank != card1.rank)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		else if ((card0.rank == card1.rank )) {                           //considering two possible types of permutation of full house
			if ((card0.rank == card1.rank) && (card1.rank == card2.rank) && (card2.rank == card3.rank) && (card3.rank != card4.rank)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		else {
			return false;
		}
		
	}
	
	/**
	 *  a method for returning a string specifying the type of this hand.
	 *  @return the type (Quad) of hand in string
	 */
	public String getType() {
		return "Quad";
	}
	
}
