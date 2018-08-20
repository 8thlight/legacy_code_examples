patterns_1= [[(/ OO....../),0],[(/O..O.. ../),6], [(/......OO /),8],[(/.. ..O..O/),2], [(/ ..O..O../),0],[(/...... OO/),6], [(/..O..O.. /),8],[(/OO ....../),2], [(/ ...O...O/),0],[(/..O.O. ../),6], [(/O...O... /),8],[(/.. .O.O../),2], [(/O O....../),1],[(/O.. ..O../),3], [(/......O O/),7],[(/..O.. ..O/),5], [(/. ..O..O./),1],[(/... OO.../),3], [(/.O..O.. ./),7],[(/...OO .../),5]]
patterns_2= [[(/  X . X  /),1],[(/ XX....../),0],[(/X..X.. ../),6], [(/......XX /),8],[(/.. ..X..X/),2],[(/ ..X..X../),0], [(/...... XX/),6],[(/..X..X.. /),8],[(/XX ....../),2], [(/ ...X...X/),0],[(/..X.X. ../),6],[(/X...X... /),8], [(/.. .X.X../),2],[(/X X....../),1],[(/X.. ..X../),3], [(/......X X/),7],[(/..X.. ..X/),5],[(/. ..X..X./),1], [(/... XX.../),3],[(/.X..X.. ./),7],[(/...XX .../),5], [(/ X X.. ../),0],[(/ ..X.. X /),6],[(/.. ..X X /),8], [(/ X ..X.. /),2],[(/  XX.. ../),0],[(/X.. .. X /),6], [(/.. .XX   /),8],[(/X  ..X.. /),2],[(/ X  ..X../),0], [(/ ..X..  X/),6],[(/..X..  X /),8],[(/X  ..X.. /),2]]
patterns_3= [[(/OOO....../),'O'], [(/...OOO.../),'O'], [(/......OOO/),'O'], [(/O..O..O../),'O'], [(/.O..O..O./),'O'], [(/..O..O..O/),'O'], [(/O...O...O/),'O'], [(/..O.O.O../),'O'], [(/XXX....../),'X'], [(/...XXX.../),'X'], [(/......XXX/),'X'], [(/X..X..X../),'X'], [(/.X..X..X./),'X'], [(/..X..X..X/),'X'], [(/X...X...X/),'X'], [(/..X.X.X../),'X']]
board= [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '];X= 'X';O= 'O';players= [X, O];curr_turn= X

comp= function(){
  x= get_pattern_1_move()
    if(x==-1){
      x= get_pattern_2_move()
        if(x==-1){x= get_move()}
    }
  move(x,O)
}
move= function(pos,x){
  if(x!=curr_turn){return false}
  if(+pos>=0&&+pos<=8&&!isNaN(+pos)&&board[+pos]==' '){
    board.splice(+pos,1,x)
      curr_turn= (x==X)? O: X
      return true
  }
  return false
}
board_display= function(){return ' '+board[0]+' |'+' '+board[1]+' |'+' '+board[2]+'\n===+===+===\n'+' '+board[3]+' |'+' '+board[4]+' |'+' '+board[5]+'\n===+===+===\n'+' '+board[6]+' |'+' '+board[7]+' |'+' '+board[8]}
show= function(){console.log(board_display())}
board_filled= function(){
  x = get_move()
    if(x==-1){
      show()
        console.log('Game over')
        return true
    }
  return false
}
winner= function(){
  board_string= board.join('')
    the_winner= null
    for(i=0;i<patterns_3.length;i++){
      array= board_string.match(patterns_3[i][0])
        if(array){the_winner= patterns_3[i][1]}
    }
  if(the_winner){
    show()
      console.log('Game over')
      return true
  }
  return false
}
get_pattern_1_move= function(){
  board_string= board.join('')
    for(i=0;i<patterns_1.length;i++){
      array= board_string.match(patterns_1[i][0])
        if(array){return patterns_1[i][1]}
    }
  return -1
}
get_pattern_2_move= function(){
  board_string= board.join('')
    for(i=0;i<patterns_2.length;i++){
      array= board_string.match(patterns_2[i][0])
        if(array){return patterns_2[i][1]}
    }
  return -1
}
get_move= function(){
  if(board[4] == ' '){return 4}
  return board.indexOf(' ')
}
exit= function(){process.exit()}
play= function(){
  show()
    console.log("Enter [0-8]:")
    process.openStdin().on('data',function(res){
      if(move(res, X)){
        if(winner()||board_filled()) {exit()} else {
          comp()
      if (winner()||board_filled()) {exit()} else {show()}
        }
      }
    })
}

play()
