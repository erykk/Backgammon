package Views;

import idkMate.Backgammon;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SideView extends GridPane {

	/**
	 * Create the side bar container box which is a grid in itself
	 * that will have 2 rows (one for the dice buttons, and the other for the chatbox chatView)
	 * @param windowLayout
	 */
	public SideView(GridPane windowLayout) {
		
		super();
		this.setStyle("-fx-background-color : #ccc;-fx-border-color : black; -fx-border-width : 0px 0px 0px 5px;");
	
		// Create a constraint so that the side bar container uses 20% of the entire windows width
		ColumnConstraints constraints = new ColumnConstraints();
		constraints.setPercentWidth(20); // 20 = 20%
		windowLayout.getColumnConstraints().addAll(constraints); // Add the constraint to the main windows layout
		
		// Add the side bar to column 1, row 0, | Span it across 1 column, and 2 rows (i.e takes up the space VERTICALLY of 2 rows)
		GridPane.setConstraints(this, 1, 0, 1, 2);
		
		// Generate the side section
		//this.diceSection();
		
		// Generate the dice section 
		this.chatSection();
		
		Backgammon.getCurrentGame().sideView = this;
	}
	

	/**
	 * Create the chat section at the bottom
	 */
	private void chatSection()
	{
		Backgammon.getCurrentGame().setChat(new ChatView(this));
		this.getChildren().addAll(Backgammon.getCurrentGame().getChat().getUIElement());
	}

}
