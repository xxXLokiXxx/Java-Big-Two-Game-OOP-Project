/** 
 * A subclass of the Hand class and is used to model Flush combination used in a 
 * Big Two card game.
 *
 * @author Loki
 *
 */
public class Flush extends Hand {
	
	/** 
	 * a constructor for building flush 
	 * with the specified player and list of cards.
	 * 
	 * @param player Player who has played the hand.
	 * @param cards list of card that the player has played.
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/** 
	 * A method for checking if this hand beats a specified hand.
	 * @param hand the hand for comparing with this hand.
	 * @return true if this hand beats the hand in the argument, false otherwise.
	 */
	public boolean beats(Hand hand) {
		if (hand == null || !hand.isValid() || !this.isValid() || hand.getType() == "Single" || hand.getType() == "Pair" || hand.getType() == "Triple"|| hand.getType() == "FullHouse" || hand.getType() == "Quad" || hand.getType() == "StraightFlush") {
			return false;
		}
		else if (hand.getType() == "Straight") {                     // implementing beats according to the rank of hands' type
			return true;	
		}
		else {
			return (this.getTopCard().compareTo(hand.getTopCard()) > 0);
		}
	}
	
	/**
	 *  a method for checking if this is a valid hand.
	 *  @return true if hand is valid, false otherwise
	 */
	public boolean isValid() {
		
		this.sort();                   // sort just to play safe
		BigTwoCard card0 = (BigTwoCard) this.getCard(0);
		BigTwoCard card1 = (BigTwoCard) this.getCard(1);
		BigTwoCard card2 = (BigTwoCard) this.getCard(2);
		BigTwoCard card3 = (BigTwoCard) this.getCard(3);
		BigTwoCard card4 = (BigTwoCard) this.getCard(4);
		
		if (this.size() != 5) {
			return false;
		}
		else if ((card0.getSuit() == card1.getSuit()) && (card1.getSuit() == card2.getSuit()) && (card2.getSuit() == card3.getSuit()) && (card3.getSuit() == card4.getSuit())) {
			return true;                          //ensure suit of the cards must stay consistent
		}
		else {
			return false;
		}
	}
	
	/**
	 *  a method for returning a string specifying the type of this hand.
	 *  @return the type (flush) of hand in string
	 */
	public String getType() {
		return "Flush";
	}
	
}
