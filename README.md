# Java-Big-Two-Game-OOP-Project
------


Author: Lo Lok Fung

This repository contains my school project for COMP2396. The project is a Java implementation of the Big Two card game, showcasing Object-Oriented Programming (OOP) principles.

## Table of Contents
- Introduction
- Features
- Class Structure
- Game Rules
- Installation
- Usage
- Contributing
- License

## Introduction
The Big Two game is a popular card game in East Asia. This project demonstrates my understanding of OOP concepts by implementing the game in Java. The project includes various classes and methods to handle the game's logic, user interface, and interactions.

## Features
- **Graphical User Interface (GUI)**: A user-friendly interface to play the game.
- **Multiplayer Support**: Play against AI or other players.
- **Robust Game Logic**: Handles all game rules and edge cases.
- **Extensible Design**: Easy to add new features or modify existing ones.

## Class Structure
### Main Classes
- **BigTwo**: The main class that initializes and starts the game.
- **BigTwoDeck**: Manages the deck of cards.
- **BigTwoCard**: Represents a single card in the game.
- **BigTwoPlayer**: Represents a player in the game.
- **BigTwoTable**: Manages the game table and user interface.

### Supporting Classes
- **CardGame**: An interface for card games.
- **CardGamePlayer**: An interface for card game players.
- **Deck**: An interface for card decks.
- **Hand**: Represents a hand of cards.
- **Card**: Represents a generic card.

## Game Rules
- **Objective**: Be the first to play all your cards.
- **Card Ranking**: Cards are ranked by their number and suit.
- **Valid Hands**: Single, Pair, Triple, Straight, Flush, Full House, Four of a Kind, Straight Flush.
- **Gameplay**: Players take turns to play a higher-ranked hand or pass.

## Specifications

### Behavior of the Game Server
- **Player List**: Upon a successful connection, the server sends a PLAYER_LIST message to the client, specifying the playerID and the names of existing players.
- **Full Server**: If the server is full, it sends a FULL message to the client and closes the connection.
- **Connection Lost**: When a connection is lost, the server broadcasts a QUIT message to all clients.
- **Message Handling**: The server replaces the playerID in incoming messages with the correct value based on the socket connection.
- **Join**: Upon receiving a JOIN message, the server broadcasts it to all clients.
- **Ready**: Upon receiving a READY message, the server broadcasts it to all clients.
- **Start**: When all clients are ready, the server broadcasts a START message with a shuffled deck.
- **Chat**: Upon receiving a MSG message, the server broadcasts it to all clients.
- **Move**: Upon receiving a MOVE message, the server broadcasts it to all clients.

### Behavior of the Client
- **Startup**: Prompts the user to enter their name and connects to the game server.
- **Player List**: Updates the player list upon receiving a PLAYER_LIST message.
- **Join**: Sends a JOIN message to the server after updating the player list.
- **Full Server**: Displays a message if the server is full.
- **Connection Lost**: Removes a player from the game and stops the game if a connection is lost.
- **Ready**: Displays a message when a player is ready.
- **Start**: Starts a new game with the given deck upon receiving a START message.
- **Move**: Sends a MOVE message when the local player makes a move.
- **Chat**: Sends a MSG message when the local player sends a chat message.
- **Game End**: Displays the game results and sends a READY message when the game ends.
- **Connect**: Establishes a connection to the server when the user selects “Connect” from the menu.
- **Quit**: Closes the window and terminates the client when the user selects “Quit” from the menu.

### BigTwoClient Class
#### Constructor
- **BigTwoClient(BigTwo game, BigTwoGUI gui)**: Creates a Big Two client with references to the game and GUI objects.

### Instance Variables
- **BigTwo game**: The Big Two card game object.
- **BigTwoGUI gui**: The GUI object for the Big Two card game.
- **Socket sock**: The socket connection to the game server.
- **ObjectOutputStream oos**: The output stream for sending messages to the server.
- **int playerID**: The playerID of the local player.
- **String playerName**: The name of the local player.
- **String serverIP**: The IP address of the game server.
- **int serverPort**: The TCP port of the game server.

#### NetworkGame Interface Methods
- **int getPlayerID()**: Gets the playerID of the local player.
- **void setPlayerID(int playerID)**: Sets the playerID of the local player.
- **String getPlayerName()**: Gets the name of the local player.
- **void setPlayerName(String playerName)**: Sets the name of the local player.
- **String getServerIP()**: Gets the IP address of the game server.
- **void setServerIP(String serverIP)**: Sets the IP address of the game server.
- **int getServerPort()**: Gets the TCP port of the game server.
- **void setServerPort(int serverPort)**: Sets the TCP port of the game server.
- **void connect()**: Establishes a socket connection with the game server.
- **void parseMessage(GameMessage message)**: Parses messages received from the game server.
- **void sendMessage(GameMessage message)**: Sends a message to the game server.

#### Inner Class
- **ServerHandler**: Implements the Runnable interface. The run() method handles receiving messages from the game server and calls parseMessage() to process them.

### Graphical User Interface
- **Connect Menu Item**: Replaces the “Restart” menu item with a “Connect” menu item for establishing a connection to the game server.

## Installation
1. **Clone the repository**:
    ```bash
    git clone https://github.com/xxXLokiXxx/Java-Big-Two-Game-OOP-Project.git
    ```
2. **Navigate to the project directory**:
    ```bash
    cd Java-Big-Two-Game-OOP-Project
    ```
3. **Compile the project**:
    ```bash
    javac *.java
    ```

## Usage
1. **Run the game**:
    ```bash
    java BigTwo
    ```
2. **Follow the on-screen instructions to play the game**.

## Contributing
Contributions are welcome! Please fork this repository and submit a pull request.

## License
This project is licensed under the MIT License.
