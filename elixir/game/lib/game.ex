defmodule Game do
  def start_game do
    b = [0, 1, 2, 3, 4, 5, 6, 7, 8]
    print_board(b)
    play_moves(b, "X")
  end

  def print_board(b) do
    IO.write("\n--------------\n\n")
    IO.write("  #{Enum.at(b, 0)}   #{Enum.at(b, 1)}   #{Enum.at(b, 2)}\n ===+===+=== \n  #{Enum.at(b, 3)}   #{Enum.at(b, 4)}   #{Enum.at(b, 5)}\n ===+===+=== \n  #{Enum.at(b, 6)}   #{Enum.at(b, 7)}   #{Enum.at(b, 8)}\n")
    IO.write("\n--------------\n\n")
  end

  def play_moves(board, player) do
    if(game_over(board) || tie(board)) do
      IO.write("Game Over")
    else
      play_moves(choose_move(board, player), toggle_player(player))
    end
  end

  def choose_move(board, player) do
    cond do
      player == "X" -> get_humans_turn(board)
      player == "O" -> get_computers_turn(board)
    end
  end

  def toggle_player(player) do
    if(player == "X") do
      "O"
    else "X"
    end
  end

  def get_humans_turn(board) do
    IO.write("Enter [0-8]")
    next_move = IO.gets(">")
    {num, _} = Integer.parse(next_move)

    # if its a free space
    if Enum.at(board, num) != "X" && Enum.at(board, num) != "O" do
      updated_board = List.update_at(board, num, fn(x) -> "X" end)
      print_board(updated_board)
      updated_board
    else
      get_humans_turn(board)
    end
  end

  def get_computers_turn(board) do
    if Enum.at(board, 4) == 4 do
      new_board = List.update_at(board, 4, fn(x) -> "O" end)
      print_board(new_board)
      new_board
    else
      comp_turn = evaluate(board)
      new_board = List.update_at(board, comp_turn, fn(x) -> "O" end)
      print_board(new_board)
      new_board
    end
  end

  def evaluate(board) do
    indexed_board = Enum.with_index(board, 0)
    filtered = Enum.filter(indexed_board, fn({value, _}) -> value != "X" && value != "O" end)
    #choose a space for next move
    available_moves = Enum.map(filtered, fn({value, index}) -> index end)
    get_best_move(available_moves, board)
  end

  def get_best_move([first|[]], board) do
    first
  end

  def get_best_move([first_index|rest], board) do
    cond do
      game_over(List.update_at(board, first_index, fn(x) -> "O" end)) -> first_index
      game_over(List.update_at(board, first_index, fn(x) -> "X" end)) -> first_index
      true -> get_best_move(rest, board)
    end
  end

  def tie(board) do
    free_spaces = Enum.any?(board, fn(x) -> x != "X" && x != "O" end)
    !free_spaces
  end

  # check for winning row
  def game_over(board) do
    Enum.at(board, 0) == Enum.at(board, 1) && Enum.at(board, 1) == Enum.at(board, 2) ||
    Enum.at(board, 3) == Enum.at(board, 4) && Enum.at(board, 4) == Enum.at(board, 5) ||
    Enum.at(board, 6) == Enum.at(board, 7) && Enum.at(board, 7) == Enum.at(board, 8) ||
    Enum.at(board, 0) == Enum.at(board, 3) && Enum.at(board, 3) == Enum.at(board, 6) ||
    Enum.at(board, 1) == Enum.at(board, 4) && Enum.at(board, 4) == Enum.at(board, 7) ||
    Enum.at(board, 2) == Enum.at(board, 5) && Enum.at(board, 5) == Enum.at(board, 8) ||
    Enum.at(board, 0) == Enum.at(board, 4) && Enum.at(board, 4) == Enum.at(board, 8) ||
    Enum.at(board, 2) == Enum.at(board, 4) && Enum.at(board, 4) == Enum.at(board, 6)
  end
end
