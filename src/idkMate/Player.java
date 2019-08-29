package idkMate;

import javafx.scene.control.Label;

/**
 * This is the uppermost panel which displays the current state for each player. Player1/2 will
 * be replaces with names. Main goal is to display the current score. Down the line, we will include
 * the option to turn off and on the numbers the pips, the last move the player made and the number of 
 * checkers left
 *  */
public class Player {
	
	private int number;
	private String name;
	private Integer currentPoints;
	private Integer maxPoints;
	private String color;
	private Triplet<Integer, Integer, Boolean> roll;
	private int bearedOffCheckers;
	private boolean bearOffFlag;
	private boolean doubleCubeFlag;
	
	public Label playerLabel;
	
	public Player (String name, Integer maxPoints) {
		
		if(this.maxPoints == null || this.maxPoints == 0) {
			this.maxPoints = 5;
		}
		
		this.name = name;
		this.currentPoints = 0;
		this.maxPoints = maxPoints;
		this.bearOffFlag = false;
		this.bearedOffCheckers = 0;
	}
	
	public String getSummary()
	{
		return "Player " + this.number + " [" + this.name + "]";
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCurrentPoints() {
		return this.currentPoints;
	}
	
	public int getMaxPoints() {
		return this.maxPoints;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setRoll(Triplet<Integer, Integer, Boolean> roll)
	{
		this.roll = roll;
	}
	
	public Triplet<Integer, Integer, Boolean> getRoll()
	{
		return this.roll;
	}
	
	public int getBearOffCheckers() {
		return this.bearedOffCheckers;
	}

	public void addBearOffCheckers() {
		this.bearedOffCheckers++;
	}

	public boolean getBearOffFlag() {
		return this.bearOffFlag;
	}

	public void setBearOffFlag (boolean flag) {
		this.bearOffFlag = flag;
	}
	
	public boolean getBarOffFlag() {
		return this.bearOffFlag;
	}

	public void setBarOffFlag (boolean flag) {
		this.bearOffFlag = flag;
	}
	
	public void setCurrentPoints (int points) {
		this.currentPoints = points;
	}
	public  boolean getDoubleCubeFlag() {
		return this.doubleCubeFlag;
	}
	public void setDoubleCubeFlag(boolean flag) {
		
		if(flag == true) {
			
		} else {
			Backgammon.getCurrentGame().getBoard().getWhiteDoublingCube().hide();
			Backgammon.getCurrentGame().getBoard().getBlackDoublingCube().hide();
			Backgammon.getCurrentGame().getBoard().getDoublingCube().hide();
		}
		
		this.doubleCubeFlag = flag;
	}
}
