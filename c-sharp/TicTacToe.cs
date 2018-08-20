using System;
using System.Collections.Generic;
using System.Linq;

namespace TicTacToe
{
    public class Game
    {

        // The game board and the game status
        public static string[] board = { "0", "1", "2", "3", "4", "5", "6", "7", "8" };
        public static int currentState;  // the current state of the game
        public static string currentPlayer; // the current player

        static void Main(string[] args)
        {
            InitGame();
            PrintBoard();
            do
            {
                GetHumanSpot();
                if (!GameIsOver() && !Tie())
                {
                    EvalBoard();
                }
            } while (!GameIsOver() && !Tie()); // repeat if not game-over
            Console.WriteLine("Game over\n");
        }

        public static void InitGame()
        {
            currentState = 0; // "playing" or ready to play
            currentPlayer = "X";  // cross plays first
        }

        public static String NextPlayer()
        {
            if (currentPlayer == "X")
            {
                return "O";
            }
            else
            {
                return "X";
            }
        }

        public static void GetHumanSpot()
        {
            bool validInput = false;  // for input validation
            Console.WriteLine("Enter [0-8]:\n");
            do
            {
                int spot = int.Parse(Console.ReadLine());//input.nextInt();
                if (board[spot] != "X" && board[spot] != "O")
                {
                    board[spot] = "X";  // update game-board content
                    PrintBoard();
                    validInput = true;  // input okay, exit loop
                }
                currentPlayer = NextPlayer();  // cross plays first
            } while (!validInput);  // repeat until input is valid
        }

        public static void EvalBoard()
        {
            bool foundSpot = false;
            do
            {
                if (board[4] == "4")
                {
                    board[4] = "O";
                    foundSpot = true;
                }
                else
                {
                    int spot = GetBestMove();
                    if (board[spot] != "X" && board[spot] != "O")
                    {
                        foundSpot = true;
                        board[spot] = "O";
                    }
                    else
                    {
                        foundSpot = false;
                    }
                }
            } while (!foundSpot);
            PrintBoard();
        }

        public static bool GameIsOver()
        {
            return board[0] == board[1] && board[1] == board[2] ||
              board[3] == board[4] && board[4] == board[5] ||
              board[6] == board[7] && board[7] == board[8] ||
              board[0] == board[3] && board[3] == board[6] ||
              board[1] == board[4] && board[4] == board[7] ||
              board[2] == board[5] && board[5] == board[8] ||
              board[0] == board[4] && board[4] == board[8] ||
              board[2] == board[4] && board[4] == board[6];
        }

        public static int GetBestMove()
        {
            List<string> availableSpaces = new List<string>();
            bool foundBestMove = false;
            int spot = 100;
            foreach (var s in board)
            {
                if (s != "X" && s != "O")
                {
                    availableSpaces.Add(s);
                }
            }
            foreach (var str in availableSpaces)
            {
                spot = int.Parse(str);
                board[spot] = "O";
                if (GameIsOver())
                {
                    foundBestMove = true;
                    board[spot] = str;
                    return spot;
                }
                else
                {
                    board[spot] = "X";
                    if (GameIsOver())
                    {
                        foundBestMove = true;
                        board[spot] = str;
                        return spot;
                    }
                    else
                    {
                        board[spot] = str;
                    }
                }
            }
            if (foundBestMove)
            {
                return spot;
            }
            else
            {
                int n = (new Random()).Next(0, availableSpaces.Count());
                return int.Parse(availableSpaces[n]);
            }
        }

        public static bool Tie()
        {
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
        public static void PrintBoard()
        {
            Console.WriteLine(" " + board[0] + " | " + board[1] + " | " + board[2] + "\n===+===+===\n" + " " + board[3] + " | " + board[4] + " | " + board[5] + "\n===+===+===\n" + " " + board[6] + " | " + board[7] + " | " + board[8] + "\n"); // print all the board cells
        }

    }
}
