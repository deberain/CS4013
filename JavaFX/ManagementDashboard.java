package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.stage.Screen;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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


public class ManagementDashboard{
	BorderPane pane = new BorderPane();
	VBox centerOfPane;
	
	public void setCenterOfPane(VBox pane) {
		this.centerOfPane = pane;
		PCMMenu.primaryStage.setScene(getManagementDashboardScene());
	}
	
	public Scene getManagementDashboardScene() {
		
	    // Place nodes in the pane
	    pane.setTop(new LogoPaneAndTitleM()); 
	    pane.setLeft(new SideMenuM());
	    if(centerOfPane != null) {
	    	pane.setCenter(centerOfPane);
	    }else {
	    	pane.setCenter(new MainPaneM());
	    }
	    // Create a scene and place it in the stage
	    Rectangle2D r = Screen.getPrimary().getBounds();
	    Scene scene = new Scene(pane, r.getWidth()-200, r.getHeight()-200);
	    return scene;
	  }
	} 

	
	class MainPaneM extends StackPane {
	  public MainPaneM() {
	    setStyle("-fx-background-color: #E0EEE0");
	  }	
	}	
		
		
	class SideMenuM extends VBox{
		public SideMenuM() {
		setStyle("-fx-background-color: #C0D9AF");	
			
		MenuItem allOwners, byArea;
		allOwners = new MenuItem("All Owners");
		byArea = new MenuItem("By Eircode");
		MenuButton overPropTax = new MenuButton("Overdue Tax",null,allOwners, byArea);
		overPropTax.setPopupSide(Side.RIGHT);
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
		MenuItem byProp = new MenuItem("By Property");
		MenuItem byOwner = new MenuItem("By Owner");
		propTaxPayData.getItems().addAll(byProp, byOwner);
	    propTaxPayData.setStyle("-fx-font-weight: bold;");
		
		Button quit = new Button("Quit");
		quit.setPrefWidth(200);
		quit.setAlignment(Pos.CENTER_LEFT);
		quit.setStyle("-fx-font-weight: bold;");
		
		getChildren().addAll(overPropTax, stats, expTaxRates, propTaxPayData, quit);
		
		overPropTax.setOnAction(e ->{
			ManagementDashboard mDash = new ManagementDashboard();
			VBox vbox = new VBox();
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			mDash.setCenterOfPane(vbox);
		});
		
		
		byArea.setOnAction(e ->{	
			ManagementDashboard mDash = new ManagementDashboard();
			VBox vbox = new VBox();
			//vbox stuff
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(15);
			vbox.setPadding(new Insets(20,20,20,20));
			  
			Label year = new Label ("Enter year you want data from");
			year.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			TextField yearInput = new TextField();
			yearInput.setMaxWidth(500);
			yearInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			Label eircodeArea = new Label("Enter eircode of area");
			eircodeArea.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			TextField eircodeAreaInput = new TextField();
			eircodeAreaInput.setMaxWidth(500);
			eircodeAreaInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			Button getOTaxEir = new Button("Get Overdue Tax");
			getOTaxEir.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			
			vbox.getChildren().addAll(year, yearInput, eircodeArea, eircodeAreaInput, getOTaxEir);
			mDash.setCenterOfPane(vbox);
				
			getOTaxEir.setOnAction(b ->{
				String yearInputVal = yearInput.getText();
				String eircodeAreaVal = eircodeAreaInput.getText();
				//send above values to appropriate method and return result if any to be put in a VBox and output on screen.
			});
		});
		
		
		allOwners.setOnAction(e ->{
			ManagementDashboard mDash = new ManagementDashboard();
			VBox vbox = new VBox();
			//vbox stuff
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(15);
			vbox.setPadding(new Insets(20,20,20,20));
			  
		  	Label year = new Label ("Enter year you want data from");
			year.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			TextField yearInput = new TextField();
			yearInput.setMaxWidth(500);
			yearInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			Button getOTaxAll = new Button("Get Overdue Tax");
			getOTaxAll.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				
			vbox.getChildren().addAll(year, yearInput, getOTaxAll);
			mDash.setCenterOfPane(vbox);
			
			getOTaxAll.setOnAction(b ->{
				String yearInputVal = yearInput.getText();
				//send above values to appropriate method and return result if any to be put in a VBox and output on screen.
			});
		});
		
		
		
		
		stats.setOnAction(e ->{
			ManagementDashboard mDash = new ManagementDashboard();
			VBox vbox = new VBox();
			//vbox stuff
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(15);
			vbox.setPadding(new Insets(20,20,20,20));
			  
			Label areaLabel = new Label ("Enter Area you want stats for (By eircode)");
			areaLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			TextField areaInput = new TextField();
			areaInput.setMaxWidth(500);
			areaInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			Button getStats = new Button("Get Statistics");
			getStats.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			
			vbox.getChildren().addAll(areaLabel, areaInput, getStats);
			mDash.setCenterOfPane(vbox);
				
			getStats.setOnAction(b ->{
				String eircodeVal = areaInput.getText();
				//send above values to appropriate method and return result if any to be put in a VBox and output on screen.
			});
		});
		
		
		expTaxRates.setOnAction(e ->{
			ManagementDashboard mDash = new ManagementDashboard();
			VBox vbox = new VBox();
			//vbox stuff
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(15);
			vbox.setPadding(new Insets(20,20,20,20));
			  
			Label flatRate = new Label ("Enter a Value for the flat charge");
			flatRate.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			TextField flatRateInput = new TextField();
			flatRateInput.setMaxWidth(500);
			flatRateInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			
			Label marValRate = new Label ("Enter a value for the rate for market values between 150,000 and 400,000 (0.xx)");
			marValRate.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			TextField marValRateInput = new TextField();
			marValRateInput.setMaxWidth(500);
			marValRateInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			
			Label marValRate2 = new Label ("Enter a value for the rate for market values between 400,000 and 650,000 (0.xx)");
			marValRate2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			TextField marValRateInput2 = new TextField();
			marValRateInput2.setMaxWidth(500);
			marValRateInput2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			
			Label marValRate3 = new Label ("Enter a value for the rate for market values above 650,000 (0.xx)");
			marValRate3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			TextField marValRateInput3 = new TextField();
			marValRateInput3.setMaxWidth(500);
			marValRateInput3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			
			Label regionType = new Label ("Enter a value for the rate for market values above 650,000 (0.xx)");
			regionType.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			ChoiceBox<String> regionTypeVal = new ChoiceBox<String>();
			regionTypeVal.getItems().addAll("City", "Large town", "Small town", "Village", "Countryside");
			
			Label pPR = new Label ("Enter a value for non principle private residence tax");
			pPR.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));				
			TextField pPRInput = new TextField();
			pPRInput.setMaxWidth(500);
			pPRInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			
			Button exp = new Button("Test with new values");
			exp.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
						
			vbox.getChildren().addAll(flatRate, flatRateInput, marValRate, marValRateInput, marValRate2, marValRateInput2, marValRate3, marValRateInput3, regionType, regionTypeVal, pPR, pPRInput, exp);
			mDash.setCenterOfPane(vbox);
			
			exp.setOnAction(b ->{
				String flatRateVal = flatRateInput.getText();
				String marVal1 = marValRateInput.getText();
				String marVal2 = marValRateInput2.getText();
				String marVal3 = marValRateInput3.getText();
				String regType = regionTypeVal.getValue();
				String principalPrivateRes = pPRInput.getText();
				//send above values to appropriate method and return result if any to be put in a VBox and output on screen.
			});
		});
		
		propTaxPayData.setOnAction(e ->{
			ManagementDashboard mDash = new ManagementDashboard();
			VBox vbox = new VBox();
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			mDash.setCenterOfPane(vbox);
		});
		
		byProp.setOnAction(e ->{
			ManagementDashboard mDash = new ManagementDashboard();
			VBox vbox = new VBox();
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(15);
			vbox.setPadding(new Insets(20,20,20,20));
			
			Label selectProperty = new Label("Select Property");
			selectProperty.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			ChoiceBox<String> propList = new ChoiceBox<String>();
			ArrayList<String> exampleValues = new ArrayList<String>();
			exampleValues.add("example property");
			propList.getItems().addAll(exampleValues);
			Button propTaxData = new Button("Get data for property");
			propTaxData.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			vbox.getChildren().addAll(selectProperty, propList, propTaxData);
			mDash.setCenterOfPane(vbox);
			
			propTaxData.setOnAction(b ->{
				String propertyVal = propList.getValue();
				//send above values to appropriate method and return result if any to be put in a VBox and output on screen.
			});
			
		});
		
		byOwner.setOnAction(e ->{
			ManagementDashboard mDash = new ManagementDashboard();
			VBox vbox = new VBox();
			vbox.setStyle("-fx-background-color: #E0EEE0");	
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(15);
			vbox.setPadding(new Insets(20,20,20,20));
			
			Label selectOwner = new Label("Select Owner");
			selectOwner.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			ChoiceBox<String> ownerList = new ChoiceBox<String>();
			ArrayList<String> exampleValues = new ArrayList<String>();
			exampleValues.add("example owner");
			ownerList.getItems().addAll(exampleValues);
			Button ownerTaxData = new Button("Get data for Owner");
			ownerTaxData.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			vbox.getChildren().addAll(selectOwner, ownerList, ownerTaxData);
			mDash.setCenterOfPane(vbox);
			ownerTaxData.setOnAction(b ->{
				String ownerVal = ownerList.getValue();
				//send above values to appropriate method and return result if any to be put in a VBox and output on screen.
			});
		});
		
		quit.setOnAction(e ->{
			PCMMenu restart = new PCMMenu();
			restart.start(PCMMenu.primaryStage);
			
		});
		
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

