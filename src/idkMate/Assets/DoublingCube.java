package idkMate.Assets;


import java.util.Stack;

import idkMate.Player;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

public class DoublingCube {

	private double maxHeight = 201;
	private double maxWidth = 201;
	
	private String type;
	private HBox body;
	
	public DoublingCube(String type) {
		
		
		this.type = type;
		this.render();
		
	}
	
	public HBox getUI()
	{
		return this.body;
	}
	
	public void render()
	{
		
		this.body = new HBox();
		this.body.setMinSize(this.maxWidth, this.maxHeight);
		
		if(this.type == "white") {
			this.body.setTranslateY(320);
			this.body.setTranslateX(830);
		} else if(this.type == "black") {
			this.body.setTranslateY(320);
			this.body.setTranslateX(200);
		}
		else if(this.type == "neutral") {
			this.body.setTranslateY(320);
			this.body.setTranslateX(535);
		}
		
		
	}
	

	public void show()
	{
		this.body.setMaxSize(this.maxWidth, this.maxHeight);
	}
	
	public void hide()
	{
		this.body.setMinSize(0, 0);
	}
	
	public void renderImg() 
	{
		BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd.png",this.maxHeight, this.maxWidth, false, true),
		        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
				this.body.setBackground(new Background(DoublingCube));

		this.show();
	}

	
	public void renderWhiteImg(int multiplier) 
	{
		
		
		if(multiplier == 1) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd2.png",this.maxHeight, this.maxWidth, false, true),
	        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
	          BackgroundSize.DEFAULT);
			this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 2) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd4.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 4) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd8.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 8) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd16.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 16) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd32.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 32) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd64.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		
		this.show();
	}
	
	public void renderBlackImg(int multiplier) {
		
		if(multiplier == 1) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd2.png",this.maxHeight, this.maxWidth, false, true),
	        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
	          BackgroundSize.DEFAULT);
			this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 2) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd4.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 4) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd8.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 8) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd16.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 16) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd32.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		if(multiplier == 32) {
			BackgroundImage DoublingCube = new BackgroundImage(new Image("File:assets/dd64.png",this.maxHeight, this.maxWidth, false, true),
			        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
					this.body.setBackground(new Background(DoublingCube));
		}
		
		this.show();
	}
}
