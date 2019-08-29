package Views;

import java.util.ArrayList;

import idkMate.Backgammon;
import idkMate.Assets.Bar;
import idkMate.Assets.Bear;
import idkMate.Assets.DoublingCube;
import idkMate.Assets.Point;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;
import java.lang.Math;

/** 
 * This is the class for the board view. We will implement the board using
 * an array. In each element of the array will be a stack, which represents the current
 * checkers on that position on the board. Also, we need to represent this information
 * visually, so we need to create some sort of grid which will overlay the board and
 * convey the information from the main array+stacks as checkers on the board. 
 * 
 * Daniel this is your part. 
 * */

public class BoardView {
	
	
	/**
	 * Holds a list of the points from 1-24
	 */
	public static ArrayList<Point> points;
	
	/**
	 * Stores the location & information of the bar
	 */
	private Bar blackBar;
	
	/**
	 * Stores the location & information of the bar
	 */
	private Bar whiteBar;
	
	/**
	 * Stores the location & information of the bar
	 */
	private Bear blackBear;
	
	/**
	 * Stores the location & information of the bar
	 */
	private Bear whiteBear;
	
	/*
	 * The BoardView's UI Container
	 */
	private Pane boardView;
	
	/*
	 * stores the location&info of cube
	 */
	private DoublingCube doublingCube;
	
	/*
	 * stores the location&info of cube
	 */
	private DoublingCube whiteDoublingCube;
	
	/*
	 * stores the location&info of cube
	 */
	private DoublingCube blackDoublingCube;
	
	public boolean boardInitialized = false;
	
	/**
	 * Create the game board
	 */
	public BoardView()
	{
		
		this.boardView = new Pane();
		
		points = new ArrayList<Point>();
		this.presetArrayList();
		
		// Set the boards image
		BackgroundImage myBI= new BackgroundImage(new Image("File:assets/board.png",1260,832,false,true),
		        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		this.boardView.setBackground(new Background(myBI));
		
		// Add the board to column 0, row 1, and make it grow vertically / downwards to occupy all the space below it
		GridPane.setConstraints(this.boardView, 0, 1);
		GridPane.setVgrow(this.boardView, Priority.ALWAYS);

		
		/**
		 * The board is divided into 4 quadrants / areas. 
		 * Quadrant1 = Top left corner
		 * Quadrant2 = Top right corner
		 * Quadrant3 = Bottom left corner
		 * Quadrant4 = Bottom right corner 
		 */
		
		
		/**
		 * Create quadrant 1 and fill it with 6 points. The point numbers 13-19
		 */
		HBox Quadrant1 = new HBox(0);	// Create a horizontal box to store 6 containers for the individual points
		Quadrant1.setPrefHeight(300);
		Quadrant1.setPrefWidth(430);
		Quadrant1.setTranslateX(140);
		Quadrant1.setTranslateY(110);
		Quadrant1.setPadding(new Insets(0, 0, 0, 5));
		for(int i=13; i<19; i++) { // Loop through 6 times
			VBox pointGrid = new VBox(); // Create a vertical container to store the checkers vertically
			pointGrid.setPrefHeight(300);
			pointGrid.setPrefWidth(70);
			Quadrant1.getChildren().addAll(pointGrid);
			Point newPoint = new Point(pointGrid, i);
			points.set(i, newPoint);
		}
	
		
		/**
		 * Create quadrant 2 and fill it with 6 points. The point numbers 19-25
		 */
		HBox Quadrant2 = new HBox(24);	
		Quadrant2.setPrefHeight(300);
		Quadrant2.setPrefWidth(398);
		Quadrant2.setTranslateX(713);
		Quadrant2.setTranslateY(110);
		Quadrant2.setPadding(new Insets(0, 0, 0, 5));
		for(int i=19; i<25; i++) {
			VBox pointGrid = new VBox();
			pointGrid.setPrefHeight(300);
			pointGrid.setPrefWidth(70);
			Quadrant2.getChildren().addAll(pointGrid);
			Point newPoint = new Point(pointGrid, i);
			points.set(i, newPoint);
		}
		
		/**
		 * Create quadrant 3 and fill it with 6 points. The point numbers from 12-6 
		 */
		HBox Quadrant3 = new HBox(0);	// Create a horizontal box to store 6 containers for the individual points
		Quadrant3.setPrefHeight(300);
		Quadrant3.setPrefWidth(420);
		Quadrant3.setTranslateX(130);
		Quadrant3.setTranslateY(484);
		Quadrant3.setAlignment(Pos.BOTTOM_CENTER);
		Quadrant3.setPadding(new Insets(0, 0, 0, 5));
		for(int i=12; i>6; i--) {
			VBox pointGrid = new VBox();
			pointGrid.setPrefHeight(300);
			pointGrid.setMaxHeight(300);
			pointGrid.setPrefWidth(70);
			pointGrid.setAlignment(Pos.BOTTOM_CENTER);
			Quadrant3.getChildren().addAll(pointGrid);
			Point newPoint = new Point(pointGrid, i);
			points.set(i, newPoint);
		}
	
		/**
		 * Create quadrant 4 and fill it with 6 points. The point numbers from 6-1
		 */
		HBox Quadrant4 = new HBox(24);	
		Quadrant4.setPrefHeight(300);
		Quadrant4.setMinHeight(300);
		Quadrant4.setMaxHeight(300);
		//Quadrant4.setStyle("-fx-background-color:red;");
		Quadrant4.setPrefWidth(400);
		Quadrant4.setTranslateX(713);
		Quadrant4.setTranslateY(484);
		Quadrant4.setAlignment(Pos.BOTTOM_CENTER);
		
		Quadrant4.setPadding(new Insets(0, 0, 0, 5));
		for(int i=6; i>0; i--) {
			VBox pointGrid = new VBox();	
			pointGrid.setPrefHeight(300);
			pointGrid.setMaxHeight(300);
			pointGrid.setPrefWidth(70);
			pointGrid.setAlignment(Pos.BOTTOM_CENTER);
			Quadrant4.getChildren().addAll(pointGrid);
			Point newPoint = new Point(pointGrid, i);
			points.set(i, newPoint);
		}
	
		/*Doubling cube
		 * When not in use, will be in centre
		 */
		
		this.setDoublingCube(new DoublingCube("neutral"));
		this.setWhiteDoublingCube(new DoublingCube("white"));
		this.setBlackDoublingCube(new DoublingCube("black"));
		

		/**
		 * White BAR
		 */
		VBox whiteBarContainer = new VBox(-10);
		//whiteBarContainer.setAlignment(Pos.BOTTOM_CENTER);
		whiteBarContainer.setPrefHeight(300);
		whiteBarContainer.setPrefWidth(67);
		//whiteBarContainer.setStyle("-fx-background-color:red");
		whiteBarContainer.setTranslateX(608);
		whiteBarContainer.setTranslateY(80);
		this.setWhiteBar(new Bar(whiteBarContainer)); // Set the bar as the container we created
		

		/**
		 * Black BAR
		 */
		VBox blackBarContainer = new VBox(-10);
		//whiteBarContainer.setAlignment(Pos.BOTTOM_CENTER);
		blackBarContainer.setPrefHeight(300);
		blackBarContainer.setPrefWidth(67);
		//blackBarContainer.setStyle("-fx-background-color:red");
		blackBarContainer.setTranslateX(608);
		blackBarContainer.setTranslateY(500);
		this.setBlackBar(new Bar(blackBarContainer)); // Set the bar as the container we created
		

		/**
		 * White BEAR-OFF
		 */
		VBox whiteBearGrid = new VBox(2);
		whiteBearGrid.setAlignment(Pos.BOTTOM_CENTER);
		whiteBearGrid.setPrefHeight(693);
		whiteBearGrid.setPrefWidth(67);
		//whiteBearGrid.setStyle("-fx-background-color:red");
		whiteBearGrid.setTranslateX(30);
		whiteBearGrid.setTranslateY(80);
		this.setWhiteBear(new Bear(whiteBearGrid)); // Set the bar as the container we created
	
		/**
		 * Black BEAR-OFF
		 */
		VBox blackBearGrid = new VBox(2);
		blackBearGrid.setAlignment(Pos.BOTTOM_CENTER);
		blackBearGrid.setPrefHeight(693);
		blackBearGrid.setPrefWidth(67);
		//blackBearGrid.setStyle("-fx-background-color:red");
		blackBearGrid.setTranslateX(1167);
		blackBearGrid.setTranslateY(80);
		this.setBlackBear(new Bear(blackBearGrid)); // Set the bar as the container we created
	
		
		// Add all of these newly created containers / quadrants to the board
		this.boardView.getChildren().addAll(Quadrant1, Quadrant2, Quadrant3, Quadrant4, whiteBarContainer, blackBarContainer, whiteBearGrid, blackBearGrid, 
				this.doublingCube.getUI(), this.whiteDoublingCube.getUI(), this.blackDoublingCube.getUI());
		
		if (Backgammon.getCurrentGame().isBearTestGame == true) {
			//
            this.initTestPieces();
        } else {
            // Place the starting pips/checkers onto the board
            this.initPieces();
        }
		
	}
	
	
	public void initPieces()
	{
		

		Point first_point = Point.getPoint(1);
		for(int i=0; i<2; i++) {
			first_point.push("black");
		}
		
		Point sixth_point = Point.getPoint(6);
		for(int i=0; i<5; i++) {
			sixth_point.push("white");
		}
		//sixth_point.bearOff(Point.getPoint(1));


		Point eighth_point = Point.getPoint(8);
		for(int i=0; i<3; i++) {
			eighth_point.push("white");
		}
		
		Point thirteenth_point = Point.getPoint(13);
		for(int i=0; i<5; i++) {
			thirteenth_point.push("white");
		}
		
		Point twenty_fourth = Point.getPoint(24);
		for(int i=0; i<2; i++) {
			twenty_fourth.push("white");
		}
		
		
		Point twelfth_point = Point.getPoint(12);
		for(int i=0; i<5; i++) {
			twelfth_point.push("black");
		}
		
		Point seventeenth_point = Point.getPoint(17);
		for(int i=0; i<3; i++) {
			seventeenth_point.push("black");
		}
		
		Point nineteenth_point = Point.getPoint(19);
		for(int i=0; i<5; i++) {
			nineteenth_point.push("black");
		}
		
	}
	
	public void clearPieces() {
		for (int i = 1; i < 25; i++) {
			while (!points.get(i).isEmpty())
				points.get(i).pop();
		}
	}

	public void initTestPieces() {
        Point first_point = Point.getPoint(1);
        Point second_point = Point.getPoint(2);
        Point third_point = Point.getPoint(3);
        Point fourth_point = Point.getPoint(4);
        Point fifth_point = Point.getPoint(5);
        Point sixth_point = Point.getPoint(6);
       
        for (int i = 0; i < 2; i++)
            first_point.push("white");
        for (int i = 0; i < 2; i++)
            second_point.push("white");
        for (int i = 0; i < 2; i++)
            third_point.push("white");
        for (int i = 0; i < 2; i++)
            fourth_point.push("white");
        for (int i = 0; i < 3; i++)
            fifth_point.push("white");
        for (int i = 0; i < 4; i++)
            sixth_point.push("white");
       
        Point ninteenth_point = Point.getPoint(19);
        Point twentieth_point = Point.getPoint(20);
        Point twentyfirst_point = Point.getPoint(21);
        Point twentysecond_point = Point.getPoint(22);
        Point twentythird_point = Point.getPoint(23);
        Point twentyfourth_point = Point.getPoint(24);
       
        for (int i = 0; i < 4; i++)
            ninteenth_point.push("black");
        for (int i = 0; i < 3; i++)
            twentieth_point.push("black");
        for (int i = 0; i < 2; i++)
            twentyfirst_point.push("black");
        for (int i = 0; i < 2; i++)
            twentysecond_point.push("black");
        for (int i = 0; i < 2; i++)
            twentythird_point.push("black");
        for (int i = 0; i < 2; i++)
            twentyfourth_point.push("black");
    }
	/**
	 * Return UI Element of ChatView
	 * @return
	 */
	public Pane getUIElement()
	{
		return this.boardView;
	}
	
	public void presetArrayList() {
		for (int i=0; i < 25; i++) {
			points.add(i,new Point(new VBox(), i));
		}
	}
	
	//Method which moves a checker based on what move the player picked
	public static void doMove(int [] a) {
		
		Point start = points.get(a[0]);
		Point destination = points.get(a[1]);
		int bearFlag = a[3];
		System.out.println("a[]: " + a[0] + " " + a[1] + " " + a[2] + " " + a[3]);
		//if bear flag is set to true, do bear off
		if (bearFlag == 1) {
			Backgammon.getCurrentGame().getChat().addGameNotification("*-1 checker Point [" + a[0] + "] -> Bear Off *");
			start.bear();
			Backgammon.getCurrentGame().restrictedMove = start.getNumber();
		}			
		//for regular moves
		else if (bearFlag == 0){		
			Backgammon.getCurrentGame().getChat().addGameNotification("*-1 checker Point [" + a[0] + "] -> Point [" + a[1] + "]*");
			start.moveOneTo(destination);
			Backgammon.getCurrentGame().restrictedMove = Math.abs(a[0] - a[1]);
		}
		
		
		
	}

	public Bar getBlackBar() {
		return blackBar;
	}


	public void setBlackBar(Bar blackBar) {
		this.blackBar = blackBar;
	}


	public Bar getWhiteBar() {
		return whiteBar;
	}


	public void setWhiteBar(Bar whiteBar) {
		this.whiteBar = whiteBar;
	}


	public Bear getBlackBear() {
		return blackBear;
	}


	public void setBlackBear(Bear blackBear) {
		this.blackBear = blackBear;
	}


	public Bear getWhiteBear() {
		return whiteBear;
	}


	public void setWhiteBear(Bear whiteBear) {
		this.whiteBear = whiteBear;
	}
	
	public void setWhiteDoublingCube(DoublingCube whiteDoublingCube) {
		this.whiteDoublingCube = whiteDoublingCube;
	}
	
	public void setBlackDoublingCube(DoublingCube blackDoublingCube) {
		this.blackDoublingCube = blackDoublingCube;
	}
	
	public void setDoublingCube(DoublingCube doublingCube) {
		this.doublingCube = doublingCube;
	}
	
	public DoublingCube getWhiteDoublingCube() {
		return whiteDoublingCube;
	}
	
	public DoublingCube getBlackDoublingCube() {
		return blackDoublingCube;
	}
	
	public DoublingCube getDoublingCube() {
		return doublingCube;
	}


}
