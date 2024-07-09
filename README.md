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
