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

public class ManagementDashboard{
	
	public Scene getManagementDashboardScene() {
		
		BorderPane pane = new BorderPane();

	    // Place nodes in the pane
	    pane.setTop(new LogoPaneAndTitleM()); 
	    pane.setLeft(new SideMenuM());
	    pane.setCenter(new MainPaneM("//Put main page content here")); 
	    
	    // Create a scene and place it in the stage
	    Rectangle2D r = Screen.getPrimary().getBounds();
	    Scene scene = new Scene(pane, r.getWidth()-200, r.getHeight()-200);
	    return scene;
	  }
	} 

	
	class MainPaneM extends StackPane {
	  public MainPaneM(String title) {
	    getChildren().add(new Label(title));
	    setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
	    setStyle("-fx-background-color: #E0EEE0");
	  }
		
	}	
		
		
	class SideMenuM extends VBox{
		public SideMenuM() {
		
		setStyle("-fx-background-color: #C0D9AF");	
			
		Button overPropTax = new Button("Overdue Property Tax");
		overPropTax.setPrefWidth(200);
		overPropTax.setAlignment(Pos.CENTER_LEFT);
		overPropTax.setStyle("-fx-font-weight: bold;");
		
		Button stats = new Button("Statistics");
		stats.setPrefWidth(200);
		stats.setAlignment(Pos.CENTER_LEFT);
		stats.setStyle("-fx-font-weight: bold;");
		
		Button expTaxRates = new Button("Experiment With Tax Rated");
		expTaxRates.setPrefWidth(200);
		expTaxRates.setAlignment(Pos.CENTER_LEFT);
		expTaxRates.setStyle("-fx-font-weight: bold;");
		
		MenuButton propTaxPayData = new MenuButton("Payment Data");
		propTaxPayData.setPrefWidth(200);
		propTaxPayData.setPopupSide(Side.RIGHT);
		propTaxPayData.getItems().addAll(new MenuItem("By Property"), new MenuItem("By Owner"));
	    propTaxPayData.setStyle("-fx-font-weight: bold;");
		
		Button quit = new Button("Quit");
		quit.setPrefWidth(200);
		quit.setAlignment(Pos.CENTER_LEFT);
		quit.setStyle("-fx-font-weight: bold;");
		
		getChildren().addAll(overPropTax, stats, expTaxRates, propTaxPayData, quit);
		
		} 
	}
	
	class LogoPaneAndTitleM extends HBox{
		public LogoPaneAndTitleM() {
		
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
			
			Text heading = new Text("Management Dashboard");
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

