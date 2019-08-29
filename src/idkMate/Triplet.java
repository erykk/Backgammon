package idkMate;

/**
 * Utility class for the sole purpose of parsing the result of the Dialog
 * which starts off the game, and also for parsing the results of a dice roll
 *  */

public class Triplet<A,B,C> {
	A v1;
	B v2;
	C v3;
	
	public Triplet (A v1, B v2, C v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 =  v3;
	}
	
	public A getPlayer1() { return this.v1;}
	public B getPlayer2() { return this.v2;}
	public C getMaxScore() {return this.v3;}
	
	public A getRoll1() { return this.v1; }
	public B getRoll2() { return this.v2; }
	public C getDoubleFlag() { return this.v3; }
	
	public void setRoll1(A v1) {  this.v1 = v1; }
	public void setRoll2(B v2) {  this.v2 = v2; }
	public void setDoubleFlag(C v3) { this.v3 = v3; }
}
