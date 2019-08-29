package idkMate;

import java.awt.Color;
import java.awt.Dimension;

import idkMate.Assets.Checker;
import idkMate.Assets.GameStatus;
import idkMate.Assets.Point;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class Backgammon extends Application {
	
	public static final Dimension PREF_SIZE = new Dimension (1563,900);
	
	/**
	 * The game window
	 */
	public static Stage mainWindow;
	
	/**
	 * The default game scene i.e playing
	 */
	public Scene gameScene;
	
	/**
	 * Player info labels
	 */
	public static Label player1Info;
	public static Label player2Info;
	
	/**
	 * Game object
	 * 
	 */
	private static Game game;
	
	/**
	 * Games window
	 */
	public static Stage gameWindow;
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	public Backgammon() {
		
	}

	@Override
	public void start(Stage window) 
	{
		try {
			
			this.gameWindow = window;
			
			generate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	public void generate()
	{
		game = new Game();
		game.createBoardUI();
		game.createPlayerInfoUI();
		game.createSideUI();
		
		// Set window details			
		mainWindow = this.gameWindow;
		mainWindow.setTitle("Backgammon");
		mainWindow.setResizable(false);
		mainWindow.setOnCloseRequest(e -> onClose());
			
		// Initialize the games default scene
		this.gameScene = new Scene(game.layout, PREF_SIZE.getWidth(), PREF_SIZE.getHeight());
		
		// Set the games default scene as the windows' current scene
		mainWindow.setScene(this.gameScene);	
		mainWindow.show();
		
		
		game.start();
	}
	
	/**
	 * Set an on close condition for the window (to stop the game)
	 */
	public static void onClose()
	{
		
		
		System.out.println("Game ended");
	
	}
	
	/** 
	 * Force close the game (buggy?)
	 * 
	 */
	public static void forceClose()
	{
		mainWindow.fireEvent(
		    new WindowEvent(
		    		mainWindow,
		        WindowEvent.WINDOW_CLOSE_REQUEST
		    )
		);
	}
	
	public static Game getCurrentGame()
	{
		return Backgammon.game;
	}

	public static void startNewGame()
	{
		Backgammon.getCurrentGame().gameStatus = GameStatus.running;
		Backgammon b = new Backgammon();
		b.generate();
	}
}