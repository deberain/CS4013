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

public class OwnerMenuLoginRegister{
	private boolean regOwner;
	
	public void setRegLogVal(boolean val) {
		this.regOwner = val;
	}
	
	public Scene getOwnerMenuScene() {
		
		BorderPane pane = new BorderPane();

	    // Place nodes in the pane
	    pane.setTop(new LogoPaneAndTitle()); 
	    
	    pane.setCenter(new MainPane(regOwner)); 
	    
	    // Create a scene and place it in the stage
	    Rectangle2D r = Screen.getPrimary().getBounds();
	    Scene scene = new Scene(pane, r.getWidth()-200, r.getHeight()-200);
	    return scene;
	  }	
	} 

	
	class MainPane extends VBox {
	  public MainPane(boolean regOwner) {
		  setAlignment(Pos.CENTER);
		  setStyle("-fx-background-color: #E0EEE0");
		  setSpacing(25);
		  setPadding(new Insets(20,20,20,20));
		  
		  Label name = new Label("Name");
			name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
			TextField nameInput = new TextField();
			nameInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
			nameInput.setMaxWidth(500);
			Label address = new Label("Address(street, city, town)");
			address.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
			TextField addressInput = new TextField();
			addressInput.setMaxWidth(500);
			addressInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
			Label eircode = new Label("Eircode");
			TextField eircodeInput= new TextField();
			eircodeInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
			eircode.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
			eircodeInput.setMaxWidth(500);
			Button buttonLogin = new Button("Login");
			buttonLogin.setMaxWidth(250);
			buttonLogin.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
			setMargin(buttonLogin, new Insets(40,0,0,0));
			Button buttonReg = new Button("Register");
			buttonReg.setMaxWidth(250);
			buttonReg.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
			setMargin(buttonReg, new Insets(40,0,0,0));
			
		if(regOwner==true) {
			getChildren().addAll(name,nameInput,address,addressInput,eircode,eircodeInput, buttonLogin);
		}else if(regOwner==false) {
			getChildren().addAll(name,nameInput,address,addressInput,eircode,eircodeInput, buttonReg);
		}
		buttonLogin.setOnAction(e ->{
			//also take in input data and send to relevant class
			OwnerDashboard oDash = new OwnerDashboard();
			PCMMenu.primaryStage.setScene(oDash.getOwnerMenuScene());
		});
		buttonReg.setOnAction(e ->{
			//also take in input data and send to relevant class
			OwnerDashboard oDash = new OwnerDashboard();
			PCMMenu.primaryStage.setScene(oDash.getOwnerMenuScene());
		});
	    
	  }
		
	}	
		
		
	class SideMenu extends VBox{
		public SideMenu() {
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
		
		} 	
	}
	
	class LogoPaneAndTitle extends HBox{
		public LogoPaneAndTitle() {
		
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
