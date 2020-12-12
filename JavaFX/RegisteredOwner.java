package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class RegisteredOwner{

	
	public Scene getScene() {
		BorderPane pane = new BorderPane();

	    // Place nodes in the pane
	    pane.setTop(new LogoPaneAndTitleBarR(pane));
	    pane.setCenter(new RegedOwner()); 
	   
	    
	    // Create a scene and place it in the stage
	    Rectangle2D r = Screen.getPrimary().getBounds();
	    Scene scene = new Scene(pane, r.getWidth()-200, r.getHeight()-200);
	    return scene;
	  }
	} 

	
class RegedOwner extends VBox {
	  public RegedOwner() {
	    setStyle("-fx-background-color: #E0EEE0");
		setSpacing(125); 
		Text text = new Text("Are you already a registered owner?");
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		Button b1 = new Button("Yes");
		Button b2 = new Button("No");
		b1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
		b2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
		b1.setPrefHeight(60);
		b1.setPrefWidth(300);
		b2.setPrefHeight(60);
		b2.setPrefWidth(300);
		getChildren().addAll(text, b1, b2);
		setAlignment(Pos.CENTER);
		

		b1.setOnAction(e -> {
			OwnerMenuLoginRegister ownerMenu = new OwnerMenuLoginRegister();
			ownerMenu.setRegLogVal(true);
			Scene ownerMenuScene = ownerMenu.getOwnerMenuScene();
			PCMMenu.primaryStage.setScene(ownerMenuScene);
		});
		
		b2.setOnAction(e -> {
			OwnerMenuLoginRegister ownerMenu = new OwnerMenuLoginRegister();
			ownerMenu.setRegLogVal(false);
			Scene ownerMenuScene = ownerMenu.getOwnerMenuScene();
			PCMMenu.primaryStage.setScene(ownerMenuScene);
			});	
	  }
		
	}	
		
	class LogoPaneAndTitleBarR extends HBox{
		public LogoPaneAndTitleBarR(BorderPane pane) {
		

			FileInputStream input=null;
			try {
				input = new FileInputStream("resources/logo.png");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Image image = new Image(input);
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(100);
			imageView.setFitWidth(225);
			HBox hboxImage = new HBox();
			hboxImage.getChildren().add(imageView);
			
			Text heading = new Text("PCM Menu");
			heading.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
			heading.setTextAlignment(TextAlignment.CENTER);
			
			heading.setFill(Color.DARKOLIVEGREEN);
			HBox hboxText = new HBox();
			hboxText.setPadding(new Insets(25,20,20,20));
			hboxText.getChildren().add(heading);
			
			setStyle("-fx-background-color: #C0D9AF");
			HBox.setHgrow(hboxText, Priority.ALWAYS);
			
			getChildren().addAll(hboxText, hboxImage);
			
	    
		} 
	}
	
	
	

	

