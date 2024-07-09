/** 
 * A subclass of the Hand class and is used to model pair used in a 
 * Big Two card game.
 *
 * @author Loki
 *
 */
public class Pair extends Hand {
	
	/** 
	 * a constructor for building pair 
	 * with the specified player and list of cards.
	 * 
	 * @param player Player who has played the hand.
	 * @param cards list of card that the player has played.
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 *  a method for checking if this is a valid hand.
	 *  @return true if hand is valid, false otherwise
	 */
	public boolean isValid() {
		if (this.size() != 2) {
			return false;
		}
		else {
			BigTwoCard card0 = (BigTwoCard) this.getCard(0);
			BigTwoCard card1 = (BigTwoCard) this.getCard(1);
			if (card0.rank != card1.rank) {                     // two card with the same rank then its valid
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	/**
	 *  a method for returning a string specifying the type of this hand.
	 *  @return the type (Pair) of hand in string
	 */
	public String getType() {
		return "Pair";
	}
}
