/** 
 * A subclass of the Hand class and is used to model triple used in a 
 * Big Two card game.
 *
 * @author Loki
 *
 */
public class Triple extends Hand {
	
	/** 
	 * a constructor for building triple
	 * with the specified player and list of cards.
	 * 
	 * @param player Player who has played the hand.
	 * @param cards list of card that the player has played.
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * Getter function for retrieving the top card of this hand.
	 * 
	 * @return top card of player's hand.
	 */
	public Card getTopCard() {
		if (!this.isEmpty()) {
			this.sort();
			return (this.getCard(this.size()-1));
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
		if (this.size() != 3) {     //check for hand size of three
			return false;
		}
		else {
			BigTwoCard card0 = (BigTwoCard) this.getCard(0);
			BigTwoCard card1 = (BigTwoCard) this.getCard(1);
			BigTwoCard card2 = (BigTwoCard) this.getCard(2);
			if (card0.rank == card1.rank && card1.rank == card2.rank && card0.rank == card2.rank) {
				return true;        // check for same rank of three cards
			}
			else {
				return false;
			}
		}
	}
	
	/**
	 *  a method for returning a string specifying the type of this hand.
	 *  @return the type (Triple) of hand in string
	 */
	public String getType() {
		return "Triple";
	}
}
