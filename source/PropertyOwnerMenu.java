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

    int yearTaxLastCalculated = 2019;

    /**
     *
     * Method that loops while user issues commands
     *
     */
    public void run() {
        if (LocalDate.now().getYear() > yearTaxLastCalculated) {
            owners.calculateAllTax();
            yearTaxLastCalculated = LocalDate.now().getYear();
        }
        
        System.out.println("Are you already a registered owner (Yes/No):");
        if(scan.nextLine().contentEquals("Yes")) {
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
        	propOwner = new PropertyOwner(ownerName, new Address(addressParts[0], addressParts[1], addressParts[2]), eircode);
        	owners.registerOwner(propOwner);
            System.out.println("You are now a registered property owner.");
        }
        System.out.println("Hi "+propOwner.getName());

        boolean more = true;
        while(more) {
            System.out.println("R)egister Property     P)ay property tax     V)iew property list and tax due     S)how past years statements filtered by year/property      G)et past payments made		Q)uit");
            String command = scan.nextLine().toUpperCase();


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
                if(scan.nextLine().equals("yes")) {
                	ppr = true;
                }
                owners.registerProperty(propOwner, new Address(addressParts[0], addressParts[1],addressParts[2]), eircode, estimatedMarketVal, locCat, ppr);
                System.out.println("The property has been registered.");
                
            }else if(command.equals("P")) {
            	if(propOwner.getTaxDue() == 0){
            		System.out.println("No tax due at the moment.");
            	}else {
            		boolean anotherProp = true;

                    System.out.println("You owe a total of €"+propOwner.getTaxDue());
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
                                Tax selectedTaxYear = getTaxChoice(propOwner.getAllUnpaidTaxes());

                                propOwner.payTax(selectedTaxYear.getCurrentValue(), selectedProp, selectedTaxYear.yearDue);
                                System.out.println("Paying...");
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Tax value of " + selectedTaxYear.getCurrentValue() + " paid for " + selectedTaxYear.yearDue);
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
            }else if (command.equals("V")) {
                System.out.println("The following is a list of your properties and tax due:");
                System.out.println(propOwner.propsAndTaxFormated());
            }else if (command.equals("S")) {
                //to be done once "year" sorted.
                propOwner.displayPropBalancesByYear();

            }else if (command.equals("G")) {
                //get past payments made
            	for(Payment payed:propOwner.getPayments()) {
            		System.out.println(payed.ownerFormat());
            	}
            }else if (command.equals("Q")) {
                more = false;
            }
        }

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
    	System.out.println("Enter Identification details:\nName:\n");
    	String name = scan.nextLine();
    	System.out.println("Enter home address(street, city, county):");
    	String[] details = scan.nextLine().split(" "); 
    	Address findAddress = new Address(details[0], details[1], details[2]);
    	System.out.println("Enter home eircode:");
    	String eircode = scan.nextLine();
    	PropertyOwner propOwner = owners.getPropOwner(name, findAddress, eircode);
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
}
