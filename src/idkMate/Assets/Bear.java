package idkMate.Assets;

import java.util.Stack;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class Bear {

	
	/**
	 * Number of checkers in the bear 
	 */
	
	private int size;
	
	/**
	 * The vertical container for the bar 
	 */
	private VBox body;
	
	/**
	 * The stack containing the checkers in the bar
	 */
	private Stack<Checker> checkers;
	
	/**
	 * Create the bar
	 * @param body
	 */	
	
	public Bear(VBox body) {
		this.body = body;
		this.checkers = new Stack<Checker>();
		this.size = 0;
	}
	
	/**
	 * Bar a pip/checker from a specific point
	 * @param p
	 */
	public void from(Point p)
	{
		Checker c = p.pop();
		
		this.body.getChildren().add(this.generateBearObject(c));
		this.size++;
	}
	
	public Rectangle generateBearObject(Checker c)
	{
		Rectangle r = new Rectangle();
		r.setX(50);
		r.setY(50);
		r.setWidth(50);
		r.setHeight(20);
		r.setArcHeight(15);
		r.setArcWidth(15);
		
		if(c.color == "white") {
			r.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		return r;
	}

	
	/**
	 * Check the checkers on the bar
	 * @return
	 */
	public Stack<Checker> getCheckers() {
		return checkers;
	}
	
	public int size() {
		return this.size;
	}
	
	public void clear() {
		while (!checkers.isEmpty()) 
			this.checkers.pop();
		this.body.getChildren().clear();
	}
}
