package idkMate.Assets;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Views.BoardView;
import idkMate.Backgammon;
import idkMate.Player;
import idkMate.Assets.Checker;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Point {

	
	public VBox body;
	private int number;
	private int size;
	private PointStatus status = PointStatus.neutral;
	
	public Stack<Checker> checkers;
	
	/**
	 * Creates a point with a VBox to act as a vertical container for the pips/checkers
	 * @param body
	 * @param number
	 */
	public Point(VBox body, int number) 
	{
		
		this.body = body;
		this.number = number;
		this.checkers = new Stack<Checker>();
		this.size = 0;

	}
	
	/**
	 * Hit a point
	 */
	public void hit()
	{
		if(this.ownedBy() == null)
			return;
		if(this.ownedBy().getColor() == null)
			return;
		
		if(this.ownedBy().getColor() == "black") {
			Backgammon.getCurrentGame().getBoard().getBlackBar().from(this);
		}
		else
		if(this.ownedBy().getColor() == "white") {
			Backgammon.getCurrentGame().getBoard().getWhiteBar().from(this);
		}
	}
	
	/**
	 * Bar one pip/checker from this point
	 */
	public void bar(String toColor)
	{
		if(toColor == "black") {
			Backgammon.getCurrentGame().getBoard().getBlackBar().from(this);
		}
		else
		if(toColor == "white") {
			Backgammon.getCurrentGame().getBoard().getWhiteBar().from(this);
		}
	}
	
	/**
	 * Bear one pip/checker from this point
	 * @param p
	 */
	public void bear()
	{
		if(this.ownedBy() == null)
			return;
		if(this.ownedBy().getColor() == null)
			return;
		if(this.ownedBy().getColor() == "black") {
			Backgammon.getCurrentGame().getBoard().getBlackBear().from(this);
		}
		else
		if(this.ownedBy().getColor() == "white") {
			Backgammon.getCurrentGame().getBoard().getWhiteBear().from(this);
		}	
			
	}
	
	
	public void moveOneTo(Point p)
	{
		Checker c = this.pop();
		
		//System.out.println("From: " + this.checkStatus().toString());
		//System.out.println("To: " + p.checkStatus().toString());

		// Check if move is a hit, if so execute the hit
		if(p.checkStatus() == PointStatus.whit && c.color == "black") {
			p.bar("white");
		}
		else
		if(p.checkStatus() == PointStatus.bhit && c.color == "white") {
			p.bar("black");
		}
		
		p.push(c.color);
	}
	
	public static Point getPoint(int point)
	{
		
		if(Backgammon.getCurrentGame().getCurrentTurn().getColor() == "black") {
			
			point = point - 24 - 1;
			point = Math.abs(point);

		}
		
		for(Point p : BoardView.points) 
		{
			if(p.number == point)
				return p;
		}
		
		return null;
	}
	
	
	/**
	 * Push a new pip/checker onto this point
	 * @param color
	 */
	public void push(String color)
	{
		//System.out.println("Pushing " + color + " to checkr " + this.number);
		this.checkers.push(new Checker(this.checkers.size(), this, color, false));
		size++;
	}

	/**
	 * Remove the top most pip/checker from this point
	 * @return
	 */
	public Checker pop()
	{
	//	System.out.println("Removing checker from point " + this.number);
		Checker c = this.checkers.pop();
		
		this.body.getChildren().remove(c.drawingObject);
		
		size--;
		
		return c;
	}
	
	public Checker peek() {
		Checker c = this.checkers.peek();
		
		return c;
	}
	
	/**
	 * Get the checkers on this point
	 * @return
	 */
	public Stack<Checker> getCheckers() {
		return checkers;
	}

	/**
	 * Get which player this point is owned by
	 * @return
	 */
	public Player ownedBy()
	{
		Player ownedBy = null;
		
		if (this.isEmpty())
			ownedBy = null;
		if (this.size() == 1) {
			if (this.checkers.peek().color == "white")
				ownedBy = Backgammon.getCurrentGame().player1;
			if (this.checkers.peek().color == "black")
				ownedBy = Backgammon.getCurrentGame().player2;
		}
		if (this.size() >= 2 && this.checkers.peek().color == "white")
			ownedBy = Backgammon.getCurrentGame().player1;
		if (this.size() >= 2 && this.checkers.peek().color == "black")
			ownedBy = Backgammon.getCurrentGame().player2;
		
		return ownedBy;
	}
	
	/**
	 * Check which color (pointstatus) this point is owned by
	 * @return
	 */
	public PointStatus checkStatus() {
		PointStatus returnStatus = PointStatus.neutral;
		
		if (this.isEmpty())
			returnStatus = PointStatus.neutral;
		if (this.size() == 1) {
			if (this.checkers.peek().color == "white")
				returnStatus = PointStatus.whit;
			if (this.checkers.peek().color == "black")
				returnStatus = PointStatus.bhit;
		}
		if (this.size() >= 2 && this.checkers.peek().color == "white")
			returnStatus = PointStatus.white;
		if (this.size() >= 2 && this.checkers.peek().color == "black")
			returnStatus = PointStatus.black;
		
		return returnStatus;
	}
	
	/**
	 * Get the point number
	 * @return
	 */
	public int getNumber() {
		return number;
	}
	
	public int size () {
		return this.size;
	}

	public boolean isEmpty() {
		return (this.size == 0);
	}
}
