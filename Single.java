/** 
 * A subclass of the Hand class and is used to model single used in a 
 * Big Two card game.
 *
 * @author Loki
 *
 */
public class Single extends Hand {
	
	/** 
	 * a constructor for building single 
	 * with the specified player and list of cards.
	 * 
	 * @param player Player who has played the hand.
	 * @param cards list of card that the player has played.
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public Card getTopCard() {
		if (!this.isEmpty()) {
			return (this.getCard(0));
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
		if (this.size() != 1) {     // valid if hand only have one card
			return false;
		}
		else {
			return true;
			}
	}
	
	/**
	 *  a method for returning a string specifying the type of this hand.
	 *  @return the type (Single) of hand in string
	 */
	public String getType() {
		return "Single";
	}
}
