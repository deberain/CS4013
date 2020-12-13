
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	
	public PropertyOwnerMenu menu = new PropertyOwnerMenu();
	
	public PropertyOwner loggedIn;
	
	public void setLoggedIn(String name, String Password) {
		loggedIn = menu.getLoggedIn(name, Password);
	}
	 
	public void registerUser(String name, String address, String eircode, String password) {
		menu.registerUser(name, address, eircode, password);
	}

	class MainPaneOwner extends VBox {
	  public MainPaneOwner() {
		  setStyle("-fx-background-color: #E0EEE0");
	  }	
	}	
		
	class SideMenuOwner extends VBox{
		public SideMenuOwner() {
		setStyle("-fx-background-color: #C0D9AF");	
		
		try {
			menu.readData();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
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
		
		Button pastStat = new Button("View Past Statements");
		pastStat.setPrefWidth(200);
		pastStat.setAlignment(Pos.CENTER_LEFT);
		pastStat.setStyle("-fx-font-weight: bold;");
		
		Button quit = new Button("Quit");
		quit.setPrefWidth(200);
		quit.setAlignment(Pos.CENTER_LEFT);
		quit.setStyle("-fx-font-weight: bold;");
		
		getChildren().addAll(regProp, payTax, viewDueTax, paymentsMade, pastStat, quit);
	
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
				String propertyAddress = propAddressInput.getText();
				String propertyEircode = eircodeInput.getText();
				Double estMarValueInput = Double.parseDouble(estMarValInput.getText());
				String propLoc = propLocCatInput.getText();
				String pprtext = pPRInput.getText();
				boolean ppr;
				if(pprtext.equalsIgnoreCase("yes")) {
					ppr = true;
				} else {
					ppr = false;
				}
				//send above values to appropriate method and return result if any to be put in a VBox and output on screen.
				menu.registerProperty(loggedIn, propertyAddress, propertyEircode, estMarValueInput, propLoc, ppr);
				
				Label regSuccess = new Label("Registered successfully");
				vbox.getChildren().clear();
				vbox.getChildren().add(regSuccess);
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
			  
			boolean propTaxDue = (loggedIn.getTaxDue() == 0);
			if(propTaxDue) {
				Label propsToPayTaxOn = new Label ("Select The property");
				propsToPayTaxOn.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				
				ChoiceBox<Property> propChoice = new ChoiceBox<Property>();
				
				ObservableList<Property> propChoices = FXCollections.observableArrayList();
				
				propChoice.setItems(propChoices);
				
				propChoices.addAll(loggedIn.displayProperties());
				
				Property chosenProp = propChoice.getValue();
				
				ChoiceBox<Tax> taxYears = new ChoiceBox<Tax>();
				
				ObservableList<Tax> taxesUnpaid = FXCollections.observableArrayList();
				
				taxYears.setItems(taxesUnpaid);
				
				taxesUnpaid.addAll(chosenProp.getAllTaxUnpaid());
				
				Label taxYearLabel = new Label("Select Year");
				taxYearLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				
				Button payButton = new Button("Pay");
				payButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				vbox.getChildren().addAll(propsToPayTaxOn, propChoice, taxYearLabel, taxYears, payButton);
				oDash.setCenterOfPane(vbox);
				payButton.setOnAction(b ->{
					
					Tax tax = taxYears.getValue();
					//send above values to appropriate method and return result if any to be put in a VBox and output on screen.
					loggedIn.payTax(tax.getCurrentValue(), chosenProp, tax.yearDue);
					vbox.getChildren().clear();
					Label paying = new Label("Paying...");
					vbox.getChildren().add(paying);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    Label paid = new Label("Tax value of " + String.format("%.2f",tax.getCurrentValue()) + " paid for " + tax.yearDue);
                    vbox.getChildren().clear();
                    vbox.getChildren().add(paid);
				});
			}else {
				Label label = new Label("No tax due at the moment");
				label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				vbox.getChildren().addAll(label);
				oDash.setCenterOfPane(vbox);
			}
		});
		
		viewDueTax.setOnAction(e ->{
			OwnerDashboard oDash = new OwnerDashboard();
			VBox vbox = new VBox();
			//vbox stuff
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(15);
			vbox.setPadding(new Insets(20,20,20,20));
			  
			Label taxDue = new Label("Below is a summary of all tax currently due");
			taxDue.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
			
			String propsAndTaxFormattedString = loggedIn.propsAndTaxFormated();
			Text propsAndTaxFormatted = new Text(propsAndTaxFormattedString);
			propsAndTaxFormatted.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
			vbox.getChildren().addAll(taxDue, propsAndTaxFormatted);
			oDash.setCenterOfPane(vbox);
			//no input needs to be taken
		});
		
		paymentsMade.setOnAction(e ->{
			OwnerDashboard oDash = new OwnerDashboard();
			VBox vbox = new VBox();
			//vbox stuff
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(15);
			vbox.setPadding(new Insets(20,20,20,20));
			  
			Label paymentsMadeLabel = new Label("Below is a summary of all payments");
			paymentsMadeLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
			
			String paymentsFormattedString = "";
			
        	for(Payment payed:loggedIn.getPayments()) {
        		paymentsFormattedString += payed.ownerFormat() + "\n";
        	}
			
			Text paymentsFormatted = new Text(paymentsFormattedString);
			paymentsFormatted.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
			vbox.getChildren().addAll(paymentsMadeLabel, paymentsFormatted);
			oDash.setCenterOfPane(vbox);
			//no inputs need to be taken
		});
		
		pastStat.setOnAction(e ->{
			OwnerDashboard oDash = new OwnerDashboard();
			VBox vbox = new VBox();
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(15);
			vbox.setPadding(new Insets(20,20,20,20));
			
		  	Label pastStatLabel = new Label("Below are the past statement details");
			pastStatLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
			
			String pastStatData = loggedIn.displayPropBalancesByYear();
			Text pastStatDataFormatted = new Text(pastStatData);
			pastStatDataFormatted.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
			vbox.getChildren().addAll(pastStatLabel, pastStatDataFormatted);
			oDash.setCenterOfPane(vbox);
			
			oDash.setCenterOfPane(vbox);
			//no input needs to be taken
		});
		
		quit.setOnAction(e ->{
			PCMMenu restart = new PCMMenu();
			restart.start(PCMMenu.primaryStage);
		});
		} 
	}
	
	class LogoPaneAndTitleOwner extends HBox{
		public LogoPaneAndTitleOwner() {
		
			FileInputStream input=null;
			try {
				input = new FileInputStream("../resources/logo.png");
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
}

