# Java-Big-Two-Game-OOP-Project

This repo contains my school project to COMP2396. 

Project Overview
This project demonstrates advanced concepts in networking and multi-threading using Java. The goal is to enhance the Big Two card game to support four players over the internet. This involves creating a robust client-server architecture and ensuring seamless communication between clients and the server.

Specifications
Behavior of the Game Server
Player Connection: Upon a successful connection, the server sends a PLAYER_LIST message to the new client, specifying the playerID and the names of existing players.
Server Full: If the server is full, it sends a FULL message and closes the connection.
Connection Loss: When a client disconnects, the server broadcasts a QUIT message to all clients.
Message Handling: The server processes messages from clients, replacing the playerID with the correct value based on the socket connection.
Game Start: When all clients are ready, the server broadcasts a START message with a shuffled deck.
Chat Messages: The server broadcasts chat messages from clients to all other clients.
Behavior of the Game Client
User Prompt: The client prompts the user to enter their name and connects to the server.
Player List Update: Upon receiving a PLAYER_LIST message, the client updates the player list and sends a JOIN message to the server.
Game Start: The client starts a new game upon receiving a START message with the given deck.
Move Handling: The client sends a MOVE message when the local player makes a move and processes incoming MOVE messages from the server.
Chat Functionality: The client sends and displays chat messages.
BigTwoClient Class
Constructor: Initializes the client with references to the game and GUI objects.
Instance Variables: Includes references to the game, GUI, socket connection, output stream, playerID, playerName, serverIP, and serverPort.
NetworkGame Interface Methods:
getPlayerID(), setPlayerID(int playerID)
getPlayerName(), setPlayerName(String playerName)
getServerIP(), setServerIP(String serverIP)
getServerPort(), setServerPort(int serverPort)
connect(): Establishes a socket connection and creates a thread for receiving messages.
parseMessage(GameMessage message): Parses messages from the server.
sendMessage(GameMessage message): Sends messages to the server.
ServerHandler Inner Class: Implements Runnable to handle incoming messages in a separate thread.
Graphical User Interface
Connect Menu Item: Replaces the “Restart” menu item to establish a connection to the game server.
