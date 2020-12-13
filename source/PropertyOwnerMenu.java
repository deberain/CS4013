import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * Class created to represent the Command Line Interface for the Property Owner menu
 *
 * Author: Bryan Carty
 *
 */
public class PropertyOwnerMenu {
    Scanner scan = new Scanner(System.in);
    PropertyOwnersList owners = new PropertyOwnersList();
    PropertyOwner propOwner;

    //READ IN DATA HERE
    String pathToSampleData = "../resources/sampleData.csv";
    File sampleData = new File(pathToSampleData);

    String pathToWorkingData = "../resources/workingData.csv";
    FileWriter workingData;;

    int yearTaxLastCalculated;

    public PropertyOwnerMenu() {
    }

    /**
     *
     * Method that loops while user issues commands
     *
     */
    public void run() throws IOException {
        //check if sampledata exists, if yes, read it in
        if (sampleData.exists()) {
            System.out.println("Reading data...");
            readData();
        }

        //if the year has changed, calculate all taxes
        if (LocalDate.now().getYear() > yearTaxLastCalculated) {
            owners.calculateAllTax();
            yearTaxLastCalculated = LocalDate.now().getYear();
        }
        //determine user's identity
        System.out.println("Are you already a registered owner (Yes/No):");
        if(scan.nextLine().equalsIgnoreCase("Yes")) {
        	propOwner = getOwner();
        }else {
        	System.out.println("You must become a registered owner first./nPlease fill in the following details to proceed.");
        	System.out.println("Your Name:");
            String ownerName = scan.nextLine();

            System.out.println("Enter your home address (Street, city, county):");
            String address = scan.nextLine();
            String[] addressParts = address.split(", ");

            System.out.println("Enter your home eircode:");
            String eircode = scan.nextLine();

            System.out.println("Please enter a Password for your account");
            String password = scan.nextLine();

        	propOwner = new PropertyOwner(ownerName, new Address(addressParts[0], addressParts[1], addressParts[2]), eircode, password);
        	owners.registerOwner(propOwner);
            System.out.println("You are now a registered property owner.");
        }
        System.out.println("Hi "+propOwner.getName());

        boolean more = true;
        while(more) {
            System.out.println("R)egister Property     P)ay property tax     V)iew property list and tax due     S)how past years statements filtered by year/property      G)et past payments made		Q)uit");
            String command = scan.nextLine().toUpperCase();

            //register property is selected by user
            if (command.equals("R")) {
                System.out.println("Enter property details below:");
                System.out.println("Enter property address (Street, city, county):");
                String address = scan.nextLine();

                String[] addressParts = address.split(", ");
                System.out.println("Enter property eircode:");
                String eircode = scan.nextLine();

                System.out.println("Enter propertys estimated market value:");
                double estimatedMarketVal = scan.nextDouble();

                System.out.println("Enter location Category (City/Large town/Small town/village/countryside))");
                String locCat = scan.nextLine();

                System.out.println("Is this property a principal private residence? (yes/no):");
                boolean ppr = false;

                if(scan.nextLine().equalsIgnoreCase("yes")) {
                	ppr = true;
                }
                owners.registerProperty(propOwner, new Address(addressParts[0], addressParts[1],addressParts[2]), eircode, estimatedMarketVal, locCat, ppr);
                System.out.println("The property has been registered.");
                
            }
            //pay property tax is selected by user
            else if(command.equals("P")) {
            	if(propOwner.getTaxDue() == 0){
            		System.out.println("No tax due at the moment.");
            	}else {
            		boolean anotherProp = true;

                    System.out.println("You owe a total of "+propOwner.getTaxDue());
                    ArrayList<Property> propsToPayTaxOn = propOwner.displayProperties();

            		while(anotherProp) {
	            		System.out.println("Select which property you wish to pay tax on:");
	            		Property selectedProp = getPropChoice(propsToPayTaxOn);

	            		if (selectedProp.getBalance() > 0) {
                            System.out.println("You owe a total of "+selectedProp.getBalance()+" on this property.");
                            System.out.println("Do you wish to pay now (Yes/No)?");
                            String answer = scan.nextLine();

                            if(answer.equalsIgnoreCase("yes")) {
                                System.out.println("Select Year of which you would like to pay tax: ");
                                Tax selectedTaxYear = getTaxChoice(selectedProp.getAllTaxUnpaid());

                                propOwner.payTax(selectedTaxYear.getCurrentValue(), selectedProp, selectedTaxYear.yearDue);
                                System.out.println("Paying...");
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Tax value of " + String.format("%.2f",selectedTaxYear.getCurrentValue()) + " paid for " + selectedTaxYear.yearDue);
                            }
                        } else {
                            System.out.println("You have no tax currently due for this property!");
                        }

	            		if(propOwner.getTaxDue() > 0) {
	            			System.out.println("Do you wish to pay tax on another property (Yes/No):");
	            			if(scan.nextLine().equalsIgnoreCase("No")) {
	            				anotherProp = false;
	            			}
	            		} else {
                            System.out.println("You have paid all tax due for the current year!");
                            anotherProp = false;
                        }
            		}
            	}
            }
            //view property list and tax due is selected by the user
            else if (command.equals("V")) {
                System.out.println("The following is a list of your properties and tax due:");
                System.out.println(propOwner.propsAndTaxFormated());
            }
            //show past year statements selected by user
            else if (command.equals("S")) {
                System.out.print(propOwner.displayPropBalancesByYear());
            }
            //get past payments is selected by the user
            else if (command.equals("G")) {
            	for(Payment payed:propOwner.getPayments()) {
            		System.out.println(payed.ownerFormat());
            	}
            }
            //quit is selected by the user
            else if (command.equals("Q")) {
                more = false;
                writeData();
            }
        }

    }
    
    public PropertyOwner getLoggedIn(String Name, String password) {
    	return owners.getPropOwner(Name, password);
    }
    

    /**
     *
     * Method to determine who is accessing the interface
     *
     * Author: Bryan Carty
     *
     * @return returns the correct user from the list of Property owners
     */
    private PropertyOwner getOwner() {
    	System.out.println("Enter Identification details:\nName: ");
    	String name = scan.nextLine();
    	System.out.println("Enter Password");
    	String password = scan.nextLine();
    	PropertyOwner propOwner = owners.getPropOwner(name, password);
    	return propOwner;
    }

    /**
     *
     * Method for user to choose a property from a list of available options
     *
     * @param choices arraylist of properties to choose from
     * @return returns the property selected by the user
     */
    private Property getPropChoice(ArrayList<Property> choices)
    {
        if (choices.size() == 0) return null;
        while (true)
        {
            char c = 'A';
            for (Property choice : choices)
            {
                System.out.println(c + ") " + choice.format());
                c++;
            }
            String input = scan.nextLine();
            int n = input.toUpperCase().charAt(0) - 'A';
            if (0 <= n && n < choices.size())
                return choices.get(n);
        }
    }

    /**
     *
     * Method for user to choose a Tax from a list of available options
     *
     * @param choices arraylist of taxes to choose from
     * @return returns the tax chosen by the user
     */
    private Tax getTaxChoice (ArrayList<Tax> choices) {
        if (choices.size() == 0) return null;
        while(true) {
            char c = 'A';
            for (Tax choice : choices)
            {
                System.out.println(c + ") " + choice.format());
                c++;
            }
            String input = scan.nextLine();
            int n = input.toUpperCase().charAt(0) - 'A';
            if (0 <= n && n < choices.size())
                return choices.get(n);
        }
    }
    /**
     *
     * method that reads in data from a csv file for use by program
     *
     * @throws IOException potential exception if no file is found
     */
    public void readData() throws IOException {
        Scanner reader = new Scanner(sampleData);
        String row;
        PropertyOwner tempOwner = null;
        Property tempProperty = null;
        Tax tempTax;
        Payment tempPayment;

        while (reader.hasNext()) {
            row = reader.nextLine();
            String[] values = row.split(",");

            if (values[0].equalsIgnoreCase("Owner")) {
                tempOwner = new PropertyOwner(values[1], new Address(values[2], values[3], values[4]), values[5], Double.parseDouble(values[6]), Double.parseDouble(values[7]), values[8]);
                owners.registerOwner(tempOwner);
            }
            else if(values[0].equalsIgnoreCase("Property")) {
                tempProperty = new Property(tempOwner, new Address(values[1], values[2], values[3]), values[4], Double.parseDouble(values[5]), values[6], Boolean.parseBoolean(values[7]));
                tempOwner.registerProperty(tempProperty);
            }
            else if (values[0].equalsIgnoreCase("Tax")) {
                if (values.length < 4) {
                    tempTax = new Tax(tempOwner, tempProperty, Double.parseDouble(values[1]), Integer.parseInt(values[2]));
                } else {
                    tempTax = new Tax(tempOwner, tempProperty, Double.parseDouble(values[1]), Integer.parseInt(values[2]), Boolean.parseBoolean(values[3]), Double.parseDouble(values[4]), Integer.parseInt(values[5]));
                }
                tempProperty.addTax(tempTax);
            }
            else if (values[0].equalsIgnoreCase("Payment")) {
                tempPayment = new Payment(tempOwner,tempProperty,Integer.parseInt(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]));
                tempOwner.addPayment(tempProperty, tempPayment);
                tempProperty.addPayment(tempPayment);
            }
            else if (values[0].equalsIgnoreCase("Year")) {
                yearTaxLastCalculated = Integer.parseInt(values[1]);
            }
        }
        reader.close();
    }

    /**
     *
     * Method that writes important records to a csv file for later use by program
     * @throws IOException 
     */
    public void writeData() throws IOException {
    	workingData = new FileWriter(pathToWorkingData);
        PrintWriter out = new PrintWriter(workingData);

        out.write("Year," + yearTaxLastCalculated + "\n");

        for (PropertyOwner owner : owners.getOwners()) {
            out.write(owner.writeFormat());

            for (Property property : owner.getProperties()) {
                out.write(property.writeFormat());

                for (Tax tax : property.getTaxes()) {
                    out.write(tax.writeFormat());
                }
                for (Payment payment : property.getPayments()) {
                    out.write(payment.writeFormat());
                }
            }

        }
        out.close();
        System.out.println("Data Saved! Now exiting");
    }

    //Methods for use with GUI
	public void registerUser(String name, String address, String eircode, String password) {
		owners.registerOwner(new PropertyOwner(name, address, eircode, password));
		
	}

	public void registerProperty(PropertyOwner owner, String propertyAddress, String propertyEircode, Double estMarValueInput,
			String propLoc, boolean ppr) {
		String[] addr = propertyAddress.split(", ");
		Address address = new Address(addr[0], addr[1], addr[2]);
		
		owners.registerProperty(owner, address, propertyEircode, estMarValueInput, propLoc, ppr);
		
	}
	
	


}
