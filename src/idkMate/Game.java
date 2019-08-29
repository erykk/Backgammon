package idkMate;

import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Timers.ElapsedTimer;
import idkMate.Player;
import idkMate.Assets.DoublingCube;
import Views.BoardView;
import Views.ChatView;
import Views.SideView;
import idkMate.Assets.GameStatus;
import idkMate.Assets.Moves;
import idkMate.Assets.PointStatus;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

public class Game {
	
	
	/**
	 * Current status of the game;
	 */
	
	public GameStatus gameStatus;
	
	/**
	 * Doubling cube value multiplier, initially 1
	 */
	
	public int doublingCubeMult = 1;
	
	/**
	 * The timer for the game
	 */
	public Timer gameTimer;
	
	/**
	 * The timer for the game's interval
	 */
	public int timerInterval = 1;
	
	public GridPane layout;
	
	/**
	 * Is a bear test game  (enabled if you wanna set up the board for bearing test)
	 */
	public static boolean isBearTestGame = false;
	
	/**
	 * Time the game / match length
	 */
	public int gameDuration = 1;
	public ElapsedTimer eGameTimer;
	public Label eGameLabel;
	
	/**
	 * Is a test game 
	 */
	public static boolean isTestGame = false;
	
	/*
	 * The game's players
	 */
	public Player player1, player2;

	/*
	 * The game's chat command view
	 */
	public SideView sideView;
	
	
	/*
	 * The game's chat command view
	 */
	private ChatView chatView;
	
	/*
	 * games double cube
	 */
	public DoublingCube doublingCube;
	/**
	 * The game's board view
	 */
	private BoardView board;
	
	/*
	 * The player who's turn it is
	 */
	private Player currentTurn;
	
	/*
	 * Number of moves available to the current player. 
	 */
	private int playerMovesLeft = 0;
	
	/*
	 * Shows whether the player is in the process of choosing a turn
	 */
	public boolean isChoosing = false;
	
	/**
	 * Shows whether the player's choice has been proceesed
	 */
	public boolean choiceProcessed = false;
	
	
	//Used for eliminating moves which require dice roll already used
	public int restrictedMove = -1;
	
	public Moves moves;
	
	// The move the player chose to perform 
	public int aMove = -1;
	
	
	// The Games dice
	public Dice dice;
	

	/**
	 * If the roll is the start roll
	 */
	public boolean startRoll = true;
	
	public Game () {
		
		this.dice = new Dice();
		this.gameTimer = new Timer();
		this.layout = new GridPane();
		this.layout.setGridLinesVisible(true);
		this.gameStatus = GameStatus.running;
		
		if(this.isTestGame) {
			startTestGame();	
		} else {
			Triplet<String, String, Integer> result = displayDialog();
			player1 = new Player (result.getPlayer1(), result.getMaxScore());
			player2 = new Player (result.getPlayer2(), result.getMaxScore());
			
			// Set the players numbers
			player1.setNumber(1);
			player2.setNumber(2);
			
			// Set player colours P1 = White, P2 = Black
			player1.setColor("white");
			player2.setColor("black");
			this.setCurrentTurn(player1);
		}
	}
	
	//This is where the whole match will be played. (main game loop)
	public void start()
	{
		this.handle();
	}
	
	public void handle()
	{
		this.setStartTime();
		
		/* Surround the game handling in a looping timer (x is 'timerInterval' variable value)
		 that loops every x seconds
		
		 When using the timerTask we must reference variables in the class using 'Backgammon.getCurrentGame().[variableNamehere]'
		 because we are created a new thread. We cannot reference the variables within this block using
		 'this'.
		*/
		
		this.gameTimer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            Platform.runLater(new Runnable() {
	                @Override
	                public void run() {

	                	if (gameStatus == GameStatus.running) {
		                	
		                	// Edit the player's labels depending on the turns
		                	Backgammon.getCurrentGame().pendingLabelChanges();
		                	
		            		if(!Backgammon.getCurrentGame().board.boardInitialized) {
		            			Backgammon.getCurrentGame().board.boardInitialized = true;
		            		}
		            		
		            		//Starting roll case
		            		if(Backgammon.getCurrentGame().startRoll) {
		            			Backgammon.getCurrentGame().startRoll();
		            		} else {
		            			
		            			if(Backgammon.getCurrentGame().isChoosing) {
		            				// do nothing / halt game
		            				if(Backgammon.getCurrentGame().isTestGame) {
		            					//System.out.println("Waiting for user to choose..");
		            				}
		            				
		            				if(Backgammon.getCurrentGame().moves.displayValidMoves().length() <= 5) {
										Backgammon.getCurrentGame().chatView.addGameNotification("*No moves are available, skipping to next player!*");
										Backgammon.getCurrentGame().skipTurn();
										
		            				}
		            
		            				return;
		            			} 
		            			
		            			
		            				
	            				// If their move was not processed and they have moves left
	            				if(!Backgammon.getCurrentGame().choiceProcessed && 
	            						Backgammon.getCurrentGame().aMove != -1 && 
	            						Backgammon.getCurrentGame().playerMovesLeft > 0) {
	            				
	            					// Process their move
	            					int[] a = Backgammon.getCurrentGame().moves.getMove(Backgammon.getCurrentGame().aMove);
	            					restrictedMove = -1;
	            					BoardView.doMove(a);
	            					Backgammon.getCurrentGame().playerMovesLeft -= a[2];
	            					
	            					Backgammon.getCurrentGame().choiceProcessed = true;
	            					
	            					// Re calculate their moves
	            					Backgammon.getCurrentGame().moves = new Moves();
	            					Backgammon.getCurrentGame().chatView.addChatAnnouncement(Backgammon.getCurrentGame().moves.displayValidMoves());
	            					Backgammon.getCurrentGame().playerMovesLeft = Backgammon.getCurrentGame().moves.getMovesLeft();
	            					Backgammon.getCurrentGame().isChoosing = true;
	            					Backgammon.getCurrentGame().aMove = -1;
	            					
	            				}
	            				
		            			if(!Backgammon.getCurrentGame().choiceProcessed && Backgammon.getCurrentGame().aMove == -1) {
		            				Backgammon.getCurrentGame().handleTurn();
		            			}     			
		            			
		            		}
		            		
		            		gameStatus = checkForWin();
		            		
		                }
	                	
	                	else if (gameStatus != GameStatus.running) {
	                		

	                		Backgammon.getCurrentGame().setEndTime();
	                		Backgammon.getCurrentGame().gameTimer.cancel();
	                		
	                		Player winningPlayer = player1;
	                		
	                		// Decide winner by max points
	                		if(gameStatus == gameStatus.time_ended) {
	                			
	                			Backgammon.getCurrentGame().chatView.addGameNotification("The match time is UP! Deciding the winner..");
	                			
	                			if(winningPlayer.getCurrentPoints() < player2.getCurrentPoints()) {
	                				winningPlayer = player2;
	                			}
	                			else if(winningPlayer.getCurrentPoints() == player2.getCurrentPoints()) {
	                				// decide the winner randomly
	                				Backgammon.getCurrentGame().chatView.addGameNotification("The winner will be randomly selected since scores match..");
		                			
	                				int r = new Random().nextInt(2 + 1 - 1) + 1;
	                				if(r == 1)
	                					winningPlayer = player1;
	                				else
	                					winningPlayer = player2;
	                			}
	                		} else {
	                			
	                			if (gameStatus == GameStatus.blackwon)
	                				winningPlayer = player2;
	                			
	                		}
	                		
	                		
	                		System.out.println(winningPlayer.getName() + " wins this game!");
                			Backgammon.getCurrentGame().chatView.addGameNotification(winningPlayer.getName() + " wins this game!");
                			//Include functionality for doubling cube 
                			int pointsWon = determinePointsWon(winningPlayer) * doublingCubeMult;
                			winningPlayer.setCurrentPoints(pointsWon);
                			winningPlayer.playerLabel.setText(winningPlayer.getName() + ": " + winningPlayer.getCurrentPoints() 
                					+ "/" + winningPlayer.getMaxPoints());
                			

	                		if(gameStatus == GameStatus.time_ended) {
	                			
	                			Backgammon.getCurrentGame().chatView.addGameNotification("Press any key to start another game");
	                			Backgammon.mainWindow.getScene().setOnKeyPressed(event -> {
	                	           Backgammon.startNewGame();
	                	        });
	                		}
	                		else {
	                			//Extend this part to define instructions for what to do when a player wins
	                			gameStatus = GameStatus.finished;
	            				Backgammon.getCurrentGame().chatView.addGameNotification("Would you like another game? Yes/no.");
	                		}
                			
                			/*if (winningPlayer.getCurrentPoints() > winningPlayer.getMaxPoints()) {
                				
                			}
                			
                			else {
                				board.clearPieces();
                				board.initPieces();
                				board.getBlackBear().clear();
                				board.getWhiteBear().clear();
                				gameStatus = GameStatus.running;
                				startRoll = true;
                				try {
									Thread.sleep(1500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
                			}*/
	                	}
	                	
	                	try {
							
	                		Thread.sleep(1500);
							Backgammon g = new Backgammon();
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	                }
	            });
	        }
	    }, 0, timerInterval * 1000);
		
		
		/*
		 * 
				//Main loop (incomplete) 
				while (true) {
					handleTurn();
				}
				
				
		 */
	}
	
	public void startRoll()
	{
		
		Backgammon.getCurrentGame().startRoll = false;
		
		int p1Roll = 0;
		int p2Roll = 0;
		Triplet<Integer, Integer, Boolean> diceRoll = new Triplet<Integer, Integer, Boolean>(0,0,false);
		
		while(p1Roll == p2Roll) {
			
			currentTurn = this.getCurrentTurn();
			diceRoll = this.dice.rollDice();
		
			p1Roll = diceRoll.getRoll1();
			this.chatView.addGameNotification(currentTurn.getName() + " rolls a [" + p1Roll + "].");			
			
			currentTurn = this.switchTurns();
			
			p2Roll = diceRoll.getRoll2();
			this.chatView.addGameNotification(currentTurn.getName() + " rolls a [" + p2Roll + "].");
			
			currentTurn = this.switchTurns();
			
		}
		
		this.startRoll = false;
		
		Player startingPlayer = (p1Roll > p2Roll) ? this.player1 : this.player2;
		
		this.setCurrentTurn(startingPlayer);
		
		this.chatView.addChatAnnouncement(startingPlayer.getName() + " rolled the highest!");
		
		this.chatView.addChatAnnouncement("Starting roll is: [" + p1Roll + "] and [" + p2Roll + "]");

		this.setPlayerRoll(diceRoll);		

		this.computeTurn();
	}
	
	public void computeTurn()
	{
		// Generate the players moves & announce them
		this.moves = new Moves();
		this.chatView.addChatAnnouncement(moves.displayValidMoves());
		// Set the number of moves the player has
		this.playerMovesLeft = moves.getMovesLeft();
		// Set the variable isChoosing so the game knows a player is currently choosing
		this.isChoosing = true;
	}
	
	public void handleTurn()
	{
		
		
		//Don't need this because the user changes turns manually by typing "next" but I could be wrong
		//this.switchTurns();
		
		Triplet<Integer, Integer, Boolean> roll = this.dice.rollDice();
		
		int roll1 = roll.getRoll1();
		int roll2 = roll.getRoll2();
		
		this.chatView.addGameNotification(this.currentTurn.getName() + " rolls a [" + roll1 + " & " + roll2 + "].");
		this.setPlayerRoll(roll);		

		this.computeTurn();
		
	}
	
	public void startTestGame()
	{
		this.gameDuration = 5;
		
		player1 = new Player ("Player1Name", 5);
		player2 = new Player ("Player2Name", 5);
		
		// Set the players numbers
		player1.setNumber(1);
		player2.setNumber(2);
		
		// Set player colours P1 = White, P2 = Black
		player1.setColor("white");
		player2.setColor("black");
		
		this.setCurrentTurn(player1);
	}
	

	public Triplet<String, String, Integer> displayDialog() {	
		
		//Create a new Dialog box 
		Dialog<Triplet<String,String,Integer>> dialog = new Dialog<>();
	    dialog.setTitle("New Game");
	    dialog.setOnCloseRequest(e -> dialog.close());
	    
	    //Add buttons: Start button which submits the player names and score and cancel
	    ButtonType startButton = new ButtonType("Start", ButtonData.APPLY);
	    dialog.getDialogPane().getButtonTypes().addAll(startButton, ButtonType.CANCEL);

	    GridPane gridPane = new GridPane();
	    gridPane.setHgap(15);
	    gridPane.setVgap(15);

	    //Input field for names and score
	    TextField p1 = new TextField();
	    p1.setPromptText("Player 1 name:");
	    TextField p2 = new TextField();
	    p2.setPromptText("Player 2 name:");
	    
	    ComboBox<Integer> points = new ComboBox<>();
	    points.getItems().addAll(5,7,9,11);
	    TextField match_duration = new TextField();
	    match_duration.setPromptText("Match duration (minutes)");
	    
	    gridPane.add(p1, 0, 0);
	    gridPane.add(p2, 1, 0);
	    gridPane.add(new Label("Points:"), 0, 1);
	    gridPane.add(points, 1, 1);
	    gridPane.add(match_duration, 0, 2);

	    
	    dialog.getDialogPane().setContent(gridPane);	    

	    //Dialog returns Triplet of 2 names and score when "start" is pressed
	    dialog.setResultConverter(btn -> {
	    	if (btn == startButton) {
	    		if(p1.getText().isEmpty()) // set default player 1 name to player1's name
	    			p1.setText("Player1'sName");
	    		if(p2.getText().isEmpty()) // set default player 2 name to player2's name
	    			p2.setText("Player2'sName");
	    		if(points.getValue() == null) // set default value to 5
	    			points.setValue(5);
	    		if(match_duration.getText().isEmpty()) // set default player 2 name to player2's name
	    			this.gameDuration = 15;
	    		else {
	    			this.gameDuration = Integer.parseInt(match_duration.getText());
	    		}
	    		return new Triplet<>(p1.getText(), p2.getText(),points.getValue());
	    	}	    	
	    	return null;
	    });
	    
	    // Request focus on the player1 name field by default.
	    Platform.runLater(() -> p1.requestFocus());	    
	    
	    Optional<Triplet<String,String,Integer>> result = dialog.showAndWait(); 
	    
	    /*
	     * This method will return a Triplet which contains the name of the 2 players
	     * and the maximum score which they will play until.
	     * */
	    return result.get();
	    
	}

	public Player getCurrentTurn() {
		
		if(currentTurn.getNumber() == 1)
			return this.player1;
		else
			return this.player2;

	}

	public void setCurrentTurn(Player currentTurn) {

		if(this.board != null) {
		if(this.board.boardInitialized)
			this.changePerspective(currentTurn);
		}
		
		this.currentTurn = currentTurn;
	}

	public void setPlayerRoll(Triplet<Integer, Integer, Boolean> roll) {
		
		this.currentTurn.setRoll(roll);
		
	}
	
	/**
	 * Get the opposing player
	 * @return
	 */
	public Player oppPlayer()
	{
		if(this.getCurrentTurn().getNumber() == 1)
			return this.player2;
		
		if(this.getCurrentTurn().getNumber() == 2)
			return this.player1;
		
		return null;
	}


	/**
	 * Toggle the current player to the next player
	 */
	public Player switchTurns()
	{
		this.setCurrentTurn((this.getCurrentTurn() == this.player1) ? this.player2 : this.player1);
		
		return this.getCurrentTurn();
		
	}
	
	/**
	 * Change the board image perspective depending on player
	 * @param player
	 */
	public void changePerspective(Player player)
	{
		if(player.getNumber() == 1) {
			
			BackgroundImage myBI= new BackgroundImage(new Image("File:assets/board.png",1260,832,false,true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
			this.board.getUIElement().setBackground(new Background(myBI));
			
		} else {
			
			BackgroundImage myBI= new BackgroundImage(new Image("File:assets/board2.png",1260,832,false,true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
			this.board.getUIElement().setBackground(new Background(myBI));
			
		}
	}
	
	public void createBoardUI()
	{
		this.board = new BoardView();
		this.layout.getChildren().addAll(this.board.getUIElement());
	}
	
	public void createPlayerInfoUI()
	{
		// Create playerInfo labels
		player1.playerLabel = new Label(this.player1.getName() + ": " 
				+ this.player1.getCurrentPoints() + "/" + this.player1.getMaxPoints());
		player2.playerLabel = new Label(this.player2.getName() + ": " 
				+ this.player2.getCurrentPoints() + "/" + this.player2.getMaxPoints());
		player1.playerLabel.setStyle("-fx-text-fill:white");
		player2.playerLabel.setStyle("-fx-text-fill:white");
		// Create a horizontal box layout to store the player info horizontally beside eachother
		HBox playerInfoBox = new HBox(200, player1.playerLabel, player2.playerLabel);	
		// Set the styling of the box layout
		playerInfoBox.setStyle("-fx-background-color:#1f1f1f;-fx-font-size: 25px;-fx-font-family:Helvetica;-fx-border-color : black; -fx-border-width : 0px 0px 5px 0px;");
		playerInfoBox.setPrefHeight(80);
		// Align the box content (the players' scores) in the center of the box
		playerInfoBox.setAlignment(Pos.CENTER);
		
		// Set a constraint so that the column of the player's info box to uses 80% of the entire windows space
		ColumnConstraints constraints = new ColumnConstraints();
		constraints.setPercentWidth(80); // 80 = 80% of the entire windows space
		this.layout.getColumnConstraints().addAll(constraints); // Add the constraint to the main layout we created at the start
		
		// Place the playerInfo horizontal box layout / container in col 0, row 0
		GridPane.setConstraints(playerInfoBox, 0, 0);
		
		// Add the playerInfo horizontal box layout / container to the main layout
		this.layout.getChildren().addAll(playerInfoBox);
	}
	
	public void pendingLabelChanges()
	{
		// Set bold depending on whos turn
    	if(Backgammon.getCurrentGame().currentTurn.getName() == Backgammon.getCurrentGame().player1.getName()) {
    		Backgammon.getCurrentGame().player1.playerLabel.setStyle("-fx-text-fill:red");
    		Backgammon.getCurrentGame().player2.playerLabel.setStyle("-fx-text-fill:white");
    	} else {
    		Backgammon.getCurrentGame().player2.playerLabel.setStyle("-fx-text-fill:red");
    		Backgammon.getCurrentGame().player1.playerLabel.setStyle("-fx-text-fill:white");
    	}
    	
	}
	
	public void createSideUI()
	{
		
		SideView sv = new SideView(this.layout);
		this.layout.getChildren().addAll(sv);
	
	}
	
	public BoardView getBoard()
	{
		return this.board;
	}
	
	public ChatView getChat()
	{
		return this.chatView;
	}
	
	public void setChat(ChatView chat)
	{
		this.chatView = chat;
	}
	
	
	public void skipTurn()
	{
		Backgammon.getCurrentGame().isChoosing = false;
		Backgammon.getCurrentGame().choiceProcessed = false;
		Backgammon.getCurrentGame().aMove = -1;
		Backgammon.getCurrentGame().switchTurns();
		Backgammon.getCurrentGame().chatView.addChatAlert(Backgammon.getCurrentGame().getCurrentTurn().getName() + " please make your move & type 'next'!");
		
	}
	public void proposedDouble() {
		
		Backgammon.getCurrentGame().chatView.addChatAlert("Do you accept the double proposed by " + Backgammon.getCurrentGame().getCurrentTurn().getName() + "?");
		switchTurns();
		if(Backgammon.getCurrentGame().chatView.getChatMessage.contains("yes") || Backgammon.getCurrentGame().chatView.getChatMessage.contains("Yes")) {
			switchTurns();
			Double();
		}
		else {
			switchTurns();
			int newPoints = currentTurn.getCurrentPoints();
			currentTurn.setCurrentPoints(newPoints++);
		}
	}
	public void Double() {
		if(player1.getCurrentPoints() == 4 && player2.getCurrentPoints() == 1) {
			
			Backgammon.getCurrentGame().chatView.addChatAlert("Cannot use doubling cube!");
		}
		else {
			currentTurn.setDoubleCubeFlag(true);
			Backgammon.getCurrentGame().chatView.addChatAlert(Backgammon.getCurrentGame().getCurrentTurn().getName() + " has used the doubling cube!");
			doublingCubeMult *= 2;
			if(currentTurn.getColor() == "white") {
				Backgammon.getCurrentGame().doublingCube.renderWhiteImg(doublingCubeMult);
			}
			if(currentTurn.getColor() == "black") {
				Backgammon.getCurrentGame().doublingCube.renderBlackImg(doublingCubeMult);
			}
		}
	}
		
	public GameStatus checkForWin() {
		
		if(this.eGameTimer.elapsed <= 0) {
			return GameStatus.time_ended;
		}
		if (board.getWhiteBear().size() == player1.getMaxPoints())
			return GameStatus.whitewon;
		else if (board.getBlackBear().size() == player2.getMaxPoints())
			return GameStatus.blackwon;
		else
			return GameStatus.running;
	}
	
	/**
	 * Check where on the board the opposing player's last checker is to determine
	 * whether the win is a gammon, backgammon or a regular win. 
	 */
	public int determinePointsWon(Player winningPlayer) {
		int pointsWon = 1;
		Player oppPlayer = (winningPlayer == player1) ? player2 : player1;
		if (oppPlayer == player2) {
			for (int i = 18; i >= 7; i--) {
				if (BoardView.points.get(i).checkStatus() == PointStatus.black
						|| BoardView.points.get(i).checkStatus() == PointStatus.bhit) {
					if(currentTurn.getDoubleCubeFlag() == true) {
						pointsWon = 2* doublingCubeMult;
					}
					else {
						pointsWon = 2;
					}
				}
					
			}
			for (int i = 1; i <= 6; i++) {
				if (BoardView.points.get(i).checkStatus() == PointStatus.black
						|| BoardView.points.get(i).checkStatus() == PointStatus.bhit) {
					if(currentTurn.getDoubleCubeFlag() == true) {
						pointsWon = 3 * doublingCubeMult;
					}
					else {
						pointsWon = 3;
					}
				}
						
					
			}
			
		}
		
		else if (oppPlayer == player1) {
			for (int i = 18; i >= 7; i--) {
				if (BoardView.points.get(i).checkStatus() == PointStatus.white
						|| BoardView.points.get(i).checkStatus() == PointStatus.whit) {
					if(currentTurn.getDoubleCubeFlag() == true) {
						pointsWon = 2* doublingCubeMult;
					}
					else {
						pointsWon = 2;
					}
				}
					
			}

			for (int i = 24; i <= 19; i--) {
				if (BoardView.points.get(i).checkStatus() == PointStatus.white
						|| BoardView.points.get(i).checkStatus() == PointStatus.whit) {
					if(currentTurn.getDoubleCubeFlag() == true) {
						pointsWon = 3* doublingCubeMult;
					}
					else {
						pointsWon = 3;
					}
				}
					
			}

			
		}
		
		return pointsWon;
	}
	
	public void setStartTime()
	{
		
		Font eGameFont = new Font(30);
	
		//eGameFont.
		this.eGameTimer = new ElapsedTimer(this.gameDuration);
		
	    this.eGameLabel = new Label(this.eGameTimer.s_time.get());
	    this.eGameLabel.setTranslateX(70);
	    this.eGameLabel.setTranslateY(20);
	    this.eGameLabel.setFont(eGameFont);
	    this.eGameLabel.setTextFill(Color.web("#fff"));
	    
	    
	    this.getBoard().getUIElement().getChildren().add(this.eGameLabel);
	    
	    this.eGameTimer.s_time.addListener(new InvalidationListener() {
	    	@Override
	    	public void invalidated(Observable observable) {
	    		Platform.runLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub

						Backgammon.getCurrentGame().eGameLabel.setText(Backgammon.getCurrentGame().eGameTimer.s_time.get());
					
					}
				
	    		});
	        }
	    });
		   
	
	}
	
	public void setEndTime()
	{
		this.eGameTimer.stopTimer();
	}
	
}
	


