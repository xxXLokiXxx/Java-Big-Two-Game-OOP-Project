import java.util.ArrayList;


/**
 * The BigTwo class implements the CardGame interface and is used to model a Big Two card 
 * game. It has private instance variables for storing the number of players, a deck of cards, a 
 * list of players, a list of hands played on the table, an index of the current player, and a user 
 * interface. 
 * 
 * @author Loki
 * 
 */
public class BigTwo {
	
	/**
	 *  A constructor for creating the BigTwo card game. 
	 */
	public BigTwo() {                       //(i) created 4 players and add them to the player list; and (ii) created a BigTwoUI object for providing the user interface.

		playerList = new ArrayList<CardGamePlayer> ();
		for(int i = 0; i < 4;i++)
		{
			CardGamePlayer newplayer = new CardGamePlayer();
			playerList.add(newplayer);
		}
		handsOnTable = new ArrayList<Hand>();
		ui = new BigTwoGUI(this);			      
		client = new BigTwoClient(this, ui, numOfPlayers);
		client.connect();
		numOfPlayers += 1;
	}

	private int numOfPlayers;                           //an int specifying the number of players.
	private Deck deck;                                  // a deck of cards.
	private ArrayList<CardGamePlayer> playerList;                 //  a list of players.
	private ArrayList<Hand> handsOnTable;                        //  a list of hands played on the table.
	private int currentPlayerIdx;                                  // an integer specifying the index of the current player.
	private BigTwoGUI ui;                                          // a BigTwoUI object for providing the user interface.
	private int lastPlayer = -1;                                  // a int specifying index of player played in last round.
	private BigTwoClient client; 
	
	/**
	 * A getter method for getting the number of players.
	 * @return an integer specifying the number of players.
	 */
	public int getNumOfPlayers() {
		 return this.numOfPlayers;                                                             //a method for getting the number of players.
	}
	
	public void setNumOfPlayers(int numOfPlayers) {
		 this.numOfPlayers = numOfPlayers;                                                             //a method for getting the number of players.
	}
	
	/**
	 * A getter method to retrieve the deck of cards being played.
	 * @return deck object containing the deck of cards currently being played 
	 */
	public Deck getDeck() {
	    return this.deck;                                                        //a method for retrieving the deck of cards being used.
	}
	
	/**
	 * A getter method to retrieve the list of players 
	 * @return an arraylist containing the list of players 
	 */
	public ArrayList<CardGamePlayer> getPlayerList() { 
		return this.playerList;                                                          //a method for retrieving the list of players.
	}
	
	/**
	 * Gets the current cards that have been played on the table by the previous player 
	 * @return an arraylist containing the cards played by the previous player
	 */
	public ArrayList<Hand> getHandsOnTable() {
		return this.handsOnTable;
	}
	
	/**
	 * A get method which retrieves the index of the active player
	 * @return an int type which could either be 0,1,2 or 3 
	 */
	public int getCurrentPlayerIdx() {
		return this.currentPlayerIdx;
	}
	
	/**
	 * A method for starting/restarting the game with a given shuffled deck of cards.
	 * @param deck shuffled deck of cards
	 */
	public void start(Deck deck) {
		for(int i = 0; i < 4;i++) {
			playerList.get(i).removeAllCards();
		}
		handsOnTable = new ArrayList<Hand>();
		
		for(int i = 0; i < 4;i++) {
			for(int j = 0; j < 13;j++) {
				playerList.get(i).addCard(deck.getCard(j+i*13));       // distributing cards to players
			}
		}
		for(int i = 0; i < 4;i++) {
			if (playerList.get(i).getCardsInHand().contains(new Card(0,2))) {     //set the diamond three player as the first player to start the game
				currentPlayerIdx = i;
				ui.setActivePlayer(currentPlayerIdx);
			}
			playerList.get(i).sortCardsInHand();
		}
		ui.repaint();
		ui.printMsg(getPlayerList().get(currentPlayerIdx).getName()+ "'s turn :");
		       // start by asking the player to select cards to play and display the board
		if (client.getPlayerID()==currentPlayerIdx) {
			ui.enable();
		}
		else {
			ui.partialDisable();
		}
	}
	
	/**
	 * A method for making a move by a player with the specified index using the cards specified by the list of indices.
	 * @param playerIdx an index of active player to select cards
	 * @param cardIdx an array of indices of the cards selected, or null if no valid cards
	 *         have been selected
	 */
	public void makeMove(int playerIdx, int[] cardIdx) {
		client.sendMessage(new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx));
		
	}
	
	
	/**
	 * A method for checking a move made by a player. 
	 * @param playerIdx an index of active player to select cards
	 * @param cardIdx an array of indices of the cards selected, or null if no valid cards
	 *         have been selected
	 */
	public void checkMove(int playerIdx, int[] cardIdx) {
		boolean validMove = true;
		
		if (cardIdx != null) {         // check validity of hand played when selected hand is not empty
			CardList cardlist = new CardList();
			cardlist = playerList.get(playerIdx).play(cardIdx);
			Hand newhand = composeHand(playerList.get(playerIdx), cardlist);
			
			if (this.handsOnTable.isEmpty()) {  // check validity of hand played when selected hand is not empty and table is empty
				if (newhand == null) {
					validMove = false;
				}
				else if (newhand.contains(new Card(0,2))  && newhand.isValid()==true) {  // hand played then must contain diamond 3
					validMove = true;
				}
				else {
					validMove = false;
				}
			}
			else {
				if (newhand == null) {         // check validity of hand played when selected hand is not empty and table is not empty
					validMove = false;
				}
				else if ( lastPlayer == currentPlayerIdx ) {
					validMove = newhand.isValid();
				}
				else {
					validMove = (newhand.isValid() && newhand.beats(handsOnTable.get(handsOnTable.size() - 1)));
				}
			}
			if(validMove) {             // for case of valid move 
				lastPlayer = currentPlayerIdx;
				playerList.get(lastPlayer).removeCards(newhand);  // remove cards has been played in player hand
				ui.printMsg("{" + newhand.getType() + "}" + " " + newhand);
				this.handsOnTable.add(newhand);
				if (currentPlayerIdx == 3) {
					currentPlayerIdx = 0;
				}
				else {
					currentPlayerIdx += 1;
				}
				ui.printMsg(getPlayerList().get(currentPlayerIdx).getName()+ "'s turn :");
				
				if (endOfGame()) {             // for case of valid move leads to end of game    
					ui.disable();
					ui.endWindow(lastPlayer);
					client.sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
				} 
				else {                               // for case of valid move doesn't leads to end of game
					ui.setActivePlayer(client.getPlayerID());  
					ui.repaint();
					
				}
			}
			else {                                           // for case of invalid move 
				if (client.getPlayerID()==currentPlayerIdx) {
					ui.printMsg("Not a legal move!!!");      // no pass available if its the first player or last player moved is current player
					ui.printMsg(getPlayerList().get(currentPlayerIdx).getName()+ "'s turn :");
				}
				
			}
		}
		else {                                                        // check validity of move when selected hand is empty, which means pass
			if(!handsOnTable.isEmpty() && lastPlayer != currentPlayerIdx) {
				if (currentPlayerIdx == 3) {
					currentPlayerIdx = 0;
				}
				else {
					currentPlayerIdx += 1;
				}
				ui.setActivePlayer(client.getPlayerID());                 // pass available 
				ui.printMsg("{Pass}");
				ui.printMsg(getPlayerList().get(currentPlayerIdx).getName()+ "'s turn :");
				ui.repaint();
				
			}
			
			else {
				if (client.getPlayerID()==currentPlayerIdx) {
					ui.printMsg("Not a legal move!!!");      // no pass available if its the first player or last player moved is current player
					ui.printMsg(getPlayerList().get(currentPlayerIdx).getName()+ "'s turn :");
				}
			} 
		}
		if (client.getPlayerID()==currentPlayerIdx) {
			ui.enable();
		}
		else {
			ui.partialDisable();
		}
	}
	
	/**
	 * A method for checking if the game ends.
	 * @return an boolean true if game end, false if not.
	 */
	public boolean endOfGame() {
		for (int i = 0; i < 4;i++) {
			if (playerList.get(i).getNumOfCards() == 0) {   // when no cards left in one's player hand, game ends
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A method for GUI to ask client to connect with server.
	 */
	public void connectViaBigTwo() {
		if (client.getPlayerID() == -1)  {
			client.connect();
		}
	}
	
	/**
	 * A method for GUI to ask a client to send messages of chat box to server, then broadcast to all clients.
	 */
	public void sendMessageViaBigTwo(String msgtext) {
		client.sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, msgtext));
	}
	
	/**
	 * A method for GUI to get a client's player ID.
	 */
	public void clientPlayerIdGetter() {
		client.getPlayerID();
	}
	
	/**
	 * Main helps in creating BigTwo and BigTwoDeck objects and shuffle and start the game by calling the void start function
	 * @param args unused
	 */
	public static void main(String[] args) {
		new BigTwo(); 
	}
	
	
	/**
	 * A method for returning a valid hand from the specified list of cards of the player.
	 * Returns null if no valid hand can be composed from the specified list of cards.
	 * 
	 * @param player CardGamePlayer object with the list of players in the game 
	 * @param cards CardList object with list of cards played by the active player
	 * @return the type object of hand
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		
		Triple triple = new Triple(player,cards);                       //convert cards input to specific type of hand 
		Pair pair = new Pair(player,cards); 
		Single single = new Single(player,cards); 
		StraightFlush straightflush = new StraightFlush(player,cards); 
		Quad quad = new Quad(player,cards); 
		FullHouse fullhouse = new FullHouse(player,cards); 
		Flush flush = new Flush(player,cards); 
		Straight straight = new Straight(player,cards); 
	
		if(straightflush.isValid())
		{
			return straightflush; 
		}
		
		if(quad.isValid())
		{
			return quad; 
		}
		
		if(fullhouse.isValid())
		{
			return fullhouse; 
		}
		
		if(flush.isValid())
		{
			return flush; 
		}
		
		if(straight.isValid())
		{	
			return straight; 
		}
		
		if(triple.isValid())
		{
			return triple; 
		}
		
		if(pair.isValid())
		{
			return pair; 
		}
		
		if(single.isValid())
		{
			return single; 
		}	
		
		return null; 
	}
}
