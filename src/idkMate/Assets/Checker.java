package idkMate.Assets;

import java.util.ArrayList;
import java.util.Stack;

import idkMate.Assets.Point;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class Checker {
	
	/**
	 * The point the checker is on
	 */
	public Point point;
	
	/**
	 * The color of the checker
	 */
	public String color;
	
	/**
	 * The checkers graphic component (i.e a circle)
	 */
	public Circle drawingObject;
	
	/**
	 * Check if the checker is bared i.e placed on the bar for additional actions
	 */
	public boolean bared;
	
	/**
	 * Check if the checker is beared
	 */
	public boolean beared;
	
	public int index;
	
	public Checker(int index, Point point, String color, boolean bared)
	{
		this.index = index;
		this.point = point;
		this.color = color;
		this.bared = bared;
		
		// Create the circular object representing the checker
		this.drawingObject = new Circle();
		this.drawingObject.setRadius(23.0f);
		
		// If the checker is white, change the color to white. (The default color is black)
		if(this.color == "white") {
			this.drawingObject.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		// If the checker if bared, do the following actions
		if(this.bared) {
			
		}
		else {
			// Draw the checker on the point it was created on if it is not bared
			this.drawOnPoint();
		}
	}

	
	/**
	 * Draws the checker onto the point instance it was created within
	 * Stacks the checkers if the point is full (has 6 or more checkers on it already)
	 */
	public void drawOnPoint()
	{
		//if(this.point.getNumber() == 1)
			//System.out.println(this.point.checkers.size());
		
		/* If the point has 6 or more checkers,
		 * this is where the functionality to stack the checkers must go
		*/
		if(this.point.checkers.size() + 1 > 5) {
			
			int checkNum = this.point.checkers.size() + 1;
			int posNum = checkNum - 5;
			
			this.drawingObject.setStyle("-fx-stroke: #ccc;" + 
					"-fx-stroke-width: 1;");
			if(posNum == 1) {
				this.drawingObject.setTranslateX(0);
				this.drawingObject.setTranslateY(-100);
				this.point.body.getChildren().add(this.drawingObject);
			}
			
			if(posNum == 2) {
				this.drawingObject.setTranslateX(0);
				this.drawingObject.setTranslateY(-250);
				this.point.body.getChildren().add(this.drawingObject);
			}
			
			if(posNum == 3) {
				this.drawingObject.setTranslateX(0);
				this.drawingObject.setTranslateY(-400);
				this.point.body.getChildren().add(this.drawingObject);
			}
			
			if(posNum == 4) {
				this.drawingObject.setTranslateX(0);
				this.drawingObject.setTranslateY(-550);
				this.point.body.getChildren().add(this.drawingObject);
			}

		} 
		else {
			this.point.body.getChildren().add(drawingObject);
		}
	}

}
