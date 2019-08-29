package idkMate.Assets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import Views.BoardView;
import idkMate.Backgammon;
import idkMate.Player;
import javafx.util.Pair;


/*
 * This class is instantiated every time a player gets their turn.
 * It contains all the possible and valid moves they can perform based
 * on the state of the board and their roll. 
 * 
 */

public class Moves {
	
	public final static int SIZE_OF_BOARD = 24;
	public final static int WHITE_BEAR_OFF = 25;
	public final static int BLACK_BEAR_OFF = 26;
	//Number of moves a player has left, based on what they roll.
	private int playerMovesLeft = 0;
	
	private class Move {
		int id;
		Point from, to;
		boolean hit;
		//Shows how many available player moves this move will exhaust
		int weight;
		boolean bear;
		boolean bar;
		
		Move (int id, Point from, Point to, boolean hit, int weight, boolean bear, boolean bar) {
			this.id = id;
			this.from = from;
			this.to = to;
			this.hit = hit;
			this.weight = weight;
			this.bear = bear;
			this.bar = bar;
		}
		
		public boolean getBear() {
			return this.bear;
		}
	}
	
	LinkedList<Move> moves;
	
	public Moves () {
		moves = new LinkedList<Move>();
		checkBearOff();
		getAllMoves();
		refineMoves();
		restrictMoves();		
		setMovesLeft();
	}
	
	/**
	 * Need to shorten this function by breaking it up into smaller functions 
	 */
	
	public LinkedList<Move> getAllMoves() {
		
		ArrayList<Point> board = BoardView.points;
		Player currentTurn = Backgammon.getCurrentGame().getCurrentTurn();
		int moveCounter = 1;
		int roll1 = Backgammon.getCurrentGame().getCurrentTurn().getRoll().getRoll1();
		int roll2 = Backgammon.getCurrentGame().getCurrentTurn().getRoll().getRoll2();
		boolean doubleFlag = Backgammon.getCurrentGame().getCurrentTurn().getRoll().getDoubleFlag();
		
		//Gets all the possible moves (regardless of opponent checkers) currnet player can make
		if (currentTurn.getNumber() == 1) {
			for (int i = SIZE_OF_BOARD; i >= 0; i--) {
				if (board.get(i).checkStatus().toString() == "white") {
					if (i - roll1 >= 0)
						moves.add(new Move(moveCounter++,board.get(i),board.get(i - roll1),false,1,false,false));
					if (i - roll2 >= 0)
						moves.add(new Move(moveCounter++,board.get(i),board.get(i - roll2),false,1,false, false));
					if (i - roll1 - roll2 >= 0)
						moves.add(new Move(moveCounter++,board.get(i),board.get(i - roll1 - roll2),false,2,false, false));
					
					//Check for valid double rolls, as roll1 = roll2 we can only use roll1 for comparison
					if (doubleFlag == true) {
						if (i - roll1 * 3 >= 0)
							moves.add(new Move(moveCounter++,board.get(i),board.get(i - (roll1 * 3)),false,3,false, false));
						if (i - roll1 * 4 >= 0)
							moves.add(new Move(moveCounter++,board.get(i),board.get(i - (roll1 * 4)),false,4,false, false));
					}					
					
				}
			}
			
			/*
			 * Get possible bear off moves with the player's roll. e.g if one of the rolls is 6, and
			 * there are no checkers on position 6, using while loop to find all positions lower which
			 * have checkers ready for bearing off. 
			 */
			if (currentTurn.getBearOffFlag() == true) {
				int counter = roll1;
				if (!board.get(roll1).isEmpty())
					moves.add(new Move(moveCounter++,board.get(roll1),board.get(0),false,1,true, false));
				while (counter > 0) {
					if (board.get(counter).isEmpty()) continue;
					else moves.add(new Move(moveCounter++,board.get(counter),board.get(0),false,1,true, false));
					counter--;
				}			
				
				counter = roll2;
				if (!board.get(roll2).isEmpty())
					moves.add(new Move(moveCounter++,board.get(roll2),board.get(0),false,1,true, false));	
				while (counter > 0) {
					if (board.get(counter).isEmpty()) continue;
					else moves.add(new Move(moveCounter++,board.get(counter),board.get(0),false,1,true, false));
					counter--;
				}
				
			}
			
			if(currentTurn.getBarOffFlag() == true) {
				for(int i = 1; i<7;i++) {
					if(board.get(i).checkStatus().toString() == "white") {
						
					}
				}
			}
		}
		
		//For black's turns, the position of the moves are stored with the coordinate system
		//which starts at the bottom right and ends top right but the moves displayed are 
		//altered to the changed perspective 
		if (currentTurn.getNumber() == 2) {
			for (int i = 0; i <= SIZE_OF_BOARD; i++) {
				if (board.get(i).checkStatus().toString() == "black") {
					if (i + roll1 <= SIZE_OF_BOARD)
						moves.add(new Move(moveCounter++,board.get(i),board.get(i + roll1),false,1,false, false));
					if (i + roll2 <= SIZE_OF_BOARD)
						moves.add(new Move(moveCounter++,board.get(i),board.get(i + roll2),false,1,false, false));
					if (i + roll1 + roll2 <= SIZE_OF_BOARD)
						moves.add(new Move(moveCounter++,board.get(i),board.get(i + roll1 + roll2),false,2,false, false));
					
					//Check for valid double rolls, as roll1 = roll2 we can only use roll1 for comparison
					if (doubleFlag == true) {
						if (i + (roll1 * 3) <= SIZE_OF_BOARD)
							moves.add(new Move(moveCounter++,board.get(i),board.get(i + (roll1 * 3)),false,3,false, false));
						if (i + (roll1 * 4) <= SIZE_OF_BOARD)
							moves.add(new Move(moveCounter++,board.get(i),board.get(i + (roll1 * 4)),false,4,false, false));
					}
				}
			}
			
			/*
			 * Similar to Player 1 function but with minor tweak. 
			 */
			if (currentTurn.getBearOffFlag() == true) {
				int counter = 25 - roll1;
				if (!board.get(counter).isEmpty())
					moves.add(new Move(moveCounter++,board.get(counter),board.get(0),false,1,true, false));
				while (counter < 25) {
					if (board.get(counter).isEmpty()) continue;
					else moves.add(new Move(moveCounter++,board.get(counter),board.get(0),false,1,true, false));
					counter++;
				}			
				
				counter = 25 - roll2;
				if (!board.get(counter).isEmpty())
					moves.add(new Move(moveCounter++,board.get(counter),board.get(0),false,1,true, false));	
				while (counter < 25) {
					if (board.get(counter).isEmpty()) continue;
					else moves.add(new Move(moveCounter++,board.get(counter),board.get(0),false,1,true, false));
					counter++;
				}
				
			}
			if(currentTurn.getBarOffFlag() == true) {
				for(int i = 18; i<25;i++) {
					if(board.get(i).checkStatus().toString() == "black") {
						
					}
				}
			}
		}	
		
		return moves;
	}
	
	//This method removes moves which cannot be used and updates the hit value of a move if the move will hit an opposing checker
	public LinkedList<Move> refineMoves() {
		
		Backgammon.getCurrentGame().getBoard().getDoublingCube().show();
		
		int trace_curr = 0;
		
		//Used to eliminate duplicate bear off moves -> change to map in future
		boolean [] bearOffDupes = new boolean [6];
		for (int i = 0; i < 6; i++) 
			bearOffDupes[i] = false;
		String oppPlayer = Backgammon.getCurrentGame().oppPlayer().getColor();
		String oppHit = (oppPlayer == "black") ? "bhit" : "whit";
				
		Iterator<Move> listIter = moves.iterator();
		
		while (listIter.hasNext()) {
			Move m = listIter.next();
			Point to = m.to;
			
			if (to.checkStatus().toString() == oppPlayer) 
				listIter.remove();
			if (to.checkStatus().toString() == oppHit)
				m.hit = true;
			//Eliminates duplicate bear off moves
			
			// Prevent out of bounds exception
			try {
				
				if (m.getBear() == true) {
					int curr = m.from.getNumber();
					
					// Debug purposes
					trace_curr = curr;
					
					if (Backgammon.getCurrentGame().getCurrentTurn().getNumber() == 2)
						curr = 25 - curr;
					if (bearOffDupes[curr] == true) {
						System.out.println("curr: " + curr);
						listIter.remove();
					}
					else 
						bearOffDupes[curr] = true;
				}
				
			} catch(Exception e) {
				System.out.println("Caught out of bounds exception for bearOffDupes[" + trace_curr + "]");
				//e.printStackTrace();
			}
		}
		
		if (moves.isEmpty())
			System.out.println("No moves");
		
		return moves;
	}
	
	/*E.g if the rolls are 5 and 6 and player makes a move using roll 6, then the 
	 * moves which require roll 6 AND a double move are removed.
	 */
	public LinkedList<Move> restrictMoves() {
		int rMove = Backgammon.getCurrentGame().restrictedMove;
		
		Iterator<Move> listIter = moves.iterator();
		
		while (listIter.hasNext()) {
			Move m = listIter.next();
			
			//remove moves which cannot be performed as user already used up the move
			if (Math.abs(m.from.getNumber() - m.to.getNumber()) == rMove)
				listIter.remove();
			//assume that double moves must also be removed
			if (m.weight == 4)
				listIter.remove();
			if (m.to.getNumber() == 0)
				listIter.remove();
		}
		
		return moves;
	}
	
	
	//Returns a string with the list of valid moves
	public String displayValidMoves() {
		Player currentTurn = Backgammon.getCurrentGame().getCurrentTurn();
		String op = new String();
		
		for (Move m: moves) {
			
			Move temp = m;
			int from = (int)temp.from.getNumber();
			int to = (int)temp.to.getNumber();
			
			if (currentTurn.getNumber() == 2) {
				from = 25 - from;
				to = 25 - to;
			}
			
			op += "[" + temp.id + "] " + from + "-" 
						+ (temp.bear == true ? "bear" : to) + (temp.hit ? "*" : "") +  "\n"; 
		}
		return op;
	}
	
	//Determines how many moves a player gets, based on if a roll is a double or not 
	public void setMovesLeft() {
		boolean doubleFlag = Backgammon.getCurrentGame().getCurrentTurn().getRoll().getDoubleFlag();
		if (doubleFlag == false)
			this.playerMovesLeft = 2;
		else
			this.playerMovesLeft = 4;		
	}
	
	public int getMovesLeft() {
		return this.playerMovesLeft;
	}
	
	//Returns the to, from and weight value of a move based on its ID.
	public int [] getMove(int i) {
		int [] move = {0,0,0,0};
		for (Move m : moves) {
			if (m.id == i) {
				move[0] = m.from.getNumber();
				move[1] = m.to.getNumber();
				move[2] = m.weight;
				if (m.bear == true)
					move[3] = 1;
				else if (m.bear == false)
					move[3] = 0;
			}
		}		
		return move;
	}
	
	//Sets bear off flag of Player to true if the player can bear off
	public void checkBearOff() {
		Player currTurn = Backgammon.getCurrentGame().getCurrentTurn();
		ArrayList<Point> board = BoardView.points;
		int counter = 0;
		if ("white" == currTurn.getColor()) {
			for (int i = 19; i <= 24; i++) {
				counter += board.get(i).size();
			}
		} else if ("black" == currTurn.getColor()) {
			for (int i = 1; i <= 6; i++) {
				counter += board.get(i).size();
			}
		}

		if (counter == 15) {
			currTurn.setBearOffFlag(true);
		}
	}
	
}

