#include <iostream>
#include <vector>

using namespace std;

class Game {
public:
  // The game board and the game status
  char board[9];
  int currentState; // the current state of the game
  char currentPlayer; // the current player

  /** Starts the game */
  void start() {
    // Initialize the game-board and current status
    initGame();
    printBoard();

    do {
      getHumanSpot();
      if (!gameIsOver() && !tie()) {
        evalBoard();
      }
    } while (!gameIsOver() && !tie()); // repeat if not game-over

    cout << "Game Over!" << endl;
  }

  /** Initializes the game */
  void initGame() {
    // Populate board with available spots
    for(int i = 1; i < 9; i++) {
      board[i] = '0' + i;
    }

    currentState = 0; // "playing" or ready to play
    currentPlayer = 'X'; // cross plays first
  }

  void printBoard() {
    cout << " " << board[0] << " | " << board[1] << " | " << board[2] << "\n===+===+===\n" << " " << board[3] << " | " << board[4] << " | " << board[5] << "\n===+===+===\n" << " " << board[6] << " | " << board[7] << " | " << board[8] << endl; // print all the board cells
  }
  /** Return true if it is a draw (no more empty cell) */
  // TODO: maybe there is an easeir way to check this
  bool tie() {
    return board[0] != '0' &&
           board[1] != '1' &&
           board[2] != '2' &&
           board[3] != '3' &&
           board[4] != '4' &&
           board[5] != '5' &&
           board[6] != '6' &&
           board[7] != '7' &&
           board[8] != '8';
//    return true;
  }

  char nextPlayer() {
    if (currentPlayer == 'X') {
      return 'O';
    } else {
      return 'X';
    }
  };

  void getHumanSpot() {
    bool validInput = false;  // for input validation
    cout << "Enter [0-8]:\n";
    do {
      int spot;
      cin >> spot;

      if (board[spot] != 'X' && board[spot] != 'O') {
        board[spot] = 'X';  // update game-board content
        printBoard();
        validInput = true;  // input okay, exit loop
      }
      currentPlayer = nextPlayer();  // cross plays first
    } while (!validInput);  // repeat until input is valid
  }

  bool gameIsOver() {
    return board[0] == board[1] && board[1] == board[2] ||
       board[3] == board[4] && board[4] == board[5] ||
       board[6] == board[7] && board[7] == board[8] ||
       board[0] == board[3] && board[3] == board[6] ||
       board[1] == board[4] && board[4] == board[7] ||
       board[2] == board[5] && board[5] == board[8] ||
       board[0] == board[4] && board[4] == board[8] ||
       board[2] == board[4] && board[4] == board[6];
  }

  void evalBoard() {
    bool foundSpot = false;
    do {
      if (board[4] == '4') {
        board[4] = 'O';
        foundSpot = true;
      } else {
        int spot = getBestMove();
        if (board[spot] != 'X' && board[spot] != 'O') {
          foundSpot = true;
          board[spot] = 'O';
        } else {
          foundSpot = false;
        }
      }
    } while (!foundSpot);
    printBoard();
  }

  int getBestMove() {
    std::vector<int> availableSpaces;
    bool foundBestMove = false;
    int spot = 100;
    for (int i = 0; i < 9; i++) {
      if(board[i] != 'X' && board[i] != 'O') {
        availableSpaces.push_back(i);
      }
    }

    for (int i = 0; i < availableSpaces.size(); i++) {
      spot = availableSpaces[i];
      board[spot] = 'O';
      if (gameIsOver()) {
        foundBestMove = true;
        board[spot] = '0' + spot;
        return spot;
      } else {
        board[spot] = 'X';
        if (gameIsOver()) {
          foundBestMove = true;
          board[spot] = '0' + spot;
          return spot;
        } else {
          board[spot] = '0' + spot;
        }
      }
    }
    if (foundBestMove) {
      return spot;
    } else {
      int n = rand() % availableSpaces.size();
      return availableSpaces[n];
    }
  }
};

/** The entry main method (the program starts here) */
int main() {
  Game game;
  game.start();

  return 0;
}
