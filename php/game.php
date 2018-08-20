<?php

class Game {
  // The game board and the game status
  public static $board = array("0", "1", "2", "3", "4", "5", "6", "7", "8");
  public static $currentState;  // the current state of the game
  public static $currentPlayer; // the current player

  /** The entry main method (the program starts here) */
  public static function main() {
    // Initialize the game-board and current status
    self::initGame();
    self::printBoard();
    do {
      self::getHumanSpot();
      if (!self::gameIsOver() && !self::tie()) {
        self::evalBoard();
      }
    } while (!self::gameIsOver() && !self::tie()); // repeat if not game-over
    echo("Game over\n");
  }

  /** Initializes the game */
  public static function initGame() {
    self::$currentState = 0; // "playing" or ready to play
    self::$currentPlayer = "X";  // cross plays first
  }

  public static function nextPlayer() {
    if (self::$currentPlayer == "X") {
      return "O";
    } else {
      return "X";
    }
  }

  /** Update global variables "board" and "currentPlayer". */
  public static function getHumanSpot() {
    $validInput = false;  // for input validation
    echo("Enter [0-8]:\n");
    do {
      $spot = stream_get_line(STDIN, 1024, PHP_EOL);
      if (self::$board[$spot] != "X" && self::$board[$spot] != "O") {
        self::$board[$spot] = "X";  // update game-board content
        self::printBoard();
        $validInput = true;  // input okay, exit loop
      }
      self::$currentPlayer = self::nextPlayer();  // cross plays first
    } while (!$validInput);  // repeat until input is valid
  }

  public static function evalBoard() {
    $foundSpot = false;
    do {
      if (self::$board[4] == "4") {
        self::$board[4] = "O";
        $foundSpot = true;
      } else {
        $spot = self::getBestMove();
        if (self::$board[$spot] != "X" && self::$board[$spot] != "O") {
          $foundSpot = true;
          self::$board[$spot] = "O";
        } else {
          $foundSpot = false;
        }
      }
    } while (!$foundSpot);
    self::printBoard();
  }

  /** Return true if the game was just won */
  public static function gameIsOver() {
    return self::$board[0] == self::$board[1] && self::$board[1] == self::$board[2] ||
      self::$board[3] == self::$board[4] && self::$board[4] == self::$board[5] ||
      self::$board[6] == self::$board[7] && self::$board[7] == self::$board[8] ||
      self::$board[0] == self::$board[3] && self::$board[3] == self::$board[6] ||
      self::$board[1] == self::$board[4] && self::$board[4] == self::$board[7] ||
      self::$board[2] == self::$board[5] && self::$board[5] == self::$board[8] ||
      self::$board[0] == self::$board[4] && self::$board[4] == self::$board[8] ||
      self::$board[2] == self::$board[4] && self::$board[4] == self::$board[6];
  }


  public static function getBestMove() {
    $availableSpaces = array();
    $foundBestMove = false;
    $spot = 100;
    foreach (self::$board as $s) {
      if ($s != "X" && $s != "O") {
        $availableSpaces[] = $s;
      }
    }
    foreach ($availableSpaces as $as) {
      $spot = intval($as);
      self::$board[$spot] = "O";
      if (self::gameIsOver()) {
        $foundBestMove = true;
        self::$board[$spot] = $as;
        return $spot;
      } else {
        self::$board[$spot] = "X";
        if (self::gameIsOver()) {
          $foundBestMove = true;
          self::$board[$spot] = $as;
          return $spot;
        } else {
          self::$board[$spot] = $as;
        }
      }
    }
    if ($foundBestMove) {
      return $spot;
    } else {
      $n = rand(0, count($availableSpaces));
      return intval($availableSpaces[$n]);
    }
  }

  /** Return true if it is a draw (no more empty cell) */
  // TODO: maybe there is an easeir way to check this
  public static function tie() {
    return self::$board[0] != "0" &&
           self::$board[1] != "1" &&
           self::$board[2] != "2" &&
           self::$board[3] != "3" &&
           self::$board[4] != "4" &&
           self::$board[5] != "5" &&
           self::$board[6] != "6" &&
           self::$board[7] != "7" &&
           self::$board[8] != "8";
  }

  /** Print the game board */
  public static function printBoard() {
    echo(" " . self::$board[0] . " | " . self::$board[1] . " | " . self::$board[2] . "\n===.===.===\n" . " " . self::$board[3] . " | " . self::$board[4] . " | " . self::$board[5] . "\n===.===.===\n" . " " . self::$board[6] . " | " . self::$board[7] . " | " . self::$board[8] . "\n\n"); // print all the self::$board cells
  }

}

Game::main();
