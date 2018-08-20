class Game:
  def __init__(self):
    self.board = ["0", "1", "2", "3", "4", "5", "6", "7", "8"]
    self.com = "X" # the computer's marker
    self.hum = "O" # the user's marker

  def start_game(self):
    # start by printing the board
    print " %s | %s | %s \n===+===+===\n %s | %s | %s \n===+===+===\n %s | %s | %s \n" % \
        (self.board[0], self.board[1], self.board[2],
             self.board[3], self.board[4], self.board[5],
             self.board[6], self.board[7], self.board[8])
    print "Enter [0-8]:"
    # loop through until the game was won or tied
    while not self.game_is_over(self.board) and not self.tie(self.board):
      self.get_human_spot()
      if not self.game_is_over(self.board) and not self.tie(self.board):
        self.eval_board()

      print " %s | %s | %s \n===+===+===\n %s | %s | %s \n===+===+===\n %s | %s | %s \n" % \
          (self.board[0], self.board[1], self.board[2],
               self.board[3], self.board[4], self.board[5],
               self.board[6], self.board[7], self.board[8])

    print "Game over"

  def get_human_spot(self):
    spot = None
    while spot is None:
      spot = int(raw_input())
      if self.board[spot] != "X" and self.board[spot] != "O":
        self.board[spot] = self.hum
      else:
        spot = None

  def eval_board(self):
    spot = None
    while spot is None:
      if self.board[4] == "4":
        spot = 4
        self.board[spot] = self.com
      else:
        spot = self.get_best_move(self.board, self.com)
        if self.board[spot] != "X" and self.board[spot] != "O":
          self.board[spot] = self.com
        else:
          spot = None

  def get_best_move(self, board, next_player, depth = 0, best_score = {}):
    available_spaces = [s for s in board if s != "X" and s != "O"]
    best_move = None

    for avail in available_spaces:
      board[int(avail)] = self.com
      if self.game_is_over(board):
        best_move = int(avail)
        board[int(avail)] = avail
        return best_move
      else:
        board[int(avail)] = self.hum
        if self.game_is_over(board):
          best_move = int(avail)
          board[int(avail)] = avail
          return best_move
        else:
          board[int(avail)] = avail

    if best_move:
      return best_move
    else:
      return int(available_spaces[0])

  def three_in_a_row(self, *args):
    return args[0] == args[1] == args[2] == "X" or \
        args[0] == args[1] == args[2] == "O"

  def game_is_over(self, b):
    return self.three_in_a_row(b[0], b[1], b[2]) == 1 or \
        self.three_in_a_row(b[3], b[4], b[5]) == 1 or \
        self.three_in_a_row(b[6], b[7], b[8]) == 1 or \
        self.three_in_a_row(b[0], b[3], b[6]) == 1 or \
        self.three_in_a_row(b[1], b[4], b[7]) == 1 or \
        self.three_in_a_row(b[2], b[5], b[8]) == 1 or \
        self.three_in_a_row(b[0], b[4], b[8]) == 1 or \
        self.three_in_a_row(b[2], b[4], b[6]) == 1

  def tie(self, b):
    return len([s for s in b if s == "X" or s == "O"]) == 9

if __name__ == '__main__':
  game = Game()
  game.start_game()
