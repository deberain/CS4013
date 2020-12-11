import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * Class created to represent the Command Line Interface for the Management Menu
 * 
 * Author: Jonathan Falvey
 * 
 */
public class ManagementMenu {
    Scanner scan = new Scanner(System.in);
    PropertyOwnersList owners = new PropertyOwnersList();

    //READ IN DATA HERE

    int yearTaxLastCalculated = 2019;

    /**
     * 
     * Method that loops while user issues different commands
     * 
     * Author: Jonathan Falvey
     * 
     */
    public void run() {
        if (LocalDate.now().getYear() > yearTaxLastCalculated) {
            owners.calculateAllTax();
            yearTaxLastCalculated = LocalDate.now().getYear();
        }

        boolean more = true;

        while(more) {
            System.out.println("P)roperty tax payment data     O)verdue property tax     S)tatistics     E)xperiment with tax rates      Q)uit");
            String command = scan.nextLine().toUpperCase();


            if (command.equals("P")) {
                System.out.println("P)roperty    O)wner");
                command = scan.nextLine().toUpperCase();

                if (command.equals("P")) {
                    System.out.println("Select property: ");
                    Property choice = getPropertyChoice(owners.getAllProperties());
                    owners.getTaxPaymentData(choice);
                }
                else if (command.equals("O")) {
                    System.out.println("Select property owner: ");
                    PropertyOwner choice = getOwnerChoice(owners.getOwners());
                    owners.getTaxPaymentData(choice);
                }
            }
            else if (command.equals("O")) {
                System.out.println("Please enter the year you want data from: ");
                int year = scan.nextInt();
                System.out.println("Get data from : A)ll property owners    P)roperty owners from particular area");
                command = scan.nextLine().toUpperCase();

                if (command.equals("A")) {
                    owners.getAllOverdueTax(year);
                } else if(command.equals("P")) {
                    System.out.println("Enter area to check (eircode format): ");
                    String area = scan.nextLine();

                    owners.getAllOverdueTax(year, area);
                }
            }
            else if (command.equals("S")) {
                System.out.println("Enter area you would like to receive statistics for (eircode format): ");
                String area =  scan.nextLine();

                System.out.println(owners.getStatistics(area));
            }
            else if (command.equals("E")) {
                System.out.println("Enter a Value for the flat charge: ");
                double flat = scan.nextDouble();

                System.out.println("Enter a value for the rate for market values between 150,000 and 400,000 (0.xx): ");
                double rate1 = scan.nextDouble();

                System.out.println("Enter a value for the rate for market values between 400,000 and 650,000 (0.xx): ");
                double rate2 = scan.nextDouble();

                System.out.println("Enter a value for the rate for market values above 650,000 (0.xx): ");
                double rate3 = scan.nextDouble();

                System.out.println("Enter Values for location tax (city, large town, small town, village, countryside)");
                String location = scan.nextLine();

                System.out.println("Enter a value for Non principle private residence tax: ");
                double ppr = scan.nextDouble();

                owners.compareRevenue(flat, rate1, rate2, rate3, location, ppr);
            }
            else if (command.equals("Q")) {
                more = false;
            }
        }

    }

    /**
     * 
     * Method for user to choose a property from a list of available options
     * 
     * Author: Jonathan Falvey
     * 
     * @param choices the arraylist to fetch options from
     * @return returns the property the user has chosen
     */
    private Property getPropertyChoice(ArrayList<Property> choices)
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
     * Method for user to choose a Property Owner from a list of available options
     * 
     * Author: Jonathan Falvey
     * 
     * @param choices an arraylist containing all the options
     * @return returns the owner chosen by the user
     */
    private PropertyOwner getOwnerChoice(ArrayList<PropertyOwner> choices)
    {
        if (choices.size() == 0) return null;
        while (true)
        {
            char c = 'A';
            for (PropertyOwner choice : choices)
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
