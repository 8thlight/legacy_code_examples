import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Tic-Tac-Toe: TWo-player console version.
 */
public class Game {
  // The game board and the game status
  public static String[] board = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
  public static int currentState;  // the current state of the game
  public static String currentPlayer; // the current player

  public static Scanner input = new Scanner(System.in); // the input Scanner

  /** The entry main method (the program starts here) */
  public static void main(String[] args) {
    // Initialize the game-board and current status
    initGame();
    printBoard();
    do {
      getHumanSpot();
      if (!gameIsOver() && !tie()) {
        evalBoard();
      }
    } while (!gameIsOver() && !tie()); // repeat if not game-over
    System.out.print("Game over\n");
  }

  /** Initializes the game */
  public static void initGame() {
    currentState = 0; // "playing" or ready to play
    currentPlayer = "X";  // cross plays first
  }

  public static String nextPlayer() {
    if (currentPlayer == "X") {
      return "O";
    } else {
      return "X";
    }
  }

  /** Update global variables "board" and "currentPlayer". */
  public static void getHumanSpot() {
    boolean validInput = false;  // for input validation
    System.out.print("Enter [0-8]:\n");
    do {
      int spot = input.nextInt();
      if (board[spot] != "X" && board[spot] != "O") {
        board[spot] = "X";  // update game-board content
        printBoard();
        validInput = true;  // input okay, exit loop
      }
      currentPlayer = nextPlayer();  // cross plays first
    } while (!validInput);  // repeat until input is valid
  }

  public static void evalBoard() {
    boolean foundSpot = false;
    do {
      if (board[4] == "4") {
        board[4] = "O";
        foundSpot = true;
      } else {
        int spot = getBestMove();
        if (board[spot] != "X" && board[spot] != "O") {
          foundSpot = true;
          board[spot] = "O";
        } else {
          foundSpot = false;
        }
      }
    } while (!foundSpot);
    printBoard();
  }

  /** Return true if the game was just won */
  public static boolean gameIsOver() {
    return board[0] == board[1] && board[1] == board[2] ||
      board[3] == board[4] && board[4] == board[5] ||
      board[6] == board[7] && board[7] == board[8] ||
      board[0] == board[3] && board[3] == board[6] ||
      board[1] == board[4] && board[4] == board[7] ||
      board[2] == board[5] && board[5] == board[8] ||
      board[0] == board[4] && board[4] == board[8] ||
      board[2] == board[4] && board[4] == board[6];
  }


  public static int getBestMove() {
    ArrayList<String> availableSpaces = new ArrayList<String>();
    boolean foundBestMove = false;
    int spot = 100;
    for (String s: board) {
      if (s != "X" && s != "O") {
        availableSpaces.add(s);
      }
    }
    for (String as: availableSpaces) {
      spot = Integer.parseInt(as);
      board[spot] = "O";
      if (gameIsOver()) {
        foundBestMove = true;
        board[spot] = as;
        return spot;
      } else {
        board[spot] = "X";
        if (gameIsOver()) {
          foundBestMove = true;
          board[spot] = as;
          return spot;
        } else {
          board[spot] = as;
        }
      }
    }
    if (foundBestMove) {
      return spot;
    } else {
      int n = ThreadLocalRandom.current().nextInt(0, availableSpaces.size());
      return Integer.parseInt(availableSpaces.get(n));
    }
  }

  /** Return true if it is a draw (no more empty cell) */
  // TODO: maybe there is an easeir way to check this
  public static boolean tie() {
    return board[0] != "0" &&
           board[1] != "1" &&
           board[2] != "2" &&
           board[3] != "3" &&
           board[4] != "4" &&
           board[5] != "5" &&
           board[6] != "6" &&
           board[7] != "7" &&
           board[8] != "8";
  }

  /** Print the game board */
  public static void printBoard() {
    System.out.println(" " + board[0] + " | " + board[1] + " | " + board[2] + "\n===+===+===\n" + " " + board[3] + " | " + board[4] + " | " + board[5] + "\n===+===+===\n" + " " + board[6] + " | " + board[7] + " | " + board[8] + "\n"); // print all the board cells
  }

}
