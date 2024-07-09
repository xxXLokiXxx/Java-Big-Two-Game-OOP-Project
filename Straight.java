/** 
 * A subclass of the Hand class and is used to model Single used in a 
 * Big Two card game.
 *
 * @author Loki
 *
 */
public class Straight extends Hand {
	
	/** 
	 * a constructor for building straight
	 * with the specified player and list of cards.
	 * 
	 * @param player Player who has played the hand.
	 * @param cards list of card that the player has played.
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
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
		
		if (this.size() != 5) {    // number of 5 cards checking
			return false;
		}
		else if ((card0.rank == card1.rank -1) && (card1.rank == card2.rank -1) && (card2.rank == card3.rank -1) && (card3.rank == card4.rank -1)) {
			return true;         //consecutive rank checking 
		}
		else {
			return false;
		}
	}
	
	/** 
	 * A method for checking if this hand beats a specified hand.
	 * @param hand the hand for comparing with this hand.
	 * @return true if this hand beats the hand in the argument, false otherwise.
	 */
	public boolean beats(Hand hand) {
		if (hand == null || !hand.isValid() || !this.isValid() || hand.getType() == "Single" || hand.getType() == "Pair" || hand.getType() == "Triple") {
			return false;
		}
		else if (hand.getType() == "Flush" || hand.getType() == "FullHouse" || hand.getType() == "Quad" || hand.getType() == "StraightFlush" ) {
			return false;	
		}
		else {
			return (this.getTopCard().compareTo(hand.getTopCard()) > 0);                              // implementing beats according to the rank of hands' type
		}
	}
	
	
	/**
	 *  a method for returning a string specifying the type of this hand.
	 *  @return the type (Straight) of hand in string
	 */
	public String getType() {
		return "Straight";
	}
}
