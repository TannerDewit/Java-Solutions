/* Problem: Interactive Tic-Tac-Toe
Link: https://open.kattis.com/problems/interactivetictactoe
Summary: Implement a bot that plays Tic-Tac-Toe interactively against an opponent. 
The program reads the current board state each turn and chooses a move based on a 
strategy: first attempt to win if possible, then block the opponent's immediate win, then attempt to set up to different possible three in a rows simultaneously,
or lastly pick the center, a corner, or a side if no other moves are available. 
The bot must handle starting first or second, as well as entering a game mid-progress. 
It outputs the updated board after each move.*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class interactivetictactoe {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int c[]=new int[2];
		String w;
		int x,y;
		char board[][] = new char[3][3];
		char board2[][] = new char[3][3];
		String order = s.nextLine().trim();
		for (int i = 0; i < 3; i++) {
			w = s.nextLine().trim();
			board[i] = w.toCharArray();
		}
		if (order.equals("first")) {
			board[1][1] = 'x';
			printBoard(board);
			board2=fill(s);
			c=opMove(board,board2);
			board[c[0]][c[1]] = 'o';
			if ((c[1] == 0 || c[1] == 2) && c[0] == 1) {
				board[0][0] = 'x';
				printBoard(board);
				board2=fill(s);
				c=opMove(board,board2);
				board[c[0]][c[1]] = 'o';
				if (c[0] != 2 || c[1] != 2) {
					board[2][2] = 'x';
					printBoard(board);
					return;
				}
				c=fork(board2);
			    board[c[0]][c[1]]='x';
			    printBoard(board);
			    board2=fill(s);
			    c=checkWin(board2);
			    board[c[0]][c[1]]='x';
			    printBoard(board);
			    return;
			} else if ((c[0] == 0 || c[0] == 2) && c[1] == 1) {
				board[0][0] = 'x';
				printBoard(board);
				board2=fill(s);
				c=opMove(board,board2);
				board[c[0]][c[1]] = 'o';
				if (c[0] != 2 || c[1] != 2) {
					board[2][2] = 'x';
					printBoard(board);
					return;
				}
				c=fork(board2);
			    board[c[0]][c[1]]='x';
			    printBoard(board);
			    board2=fill(s);
			    c=checkWin(board2);
			    board[c[0]][c[1]]='x';
			    printBoard(board);
			    return;
				
			} else {
				if (c[0] == c[1]) {
					if (c[0] == 2) {
						board[0][0] = 'x';
					} else {
						board[2][2] = 'x';
					}
				} else {
					board[c[1]][c[0]] = 'x';
				}
				printBoard(board);
				
			}
		}else if(order.equals("second")) {
			c=checkWin(board);
			if(c!=null) {
				 board[c[0]][c[1]]='x';
				    printBoard(board);
				    return;
			}
			c=blockFullBoard(board);
			if(c!=null) {
				 board[c[0]][c[1]]='x';
				    printBoard(board);
			}
			c=fork(board);
				if(c!=null) {
					 board[c[0]][c[1]]='x';
					    printBoard(board);
			}else {
				c=randomMove(board);
				if(c!=null) {board[c[0]][c[1]]='x';
			    printBoard(board);}
				else {
					return;
				}
			}
		}
		while (true) {
			board2=fill(s);
			c=opMove(board,board2);
			board[c[0]][c[1]] = 'o';
			x=c[0];
			y=c[1];
			c=checkWin(board2);
			if(c!=null) {
				 board[c[0]][c[1]]='x';
				    printBoard(board);
				    return;
			}
			c=block(board2,x,y);
			if(c!=null) {
				 board[c[0]][c[1]]='x';
				    printBoard(board);
				    continue;
			}
			c=fork(board2);
				if(c!=null) {
					 board[c[0]][c[1]]='x';
					    printBoard(board);
			}else {
				c=randomMove(board2);
				if(c!=null) {board[c[0]][c[1]]='x';
			    printBoard(board);}
				else {
					return;
				}
			}
			
			
		}

	}
	public static int[] randomMove(char[][] b) {
	    if (b[1][1] == '.') return new int[]{1, 1};

	  
	    if (b[0][0] == '.') return new int[]{0, 0};
	    if (b[0][2] == '.') return new int[]{0, 2};
	    if (b[2][0] == '.') return new int[]{2, 0};
	    if (b[2][2] == '.') return new int[]{2, 2};

	    
	    if (b[0][1] == '.') return new int[]{0, 1};
	    if (b[1][0] == '.') return new int[]{1, 0};
	    if (b[1][2] == '.') return new int[]{1, 2};
	    if (b[2][1] == '.') return new int[]{2, 1};

	   
	    return null;
	}

	public static char[][] fill(Scanner scnr) {
		char[][] b = new char[3][3];
		String w;
		for (int i = 0; i < 3; i++) {
			w = scnr.nextLine();
			for (int j = 0; j < 3; j++) {
				b[i] = w.toCharArray();
			}
		}
		return b;
	}
	public static int[] opMove(char[][] b1, char[][] b2) {
		int[] coord= new int[2];
		boolean done=false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (b2[i][j] - b1[i][j] != 0) {
				coord[0] = i;
				coord[1] = j;
				done=true;
				break;
				}
				
			}
			if(done) {break;}
		}
		return coord;
	}
public static int[] checkWin(char[][] b) {

    for (int i = 0; i < 3; i++) {
        int count = 0, emptyCol = -1;
        for (int j = 0; j < 3; j++) {
            if (b[i][j] == 'x') count++;
            else if (b[i][j] == '.') emptyCol = j;
        }
        if (count == 2 && emptyCol != -1) return new int[]{i, emptyCol};
    }
    for (int j = 0; j < 3; j++) {
        int count = 0, emptyRow = -1;
        for (int i = 0; i < 3; i++) {
            if (b[i][j] == 'x') count++;
            else if (b[i][j] == '.') emptyRow = i;
        }
        if (count == 2 && emptyRow != -1) return new int[]{emptyRow, j};
    }
    int count = 0, emptyX = -1, emptyY = -1;
    for (int i = 0; i < 3; i++) {
        if (b[i][i] == 'x') count++;
        else if (b[i][i] == '.') { emptyX = i; emptyY = i; }
    }
    if (count == 2 && emptyX != -1) return new int[]{emptyX, emptyY};
    count = 0; emptyX = -1; emptyY = -1;
    for (int i = 0; i < 3; i++) {
        if (b[i][2-i] == 'x') count++;
        else if (b[i][2-i] == '.') { emptyX = i; emptyY = 2-i; }
    }
    if (count == 2 && emptyX != -1) return new int[]{emptyX, emptyY};

    return null; 
}
public static int[] block(char[][] b, int x, int y) {

    
    int[][] linesToCheck;

    if (x == 1 && y == 1) {

        linesToCheck = new int[][] {
            {1,0, 1,1, 1,2},
            {0,1, 1,1, 2,1}, 
            {0,0, 1,1, 2,2},
            {0,2, 1,1, 2,0}  
        };
    } else if ((x == 0 && y == 1) || (x == 1 && y == 0) || (x == 1 && y == 2) || (x == 2 && y == 1)) {
      
        linesToCheck = new int[2][6];

        linesToCheck[0] = new int[]{x,0, x,1, x,2};
 
        linesToCheck[1] = new int[]{0,y, 1,y, 2,y};
    } else {
 
        if ((x == 0 && y == 0) || (x == 2 && y == 2)) {
        
            linesToCheck = new int[][] {
                {x,0, x,1, x,2},
                {0,y, 1,y, 2,y},
                {0,0, 1,1, 2,2}  
            };
        } else if ((x == 0 && y == 2) || (x == 2 && y == 0)) {
    
            linesToCheck = new int[][] {
                {x,0, x,1, x,2},
                {0,y, 1,y, 2,y}, 
                {0,2, 1,1, 2,0} 
            };
        } else {
            return null;
        }
    }


    for (int[] line : linesToCheck) {
        int count = 0;
        int emptyX = -1, emptyY = -1;
        for (int i = 0; i < line.length; i += 2) {
            int r = line[i], c = line[i+1];
            if (b[r][c] == 'o') count++;
            else if (b[r][c] == '.') { 
            emptyX = r; 
            emptyY = c;
 }
        }
        if (count == 2 && emptyX != -1) {
            return new int[]{emptyX, emptyY};
        }
    }

    return null;
}
public static int[] blockFullBoard(char[][] b) {


    for (int i = 0; i < 3; i++) {
        int count = 0, emptyCol = -1;
        for (int j = 0; j < 3; j++) {
            if (b[i][j] == 'o') count++;
            else if (b[i][j] == '.') emptyCol = j;
        }
        if (count == 2 && emptyCol != -1) return new int[]{i, emptyCol};
    }

    for (int j = 0; j < 3; j++) {
        int count = 0, emptyRow = -1;
        for (int i = 0; i < 3; i++) {
            if (b[i][j] == 'o') count++;
            else if (b[i][j] == '.') emptyRow = i;
        }
        if (count == 2 && emptyRow != -1) return new int[]{emptyRow, j};
    }

    int countDiag = 0, emptyX = -1;
    for (int i = 0; i < 3; i++) {
        if (b[i][i] == 'o') countDiag++;
        else if (b[i][i] == '.') emptyX = i;
    }
    if (countDiag == 2 && emptyX != -1) return new int[]{emptyX, emptyX};

    countDiag = 0; emptyX = -1;
    for (int i = 0; i < 3; i++) {
        if (b[i][2-i] == 'o') countDiag++;
        else if (b[i][2-i] == '.') emptyX = i;
    }
    if (countDiag == 2 && emptyX != -1) return new int[]{emptyX, 2 - emptyX};

    return null; 
}
public static int[] fork(char[][] b) { if (b[1][1] != 'x') return null; 

if (b[0][0] == 'x' && b[2][2] == 'o') {
    if (b[2][0] == '.' && b[1][0] == '.' && b[0][2] == '.') {
        return new int[]{2, 0};
    }
    if (b[0][2] == '.' && b[0][1] == '.' && b[2][0] == '.') {
        return new int[]{0, 2};
    }
}

if (b[2][2] == 'x' && b[0][0] == 'o') {
    if (b[2][0] == '.' && b[2][1] == '.' && b[0][2] == '.') {
        return new int[]{2, 0};
    }
    if (b[0][2] == '.' && b[1][2] == '.' && b[2][0] == '.') {
        return new int[]{0, 2};
    }
}

if (b[0][2] == 'x' && b[2][0] == 'o') {
    if (b[0][0] == '.' && b[0][1] == '.' && b[2][2] == '.') {
        return new int[]{0, 0};
    }
    if (b[2][2] == '.' && b[1][2] == '.' && b[0][0] == '.') {
        return new int[]{2, 2};
    }
}
if (b[2][0] == 'x' && b[0][2] == 'o') {
    if (b[0][0] == '.' && b[1][0] == '.' && b[2][2] == '.') {
        return new int[]{0, 0};
    }
    if (b[2][2] == '.' && b[2][1] == '.' && b[0][0] == '.') {
        return new int[]{2, 2};
    }
}

return null;
}
	public static void printBoard(char[][] b) {
		for (int i = 0; i < 3; i++) {
			System.out.println(new String(b[i]));
		}
	}
}
