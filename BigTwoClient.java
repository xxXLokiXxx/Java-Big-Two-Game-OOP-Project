import java.io.*;
import java.net.*;

/**
 * The BigTwoClient class implements the NetworkGame interface. It is used to model a Big 
 * Two game client that is responsible for establishing a connection and communicating with 
 * the Big Two game server. 
 * 
 * @author Loki
 * 
 */
public class BigTwoClient implements NetworkGame{

	private int playerID = -1;          // an integer specifying the playerID (i.e., index) of the local player

	private String playerName;       // a string specifying the name of the local player.

	private String serverIP;          // a string specifying the IP address of the game server.

	private int serverPort;           // an integer specifying the TCP port of the game server.

	
	private Socket sock;              // a socket connection to the game server.

	private ObjectOutputStream oos;   //an ObjectOutputStream for sending messages to the server.

	private BigTwoGUI gui;            //a BigTwoGUI object for the Big Two card game.

	private BigTwo game;                // a BigTwo object for the Big Two card game.
	
	private boolean flag = false;
	
	private ObjectInputStream ois;
	/**
	 * a constructor for creating a Big Two client.
	 * 
	 * @param game is a reference to a BigTwo object associated with this client 
	 * @param gui is a reference to a BigTwoGUI object associated the BigTwo object.
	 */
	public BigTwoClient(BigTwo game, BigTwoGUI gui, int num) {
		this.game = game;
		this.gui = gui;                   //set up the parameters' pass in variables to clients' instance variables
		playerName = gui.nameEnterWindow();

		if(playerName != null) {                 // prompt for user to input name, if not inputed then use default names
			setPlayerName(playerName);
		}
		else {
			setPlayerName("Default_Name"+ "_");
			flag=true;
		}
		setServerIP("127.0.0.1");              //set up ip and port of server for instance of clients
		setServerPort(80);
		
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public int getPlayerID() {
		return this.playerID;
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayerID(int playerID) {
		this.playerID = playerID;               // getter and setter of player id
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;                         // getter and setter of player names
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public String getServerIP() {
		return this.serverIP;
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;                // getter and setter of player ip of server
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public int getServerPort() {
		return serverPort;               
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;       // getter and setter of player port of server
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public void connect() {
		try {
			sock = new Socket(getServerIP(), getServerPort());  //create an ObjectOutputStream for sending messages to the game server
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
			Runnable ser_Hand = new ServerHandler();
			Thread thread = new Thread(ser_Hand);
			thread.start();                                         // create a new thread for receiving messages from the game server
			gui.partialDisable();
                //disable gui except chat before start
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
	

	}
	
	/*  
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void parseMessage(GameMessage message) {
		// parses the message based on it type
				switch (message.getType()) {
				
				    case CardGameMessage.PLAYER_LIST:
						
						this.playerID = message.getPlayerID();
						
							//set the playerID of the local player and update the names in the player list
						for (int i = 0; i < 4; i++) {
							String client_name = ((String[])message.getData())[i];
							if (client_name != null) {
								game.getPlayerList().get(i).setName(client_name);
							}
							
						}
						
						//a message of type JOIN to server
						if (flag) {
							sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, this.getPlayerName()+message.getPlayerID()));
						}
						else {
							sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, this.getPlayerName()));
						}
						break;
				 
						
					case CardGameMessage.JOIN:
						gui.disableConnectButton();  //due to the present of client connection establishment, no need connect to server again so connect button is disabled
						
						game.getPlayerList().get(message.getPlayerID()).setName((String)message.getData());  //add a new player to the player list by updating his/her name. 
						
						
						if (message.getPlayerID()==playerID) {  // client send a message of type READY when identical id of message and client
							sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
						}
						
						gui.printMsg(game.getPlayerList().get(message.getPlayerID()).getName()+ " joined the game.");  //print join game msg in game msg area box in gui
						break;
						
						
					case CardGameMessage.FULL:
						gui.printMsg("Game is full already. Bye!");  
						
						try {
							sock.close();  // close the connection to server
						}
						catch(IOException e) {
							e.printStackTrace();
						}
						break;
						
					case CardGameMessage.QUIT:
						int leaveID= message.getPlayerID();
						String leaveName= game.getPlayerList().get(leaveID).getName();
						game.getPlayerList().get(leaveID).setName("");   //he client removes a player from the game by setting his/her name to an empty string.
						gui.printMsg(leaveName + " " + "left the game.");  //pring in msg box for who left the game
				
						if (game.endOfGame() != true) {
							gui.partialDisable();           //If a game is in progress, the client stop the game 
							game.getPlayerList().get(0).removeAllCards();
							game.getPlayerList().get(1).removeAllCards();
							game.getPlayerList().get(2).removeAllCards();
							game.getPlayerList().get(3).removeAllCards();
							sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));  // and send ready message to server
						}
						break;
						
					case CardGameMessage.READY:
						// marks the specified player as ready for a new game
						gui.printMsg("Player " +game.getPlayerList().get(message.getPlayerID()).getName()+ " is ready.");  // print whos ready on msg box
						break;
					
					case CardGameMessage.START:
						gui.clearMsgArea();
						gui.printMsg("All players are ready. Game starts.");
						game.start((BigTwoDeck)message.getData());     //new game started using start method with the given deck of cards 
						gui.setActivePlayer(playerID);            //set gui for each exclusive client
						break;
						
					case CardGameMessage.MOVE:
						game.checkMove(message.getPlayerID(), (int[])message.getData());        // call the checkMove() method from the CardGame interface
						break;
	
					case CardGameMessage.MSG:
						gui.printChat((String)message.getData()); // append string on chat area in GUI for all clients
						break;

					default:
						System.out.println("Wrong message type: " + message.getType());
						// invalid message
						break;
				
				}
	}

	/*  
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);         //called whenever the client wants to communicate with the game server or other clients
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * An inner class that implements the Runnable interface.
     * Implementing the run() method from the Runnable interface and create a thread 
     * with an instance of this class as its job in the connect() method from the NetworkGame 
     * interface for receiving messages from the game server
     * 
     * @author Loki
	 */
	class ServerHandler implements Runnable {

		/*  
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void run() { 
			CardGameMessage messageFromSev = null;
			try {
				
				while(sock.isClosed() != true) {                                      // thread to run this method to check any income msg from server and act based on types of msg received
					messageFromSev = (CardGameMessage) ois.readObject();
					if (messageFromSev != null) {
						parseMessage(messageFromSev);         //the parseMessage() method from the NetworkGame interface is called to parse the messages accordingly.

					}
				}
				ois.close();
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
