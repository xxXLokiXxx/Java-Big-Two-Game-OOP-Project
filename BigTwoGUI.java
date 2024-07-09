import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.Image;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



/**
 * This class is used for modeling a graphical user interface for the Big Two card game.
 * 
 * @author Loki
 */
public class BigTwoGUI implements CardGameUI{
	private final static int MAX_CARD_NUM = 13; // max. no. of cards each player holds **
	private BigTwo game = null; // a BigTwo object
	private ArrayList<CardGamePlayer> playerList; // the list of players **
	private ArrayList<Hand> handsOnTable; // the list of hands played on the **
	private int activePlayer = -1; // the index of the active player
	private boolean[] selected = new boolean[MAX_CARD_NUM]; // selected cards
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private JTextArea chatArea;
	private JTextField chatInput;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	private JPanel buttomPanel;
	private JMenuItem connectButton;
	
	/**
	 * a constructor for creating a BigTwoGUI.
	 * 
	 * @param game a reference to a Big Two card game associates with this GUI.
	 */
	public BigTwoGUI(BigTwo game) {
		this.game = game;
		playerList = game.getPlayerList();
		handsOnTable = game.getHandsOnTable();
		this.imagesRetrieve();
		this.makeGUI();
	}
	
	private ArrayList<ArrayList<Image>> cardImages;
	
	private ArrayList<Image> tempCardArray;
	

	private Image imageOfBackCard;
	
	private ArrayList<Image> thumbNail;
	
	private void imagesRetrieve() {
		thumbNail = new ArrayList<Image>();                        // first save all the images that need to access hard disk in one go in array list, so there is faster loading time of the game
		thumbNail.add(new ImageIcon("avatars/pokemon_128.png").getImage());
		thumbNail.add(new ImageIcon("avatars/snorlax_128.png").getImage());
		thumbNail.add(new ImageIcon("avatars/megacharzardx_128.png").getImage());
		thumbNail.add(new ImageIcon("avatars/charzard_128.png").getImage());   //multiple thumbnails to choose in doc 
		
		
		char[] suit = {'d','c','h','s'};
		
		char[] rank = {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};
		
		imageOfBackCard = new ImageIcon("cards/b.gif").getImage();
		
		cardImages = new ArrayList<ArrayList<Image>>();
		String picLocate;
		for(int j=0;j<13;j++) {
			tempCardArray = new ArrayList<Image>();
			picLocate = new String();
			picLocate="cards/" + rank[j] + suit[0] + ".gif";  //save cards image
			tempCardArray.add(new ImageIcon(picLocate).getImage());
			
			picLocate = new String();
			picLocate="cards/" + rank[j] + suit[1] + ".gif";
			tempCardArray.add(new ImageIcon(picLocate).getImage());
			
			picLocate = new String();
			picLocate="cards/" + rank[j] + suit[2] + ".gif";
			tempCardArray.add(new ImageIcon(picLocate).getImage());
			
			picLocate = new String();
			picLocate="cards/" + rank[j] + suit[3] + ".gif";
			tempCardArray.add(new ImageIcon(picLocate).getImage());
			
			cardImages.add(tempCardArray);
		}
	}
	
	
	private void makeGUI() {
		frame = new JFrame("Big Two");
		frame.setSize(1100,900);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Top menu
		JMenuBar menuB = new JMenuBar();
		JMenu topMenu = new JMenu("Game");
		JMenu messageMenu = new JMenu("Message");
		
		connectButton = new JMenuItem("Connect"); // connect button made in menu of gui
		connectButton.addActionListener(new ConnectMenuItemListener());
		topMenu.add(connectButton);

		
		JMenuItem quitButton = new JMenuItem("Quit");
		quitButton.addActionListener(new QuitMenuItemListener());
		topMenu.add(quitButton);
		
		
		JMenuItem clearChatMsg = new JMenuItem("Clear Chat Message");
		clearChatMsg.addActionListener(new clearChatMessageListener());
		messageMenu.add(clearChatMsg);
		
		menuB.add(topMenu);
		menuB.add(messageMenu);
		frame.setJMenuBar(menuB);
		
		//bottom Panel
		buttomPanel = new JPanel();
		JPanel chatInButtonsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		playButton = new JButton("Play");                                        //Buttons inside panel
		playButton.addActionListener(new PlayButtonListener());
		passButton = new JButton("Pass");
		passButton.addActionListener(new PassButtonListener());
		
		JLabel l1 = new JLabel("Message:");                                      //chat input and label in panel
		chatInput = new JTextField(25);
		chatInput.addActionListener(new TextFieldListener());
		
		buttomPanel.setLayout(new BorderLayout());               //Adding buttons, chat input and label to bottom panel
		chatInButtonsPanel.setLayout(new BorderLayout());
		buttonsPanel.setLayout(new BorderLayout());
		buttonsPanel.add(BorderLayout.WEST, playButton);
		buttonsPanel.add(BorderLayout.CENTER, passButton);
		chatInButtonsPanel.add(BorderLayout.EAST, chatInput);
		chatInButtonsPanel.add(BorderLayout.CENTER, l1);
		buttomPanel.add(BorderLayout.EAST, chatInButtonsPanel);
		buttomPanel.add(BorderLayout.WEST, buttonsPanel);
		frame.add(BorderLayout.SOUTH, buttomPanel);
		
		//East Text panel
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		Font font = new Font("Century", Font.ITALIC, 15);
		msgArea.setFont(font);
		
		chatArea = new JTextArea();
		chatArea.setBackground(new Color(250,250,250));
		chatArea.setEditable(false);
		Font font2 = new Font("TimesRoman", Font.BOLD, 13);
		chatArea.setFont(font2);
		
		scrollPane = new JScrollPane (msgArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(frame.getWidth()*450/1100, frame.getHeight()*450/900));
		
		scrollPane2 = new JScrollPane (chatArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane2.setPreferredSize(new Dimension(frame.getWidth()*450/1100, frame.getHeight()*450/900));
		
		textPanel.add(scrollPane);
		textPanel.add(scrollPane2);
		
		frame.add(BorderLayout.EAST, textPanel);
		
		//Central BigTwo Panel
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setLayout(new BoxLayout(bigTwoPanel, BoxLayout.Y_AXIS));
		
		frame.add(BorderLayout.CENTER, bigTwoPanel);
		
		
		//Frame final setting 
	    frame.setVisible(true); 
	}
	/**
	 * a method for setting the index of the active player
	 * 
	 * @param activePlayer the index of the active player (i.e., the player who can
	 *                     make a move)
	 */
	public void setActivePlayer(int activePlayer) {
		if (activePlayer < 0 || activePlayer >= playerList.size()) {
			this.activePlayer = -1;
		} else {
			this.activePlayer = activePlayer;
		}
	}
	
	/**
	 * a method for creating a window for players to enter user name in client.
	 * 
	 * @return a string of entered user name of client.
	 */
	public String nameEnterWindow() {
		return (String) JOptionPane.showInputDialog("Enter your name: ");
	}
	
	/**
	 * a method for creating end game window to display game results.
	 * 
	 * @param lastPlayer integer of the last player played when game finished, which means the id of winner.
	 */
	public void endWindow(int lastPlayer) {
		String endmessage = "Game ended!";
		endmessage += "\n";
		for(int j = 0; j < game.getPlayerList().size();j++) {
			
			if(j == lastPlayer) {
				endmessage += game.getPlayerList().get(j).getName() + j + " wins the game."; // declare that player as winner
			}
			
			else
			{
				endmessage += game.getPlayerList().get(j).getName() + j + " has " + game.getPlayerList().get(j).getCardsInHand().size() + " cards in hand."; // list the number of cards left in the other players' hand
			}
			endmessage += "\n";
		}
		
		JOptionPane.showMessageDialog(null, endmessage);
	}
	
	/**
	 *  a method for repainting the GUI
	 */
	public void repaint() {
		frame.repaint();
	}

	/**
	 * a method for printing the specified string to the message area of the GUI.
	 * 
	 * @param msg the string to be printed to the message area of the GUI.
	 */
	public void printMsg(String msg) {
		msgArea.append(msg+"\n");
	}

	/**
	 * a method for clearing the game message area of the GUI. 
	 */
	public void clearMsgArea() {
		msgArea.setText(null);
	}
	
	/**
	 * a method for printing the specified string to the chat message area of the GUI.
	 * 
	 * @param msg the string to be printed to the chat message area of the GUI.
	 */
	public void printChat(String msg) {
		chatArea.append(msg+"\n");
	}
	
	/**
	 * a method for clearing the chat message area of the GUI. 
	 */
	public void clearChatArea() {
		chatArea.setText(null);
	}

	/**
	 * a method for resetting the GUI. 
	 */
	public void reset() {
		resetSelected();       //reset the list of selected cards;
		clearMsgArea();        // clear the game message area;
		clearChatArea();       // clear the chat message area;
		enable();              //enable user interactions
	}

	/**
	 *  a method for enabling user interactions with the GUI.
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);                //enable and disable interactive components
		bigTwoPanel.setEnabled(true);
		chatInput.setEnabled(true);
	}

	/**
	 *  a method for disabling user interactions with the GUI.
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
		chatInput.setEnabled(false);
	}
	
	/**
	 *  a method for partially disabling user interactions with the GUI.
	 */
	public void partialDisable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);

	}
	
	/**
	 *  a method for disabling connection button the GUI.
	 */
	public void disableConnectButton() {
		connectButton.setEnabled(false);
	}

	/**
	 * a method for prompting active player to select cards and make his/her move.
	 */
	
	public void promptActivePlayer() {

		int[] cardIdx = getSelected();
		resetSelected();

		game.makeMove(activePlayer, cardIdx);
	
	}
	
	/**
	 * an inner class that extends the JPanel class and implements the 
	 * MouseListener interface. Overrides the paintComponent() method inherited from the 
	 * JPanel class to draw the card game table. Implements the mouseReleased() method 
	 * from the MouseListener interface to handle mouse click events. 
	 * 
	 * @author Loki
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		
	
		private int originalYcoorOfCard;
		private int raiseUp;
		private int originalXcoorOfCard;
		private int widthOfCard;
		private int yMovePerPlayer;
		private int widthOfBigCard;
		private int heightOfBigCard;

		
		/**
		 * Default constructor for registering mouse listener with BigTwo Panel.
		 */
		public BigTwoPanel() {
			this.addMouseListener(this);
		}
		
		/**
		 * Overriding paintComponent to paint all the components on the card game table.
		 * 
		 * @param g Graphic object provided by system.
		 */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);                                   //let super class to paint component
			
			this.setBackground(Color.GREEN.darker().darker());             //set background
			g.setColor(Color.BLACK);
			
			
			
			scrollPane.setPreferredSize(new Dimension(frame.getWidth()*450/1100, frame.getHeight()*450/900));
			scrollPane2.setPreferredSize(new Dimension(frame.getWidth()*450/1100, frame.getHeight()*450/900));
			buttomPanel.setPreferredSize(new Dimension(frame.getWidth()*1100/1100, frame.getHeight()*26/900));
			
			for (int i =0; i<4; ++i) {
				
			if (game.getPlayerList().get(i).getName() != "") {
				
			//paint avatars
			
			g.drawImage(thumbNail.get(i), frame.getWidth()*10/1100, frame.getHeight()*(30+i*160)/900, frame.getWidth()*128/1100, frame.getHeight()*128/900, this);
			
			}
			
			}
			
			//paint frame
			g.drawLine(0, frame.getHeight()*160/900, 2000, frame.getHeight()*160/900);
			g.drawLine(0, frame.getHeight()*320/900, 2000, frame.getHeight()*320/900);           // All sizes resizable when window resized
			g.drawLine(0, frame.getHeight()*480/900, 2000, frame.getHeight()*480/900);
			g.drawLine(0, frame.getHeight()*640/900, 2000, frame.getHeight()*640/900);

			
			
			
			for (int i =0; i<4; ++i) {
				
			    if(activePlayer==i) {
					g.setColor(Color.WHITE);
					g.drawString(game.getPlayerList().get(i).getName(), frame.getWidth()*30/1100 , frame.getHeight()*(20 + 160*i)/900);    //draw player name tags
				}
				else {
					g.drawString(game.getPlayerList().get(i).getName(), frame.getWidth()*30/1100 , frame.getHeight()*(20 + 160*i)/900); 
				}
				g.setColor(Color.BLACK);
			    
			    
			    
			    for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); j++) {
			    
			    	if (activePlayer != i) {          //showing cards for non active players
			    		g.drawImage(imageOfBackCard, frame.getWidth()*(155+20*j)/1100, frame.getHeight()*(43+160*i)/900, frame.getWidth()*(73)/1100, frame.getHeight()*(97)/900, null);
			    	} 
			    
			    	else if (activePlayer == i) {              //hiding cards for active active players
			    		if (selected[j] == true) {
			    			g.drawImage(cardImages.get(game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()).get(game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()), frame.getWidth()*(155+20*j)/1100, frame.getHeight()*(43+160*i-20)/900, frame.getWidth()*(73)/1100, frame.getHeight()*(97)/900, null);
			    		}	
			    		else {
			    		g.drawImage(cardImages.get(game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()).get(game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()), frame.getWidth()*(155+20*j)/1100, frame.getHeight()*(43+160*i)/900, frame.getWidth()*(73)/1100, frame.getHeight()*(97)/900, null);
			    		}
					}
			    }
			}
		    

			handsOnTable = game.getHandsOnTable();
			int HandsSize = game.getHandsOnTable().size();        // draw lower part of the graphical panel, which is components regarding on hands on table

		    if (HandsSize == 0) {
		    	
		    	g.drawString("Last Hand on Table", frame.getWidth()*10/1100, frame.getHeight()*660/900);
		    }
		    else {
		    	g.drawString("Played by " + game.getHandsOnTable().get(HandsSize-1).getPlayer().getName(), frame.getWidth()*10/1100, frame.getHeight()*660/900);
		    	
		    	Hand lastHandOnTable = handsOnTable.get(HandsSize - 1);
		    	for (int i = 0; i < lastHandOnTable.size(); i++)
	    		{
	    			g.drawImage(cardImages.get(lastHandOnTable.getCard(i).getRank()).get(lastHandOnTable.getCard(i).getSuit()), frame.getWidth()*(10+20*i)/1100, frame.getHeight()*680/900,frame.getWidth()*(73)/1100, frame.getHeight()*(97)/900, null);
	    		}
	    		
		    }
		    originalYcoorOfCard = frame.getHeight()*(43)/900;
			raiseUp = frame.getHeight()*(20)/900;
			originalXcoorOfCard = frame.getWidth()*155/1100;
			widthOfCard = frame.getWidth()*(20)/1100;
			yMovePerPlayer = frame.getHeight()*(160)/900;           // coordinates adjusted to frame size
			widthOfBigCard = frame.getWidth()*73/1100;              // put back to instance variables
			heightOfBigCard = frame.getHeight()*(97)/900;
		    repaint();
		}
		
		
		
		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		
		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			
		}
		
		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void mouseReleased(MouseEvent e) {   //detect click of cards even after frame resized
			
			if ( game.getPlayerList().get(activePlayer).getNumOfCards() != 0) {
			
			int cardForValidate = game.getPlayerList().get(activePlayer).getNumOfCards()-1;
			
			
			 //for loop to check cards before last card
			for (int i = 0; i <= cardForValidate-1; i++) {
				if (e.getX() >= (originalXcoorOfCard+i*widthOfCard) && e.getX() <= (originalXcoorOfCard+(i+1)*widthOfCard)) {       // - x axis (card to card width case)
					if(!selected[i] && e.getY() >= (originalYcoorOfCard+yMovePerPlayer*activePlayer) && e.getY() <= (originalYcoorOfCard+yMovePerPlayer*activePlayer+heightOfBigCard)) { //- y axis
						selected[i] = true;
					} 
					else if (selected[i] && e.getY() >= (-raiseUp + yMovePerPlayer*activePlayer + originalYcoorOfCard) && e.getY() <= (-raiseUp+yMovePerPlayer*activePlayer+heightOfBigCard+originalYcoorOfCard)) {
						selected[i] = false;	
					}
				}
			}
			
			if (e.getX() >= (originalXcoorOfCard+cardForValidate*widthOfCard) && e.getX() <= (originalXcoorOfCard+cardForValidate*widthOfCard+widthOfBigCard)) //det last card x axis in bound with selecting
			{
				if(!selected[cardForValidate] && e.getY() >= (originalYcoorOfCard + yMovePerPlayer*activePlayer) && e.getY() <= (originalYcoorOfCard + yMovePerPlayer*activePlayer+heightOfBigCard))  //det last card y axis in bound
				{
					selected[cardForValidate] = true;   //select last card when card not raised
		
				} 
				else if (selected[cardForValidate] && e.getY() >= (-raiseUp + yMovePerPlayer*activePlayer + originalYcoorOfCard) && e.getY() <= (-raiseUp + yMovePerPlayer*activePlayer+heightOfBigCard+ originalYcoorOfCard))
				{
					selected[cardForValidate] = false;   //deselect last card when card raised
				
				}
			}
			this.repaint();
			
			}
		}
		
		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		
		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			
		}
	}
	
	/**
	 * an inner class that implements the ActionListener 
     * interface to handle button-click events for the Play button.
     * 
     * @author Loki
	 */
	private class PlayButtonListener implements ActionListener{
		
		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {    //   handle button-click events for the Play button.
			if (getSelected() != null) {
				promptActivePlayer();
				repaint();      
			}
			else {
				printMsg("Select cards to play.");
				printMsg(game.getPlayerList().get(game.getCurrentPlayerIdx()).getName()+ "'s turn :");
			}
		}
	}
	
	/**
	 *  an inner class that implements the ActionListener to handle button-click events for the Pass button
	 *  
	 *  @author Loki
	 */
	private class PassButtonListener implements ActionListener{

		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			resetSelected();                                   // pass to make move with no selected cards.
			promptActivePlayer();
			repaint();
		}
	}
	
	/**
	 * an inner class that implements the ActionListener
	 * interface. Implements the actionPerformed() method from the ActionListener interface 
     * to handle menu-item-click events for the Quit menu item.
     * 
     * @author Loki
	 */
	private class QuitMenuItemListener implements ActionListener{

		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {                      //terminate the game by clicking quit
			System.exit(0);
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface to handle chat bar entering event for chat input item.
	 * 
	 * @author Loki
	 */
	private class TextFieldListener implements ActionListener {
		
		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {  
			String inputString = chatInput.getText();                                //handle chat entering issues
	        game.sendMessageViaBigTwo(inputString);
	        chatInput.setText("");
	      }
	   }
	
	/**
	 * an inner class that implements the ActionListener interface to handle chat message area clearing event.
	 * 
	 * @author Loki
	 */
	private class clearChatMessageListener implements ActionListener{
		
		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {                      //clear chat message
			clearChatArea();
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface to handle connect-button-clicking event.
	 * 
	 * @author Loki
	 */
	class ConnectMenuItemListener implements ActionListener {
		
		/*  
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			game.connectViaBigTwo();              //attempt to connect to server when connect button pressed
		}
	}
	
	/**
	 * Returns an array of indices of the cards selected through the UI.
	 * 
	 * @return an array of indices of the cards selected, or null if no valid cards
	 *         have been selected
	 */
	private int[] getSelected() {
		
		int[] cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}

		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					cardIdx[count] = j;
					count++;
				}
			}
		}
		return cardIdx;
	}

	/**
	 * Resets the list of selected cards to an empty list.
	 */
	private void resetSelected() {
		for (int j = 0; j < selected.length; j++) {
			selected[j] = false;
		}
	}
}
