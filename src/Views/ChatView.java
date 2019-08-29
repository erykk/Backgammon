package Views;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import idkMate.Backgammon;
import idkMate.Player;
import idkMate.Assets.GameStatus;
import idkMate.Assets.Memory;
import idkMate.Assets.Point;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/** 
 * This is the area to the right of the GameView, where the chat goes. 
 * */
public class ChatView {

	/** 
	 * The container for the side content
	 */
	private GridPane sideView;
	
	/**
	 * A list of messages from the input 
	 */
	private ArrayList<Label> messages = new ArrayList<>();
	
	
	private TextFlow textContent;
	
	
	/*
	 * The UI Element & main container of ChatView
	 */
	public ScrollPane scrollPane;

	public String getChatMessage;
	
	/**
	 * Create the chatbox
	 * @param sideView
	 */
	public ChatView(GridPane sideView)
	{
		super();
		this.scrollPane = new ScrollPane();
		
		this.sideView = sideView;
		this.scrollPane.setPrefHeight(900); // height of 800
		this.scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		this.scrollPane.setStyle("-fx-background-color:#1f1f1f");
		
		this.textContent = new TextFlow();
		this.textContent.setPrefHeight(900);
		this.textContent.setPrefWidth(283);
		this.textContent.setPadding(new Insets(35, 0, 0, 0));
		this.textContent.setDisable(false);
		this.textContent.setStyle("-fx-background-color:#1f1f1f");
		
		this.scrollPane.setContent(textContent);
		// Put the chatbox on col 0, row 0 (i.e below the dice buttons)
		GridPane.setConstraints(this.scrollPane, 0, 0);
		
		// Initialize / generate the input box section
		this.onStart();
	}
	
	/**
	 * Return UI Element of ChatView
	 * @return
	 */
	public ScrollPane getUIElement()
	{
		return this.scrollPane;
	}
	
	/**
	 * Create an input box for messages to be inputted into
	 */
	public void onStart()
	{
		
		// Create a new text field object
		TextField input = new TextField ();
		input.setPromptText("Click here to type message.."); // Set a placeholder for the input box
		
		// On input chat
		input.setOnKeyPressed(e -> onChat(e, input));
		
		// Add the input box to col 0, row 2 (i.e below the chat box)
		GridPane.setConstraints(input, 0, 2);
		
		this.sideView.getChildren().addAll(input);

		this.scrollPane.setPadding(new Insets(5,5,5,5));
		

		this.addChatMessage("Type commands for a list of commands\n");
		
		this.announceStart();
	}
	
	/**
	 * Announce the player's colors & prompt p1 to roll the die
	 */
	public void announceStart()
	{
		this.addChatAction("[The game has begun]");
		this.addChatAction("[" + Backgammon.getCurrentGame().player1.getName() + "]'s checker is white!");
		this.addChatAction("[" + Backgammon.getCurrentGame().player2.getName() + "]'s checker is black!");
		
		
		Player curPlayer = Backgammon.getCurrentGame().getCurrentTurn();
	
		this.addChatAnnouncement("Roll to determine first player..");
	}
	
	public void addChatMessage(String msg)
	{
		Text t = new Text();
		t.setText("" + msg + "\n");
		t.setStyle("-fx-fill: #47B0DC;");
		
		this.textContent.getChildren().addAll(t);
		this.scrollToBottom();
	}
	
	public void addChatMessage(Player p, String msg)
	{
		Text t = new Text();
		t.setText("[" + p.getName() + "]:\n" + msg + "\n");
		t.setStyle("-fx-fill: #47B0DC;");
		
		this.textContent.getChildren().addAll(t);
		this.scrollToBottom();

	}
	
	public void addChatAction(String msg)
	{
		Text t = new Text();
		t.setText("*" + msg + "*\n");
		t.setStyle("-fx-fill: #47B0DC;");
		
		this.textContent.getChildren().addAll(t);
		this.scrollToBottom();
	}
	
	public void addGameNotification(String msg)
	{
		Text t = new Text();
		t.setText("\n" + msg + "\n");
		t.setStyle("-fx-font-weight: bold; -fx-fill: #47B0DC;");
		
		this.textContent.getChildren().addAll(t);
		this.scrollToBottom();

	}
	
	
	public void addChatAnnouncement(String msg)
	{
		Text t = new Text();
		t.setText("\n" + msg + "\n");
		t.setStyle("-fx-font-weight: bold;-fx-fill: white;");
		
		this.textContent.getChildren().addAll(t);
		this.scrollToBottom();
	}
	
	public void addChatAlert(String msg)
	{
		Text t = new Text();
		t.setText("\n" + msg + "\n");
		t.setStyle("-fx-font-weight: bold;-fx-fill: red;");
		
		this.textContent.getChildren().addAll(t);
		this.scrollToBottom();
	}
	
	public void scrollToBottom()
	{
		new Timer().schedule(new TimerTask() {
	        @Override
	        public void run() {
	            Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                	Backgammon.getCurrentGame().getChat().scrollPane.setVvalue(1.0);
	               //    System.out.println("Scrolled to bottom");
	                }
	            });
	        }
	    }, 300);
		
	}
	public String getChatMessage(KeyEvent e, TextField input)
	{	
		if(e.getCode().equals(KeyCode.ENTER)) {
			
			String msg =  input.getText();
			return msg;
		}
		return null;
	}
	public void onChat(KeyEvent e, TextField input)
	{	
		if(e.getCode().equals(KeyCode.ENTER)) {
			
			String msg =  input.getText();
			

			if(msg.trim().isEmpty())
				return;
			
			//System.out.println("[" + msg + "]");
			
			
			if(Backgammon.getCurrentGame().gameStatus != GameStatus.running) {
				
				if(msg.toLowerCase().equals("yes") ||
						msg.toLowerCase().contains("no")) {
					
					if(Backgammon.getCurrentGame().gameStatus == GameStatus.finished)
					{

						if(msg.toLowerCase().equals("yes")) {
							Backgammon.startNewGame();
						}
						else {
							Backgammon.forceClose();
						}
						
					}
					
					return;
				}
				else {
					
					Backgammon.getCurrentGame().getChat().addGameNotification("Invalid response. Would you like another game? Yes/no.");
    				
					input.clear();
					
					return;
				}
			}
			
			if(msg.contains("commands")) {
				
				
				String commands = "";
				commands += "\n--------------------------\n";
				commands += "- /quit or quit\nQuits the game\n\n";
				commands += "- /move x1 x2\nMoves a point from point x1 to point x2\n\n";
				commands += "--------------------------";
				this.addChatMessage(commands);
				
				input.clear();
				return;

			}
			
			switch(msg)
			{
				case "quit":
				case "/quit": {
					System.out.println("Quitting..");
					Backgammon.forceClose();
					break;
				}
						
				case "next":
				case "skip": {
					
					Backgammon.getCurrentGame().skipTurn();
					input.clear();
					
					break;
				}
				case "cheat": {
					
					
					if(Backgammon.getCurrentGame().getCurrentTurn().getColor() == "black") {
						Backgammon.getCurrentGame().switchTurns();
					}
					
					
					
					Point p13 = Point.getPoint(13);
					Point p6 = Point.getPoint(6);
					Point p5 = Point.getPoint(5);
					Point p4 = Point.getPoint(4);
					Point p3 = Point.getPoint(3);
					Point p2 = Point.getPoint(2);
					Point p1 = Point.getPoint(1);

					Point p12 = Point.getPoint(12);
					Point p19 = Point.getPoint(19);
					Point p20 = Point.getPoint(20);
					Point p21 = Point.getPoint(21);
					Point p22 = Point.getPoint(22);
					Point p23 = Point.getPoint(23);
					Point p24 = Point.getPoint(24);
					
					// Initialize moves
					p1.moveOneTo(p19);
					p1.moveOneTo(p19);
					p24.moveOneTo(p6);
					p24.moveOneTo(p6);
					
					///////////////////////////////
					
					p6.moveOneTo(p5);
					p6.moveOneTo(p5);
					p6.moveOneTo(p4);
					p6.moveOneTo(p4);
					p6.moveOneTo(p3);
					p13.moveOneTo(p3);
					p13.moveOneTo(p2);
					p13.moveOneTo(p2);
					p13.moveOneTo(p1);
					p13.moveOneTo(p1);
					////
					
					p19.moveOneTo(p21);
					p19.moveOneTo(p21);
					p19.moveOneTo(p21);
					p19.moveOneTo(p22);
					p19.moveOneTo(p22);
					p19.moveOneTo(p22);
					p19.moveOneTo(p24);
					p12.moveOneTo(p24);
					p12.moveOneTo(p24);
					
					// Bar 3 whites
					Point p8 = Point.getPoint(8);
					p8.bear();
					p8.bear();
					p8.bear();
					

					// Bar 3 whites
					Point p17 = Point.getPoint(17);
					p17.bear();
					p17.bear();
					p17.bear();
					
					
					p6.bear();
					p6.bear();
					
					p12.bear();
					p12.bear();
					p12.bear();
					
					p21.bear();
					p21.bear();
					p21.bear();
					p22.bear();
					p22.bear();
					p22.bear();
					p24.bear();
					
					p5.bear();
					p5.bear();
					p4.bear();
					p4.bear();
					p3.bear();
					p3.bear();
					p2.bear();
					p2.bear();
					
					Backgammon.getCurrentGame().computeTurn();
					input.clear();
					
					break;
					
				}
				// Old cheat command
				case "old_cheat": {
					
				
					if(Backgammon.getCurrentGame().getCurrentTurn().getColor() == "black") {
						Backgammon.getCurrentGame().switchTurns();
					}
					
					
					
					Point p13 = Point.getPoint(13);
					Point p6 = Point.getPoint(6);
					Point p5 = Point.getPoint(5);
					Point p4 = Point.getPoint(4);
					Point p3 = Point.getPoint(3);
					Point p2 = Point.getPoint(2);
					Point p1 = Point.getPoint(1);

					Point p12 = Point.getPoint(12);
					Point p19 = Point.getPoint(19);
					Point p20 = Point.getPoint(20);
					Point p21 = Point.getPoint(21);
					Point p22 = Point.getPoint(22);
					Point p23 = Point.getPoint(23);
					Point p24 = Point.getPoint(24);
					
					// Initialize moves
					p1.moveOneTo(p19);
					p1.moveOneTo(p19);
					p24.moveOneTo(p6);
					p24.moveOneTo(p6);
					
					///////////////////////////////
					
					p6.moveOneTo(p5);
					p6.moveOneTo(p5);
					p6.moveOneTo(p4);
					p6.moveOneTo(p4);
					p6.moveOneTo(p3);
					p13.moveOneTo(p3);
					p13.moveOneTo(p2);
					p13.moveOneTo(p2);
					p13.moveOneTo(p1);
					p13.moveOneTo(p1);
					////
					
					p19.moveOneTo(p21);
					p19.moveOneTo(p21);
					p19.moveOneTo(p21);
					p19.moveOneTo(p22);
					p19.moveOneTo(p22);
					p19.moveOneTo(p22);
					p19.moveOneTo(p24);
					p12.moveOneTo(p24);
					p12.moveOneTo(p24);
					
					// Bar 3 whites
					Point p8 = Point.getPoint(8);
					p8.hit();
					p8.hit();
					p8.hit();
					

					// Bar 3 whites
					Point p17 = Point.getPoint(17);
					p17.hit();
					p17.hit();
					p17.hit();
					
					
					p6.bear();
					p6.bear();
					
					p23.bear();
					p23.bear();
					
					p12.bear();
					p12.bear();
					p12.bear();
					
					Backgammon.getCurrentGame().computeTurn();
					input.clear();
					
					break;
				}
				
				default: {
					this.addChatMessage(msg);
					input.clear();
					break;
				}
			}
			
			if(msg.contains("forcewin")) {
				
				Backgammon.getCurrentGame().gameStatus = GameStatus.blackwon;
				
				
				return;
			}
			
			// New move method, changes the aMove value of the Game class to the move which the player chose
			if(msg.contains("move")) {
				
			
				String[] parts = msg.split(" ");
				String p = parts[1];
				int choice = Integer.parseInt(p);
				
				System.out.print(choice);
				Backgammon.getCurrentGame().aMove = choice;
				Backgammon.getCurrentGame().isChoosing = false;
				Backgammon.getCurrentGame().choiceProcessed = false;

			}
			
			if(msg.contains("testbear"))
			{
				
				String[] parts = msg.split(" ");
				String p = parts[1];
				int choice = Integer.parseInt(p);
				
				Point point = Point.getPoint(choice);
				point.bear();
				
			}
			
			if(msg.contains("force")) {
				
				String[] parts = msg.split(" ");
				String point = parts[1];
				String point2 = parts[2];
				int p1 = Integer.parseInt(point);
				int p2 = Integer.parseInt(point2);
				
				
				if(Backgammon.getCurrentGame().getCurrentTurn().getColor() == "black") {
					
					p1 = p1 - 24 - 1;
					p1 = Math.abs(p1);
					
					p2 = p2 - 24 - 1;
					p2 = Math.abs(p2);
					
				}
				
				Point start = Point.getPoint(p1);
				
				Point destination = Point.getPoint(p2);
				
				
				int roll1 = Backgammon.getCurrentGame().getCurrentTurn().getRoll().getRoll1();
				int roll2 = Backgammon.getCurrentGame().getCurrentTurn().getRoll().getRoll2();
				
				int distance = Math.abs(p1 - p2);
				/*
				if(distance > roll1 && distance > roll2) {
					this.addChatAlert("The move you selected is out of range from your roll!");
					return;
				}
				
				if(Backgammon.getCurrentGame().getCurrentTurn().getColor() == "white") {
					if(p2 > p1) {
						this.addChatAlert("The move you selected is out of range for your color!");
						return;
					}
				}
				
				if(Backgammon.getCurrentGame().getCurrentTurn().getColor() == "black") {
					if(p1 > p2) {
						this.addChatAlert("The move you selected is out of range for your color!");
						return;
					}
					
				}
				
				if(destination.ownedBy() != null) {
					if(destination.ownedBy().getColor() != Backgammon.getCurrentGame().getCurrentTurn().getColor()) {
						this.addChatAlert("The destination point you selected is owned by the opponent!");
						return;
					}
				}*/
				
				start.moveOneTo(destination);
				Memory.setMemory(p1, p2);

			}
			
			if(msg.contains("undo")) {
               
				Point p = Point.getPoint(Memory.getPointA());
                Point q = Point.getPoint(Memory.getPointB());
                
                q.moveOneTo(p);
                
                this.addChatAlert("Board returned to last move!");
               
            }
			if(msg.contains("double")) {
				Backgammon.getCurrentGame().proposedDouble();
			}
			
		}
	}
	
}
