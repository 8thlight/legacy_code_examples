import scala.util.Random

/**
  * Tic-Tac-Toe: Two-player console version
  */

class Game {
  // board
  var b = List("0", "1", "2", "3", "4", "5", "6", "7", "8")
  // current player: X plays first
  var cp = "X"

  def go(): Unit = {
    printBoard(b)
    do {
      getHumanSpot()
      if (!gameOver && !tie()) {
        evalBoard()
      }
    } while (!gameOver && !tie()) // repeate if not gameOver
    println("Game over")
  }

  def printBoard(b: List[String]) = {
    // print the board
    println(" ")
    println(s" ${b(0)} | ${b(1)} | ${b(2)} \n===+===+===\n ${b(3)} | ${b(4)} | ${b(5)} \n===+===+===\n ${b(6)} | ${b(7)} | ${b(8)} ")
    println(" ")
  }

  def nextPlayer: String = {
    if (cp == "X") {
      return "O"
    } else {
      return "X"
    }
  }

  def getHumanSpot() = {
    // valid input is false
    var v = false;
    println("Enter [0-8]")
    do {
      // get spot
      var s = readLine()
      if (b(s.toInt) != "X" && b(s.toInt) != "O") {
        // update the board
        b = b.updated(s.toInt, "X")
        printBoard(b)
        v = true // input is valid, exit the loop
      }
      // change the current player
      cp = nextPlayer
    } while(!v) // ask for user input until input is valid
  }

  def evalBoard() = {
    var s = 100
    var f = false
    do {
      if (b(4) == "4") {
        b = b.updated(4, "O")
        f = true
      } else {
        s = getBestMove
        if (b(s) != "X" && b(s) != "O") {
          f = true
          b = b.updated(s, "O")
        } else {
          f = false
        }
      }
    } while (!f)
    printBoard(b)
  }

  /** return true if game was won */
  def gameOver = {
    b(0) == b(1) && b(1) == b(2) ||
      b(3) == b(4) && b(4) == b(5) ||
      b(6) == b(7) && b(7) == b(8) ||
      b(0) == b(3) && b(3) == b(6) ||
      b(1) == b(4) && b(4) == b(7) ||
      b(2) == b(5) && b(5) == b(8) ||
      b(0) == b(4) && b(4) == b(8) ||
      b(2) == b(4) && b(4) == b(6)
  }

  def getBestMove: Int = {
    var availableSpaces: List[String] = List()
    var f = false
    var s = 100
    for (i <- b) {
      if (i != "X" && i != "O") {
        availableSpaces = i :: availableSpaces
      }
    }
    for (as <- availableSpaces) {
      s = if (as.forall(_.isDigit)) as.toInt else 100
      b = b.updated(s, "O")
      if (gameOver) {
        f = true;
        b = b.updated(s, as)
        s
      } else {
        b = b.updated(s, "X")
        if (gameOver) {
          f = true
          b = b.updated(s, as)
          s
        } else {
          b = b.updated(s, as)
        }
      }
    }
    if (f) {
      s
    } else {
      val p = Random.shuffle(availableSpaces).head
      s = if (p.forall(_.isDigit)) p.toInt else 100
      s
    }
  }

  /** return true if game has no more empty cells */
  def tie() = {
    b(0) != 0 &&
      b(1) != "1" &&
      b(2) != "2" &&
      b(3) != "3" &&
      b(4) != "4" &&
      b(5) != "5" &&
      b(6) != "6" &&
      b(7) != "7" &&
      b(8) != "8"
  }
}

/**
  * Runs the game on the console
  */

object Game {
  def main(args: Array[String]): Unit = {
    (new Game).go()
  }
}
