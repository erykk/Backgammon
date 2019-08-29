package idkMate.Assets;

import java.util.Stack;

import idkMate.Assets.Checker;
import javafx.scene.layout.VBox;

public class Bar {

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
	public Bar(VBox body) {
		this.body = body;
		this.checkers = new Stack<Checker>();
	}
	
	/**
	 * Bar a pip/checker from a specific point
	 * @param p
	 */
	public void from(Point p)
	{
		Checker c = p.pop();
		
		c.bared = true;
		
		c.drawingObject.setStyle("-fx-stroke: #ccc;" + 
				"-fx-stroke-width: 1;");
		
		this.body.getChildren().add(c.drawingObject);
	}

	/**
	 * Remove an element from the bar and put it back on the board
	 */
	public void pop()
	{
		Checker c = this.checkers.pop();
		c.bared = false;
		
		this.body.getChildren().remove(c.drawingObject);
		///c.drawOnPoint();
	}
	
	/**
	 * Check the checkers on the bar
	 * @return
	 */
	public Stack<Checker> getCheckers() {
		return checkers;
	}
	
	public boolean isEmpty() {
		if(getCheckers() == null) {
			return true;
		}
		else {
			return false;
		}
	}
	public void clear() {
		while (!checkers.isEmpty()) 
			this.checkers.pop();
		this.body.getChildren().clear();
	}

}
