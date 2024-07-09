/** 
 * A subclass of the CardList class and is used to model a hand of cards. It has 
 * a private instance variable for storing the player who plays this hand. It also has methods for 
 * getting the player of this hand, checking if it is a valid hand, getting the type of this hand, 
 * getting the top card of this hand, and checking if it beats a specified hand.
 * Big Two card game.
 *
 * @author Loki
 *
 */
public abstract class Hand extends CardList{
	
	private final CardGamePlayer player;

	/** 
	 * a constructor for building a hand 
	 * with the specified player and list of cards.
	 * 
	 * @param player Player who has played the hand.
	 * @param cards list of card that the player has played.
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player = player;
		for (int i=0; i < cards.size(); i++) {
			this.addCard(cards.getCard(i));               //constructing hand
		}
		this.sort();                                     //sort just for safe
	}

	
	
	
	/**
	 * Getter function of player who has played the hand.
	 * 
	 * @return player of the hand object.
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
	}

	/**
	 * Getter function for retrieving the top card of this hand.
	 * 
	 * @return top card of player's hand.
	 */
	public Card getTopCard() {                       // Will be overridden be some hand subclasses
		if (!this.isEmpty()) {
			this.sort();                              //sort just in case
			return (this.getCard(this.size()-1));
		}
		else {
			return null;                             //null returned if empty hand
		}
	}

	/** 
	 * A method for checking if this hand beats a specified hand.
	 * @param hand for single, pair, triple
	 * @return true if this hand beats the hand in the argument, false otherwise.
	 */
	public boolean beats(Hand hand) {                   // Will be overridden be some hand subclasses to implement methods more specifically
		if (hand == null || !hand.isValid() || !this.isValid() || this.getType() != hand.getType()) {
			return false;
		}
		else {
			return (this.getTopCard().compareTo(hand.getTopCard()) > 0);
		}
	}
	
	
	
	/**
	 *  a method for checking if this is a valid hand.
	 *  @return true if hand is valid, false otherwise
	 */
	public abstract boolean isValid();                 //abstract means these methods must be implemented by following subclasses
	
	/**
	 *  a method for returning a string specifying the type of this hand.
	 *  @return the type of hand in string
	 */
	public abstract String getType();

}
