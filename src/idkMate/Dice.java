package idkMate;

/*
 * This class is responsible for the generation of random numbers for the dice roll.
 * It returns a Triplet, which contains the result of the roll of dice 1 and dice 2.
 * It also includes a boolean which indicates whether the dice roll resulted in a double. 
 * A double means that the number of possible player moves during a turn goes from 2 to 4. 
 * The results of this class should be used during the development of the /move function
 * and its valid move checking method. 
 */

public class Dice {
	final static int SIDES = 6;
	static Integer die1, die2;
	static boolean doubleFlag = false;
	
	public Triplet<Integer, Integer, Boolean> rollDice () {
		die1 = new Integer((int) ((Math.random() * 6) + 1));
		die2 = new Integer((int) ((Math.random() * 6) + 1));
		
		if (die1.equals(die2)) doubleFlag = true;
		else doubleFlag = false;
		
		return new Triplet<>(die1,die2,doubleFlag);
	}
}

/* 
 * in the future, I will add a method which parses the result of the roll and displays
 * the dice faces on the diceInfo panel. 
 */