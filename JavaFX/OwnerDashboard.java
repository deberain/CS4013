package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.stage.Screen;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class OwnerDashboard{
	BorderPane pane = new BorderPane();
	VBox centerOfPane;
	
	public void setCenterOfPane(VBox pane) {
		this.centerOfPane = pane;
		PCMMenu.primaryStage.setScene(getOwnerMenuScene());
	}
	
	
	public Scene getOwnerMenuScene() {
		
	    // Place nodes in the pane
	    pane.setTop(new LogoPaneAndTitleOwner()); 
	    pane.setLeft(new SideMenuOwner());
	    if(centerOfPane != null) {
	    	pane.setCenter(centerOfPane);
	    }else {
	    	pane.setCenter(new MainPaneOwner());
	    }
	    // Create a scene and place it in the stage
	    Rectangle2D r = Screen.getPrimary().getBounds();
	    Scene scene = new Scene(pane, r.getWidth()-200, r.getHeight()-200);
	    return scene;
	  }
	
    	
	} 

	
	class MainPaneOwner extends VBox {
	  public MainPaneOwner() {
		  setStyle("-fx-background-color: #E0EEE0");
	  }
		
	}	
		
	class SideMenuOwner extends VBox{
		public SideMenuOwner() {
		setStyle("-fx-background-color: #C0D9AF");	
			
		Button regProp = new Button("Register Property");
		regProp.setPrefWidth(200);
		regProp.setAlignment(Pos.CENTER_LEFT);
		regProp.setStyle("-fx-font-weight: bold;");
		
		Button payTax = new Button("Pay Tax");
		payTax.setPrefWidth(200);
		payTax.setAlignment(Pos.CENTER_LEFT);
		payTax.setStyle("-fx-font-weight: bold;");
		
		Button viewDueTax = new Button("View Tax Due");
		viewDueTax.setPrefWidth(200);
		viewDueTax.setAlignment(Pos.CENTER_LEFT);
		viewDueTax.setStyle("-fx-font-weight: bold;");
		
		Button paymentsMade = new Button("Payments Made");
		paymentsMade.setPrefWidth(200);
		paymentsMade.setAlignment(Pos.CENTER_LEFT);
		paymentsMade.setStyle("-fx-font-weight: bold;");
		
		MenuButton pastStat = new MenuButton("View Past Statements");
	    pastStat.setPrefWidth(200);
	    pastStat.setPopupSide(Side.RIGHT);
	    pastStat.getItems().addAll(new MenuItem("By Year"), new MenuItem("By Property"));
	    pastStat.setStyle("-fx-font-weight: bold;");
		
		Button quit = new Button("Quit");
		quit.setPrefWidth(200);
		quit.setAlignment(Pos.CENTER_LEFT);
		quit.setStyle("-fx-font-weight: bold;");
		
		getChildren().addAll(regProp, payTax, viewDueTax, paymentsMade, pastStat, quit);
		
		quit.setOnAction(e ->{
			OwnerDashboard oDash = new OwnerDashboard();
			VBox vbox = new VBox();
			vbox.getChildren().add(new Label("QUIT"));
			oDash.setCenterOfPane(vbox);
			
		});
		
		regProp.setOnAction(e ->{
			OwnerDashboard oDash = new OwnerDashboard();
			VBox vbox = new VBox();
			//vbox stuff
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			
			vbox.setAlignment(Pos.CENTER);
			  
			  vbox.setSpacing(15);
			  vbox.setPadding(new Insets(20,20,20,20));
			  
			  	Label propAddress = new Label("Property Address (street, city, county)");
				propAddress.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				TextField propAddressInput = new TextField();
				propAddressInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				propAddressInput.setMaxWidth(500);
				
				Label eircode = new Label("Property Eircode");
				TextField eircodeInput= new TextField();
				eircodeInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				eircode.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				eircodeInput.setMaxWidth(500);
				
				Label estMarValue = new Label("Estimated Market Value");
				TextField estMarValInput= new TextField();
				estMarValInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				estMarValue.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				estMarValInput.setMaxWidth(500);
				
				Label propLocCat = new Label("Enter location Category (City/Large town/Small town/village/countryside))");
				TextField propLocCatInput= new TextField();
				propLocCatInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				propLocCat.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				propLocCatInput.setMaxWidth(500);
				
				Label pPR = new Label("Is this property a principal private residence? (yes/no):");
				TextField pPRInput= new TextField();
				pPRInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				pPR.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				pPRInput.setMaxWidth(500);
				
				Button regProperty = new Button("Register Property");
				regProperty.setMaxWidth(250);
				regProperty.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				setMargin(regProperty, new Insets(40,0,0,0));

			
			
				vbox.getChildren().addAll(propAddress, propAddressInput, eircode, eircodeInput, estMarValue, estMarValInput, propLocCat, propLocCatInput, pPR, pPRInput, regProperty);
				oDash.setCenterOfPane(vbox);
				
				regProperty.setOnAction(b ->{
					//Take input values and send to the necessary class.
				});
			
		});
		
		
		payTax.setOnAction(e ->{
			OwnerDashboard oDash = new OwnerDashboard();
			VBox vbox = new VBox();
			//vbox stuff
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			
			vbox.setAlignment(Pos.CENTER);
			  
			  vbox.setSpacing(15);
			  vbox.setPadding(new Insets(20,20,20,20));
			  
			  //to be continued
			
		});
		
		} 
	}
	
	class LogoPaneAndTitleOwner extends HBox{
		public LogoPaneAndTitleOwner() {
		
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
			
			Text heading = new Text("Owner Dashboard");
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

